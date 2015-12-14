// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

/*
 * Created on 7/11/2005
 * 
 * @todo To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.renewtek.sla.operations;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.renewtek.Constants;
import com.renewtek.service.SLAServices;

public class SLAOperations {

   protected static final String msClassName = "SLAOperations";

   protected static Logger logger = LoggerFactory.getLogger("com.renewtek.sla.operations.SLAOperations");

   private SLAServices services = null;

   public SLAOperations() {
      services = new SLAComponent();
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.renewtek.sla.operations.SLAServices#getSLAStartTime(java.lang.String,
    * java.lang.String, java.util.Date)
    */
   public Date getSLAStartTime(String processName, String processType, Date startTime) {
      String lsMethodName = "getSLAStartTime";
      logger.debug(msClassName + lsMethodName + " Calling services.getSLAStartTime");
      logger.debug(msClassName + lsMethodName + " processName: " + processName + " processType: " + processType
            + " startTime: " + startTime);

      // Check for null date and FileNet's default date
      SimpleDateFormat dateParser = new SimpleDateFormat("yyyy");
      if (null == startTime || dateParser.format(startTime).equals(Constants.DEFAULT_FILENET_YEAR)) {
         startTime = new Date();
      }

      Date retVal = services.getSLAStartTime(processName, processType, startTime);

      logger.debug(msClassName + lsMethodName + " returnValue: " + retVal);

      return retVal;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.renewtek.sla.operations.SLAServices#getSLADeadline(java.lang.String,
    * java.lang.String, java.util.Date, long)
    */
   public Date getSLADeadline(String processName, String processType, Date startTime, long sleepTime) {
      String lsMethodName = "getSLADeadline";

      logger.debug(msClassName + lsMethodName + " Calling services.getSLADeadLine");
      logger.debug(msClassName + lsMethodName + " processName: " + processName + " processType: " + processType
            + " startTime: " + startTime + " sleepTime: " + sleepTime);

      Date retVal = services.getSLADeadline(processName, processType, startTime, sleepTime);

      logger.debug(msClassName + lsMethodName + " returnValue: " + retVal);

      return retVal;
   }

   public Date getSLADeadline(String processName, String processType, Date startTime) {
      return this.getSLADeadline(processName, processType, startTime, 0);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.renewtek.sla.operations.SLAServices#getSLAReminderTime(java.lang.String
    * , java.lang.String, java.util.Date)
    */
   public Date getSLAReminderTime(String processName, String processType, Date startTime, Date deadline) {
      String lsMethodName = "getSLAReminderTime";

      logger.debug(msClassName + lsMethodName + " Calling services.getSLAReminderTime");
      logger.debug(msClassName + lsMethodName + " processName: " + processName + " processType: " + processType
            + " startTime: " + startTime + " deadLine: " + deadline);

      Date retVal = services.getSLAReminderTime(processName, processType, startTime, deadline);

      logger.debug(msClassName + lsMethodName + " returnValue: " + retVal);
      return retVal;
   }

   public Date getSLAReminderTime(String processName,String processType,Date deadline) {
      return this.getSLAReminderTime(processName, processType, null, deadline); 
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.renewtek.sla.operations.SLAServices#getSLAAccumSleepTime(java.lang
    * .String, java.lang.String, java.util.Date, java.util.Date, float)
    */
   public float getSLAAccumSleepTime(String processName, String processType, Date fromDate, Date toDate,
         float accumSleepTime) {
      String lsMethodName = "getSLAAccumSleepTime";
      logger.debug(msClassName + lsMethodName + " Calling services.getSLAAccumSleepTime");
      logger.debug(msClassName + lsMethodName + " processName: " + processName + " processType: " + processType
            + " fromDate: " + fromDate + " toDate: " + toDate + " accumSleepTime: " + accumSleepTime);

      float retVal = services.getSLAAccumSleepTime(processName, processType, fromDate, toDate, accumSleepTime);

      logger.debug(msClassName + lsMethodName + "returnValue: " + retVal);
      return retVal;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.renewtek.sla.operations.SLAServices#getSLAStopWhenPaused(java.lang
    * .String, java.lang.String)
    */
   public boolean getSLAStopWhenPaused(String processName, String processType) {
      String lsMethodName = "getSLAStopWhenPaused";
      logger.debug(msClassName + lsMethodName + " Calling services.getSLAStopWhenPaused");
      logger.debug(msClassName + lsMethodName + " processName: " + processName + " processType: " + processType);

      boolean retVal = services.getSLAStopWhenPaused(processName, processType);

      logger.debug(msClassName + lsMethodName + " returnValue: " + retVal);
      return retVal;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.renewtek.sla.operations.SLAServices#getSLAStatus(java.lang.String,
    * java.lang.String)
    */
   public String getSLAStatus(String processName, String processType, Date pointInTime, Date deadline, Date reminderTime) {
      String lsMethodName = "getSLAStatus";
      logger.debug(msClassName + lsMethodName + " Calling services.getSLAStatus");
      logger.debug(msClassName + lsMethodName + "processName: " + processName + " processType: " + processType
            + " pointInTime: " + pointInTime + " reminderTime: " + reminderTime);

      String retVal = services.getSLAStatus(processName, processType, pointInTime, deadline, reminderTime);

      logger.debug(msClassName + lsMethodName + " returnValue: " + retVal);

      return retVal;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.renewtek.sla.operations.SLAServices#getSLANotifyDeadlineApproaching
    * (java.lang.String, java.lang.String)
    */
   public boolean getSLANotifyDeadlineApproaching(String processName, String processType) {
      String lsMethodName = "getSLAStatus";
      logger.debug(msClassName + lsMethodName + " Calling services.getSLANotifyDeadlineApproaching");
      logger.debug(msClassName + lsMethodName + " processName: " + processName + " processType: " + processType);

      boolean retVal = services.getSLANotifyDeadlineApproaching(processName, processType);

      logger.debug(msClassName + lsMethodName + " returnValue: " + retVal);

      return retVal;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.renewtek.sla.operations.SLAServices#addDays(java.lang.String,
    * java.lang.String, java.util.Date, int)
    */
   public Date addDays(String processName, String processType, Date date, int amount) {
      String lsMethodName = "addDays";
      logger.debug(msClassName + lsMethodName + " Calling services.addDays");
      logger.debug(msClassName + lsMethodName + " processName: " + processName + " processType: " + processType
            + " amount: " + amount);

      Date retVal = services.addDays(processName, processType, date, amount);

      logger.debug(msClassName + lsMethodName + " returnValue: " + retVal);
      return retVal;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.renewtek.sla.operations.SLAServices#isNonBusinessDay(java.lang.String,
    * java.lang.String, java.util.Date)
    */
   public boolean isNonBusinessDay(String processName, String processType, Date date) {
      String lsMethodName = "isNonBusinessDay";
      logger.debug(msClassName + lsMethodName + " Calling services.isNonBusinessDay");
      logger.debug(msClassName + lsMethodName + " processName: " + processName + " processType: " + processType
            + " date: " + date);

      boolean retVal = services.isNonBusinessDay(processName, processType, date);

      logger.debug(msClassName + lsMethodName + " returnValue: " + retVal);
      return retVal;
   }

   public Date findBusinessDay(Date date, String calendarName) {
      String lsMethodName = "findBusinessDay";
      logger.debug(msClassName + lsMethodName + " Calling services.findBusinessDay");
      logger.debug(msClassName + lsMethodName + " Reference: " + calendarName + " date: " + date);

      return services.findBusinessDay(date, calendarName);
   }
}
