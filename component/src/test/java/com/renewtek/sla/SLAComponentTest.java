// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.sla;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;


import com.renewtek.service.SLACalendar;
import com.renewtek.sla.operations.SLAComponent;

public class SLAComponentTest extends SLABaseTest {

   private SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");

   SLAComponent component = new SLAComponent();
   
   @Test
   public void testSLACalendar() throws Exception {
      Date dt = dateParser.parse("21/12/2005 15:00");
      SLACalendar calendar = component.getSLACalendar("NewBusiness", "Motor");
      calendar.setTime(dt);
      assertTrue(!calendar.isNonBusinessDay());
      for (int i = 0; i < 5; i++) {
         calendar.add(Calendar.DATE, 1);
      }
      assertTrue(calendar.isNonBusinessDay());

      calendar = component.getSLACalendar("Victoria");
      assertNotNull(calendar);
   }
   
   @Test
   public void testSLACalendarFields() throws Exception {
      Date dt = dateParser.parse("20/12/2005 15:00");
      SLACalendar calendar = component.getSLACalendar("NewBusiness", "Motor");
      calendar.setTime(dt);
      assertTrue(!calendar.isNonBusinessDay());
      for (int i = 0; i < 4; i++) {
         assertEquals(9, calendar.get(SLACalendar.START_OF_BUSINESS_HOUR));
         calendar.add(Calendar.DATE, 1);
      }
   }
   
   @Test
   public void testWorkHourRange1() throws Exception {
      Date dt = dateParser.parse("5/12/2005 09:22");
      int[] range = component.getWorkingHourRange("NewBusiness", "Motor", dt);
      
      assertEquals(9, range[0]);
      assertEquals(0, range[1]);
      assertEquals(17, range[2]);
      assertEquals(0, range[3]);
   }
   
   @Test
   public void testWorkHourRange1A1() throws Exception {
      Date dt = dateParser.parse("5/12/2005 00:00");
      int[] range = component.getWorkingHourRange("NewBusiness", "Motor", dt);
      
      assertEquals(9, range[0]);
      assertEquals(0, range[1]);
      assertEquals(17, range[2]);
      assertEquals(0, range[3]);
   }
   
   @Test
   public void testWorkHourRange2() throws Exception {
      // weekend saturday (non working day)
      Date dt = dateParser.parse("3/12/2005 00:00");
      int[] range = component.getWorkingHourRange("NewBusiness", "Motor", dt);
      
      assertEquals(0, range[0]);
      assertEquals(0, range[1]);
      assertEquals(0, range[2]);
      assertEquals(0, range[3]);
   }
   
   @Test
   public void testWorkHourRange3() throws Exception {
      // special day
      Date dt = dateParser.parse("23/12/2005 00:00");
      int[] range = component.getWorkingHourRange("NewBusiness", "Motor", dt);
      
      assertEquals(9, range[0]);
      assertEquals(0, range[1]);
      assertEquals(13, range[2]);
      assertEquals(0, range[3]);
   }
   
   @Test
   public void testWorkHourRange4() throws Exception {
      // holiday day
      Date dt = dateParser.parse("26/12/2005 00:00");
      int[] range = component.getWorkingHourRange("NewBusiness", "Motor", dt);
      
      assertEquals(0, range[0]);
      assertEquals(0, range[1]);
      assertEquals(0, range[2]);
      assertEquals(0, range[3]);
   }
   
   @Test
   public void testWorkHourRange5() throws Exception {
      Date dt = dateParser.parse("1/12/2009 00:00");
      int[] range = component.getWorkingHourRange("DailyMovieBook", "HouseHold", dt);
      
      assertEquals(14, range[0]);
      assertEquals(0, range[1]);
      assertEquals(9, range[2]);
      assertEquals(0, range[3]);
   }
   
   @Test
   public void testWorkHourRange6() throws Exception {
      Date dt = dateParser.parse("7/12/2009 00:00");
      int[] range = component.getWorkingHourRange("DailyMovieBook", "HouseHold", dt);
      
      assertEquals(15, range[0]);
      assertEquals(30, range[1]);
      assertEquals(7, range[2]);
      assertEquals(0, range[3]);
   }
}
