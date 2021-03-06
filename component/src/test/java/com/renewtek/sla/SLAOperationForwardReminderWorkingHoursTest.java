package com.renewtek.sla;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

public class SLAOperationForwardReminderWorkingHoursTest extends SLABaseTest {

   private String processName = "ForwardReminder";
   private String spanningProcessName = "ForwardReminder Spanning";
   private String processType = "Time Based Working Hours";

   /**
    * NON-SPANNING TEST CASES
    */

   /**
    * Call drops in on Working Day and within working hours
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder1() throws ParseException {
      Date startTime = dateParser.parse("21/12/2005 09:05");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("21/12/2005 11:05", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working Day and nearly end of working hours
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder2() throws ParseException {
      Date startTime = dateParser.parse("21/12/2005 16:05");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("22/12/2005 10:05", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working Day and out of working hours
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder3() throws ParseException {
      Date startTime = dateParser.parse("21/12/2005 17:05");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("22/12/2005 11:00", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special Day and within working hours
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder4() throws ParseException {
      Date startTime = dateParser.parse("23/12/2005 09:05");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("23/12/2005 11:05", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special Day and nearly end of working hours
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder5() throws ParseException {
      Date startTime = dateParser.parse("28/12/2005 12:05");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("29/12/2005 10:05", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special Day and out of working hours
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder6() throws ParseException {
      Date startTime = dateParser.parse("28/12/2005 13:05");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("29/12/2005 11:00", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on non-Working Day 
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder7() throws ParseException {
      Date startTime = dateParser.parse("24/12/2005 12:05");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("28/12/2005 11:00", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Holiday 
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder8() throws ParseException {
      Date startTime = dateParser.parse("26/12/2005 12:05");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("28/12/2005 11:00", dateParser.format(reminderTime));
   }

   /**
    * SPANNING TESTCASES
    */

   /**
    * Call drops in on Working day within working hours 
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder9() throws ParseException {
      Date startTime = dateParser.parse("15/12/2009 14:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("15/12/2009 15:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working day and nearly end of working hours 
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder10() throws ParseException {
      Date startTime = dateParser.parse("15/12/2009 08:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("15/12/2009 14:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working day and out of working hours 
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder11() throws ParseException {
      Date startTime = dateParser.parse("15/12/2009 10:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("15/12/2009 15:30", dateParser.format(reminderTime));
   }
   
   /**
    * Call drops in on Working day and out of working hours 
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder12() throws ParseException {
      Date startTime = dateParser.parse("08/12/2009 08:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("08/12/2009 15:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special day and within working hours 
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder13() throws ParseException {
      Date startTime = dateParser.parse("07/12/2009 16:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("07/12/2009 17:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special day and nearly end of working hours 
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder14() throws ParseException {
      Date startTime = dateParser.parse("08/12/2009 06:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("08/12/2009 14:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special day and out of working hours 
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder15() throws ParseException {
      Date startTime = dateParser.parse("07/12/2009 14:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("07/12/2009 17:00", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Non-working days 
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder16() throws ParseException {
      Date startTime = dateParser.parse("12/12/2009 14:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("14/12/2009 15:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on holiday 
    * 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder17() throws ParseException {
      Date startTime = dateParser.parse("21/12/2009 14:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("22/12/2009 20:00", dateParser.format(reminderTime));
   }
}
