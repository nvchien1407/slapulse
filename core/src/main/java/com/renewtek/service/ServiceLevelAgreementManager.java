//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service;

import java.util.List;

import com.renewtek.model.ServiceLevelAgreement;
import com.renewtek.dao.ServiceLevelAgreementDAO;

public interface ServiceLevelAgreementManager extends Manager {

    /**
     * Setter for DAO, convenient for unit testing
     */
    public void setServiceLevelAgreementDAO(ServiceLevelAgreementDAO serviceLevelAgreementDAO);

    /**
     * Retrieves all of the serviceLevelAgreements
     */
    public List<ServiceLevelAgreement> getServiceLevelAgreements(ServiceLevelAgreement serviceLevelAgreement);

    /**
     * Gets serviceLevelAgreement's information based on id.
     *
     * @param id the serviceLevelAgreement's id
     * @return serviceLevelAgreement populated serviceLevelAgreement object
     */
    public ServiceLevelAgreement getServiceLevelAgreement(final String id);

    /**
     * Saves a serviceLevelAgreement's information
     *
     * @param serviceLevelAgreement the object to be saved
     */
    public void saveServiceLevelAgreement(ServiceLevelAgreement serviceLevelAgreement);

    /**
     * Removes a serviceLevelAgreement from the database by id
     *
     * @param id the serviceLevelAgreement's id
     */
    public void removeServiceLevelAgreement(final String id);

    public List<String> getMinutes();
}

