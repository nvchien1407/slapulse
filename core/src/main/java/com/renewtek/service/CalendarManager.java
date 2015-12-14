//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.renewtek.model.CalendarDays;
import com.renewtek.model.Reference;
import com.renewtek.service.SLACalendar;

public interface CalendarManager extends Manager {

    CalendarDays getCalendarDaysByMonth(Date dt, Locale locale, Reference region);

    CalendarDays getCalendarDaysByMonthWithoutTimeZone(Date dt, Locale locale, Reference region);

    CalendarDays getCalendarDates(Reference calendarName, Calendar cal, Calendar startDate, Calendar endDate);

    SLACalendar getSLACalendar(Reference region);

}
