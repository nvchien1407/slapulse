//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.sla;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class SLAOperationsDurationTest extends SLABaseTest {

//	private SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//	private SLAOperations operations = new SLAOperations();

	
   @Test
   public void testDeadLineDuration2() throws Exception {
		Date dt = dateParser.parse("05/12/2005 15:00");
		Date retDt = operations.getSLADeadline("Claim", null, dt, 0);
		assertTrue(dateParser.format(retDt).equals("08/12/2005 11:00"));
	}

	
   @Test
   public void testDeadLineDuration3() throws Exception {
		Date dt = dateParser.parse("09/12/2005 15:00");
		Date retDt = operations.getSLADeadline("Claim", null, dt, 0);
		assertTrue(dateParser.format(retDt).equals("13/12/2005 15:00"));
	}

	
   @Test
   public void testDeadLineDuration4() throws Exception {
		Date dt = dateParser.parse("23/12/2005 15:00");
		Date retDt = operations.getSLADeadline("Claim", null, dt, 0);
		assertTrue(dateParser.format(retDt).equals("30/12/2005 15:00"));
	}

	
   @Test
   public void testDeadLineDuration5() throws Exception {
		Date dt = dateParser.parse("15/01/2007 13:20");
		Date retDt = operations.getSLADeadline("Staff", "DEFAULT", dt, 0);
		assertTrue(dateParser.format(retDt).equals("15/01/2007 13:30"));
	}

	
   @Test
   public void testDeadLineDuration6() throws Exception {
		Date dt = dateParser.parse("15/01/2007 13:20");
		Date retDt = operations.getSLADeadline("Renewal", "DEFAULT", dt, 0);
		assertTrue(dateParser.format(retDt).equals("16/01/2007 13:20"));
	}

	
   @Test
   public void testDeadLineDuration7() throws Exception {
		Date dt = dateParser.parse("15/01/2007 18:20");
		Date retDt = operations.getSLADeadline("Renewal", null, dt, 0);
		assertTrue(dateParser.format(retDt).equals("17/01/2007 09:00"));
	}
	
	
   @Test
   public void testDeadLineDuration72() throws Exception {
      Date dt = dateParser.parse("22/12/2005 14:00");
      Date retDt = operations.getSLADeadline("Renewal", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("28/12/2005 09:00"));
   }
	
	
   @Test
   public void testDeadLineDuration73() throws Exception {
      Date dt = dateParser.parse("22/12/2005 11:00");
      Date retDt = operations.getSLADeadline("Renewal", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("23/12/2005 11:00"));
   }
	
	
   @Test
   public void testDeadLineDuration71() throws Exception {
      Date dt = dateParser.parse("12/01/2007 13:00");
      Date retDt = operations.getSLADeadline("Renewal", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("15/01/2007 09:00"));
   }

	
   @Test
   public void testDeadLineDuration8() throws Exception {
		Date dt = dateParser.parse("15/01/2007 20:15");
		Date retDt = operations.getSLADeadline("Staff", "DEFAULT", dt, 0);
		assertTrue(dateParser.format(retDt).equals("16/01/2007 09:10"));
	}

	
   @Test
   public void testDeadLineDuration9() throws Exception {
		Date dt = dateParser.parse("15/01/2007 06:45");
		Date retDt = operations.getSLADeadline("Staff", null, dt, 0);
		assertTrue(dateParser.format(retDt).equals("15/01/2007 09:10"));
	}

	
   @Test
   public void testDeadLineDuration10() throws Exception {
		Date dt = dateParser.parse("27/12/2005 15:00");
		Date retDt = operations.getSLADeadline("Claim", null, dt, 0);
		assertTrue(dateParser.format(retDt).equals("31/12/2005 13:00"));
	}
}
