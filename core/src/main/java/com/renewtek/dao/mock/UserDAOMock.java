// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao.mock;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import com.renewtek.dao.UserDAO;
import com.renewtek.model.User;

/**
 * This class pretends to talk to a DB to fetch, save etc. user objects
 */
public class UserDAOMock implements UserDAO {

   /**
    * @see com.renewtek.dao.UserDAO#getUser(java.lang.String)
    */
   public User getUser(String username) {
      User user = new User(username);
      return user;
   }

   /**
    * @see com.renewtek.dao.UserDAO#getUsers(com.renewtek.model.User)
    */
   public List<User> getUsers(User user) {
      throw new NotImplementedException("not implemented yet");
   }

   /**
    * @see com.renewtek.dao.UserDAO#saveUser(com.renewtek.model.User)
    */
   public void saveUser(final User user) {
      throw new NotImplementedException("not implemented yet");
   }

   /**
    * @see com.renewtek.dao.UserDAO#removeUser(java.lang.String)
    */
   public void removeUser(String username) {
      throw new NotImplementedException("not implemented yet");
   }

   @SuppressWarnings("unchecked")
   public List getObjects(Class clazz) {
      throw new NotImplementedException("not implemented yet");
   }

   @SuppressWarnings("unchecked")
   public Object getObject(Class clazz, Serializable id) {
      throw new NotImplementedException("not implemented yet");
   }

   public void saveObject(Object o) {
      throw new NotImplementedException("not implemented yet");
   }

   @SuppressWarnings("unchecked")
   public void removeObject(Class clazz, Serializable id) {
      throw new NotImplementedException("not implemented yet");
   }
}
