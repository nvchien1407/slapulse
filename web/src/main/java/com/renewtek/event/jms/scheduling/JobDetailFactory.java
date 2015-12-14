package com.renewtek.event.jms.scheduling;

import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;

/**
 * 
 * Job Details Factory which will produce JobDetails
 * 
 * @author kietnguyen
 * 
 */
public class JobDetailFactory {

   private Map<String, Object> jobClassMap;

   public Object getJobDetailFactoryBean(final String groupName, final String jobName, final String serviceName, final String methodName,
         Object[] arguments) throws Exception {

      String contextServiceName = String.valueOf(jobClassMap.get(serviceName));

      JobDetail jobDetail = new JobDetail();
      jobDetail.setName(jobName);
      jobDetail.setGroup(groupName);
      JobDataMap jobDataMap = new JobDataMap();
      jobDataMap.put(SlaMethodInvokingQuartzJob.METHOD_NAME, methodName);
      jobDataMap.put(SlaMethodInvokingQuartzJob.SERVICE_NAME, contextServiceName);
      jobDataMap.put(SlaMethodInvokingQuartzJob.METHOD_ARGUMENTS, arguments);
      jobDetail.setJobDataMap(jobDataMap);
      jobDetail.setJobClass(SlaMethodInvokingQuartzJob.class);
      jobDetail.setVolatility(false);

      return jobDetail;
   }

   public Map<String, Object> getJobClassMap() {
      return jobClassMap;
   }

   public void setJobClassMap(Map<String, Object> jobClassMap) {
      this.jobClassMap = jobClassMap;
   }

}
