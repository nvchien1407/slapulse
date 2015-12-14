//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.sla;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class SLAOperationsDurationCutOffTimeTest extends SLABaseTest {

   @Test
   public void testDeadLineDuration2CutOffTime1() throws Exception {
      Date dt = dateParser.parse("05/12/2005 15:00");
      Date retDt = operations.getSLADeadline("Claim-CutOff", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("08/12/2005 11:00"));
   }
   
   @Test
   public void testDeadLineDuration2CutOffTime2() throws Exception {
      Date dt = dateParser.parse("05/12/2005 16:20");
      Date retDt = operations.getSLADeadline("Claim-CutOff", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("08/12/2005 13:00"));
   }
   
   @Test
   public void testDeadLineDuration2CutOffTime3() throws Exception {
      Date dt = dateParser.parse("05/12/2005 17:20");
      Date retDt = operations.getSLADeadline("Claim-CutOff", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("08/12/2005 13:00"));
   }

   @Test
   public void testDeadLineDuration3CutOffTime1() throws Exception {
      Date dt = dateParser.parse("09/12/2005 15:00");
      Date retDt = operations.getSLADeadline("Claim-CutOff", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("13/12/2005 15:00"));
   }
   
   @Test
   public void testDeadLineDuration3CutOffTime2() throws Exception {
      Date dt = dateParser.parse("09/12/2005 16:20");
      Date retDt = operations.getSLADeadline("Claim-CutOff", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("13/12/2005 17:00"));
   }

   @Test
   public void testDeadLineDuration4CutOffTime1() throws Exception {
      Date dt = dateParser.parse("23/12/2005 15:00");
      Date retDt = operations.getSLADeadline("Claim-CutOff", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("30/12/2005 15:00"));
   }

   @Test
   public void testDeadLineDuration5CutOffTime1() throws Exception {
      Date dt = dateParser.parse("15/01/2007 13:20");
      Date retDt = operations.getSLADeadline("Staff-CutOff", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("15/01/2007 13:30"));
   }
   
   @Test
   public void testDeadLineDuration5CutOffTime2() throws Exception {
      Date dt = dateParser.parse("15/01/2007 16:20");
      Date retDt = operations.getSLADeadline("Staff-CutOff", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("16/01/2007 09:10"));
   }

   @Test
   public void testDeadLineDuration6CutOffTime1() throws Exception {
      Date dt = dateParser.parse("15/01/2007 13:20");
      Date retDt = operations.getSLADeadline("Renewal-CutOff", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("16/01/2007 13:20"));
   }
   
   @Test
   public void testDeadLineDuration6CutOffTime2() throws Exception {
      Date dt = dateParser.parse("15/01/2007 16:20");
      Date retDt = operations.getSLADeadline("Renewal-CutOff", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("17/01/2007 09:00"));
   }

   @Test
   public void testDeadLineDuration7CutOffTime() throws Exception {
      Date dt = dateParser.parse("15/01/2007 18:20");
      Date retDt = operations.getSLADeadline("Renewal-CutOff", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("17/01/2007 09:00"));
   }

}
