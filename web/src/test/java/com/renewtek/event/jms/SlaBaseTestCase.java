package com.renewtek.event.jms;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class SlaBaseTestCase extends TestCase {

   protected transient final Log log = LogFactory.getLog(getClass());

   protected static ClassPathXmlApplicationContext ctx;

   protected Scheduler scheduler;
   protected MockUpdatePropsWebService updatePropsService;

   // This static block ensures that Spring's BeanFactory is only loaded
   // once for all tests
   static {
      // String pkg = ClassUtils.classPackageAsResourcePath(Constants.class);
      String[] paths = { "classpath:test-applicationContext-resources.xml",
            "classpath:test-applicationContext-hibernate.xml", "classpath:test-applicationContext-service.xml",
            "classpath:test-applicationContext-validation.xml", "classpath:test-applicationContextWS-hibernate.xml" };

      ctx = new ClassPathXmlApplicationContext(paths);
   }

   protected void setUp() throws Exception {
      log.debug("\tSETUP for test class " + this.getClass().getName());
      scheduler = (Scheduler) ctx.getBean("scheduler");
      updatePropsService = (MockUpdatePropsWebService) ctx.getBean("MockUpdatePropsWebService");
      resetTestEnvironment();
   }

   private void resetTestEnvironment() throws SchedulerException {
      updatePropsService.setStatusUpdateCount(0);
      String[] jobGroups = scheduler.getJobGroupNames();
      for (int i = 0; i < jobGroups.length; i++) {
         String[] jobsInGroup = scheduler.getJobNames(jobGroups[i]);
         for (int j = 0; j < jobsInGroup.length; j++) {
            scheduler.deleteJob(jobsInGroup[j], jobGroups[i]);
         }
      }
   }

}
