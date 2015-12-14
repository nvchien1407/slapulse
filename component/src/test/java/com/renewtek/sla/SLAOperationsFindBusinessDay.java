package com.renewtek.sla;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

public class SLAOperationsFindBusinessDay extends SLABaseTest {

//   private SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//
//   private SLAOperations operations = new SLAOperations();
   
   
   @Test
   public void testFindBusinessDayNewYearDay() throws ParseException {
      Date dt = dateParser.parse("01/01/2006 15:00");
      Date resultDate = operations.findBusinessDay(dt, "Victoria");
      
      assertTrue(dateParser.format(resultDate).equals("03/01/2006 15:00"));
   }

   
   @Test
   public void testFindBusinessDayNormalDay() throws ParseException {
      Date dt = dateParser.parse("03/01/2006 15:00");
      Date resultDate = operations.findBusinessDay(dt, "Victoria");

      assertTrue(dateParser.format(resultDate).equals("03/01/2006 15:00"));
   }

   
   @Test
   public void testFindBusinessDayWeekend() throws ParseException {
      Date dt = dateParser.parse("14/01/2006 15:00");
      Date resultDate = operations.findBusinessDay(dt, "Victoria");

      assertTrue(dateParser.format(resultDate).equals("16/01/2006 15:00"));
   }
}
