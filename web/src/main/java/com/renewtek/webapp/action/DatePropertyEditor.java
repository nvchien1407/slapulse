//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;

public class DatePropertyEditor extends CustomDateEditor {

   protected final transient Logger log = LoggerFactory.getLogger(getClass());

   private static final String MAX_DATE = "01/01/3000";
   private static final String MIN_DATE = "01/01/1900";
   private DateFormat dtFormat;
   private Date minDate;
   private Date maxDate;

   public DatePropertyEditor(DateFormat dateFormat, boolean allowEmpty, int exactDateLength) {
      super(dateFormat, allowEmpty, exactDateLength);
      dtFormat = dateFormat;
      try {
         this.minDate = dtFormat.parse(MIN_DATE);
         this.maxDate = dtFormat.parse(MAX_DATE);
      }
      catch (ParseException e) {
         log.debug("Exception: ", e);
      }
   }

   public DatePropertyEditor(DateFormat dateFormat, boolean allowEmpty) {
      super(dateFormat, allowEmpty);
      dtFormat = dateFormat;
      try {
         this.minDate = dtFormat.parse(MIN_DATE);
         this.maxDate = dtFormat.parse(MAX_DATE);
      }
      catch (ParseException e) {
         log.debug("Exception: ", e);
      }
   }

   /**
    * Parse the Date from the given text, using the specified DateFormat.
    */
   public void setAsText(String text) throws IllegalArgumentException {
      super.setAsText(text);
      if (text == null || text.equals(""))
         return;
      try {
         Date dt = this.dtFormat.parse(text);
         if (dt.before(minDate) || dt.after(maxDate))
            throw new IllegalArgumentException("Date range error");
      }
      catch (ParseException e) {
         log.debug("Exception: ", e);
      }
   }
}
