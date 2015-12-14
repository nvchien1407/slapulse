//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import com.renewtek.Constants;
import com.renewtek.dao.CalendarDateDAO;
import com.renewtek.dao.ReferenceDAO;
import com.renewtek.model.CalendarDate;
import com.renewtek.model.CalendarDays;
import com.renewtek.model.DayModel;
import com.renewtek.model.DefaultWeekDay;
import com.renewtek.model.Reference;
import com.renewtek.service.CalendarDateManager;

public class CalendarDateManagerImpl extends BaseManager implements CalendarDateManager {
    private ReferenceDAO referenceDAO;
    
    public ReferenceDAO getReferenceDAO() {
      return referenceDAO;
   }

   public void setReferenceDAO(ReferenceDAO referenceDAO) {
      this.referenceDAO = referenceDAO;
   }

   protected CalendarDateDAO getCalendarDateDAO() {
       return (CalendarDateDAO) this.dao;
    }

    /**
     * Set the DAO for communication with the data layer.
     *
     * @param dao
     */
    public void setCalendarDateDAO(CalendarDateDAO dao) {
        this.dao = dao;
    }

    /**
     * @see com.renewtek.service.CalendarDateManager#getCalendarDates(com.renewtek.model.CalendarDate)
     */
    public List<CalendarDate> getCalendarDates(final CalendarDate calendarDate) {
        return getCalendarDateDAO().getCalendarDates(calendarDate);
    }

    public CalendarDate getCalendarDate(final CalendarDate calendarDate) {
        List<CalendarDate> dates = getCalendarDateDAO().getCalendarDates(calendarDate);
        if (dates != null && dates.size() > 0)
            return dates.get(0);
        return null;
    }

    /**
     * @see com.renewtek.service.CalendarDateManager#getCalendarDate(String id)
     */
    public CalendarDate getCalendarDate(final String id) {
        return getCalendarDateDAO().getCalendarDate(new Integer(id));
    }

    public void saveCalendarDate(DayModel calendarDate) {
        getCalendarDateDAO().saveCalendarDate(calendarDate);
    }

    /**
     * @see com.renewtek.service.CalendarDateManager#removeCalendarDate(String id)
     */
    public void removeCalendarDate(final String id) {
        getCalendarDateDAO().removeCalendarDate(new Integer(id));
    }

    public List<String> getHours() {
        List<String> hours = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                hours.add("0" + String.valueOf(i));
            } else {
                hours.add(String.valueOf(i));
            }
        }
        return hours;
    }

    public List<String> getMinutes() {
        int step = 10;
        List<String> minutes = new ArrayList<String>();
        for (int i = 0; i < 60; i += step) {
            minutes.add(String.valueOf(i));
        }
        return minutes;
    }

    public DayModel getDayModel(String dayId, String type) {
        return this.getCalendarDateDAO().getDay(Integer.valueOf(dayId), type);
    }

    public List<CalendarDate> getCalendarDate(Date date, Reference region) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Calendar cal2 = (Calendar) cal.clone();
        cal2.add(Calendar.DATE, 1);
        return getCalendarDateDAO().getCalendarDates(cal, cal2, region);
    }

    public List<CalendarDate> getCalendarDateWithoutTimeZone(Date date, Reference region) {
       Calendar cal = Calendar.getInstance();
       cal.setTime(date);
       Calendar cal2 = (Calendar) cal.clone();
       cal2.add(Calendar.DATE, 1);
       return getCalendarDateDAO().getCalendarDatesWithoutTimeZone(cal, cal2, region);
    }

    public void saveCalendar(CalendarDays calendar) {
        Reference cal = calendar.getRegion();
        cal.setDefaultWeekDays(new LinkedHashSet<DayModel>());
        if (cal.getId() == null) {
            DayModel day = new DayModel();
            day.setDefaultDay(true);
            day.setNonWorkingDay(false);
            day.setRegion(cal);
            cal.getDefaultWeekDays().add(day);
        }
        //for (Iterator it = calendar.getDefaultWeekDays().iterator(); it.hasNext();) {
        List<DefaultWeekDay> weekdays = calendar.getDefaultWeekDays();
        for(DefaultWeekDay weekDay : weekdays) {
            //DefaultWeekDay weekDay = (DefaultWeekDay) it.next();
            if (weekDay.getId() == null) {
                weekDay.setRegion(cal);
                cal.getDefaultWeekDays().add(weekDay);
            } else {
                dao.saveObject(weekDay);
            }
        }
        dao.saveObject(cal);
    }

    public List<DayModel> getDayModelByTemplate(DayModel day) {
        return getCalendarDateDAO().getDayModelByTemplate(day);
    }

    public List<Reference> getCalendars() {
       return getReferenceDAO().getReferences(Constants.REFERENCE_GROUPNAME_CALENDAR);
    }

}
