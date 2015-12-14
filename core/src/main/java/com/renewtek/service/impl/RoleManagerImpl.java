//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service.impl;

import java.util.List;

import com.renewtek.dao.RoleDAO;
import com.renewtek.model.Role;
import com.renewtek.service.RoleManager;

/**
 * Implementation of RoleManager interface.</p>
 * <p/>
 * <p><a href="RoleManagerImpl.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 */
public class RoleManagerImpl extends BaseManager implements RoleManager {
    protected RoleDAO getRoleDAO() {
       return (RoleDAO) this.dao;
    }

    public void setRoleDAO(RoleDAO dao) {
        this.dao = dao;
    }

    public List<Role> getRoles(Role role) {
        return getRoleDAO().getRoles(role);
    }

    public Role getRole(String rolename) {
        return getRoleDAO().getRole(rolename);
    }

    public void saveRole(Role role) {
        getRoleDAO().saveRole(role);
    }

    public void removeRole(String rolename) {
        getRoleDAO().removeRole(rolename);
    }
}
