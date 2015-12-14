// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.renewtek.Constants;
import com.renewtek.model.BusinessProcess;
import com.renewtek.model.Reference;
import com.renewtek.model.ServiceLevelAgreement;
import com.renewtek.service.BusinessProcessManager;
import com.renewtek.service.CalendarManager;
import com.renewtek.service.ReferenceManager;
import com.renewtek.service.SLACalendar;
import com.renewtek.service.SLAServices;

public class SLAServicesImpl implements SLAServices {

   private BusinessProcessManager manager = null;

   private CalendarManager calendarManager = null;

   private ReferenceManager referenceManager = null;

   protected transient Log log = null;

   public SLAServicesImpl() {
      log = LogFactory.getLog(getClass());
      log.info("=========SLA Service initialized===========");
   }

   public Date getSLAStartTime(String processName, String processType, Date startTime) {
      processType = this.getProcessType(processType);
      return manager.getSLAStartTime(processName, processType, startTime);
   }

   public Date getSLADeadline(String processName, String processType, Date startTime, long sleepTime) {
      return getSLADeadline(processName, processType, null, null, startTime, sleepTime);
   }

   public Date getSLADeadline(String processName, String processType, String transaction, String step, Date startTime, long sleepTime) {
      processType = this.getProcessType(processType);
      Date deadLine = new Date();
      deadLine = manager.getSLADeadline(processName, processType, transaction, step, startTime, sleepTime);
      return deadLine;
   }

   public boolean getSLAStopWhenPaused(String processName, String processType) {
      processType = this.getProcessType(processType);
      return manager.getSLAStopWhenPaused(processName, processType);
   }

   public void setBusinessProcessManager(BusinessProcessManager manager) {
      this.manager = manager;
   }

   public void setCalendarManager(CalendarManager calendarManager) {
      this.calendarManager = calendarManager;
   }

   public void setReferenceManager(ReferenceManager referenceManager) {
      this.referenceManager = referenceManager;
   }

   public String getProcessType(String processType) {
      if (processType == null || processType.equals(""))
         processType = Constants.DEFAULT_BUSINESS_PROCESS_TYPE;

      return processType;
   }

   public Date getSLAReminderTime(String processName, String processType, Date startTime, Date deadline) {
      return getSLAReminderTime(processName, processType, null, null, startTime, deadline);
   }

   public Date getSLAReminderTime(String processName, String processType, String transaction, String step, Date startTime, Date deadline) {
      processType = this.getProcessType(processType);
      return manager.getSLAReminderTime(processName, processType, transaction, step, startTime, deadline);
   }

   public float getSLAAccumSleepTime(String processName, String processType, Date fromDate, Date toDate,
         float accumSleepTime) {
      processType = this.getProcessType(processType);
      return manager.getSLAAccumSleepTime(processName, processType, fromDate, toDate, accumSleepTime);
   }

   public String getSLAStatus(String processName, String processType, Date pointInTime, Date deadline, Date reminderTime) {
      processType = this.getProcessType(processType);
      return manager.getSLAStatus(processName, processType, pointInTime, deadline, reminderTime);
   }

   public boolean getSLANotifyDeadlineApproaching(String processName, String processType) {
      return getSLANotifyDeadlineApproaching(processName, processType, null, null);
   }

   public boolean getSLANotifyDeadlineApproaching(String processName, String processType, String transaction, String step) {
      processType = this.getProcessType(processType);
      return manager.getSLANotifyDeadlineApproaching(processName, processType, transaction, step);
   }

   public SLACalendar getSLACalendar(String processName, String processType) {
      BusinessProcess bp = manager.getBusinessProcess(processName, processType);
      ServiceLevelAgreement sla = bp.getServiceLevelAgreement();
      Reference reference = sla.getCalendar();
      return calendarManager.getSLACalendar(reference);
   }

   public SLACalendar getSLACalendar(String calendarName) {
      Reference reference = referenceManager.getReferenceByItemName(calendarName);
      return calendarManager.getSLACalendar(reference);
   }

   public Date addDays(String processName, String processType, Date date, int amount) {
      SLACalendar slaCalendar = getSLACalendar(processName, processType);
      slaCalendar.setTime(date);
      slaCalendar.add(SLACalendar.BUSINESS_DATE, amount);
      return slaCalendar.getTime();
   }

   public boolean isNonBusinessDay(String processName, String processType, Date date) {
      SLACalendar slaCalendar = getSLACalendar(processName, processType);
      slaCalendar.setTime(date);
      return slaCalendar.isNonBusinessDay();
   }

   public int[] getWorkingHourRange(String processName, String processType, Date date) {
      SLACalendar slaCalendar = getSLACalendar(processName, processType);
      slaCalendar.setTime(date);
      return slaCalendar.getWorkingHourRange();
   }

   public Date findBusinessDay(Date startTime, String calendarName) {
      Reference reference = referenceManager.getReferenceByItemName(calendarName);
      SLACalendar slaCalendar = calendarManager.getSLACalendar(reference);
      slaCalendar.setTime(startTime);

      if (slaCalendar.isNonBusinessDay()) {
         return manager.findNextWorkDate(startTime, calendarName);
      }
      else {
         return startTime;
      }
   }
   
   public Integer isSlaConfiguration(String processName, String processType) {
      return isSlaConfiguration(processName, processType, null, null);
   }

   public Integer isSlaConfiguration(String processName, String processType, String transaction, String step) {
      BusinessProcess bp = manager.getBusinessProcess(processName, processType, transaction, step);
      if (bp != null) {
         if (bp.getServiceLevelAgreement() != null) {
            return bp.getServiceLevelAgreement().getId();
         }
      }
      return null;
   }
}
