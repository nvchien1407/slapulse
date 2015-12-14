//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.dao;

import java.util.List;

import com.renewtek.model.BusinessProcess;

public interface BusinessProcessDAO extends DAO {

    /**
     * Retrieves all of the businessProcesss
     */
    public List<BusinessProcess> getBusinessProcesses(BusinessProcess businessProcess);

    /**
     * Retrieves all of the businessProcesses that have not yet
     * been assigned to an SLA.
     */
    public List<BusinessProcess> getUnassignedBusinessProcesses();

    /**
     * Gets businessProcess's information based on primary key. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the businessProcess's id
     * @return businessProcess populated businessProcess object
     */
    public BusinessProcess getBusinessProcess(final Integer id);

    public List<BusinessProcess> getBusinessProcess(final String name, final String type, String transaction, String step);

    public List<BusinessProcess> getBusinessProcessById(final Integer nameId, final Integer typeId);

    /**
     * Saves a businessProcess's information
     *
     * @param businessProcess the object to be saved
     */
    public void saveBusinessProcess(BusinessProcess businessProcess);

    /**
     * Removes a businessProcess from the database by id
     *
     * @param id the businessProcess's id
     */
    public void removeBusinessProcess(final Integer id);

    public Object merge(Object o);
}

