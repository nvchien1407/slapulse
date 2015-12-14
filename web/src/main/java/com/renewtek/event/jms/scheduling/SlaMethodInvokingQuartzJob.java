package com.renewtek.event.jms.scheduling;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.renewtek.event.jms.IUpdatePropertiesService;
import com.renewtek.event.jms.SlaEventHandler;

public class SlaMethodInvokingQuartzJob implements Job {

   public static final String SERVICE_NAME = "SERVICE_NAME";
   public static final String METHOD_NAME = "METHOD_NAME";
   public static final String METHOD_ARGUMENTS = "METHOD_ARGUMENTS";
   public static final String APPLICATION_CONTEXT = "applicationContext";

   public void execute(JobExecutionContext context) throws JobExecutionException {
      JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
      String serviceName = String.valueOf(jobDataMap.get(SERVICE_NAME));
      String methodName = String.valueOf(jobDataMap.get(METHOD_NAME));
      Object [] arguments = (Object[]) jobDataMap.get(METHOD_ARGUMENTS);

      Scheduler scheduler = context.getScheduler();

      ApplicationContext applicationContext;
      try {
         applicationContext = (ApplicationContext) scheduler.getContext().get(APPLICATION_CONTEXT);
      } catch (SchedulerException e) {
         throw new IllegalStateException("No applicationContext has been set in scheduler.");
      }

      IUpdatePropertiesService service = (IUpdatePropertiesService) applicationContext.getBean(serviceName);
      if (methodName != null && methodName.equals(SlaEventHandler.METHOD_UPDATE_SLA_STATUS_IN_TIME)) {
         service.updateSlaStatusInSpecifyTime((Long)arguments[0], String.valueOf(arguments[1]), (Date)arguments[2], (Date) arguments[3], false, (Integer)arguments[4]);
      }
   }

}
