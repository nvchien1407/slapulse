package com.renewtek.sla;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

/**
 * This is to test the code fix for RSLAP-60
 * @author camhoang
 *
 */
public class SLAOperationWithTimeZoneTest extends SLABaseTest {
   private static final String processName = "DifferentTimeZone";
   private static final String processType = "London Calendar";

   private SimpleDateFormat tzDateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm zzzz"); 

   private String tz = TimeZone.getTimeZone("Europe/London").getDisplayName();

   @Test
   public void testWithTimeZone1() throws ParseException {
      Date dt = tzDateParser.parse("14/02/2012 04:30 " + tz);
      Date retDt = operations.getSLADeadline(processName, processType, dt, 0);
      assertEquals(tzDateParser.parse("14/02/2012 04:40 " + tz), retDt);

      Date retRm = operations.getSLAReminderTime(processName, processType, dt, retDt);
      assertEquals(tzDateParser.parse("14/02/2012 04:35 " + tz), retRm);
   }

   @Test
   public void testWithTimeZone2() throws ParseException {
      Date dt = tzDateParser.parse("14/02/2012 00:00 " + tz);
      Date retDt = operations.getSLADeadline(processName, processType, dt, 0);
      assertEquals(tzDateParser.parse("14/02/2012 00:10 " + tz), retDt);

      Date retRm = operations.getSLAReminderTime(processName, processType, dt, retDt);
      assertEquals(tzDateParser.parse("14/02/2012 00:05 " + tz), retRm);
   }

   @Test
   public void testWithTimeZone3() throws ParseException {
      Date dt = tzDateParser.parse("14/02/2012 23:00 " + tz);
      Date retDt = operations.getSLADeadline(processName, processType, dt, 0);
      assertEquals(tzDateParser.parse("14/02/2012 23:10 " + tz), retDt);

      Date retRm = operations.getSLAReminderTime(processName, processType, dt, retDt);
      assertEquals(tzDateParser.parse("14/02/2012 23:05 " + tz), retRm);
   }

   @Test
   public void testWithTimeZone4() throws ParseException {
      Date dt = tzDateParser.parse("14/02/2012 23:59 " + tz);
      Date retDt = operations.getSLADeadline(processName, processType, dt, 0);
      
      //Change time because of 23:59 is the end of business day
      assertEquals(tzDateParser.parse("15/02/2012 00:10 " + tz), retDt);

      Date retRm = operations.getSLAReminderTime(processName, processType, dt, retDt);
      assertEquals(tzDateParser.parse("15/02/2012 00:05 " + tz), retRm);
   }

   @Test
   public void testWithTimeZone5() throws ParseException {
      Date dt = tzDateParser.parse("14/02/2012 04:40 " + tz);
      Date retDt = operations.getSLADeadline(processName, processType, dt, 0);
      assertEquals(tzDateParser.parse("14/02/2012 04:50 " + tz), retDt);

      Date retRm = operations.getSLAReminderTime(processName, processType, dt, retDt);
      assertEquals(tzDateParser.parse("14/02/2012 04:45 " + tz), retRm);
   }

   /**
    * These tests are to make sure that new fix doesn't break down the functionality.
    */

   @Test
   public void testTFormulae1() throws ParseException {
      Date dt = dateParser.parse("14/02/2012 09:30");
      Date retDt = operations.getSLADeadline("NewBusiness", "Motor", dt);
      assertEquals(dateParser.parse("15/02/2012 17:00"), retDt);

      Date retRm = operations.getSLAReminderTime("NewBusiness", "Motor", dt, retDt);
      assertEquals(dateParser.parse("15/02/2012 09:00"), retRm);
   }

   @Test
   public void testTFormulae2() throws ParseException {
      Date dt = dateParser.parse("14/02/2012 09:30");
      Date retDt = operations.getSLADeadline("NewBusiness", "HouseHold", dt);
      assertEquals(dateParser.parse("17/02/2012 23:59"), retDt);

      // The deadline was calculated here is the EOB not really EOD???
      Date retRm = operations.getSLAReminderTime("NewBusiness", "HouseHold", dt, retDt);
      assertEquals(dateParser.parse("17/02/2012 09:00"), retRm);
   }

   @Test
   public void testTFormulaeSpanning1() throws ParseException {
      Date dt = dateParser.parse("14/02/2012 09:30");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "Extended", dt);
      assertEquals(dateParser.parse("16/02/2012 09:00"), retDt);
   }

   @Test
   public void testFixedDeadline1() throws ParseException {
      Date dt = dateParser.parse("14/02/2012 09:30");
      Date retDt = operations.getSLADeadline("NCD", "DEFAULT", dt);
      assertEquals(dateParser.parse("14/02/2012 17:00"), retDt);

      // The deadline was calculated here is the EOB not really EOD???
      Date retRm = operations.getSLAReminderTime("NCD", "DEFAULT", dt, retDt);
      assertEquals(dateParser.parse("14/02/2012 15:00"), retRm);
   }

   @Test
   public void testFixedDeadline2() throws ParseException {
      Date dt = dateParser.parse("14/02/2012 15:40");
      Date retDt = operations.getSLADeadline("NCD", "DEFAULT", dt);
      assertEquals(dateParser.parse("15/02/2012 17:00"), retDt);

      // The deadline was calculated here is the EOB not really EOD???
      Date retRm = operations.getSLAReminderTime("NCD", "DEFAULT", dt, retDt);
      assertEquals(dateParser.parse("15/02/2012 15:00"), retRm);
   }

   @Test
   public void testDuration1() throws ParseException {
      Date dt = dateParser.parse("14/02/2012 15:40");
      Date retDt = operations.getSLADeadline("Staff", "DEFAULT", dt);
      assertEquals(dateParser.parse("14/02/2012 15:50"), retDt);
   }
}
