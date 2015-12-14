//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.sla;

import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SLAOperationsTFormulaeTest extends SLABaseTest {

//	private SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//	private SLAOperations operations = new SLAOperations();
//
//	
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
   public void testTFormulae1() throws Exception {
		Date dt = dateParser.parse("23/12/2005 11:35");
		Date retDt = operations.getSLADeadline("NewBusiness", "Motor", dt, 0);
		assertEquals("28/12/2005 13:00", dateParser.format(retDt));
	}

	
   @Test
   public void testTFormulae2() throws Exception {
		Date dt = dateParser.parse("05/12/2005 15:00");
		Date retDt = operations.getSLADeadline("NewBusiness", "HouseHold", dt, 0);
		assertEquals("08/12/2005 23:59", dateParser.format(retDt));
	}
	
   @Test
   public void testTFormulae3() throws Exception {
		Date dt = dateParser.parse("20/12/2005 15:00");
		Date retDt = operations.getSLADeadline("NewBusiness", "HouseHold", dt, 0);
		assertEquals("29/12/2005 23:59", dateParser.format(retDt));
	}

	
   @Test
   public void testTFormulae4() throws Exception {
		Date dt = dateParser.parse("27/12/2005 11:00");
		Date retDt = operations.getSLADeadline("NewBusiness", "HouseHold", dt, 0);
		assertEquals("04/01/2006 23:59", dateParser.format(retDt));
	}

	
   @Test
   public void testTFormulae5() throws Exception {
		Date dt = dateParser.parse("27/12/2007 11:00");
		Date retDt = operations.getSLADeadline("NewBusiness", "Extended", dt, 0);
		assertEquals("14/02/2008 17:00", dateParser.format(retDt));
	}
	
	
   @Test
   public void testTFormulae6() throws Exception {
		Date dt = dateParser.parse("23/12/2005 11:00"); // start from special day.
		Date retDt = operations.getSLADeadline("NewBusiness", "Motor", dt, 0);
		assertEquals("28/12/2005 13:00", dateParser.format(retDt)); 
	}

	
   @Test
   public void testTFormulae7() throws Exception {
		Date dt = dateParser.parse("26/12/2005 11:00"); // start from holiday.
		Date retDt = operations.getSLADeadline("NewBusiness", "Motor", dt, 0);
		assertEquals("29/12/2005 17:00", dateParser.format(retDt));
	}
	
	
   @Test
   public void testTFormulae8() throws Exception {
		Date dt = dateParser.parse("25/12/2005 11:00"); // start from weekend.
		Date retDt = operations.getSLADeadline("NewBusiness", "Motor", dt, 0);
		assertEquals("29/12/2005 17:00", dateParser.format(retDt));
	}
   
   @Test
   public void testTFormulae8Notify() throws Exception {
      Date dt = dateParser.parse("28/10/2010 10:22"); // start from weekend.
      Date deadline = operations.getSLADeadline("NewBusiness", "Motor", dt, 0);
      Date retDt = operations.getSLAReminderTime("NewBusiness", "Motor", null, deadline);
      assertEquals("29/10/2010 09:00", dateParser.format(retDt));
   }
	
}
