//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.sla;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class SLAOperationsTFormulaeNewTest extends SLABaseTest {

//	private SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//	private SLAOperations operations = new SLAOperations();

	
//   @Test
//   public void setUp() throws Exception {
//		super.setUp();
//		operations = new SLAOperations();
//		dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//	}
//
//	
//   @Test
//   public void tearDown() throws Exception {
//		super.tearDown();
//	}
	
	
   @Test
   public void testTFormulae11() throws Exception {
      Date dt = dateParser.parse("10/12/2005 9:10");
      Date retDt = operations.getSLADeadline("NewBusiness", "Motor", dt, 0);
      //assertTrue(dateParser.format(retDt).equals("12/12/2005 17:00"));
      assertTrue(dateParser.format(retDt).equals("13/12/2005 17:00"));
   }
	
   
   @Test
   public void testTFormulae12() throws Exception {
      Date dt = dateParser.parse("19/12/2005 06:12");
      Date retDt = operations.getSLADeadline("NewBusiness", "Motor", dt, 0);
      assertTrue(dateParser.format(retDt).equals("20/12/2005 17:00"));
   }	
   
   
   @Test
   public void testTFormulae13() throws Exception {
      Date dt = dateParser.parse("19/12/2005 18:28");
      Date retDt = operations.getSLADeadline("NewBusiness", "Motor", dt, 0);
      //assertTrue(dateParser.format(retDt).equals("20/12/2005 17:00"));
      assertTrue(dateParser.format(retDt).equals("21/12/2005 17:00"));
   }
   
   
   @Test
   public void testTFormulae68() throws Exception {
      Date dt = dateParser.parse("19/12/2005 17:00");
      Date retDt = operations.getSLADeadline("NewBusiness", "Motor", dt, 0);
      assertTrue(dateParser.format(retDt).equals("20/12/2005 17:00"));
   }
   
   
   @Test
   public void testTFormulae14() throws Exception {
      Date dt = dateParser.parse("26/12/2005 10:28");
      Date retDt = operations.getSLADeadline("NewBusiness", "Motor", dt, 0);
      assertTrue(dateParser.format(retDt).equals("29/12/2005 17:00"));
   }
   
   
   @Test
   public void testTFormulae15() throws Exception {
      Date dt = dateParser.parse("22/12/2005 14:00"); // start from special day.
      Date retDt = operations.getSLADeadline("NewBusiness", "Motor", dt, 0);
      assertEquals("23/12/2005 13:00", dateParser.format(retDt)); 
   }
   
   // TODO check with testTFormulaeSpanningDay1
   
   @Test
   public void testTFormulae16() throws Exception {
      Date dt = dateParser.parse("05/12/2005 09:00");
      Date retDt = operations.getSLADeadline("NewBusiness", "Motor", dt, 0);
      assertEquals("06/12/2005 17:00", dateParser.format(retDt)); 
   }
   
   
   @Test
   public void testTFormulae22() throws Exception {
      Date dt = dateParser.parse("05/12/2005 06:00");
      Date retDt = operations.getSLADeadline("NewBusiness", "HouseHold", dt, 0);
      assertTrue(dateParser.format(retDt).equals("08/12/2005 23:59"));
   }
   
//   
//   @Test
//   public void testTFormulae23() throws Exception {
//      Date dt = dateParser.parse("05/12/2005 18:00");
//      Date retDt = operations.getSLADeadline("NewBusiness", "HouseHold", dt, 0);
//      assertTrue(dateParser.format(retDt).equals("08/12/2005 23:59"));
//   }
   
   
   @Test
   public void testTFormulae60() throws Exception {
      Date dt = dateParser.parse("05/12/2005 10:28");
      Date retDt = operations.getSLADeadline("DailyMovieBook", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("07/12/2005 10:28"));
   }
   
   
   @Test
   public void testTFormulae61() throws Exception {
      Date dt = dateParser.parse("05/12/2005 06:37");
      Date retDt = operations.getSLADeadline("DailyMovieBook", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("07/12/2005 09:00"));
   }
   
//   
//   @Test
//   public void testTFormulae62() throws Exception {
//      Date dt = dateParser.parse("05/12/2005 10:45");
//      Date retDt = operations.getSLADeadline("DailyMovieBook", null, dt, 0);
//      //System.out.println("RESULT = " + retDt);
//      assertTrue(dateParser.format(retDt).equals("07/12/2005 10:45"));
//   }
   
   
   @Test
   public void testTFormulae62() throws Exception {
      Date dt = dateParser.parse("05/12/2005 19:22");
      Date retDt = operations.getSLADeadline("DailyMovieBook", null, dt, 0);
      //System.out.println("RESULT = " + retDt);
      assertTrue(dateParser.format(retDt).equals("08/12/2005 09:00"));
   }
   
   
   @Test
   public void testTFormulae69() throws Exception {
      Date dt = dateParser.parse("05/12/2005 17:00");
      Date retDt = operations.getSLADeadline("DailyMovieBook", null, dt, 0);
      //System.out.println("RESULT = " + retDt);
      assertTrue(dateParser.format(retDt).equals("07/12/2005 17:00"));
   }
   
   
   @Test
   public void testTFormulae63() throws Exception {
      Date dt = dateParser.parse("10/12/2005 09:22");
      Date retDt = operations.getSLADeadline("DailyMovieBook", null, dt, 0);
      //System.out.println("RESULT = " + retDt);
      //assertTrue(dateParser.format(retDt).equals("14/12/2005 09:22"));
      assertTrue(dateParser.format(retDt).equals("14/12/2005 09:00"));
   }
   
   
   @Test
   public void testTFormulae64() throws Exception {
      Date dt = dateParser.parse("10/12/2005 18:40");
      Date retDt = operations.getSLADeadline("DailyMovieBook", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("14/12/2005 09:00"));
   }
   
   
   @Test
   public void testTFormulae65() throws Exception {
      Date dt = dateParser.parse("10/12/2005 06:45");
      Date retDt = operations.getSLADeadline("DailyMovieBook", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("14/12/2005 09:00"));
   }
   
   
   @Test
   public void testTFormulae66() throws Exception {
      Date dt = dateParser.parse("26/12/2005 09:45");
      Date retDt = operations.getSLADeadline("DailyMovieBook", null, dt, 0);
      //System.out.println("RESULT = " + retDt);
      assertTrue(dateParser.format(retDt).equals("03/01/2006 09:00"));
   }
   
   
   @Test
   public void testTFormulae67() throws Exception {
      Date dt = dateParser.parse("02/01/2006 09:45");
      Date retDt = operations.getSLADeadline("DailyMovieBook", null, dt, 0);
      //System.out.println("RESULT = " + retDt);
      assertTrue(dateParser.format(retDt).equals("05/01/2006 09:00"));
   }
   
   
   @Test
   public void testTFormulae25() throws Exception {
      Date dt = dateParser.parse("05/12/2005 17:00");
      Date retDt = operations.getSLADeadline("NewBusiness", "HouseHold", dt, 0);
      assertEquals("08/12/2005 23:59", dateParser.format(retDt));
   }
   
   
   @Test
   public void testTFormulae24() throws Exception {
      Date dt = dateParser.parse("05/12/2005 17:13");
      Date retDt = operations.getSLADeadline("NewBusiness", "HouseHold", dt, 0);
      assertEquals("09/12/2005 23:59", dateParser.format(retDt));
   }
}
