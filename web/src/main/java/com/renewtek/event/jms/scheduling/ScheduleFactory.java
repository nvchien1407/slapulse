package com.renewtek.event.jms.scheduling;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerBean;

import com.renewtek.event.jms.SlaMessageListener;

/**
 * User: khanhpham Date: 10/26/11
 */
public class ScheduleFactory {

   private static final Logger log = LoggerFactory.getLogger(SlaMessageListener.class);

   @Autowired
   private Scheduler scheduler;

   public void scheduleJob(CronTriggerBean cronTriggerBean) throws SchedulerException {
      JobDetail jobDetail = cronTriggerBean.getJobDetail();
      scheduler.scheduleJob(jobDetail, cronTriggerBean);
   }

   public void scheduleJob(JobDetail jobDetail, SimpleTrigger simpleTrigger) throws SchedulerException {
      scheduler.scheduleJob(jobDetail, simpleTrigger);
   }

   public void terminateJob(String triggerName, String groupName) throws SchedulerException {
      scheduler.unscheduleJob(triggerName, groupName);
   }

   public Scheduler getScheduler() {
      return scheduler;
   }

   public void setScheduler(Scheduler scheduler) {
      this.scheduler = scheduler;
   }

}
