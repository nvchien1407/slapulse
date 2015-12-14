// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao.hibernate;

import java.util.List;

import com.renewtek.dao.RoleDAO;
import com.renewtek.model.Role;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve Role objects.
 * <p/>
 * <p>
 * <a href="RoleDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 */
public class RoleDAOHibernate extends BaseDAOHibernate implements RoleDAO {

   @SuppressWarnings("unchecked")
   public List<Role> getRoles(Role role) {
      return getHibernateTemplate().find("from Role");
   }

   public Role getRole(String rolename) {
      return (Role) getHibernateTemplate().get(Role.class, rolename);
   }

   public void saveRole(Role role) {
      getHibernateTemplate().saveOrUpdate(role);
   }

   public void removeRole(String rolename) {
      Object role = getHibernateTemplate().load(Role.class, rolename);
      getHibernateTemplate().delete(role);
   }

}
