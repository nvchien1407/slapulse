//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service;

import java.util.Date;
import java.util.List;

import com.renewtek.dao.CalendarDateDAO;
import com.renewtek.model.CalendarDate;
import com.renewtek.model.CalendarDays;
import com.renewtek.model.DayModel;
import com.renewtek.model.Reference;

public interface CalendarDateManager extends Manager {

    /**
     * Setter for DAO, convenient for unit testing
     */
    public void setCalendarDateDAO(CalendarDateDAO calendarDateDAO);

    /**
     * Retrieves all of the calendarDates
     */
    public List<CalendarDate> getCalendarDates(CalendarDate calendarDate);

    /**
     * Gets calendarDate's information based on id.
     *
     * @param id the calendarDate's id
     * @return calendarDate populated calendarDate object
     */
    public CalendarDate getCalendarDate(final String id);

    /**
     * Saves a calendarDate's information
     *
     * @param calendarDate the object to be saved
     */
    public void saveCalendarDate(DayModel calendarDate);

    /**
     * Removes a calendarDate from the database by id
     *
     * @param id the calendarDate's id
     */
    public void removeCalendarDate(final String id);

    public CalendarDate getCalendarDate(final CalendarDate calendarDate);

    public List<String> getHours();

    public List<String> getMinutes();

    public DayModel getDayModel(String dayId, String dayType);

    public List<CalendarDate> getCalendarDate(Date date, Reference region);

    List<CalendarDate> getCalendarDateWithoutTimeZone(Date date, Reference region);

    public void saveCalendar(CalendarDays calendar);

    public List<DayModel> getDayModelByTemplate(final DayModel day);

    public List<Reference> getCalendars();

}

