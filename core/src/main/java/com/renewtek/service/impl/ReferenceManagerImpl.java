//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service.impl;

import java.util.Iterator;
import java.util.List;

import com.renewtek.Constants;
import com.renewtek.dao.ReferenceDAO;
import com.renewtek.model.Reference;
import com.renewtek.service.ReferenceManager;

public class ReferenceManagerImpl extends BaseManager implements ReferenceManager {
    protected ReferenceDAO getReferenceDAO() {
       return (ReferenceDAO) this.dao;
    }

    /**
     * Set the DAO for communication with the data layer.
     *
     * @param dao
     */
    public void setReferenceDAO(ReferenceDAO dao) {
        this.dao = dao;
    }

    public List<Reference> getCountryStates() {
        Reference ref = new Reference();
        ref.setGroupName(Constants.REFERENCE_STATE_COUNTRTY);
        return getReferenceDAO().getReferencesByTemplate(ref);
    }

    /**
     * @see com.renewtek.service.ReferenceManager#getReferences(com.renewtek.model.Reference)
     */
    public List<Reference> getReferences(final Reference reference) {
        return getReferenceDAO().getReferences(reference);
    }

    public List<Reference> getReferencesByTemplate(final Reference reference) {
        return getReferenceDAO().getReferencesByTemplate(reference);
    }


    public List<Reference> getRefernceByGroup(String groupName) {
        Reference ref = new Reference();
        ref.setSubGroupName(groupName);
        return getReferenceDAO().getReferencesByTemplate(ref);
    }

    public List<Reference> getReferencesByParam(String subgroupName) {
        return getReferenceDAO().getReferences(subgroupName);
    }

    /**
     * @see com.renewtek.service.ReferenceManager#getReference(String id)
     */
    public Reference getWithdrawStatus() {
        return getReferenceDAO().getReference(Constants.STATUS_GROUPNAME, Constants.WITHDRAWN_NAME);
    }

    /**
     * @see com.renewtek.service.ReferenceManager#getReference(String id)
     */
    public Reference getReference(final String id) {
        return getReferenceDAO().getReference(new Integer(id));
    }

    /**
     * @see com.renewtek.service.ReferenceManager#saveReference(Reference reference)
     */
    public void saveReference(Reference reference) {
        getReferenceDAO().saveReference(reference);
    }

    /**
     * @see com.renewtek.service.ReferenceManager#removeReference(String id)
     */
    public void removeReference(final String id) {
        getReferenceDAO().removeReference(new Integer(id));
    }

    /**
     * @see com.renewtek.service.ReferenceManager#getSubgroups()
     */
    public List<String> getSubgroups() {
        return getReferenceDAO().getSubgroups();
    }

    public void editSubgroupName(String oldName, String newName) {
        // get references by 'old' subgroup name
        List<Reference> res = getReferenceDAO().getReferences(oldName);

        // for each: change old to new subGroupName and save
        Iterator<Reference> it = res.iterator();
        Reference ref;
        while (it.hasNext()) {
            ref = it.next();
            ref.setSubGroupName(newName);
            getReferenceDAO().saveReference(ref);
        }
    }

    public Reference getReferenceByItemName(String itemName) {
        return getReferenceDAO().getReferenceByItemName(itemName);
	}
    public Boolean isReferenced(final String id){
       return getReferenceDAO().isReferenced(id);
    }
}
