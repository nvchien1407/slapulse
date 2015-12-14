//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.renewtek.dao.DAO;
import com.renewtek.service.Manager;

/**
 * Base class for Business Services - use this class for utility methods and
 * generic CRUD methods.
 * <p/>
 * <p><a href="BaseManager.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseManager implements Manager {

   protected final Logger log = LoggerFactory.getLogger(getClass());

   protected DAO dao = null;

   /**
    * @see com.renewtek.service.Manager#setDAO(com.renewtek.dao.DAO)
    */
   public void setDAO(DAO dao) {
      this.dao = dao;
   }

   /**
    * @see com.renewtek.service.Manager#getObject(java.lang.Class, java.io.Serializable)
    */
   public Object getObject(Class<?> clazz, Serializable id) {
      return dao.getObject(clazz, id);
   }

   /**
    * @see com.renewtek.service.Manager#getObjects(java.lang.Class)
    */
   public List<?> getObjects(Class<?> clazz) {
      return dao.getObjects(clazz);
   }

   /**
    * @see com.renewtek.service.Manager#removeObject(java.lang.Class, java.io.Serializable)
    */
   public void removeObject(Class<?> clazz, Serializable id) {
      dao.removeObject(clazz, id);
   }

   /**
    * @see com.renewtek.service.Manager#saveObject(java.lang.Object)
    */
   public void saveObject(Object o) {
      dao.saveObject(o);
   }
}
