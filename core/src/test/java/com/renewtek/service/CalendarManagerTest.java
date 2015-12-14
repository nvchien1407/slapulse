package com.renewtek.service;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Mock;

import com.renewtek.dao.CalendarDAO;
import com.renewtek.dao.CalendarDateDAO;
import com.renewtek.dao.DefaultWeekDAO;
import com.renewtek.model.CalendarDate;
import com.renewtek.model.DefaultWeek;
import com.renewtek.model.DefaultWeekDay;
import com.renewtek.model.Reference;
import com.renewtek.service.impl.CalendarManagerImpl;

public class CalendarManagerTest extends BaseManagerTestCase {
   private Mock mockCalendarDAO;
   private Mock mockDefaultWeekDAO;
   private Mock mockCalendarDateDAO;
   
   private CalendarManagerImpl objectUnderTest; 
   
   
   protected void setUp() throws Exception {
      objectUnderTest = new CalendarManagerImpl();
      
      mockCalendarDAO = new Mock(CalendarDAO.class);
      mockDefaultWeekDAO = new Mock(DefaultWeekDAO.class);
      mockCalendarDateDAO = new Mock(CalendarDateDAO.class);
      
      objectUnderTest.setCalendarDAO((CalendarDAO) mockCalendarDAO.proxy());
      objectUnderTest.setCalendarDateDAO((CalendarDateDAO) mockCalendarDateDAO.proxy());
      objectUnderTest.setDefaultWeekDAO((DefaultWeekDAO) mockDefaultWeekDAO.proxy());
   }
   
   public void testGetSLACalendar() throws Exception {
      Reference calendarName = new Reference();
      List<DefaultWeekDay> weekDays = new ArrayList<DefaultWeekDay>();
      List<CalendarDate> calendarDates = new ArrayList<CalendarDate>();
      
      DefaultWeek defaultWeek = new DefaultWeek();
      defaultWeek.setWeekDays(weekDays);
      
      mockDefaultWeekDAO.expects(once()).method("getDefaultWeek").with(same(calendarName)).will(returnValue(defaultWeek));
      
      mockCalendarDateDAO.expects(once()).method("getCalendarDates").with(same(calendarName)).will(returnValue(calendarDates));
      
      calendarName.setItemName("ABCD");
      
      SLACalendar actual = objectUnderTest.getSLACalendar(calendarName);
      
      mockDefaultWeekDAO.verify();
      mockCalendarDateDAO.verify();
      
      assertEquals("ABCD", actual.getCalendarName());
      
      
   }
   
//   public void testGetCalendarDaysByMonth() {
//      Date date = new Date();
//      Locale locale = new Locale("en_US");
//      Reference region = new Reference();
//      Calendar calendar = Calendar.getInstance();
//      CalendarDays calendarDays = new CalendarDays(calendar);
//      DefaultWeek defaultWeek = new DefaultWeek();
//      List<CalendarDate> calendarDates = new ArrayList<CalendarDate>();
//      
//      List<DefaultWeekDay> defaultWeekDays = new ArrayList<DefaultWeekDay>();
//      
//      mockCalendarDAO.expects(once()).method("getCalendarDays").with(isA(Calendar.class), isA(Reference.class)).will(returnValue(calendarDays));
//      
//      mockDefaultWeekDAO.expects(once()).method("getDefaultWeek").with(isA(Reference.class)).will(returnValue(defaultWeek));
//      
//      mockCalendarDateDAO.expects(once()).method("getCalendarDates").with(isA(Calendar.class), isA(Calendar.class),isA(Reference.class)).will(returnValue(calendarDates));
//      
//      CalendarDays actual = objectUnderTest.getCalendarDaysByMonth(date, locale, region);
//      
//      
//   }
   
}
