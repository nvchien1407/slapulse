//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT©2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/*
 * detects when something interesting has happened and sets a thread local.
 * Used for detecting if we should commit transactions or just close them
 */
public class SomethingHappenedInterceptor implements MethodInterceptor {

    public static String SOMETHING_HAPPENED = "SH";

    private final Logger log = LoggerFactory.getLogger(SomethingHappenedInterceptor.class);

    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            Object rval = invocation.proceed();
            if (invocation.getMethod().getName().equals("onSubmit")) {
                TransactionSynchronizationManager.bindResource(SOMETHING_HAPPENED, Boolean.TRUE);
            }
            return rval;
        }
        catch (Throwable ex) {
            log.error("Error: ", ex);
            throw ex;
        }
    }

}
