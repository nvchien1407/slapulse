//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import junit.framework.TestCase;

import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

import com.renewtek.Constants;

public class ActionFilterTest extends TestCase {
    private ActionFilter filter = null;
    private MockFilterConfig config = null;

    @SuppressWarnings("unchecked")
   protected void setUp() throws Exception {
        super.setUp();
        filter = new ActionFilter();

        MockServletContext sc = new MockServletContext("");
        Map appConfig = new HashMap();
        appConfig.put(Constants.HTTP_PORT, "80");
        appConfig.put(Constants.HTTPS_PORT, "443");
        sc.setAttribute(Constants.CONFIG, appConfig);
        
        String pkg = ClassUtils.classPackageAsResourcePath(Constants.class);
        sc.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM,
                "classpath*:/" + pkg + "/dao/applicationContext-*.xml," +
                "classpath*:META-INF/applicationContext-*.xml");
        
        ServletContextListener listener = new ContextLoaderListener();
        ServletContextEvent event = new ServletContextEvent(sc);
        listener.contextInitialized(event);
        
        // set initialization parameters
        config = new MockFilterConfig(sc);
        config.addInitParameter("isSecure", "false");
        filter.init(config);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        filter = null;
    }

    public void testInit() throws Exception {
        assertTrue(config != null);
        assertEquals(config.getInitParameter("isSecure"), "false");
    }
}
