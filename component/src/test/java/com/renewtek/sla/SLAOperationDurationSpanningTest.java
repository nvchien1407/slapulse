//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.sla;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;


public class SLAOperationDurationSpanningTest extends SLABaseTest {

//   private SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//   private SLAOperations operations = new SLAOperations();
   
   
   @Test
   public void testDeadLineDurationSpanning1() throws Exception {
      Date dt = dateParser.parse("08/12/2009 15:00");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("09/12/2009 16:00"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning1A1() throws Exception {
      Date dt = dateParser.parse("08/12/2009 07:00");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("09/12/2009 08:00"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning1A2() throws Exception {
      Date dt = dateParser.parse("08/12/2009 07:30");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("09/12/2009 08:30"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning2() throws Exception {
      Date dt = dateParser.parse("11/12/2009 15:00");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("14/12/2009 20:00"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning3() throws Exception {
      Date dt = dateParser.parse("12/12/2009 15:00");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("15/12/2009 15:00"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning4() throws Exception {
      Date dt = dateParser.parse("07/12/2009 20:00");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("08/12/2009 23:00"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning5() throws Exception {
      Date dt = dateParser.parse("21/12/2009 18:00");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("23/12/2009 21:30"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning6() throws Exception {
      Date dt = dateParser.parse("20/12/2009 18:00");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("23/12/2009 21:30"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning7() throws Exception {
      Date dt = dateParser.parse("08/12/2009 15:00");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Extended", dt, 0);
      assertTrue(dateParser.format(retDt).equals("09/12/2009 15:00"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning7A1() throws Exception {
      Date dt = dateParser.parse("08/12/2009 06:10");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Extended", dt, 0);
      assertTrue(dateParser.format(retDt).equals("09/12/2009 06:10"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning8() throws Exception {
      Date dt = dateParser.parse("11/12/2009 15:10");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Extended", dt, 0);
      assertTrue(dateParser.format(retDt).equals("14/12/2009 14:00"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning9() throws Exception {
      Date dt = dateParser.parse("13/12/2009 15:10");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Extended", dt, 0);
      assertTrue(dateParser.format(retDt).equals("15/12/2009 14:00"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning10() throws Exception {
      Date dt = dateParser.parse("21/12/2009 15:10");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Extended", dt, 0);
      assertTrue(dateParser.format(retDt).equals("23/12/2009 18:30"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning11() throws Exception {
      Date dt = dateParser.parse("20/12/2009 16:10");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Extended", dt, 0);
      assertTrue(dateParser.format(retDt).equals("23/12/2009 18:30"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning12() throws Exception {
      Date dt = dateParser.parse("01/12/2009 16:10");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Motor", dt, 0);
      assertTrue(dateParser.format(retDt).equals("01/12/2009 16:20"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning13() throws Exception {
      Date dt = dateParser.parse("01/12/2009 16:10");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Motor", dt, 0);
      assertTrue(dateParser.format(retDt).equals("01/12/2009 16:20"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning14() throws Exception {
      Date dt = dateParser.parse("01/12/2009 09:00");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Motor", dt, 0);
      assertTrue(dateParser.format(retDt).equals("01/12/2009 14:10"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning15() throws Exception {
      Date dt = dateParser.parse("07/12/2009 06:13");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Motor", dt, 0);
      assertTrue(dateParser.format(retDt).equals("07/12/2009 06:23"));
   }
   
   @Test
   public void testDeadLineDurationSpanning15Afternoon() throws Exception {
      Date dt = dateParser.parse("02/12/2009 13:21");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Motor", dt, 0);
      assertTrue(dateParser.format(retDt).equals("02/12/2009 14:10"));
   }
   
   @Test
   public void testDeadLineDurationSpanning15morning() throws Exception {
      Date dt = dateParser.parse("02/12/2009 01:14");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Motor", dt, 0);
      assertTrue(dateParser.format(retDt).equals("02/12/2009 01:24"));
   }
   
   @Test
   public void testDeadLineDurationSpanning16() throws Exception {
      Date dt = dateParser.parse("09/12/2009 05:43");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Motor", dt, 0);
      assertTrue(dateParser.format(retDt).equals("09/12/2009 05:53"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning17() throws Exception {
      Date dt = dateParser.parse("13/12/2009 14:30");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Motor", dt, 0);
      assertTrue(dateParser.format(retDt).equals("14/12/2009 14:10"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning18() throws Exception {
      Date dt = dateParser.parse("12/12/2009 14:30");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Motor", dt, 0);
      assertTrue(dateParser.format(retDt).equals("14/12/2009 14:10"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning19() throws Exception {
      Date dt = dateParser.parse("21/12/2009 14:30");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Motor", dt, 0);
      assertTrue(dateParser.format(retDt).equals("22/12/2009 18:40"));
   }
   
   
   @Test
   public void testDeadLineDurationSpanning20() throws Exception {
      Date dt = dateParser.parse("20/12/2009 14:30");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Motor", dt, 0);
      assertTrue(dateParser.format(retDt).equals("22/12/2009 18:40"));
   }
   
   @Test
   public void testDeadLineDurationSpanning20Notify() throws Exception {
      Date dt = dateParser.parse("29/10/2010 14:30");
      Date retDt = operations.getSLADeadline("DurationBusinessSpan", "Notify", dt, 0);
      assertTrue(dateParser.format(retDt).equals("29/10/2010 19:20"));
   }
   
   @Test
   public void testDeadLineDurationSpanning20Notify2() throws Exception {
      Date dt = dateParser.parse("29/10/2010 14:30");
      Date deadline = operations.getSLADeadline("DurationBusinessSpan", "Notify", dt, 0);
      Date retDt = operations.getSLAReminderTime("DurationBusinessSpan", "Notify", null, deadline);
      assertTrue(dateParser.format(retDt).equals("29/10/2010 19:10"));
   }
}
