//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service;

import java.util.List;

import com.renewtek.model.Reference;
import com.renewtek.dao.ReferenceDAO;

public interface ReferenceManager extends Manager {

    /**
     * Setter for DAO, convenient for unit testing
     */
    public void setReferenceDAO(ReferenceDAO referenceDAO);

    /**
     * Retrieves all of the references
     */
    public List<Reference> getReferences(Reference reference);

    public List<Reference> getReferencesByTemplate(Reference reference);

    public List<Reference> getRefernceByGroup(String groupName);

    public List<Reference> getCountryStates();

    public Reference getWithdrawStatus();

    /**
     * Retrieves all of the sub-groups in references
     */
    public List<String> getSubgroups();

    /**
     * Edits sub-group's Name in related references
     */
    public void editSubgroupName(String oldName, String newName);

    /**
     * Retrieves all references of the given sub-groups name
     */
    public List<Reference> getReferencesByParam(String subgroupName);

    /**
     * Gets reference's information based on id.
     *
     * @param id the reference's id
     * @return reference populated reference object
     */
    public Reference getReference(final String id);

    public Reference getReferenceByItemName(final String itemName);

    /**
     * Saves a reference's information
     *
     * @param reference the object to be saved
     */
    public void saveReference(Reference reference);

    /**
     * Removes a reference from the database by id
     *
     * @param id the reference's id
     */
    public void removeReference(final String id);
    
    public Boolean isReferenced(final String id);
}

