package com.renewtek.service;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Mock;

import com.renewtek.dao.RoleDAO;
import com.renewtek.model.Role;
import com.renewtek.service.impl.RoleManagerImpl;


public class RoleManagerTest extends BaseManagerTestCase {
   private RoleManager objectUnderTest;
   
   private Mock mockRoleDAO;
   
   protected void setUp() throws Exception {
      mockRoleDAO = new Mock(RoleDAO.class);
      
      objectUnderTest = new RoleManagerImpl();
      objectUnderTest.setRoleDAO((RoleDAO) mockRoleDAO.proxy());
   }
   
   public void testGetRoles() {
      Role role = new Role();
      
      List<Role> testData = new ArrayList<Role>();
      testData.add(new Role());
      testData.add(new Role());
      
      mockRoleDAO.expects(once()).method("getRoles").with(same(role)).will(returnValue(testData));
      
      List<Role> actual =  objectUnderTest.getRoles(role);
      
      mockRoleDAO.verify();
      assertNotNull(actual);
      assertTrue(actual.size() == 2);
   }
   
   public void testGetRole() {
      String roleName = "ABC";
      Role result = new Role();
      
      mockRoleDAO.expects(once()).method("getRole").with(eq(roleName)).will(returnValue(result));
      
      Role role = objectUnderTest.getRole(roleName);
      
      mockRoleDAO.verify();
      assertNotNull(role);
      assertSame(result, role);
   }
   
   public void testSaveRole() {
      Role role = new Role();
      
      mockRoleDAO.expects(once()).method("saveRole").with(same(role)).isVoid();
      objectUnderTest.saveRole(role);
      mockRoleDAO.verify();
   }
   
   public void testRemoveRole() {
      String roleName = "ABC";
      
      mockRoleDAO.expects(once()).method("removeRole").with(eq(roleName)).isVoid();
      objectUnderTest.removeRole(roleName);
      mockRoleDAO.verify();
   }
   
   
}
