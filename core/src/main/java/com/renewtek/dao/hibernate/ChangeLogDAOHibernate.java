// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.renewtek.dao.ChangeLogDAO;
import com.renewtek.model.ChangeLog;

public class ChangeLogDAOHibernate extends BaseDAOHibernate implements ChangeLogDAO {

   /**
    * @see com.renewtek.dao.ChangeLogDAO#getChangeLogs(com.renewtek.model.ChangeLog)
    */
   @SuppressWarnings("unchecked")
   public List<ChangeLog> getChangeLogs(final ChangeLog changeLog) {
      return getHibernateTemplate().find("from ChangeLog");
   }

   /**
    * @see com.renewtek.dao.ChangeLogDAO#getChangeLog(Integer id)
    */
   public ChangeLog getChangeLog(final Integer id) {
      ChangeLog changeLog = (ChangeLog) getHibernateTemplate().get(ChangeLog.class, id);
      if (changeLog == null) {
         log.warn("uh oh, changeLog with id '" + id + "' not found...");
         throw new ObjectRetrievalFailureException(ChangeLog.class, id);
      }

      return changeLog;
   }

   /**
    * @see com.renewtek.dao.ChangeLogDAO#saveChangeLog(ChangeLog changeLog)
    */
   public void saveChangeLog(final ChangeLog changeLog) {
      getHibernateTemplate().saveOrUpdate(changeLog);
   }

   /**
    * @see com.renewtek.dao.ChangeLogDAO#removeChangeLog(Integer id)
    */
   public void removeChangeLog(final Integer id) {
      getHibernateTemplate().delete(getChangeLog(id));
   }
   
   public List<ChangeLog> getChangeLogsSince(final Date since) {
      HibernateCallback<List<ChangeLog>> callback = new HibernateCallback<List<ChangeLog>>() {
         @SuppressWarnings("unchecked")
         public List<ChangeLog> doInHibernate(Session session) throws HibernateException {
            Criteria criteria = session.createCriteria(ChangeLog.class).add(
                  Restrictions.gt("operateTime", since));
            return criteria.list();
         }
      };
      return getHibernateTemplate().execute(callback);
   }
}
