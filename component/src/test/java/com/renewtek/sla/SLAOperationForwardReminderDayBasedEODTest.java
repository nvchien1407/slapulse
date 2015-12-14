package com.renewtek.sla;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

public class SLAOperationForwardReminderDayBasedEODTest extends SLABaseTest {

   private String processName = "ForwardReminder";
   private String spanningProcessName = "ForwardReminder Spanning";
   private String processType = "Day Based EOD";
   private String specialProcessName = "ForwardReminder Including Special Days";

   /**
    * NON-SPANNING NON-INCLUDING SPECIAL DAYS TEST CASES
    */

   /**
    * Call drops in on Special Day and within working hours 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder1() throws ParseException {
      Date startTime = dateParser.parse("29/12/2005 09:00");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("02/01/2006 15:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special Day and nearly end of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder2() throws ParseException {
      Date startTime = dateParser.parse("29/12/2005 12:45");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("02/01/2006 15:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special Day and out of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder3() throws ParseException {
      Date startTime = dateParser.parse("29/12/2005 13:00");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("02/01/2006 15:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Non-working Day
    * @throws ParseException
    */
   @Test
   public void testForwardReminder4() throws ParseException {
      Date startTime = dateParser.parse("25/12/2005 13:00");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("02/01/2006 15:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working Day and within working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder5() throws ParseException {
      Date startTime = dateParser.parse("19/12/2005 13:00");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("21/12/2005 19:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working Day and nearly end of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder6() throws ParseException {
      Date startTime = dateParser.parse("19/12/2005 16:45");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("21/12/2005 23:15", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working Day and out of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder7() throws ParseException {
      Date startTime = dateParser.parse("19/12/2005 18:00");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("22/12/2005 15:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Holiday
    * @throws ParseException
    */
   @Test
   public void testForwardReminder8() throws ParseException {
      Date startTime = dateParser.parse("26/12/2005 10:00");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("02/01/2006 15:30", dateParser.format(reminderTime));
   }

   /**
    * INCLUDE SPECIAL DAYS TEST CASES
    */

   /**
    * Call drops in on Special Day and within working hours 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder9() throws ParseException {
      Date startTime = dateParser.parse("29/12/2005 09:00");
      Date deadline = operations.getSLADeadline(specialProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(specialProcessName, processType, startTime, deadline);
      assertEquals("30/12/2005 15:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special Day and nearly end of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder10() throws ParseException {
      Date startTime = dateParser.parse("29/12/2005 12:45");
      Date deadline = operations.getSLADeadline(specialProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(specialProcessName, processType, startTime, deadline);
      assertEquals("30/12/2005 19:15", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special Day and out of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder11() throws ParseException {
      Date startTime = dateParser.parse("29/12/2005 14:00");
      Date deadline = operations.getSLADeadline(specialProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(specialProcessName, processType, startTime, deadline);
      assertEquals("31/12/2005 15:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Non-working Day
    * @throws ParseException
    */
   @Test
   public void testForwardReminder12() throws ParseException {
      Date startTime = dateParser.parse("25/12/2005 13:00");
      Date deadline = operations.getSLADeadline(specialProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(specialProcessName, processType, startTime, deadline);
      assertEquals("29/12/2005 15:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working Day and within working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder13() throws ParseException {
      Date startTime = dateParser.parse("19/12/2005 9:00");
      Date deadline = operations.getSLADeadline(specialProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(specialProcessName, processType, startTime, deadline);
      assertEquals("20/12/2005 15:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working Day and nearly end of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder14() throws ParseException {
      Date startTime = dateParser.parse("19/12/2005 16:45");
      Date deadline = operations.getSLADeadline(specialProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(specialProcessName, processType, startTime, deadline);
      assertEquals("20/12/2005 23:15", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working Day and out of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder15() throws ParseException {
      Date startTime = dateParser.parse("19/12/2005 18:00");
      Date deadline = operations.getSLADeadline(specialProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(specialProcessName, processType, startTime, deadline);
      assertEquals("21/12/2005 15:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working Day and reminder is after 12:00 AM
    * @throws ParseException
    */
   @Test
   public void testForwardReminder28() throws ParseException {
      Date startTime = dateParser.parse("03/01/2012 19:00");
      Date deadline = operations.getSLADeadline(specialProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(specialProcessName, processType, startTime, deadline);
      assertEquals("05/01/2012 10:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Holiday
    * @throws ParseException
    */
   @Test
   public void testForwardReminder16() throws ParseException {
      Date startTime = dateParser.parse("26/12/2005 10:00");
      Date deadline = operations.getSLADeadline(specialProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(specialProcessName, processType, startTime, deadline);
      assertEquals("29/12/2005 15:30", dateParser.format(reminderTime));
   }

   /**
    * SPANNING TEST CASES
    */

   /**
    * Call drops in on Working days and within working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder17() throws ParseException {
      Date startTime = dateParser.parse("14/12/2009 15:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("14/12/2009 16:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working days and nearly end of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder18() throws ParseException {
      Date startTime = dateParser.parse("15/12/2009 8:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("15/12/2009 14:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working days and out of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder19() throws ParseException {
      Date startTime = dateParser.parse("15/12/2009 10:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("15/12/2009 15:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working days and out of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder20() throws ParseException {
      Date startTime = dateParser.parse("8/12/2009 8:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("08/12/2009 15:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special days and within working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder21() throws ParseException {
      Date startTime = dateParser.parse("07/12/2009 16:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("07/12/2009 17:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special days and nearly end of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder22() throws ParseException {
      Date startTime = dateParser.parse("8/12/2009 6:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("08/12/2009 14:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special days and out of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder23() throws ParseException {
      Date startTime = dateParser.parse("7/12/2009 14:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("07/12/2009 17:00", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Non-working days
    * @throws ParseException
    */
   @Test
   public void testForwardReminder24() throws ParseException {
      Date startTime = dateParser.parse("12/12/2009 15:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("14/12/2009 15:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Holiday
    * @throws ParseException
    */
   @Test
   public void testForwardReminder25() throws ParseException {
      Date startTime = dateParser.parse("21/12/2009 15:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("22/12/2009 20:00", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working days and the reminder is after 00:00 AM
    * @throws ParseException
    */
   @Test
   public void testForwardReminder26() throws ParseException {
      Date startTime = dateParser.parse("15/12/2009 23:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("16/12/2009 00:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working days and the reminder is after 00:00 AM and out of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder27() throws ParseException {
      Date startTime = dateParser.parse("08/12/2009 06:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("08/12/2009 14:30", dateParser.format(reminderTime));
   }
}
