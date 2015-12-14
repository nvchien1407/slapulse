//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.dao.hibernate;

import java.util.List;

import com.renewtek.model.WorkHourRange;
import com.renewtek.dao.WorkHourRangeDAO;

import org.springframework.orm.ObjectRetrievalFailureException;

public class WorkHourRangeDAOHibernate extends BaseDAOHibernate implements WorkHourRangeDAO {

   /**
    * @see com.renewtek.dao.WorkHourRangeDAO#getWorkHourRanges(com.renewtek.model.WorkHourRange)
    */
   @SuppressWarnings("unchecked")
   public List<WorkHourRange> getWorkHourRanges(final WorkHourRange workHourRange) {
      return getHibernateTemplate().find("from WorkHourRange");

        /* Remove the line above and uncomment this code block if you want 
           to use Hibernate's Query by Example API.
        if (workHourRange == null) {
            return getHibernateTemplate().find("from WorkHourRange");
        } else {
            // filter on properties set in the workHourRange
            HibernateCallback callback = new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    Example ex = Example.create(workHourRange).ignoreCase().enableLike(MatchMode.ANYWHERE);
                    return session.createCriteria(WorkHourRange.class).add(ex).list();
                }
            };
            return (List) getHibernateTemplate().execute(callback);
        }*/
   }

   /**
    * @see com.renewtek.dao.WorkHourRangeDAO#getWorkHourRange(Integer id)
    */
   public WorkHourRange getWorkHourRange(final Integer id) {
      WorkHourRange workHourRange = (WorkHourRange) getHibernateTemplate().get(WorkHourRange.class, id);
      if (workHourRange == null) {
         log.warn("uh oh, workHourRange with id '" + id + "' not found...");
         throw new ObjectRetrievalFailureException(WorkHourRange.class, id);
      }

      return workHourRange;
   }

   /**
    * @see com.renewtek.dao.WorkHourRangeDAO#saveWorkHourRange(WorkHourRange
    *      workHourRange)
    */
   public void saveWorkHourRange(final WorkHourRange workHourRange) {
      getHibernateTemplate().saveOrUpdate(workHourRange);
   }

   /**
    * @see com.renewtek.dao.WorkHourRangeDAO#removeWorkHourRange(Integer id)
    */
   public void removeWorkHourRange(final Integer id) {
      getHibernateTemplate().delete(getWorkHourRange(id));
   }
}
