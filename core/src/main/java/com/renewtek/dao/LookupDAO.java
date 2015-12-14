//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao;

import java.util.List;

import com.renewtek.model.Role;


/**
 * Lookup Data Access Object (DAO) interface.  This is used to lookup values in
 * the database (i.e. for drop-downs).
 * <p/>
 * <p>
 * <a href="LookupDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface LookupDAO extends DAO {
    //~ Methods ================================================================

    public List<Role> getRoles();
}
