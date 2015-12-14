package com.renewtek.dao;

import java.util.Calendar;
import java.util.List;

import com.renewtek.model.CalendarDate;
import com.renewtek.model.DayModel;
import com.renewtek.model.Reference;

public class CalendarDateDaoTest extends BaseDAOTestCase {

   private CalendarDateDAO calendarDateDAO = null;

   private ReferenceDAO referenceDAO = null;

   public CalendarDateDaoTest() {
      referenceDAO = (ReferenceDAO)ctx.getBean("referenceDAO");
      calendarDateDAO = (CalendarDateDAO)ctx.getBean("calendarDateDAO");
   }

   public void testgetDayModel() throws Exception {
      // setup
      List<Reference> regions = referenceDAO.getReferences("Calendar");
      Reference region = regions.get(0);
      List<CalendarDate> calendarDates = calendarDateDAO.getCalendarDates(region);
      CalendarDate aCalendarDate = calendarDates.get(0);
      // test
      DayModel day = this.calendarDateDAO.getDay(aCalendarDate.getId(), "CalendarDate");
      String name = ((CalendarDate) day).getName();
      assertNotNull(name);
      assertTrue(true);
   }
   
   public void testFindAllCalendars() {
      CalendarDate calendarDate = null;
      List<CalendarDate> calendarDates = calendarDateDAO.getCalendarDates(calendarDate);
      assertEquals(18, calendarDates.size());
   }
   
   public void testFindAllCalendarsByName() {
      CalendarDate calendarDate = new CalendarDate();
      calendarDate.setName("Christmas Eve");
      List<CalendarDate> calendarDates = calendarDateDAO.getCalendarDates(calendarDate);
      assertNotNull(calendarDates);
      assertEquals(1, calendarDates.size());
   }
   
   public void testFindAllCalendarById() {
      CalendarDate calendarDate = calendarDateDAO.getCalendarDate(9);
      assertNotNull(calendarDate);
      assertEquals(9, calendarDate.getId().intValue());
   }

   public void testCalendarDates() throws Exception {
      Calendar cal = Calendar.getInstance();
      cal.set(Calendar.DATE, 40);
      assertTrue(true);
   }
}
