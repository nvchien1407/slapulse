//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.sla;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.renewtek.service.SLAServices;
import com.renewtek.service.impl.SLAServicesImpl;

public class SLAServicesTest {
    private static SLAServices service = null;
    
    @BeforeClass
    public static void setUp() throws Exception {
        //super.setUp();
        service = new SLAServicesImpl();
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        //super.tearDown();
        service = null;
    }
    
    @Test
    public void testInit() throws Exception {
        assertTrue(service != null);
    }
}
