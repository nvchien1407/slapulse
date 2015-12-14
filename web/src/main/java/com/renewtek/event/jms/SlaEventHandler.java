package com.renewtek.event.jms;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.renewtek.event.jms.scheduling.JobDetailFactory;
import com.renewtek.event.jms.scheduling.ScheduleFactory;
import com.renewtek.event.jms.scheduling.SimpleTriggerFactory;
import com.renewtek.service.SLAServices;

/**
 * SLA Event Handler to define the Scheduler Services and SLA Services
 * 
 * @author kietnguyen
 * 
 */
public abstract class SlaEventHandler implements IEventHandler {
   public static final String METHOD_UPDATE_SLA_STATUS_IN_TIME = "updateSlaStatusInSpecifyTime";

   protected SLAServices slaService;
   protected IUpdatePropertiesService updatePropsService;
   protected ScheduleFactory scheduleFactory;
   protected JobDetailFactory jobDetailFactory;

   public static final String PROCESS_TYPE = "Workflow Event";
   public static final String GROUP_NAME_EXPIRE = "EXPIRE";
   public static final String GROUP_NAME_REMIDER = "REMIDER";
   public static final String SEPARATOR = "_";
   public static final String CASE_SLA_STEP_NULL = null;

   private static final Logger log = LoggerFactory.getLogger(SlaEventHandler.class);

   /**
    * Function to check whether the workflow is configured in SLA DB
    * 
    * @param processName
    *           the process name definition
    * @return null if not configured, otherwise the sla Id
    */
   protected Integer isSlaConfigured(String processName, String transaction) {
      return slaService.isSlaConfiguration(processName, PROCESS_TYPE, transaction, CASE_SLA_STEP_NULL);
   }

   /**
    * Trigger a job details
    * 
    * @param arguments
    *           list of objects which was passed to method
    * @param serviceName
    *           name of the service
    * @param methodName
    *           name of the method of the service
    * @param startDate
    *           Start time of job
    * @param endDate
    *           End time of job
    * @throws Exception
    */

   public void triggerJobDetails(String groupName, String jobName, String serviceName, String methodName,
         Object arguments[], Date startDate, Date endDate) throws Exception {
      log.info("Start Trigger a Job: " + jobName + " " + groupName);

      JobDetail jobDetails = (JobDetail) jobDetailFactory.getJobDetailFactoryBean(groupName, jobName, serviceName, methodName,
            arguments);

      SimpleTrigger triggerUpdate = SimpleTriggerFactory.createTrigger(startDate, endDate,
            groupName, jobName);

      scheduleFactory.scheduleJob(jobDetails, triggerUpdate);
   }

   public ScheduleFactory getScheduleFactory() {
      return scheduleFactory;
   }

   public void setScheduleFactory(ScheduleFactory scheduleFactory) {
      this.scheduleFactory = scheduleFactory;
   }

   public SLAServices getSlaService() {
      return slaService;
   }

   public void setSlaService(SLAServices slaService) {
      this.slaService = slaService;
   }

   public IUpdatePropertiesService getUpdatePropsService() {
      return updatePropsService;
   }

   public void setUpdatePropsService(IUpdatePropertiesService updatePropsService) {
      this.updatePropsService = updatePropsService;
   }

   public JobDetailFactory getJobDetailFactory() {
      return jobDetailFactory;
   }

   public void setJobDetailFactory(JobDetailFactory jobDetailFactory) {
      this.jobDetailFactory = jobDetailFactory;
   }
}
