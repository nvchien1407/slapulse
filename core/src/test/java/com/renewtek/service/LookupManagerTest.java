//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service;

import java.util.ArrayList;
import java.util.List;

import com.renewtek.dao.LookupDAO;
import com.renewtek.model.LabelValue;
import com.renewtek.model.Role;
import com.renewtek.service.impl.LookupManagerImpl;
import org.jmock.Mock;


public class LookupManagerTest extends BaseManagerTestCase {
    private LookupManager mgr = new LookupManagerImpl();
    private Mock lookupDAO = null;

    protected void setUp() throws Exception {
        super.setUp();
        lookupDAO = new Mock(LookupDAO.class);
        mgr.setLookupDAO((LookupDAO) lookupDAO.proxy());
    }

    public void testGetAllRoles() {
        if (log.isDebugEnabled()) {
            log.debug("entered 'testGetAllRoles' method");
        }

        // set expected behavior on dao
        Role role = new Role("admin");
        List<Role> testData = new ArrayList<Role>();
        testData.add(role);
        lookupDAO.expects(once()).method("getRoles")
                .withNoArguments().will(returnValue(testData));

        List<LabelValue> roles = mgr.getAllRoles();
        assertTrue(roles.size() > 0);
        // verify expectations
        lookupDAO.verify();
    }
}
