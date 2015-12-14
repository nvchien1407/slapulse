//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.renewtek.dao.DayModelDAO;
import com.renewtek.model.DayModel;

public class DayModelDAOHibernate extends BaseDAOHibernate implements DayModelDAO {

   /**
    * @see com.renewtek.dao.DayModelDAO#getDayModels(com.renewtek.model.DayModel)
    */
   @SuppressWarnings("unchecked")
   public List<DayModel> getDayModels(final DayModel dayModel) {
      //Remove the line above and uncomment this code block if you want
      //to use Hibernate's Query by Example API.
      if (dayModel == null) {
         return getHibernateTemplate().find("from DayModel");
      }
      else {
         // filter on properties set in the dayModel
         HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {
               Example ex = Example.create(dayModel).ignoreCase().enableLike(MatchMode.ANYWHERE);
               return session.createCriteria(DayModel.class).add(ex).list();
            }
         };
         return (List<DayModel>) getHibernateTemplate().execute(callback);
      }
   }

   /**
    * @see com.renewtek.dao.DayModelDAO#getDayModel(Integer id)
    */
   public DayModel getDayModel(final Integer id) {
      DayModel dayModel = (DayModel) getHibernateTemplate().get(DayModel.class, id);
      if (dayModel == null) {
         log.warn("uh oh, dayModel with id '" + id + "' not found...");
         throw new ObjectRetrievalFailureException(DayModel.class, id);
      }

      return dayModel;
   }

   /**
    * @see com.renewtek.dao.DayModelDAO#saveDayModel(DayModel dayModel)
    */
   public void saveDayModel(final DayModel dayModel) {
      getHibernateTemplate().saveOrUpdate(dayModel);
   }

   /**
    * @see com.renewtek.dao.DayModelDAO#removeDayModel(Integer id)
    */
   public void removeDayModel(final Integer id) {
      getHibernateTemplate().delete(getDayModel(id));
   }
}
