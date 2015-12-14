// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao;

import java.util.List;

import com.renewtek.model.Reference;
import com.renewtek.model.ServiceLevelAgreement;

import org.springframework.orm.ObjectRetrievalFailureException;

public class ServiceLevelAgreementDAOTest extends BaseDAOTestCase {

   private static Integer serviceLevelAgreementId = null;

   private ServiceLevelAgreement serviceLevelAgreement = null;

   private ServiceLevelAgreementDAO dao = null;
   
   private final String deadlineType = "RuAfEuOyNbOoQgFyEdSjCbDeA";
   
   private final String durationType = "IuAeVjOfNfNoDsZsEcQzKuKnZ";

   private final String name = "GnNjCtJtFrEpEtZpCeIkLmJgG";
   
   private final String workTime = "ZqTbYxEcXdTaJkTpZsHnYsZgM";
   
   private final Boolean stopSlaWhenPaused = false;

   private final Boolean enableCutOffTime = false;

   public ServiceLevelAgreementDAOTest() {
      dao = (ServiceLevelAgreementDAO)ctx.getBean("serviceLevelAgreementDAO");
   }
   
   private void setUpData() {
      Reference reference = new Reference();
      reference.setId(10);
      
      serviceLevelAgreement = new ServiceLevelAgreement();

      // set required fields
      
      serviceLevelAgreement.setDeadlineType(deadlineType);

      serviceLevelAgreement.setDurationType(durationType);

      serviceLevelAgreement.setName(name);

      serviceLevelAgreement.setWorkTime(workTime);

      serviceLevelAgreement.setStopSlaWhenPaused(stopSlaWhenPaused);

      serviceLevelAgreement.setEnableCutOffTime(enableCutOffTime);
      
      serviceLevelAgreement.setCalendar(reference);
      dao.saveServiceLevelAgreement(serviceLevelAgreement);
      
      serviceLevelAgreementId = serviceLevelAgreement.getId();
   }

   public void testAddServiceLevelAgreement() throws Exception {
      this.setUpData();

      // verify a primary key was assigned
      assertNotNull(serviceLevelAgreement.getId());

      // verify set fields are same after save
      assertEquals(deadlineType, serviceLevelAgreement.getDeadlineType());
      assertEquals(durationType, serviceLevelAgreement.getDurationType());
      assertEquals(name, serviceLevelAgreement.getName());
      assertEquals(workTime, serviceLevelAgreement.getWorkTime());
      assertEquals(stopSlaWhenPaused, serviceLevelAgreement.getStopSlaWhenPaused());
   }

   public void testGetServiceLevelAgreement() throws Exception {
      this.setUpData();
      serviceLevelAgreement = dao.getServiceLevelAgreement(serviceLevelAgreementId);
      assertNotNull(serviceLevelAgreement);
   }

   public void testGetServiceLevelAgreements() throws Exception {
      serviceLevelAgreement = new ServiceLevelAgreement();

      List<ServiceLevelAgreement> results = dao.getServiceLevelAgreements(serviceLevelAgreement);
      assertTrue(results.size() > 0);
   }

   public void testSaveServiceLevelAgreement() throws Exception {
      this.setUpData();
      serviceLevelAgreement = dao.getServiceLevelAgreement(serviceLevelAgreementId);

      // update required fields
      String deadlineType = "ApUbZtNnEtAnWiPyInRaCsYfU";
      serviceLevelAgreement.setDeadlineType(deadlineType);
      String durationType = "AiNnSpDlZaLoGuNmLkByXcPjG";
      serviceLevelAgreement.setDurationType(durationType);
      String name = "GaMnAzVcCrRsZvVfPmAbPnXzO";
      serviceLevelAgreement.setName(name);
      String workTime = "KhOqZwYnEtCmZrMrIrOrJzOmM";
      serviceLevelAgreement.setWorkTime(workTime);
      Boolean stopSlaWhenPaused = false;
      serviceLevelAgreement.setStopSlaWhenPaused(stopSlaWhenPaused);

      dao.saveServiceLevelAgreement(serviceLevelAgreement);

      assertEquals(deadlineType, serviceLevelAgreement.getDeadlineType());
      assertEquals(durationType, serviceLevelAgreement.getDurationType());
      assertEquals(name, serviceLevelAgreement.getName());
      assertEquals(workTime, serviceLevelAgreement.getWorkTime());
      assertEquals(stopSlaWhenPaused, serviceLevelAgreement.getStopSlaWhenPaused());
   }

   public void testRemoveServiceLevelAgreement() throws Exception {
      dao.removeServiceLevelAgreement(serviceLevelAgreementId);
      try {
         dao.getServiceLevelAgreement(serviceLevelAgreementId);
         fail("serviceLevelAgreement found in database");
      }
      catch (ObjectRetrievalFailureException e) {
         assertNotNull(e.getMessage());
      }
   }
}
