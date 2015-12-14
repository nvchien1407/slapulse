//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao.hibernate;

import java.util.Calendar;

import com.renewtek.dao.CalendarDAO;
import com.renewtek.model.CalendarDays;
import com.renewtek.model.Reference;

public class CalendarDAOHibernate extends BaseDAOHibernate implements CalendarDAO {

   public CalendarDays getCalendarDays(Calendar cal, Reference calendarName) {
      CalendarDays days = new CalendarDays(cal);
      days.setRegion(calendarName);
      return days;
   }
}
