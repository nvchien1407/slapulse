//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckboxPropertyEditor extends PropertyEditorSupport {

   protected final transient Logger log = LoggerFactory.getLogger(getClass());

   public void setAsText(String text) {
      setValue(Boolean.TRUE);
   }

   public String getAsText(Object value) {
      return ((Boolean) value).toString();
   }
}