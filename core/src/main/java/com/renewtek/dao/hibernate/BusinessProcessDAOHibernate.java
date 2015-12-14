// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.renewtek.Constants;
import com.renewtek.dao.BusinessProcessDAO;
import com.renewtek.model.BusinessProcess;
import com.renewtek.model.Reference;

public class BusinessProcessDAOHibernate extends BaseDAOHibernate implements BusinessProcessDAO {

   /**
    * @see com.renewtek.dao.BusinessProcessDAO#getBusinessProcesss(com.renewtek.model.BusinessProcess)
    */
   @SuppressWarnings("unchecked")
   public List<BusinessProcess> getBusinessProcesses(final BusinessProcess businessProcess) {
      if (businessProcess == null) {
         return getHibernateTemplate().find("from BusinessProcess");
      }
      else {
         HibernateCallback<Object> callback = new HibernateCallback<Object>() {
            public Object doInHibernate(Session session) throws HibernateException {
               Criteria criteria = session.createCriteria(BusinessProcess.class);
               if (businessProcess.getName() != null) {
                  criteria.createCriteria("name", "nameAlias")
                        .add(Restrictions.eq("nameAlias.itemName", businessProcess.getName().getItemName()))
                        .add(Restrictions.eq("nameAlias.subGroupName", Constants.REFERENCE_BUSINESSPROCESS_NAME));
               }
               if (businessProcess.getType() != null) {
                  criteria.createCriteria("type", "typeAlias")
                        .add(Restrictions.eq("typeAlias.itemName", businessProcess.getType().getItemName()))
                        .add(Restrictions.eq("typeAlias.subGroupName", Constants.REFERENCE_BUSINESSPROCESS_TYPE));
               }
               nullableAddToQuery(businessProcess, criteria, businessProcess.getTxn(), "txn", Constants.REFERENCE_BUSINESSPROCESS_TXN);
               nullableAddToQuery(businessProcess, criteria, businessProcess.getStep(), "step", Constants.REFERENCE_BUSINESSPROCESS_STEP);
               return criteria.list();
            }

            private void nullableAddToQuery(final BusinessProcess businessProcess, Criteria criteria, Reference ref, String propertyName, String subGroupName) {
               String alias = propertyName + "Alias";
               if (ref != null) {
                  if (ref == Reference.NULL_VALUE) {
                     criteria.add(Restrictions.isNull(propertyName));
                  }
                  else {
                     criteria.createCriteria(propertyName, alias)
                           .add(Restrictions.eq(alias + ".itemName", ref.getItemName()))
                           .add(Restrictions.eq(alias + ".subGroupName", subGroupName));
                  }
               }
            }
         };
         return (List<BusinessProcess>) getHibernateTemplate().execute(callback);
      }
   }

   /**
    * @see com.renewtek.dao.BusinessProcessDAO#getBusinessProcess(Integer id)
    */
   public BusinessProcess getBusinessProcess(final Integer id) {
      BusinessProcess businessProcess = (BusinessProcess) getHibernateTemplate().get(BusinessProcess.class, id);
      if (businessProcess == null) {
         log.warn("uh oh, businessProcess with id '" + id + "' not found...");
         throw new ObjectRetrievalFailureException(BusinessProcess.class, id);
      }

      return businessProcess;
   }

   /**
    * @see com.renewtek.dao.BusinessProcessDAO#saveBusinessProcess(BusinessProcess
    *      businessProcess)
    */
   public void saveBusinessProcess(final BusinessProcess businessProcess) {
      getHibernateTemplate().saveOrUpdate(businessProcess);
   }

   /**
    * @see com.renewtek.dao.BusinessProcessDAO#removeBusinessProcess(Integer id)
    */
   public void removeBusinessProcess(final Integer id) {
      getHibernateTemplate().delete(getBusinessProcess(id));
   }

   @SuppressWarnings("unchecked")
   public List<BusinessProcess> getUnassignedBusinessProcesses() {
      return getHibernateTemplate().find("from BusinessProcess where servicelevelagreementid = NULL");
   }

   public List<BusinessProcess> getBusinessProcess(String name, String type, String txn, String step) {
      BusinessProcess q = new BusinessProcess();
      q.setName(new Reference(name));
      q.setType(new Reference(type));
      q.setTxn(nullableAddToExample(txn, q));
      q.setStep(nullableAddToExample(step, q));
      return this.getBusinessProcesses(q);
   }

   private Reference nullableAddToExample(String str, BusinessProcess q) {
      // a null property in Saigon will come here as ""
      if (StringUtils.isNotEmpty(str)) {
         return new Reference(str);
      } else {
         return Reference.NULL_VALUE;
      }
   }

   @SuppressWarnings("unchecked")
   public List<BusinessProcess> getBusinessProcessById(final Integer nameId, final Integer typeId) {
      String[] paramNames = { "nameId", "typeId" };
      Object[] values = { nameId, typeId };
      return getHibernateTemplate().findByNamedQueryAndNamedParam(BusinessProcess.QUERY_FIND_BY_NAMEID_AND_TYPEID,
            paramNames, values);
   }
}
