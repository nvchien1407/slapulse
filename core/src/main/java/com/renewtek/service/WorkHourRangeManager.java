//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service;

import java.util.List;

import com.renewtek.model.WorkHourRange;
import com.renewtek.dao.WorkHourRangeDAO;

public interface WorkHourRangeManager extends Manager {

    /**
     * Setter for DAO, convenient for unit testing
     */
    public void setWorkHourRangeDAO(WorkHourRangeDAO workHourRangeDAO);

    /**
     * Retrieves all of the workHourRanges
     */
    public List<WorkHourRange> getWorkHourRanges(WorkHourRange workHourRange);

    /**
     * Gets workHourRange's information based on id.
     *
     * @param id the workHourRange's id
     * @return workHourRange populated workHourRange object
     */
    public WorkHourRange getWorkHourRange(final String id);

    /**
     * Saves a workHourRange's information
     *
     * @param workHourRange the object to be saved
     */
    public void saveWorkHourRange(WorkHourRange workHourRange);

    /**
     * Removes a workHourRange from the database by id
     *
     * @param id the workHourRange's id
     */
    public void removeWorkHourRange(final String id);
}

