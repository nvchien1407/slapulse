// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.sla.operations.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class HibernateSessionManager implements MethodInterceptor {

   private SessionFactory sessionFactory = null;

   // private PlatformTransactionManager transactionManager = null;
   private boolean singleSession = true;

   protected final Logger log = LoggerFactory.getLogger(getClass());

   // TODO: Add transaction Management for save/update operations.
   // At this release no update/save operations are
   /**
    * @todo: Add transaction Management for save/update operations.
    */
   public Object invoke(MethodInvocation invocation) throws Throwable {
      Session session = null;
      boolean participate = false;

      if (isSingleSession()) {
         // single session mode
         if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
            // Do not modify the Session: just set the participate flag.
            participate = true;
         }
         else {
            session = getSession();
            TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
         }
      }
      else {
         // deferred close mode
         if (SessionFactoryUtils.isDeferredCloseActive(sessionFactory)) {
            // Do not modify deferred close: just set the participate flag.
            participate = true;
         }
         else {
            SessionFactoryUtils.initDeferredClose(sessionFactory);
         }
      }
      // TransactionStatus status = transactionManager.getTransaction(new
      // DefaultTransactionDefinition());

      try {
         // transactionManager.commit(status);
         return invocation.proceed();
      }
      catch (Exception e) {
         log.error("Exception -", e);
         // try{
         // transactionManager.rollback(status);
         // }catch(Exception ee){;}
      }
      finally {
         if (!participate) {
            if (isSingleSession()) {
               // single session mode
               TransactionSynchronizationManager.unbindResource(sessionFactory);
               try {
                  closeSession(session, sessionFactory);
               }
               catch (RuntimeException ex) {
                  log.error("Failed to close session", ex);
               }
            }
            else {
               // deferred close mode
               SessionFactoryUtils.processDeferredClose(sessionFactory);
            }
         }
      }
      return null;
   }

   protected Session getSession() throws DataAccessResourceFailureException {
      Session session = SessionFactoryUtils.getSession(sessionFactory, true);
      session.setFlushMode(FlushMode.AUTO);
      return session;
   }

   public void setSessionFactory(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   protected void closeSession(Session session, SessionFactory sessionFactory) {
      SessionFactoryUtils.releaseSession(session, sessionFactory);
   }

   //
   // public void setTransactionManager(PlatformTransactionManager
   // transactionManager) {
   // this.transactionManager = transactionManager;
   // }
   protected boolean isSingleSession() {
      return singleSession;
   }

}
