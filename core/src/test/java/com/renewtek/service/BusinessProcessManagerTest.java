//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jmock.Mock;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.renewtek.dao.BusinessProcessDAO;
import com.renewtek.model.BusinessProcess;
import com.renewtek.service.impl.BusinessProcessManagerImpl;

public class BusinessProcessManagerTest extends BaseManagerTestCase {
    private final String businessProcessId = "1";
    private BusinessProcessManager businessProcessManager = new BusinessProcessManagerImpl();
    private Mock businessProcessDAO = null;
    private BusinessProcess businessProcess = null;

    protected void setUp() throws Exception {
        super.setUp();
        businessProcessDAO = new Mock(BusinessProcessDAO.class);
        businessProcessManager.setBusinessProcessDAO((BusinessProcessDAO) businessProcessDAO.proxy());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        businessProcessManager = null;
    }

    public void testGetBusinessProcesses() throws Exception {
        List<BusinessProcess> results = new ArrayList<BusinessProcess>();
        businessProcess = new BusinessProcess();
        results.add(businessProcess);

        // set expected behavior on dao
        businessProcessDAO.expects(once()).method("getBusinessProcesses")
                .will(returnValue(results));

        List<BusinessProcess> businessProcesses = businessProcessManager.getBusinessProcesses(null);
        businessProcessDAO.verify();
        assertTrue(businessProcesses.size() == 1);
    }

    public void testGetBusinessProcess() throws Exception {
        // set expected behavior on dao
        businessProcessDAO.expects(once()).method("getBusinessProcess")
                .will(returnValue(new BusinessProcess()));
        businessProcess = businessProcessManager.getBusinessProcess(businessProcessId);
        businessProcessDAO.verify();
        assertTrue(businessProcess != null);
    }

    public void testSaveBusinessProcess() throws Exception {
        // set expected behavior on dao
        businessProcessDAO.expects(once()).method("saveBusinessProcess")
                .with(same(businessProcess)).isVoid();

        businessProcessManager.saveBusinessProcess(businessProcess);
        businessProcessDAO.verify();
    }
    
    public void testGetBussinessProcessUsingNameAndType() throws Exception {
       List<BusinessProcess> result = null;
       
       businessProcessDAO.expects(once()).method("getBusinessProcess").with(eq("Type1"),eq("ABC"),eq(null),eq(null)).will(returnValue(result));
       
       BusinessProcess businessProcess = businessProcessManager.getBusinessProcess("Type1", "ABC");
       
       businessProcessDAO.verify();
       assertNull(businessProcess);
    }
    
    public void testGetBussinessProcessUsingNameAndType1() throws Exception {
       List<BusinessProcess> result = new ArrayList<BusinessProcess>();
       BusinessProcess process; 
       process = new BusinessProcess();
       process.setId(1000);
       process.setDescription("Description 1");
       result.add(process);
       
       process = new BusinessProcess();
       process.setId(2000);
       process.setDescription("Description 2");
       result.add(process);
       
       businessProcessDAO.expects(once()).method("getBusinessProcess").with(eq("Type1"),eq("ABC"),eq(null),eq(null)).will(returnValue(result));
       
       BusinessProcess businessProcess = businessProcessManager.getBusinessProcess("Type1", "ABC");
       
       businessProcessDAO.verify();
       
       assertEquals(new Integer(1000), businessProcess.getId());
       assertEquals("Description 1", businessProcess.getDescription());
    }
    
    public void testGetUnassignedBusinessProcesses() throws Exception {
       List<BusinessProcess> results = new ArrayList<BusinessProcess>();
       businessProcess = new BusinessProcess();
       results.add(businessProcess);

       // set expected behavior on dao
       businessProcessDAO.expects(once()).method("getUnassignedBusinessProcesses")
               .will(returnValue(results));

       List<BusinessProcess> businessProcesses = businessProcessManager.getUnassignedBusinessProcesses();
       
       businessProcessDAO.verify();
       
       assertTrue(businessProcesses.size() == 1);
    }
    
    public void testGetSLAStartTime() throws Exception {
       Date startTime = new Date();
       Date actual = businessProcessManager.getSLAStartTime("ABC", "ABC", startTime);
       assertNotNull(actual);
       assertEquals(startTime, actual);
    }
    
    public void testGetSLAStartTime2() {
       Date actual = businessProcessManager.getSLAStartTime("ABC", "ABC", null);
       assertNotNull(actual);
    }
    
    public void testGetSLADeadline() {
//       List<BusinessProcess> businessProcesses = new ArrayList<BusinessProcess>();
//       
//       businessProcess  = new BusinessProcess();
//       ServiceLevelAgreement sla = new ServiceLevelAgreement();
//       businessProcess.setServiceLevelAgreement(sla);
//       sla.setDurationType(Constants.T_FORMULA_TYPE);
//       sla.setIncludeSpecialDays(Constants.NO_TYPE);
//       
//       businessProcessManager.
//       
//       
//       businessProcesses.add(businessProcess);
//       
//       businessProcessDAO.expects(once()).method("getBusinessProcess").with(eq("ABC"), eq("ABC")).will(returnValue(businessProcesses));
//       
//       Date actual = businessProcessManager.getSLADeadline("ABC", "ABC", new Date(), 123);
//       
//       businessProcessDAO.verify();
//       
//       assertNotNull(actual);
    }
    
    

    public void testAddAndRemoveBusinessProcess() throws Exception {
        businessProcess = new BusinessProcess();

        // set required fields
        businessProcess.setDescription("GqFfIjObPfHoQaHrDzZsFaTbKjGnGbYhZjSuOxTnGxQyCaEgSyEwDkHiCeYnDvOsAgLhBfThJgPkGtFhPiQpMzYnKfUeZvAnGsDbGoLxSgNiQyNsGuOiAhLzBwFwXoYtDtDjFtPhXsEbRnUeHrMkCnFoCnXoHuTvMiGkZjIyTuBeHnGhKrYhQqEuHiGzXnXsGzRtPwUiWuUtUmCdFmVyQrPhRzYeKlEqXwEtOzJfDcEbYyWuVaMqRgFpMvAjUqK");

        // set expected behavior on dao
        businessProcessDAO.expects(once()).method("saveBusinessProcess")
                .with(same(businessProcess)).isVoid();
        businessProcessManager.saveBusinessProcess(businessProcess);
        businessProcessDAO.verify();

        // reset expectations
        businessProcessDAO.reset();

        businessProcessDAO.expects(once()).method("removeBusinessProcess").with(eq(new Integer(businessProcessId)));
        businessProcessManager.removeBusinessProcess(businessProcessId);
        businessProcessDAO.verify();

        // reset expectations
        businessProcessDAO.reset();
        // remove
        Exception ex = new ObjectRetrievalFailureException(BusinessProcess.class, businessProcess.getId());
        businessProcessDAO.expects(once()).method("removeBusinessProcess").isVoid();
        businessProcessDAO.expects(once()).method("getBusinessProcess").will(throwException(ex));
        businessProcessManager.removeBusinessProcess(businessProcessId);
        try {
            businessProcessManager.getBusinessProcess(businessProcessId);
            fail("BusinessProcess with identifier '" + businessProcessId + "' found in database");
        } catch (ObjectRetrievalFailureException e) {
            assertNotNull(e.getMessage());
        }
        businessProcessDAO.verify();
    }
}
