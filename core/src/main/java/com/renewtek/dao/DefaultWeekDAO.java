//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.dao;

import java.util.Date;
import java.util.List;

import com.renewtek.model.DayModel;
import com.renewtek.model.DefaultWeek;
import com.renewtek.model.DefaultWeekDay;
import com.renewtek.model.Reference;

public interface DefaultWeekDAO extends DAO {

    /**
     * Retrieves all of the defaultWeeks
     */
    public DefaultWeek getDefaultWeek(Reference region);

    /**
     * Gets defaultWeek's information based on primary key. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the defaultWeek's id
     * @return defaultWeek populated defaultWeek object
     */
    public DefaultWeek getDefaultWeekById(final Integer id);

    /**
     * Saves a defaultWeek's information
     *
     * @param defaultWeek the object to be saved
     */
    public void saveDefaultWeek(DefaultWeek defaultWeek);

    /**
     * Removes a defaultWeek from the database by id
     *
     * @param id the defaultWeek's id
     */
    public void removeDefaultWeek(final Integer id);

    public DefaultWeekDay getDefaultDay(Date date, List<DefaultWeekDay> week);

    public DayModel getDefaultDay(final Date date, final Reference region);

}

