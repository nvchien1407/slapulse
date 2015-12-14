//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.sla;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class SLAOperationsFixedDeadlineNewTest extends SLABaseTest {

//   private SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//
//   private SLAOperations operations = new SLAOperations();

   
   @Test
   public void testFixedDeadline55() throws Exception {
      Date dt = dateParser.parse("26/12/2005 11:00");
      Date retDt = operations.getSLADeadline("NCD", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("28/12/2005 17:00"));
   }

   
   @Test
   public void testFixedDeadline56() throws Exception {
      Date dt = dateParser.parse("20/12/2005 15:30");
      Date retDt = operations.getSLADeadline("NCD", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("20/12/2005 17:00"));
   }
   
   
   @Test
   public void testFixedDeadline57() throws Exception {
      Date dt = dateParser.parse("20/12/2005 17:00");
      Date retDt = operations.getSLADeadline("NCD", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("21/12/2005 17:00"));
   }
   
   
   @Test
   public void testFixedDeadline58() throws Exception {
      Date dt = dateParser.parse("20/12/2005 17:13");
      Date retDt = operations.getSLADeadline("NCD", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("21/12/2005 17:00"));
   }
   
   
   @Test
   public void testFixedDeadline59() throws Exception {
      Date dt = dateParser.parse("02/11/2009 11:13");
      Date retDt = operations.getSLADeadline("FixedDeadLineSpan", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("02/11/2009 17:30"));
   }
   
   
   @Test
   public void testFixedDeadline60() throws Exception {
      Date dt = dateParser.parse("02/11/2009 11:13");
      Date retDt = operations.getSLADeadline("FixedDeadLineSpan", "DEFAULT", dt, 0);
      assertTrue(dateParser.format(retDt).equals("02/11/2009 17:30"));
   }
}
