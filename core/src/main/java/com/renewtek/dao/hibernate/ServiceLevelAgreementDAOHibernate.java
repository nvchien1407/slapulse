//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.dao.hibernate;

import java.util.List;

import com.renewtek.model.ServiceLevelAgreement;
import com.renewtek.dao.ServiceLevelAgreementDAO;

import org.springframework.orm.ObjectRetrievalFailureException;

public class ServiceLevelAgreementDAOHibernate extends BaseDAOHibernate implements ServiceLevelAgreementDAO {

   /**
    * @see com.renewtek.dao.ServiceLevelAgreementDAO#getServiceLevelAgreements(com.renewtek.model.ServiceLevelAgreement)
    */
   @SuppressWarnings("unchecked")
   public List<ServiceLevelAgreement> getServiceLevelAgreements(final ServiceLevelAgreement serviceLevelAgreement) {
      return getHibernateTemplate().find("from ServiceLevelAgreement");

        /* Remove the line above and uncomment this code block if you want 
           to use Hibernate's Query by Example API.
        if (serviceLevelAgreement == null) {
            return getHibernateTemplate().find("from ServiceLevelAgreement");
        } else {
            // filter on properties set in the serviceLevelAgreement
            HibernateCallback callback = new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    Example ex = Example.create(serviceLevelAgreement).ignoreCase().enableLike(MatchMode.ANYWHERE);
                    return session.createCriteria(ServiceLevelAgreement.class).add(ex).list();
                }
            };
            return (List) getHibernateTemplate().execute(callback);
        }*/
   }

   /**
    * @see com.renewtek.dao.ServiceLevelAgreementDAO#getServiceLevelAgreement(Integer
    *      id)
    */
   public ServiceLevelAgreement getServiceLevelAgreement(final Integer id) {
      ServiceLevelAgreement serviceLevelAgreement = (ServiceLevelAgreement) getHibernateTemplate().get(
            ServiceLevelAgreement.class, id);
      if (serviceLevelAgreement == null) {
         log.warn("uh oh, serviceLevelAgreement with id '" + id + "' not found...");
         throw new ObjectRetrievalFailureException(ServiceLevelAgreement.class, id);
      }

      return serviceLevelAgreement;
   }

   /**
    * @see com.renewtek.dao.ServiceLevelAgreementDAO#saveServiceLevelAgreement(ServiceLevelAgreement
    *      serviceLevelAgreement)
    */
   public void saveServiceLevelAgreement(final ServiceLevelAgreement serviceLevelAgreement) {
      getHibernateTemplate().saveOrUpdate(serviceLevelAgreement);
   }

   /**
    * @see com.renewtek.dao.ServiceLevelAgreementDAO#removeServiceLevelAgreement(Integer
    *      id)
    */
   public void removeServiceLevelAgreement(final Integer id) {
      getHibernateTemplate().delete(getServiceLevelAgreement(id));
   }
}
