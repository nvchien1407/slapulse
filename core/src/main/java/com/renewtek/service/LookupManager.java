//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service;

import java.util.List;

import com.renewtek.dao.LookupDAO;
import com.renewtek.model.LabelValue;


/**
 * Business Service Interface to talk to persistence layer and
 * retrieve values for drop-down choice lists.
 * <p/>
 * <p>
 * <a href="LookupManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface LookupManager {
    //~ Methods ================================================================

    public void setLookupDAO(LookupDAO dao);

    /**
     * Retrieves all possible roles from persistence layer
     *
     * @return List
     */
    public List<LabelValue> getAllRoles();
}
