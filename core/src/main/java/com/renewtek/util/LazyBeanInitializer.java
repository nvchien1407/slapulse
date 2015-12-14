package com.renewtek.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/**
 * This class is designed to allow its referenced beans to remain uncreated if not enabled.
 * If enabled property is true then the beans referred to in lazyLoadingBeanNames will be fetched
 * from the applicationContext, thus forcing their creation and initialisation.
 * If enabled property is false, then this won't happen, and assuming these beans are marked as
 * lazy-init="true", they will not be created and initialised.
 * This is use if, for example, you want to be able to turn on/off something heavy like JMS
 */
public class LazyBeanInitializer implements ApplicationContextAware, InitializingBean {
   private static final Log LOGGER = LogFactory.getLog(LazyBeanInitializer.class);
   private ApplicationContext applicationContext;
   private List<String> lazyLoadingBeanNames;
   private boolean enabled = false;

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   protected boolean isEnabled() {
      return enabled;
   }

   public void setLazyLoadingBeanNames(List<String> lazyLoadingBeanNames) {
      this.lazyLoadingBeanNames = lazyLoadingBeanNames;
   }

   public void setApplicationContext(ApplicationContext applicationContext) {
      this.applicationContext = applicationContext;
   }

   protected ApplicationContext getApplicationContext() {
      return applicationContext;
   }

   public void afterPropertiesSet() throws Exception {
      if (enabled) {
         LOGGER.info("Initialize lazy beans starting");
         for (String lazyLoadingBeanName : lazyLoadingBeanNames) {
            applicationContext.getBean(lazyLoadingBeanName);
         }
         LOGGER.info("Initialize lazy beans done");
      }
   }
}
