//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.renewtek.dao.CalendarDAO;
import com.renewtek.dao.CalendarDateDAO;
import com.renewtek.dao.DefaultWeekDAO;
import com.renewtek.model.CalendarDate;
import com.renewtek.model.CalendarDays;
import com.renewtek.model.DefaultWeekDay;
import com.renewtek.model.Reference;
import com.renewtek.model.WorkHourRange;
import com.renewtek.service.CalendarManager;
import com.renewtek.service.SLACalendar;
import com.renewtek.util.DateUtil;

public class CalendarManagerImpl extends BaseManager implements CalendarManager {
    private DefaultWeekDAO defaultWeekDAO;
    private CalendarDateDAO calendarDateDAO;

    protected CalendarDAO getCalendarDAO() {
      return (CalendarDAO) this.dao;
    }

    public void setCalendarDateDAO(CalendarDateDAO calendarDateDAO) {
        this.calendarDateDAO = calendarDateDAO;
    }

    public void setDefaultWeekDAO(DefaultWeekDAO defaultWeekDAO) {
        this.defaultWeekDAO = defaultWeekDAO;
    }

    public SLACalendar getSLACalendar(Reference calendarName) {
        List<DefaultWeekDay> weekDays = this.defaultWeekDAO.getDefaultWeek(calendarName).getWeekDays();
        List<CalendarDate> days = this.calendarDateDAO.getCalendarDates(calendarName);

        SLACalendar calendar = new SLACalendarImpl();
        calendar.setCalendarName(calendarName.getItemName());
        calendar.setDefaultWeek(weekDays);
        calendar.setCalendarDays(days);

        return calendar;
    }

    public CalendarDays getCalendarDaysByMonth(Date dt, Locale locale, Reference region) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        Calendar startDate = (Calendar) cal.clone();
        startDate.set(Calendar.DATE, 1);
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.MONTH, 1);

        return getCalendarDates(region, cal, startDate, endDate);
    }

    public CalendarDays getCalendarDaysByMonthWithoutTimeZone(Date dt, Locale locale, Reference region) {
       Calendar cal = Calendar.getInstance();
       cal.setTime(dt);
       cal.setFirstDayOfWeek(Calendar.MONDAY);
       Calendar startDate = (Calendar) cal.clone();
       startDate.set(Calendar.DATE, 1);
       Calendar endDate = (Calendar) startDate.clone();
       endDate.add(Calendar.MONTH, 1);

       return getCalendarDatesToShowInWebappOrCalculateInComponent(region, cal, startDate, endDate, true);
   }

    public CalendarDays getCalendarDates(Reference calendarName, Calendar cal, Calendar startDate, Calendar endDate) {
       return this.getCalendarDatesToShowInWebappOrCalculateInComponent(calendarName, cal, startDate, endDate, false);
    }

    private CalendarDays getCalendarDatesToShowInWebappOrCalculateInComponent(Reference calendarName, Calendar cal, Calendar startDate, Calendar endDate, boolean calledFromWebapp) {
        CalendarDays calDays = getCalendarDAO().getCalendarDays(cal, calendarName);
        List<DefaultWeekDay> weekDefaults = this.defaultWeekDAO.getDefaultWeek(calendarName).getWeekDays();
        calDays.setDefaultWeekDays(weekDefaults);
        List<CalendarDate> orangeDays;
        if (calledFromWebapp) {
           orangeDays = this.calendarDateDAO.getCalendarDatesWithoutTimeZone(startDate, endDate, calendarName);
        } else {
           orangeDays = this.calendarDateDAO.getCalendarDates(startDate, endDate, calendarName);
        }

        String timezone = (calendarName != null) ? calendarName.getTimezone() : null;
        List<CalendarDate> days = applyDateSettings(calDays.getCalendar(), weekDefaults, orangeDays, timezone, calledFromWebapp);
        calDays.setDays(days);
        calDays.setExceptionalDays(orangeDays);
        return calDays;
    }

    public List<CalendarDate> applyDateSettings(Calendar calendar, List<DefaultWeekDay> week, List<CalendarDate> orangeDays, String timezone, boolean calledFromWebapp) {
        ArrayList<CalendarDate> list = new ArrayList<CalendarDate>();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        Calendar cal = (Calendar) calendar.clone();
        Calendar maxCal = (Calendar) calendar.clone();
        cal.set(Calendar.DATE, 1);
        maxCal.add(Calendar.MONTH, 1);
        maxCal.set(Calendar.DATE, 1);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int emptyDays = firstDayOfWeek - calendar.getFirstDayOfWeek();
        if (emptyDays < 0)
            emptyDays = 7 + emptyDays;
        for (int i = 0; i < emptyDays; i++) {
            list.add(null);
        }
        while (cal.before(maxCal)) {
            Date dt = cal.getTime();
            CalendarDate cd = null;

            if (orangeDays != null) {
                boolean found = false;
               for (CalendarDate orangeDay : orangeDays) {
                  cd = orangeDay;
                  if (DateUtil.getDateInt(cd.getDateInfo()) == DateUtil.getDateInt(dt)) {
                     found = true;
                     break;
                  }
               }
                //no special dates found for this date. Then apply default values
                DefaultWeekDay weekDay = this.defaultWeekDAO.getDefaultDay(dt, week);
                if (!found) {
                    cd = new CalendarDate();
                    cd.setDateInfo(dt);
                    if (weekDay != null && weekDay.getId() != null) {
                        cd.setNonWorkingDay(weekDay.getNonWorkingDay());
                    }
                    setWorkHourRanges(cd, weekDay, timezone, calledFromWebapp);
                } else {
                    if (!cd.getNonWorkingDay()) {//special day
                       setWorkHourRangesForSpecialDay(cd, timezone, calledFromWebapp);
//                        setWorkHourRanges(cd, weekDay, timezone);
                    }
                }
            } else {
                cd = new CalendarDate();
                cd.setDateInfo(dt);
                DefaultWeekDay weekDay = this.defaultWeekDAO.getDefaultDay(dt, week);
                if (weekDay != null && weekDay.getId() != null) {
                    cd.setNonWorkingDay(weekDay.getNonWorkingDay());
                }
                setWorkHourRanges(cd, weekDay, timezone, calledFromWebapp);
            }
            list.add(cd);
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
        }
        return list;
    }

    private void setWorkHourRangesForSpecialDay(CalendarDate dt, String timezone, boolean calledFromWebapp) {
       Set<WorkHourRange> workHourRanges = dt.getWorkHourRanges();
       //dt.setWorkHourRanges(new LinkedHashSet<WorkHourRange>());

       for (WorkHourRange range : workHourRanges) {
          //WorkHourRange range1 = new WorkHourRange();
          //range1.setDay(dt);
          
          Date fromTimeConverted = range.getFromTime();
          Date toTimeConverted = range.getToTime();
          
          if(!dt.getTimezoneConverted() && !calledFromWebapp) {
             dt.setTimezoneConverted(true);
             //fix DE2522
             fromTimeConverted = DateUtil.convertLocalTimeWithSpecifiedTimeZoneToServerTime4WorkHourRange(fromTimeConverted, timezone);
             toTimeConverted = DateUtil.convertLocalTimeWithSpecifiedTimeZoneToServerTime4WorkHourRange(toTimeConverted, timezone);
          }

          range.setFromTime(fromTimeConverted);
          range.setToTime(toTimeConverted);
          //dt.getWorkHourRanges().add(range1);
       }
    }

    private void setWorkHourRanges(CalendarDate dt, DefaultWeekDay day, String timezone, boolean calledFromWebapp) {
       if (day.getWorkHourRanges() == null) {
          return;
       }
       dt.setWorkHourRanges(new LinkedHashSet<WorkHourRange>());
       for (WorkHourRange range : day.getWorkHourRanges()) {
          WorkHourRange range1 = new WorkHourRange();
          range1.setDay(dt);
          
          Date fromTimeConverted = range.getFromTime();
          Date toTimeConverted = range.getToTime();
          
          if(!dt.getTimezoneConverted() && !calledFromWebapp) {
             dt.setTimezoneConverted(true);
             //fix DE2522
             fromTimeConverted = DateUtil.convertLocalTimeWithSpecifiedTimeZoneToServerTime4WorkHourRange(fromTimeConverted, timezone);
             toTimeConverted = DateUtil.convertLocalTimeWithSpecifiedTimeZoneToServerTime4WorkHourRange(toTimeConverted, timezone);
          }

          range1.setFromTime(fromTimeConverted);
          range1.setToTime(toTimeConverted);
          dt.getWorkHourRanges().add(range1);
       }
    }

    public void setCalendarDAO(CalendarDAO calendarDAO) {
        this.dao = calendarDAO;
    }

}
