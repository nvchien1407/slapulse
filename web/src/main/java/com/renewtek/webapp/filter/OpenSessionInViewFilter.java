//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.renewtek.service.SomethingHappenedInterceptor;

/**
 * <p>Open Session In View Renewtek impl.</p>
 *
 *
 * @web.filter name="hibernateFilter"
 */
public class OpenSessionInViewFilter extends org.springframework.orm.hibernate3.support.OpenSessionInViewFilter {

   private final Logger log = LoggerFactory.getLogger(getClass());

   private PlatformTransactionManager transactionManager;

   public void setTransactionManager(PlatformTransactionManager transactionManager) {
      this.transactionManager = transactionManager;
   }
   
   /*
    * Call filter chain with a transaction wrapped around it. We use
    * @see DefaultTransactionDefinition for the transaction's configuration. Should
    * probably allow this to be configurable going forward
    */
   private void callFilter(FilterChain filterChain, HttpServletRequest request, HttpServletResponse response)
         throws IOException, ServletException {
      TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
      try {
         log.debug("transaction started");
         filterChain.doFilter(request, response);
         if (TransactionSynchronizationManager.getResource(SomethingHappenedInterceptor.SOMETHING_HAPPENED) == Boolean.TRUE) {
            log.debug("transaction to be committed");
            // don't flush session here - commit will flush it as we are FlushMode.AUTO
            transactionManager.commit(status);
         }
         else {
            log.debug("transaction to be rolled back as nothing interesting happened");
            transactionManager.rollback(status);
         }
      }
      catch (Throwable e) {
         log.debug("transaction to be rolled back due to exception : ", e);
         try {
            transactionManager.rollback(status);
         }
         catch (Throwable re) {
            log.error("Error rolling back txn", re);
         }
         log.error("Exception might not be reported to user", e);
         throw new ServletException(e);
      }
      finally {
         if (TransactionSynchronizationManager.getResource(SomethingHappenedInterceptor.SOMETHING_HAPPENED) != null)
            TransactionSynchronizationManager.unbindResource(SomethingHappenedInterceptor.SOMETHING_HAPPENED);
      }
   }

   /*
    * get transaction manager from context
    * @see org.springframework.web.filter.GenericFilterBean#initFilterBean()
    */
   protected void initFilterBean() throws ServletException {
   }

   /*
    * Override base class to get a session with FlushMode.AUTO.
    * We do this so that our transaction's commit flushes the session, and also so that
    * we sit more happily within Spring's expectations of hibernate - @see HibernateTemplate
    * @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter#getSession(org.hibernate.SessionFactory)
    */
   protected Session getSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
      Session session = SessionFactoryUtils.getSession(sessionFactory, true);
      session.setFlushMode(FlushMode.AUTO);
      return session;
   }

   /*
    * Override super class method to invoke filter chain in a transaction
    * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
    */
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
         throws ServletException, IOException {
      final OpenSessionInViewFilter thisFinal = this;
      final HttpServletRequest requestFinal = request;
      final HttpServletResponse responseFinal = response;
      final FilterChain filterChainFinal = filterChain;
      // call superclass to do its thing but make sure we call our own impl. of the FilterChain
      // which delegates control to our callFilter method. We wrap a transaction around this
      super.doFilterInternal(request, response, new FilterChain() {

         public void doFilter(ServletRequest arg0, ServletResponse arg1) throws IOException, ServletException {
            thisFinal.callFilter(filterChainFinal, requestFinal, responseFinal);
         }
      });
   }
}
