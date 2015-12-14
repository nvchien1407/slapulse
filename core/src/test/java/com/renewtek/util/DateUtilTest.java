//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT©2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.util;

import java.util.Date;
import java.util.Locale;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

public class DateUtilTest extends TestCase {
    //~ Instance fields ========================================================

    private final Log log = LogFactory.getLog(DateUtilTest.class);

    //~ Constructors ===========================================================

    public DateUtilTest(String name) {
        super(name);
    }

    public void testGetDatePattern() throws Exception {
        String pattern = DateUtil.getDatePattern();
        assertTrue(pattern.equals("dd/MM/yyyy"));
    }

    public void testGetNonEnglishDatePattern() {
        LocaleContextHolder.setLocale(new Locale("nl"));
        assertEquals("dd-MMM-yyyy", DateUtil.getDatePattern());

        LocaleContextHolder.setLocale(new Locale("fr"));
        assertEquals("dd/MM/yyyy", DateUtil.getDatePattern());

        // non-existant bundle should default to default
        LocaleContextHolder.setLocale(new Locale("de"));
        assertEquals("dd/MM/yyyy", DateUtil.getDatePattern());
    }

    public void testGetDate() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("db date to convert: " + new Date());
        }

        String date = DateUtil.getDate(new Date());

        if (log.isDebugEnabled()) {
            log.debug("converted ui date: " + date);
        }

        assertTrue(date != null);
    }

    public void testGetDateTime() {
        if (log.isDebugEnabled()) {
            log.debug("entered 'testGetDateTime' method");
        }
        String now = DateUtil.getTimeNow(new Date());
        assertTrue(now != null);
        log.debug(now);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(DateUtilTest.class);
    }
}
