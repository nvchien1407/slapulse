//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.renewtek.dao.DAO;

/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common methods that they might all use. Can be used for standard CRUD
 * operations.</p>
 * <p/>
 * <p><a href="BaseDAOHibernate.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseDAOHibernate extends HibernateDaoSupport implements DAO {

   protected final Logger log = LoggerFactory.getLogger(getClass());

   /**
    * @see com.renewtek.dao.DAO#saveObject(java.lang.Object)
    */
   public void saveObject(Object o) {
      getHibernateTemplate().saveOrUpdate(o);
   }

   /**
    * @see com.renewtek.dao.DAO#getObject(java.lang.Class, java.io.Serializable)
    */
   public Object getObject(Class<?> clazz, Serializable id) {
      Object o = getHibernateTemplate().get(clazz, id);

      if (o == null) {
         throw new ObjectRetrievalFailureException(clazz, id);
      }

      return o;
   }

   /**
    * @see com.renewtek.dao.DAO#getObjects(java.lang.Class)
    */
   public List<?> getObjects(Class<?> clazz) {
      return getHibernateTemplate().loadAll(clazz);
   }

   /**
    * @see com.renewtek.dao.DAO#removeObject(java.lang.Class, java.io.Serializable)
    */
   public void removeObject(Class<?> clazz, Serializable id) {
      getHibernateTemplate().delete(getObject(clazz, id));
   }

   public Object merge(Object o) {
      Object rv = getHibernateTemplate().merge(o);
      log.debug("merged {} same? {}", o, (o == rv));
      return rv;
   }
}
