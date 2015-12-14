// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.renewtek.dao.ReferenceDAO;
import com.renewtek.model.Reference;

public class ReferenceDAOHibernate extends BaseDAOHibernate implements ReferenceDAO {

   @SuppressWarnings("unchecked")
   public Reference getReference(String groupName, String itemName) {
      List<Reference> list = getHibernateTemplate().find(
            "from Reference ref where ref.groupName=" + groupName + " AND ref.itemName = " + itemName);
      if (list == null || list.size() <= 0)
         return null;
      return list.get(0);
   }

   /**
    * @see com.renewtek.dao.ReferenceDAO#getReferences(com.renewtek.model.Reference)
    */
   @SuppressWarnings("unchecked")
   public List<Reference> getReferences(final Reference reference) {
      return getHibernateTemplate().find("from Reference");
      /*
       * Remove the line above and uncomment this code block if you want to use
       * Hibernate's Query by Example API. if (reference == null) { return
       * getHibernateTemplate().find("from Reference"); } else { // filter on
       * properties set in the reference HibernateCallback callback = new
       * HibernateCallback() { public Object doInHibernate(Session session)
       * throws HibernateException { Example ex =
       * Example.create(reference).ignoreCase().enableLike(MatchMode.ANYWHERE);
       * return session.createCriteria(Reference.class).add(ex).list(); } };
       * return (List) getHibernateTemplate().execute(callback); }
       */
   }

   /**
    * @see com.renewtek.dao.ReferenceDAO#getReferences(String subgroupName)
    */
   @SuppressWarnings("unchecked")
   public List<Reference> getReferences(final String subgroupName) {
      HibernateCallback callback = new HibernateCallback() {

         public Object doInHibernate(Session session) throws HibernateException {
            Criteria criteria = session.createCriteria(Reference.class).add(
                  Restrictions.like("subGroupName", subgroupName));
            return criteria.list();
         }
      };
      return (List<Reference>) getHibernateTemplate().execute(callback);
   }

   /**
    * @see com.renewtek.dao.ReferenceDAO#getReference(Integer id)
    */
   public Reference getReference(final Integer id) {
      Reference reference = (Reference) getHibernateTemplate().get(Reference.class, id);
      if (reference == null) {
         log.warn("uh oh, reference with id '" + id + "' not found...");
         throw new ObjectRetrievalFailureException(Reference.class, id);
      }

      return reference;
   }

   @SuppressWarnings("unchecked")
   public Reference getReferenceByItemName(final String itemName) {
      HibernateCallback callback = new HibernateCallback() {

         public Object doInHibernate(Session session) throws HibernateException {
            Criteria criteria = session.createCriteria(Reference.class).add(Restrictions.eq("itemName", itemName));
            return criteria.list();
         }
      };
      List<Reference> list = (List<Reference>) getHibernateTemplate().execute(callback);
      if (list == null || list.size() <= 0)
         return null;
      return list.get(0);
   }

   /**
    * @see com.renewtek.dao.ReferenceDAO#getReferencesByTemplate(com.renewtek.model.Reference)
    */
   @SuppressWarnings("unchecked")
   public List<Reference> getReferencesByTemplate(final Reference reference) {
      /*
       * Remove the line above and uncomment this code block if you want to use
       * Hibernate's Query by Example API.
       */
      // filter on properties set in the reference
      HibernateCallback callback = new HibernateCallback() {

         public Object doInHibernate(Session session) throws HibernateException {
            Example ex = Example.create(reference).ignoreCase().enableLike(MatchMode.EXACT);
            return session.createCriteria(Reference.class).add(ex).list();
         }
      };
      return (List<Reference>) getHibernateTemplate().execute(callback);
   }

   /**
    * @see com.renewtek.dao.ReferenceDAO#saveReference(Reference reference)
    */
   public void saveReference(final Reference reference) {
      getHibernateTemplate().saveOrUpdate(reference);
   }

   /**
    * @see com.renewtek.dao.ReferenceDAO#removeReference(Integer id)
    */
   public void removeReference(final Integer id) {
      getHibernateTemplate().delete(getReference(id));
      Session s = null;
      try {
         s = getHibernateTemplate().getSessionFactory().getCurrentSession();
         s.flush();
      }
      catch (HibernateException e) {
         s.clear();
         throw e;
      }
   }

   @SuppressWarnings("unchecked")
   public List<String> getSubgroups() {
      return getHibernateTemplate().find("select distinct ref.subGroupName  from Reference as ref");
   }
   @SuppressWarnings("unchecked")
   public Boolean isReferenced(final String id)
   {
      List<?> list = getHibernateTemplate().find("select bp.id from BusinessProcess as bp where bp.txn.id="+id);
      return list.size() > 0 ? true : false;
   }
}
