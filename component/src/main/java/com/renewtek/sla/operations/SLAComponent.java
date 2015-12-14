// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.sla.operations;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.renewtek.service.SLACalendar;
import com.renewtek.service.SLAServices;

public class SLAComponent implements SLAServices {

   private final SLAServices service;

   protected final Logger log = LoggerFactory.getLogger(getClass());

   public SLAComponent() {
      ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] {
            "classpath:applicationContext-resources.xml", "classpath:applicationContext-hibernate.xml",
            "classpath:applicationContext-service.xml" });

      service = (SLAServices) ctx.getBean("slaServices");
   }

   public Date getSLAStartTime(String processName, String processType, Date startTime) {
      return service.getSLAStartTime(processName, processType, startTime);
   }

   public Date getSLADeadline(String processName, String processType, Date startTime, long sleepTime) {
      return service.getSLADeadline(processName, processType, startTime, sleepTime);
   }

   public boolean getSLAStopWhenPaused(String processName, String processType) {
      return service.getSLAStopWhenPaused(processName, processType);
   }

   public Date getSLAReminderTime(String processName, String processType, Date startTime, Date deadline) {
      return service.getSLAReminderTime(processName, processType, startTime, deadline);
   }

   public float getSLAAccumSleepTime(String processName, String processType, Date fromDate, Date toDate,
         float accumSleepTime) {
      return service.getSLAAccumSleepTime(processName, processType, fromDate, toDate, accumSleepTime);
   }

   public String getSLAStatus(String processName, String processType, Date pointInTime, Date deadline, Date reminderTime) {
      return service.getSLAStatus(processName, processType, pointInTime, deadline, reminderTime);
   }

   public boolean getSLANotifyDeadlineApproaching(String processName, String processType) {
      return service.getSLANotifyDeadlineApproaching(processName, processType);
   }

   public SLACalendar getSLACalendar(String processName, String processType) {
      return service.getSLACalendar(processName, processType);
   }

   public SLACalendar getSLACalendar(String calendarName) {
      return service.getSLACalendar(calendarName);
   }

   public Date addDays(String processName, String processType, Date date, int amount) {
      return service.addDays(processName, processType, date, amount);
   }

   public boolean isNonBusinessDay(String processName, String processType, Date date) {
      return service.isNonBusinessDay(processName, processType, date);
   }

   public int[] getWorkingHourRange(String processName, String processType, Date date) {
      return service.getWorkingHourRange(processName, processType, date);
   }

   public Date findBusinessDay(Date startTime, String calendarName) {
      return service.findBusinessDay(startTime, calendarName);
   }

    public Integer isSlaConfiguration(String processName, String processType) {
       return service.isSlaConfiguration(processName, processType);
    }
    
    public Date getSLADeadline(String processName, String processType,
          String transaction, String step, Date slaStartTime, long slaAccumSleepTime) {
       return service.getSLADeadline(processName, processType, transaction, step,
             slaStartTime, slaAccumSleepTime);
    }

    public Date getSLAReminderTime(String processName, String processType,
          String transaction, String step, Date slaStartTime, Date slaDeadline) {
       return service.getSLAReminderTime(processName, processType, transaction, step,
             slaStartTime, slaDeadline);
    }

    public boolean getSLANotifyDeadlineApproaching(String workflowName,
          String processType, String transaction, String step) {
       return service.getSLANotifyDeadlineApproaching(workflowName, processType,
             transaction, step);
    }

    public Integer isSlaConfiguration(String processName, String processType,
          String transaction, String step) {
       return service.isSlaConfiguration(processName, processType, transaction, step);
    }
}
