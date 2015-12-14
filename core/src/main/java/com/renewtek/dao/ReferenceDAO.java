//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.dao;

import java.util.List;

import com.renewtek.model.Reference;

public interface ReferenceDAO extends DAO {

    /**
     * Retrieves all of the references
     */
    public List<Reference> getReferences(Reference reference);

    /**
     * Gets references information based on subGroupName. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param subgroupName
     * @return references of given subgroupName
     */
    public List<Reference> getReferences(final String subgroupName);

    /**
     * Retrieves all of the sub-groups in references
     */
    public List<String> getSubgroups();

    /**
     * Gets reference's information based on primary key. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the reference's id
     * @return reference populated reference object
     */
    public Reference getReference(final Integer id);

    public Reference getReferenceByItemName(final String itemName);

    public List<Reference> getReferencesByTemplate(Reference reference);

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
    public void removeReference(final Integer id);

    public Reference getReference(String groupName, String itemName);
    public Boolean isReferenced(final String id);
    
}

