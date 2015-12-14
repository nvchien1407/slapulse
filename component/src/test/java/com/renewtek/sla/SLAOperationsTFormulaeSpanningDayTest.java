// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.sla;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class SLAOperationsTFormulaeSpanningDayTest extends SLABaseTest {

//   private SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//
//   private SLAOperations operations = new SLAOperations();

   
//   @Test
//   public void setUp() throws Exception {
//      super.setUp();
//      operations = new SLAOperations();
//      dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//   }
//
//   
//   @Test
//   public void tearDown() throws Exception {
//      super.tearDown();
//   }

   
   @Test
   public void testTFormulaeSpanningDay1() throws Exception {
      Date dt = dateParser.parse("09/11/2009 14:00");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "Extended", dt, 0);
      assertEquals("11/11/2009 09:00", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulaeSpanningDay2() throws Exception {
      Date dt = dateParser.parse("09/11/2009 10:00");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "Extended", dt, 0);
      assertEquals("11/11/2009 09:00", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulaeSpanningDay22() throws Exception {
      Date dt = dateParser.parse("09/11/2009 06:00");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "Extended", dt, 0);
      assertEquals("11/11/2009 09:00", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulaeSpanningDay3() throws Exception {
      Date dt = dateParser.parse("08/11/2009 10:00");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "Extended", dt, 0);
      assertEquals("11/11/2009 09:00", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulaeSpanningDay4() throws Exception {
      Date dt = dateParser.parse("07/11/2009 15:00");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "Extended", dt, 0);
      assertEquals("11/11/2009 09:00", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulaeSpanningDay5() throws Exception {
      Date dt = dateParser.parse("23/11/2009 15:00");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "Extended", dt, 0);
      assertEquals("26/11/2009 09:00", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulaeSpanningDay6() throws Exception {
      Date dt = dateParser.parse("17/11/2009 15:00");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "Extended", dt, 0);
      assertEquals("19/11/2009 18:00", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulaeSpanningDay7() throws Exception {
      Date dt = dateParser.parse("02/11/2009 15:00");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "Motor", dt, 0);
      assertEquals("04/11/2009 23:59", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulaeSpanningDay8() throws Exception {
      Date dt = dateParser.parse("08/11/2009 16:00");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "Motor", dt, 0);
      assertEquals("11/11/2009 23:59", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulaeSpanningDay9() throws Exception {
      Date dt = dateParser.parse("17/11/2009 14:30");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "Motor", dt, 0);
      assertEquals("20/11/2009 23:59", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulaeSpanningDay10() throws Exception {
      Date dt = dateParser.parse("22/11/2009 14:30");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "Motor", dt, 0);
      assertEquals("26/11/2009 23:59", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulaeSpanningDay11() throws Exception {
      Date dt = dateParser.parse("06/11/2009 14:30");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "Motor", dt, 0);
      assertEquals("11/11/2009 23:59", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulaeSpanningDay12() throws Exception {
      Date dt = dateParser.parse("14/12/2009 14:30");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "HouseHold", dt, 0);
      assertEquals("16/12/2009 14:30", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulaeSpanningDay13() throws Exception {
      Date dt = dateParser.parse("07/12/2009 15:30");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "HouseHold", dt, 0);
      assertEquals("09/12/2009 15:30", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulaeSpanningDay14() throws Exception {
      Date dt = dateParser.parse("18/12/2009 15:30");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "HouseHold", dt, 0);
      assertEquals("23/12/2009 20:30", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulaeSpanningDay15() throws Exception {
      Date dt = dateParser.parse("21/12/2009 15:30");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "HouseHold", dt, 0);
      assertEquals("25/12/2009 19:00", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulaeSpanningDay16() throws Exception {
      Date dt = dateParser.parse("12/12/2009 15:30");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "HouseHold", dt, 0);
      assertEquals("16/12/2009 14:00", dateParser.format(retDt));
   }

   @Test
   public void testTFormulaeSpanningDay17() throws Exception {
      Date dt = dateParser.parse("08/12/2009 07:05");
      Date retDt = operations.getSLADeadline("DailyMovieBook", "Extended", dt, 0);
      assertEquals("10/12/2009 09:00", dateParser.format(retDt));
   }
}
