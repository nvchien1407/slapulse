//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.dao;

import java.util.List;

import com.renewtek.model.ServiceLevelAgreement;

public interface ServiceLevelAgreementDAO extends DAO {

    /**
     * Retrieves all of the serviceLevelAgreements
     */
    public List<ServiceLevelAgreement> getServiceLevelAgreements(ServiceLevelAgreement serviceLevelAgreement);

    /**
     * Gets serviceLevelAgreement's information based on primary key. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the serviceLevelAgreement's id
     * @return serviceLevelAgreement populated serviceLevelAgreement object
     */
    public ServiceLevelAgreement getServiceLevelAgreement(final Integer id);

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
    public void removeServiceLevelAgreement(final Integer id);
}

