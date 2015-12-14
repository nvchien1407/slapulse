// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Mock;
import org.springframework.dao.DataIntegrityViolationException;

import com.renewtek.Constants;
import com.renewtek.dao.RoleDAO;
import com.renewtek.dao.UserDAO;
import com.renewtek.model.Role;
import com.renewtek.model.User;
import com.renewtek.service.impl.RoleManagerImpl;
import com.renewtek.service.impl.UserManagerImpl;

public class UserManagerTest extends BaseManagerTestCase {

   // ~ Instance fields ========================================================

   private UserManager userManager = new UserManagerImpl();

   private RoleManager roleManager = new RoleManagerImpl();

   private Mock userDAO = null;

   private Mock roleDAO = null;

   private User user = null;

   private Role role = null;

   // ~ Methods ================================================================

   protected void setUp() throws Exception {
      super.setUp();
      userDAO = new Mock(UserDAO.class);
      userManager.setUserDAO((UserDAO) userDAO.proxy());
      roleDAO = new Mock(RoleDAO.class);
      roleManager.setRoleDAO((RoleDAO) roleDAO.proxy());
   }

   public void testGetUser() throws Exception {
      User testData = new User("tomcat");
      testData.getRoles().add(new Role("user"));
      // set expected behavior on dao
      userDAO.expects(once()).method("getUser").with(eq("tomcat")).will(returnValue(testData));

      user = userManager.getUser("tomcat");
      assertTrue(user != null);
      assertTrue(user.getRoles().size() == 1);
      userDAO.verify();
   }

   public void testSaveUser() throws Exception {
      User testData = new User("tomcat");
      testData.getRoles().add(new Role("user"));
      // set expected behavior on dao
      userDAO.expects(once()).method("getUser").with(eq("tomcat")).will(returnValue(testData));

      user = userManager.getUser("tomcat");
      user.setPhoneNumber("303-555-1212");
      userDAO.verify();

      // reset expectations
      userDAO.reset();
      userDAO.expects(once()).method("saveUser").with(same(user));

      userManager.saveUser(user);
      assertTrue(user.getPhoneNumber().equals("303-555-1212"));
      assertTrue(user.getRoles().size() == 1);
      userDAO.verify();
   }

   public void testAddAndRemoveUser() throws Exception {
      user = new User();

      // call populate method in super class to populate test data
      // from a properties file matching this class name
      user = (User) populate(user);

      // set expected behavior on role dao
      roleDAO.expects(once()).method("getRole").with(eq("tomcat")).will(returnValue(new Role("tomcat")));

      role = roleManager.getRole(Constants.USER_ROLE);
      roleDAO.verify();
      user.addRole(role);

      // set expected behavior on user dao
      userDAO.expects(once()).method("saveUser").with(same(user));

      userManager.saveUser(user);
      assertTrue(user.getUsername().equals("john"));
      assertTrue(user.getRoles().size() == 1);
      userDAO.verify();

      // reset expectations
      userDAO.reset();

      userDAO.expects(once()).method("removeUser").with(eq(user.getUsername()));
      userManager.removeUser(user.getUsername());
      userDAO.verify();

      // reset expectations
      userDAO.reset();
      userDAO.expects(once()).method("getUser").will(returnValue(null));
      user = userManager.getUser("john");
      assertNull(user);
      userDAO.verify();
   }

   public void testUserExistsException() {
      // set expectations
      user = new User("admin");
      user.setEmail("matt@raibledesigns.com");
      List<User> users = new ArrayList<User>();

      users.add(user);
      Exception ex = new DataIntegrityViolationException("");
      userDAO.expects(once()).method("saveUser").with(same(user)).will(throwException(ex));

      // run test
      try {
         userManager.saveUser(user);
         fail("Expected UserExistsException not thrown");
      }
      catch (UserExistsException e) {
         log.debug("expected exception: " + e.getMessage());
         assertNotNull(e);
      }
   }

   public void testGetUsers() {
      user = new User("ABC");

      List<User> result = new ArrayList<User>();
      result.add(new User());
      result.add(new User());
      
      userDAO.expects(once()).method("getUsers").with(same(user)).will(returnValue(result));

      List<User> actual = userManager.getUsers(user);
      
      userDAO.verify();
      assertTrue(actual.size() == 2);
   }
}
