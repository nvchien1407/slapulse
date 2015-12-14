package com.renewtek.sla;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

public class SLAOperationForwardReminderDayBasedEOBTest extends SLABaseTest {
   private String processName = "ForwardReminder";
   private String spanningProcessName = "ForwardReminder Spanning";
   private String specialProcessName = "ForwardReminder Including Special Days";
   private String processType = "Day Based EOB";

   /**
    * NON-SPANNING NON-INCLUDING SPECIAL DAYS TEST CASES
    */

   /**
    * Call drops in on Special Day and within working hours 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder1() throws ParseException {
      Date startTime = dateParser.parse("23/12/2005 09:00");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("29/12/2005 09:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special Day and nearly end of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder2() throws ParseException {
      Date startTime = dateParser.parse("23/12/2005 12:45");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("29/12/2005 09:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special Day and out of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder3() throws ParseException {
      Date startTime = dateParser.parse("23/12/2005 13:00");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("29/12/2005 09:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Non-working Day
    * @throws ParseException
    */
   @Test
   public void testForwardReminder4() throws ParseException {
      Date startTime = dateParser.parse("24/12/2005 13:00");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("29/12/2005 09:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working Day and within working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder5() throws ParseException {
      Date startTime = dateParser.parse("22/12/2005 13:00");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("22/12/2005 13:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working Day and nearly end of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder6() throws ParseException {
      Date startTime = dateParser.parse("22/12/2005 16:45");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("29/12/2005 09:15", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working Day and out of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder7() throws ParseException {
      Date startTime = dateParser.parse("22/12/2005 18:00");
      Date deadline = operations.getSLADeadline(processName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(processName, processType, startTime, deadline);
      assertEquals("29/12/2005 09:30", dateParser.format(reminderTime));
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
      assertEquals("29/12/2005 09:30", dateParser.format(reminderTime));
   }

   /**
    * INCLUDING NON-STANDARD WORKING DAYS TEST CASES
    */
   

   /**
    * Call drops in on Special Day and within working hours 
    * @throws ParseException
    */
   @Test
   public void testForwardReminder9() throws ParseException {
      Date startTime = dateParser.parse("23/12/2005 09:00");
      Date deadline = operations.getSLADeadline(specialProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(specialProcessName, processType, startTime, deadline);
      assertEquals("28/12/2005 11:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special Day and nearly end of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder10() throws ParseException {
      Date startTime = dateParser.parse("23/12/2005 12:45");
      Date deadline = operations.getSLADeadline(specialProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(specialProcessName, processType, startTime, deadline);
      assertEquals("29/12/2005 11:15", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special Day and out of working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder11() throws ParseException {
      Date startTime = dateParser.parse("23/12/2005 13:01");
      Date deadline = operations.getSLADeadline(specialProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(specialProcessName, processType, startTime, deadline);
      assertEquals("29/12/2005 11:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Non-working Day
    * @throws ParseException
    */
   @Test
   public void testForwardReminder12() throws ParseException {
      Date startTime = dateParser.parse("24/12/2005 13:00");
      Date deadline = operations.getSLADeadline(specialProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(specialProcessName, processType, startTime, deadline);
      assertEquals("29/12/2005 11:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working Day and within working hours
    * @throws ParseException
    */
   @Test
   public void testForwardReminder13() throws ParseException {
      Date startTime = dateParser.parse("19/12/2005 09:00");
      Date deadline = operations.getSLADeadline(specialProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(specialProcessName, processType, startTime, deadline);
      assertEquals("19/12/2005 15:30", dateParser.format(reminderTime));
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
      assertEquals("20/12/2005 15:15", dateParser.format(reminderTime));
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
      assertEquals("20/12/2005 15:30", dateParser.format(reminderTime));
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
      assertEquals("29/12/2005 11:30", dateParser.format(reminderTime));
   }

   /**
    * SPANNING TEST CASES
    */
   /**
    * Call drops in on Working Day within working hours.
    */
   @Test
   public void testForwardReminder17() throws ParseException {
      Date startTime = dateParser.parse("14/12/2009 16:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("14/12/2009 16:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working Day nearly end of working hours.
    */
   @Test
   public void testForwardReminder18() throws ParseException {
      Date startTime = dateParser.parse("15/12/2009 08:45");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("15/12/2009 14:15", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Working Day out of working hours.
    */
   @Test
   public void testForwardReminder19() throws ParseException {
      Date startTime = dateParser.parse("15/12/2009 10:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("15/12/2009 14:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special Day and within working hours.
    * 
    */
   @Test
   public void testForwardReminder20() throws ParseException {
      Date startTime = dateParser.parse("07/12/2009 16:00");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("07/12/2009 16:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Special Day out of working hours.
    * 
    */
   @Test
   public void testForwardReminder21() throws ParseException {
      Date startTime = dateParser.parse("08/12/2009 6:45");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("08/12/2009 14:15", dateParser.format(reminderTime));
   }


   /**
    * Call drops in on Special Day out of working hours.
    * 
    */
   @Test
   public void testForwardReminder22() throws ParseException {
      Date startTime = dateParser.parse("08/12/2009 7:05");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("08/12/2009 14:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Non-working day.
    * 
    */
   @Test
   public void testForwardReminder23() throws ParseException {
      Date startTime = dateParser.parse("12/12/2009 10:05");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("14/12/2009 14:30", dateParser.format(reminderTime));
   }

   /**
    * Call drops in on Holiday
    * 
    */
   @Test
   public void testForwardReminder24() throws ParseException {
      Date startTime = dateParser.parse("21/12/2009 10:05");
      Date deadline = operations.getSLADeadline(spanningProcessName, processType, startTime);
      Date reminderTime = operations.getSLAReminderTime(spanningProcessName, processType, startTime, deadline);
      assertEquals("22/12/2009 19:00", dateParser.format(reminderTime));
   }
}
