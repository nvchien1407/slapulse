//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.renewtek.Constants;
import com.renewtek.service.LookupManager;

/**
 * StartupListener class used to initialize and database settings
 * and populate any application-wide drop-downs.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *
 * @web.listener
 */
public class StartupListener extends ContextLoaderListener implements ServletContextListener {

   private static final Logger log = LoggerFactory.getLogger(StartupListener.class);

   public void contextInitialized(ServletContextEvent event) {
      if (log.isDebugEnabled()) {
         log.debug("initializing context...");
      }

      // call Spring's context ContextLoaderListener to initialize
      // all the context files specified in web.xml
      super.contextInitialized(event);

      ServletContext context = event.getServletContext();
      String daoType = context.getInitParameter(Constants.DAO_TYPE);

      // if daoType is not specified, use DAO as default
      if (daoType == null) {
         log.warn("No 'daoType' context carameter, using hibernate");
         daoType = Constants.DAO_TYPE_HIBERNATE;
      }

      // Orion starts Servlets before Listeners, so check if the config
      // object already exists
      @SuppressWarnings("unchecked")
      Map<String, Object> config = (HashMap<String, Object>) context.getAttribute(Constants.CONFIG);

      if (config == null) {
         config = new HashMap<String, Object>();
      }

      // Create a config object to hold all the app config values
      config.put(Constants.DAO_TYPE, daoType);
      context.setAttribute(Constants.CONFIG, config);

      // output the retrieved values for the Init and Context Parameters
      if (log.isDebugEnabled()) {
         log.debug("daoType: " + daoType);
         log.debug("populating drop-downs...");
      }

      setupContext(context);
   }

   public static void setupContext(ServletContext context) {
      ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);

      LookupManager mgr = (LookupManager) ctx.getBean("lookupManager");

      // get list of possible roles
      context.setAttribute(Constants.AVAILABLE_ROLES, mgr.getAllRoles());

      if (log.isDebugEnabled()) {
         log.debug("drop-down initialization complete [OK]");
      }
   }
}
