package com.renewtek.dao.hibernate;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.MultipleHiLoPerTableGenerator;
import org.hibernate.type.Type;

/**
 * This class was written for SLAP & WPE import/export. It ensures that when you try and merge an 
 * entity with an assigned ID, hibernate will use that assigned ID instead of regenerating
 * @author jerryshea
 */
public class AssignedHiLoIdGenerator extends MultipleHiLoPerTableGenerator {

   private String entityName;

   @Override
   public synchronized Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
      // this code taken from org.hibernate.id.Assigned
      Serializable id = session.getEntityPersister( entityName, obj ).getIdentifier( obj, session.getEntityMode() );
      if (id == null) {
         id = super.generate(session, obj);
      }
      return id;
   }

   @Override
   public void configure(Type type, Properties params, Dialect dialect) throws MappingException {
      entityName = params.getProperty(ENTITY_NAME);
      if (entityName==null) {
          throw new MappingException("no entity name");
      }
      // hack params values as keys used in @TableGenerator are different to what is expected by
      // MultipleHiLoPerTableGenerator - see org.hibernate.cfg.AnnotationBinder.buildIdGenerator
      params.put(MultipleHiLoPerTableGenerator.PK_COLUMN_NAME, params.get("pkColumnName"));
      params.put(MultipleHiLoPerTableGenerator.VALUE_COLUMN_NAME, params.get("valueColumnName"));
      params.put(MultipleHiLoPerTableGenerator.PK_VALUE_NAME, params.get("pkColumnValue"));
      String as = (String) params.get("allocationSize");
      int maxLo = Integer.parseInt(as) - 1;
      params.put(MultipleHiLoPerTableGenerator.MAX_LO, Integer.toString(maxLo));
      super.configure(type, params, dialect);
   }
}
