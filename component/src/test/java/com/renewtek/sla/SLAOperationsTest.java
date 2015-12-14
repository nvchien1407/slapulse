//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.sla;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.renewtek.Constants;

public class SLAOperationsTest extends SLABaseTest {

//	private SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private SimpleDateFormat dateOnlyParser = new SimpleDateFormat("dd/MM/yyyy");
	//private SLAOperations operations = new SLAOperations();

	
   @Test
   public void testStartTime() throws Exception {
		Date dt = dateOnlyParser.parse("16/08/1906 19:08");
		Date retDt = operations.getSLAStartTime("Claim", null, dt);
		String retDate = dateOnlyParser.format(retDt);
		String sysDate = dateOnlyParser.format(new Date());

		assertTrue(retDate.equals(sysDate));
	}

	
   @Test
   public void testReminderTime01() throws Exception {
		Date dt = dateParser.parse("16/08/2006 16:00");
		Date retDt = operations.getSLAReminderTime("Claim",null, null, dt);
		assertTrue(dateParser.format(retDt).equals("15/08/2006 16:00"));
	}

	
   @Test
   public void testReminderTime02() throws Exception {
		Date dt = dateParser.parse("23/08/2006 16:00");
		Date retDt = operations.getSLAReminderTime("Claim","Motor", null, dt);
		assertTrue(dateParser.format(retDt).equals("23/08/2006 11:50"));
	}

	
   @Test
   public void testReminderTime03() throws Exception {
		Date dt = dateParser.parse("21/08/2006 10:00");
		Date retDt = operations.getSLAReminderTime("Claim","Motor", null, dt);
		assertTrue(dateParser.format(retDt).equals("19/08/2006 09:50"));
	}

	
   @Test
   public void testReminderTime04() throws Exception {
		Date dt = dateParser.parse("28/12/2005 17:00");
		Date retDt = operations.getSLAReminderTime("NCD",null, null, dt);
		assertTrue(dateParser.format(retDt).equals("28/12/2005 15:00"));
	}

	
   @Test
   public void testSLAStatus() throws Exception {
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, 4);
		Date deadline = cal.getTime();

		cal.add(Calendar.DATE, -2);
		Date reminder = cal.getTime();

		cal.add(Calendar.DATE, -1);
		Date pointInTime = cal.getTime();

		String status = operations.getSLAStatus(null, null, pointInTime, deadline, reminder);
		assertTrue(status.equals(Constants.DEFAULT_SLASTATUS_OK));

		cal.add(Calendar.DATE, 2);
		pointInTime = cal.getTime();
		status = operations.getSLAStatus(null, null, pointInTime, deadline, reminder);
		assertTrue(status.equals(Constants.DEFAULT_SLASTATUS_APPROACHING));

		cal.add(Calendar.DATE, 2);
		pointInTime = cal.getTime();
		status = operations.getSLAStatus(null, null, pointInTime, deadline, reminder);
		assertTrue(status.equals(Constants.DEFAULT_SLASTATUS_EXCEEDED));
	}

	
   @Test
   public void testGetSLANotifyDeadlineApproaching() throws Exception {
		String processName = "NewBusiness";
		String processType = "";
		boolean notify = operations.getSLANotifyDeadlineApproaching( processName, processType);
		assertTrue(!notify);
	}
}
