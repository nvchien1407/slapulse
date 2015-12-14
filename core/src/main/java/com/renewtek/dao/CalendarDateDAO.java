//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.dao;

import java.util.Calendar;
import java.util.List;

import com.renewtek.model.CalendarDate;
import com.renewtek.model.DayModel;
import com.renewtek.model.Reference;

public interface CalendarDateDAO extends DAO {

    /**
     * Retrieves all of the calendarDates
     */
    public List<CalendarDate> getCalendarDates(CalendarDate calendarDate);

    /**
     * Gets calendarDate's information based on primary key. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the calendarDate's id
     * @return calendarDate populated calendarDate object
     */
    public CalendarDate getCalendarDate(final Integer id);

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
    public void removeCalendarDate(final Integer id);

    public List<CalendarDate> getCalendarDates(final Calendar startDate, final Calendar endDate, final Reference region);

    public List<CalendarDate> getCalendarDates(final Reference region);

    List<CalendarDate> getCalendarDatesWithoutTimeZone(final Calendar startDate, final Calendar endDate, final Reference region);

    public DayModel getDay(Integer dayId, String type);

    public List<DayModel> getDayModelByTemplate(DayModel day);
}

