// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao;

import java.util.List;

import com.renewtek.model.Role;

public class LookupDAOTest extends BaseDAOTestCase {

   private LookupDAO lookupDAO = null;

   public LookupDAOTest() {
      lookupDAO = (LookupDAO)ctx.getBean("lookupDAO");
   }

   public void testFindAllRoles() {
      List<Role> roles = lookupDAO.getRoles();
      assertEquals(2, roles.size());
   }

}
