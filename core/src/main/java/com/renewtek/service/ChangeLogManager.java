//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service;

import java.util.Date;
import java.util.List;

import com.renewtek.model.ChangeLog;
import com.renewtek.dao.ChangeLogDAO;

public interface ChangeLogManager extends Manager {

    /**
     * Setter for DAO, convenient for unit testing
     */
    public void setChangeLogDAO(ChangeLogDAO changeLogDAO);

    /**
     * Retrieves all of the changeLogs
     */
    public List<ChangeLog> getChangeLogs(ChangeLog changeLog);

    /**
     * Gets changeLog's information based on id.
     *
     * @param id the changeLog's id
     * @return changeLog populated changeLog object
     */
    public ChangeLog getChangeLog(final String id);

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
    public void removeChangeLog(final String id);

    public List<ChangeLog> getChangeLogsSince(Date operateTime);
}

