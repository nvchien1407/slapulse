package com.renewtek.sla;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

public class SLAOperationBackwardCompatibilityTest extends SLABaseTest {

   private String processName = "ForwardReminder";
   private String processTypeTimeBased24Hours = "Time Based 24 Hours";
   private String processTypeDayBasedEOB = "Day Based EOB";
   private String processTypeFixedDeadline = "Fixed Deadline";
   private String processTypeTimeBasedWorkingHours = "Time Based Working Hours";

   /**
    * Time Based 24 Hours
    * @throws ParseException 
    */

   @Test
   public void testBackwardCompatibility1() throws ParseException {
      Date startTime = dateParser.parse("21/12/2005 09:05");
      Date deadline = operations.getSLADeadline(processName, processTypeTimeBased24Hours, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processTypeTimeBased24Hours, startTime, deadline);

      // Forward Reminder
      assertEquals("21/12/2005 15:35", dateParser.format(reminderTime));

      // Backward Compatibility: deadline is 22/12/2005 09:00
      reminderTime = operations.getSLAReminderTime(processName, processTypeTimeBased24Hours, deadline);
      assertEquals("21/12/2005 10:30", dateParser.format(reminderTime));
   }

   @Test
   public void testBackwardCompatibility2() throws ParseException {
      Date startTime = dateParser.parse("21/12/2005 17:05");
      Date deadline = operations.getSLADeadline(processName, processTypeTimeBased24Hours, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processTypeTimeBased24Hours, startTime, deadline);

      // Forward Reminder
      assertEquals("22/12/2005 15:30", dateParser.format(reminderTime));

      // Backward Compatibility: deadline is 22/12/2005 17:00
      reminderTime = operations.getSLAReminderTime(processName, processTypeTimeBased24Hours, deadline);
      assertEquals("22/12/2005 10:30", dateParser.format(reminderTime));
   }

   /**
    * Day Based EOB
    */

   @Test
   public void testBackwardCompatibility3() throws ParseException {
      Date startTime = dateParser.parse("22/12/2005 13:00");
      Date deadline = operations.getSLADeadline(processName, processTypeDayBasedEOB, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processTypeDayBasedEOB, startTime, deadline);

      // Forward Reminder
      assertEquals("22/12/2005 13:30", dateParser.format(reminderTime));

      // Backward Compatibility: deadline is 29/12/2005 17:00
      reminderTime = operations.getSLAReminderTime(processName, processTypeDayBasedEOB, deadline);
      assertEquals("29/12/2005 16:30", dateParser.format(reminderTime));
   }

   @Test
   public void testBackwardCompatibility4() throws ParseException {
      Date startTime = dateParser.parse("22/12/2005 18:00");
      Date deadline = operations.getSLADeadline(processName, processTypeDayBasedEOB, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processTypeDayBasedEOB, startTime, deadline);

      // Forward Reminder
      assertEquals("29/12/2005 09:30", dateParser.format(reminderTime));

      // Backward Compatibility: deadline is 30/12/2005 17:00
      reminderTime = operations.getSLAReminderTime(processName, processTypeDayBasedEOB, deadline);
      assertEquals("30/12/2005 16:30", dateParser.format(reminderTime));
   }

   /**
    * Fixed Deadline
    */

   @Test
   public void testBackwardCompatibility5() throws ParseException {
      Date startTime = dateParser.parse("21/12/2005 09:05");
      Date deadline = operations.getSLADeadline(processName, processTypeFixedDeadline, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processTypeFixedDeadline, startTime, deadline);

      // Forward Reminder
      assertEquals("21/12/2005 12:05", dateParser.format(reminderTime));

      // Backward Compatibility: deadline is 21/12/2005 18:30
      reminderTime = operations.getSLAReminderTime(processName, processTypeFixedDeadline, deadline);
      assertEquals("21/12/2005 15:30", dateParser.format(reminderTime));
   }

   @Test
   public void testBackwardCompatibility6() throws ParseException {
      Date startTime = dateParser.parse("21/12/2005 15:31");
      Date deadline = operations.getSLADeadline(processName, processTypeFixedDeadline, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processTypeFixedDeadline, startTime, deadline);

      // Forward Reminder
      assertEquals("22/12/2005 12:00", dateParser.format(reminderTime));

      // Backward Compatibility: deadline is 22/12/2005 18:30
      reminderTime = operations.getSLAReminderTime(processName, processTypeFixedDeadline, deadline);
      assertEquals("22/12/2005 15:30", dateParser.format(reminderTime));
   }

   /**
    * Time Based Working Hours
    */

   @Test
   public void testBackwardCompatibility7() throws ParseException {
      Date startTime = dateParser.parse("21/12/2005 09:05");
      Date deadline = operations.getSLADeadline(processName, processTypeTimeBasedWorkingHours, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processTypeTimeBasedWorkingHours, startTime, deadline);

      // Forward Reminder
      assertEquals("21/12/2005 11:05", dateParser.format(reminderTime));

      // Backward Compatibility: deadline is 21/12/2005 13:35
      reminderTime = operations.getSLAReminderTime(processName, processTypeTimeBasedWorkingHours, deadline);
      assertEquals("21/12/2005 11:35", dateParser.format(reminderTime));
   }

   @Test
   public void testBackwardCompatibility8() throws ParseException {
      Date startTime = dateParser.parse("21/12/2005 17:05");
      Date deadline = operations.getSLADeadline(processName, processTypeTimeBasedWorkingHours, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processTypeTimeBasedWorkingHours, startTime, deadline);
      

      // Forward Reminder
      assertEquals("22/12/2005 11:00", dateParser.format(reminderTime));

      // Backward Compatibility: deadline is 22/12/2005 13:30
      reminderTime = operations.getSLAReminderTime(processName, processTypeTimeBasedWorkingHours, deadline);
      assertEquals("22/12/2005 11:30", dateParser.format(reminderTime));
   }
}
