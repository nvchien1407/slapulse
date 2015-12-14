//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.dao;

import java.util.Date;
import java.util.List;

import com.renewtek.model.ChangeLog;

public interface ChangeLogDAO extends DAO {

    /**
     * Retrieves all of the changeLogs
     */
    public List<ChangeLog> getChangeLogs(ChangeLog changeLog);

    /**
     * Gets changeLog's information based on primary key. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the changeLog's id
     * @return changeLog populated changeLog object
     */
    public ChangeLog getChangeLog(final Integer id);

    /**
     * Saves a changeLog's information
     *
     * @param changeLog the object to be saved
     */
    public void saveChangeLog(ChangeLog changeLog);

    /**
     * Removes a changeLog from the database by id
     *
     * @param id the changeLog's id
     */
    public void removeChangeLog(final Integer id);
    
    public List<ChangeLog> getChangeLogsSince(Date since);
}

