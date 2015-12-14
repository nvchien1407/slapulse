//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.sla;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class SLAOperationsFixedDeadlineSpanningTest extends SLABaseTest {

//   private SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//
//   private SLAOperations operations = new SLAOperations();

   
   @Test
   public void testFixedDeadlineSpanning1() throws Exception {
      Date dt = dateParser.parse("18/12/2009 14:00");
      Date retDt = operations.getSLADeadline("FixedDeadLineSpan", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("18/12/2009 17:30"));
   }
   
   
   @Test
   public void testFixedDeadlineSpanning2() throws Exception {
      Date dt = dateParser.parse("18/12/2009 15:30");
      Date retDt = operations.getSLADeadline("FixedDeadLineSpan", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("18/12/2009 17:30"));
   }
   
   
   @Test
   public void testFixedDeadlineSpanning3() throws Exception {
      Date dt = dateParser.parse("18/12/2009 15:37");
      Date retDt = operations.getSLADeadline("FixedDeadLineSpan", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("22/12/2009 17:30"));
   }
   
   
   @Test
   public void testFixedDeadlineSpanning4() throws Exception {
      Date dt = dateParser.parse("09/12/2009 13:20");
      Date retDt = operations.getSLADeadline("FixedDeadLineSpan", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("09/12/2009 17:30"));
   }
   
   
   @Test
   public void testFixedDeadlineSpanning5() throws Exception {
      Date dt = dateParser.parse("09/12/2009 15:30");
      Date retDt = operations.getSLADeadline("FixedDeadLineSpan", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("09/12/2009 17:30"));
   }
   
   
   @Test
   public void testFixedDeadlineSpanning6() throws Exception {
      Date dt = dateParser.parse("09/12/2009 15:43");
      Date retDt = operations.getSLADeadline("FixedDeadLineSpan", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("10/12/2009 17:30"));
   }
   
   
   @Test
   public void testFixedDeadlineSpanning7() throws Exception {
      Date dt = dateParser.parse("13/12/2009 12:00");
      Date retDt = operations.getSLADeadline("FixedDeadLineSpan", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("14/12/2009 17:30"));
   }
   
   
   @Test
   public void testFixedDeadlineSpanning71() throws Exception {
      Date dt = dateParser.parse("13/12/2009 18:00");
      Date retDt = operations.getSLADeadline("FixedDeadLineSpan", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("14/12/2009 17:30"));
   }
   
   
   @Test
   public void testFixedDeadlineSpanning8() throws Exception {
      Date dt = dateParser.parse("12/12/2009 12:40");
      Date retDt = operations.getSLADeadline("FixedDeadLineSpan", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("14/12/2009 17:30"));
   }
   
   
   @Test
   public void testFixedDeadlineSpanning9() throws Exception {
      Date dt = dateParser.parse("21/12/2009 12:40");
      Date retDt = operations.getSLADeadline("FixedDeadLineSpan", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("22/12/2009 17:30"));
   }
   
   
   @Test
   public void testFixedDeadlineSpanning91() throws Exception {
      Date dt = dateParser.parse("21/12/2009 19:40");
      Date retDt = operations.getSLADeadline("FixedDeadLineSpan", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("22/12/2009 17:30"));
   }
   
   
   @Test
   public void testFixedDeadlineSpanning10() throws Exception {
      Date dt = dateParser.parse("20/12/2009 12:40");
      Date retDt = operations.getSLADeadline("FixedDeadLineSpan", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("22/12/2009 17:30"));
   }
}
