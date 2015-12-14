//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.renewtek.dao.UserDAO;
import com.renewtek.model.User;
import com.renewtek.service.UserExistsException;
import com.renewtek.service.UserManager;


/**
 * Implementation of UserManager interface.</p>
 * <p/>
 * <p>
 * <a href="UserManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class UserManagerImpl extends BaseManager implements UserManager {
    protected UserDAO getUserDAO() {
       return (UserDAO) this.dao;
    }

    /**
     * Set the DAO for communication with the data layer.
     *
     * @param dao
     */
    public void setUserDAO(UserDAO dao) {
        this.dao = dao;
    }

    /**
     * @see com.renewtek.service.UserManager#getUser(java.lang.String)
     */
    public User getUser(String username) {
        return getUserDAO().getUser(username);
    }

    /**
     * @see com.renewtek.service.UserManager#getUsers(com.renewtek.model.User)
     */
    public List<User> getUsers(User user) {
        return getUserDAO().getUsers(user);
    }

    /**
     * @see com.renewtek.service.UserManager#saveUser(com.renewtek.model.User)
     */
    public void saveUser(User user) throws UserExistsException {
        try {
            getUserDAO().saveUser(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserExistsException("User '" + user.getUsername() +
                    "' already exists!");
        }
    }

    /**
     * @see com.renewtek.service.UserManager#removeUser(java.lang.String)
     */
    public void removeUser(String username) {
        if (log.isDebugEnabled()) {
            log.debug("removing user: " + username);
        }

        getUserDAO().removeUser(username);
    }
}
