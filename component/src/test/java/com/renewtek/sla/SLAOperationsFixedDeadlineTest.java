//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.sla;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class SLAOperationsFixedDeadlineTest extends SLABaseTest {
	
//	private SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//	private SLAOperations operations = new SLAOperations();

	
   @Test
   public void testFixedDeadline1() throws Exception {
		Date dt = dateParser.parse("23/12/2005 11:35");
		Date retDt = operations.getSLADeadline("NCD", null, dt, 0);
		assertTrue(dateParser.format(retDt).equals("23/12/2005 17:00"));
	}

	
   @Test
   public void testFixedDeadline2() throws Exception {
		Date dt = dateParser.parse("23/12/2005 15:35");
		Date retDt = operations.getSLADeadline("NCD", null, dt, 0);
		assertTrue(dateParser.format(retDt).equals("28/12/2005 17:00"));
	}
	
   @Test
   public void testFixedDeadline4() throws Exception {
		Date dt = dateParser.parse("20/12/2005 16:00");
		Date retDt = operations.getSLADeadline("NCD", null, dt, 0);
		assertTrue(dateParser.format(retDt).equals("21/12/2005 17:00"));
	}

	
   @Test
   public void testFixedDeadline5() throws Exception {
		Date dt = dateParser.parse("25/12/2005 11:00");
		Date retDt = operations.getSLADeadline("NCD", "DEFAULT", dt, 0);
		assertTrue(dateParser.format(retDt).equals("28/12/2005 17:00"));
	}
   
   @Test
   public void testFixedDeadline5Notify() throws Exception {
      Date dt = dateParser.parse("29/10/2010 15:00");
      Date deadline = operations.getSLADeadline("NCD", "DEFAULT", dt, 0);
      Date retDt = operations.getSLAReminderTime("NCD", "DEFAULT", null, deadline);
      assertTrue(dateParser.format(retDt).equals("29/10/2010 15:00"));
   }
}
