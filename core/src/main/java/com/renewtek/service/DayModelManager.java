//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service;

import java.util.List;

import com.renewtek.model.DayModel;
import com.renewtek.dao.DayModelDAO;

public interface DayModelManager extends Manager {

    /**
     * Setter for DAO, convenient for unit testing
     */
    public void setDayModelDAO(DayModelDAO dayModelDAO);

    /**
     * Retrieves all of the dayModels
     */
    public List<DayModel> getDayModels(DayModel dayModel);

    /**
     * Gets dayModel's information based on id.
     *
     * @param id the dayModel's id
     * @return dayModel populated dayModel object
     */
    public DayModel getDayModel(final String id);

    /**
     * Saves a dayModel's information
     *
     * @param dayModel the object to be saved
     */
    public void saveDayModel(DayModel dayModel);

    /**
     * Removes a dayModel from the database by id
     *
     * @param id the dayModel's id
     */
    public void removeDayModel(final String id);

    public List<DayModel> getCalendars();
}

