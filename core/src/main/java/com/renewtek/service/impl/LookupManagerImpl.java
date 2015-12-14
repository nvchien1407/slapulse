//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.renewtek.dao.LookupDAO;
import com.renewtek.model.LabelValue;
import com.renewtek.model.Role;
import com.renewtek.service.LookupManager;


/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 * <p/>
 * <p><a href="LookupManagerImpl.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class LookupManagerImpl extends BaseManager implements LookupManager {
   //~ Instance fields ========================================================

   protected LookupDAO getLookupDAO() {
      return (LookupDAO) this.dao;
   }

   //~ Methods ================================================================

   public void setLookupDAO(LookupDAO dao) {
      this.dao = dao;
   }

   /**
    * @see com.renewtek.service.LookupManager#getAllRoles()
    */
   public List<LabelValue> getAllRoles() {
      List<Role> roles = getLookupDAO().getRoles();
      List<LabelValue> list = new ArrayList<LabelValue>();
      for (Role role : roles) {
         list.add(new LabelValue(role.getName(), role.getName()));
      }
      return list;
   }
}
