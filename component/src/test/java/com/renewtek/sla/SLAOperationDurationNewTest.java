//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.sla;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class SLAOperationDurationNewTest extends SLABaseTest {

//   private SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//   private SLAOperations operations = new SLAOperations();
   
   @Test
   public void testDeadLineDuration41() throws Exception {
      Date dt = dateParser.parse("05/12/2005 17:30");
      Date retDt = operations.getSLADeadline("Claim", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("08/12/2005 13:00"));
   }
   
   @Test
   public void testDeadLineDuration42() throws Exception {
      Date dt = dateParser.parse("05/12/2005 6:30");
      Date retDt = operations.getSLADeadline("Claim", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("07/12/2005 13:00"));
   }
   
   @Test
   public void testDeadLineDuration43() throws Exception {
      Date dt = dateParser.parse("11/12/2005 19:30");
      Date retDt = operations.getSLADeadline("Claim", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("14/12/2005 13:00"));
   }
   
   @Test
   public void testDeadLineDuration51() throws Exception {
      Date dt = dateParser.parse("15/01/2007 17:00");
      Date retDt = operations.getSLADeadline("Staff", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("16/01/2007 09:00"));
   }  
   
   @Test
   public void testDeadLineDuration52() throws Exception {
      Date dt = dateParser.parse("15/01/2007 06:25");
      Date retDt = operations.getSLADeadline("Staff", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("15/01/2007 09:10"));
   }  
   
   @Test
   public void testDeadLineDuration53() throws Exception {
      Date dt = dateParser.parse("15/01/2007 23:57");
      Date retDt = operations.getSLADeadline("Staff", null, dt, 0);
      //System.out.println("RESULT = " + retDt);
      assertTrue(dateParser.format(retDt).equals("16/01/2007 09:10"));
   }
   
   @Test
   public void testDeadLineDuration54() throws Exception {
      Date dt = dateParser.parse("15/01/2007 17:04");
      Date retDt = operations.getSLADeadline("Staff", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("16/01/2007 09:10"));
   }
   
   @Test
   public void testDeadLineDuration55() throws Exception {
      Date dt = dateParser.parse("10/12/2005 12:04");
      Date retDt = operations.getSLADeadline("Staff", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("12/12/2005 09:10"));
   }
   
   @Test
   public void testDeadLineDuration56() throws Exception {
      Date dt = dateParser.parse("26/01/2006 12:04");
      Date retDt = operations.getSLADeadline("Staff", null, dt, 0);
      //System.out.println("RESULT = " + retDt);
      assertTrue(dateParser.format(retDt).equals("27/01/2006 09:10"));
   }
   
//   public void testDeadLineDuration57() throws Exception {
//      Date dt = dateParser.parse("30/07/2005 12:04");
//      Date retDt = operations.getSLADeadline("Staff", null, dt, 0);
//      assertTrue(dateParser.format(retDt).equals("01/08/2005 09:10"));
//   }
   
   @Test
   public void testDeadLineDuration57() throws Exception {
      Date dt = dateParser.parse("26/12/2005 12:04");
      Date retDt = operations.getSLADeadline("Staff", null, dt, 0);
      assertTrue(dateParser.format(retDt).equals("28/12/2005 09:10"));
   }
   
   @Test
   public void testDeadLineDuration66() throws Exception {
      Date dt = dateParser.parse("26/12/2005 13:20");
      Date retDt = operations.getSLADeadline("Renewal", "DEFAULT", dt, 0);
      //System.out.println("RESULR = " + retDt);
      assertTrue(dateParser.format(retDt).equals("29/12/2005 09:00"));
   }
   
   @Test
   public void testDeadLineDuration71() throws Exception {
      Date dt = dateParser.parse("15/01/2007 18:20");
      Date retDt = operations.getSLADeadline("Claim", "HouseHold", dt, 0);
      assertTrue(dateParser.format(retDt).equals("17/01/2007 11:00"));
   }
   
   @Test
   public void testDeadLineDuration72() throws Exception {
      Date dt = dateParser.parse("15/01/2007 05:20");
      Date retDt = operations.getSLADeadline("Claim", "HouseHold", dt, 0);
      assertTrue(dateParser.format(retDt).equals("16/01/2007 11:00"));
   }
   
   /////////////
   
   @Test
   public void testDeadLineDuration221() throws Exception {
      Date dt = dateParser.parse("05/12/2005 15:00");
      Date retDt = operations.getSLAStartTime("Claim", null, dt);
      assertTrue(dateParser.format(retDt).equals("05/12/2005 15:00"));
   }
   
   @Test
   public void testDeadLineDuration331() throws Exception {
      Date dt = dateParser.parse("09/12/2005 15:00");
      Date retDt = operations.getSLAStartTime("Claim", null, dt);
      //System.out.println("RESULT = " + retDt);
      assertTrue(dateParser.format(retDt).equals("09/12/2005 15:00"));
   }
   
   @Test
   public void testDeadLineDuration441() throws Exception {
      Date dt = dateParser.parse("23/12/2005 15:00");
      Date retDt = operations.getSLAStartTime("Claim", null, dt);
      assertTrue(dateParser.format(retDt).equals("23/12/2005 15:00"));
   }
   
   @Test
   public void testDeadLineDuration431() throws Exception {
      Date dt = dateParser.parse("11/12/2005 19:30");
      Date retDt = operations.getSLAStartTime("Claim", null, dt);
      //System.out.println("START DATE = " + retDt);
      assertTrue(dateParser.format(retDt).equals("11/12/2005 19:30"));
   }
   
   @Test
   public void testDeadLineDuration10101() throws Exception {
      Date dt = dateParser.parse("27/12/2005 15:00");
      Date retDt = operations.getSLAStartTime("Claim", null, dt);
      assertTrue(dateParser.format(retDt).equals("27/12/2005 15:00"));
   }
}
