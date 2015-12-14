package com.renewtek.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jmock.Mock;

import com.renewtek.dao.CalendarDateDAO;
import com.renewtek.model.CalendarDate;
import com.renewtek.model.CalendarDays;
import com.renewtek.model.DayModel;
import com.renewtek.model.DefaultWeekDay;
import com.renewtek.model.Reference;
import com.renewtek.service.impl.CalendarDateManagerImpl;

public class CalendarDateManagerTest extends BaseManagerTestCase {

   private CalendarDateManager objectUnderTest;

   private Mock mockCalendarDateDAO;

   protected void setUp() throws Exception {
      objectUnderTest = new CalendarDateManagerImpl();
      mockCalendarDateDAO = new Mock(CalendarDateDAO.class);

      objectUnderTest.setCalendarDateDAO((CalendarDateDAO) mockCalendarDateDAO.proxy());
   }

   public void testGetCalendarDates() {
      CalendarDate date = new CalendarDate();
      List<CalendarDate> results = new ArrayList<CalendarDate>();
      CalendarDate result1 = new CalendarDate();
      CalendarDate result2 = new CalendarDate();
      results.add(result1);
      results.add(result2);

      mockCalendarDateDAO.expects(once()).method("getCalendarDates").with(same(date)).will(returnValue(results));
      objectUnderTest.getCalendarDates(date);

      mockCalendarDateDAO.verify();
   }

   public void testGetCalendarDate() {
      CalendarDate date = new CalendarDate();
      List<CalendarDate> result = new ArrayList<CalendarDate>();
      CalendarDate result1 = new CalendarDate();
      CalendarDate result2 = new CalendarDate();
      result.add(result1);
      result.add(result2);
      CalendarDate actual;

      mockCalendarDateDAO.expects(once()).method("getCalendarDates").with(same(date)).will(returnValue(result));
      actual = objectUnderTest.getCalendarDate(date);

      mockCalendarDateDAO.verify();
      assertNotNull(actual);
      assertSame(result1, actual);

      mockCalendarDateDAO.reset();
      mockCalendarDateDAO.expects(once()).method("getCalendarDates").with(same(date)).will(returnValue(null));
      actual = objectUnderTest.getCalendarDate(date);
      assertNull(actual);
   }

   public void testGetCalendarDateUsingId() {
      CalendarDate date = new CalendarDate();

      mockCalendarDateDAO.expects(once()).method("getCalendarDate").with(eq(1234)).will(returnValue(date));
      CalendarDate actual = objectUnderTest.getCalendarDate("1234");

      mockCalendarDateDAO.verify();
      assertSame(date, actual);
   }

   public void testSaveCalendarDate() {
      DayModel calendar = new DayModel();

      mockCalendarDateDAO.expects(once()).method("saveCalendarDate").with(same(calendar)).isVoid();

      objectUnderTest.saveCalendarDate(calendar);

      mockCalendarDateDAO.verify();
   }

   public void testRemoveCalendarDate() {
      mockCalendarDateDAO.expects(once()).method("removeCalendarDate").with(eq(1234)).isVoid();

      objectUnderTest.removeCalendarDate("1234");

      mockCalendarDateDAO.verify();
   }

   public void testGetHours() {
      List<String> actual = objectUnderTest.getHours();

      assertEquals("01", actual.get(1));
      assertEquals("02", actual.get(2));
      assertEquals("03", actual.get(3));
      assertEquals("04", actual.get(4));
      assertEquals("05", actual.get(5));
      assertEquals("11", actual.get(11));
      assertEquals("12", actual.get(12));
      assertEquals("13", actual.get(13));
      assertEquals("14", actual.get(14));
      assertEquals("15", actual.get(15));
      assertEquals("23", actual.get(23));
   }

   public void testGetMinutes() {
      List<String> actual = objectUnderTest.getMinutes();

      assertTrue(actual.size() == 6);
      assertEquals("0", actual.get(0));
      assertEquals("10", actual.get(1));
      assertEquals("20", actual.get(2));
      assertEquals("30", actual.get(3));
      assertEquals("40", actual.get(4));
      assertEquals("50", actual.get(5));
   }

   public void testGetDayModel() {
      String dayId = "1234";
      String dayType = "Type1";

      mockCalendarDateDAO.expects(once()).method("getDay").with(eq(1234), eq(dayType))
            .will(returnValue(new DayModel()));

      DayModel actual = objectUnderTest.getDayModel(dayId, dayType);

      mockCalendarDateDAO.verify();
      assertNotNull(actual);
   }

   public void testGetCalendarDateUsingRegion() throws Exception {
      Date date = new Date();
      Reference region = new Reference();

      List<CalendarDate> result = new ArrayList<CalendarDate>();

      mockCalendarDateDAO.expects(once()).method("getCalendarDates")
            .with(isA(Calendar.class), isA(Calendar.class), same(region)).will(returnValue(result));

      List<CalendarDate> actual = objectUnderTest.getCalendarDate(date, region);

      mockCalendarDateDAO.verify();
      mockCalendarDateDAO.verify();

      assertNotNull(actual);
      assertSame(result, actual);
   }

   public void testGetCalendarDateWithoutTimeZone() {
      Date date = new Date();
      Reference region = new Reference();

      List<CalendarDate> result = new ArrayList<CalendarDate>();

      mockCalendarDateDAO.expects(once()).method("getCalendarDatesWithoutTimeZone")
            .with(isA(Calendar.class), isA(Calendar.class), same(region)).will(returnValue(result));

      List<CalendarDate> actual = objectUnderTest.getCalendarDateWithoutTimeZone(date, region);

      mockCalendarDateDAO.verify();
      mockCalendarDateDAO.verify();

      assertNotNull(actual);
      assertSame(result, actual);
   }

   public void testSaveCalendar() {
      CalendarDays calendars = new CalendarDays(Calendar.getInstance());

      List<DefaultWeekDay> days = new ArrayList<DefaultWeekDay>();
      DefaultWeekDay day;
      day = new DefaultWeekDay();
      days.add(day);

      Reference region = new Reference();

      calendars.setDefaultWeekDays(days);
      calendars.setRegion(region);

      // mockCalendarDateDAO.expects(once()).method("saveObject").with(isA(DefaultWeekDay.class)).isVoid();
      mockCalendarDateDAO.expects(once()).method("saveObject").with(isA(Reference.class)).isVoid();

      objectUnderTest.saveCalendar(calendars);

      mockCalendarDateDAO.verify();

      mockCalendarDateDAO.reset();
      day.setId(1234);

      mockCalendarDateDAO.expects(once()).method("saveObject").with(isA(DefaultWeekDay.class)).isVoid();
      mockCalendarDateDAO.expects(once()).method("saveObject").with(isA(Reference.class)).isVoid();

      objectUnderTest.saveCalendar(calendars);

      mockCalendarDateDAO.verify();
   }

   public void testGetDayModelByTemplate() {
      DayModel dayModel = new DayModel();
      
      List<DayModel> result = new ArrayList<DayModel>();
      result.add(new DayModel());
      mockCalendarDateDAO.expects(once()).method("getDayModelByTemplate").with(same(dayModel)).will(returnValue(result));
      
      List<DayModel> actual = objectUnderTest.getDayModelByTemplate(dayModel );
      
      mockCalendarDateDAO.verify();
      assertNotNull(actual);
      assertSame(result, actual);
      
   }

}
