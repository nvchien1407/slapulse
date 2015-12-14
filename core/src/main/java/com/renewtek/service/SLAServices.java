// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service;

import java.util.Date;


public interface SLAServices {

   public Date getSLAStartTime(String processName, String processType, Date startTime);

   public Date getSLADeadline(String processName, String processType, Date startTime, long sleepTime);

   public Date getSLADeadline(String processName, String processType, String transaction, String step, Date slaStartTime, long slaAccumSleepTime);

   public Date getSLAReminderTime(String processName, String processType, Date startTime, Date deadline);

   public Date getSLAReminderTime(String processName, String processType, String transaction, String step, Date slaStartTime, Date slaDeadline);

   public float getSLAAccumSleepTime(String processName, String processType, Date fromDate, Date toDate,
         float accumSleepTime);

   public String getSLAStatus(String processName, String processType, Date pointInTime, Date deadline, Date reminderTime);

   public boolean getSLAStopWhenPaused(String processName, String processType);

   public boolean getSLANotifyDeadlineApproaching(String processName, String processType);

   public boolean getSLANotifyDeadlineApproaching(String workflowName, String processType, String transaction, String step);

   public SLACalendar getSLACalendar(String processName, String processType);

   public SLACalendar getSLACalendar(String calendarName);

   public Date addDays(String processName, String processType, Date date, int amount);

   public boolean isNonBusinessDay(String processName, String processType, Date date);

   public int[] getWorkingHourRange(String processName, String processType, Date date);

   public Date findBusinessDay(Date startTime, String calendarName);
   
   public Integer isSlaConfiguration(String processName, String processType);

   public Integer isSlaConfiguration(String processName, String processType, String transaction, String step);
}
