//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.dao;

import java.util.List;

import com.renewtek.model.WorkHourRange;

public interface WorkHourRangeDAO extends DAO {

    /**
     * Retrieves all of the workHourRanges
     */
    public List<WorkHourRange> getWorkHourRanges(WorkHourRange workHourRange);

    /**
     * Gets workHourRange's information based on primary key. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the workHourRange's id
     * @return workHourRange populated workHourRange object
     */
    public WorkHourRange getWorkHourRange(final Integer id);

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
    public void removeWorkHourRange(final Integer id);
}

