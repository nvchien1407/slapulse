// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao;

import java.util.List;

import com.renewtek.model.BusinessProcess;
import com.renewtek.model.Reference;

import org.springframework.orm.ObjectRetrievalFailureException;

public class BusinessProcessDAOTest extends BaseDAOTestCase {

   private static Integer businessProcessId = new Integer("100");

   private BusinessProcess businessProcess = null;

   private BusinessProcessDAO businessProcessDao = null;

   private ReferenceDAO referenceDAO = null;

   private final String description = "A Test Description";
   
   public BusinessProcessDAOTest() {
      referenceDAO = (ReferenceDAO)ctx.getBean("referenceDAO");
      businessProcessDao = (BusinessProcessDAO)ctx.getBean("businessProcessDAO");
   }

//   public void setBusinessProcessDAO(BusinessProcessDAO dao) {
//      this.dao = dao;
//   }
//
//   public void setReferenceDao(ReferenceDAO referenceDAO) {
//      this.referenceDAO = referenceDAO;
//   }

   private void setUpData() {
      List<Reference> references = referenceDAO.getReferences("BusinessProcessName");
      Reference referenceName = references.get(0);
      // Reference referenceName = new Reference();
      // referenceName.setId(100);

      references = referenceDAO.getReferences("BusinessProcessType");
      Reference referenceType = references.get(0);
      // Reference referenceType = new Reference();
      // referenceType.setId(110);

      businessProcess = new BusinessProcess();

      // set required fields

      // String description =
      // "YaJlMhJvFcAdLrWcRlTyFpJnDxTjWvNxVyFzUgPwPvVdUsHbVoWyTcYsUnFmQoCyElOuFiSkWiOgTdOjTeKwKoGcUdVeLeRgJdMvAfYrDuJbTiKwCxOvVfZvNmJaGlZyWjDfKgDvFiPrUxNeEfFbIzUfApLjWwPjFlSkXvClGcKfKuIiZlFhUxSjNhXmXyFkQrSkDmClCpMsZsAnGiPvJmWeDmByLqQuOfKcTqOwZlDwLsXdRvFhPmHdScPgPjI";
      businessProcess.setDescription(description);

      businessProcess.setName(referenceName);
      businessProcess.setType(referenceType);
      businessProcess.setEmailNotification(Boolean.FALSE);
      
      businessProcessDao.saveBusinessProcess(businessProcess);

      businessProcessId = businessProcess.getId();
   }

   public void testAddBusinessProcess() throws Exception {
      this.setUpData();

      // verify a primary key was assigned
      assertNotNull(businessProcess.getId());

      // verify set fields are same after save
      assertEquals(description, businessProcess.getDescription());
   }

   public void testGetBusinessProcess() throws Exception {
      this.setUpData();

      businessProcess = businessProcessDao.getBusinessProcess(businessProcessId);
      assertNotNull(businessProcess);
   }

   public void testGetBusinessProcesss() throws Exception {
      businessProcess = new BusinessProcess();

      List<BusinessProcess> results = businessProcessDao.getBusinessProcesses(businessProcess);
      assertTrue(results.size() > 0);
   }

   public void testSaveBusinessProcess() throws Exception {
      this.setUpData();
      businessProcess = businessProcessDao.getBusinessProcess(businessProcessId);

      // update required fields
      // String description =
      // "BySnYwIdFwBpUxEaHrVsKxWdCwYeSnWcAbKiEiOaXiZpVuBxYbNoAcCxOrCyUoNbMjNuSrIiAkFbFeDsEiVjPiLjScHrDqAsExTdVxLbLgSgEjToXwYoQfYeOzVtQcWqWpEkVbIxVeMbAqVcKmMeQcFdEdLpAmUaWyGtWiDrIyPmAiVhSoPdCwSrJwOoDtSqSgCjKcArEgOnSgInMsJkZsHhRnYoJwMcZnFdLxWqWpAdKcQzCoPqBeTpFmJlDgQ";
      String description = "Another Test Description";
      businessProcess.setDescription(description);

      businessProcessDao.saveBusinessProcess(businessProcess);

      assertEquals(description, businessProcess.getDescription());
   }
   
   public void testFindUnassignedBusinessProcesses() {
      List<BusinessProcess> result = businessProcessDao.getUnassignedBusinessProcesses();
      assertEquals(3, result.size());
   }
   
   public void testFindBusinessProcessByNameAndType() {
      List<BusinessProcess> result = businessProcessDao.getBusinessProcess("NewBusiness", "Motor", null, null);
      assertEquals(4, result.size());
   }
   
   public void testFindBusinessProcessByRefereceId() {
      int bizNameId = 3;
      int bizTypeId = 4;
      List<BusinessProcess> result = businessProcessDao.getBusinessProcessById(bizNameId, bizTypeId);
      assertEquals(4, result.size());
   }
   
   public void testFindAllBusinessProcess() {
      List<BusinessProcess> result = businessProcessDao.getBusinessProcesses(null);
      assertEquals(21, result.size());
   }
   
   public void testFindBusinessProcessByNameAlias() {
      Reference reference = new Reference();
      reference.setItemName("NewBusiness");
      BusinessProcess businessProcess = new BusinessProcess();
      businessProcess.setName(reference);
      List<BusinessProcess> result = businessProcessDao.getBusinessProcesses(businessProcess);
      assertEquals(6, result.size());
   }
   
   public void testFindBusinessProcessByTypeAlias() {
      Reference reference = new Reference();
      reference.setItemName("Motor");
      BusinessProcess businessProcess = new BusinessProcess();
      businessProcess.setType(reference);
      List<BusinessProcess> result = businessProcessDao.getBusinessProcesses(businessProcess);
      assertEquals(7, result.size());
   }

   public void testRemoveBusinessProcess() throws Exception {
      businessProcessDao.removeBusinessProcess(businessProcessId);
      try {
         businessProcessDao.getBusinessProcess(businessProcessId);
         fail("businessProcess found in database");
      }
      catch (ObjectRetrievalFailureException e) {
         assertNotNull(e.getMessage());
      }
   }
}
