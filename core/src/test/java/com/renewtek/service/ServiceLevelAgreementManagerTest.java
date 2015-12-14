//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service;

import java.util.List;
import java.util.ArrayList;

import com.renewtek.dao.ServiceLevelAgreementDAO;
import com.renewtek.model.ServiceLevelAgreement;
import com.renewtek.service.impl.ServiceLevelAgreementManagerImpl;

import org.jmock.Mock;
import org.springframework.orm.ObjectRetrievalFailureException;

public class ServiceLevelAgreementManagerTest extends BaseManagerTestCase {
    private final String serviceLevelAgreementId = "1";
    private ServiceLevelAgreementManager serviceLevelAgreementManager = new ServiceLevelAgreementManagerImpl();
    private Mock serviceLevelAgreementDAO = null;
    private ServiceLevelAgreement serviceLevelAgreement = null;

    protected void setUp() throws Exception {
        super.setUp();
        serviceLevelAgreementDAO = new Mock(ServiceLevelAgreementDAO.class);
        serviceLevelAgreementManager.setServiceLevelAgreementDAO((ServiceLevelAgreementDAO) serviceLevelAgreementDAO.proxy());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        serviceLevelAgreementManager = null;
    }

    public void testGetServiceLevelAgreements() throws Exception {
        List<ServiceLevelAgreement> results = new ArrayList<ServiceLevelAgreement>();
        serviceLevelAgreement = new ServiceLevelAgreement();
        results.add(serviceLevelAgreement);

        // set expected behavior on dao
        serviceLevelAgreementDAO.expects(once()).method("getServiceLevelAgreements")
                .will(returnValue(results));

        List<ServiceLevelAgreement> serviceLevelAgreements = serviceLevelAgreementManager.getServiceLevelAgreements(null);
        assertTrue(serviceLevelAgreements.size() == 1);
        serviceLevelAgreementDAO.verify();
    }

    public void testGetServiceLevelAgreement() throws Exception {
        // set expected behavior on dao
        serviceLevelAgreementDAO.expects(once()).method("getServiceLevelAgreement")
                .will(returnValue(new ServiceLevelAgreement()));
        serviceLevelAgreement = serviceLevelAgreementManager.getServiceLevelAgreement(serviceLevelAgreementId);
        assertTrue(serviceLevelAgreement != null);
        serviceLevelAgreementDAO.verify();
    }

    public void testSaveServiceLevelAgreement() throws Exception {
        // set expected behavior on dao
        serviceLevelAgreementDAO.expects(once()).method("saveServiceLevelAgreement")
                .with(same(serviceLevelAgreement)).isVoid();

        serviceLevelAgreementManager.saveServiceLevelAgreement(serviceLevelAgreement);
        serviceLevelAgreementDAO.verify();
    }

    
    
    public void testGetMinutes() throws Exception {
       List<String> minutes = serviceLevelAgreementManager.getMinutes();
       
       assertNotNull(minutes);
    }
    
    public void testRemoveSLA() throws Exception {
       
       serviceLevelAgreementDAO.expects(once()).method("removeServiceLevelAgreement").with(eq(1234)).isVoid();
       
       serviceLevelAgreementManager.removeServiceLevelAgreement("1234");
       
       serviceLevelAgreementDAO.verify();
    }
    
}
