//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.dao;

import java.util.List;

import com.renewtek.model.DayModel;

public interface DayModelDAO extends DAO {

    /**
     * Retrieves all of the dayModels
     */
    public List<DayModel> getDayModels(DayModel dayModel);

    /**
     * Gets dayModel's information based on primary key. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the dayModel's id
     * @return dayModel populated dayModel object
     */
    public DayModel getDayModel(final Integer id);

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
    public void removeDayModel(final Integer id);
}

