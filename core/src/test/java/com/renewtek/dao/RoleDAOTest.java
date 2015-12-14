// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao;

import java.util.List;

import com.renewtek.model.Role;


public class RoleDAOTest extends BaseDAOTestCase {

   private RoleDAO roleDAO = null;

   public RoleDAOTest() {
      roleDAO = (RoleDAO)ctx.getBean("roleDAO");
   }

   public void testFindAllRoles() {
      List<Role> roles = roleDAO.getRoles(null);
      assertEquals(2, roles.size());
   }

   public void testFindRoleByName() {
      final String roleName = "Administrator";
      Role role = roleDAO.getRole(roleName);
      assertNotNull(role);
      assertEquals(roleName, role.getName());
   } 
   
   public void testAddRole() {
      Role role = new Role();
      role.setName("Vendor");
      role.setDescription("Vendor");
      roleDAO.saveObject(role);
      assertNotNull(role.getId());
   }
   
   public void testUpdateRole() {
      Role role = roleDAO.getRole("Administrator");
      role.setDescription("System Administrator");
      roleDAO.saveRole(role);
      assertEquals("System Administrator", role.getDescription());
   }
   
   public void testRemoveRole() {
      Role role = roleDAO.getRole("User");
      assertNotNull(role);
      roleDAO.removeRole("User");
      role = roleDAO.getRole("User");
      assertNull(role);
   }

}
