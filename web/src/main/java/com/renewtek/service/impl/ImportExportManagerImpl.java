package com.renewtek.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.jsontype.TypeResolverBuilder;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.xml.XmlMapper;
import com.renewtek.dao.BusinessProcessDAO;
import com.renewtek.dao.ReferenceDAO;
import com.renewtek.model.BusinessProcess;
import com.renewtek.model.CalendarDate;
import com.renewtek.model.DayModel;
import com.renewtek.model.DefaultWeekDay;
import com.renewtek.model.Reference;
import com.renewtek.model.ServiceLevelAgreement;
import com.renewtek.model.WorkHourRange;
import com.renewtek.service.ImportExportManager;
import com.renewtek.service.SlaExport;
import com.renewtek.webapp.controller.HibernateAwareIgnoringXmlMapper;

public class ImportExportManagerImpl implements ImportExportManager {
   protected static final Logger log = LoggerFactory.getLogger(ImportExportManagerImpl.class);
   
   private BusinessProcessDAO businessProcessDAO;
   private ReferenceDAO referenceDAO;

   public void setBusinessProcessDAO(BusinessProcessDAO businessProcessDAO) {
      this.businessProcessDAO = businessProcessDAO;
   }

   public void setReferenceDAO(ReferenceDAO referenceDAO) {
      this.referenceDAO = referenceDAO;
   }

   /**
    * Import using jackson. This first version of import/export preserves IDs between databases -
    * if your source database stores a calendar called Jerry as ID 1 then import will store it
    * as ID 1 in the dest DB. 
    * For this reason:
    *  + you can either maintain a DB using admin webapp OR import to it NOT BOTH
    *  + if you plan to import to a DB then you should clear it first if you have previously 
    *    maintained it using admin webapp   
    * See also AssignedHiLoIdGenerator
    */
   public String importWorkflows(String str) throws Exception {
      XmlMapper mapper = new XmlMapper();
      mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      configurePolymorphism(mapper);
      SlaExport slax = mapper.readValue(str, SlaExport.class);
      String message = "SLA import successfull: ";
      
      Set<Reference> refs = new HashSet<Reference>();
      if (slax.bps != null) {
         for (BusinessProcess bp : slax.bps) {
            mergeReference(bp.getName(), refs);
            mergeReference(bp.getType(), refs);
            if (bp.getTxn() != null) {
               mergeReference(bp.getTxn(), refs);
            }
            businessProcessDAO.merge(bp);
         }
         message += (slax.bps.size() + " business processes, ");
      }
      
      Set<Reference> cals = new HashSet<Reference>();
      if (slax.slas != null) {
         Set<ServiceLevelAgreement> slas = new HashSet<ServiceLevelAgreement>(slax.slas);
         for (ServiceLevelAgreement sla : slas) {
            cals.add(sla.getCalendar());
            saveCalendar(sla.getCalendar());
            businessProcessDAO.merge(sla);
            
            // re-connect SLA & BP and save
            for (BusinessProcess bp : sla.getBusinessProcesses()) {
               bp.setServiceLevelAgreement(sla);
               businessProcessDAO.merge(bp);
            }
         }
         message += (slas.size() + " SLAs, " + cals.size() + " calendars, ");
      }

      if (slax.refs != null) {
         for (Reference ref : slax.refs) {
            businessProcessDAO.merge(ref);
         }
         message += ((refs.size() + cals.size() + slax.refs.size()) + " references ");
      }
      
      // TODO: report on inserts and updates separately
      return message + "were inserted/updated";
   }

   private void mergeReference(Reference ref, Set<Reference> refs) {
      businessProcessDAO.merge(ref);
      refs.add(ref);
   }

   private void saveCalendar(Reference cal) {
      // re-connect Calendar & DM
      if (cal.getDefaultWeekDays() == null) {
         throw new NullPointerException("Calendar has no working days: "+cal.getItemName());
      }
      for (DayModel dm : cal.getDefaultWeekDays()) {
         dm.setRegion(cal);
         // ... and whr
         if (dm.getWorkHourRanges() != null) {
            for (WorkHourRange whr : dm.getWorkHourRanges()) {
               whr.setDay(dm);
            }
         }
         else {
            // if we don't do this hibernate complains about unowned orphaned collections
            dm.setWorkHourRanges(Collections.<WorkHourRange> emptySet());
         }
      }
      businessProcessDAO.merge(cal);
      
      // and save
      for (DayModel dm : cal.getDefaultWeekDays()) {
         businessProcessDAO.merge(dm);
         if (dm.getWorkHourRanges() != null) {
            for (WorkHourRange whr : dm.getWorkHourRanges()) {
               businessProcessDAO.merge(whr);
            }
         }
      }
   }

   public String exportWorkflows() throws Exception {
      Set<BusinessProcess> bps = new HashSet<BusinessProcess>(businessProcessDAO.getBusinessProcesses(new BusinessProcess()));
      Set<ServiceLevelAgreement> slas = new HashSet<ServiceLevelAgreement>();
      Set<Reference> exportedRefs = new HashSet<Reference>();
      // lazy load entities
      for (BusinessProcess bp : bps) {
         Reference name = bp.getName();
         Reference type = bp.getType();
         Reference txn = bp.getTxn();
         checkForDays(name, type, txn);
         addToRefs(exportedRefs, name, type, txn);
         ServiceLevelAgreement sla = bp.getServiceLevelAgreement();
         if (sla != null) {
            Set<BusinessProcess> slabps = sla.getBusinessProcesses();
            Reference c = sla.getCalendar();
            addToRefs(exportedRefs, c);
            Set<DayModel> dwds = c.getDefaultWeekDays();
            if (dwds == null || dwds.size() == 0) {
               throw new NullPointerException("Calendar has no working days: "+c.getItemName());
            }
            slas.add(sla);
            log.trace("BP {} slabps {} dwds {}", new Object[] {bp.getName().getItemName(), slabps.size(), dwds.size()});
         }
         else {
            log.trace("BP {}", bp.getName().getItemName());
         }
      }

      // export any References that we haven't exported yet
      Set<Reference> allRefs = new HashSet<Reference>(referenceDAO.getReferences(new Reference()));
      allRefs.removeAll(exportedRefs);
      SlaExport slax = new SlaExport(bps, slas, allRefs);
      
      // exclude some properties from the export because 
      // 1. we are exporting a connected graph of objects and Jackson is not smart enough to handle circular references 
      // 2. some properties exposed from the SLAP data model are redundant
      Map<Class<?>, Set<String>> methodsToIgnore = new HashMap<Class<?>, Set<String>>();
      Set<String> set = new HashSet<String>();
      set.add("businessProcessNames");
      set.add("businessProcessTypes");
      set.add("calendars");
      methodsToIgnore.put(Reference.class, set);
      methodsToIgnore.put(ServiceLevelAgreement.class, set);
      set = new HashSet<String>();
      set.add("serviceLevelAgreement");
      methodsToIgnore.put(BusinessProcess.class, set);
      set = new HashSet<String>();
      set.add("region");
      set.add("fromTime");
      set.add("toTime");
      set.add("workHourRange");
      methodsToIgnore.put(DayModel.class, set);
      methodsToIgnore.put(DefaultWeekDay.class, set);
      methodsToIgnore.put(CalendarDate.class, set);
      set = new HashSet<String>();
      set.add("day");
      set.add("fromTime");
      set.add("toTime");
      methodsToIgnore.put(WorkHourRange.class, set);
      
      XmlMapper mapper = new HibernateAwareIgnoringXmlMapper(methodsToIgnore);
      configurePolymorphism(mapper);

      String xml = mapper.writeValueAsString(slax);
      return xml;
   }

   private void addToRefs(Set<Reference> refs, Reference... add) {
      for (Reference r : add) {
         if (r != null) {
            refs.add(r);
         }
      }
   }

   private void checkForDays(Reference... refs) {
      for (Reference r : refs) {
         if (r != null) {
            Set<DayModel> dwds = r.getDefaultWeekDays();
            if (dwds != null && dwds.size() > 0) {
               /*
                * I don't know why this happened in one of the VN SLAP DBs
                * To fix:
delete workhourrange where DAYID in (select id from defaultday where regionid=$ID) 
delete defaultweekday where DEFAULTWEEKDAYID in (select id from defaultday where regionid=$ID) 
delete defaultday where regionid=$ID             
                */
               throw new RuntimeException("A Reference that is not a Calendar has working days: "+r.getItemName()+" (id "+r.getId()+")");
            }
         }
      }
   }

   /**
    * Configure Jackson to store polymorphic information for DayModel and its descendants so
    * we can deserialize them as the correct classes
    */
   private void configurePolymorphism(XmlMapper mapper) {
      TypeResolverBuilder<?> typer = new ClassTypeResolverBuilder(DayModel.class);
      // config & initialse
      typer = typer.init(JsonTypeInfo.Id.CLASS, null);
      typer = typer.inclusion(JsonTypeInfo.As.PROPERTY);
      // store the class of the object in a named property
      typer = typer.typeProperty("class");
      mapper.setDefaultTyping(typer);
   }

   /**
    * Configure Jackson to only write polymorphic information for descendants of clazz
    * See also doco of the superclass.
    * @author jerryshea
    */
   public static class ClassTypeResolverBuilder extends ObjectMapper.DefaultTypeResolverBuilder {
      private final Class<?> clazz;

      public ClassTypeResolverBuilder(Class<?> clazz) {
         super(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
         this.clazz = clazz;
      }

      @Override
      public boolean useForType(JavaType t) {
         boolean iaf = clazz.isAssignableFrom(t.getRawClass());
         return iaf;
      }
   }
}
