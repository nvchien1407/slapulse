//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service;

import java.util.Date;

import com.renewtek.dao.DefaultWeekDAO;
import com.renewtek.model.DayModel;
import com.renewtek.model.DefaultWeek;
import com.renewtek.model.Reference;

public interface DefaultWeekManager extends Manager {

    /**
     * Setter for DAO, convenient for unit testing
     */
    public void setDefaultWeekDAO(DefaultWeekDAO defaultWeekDAO);

    /**
     * Retrieves all of the defaultWeeks
     */
    public DefaultWeek getDefaultWeek(Reference region);

    /**
     * Gets defaultWeek's information based on id.
     *
     * @param id the defaultWeek's id
     * @return defaultWeek populated defaultWeek object
     */
    public DefaultWeek getDefaultWeekById(final String id);

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
    public void removeDefaultWeek(final String id);

    public DayModel getDayByRegion(Date date, Reference region);
}

