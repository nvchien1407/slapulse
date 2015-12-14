package com.renewtek.event.jms.scheduling;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Using @see com.renewtek.util.LazyBeanInitializer is not enough to disable JMS
 * as org.springframework.scheduling.quartz.SchedulerFactoryBean implements
 * 
 * @see org.springframework.context.Lifecycle and the spring container still
 *      calls start on it. This class enables the message listener container to
 *      be disabled
 */
public class SlaSchedulerFactoryBean extends SchedulerFactoryBean {
   private boolean enabled = true;
   private ApplicationContext applicationContext;
   private String dataSourceBean;
   private Properties quartzProperties;

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   /**
    * "lazy-init" is not enough to disable dependency injection. We will
    * manually inject dataSource when need.
    * 
    * @param dataSourceBean
    */
   public void setDataSourceBean(String dataSourceBean) {
      this.dataSourceBean = dataSourceBean;
   }

   @Override
   public void start() throws SchedulingException {
      if (enabled) {
         super.start();
      }
   }

   /**
    * Although the parent class has implemented ApplicationContextAware, but it
    * doesn't expose the context object to us. So we need to override this
    * method.
    */
   @Override
   public void setApplicationContext(ApplicationContext applicationContext) {
      super.setApplicationContext(applicationContext);
      this.applicationContext = applicationContext;
   }

   @Override
   public void setQuartzProperties(Properties quartzProperties) {
      this.quartzProperties = quartzProperties;
   }

   @Override
   public void afterPropertiesSet() throws Exception {
      if (enabled) {
         Object dataSource = applicationContext.getBean(dataSourceBean);
         if (dataSource != null) {
            this.setDataSource((DataSource) dataSource);
         }
         super.setQuartzProperties(quartzProperties);
      }
      super.afterPropertiesSet();
   }
}
