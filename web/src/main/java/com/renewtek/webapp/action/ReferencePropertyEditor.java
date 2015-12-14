//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.beans.PropertyEditorSupport;

import com.renewtek.model.Reference;
import com.renewtek.service.ReferenceManager;

public class ReferencePropertyEditor extends PropertyEditorSupport {

   private ReferenceManager referenceManager = null;

   public void setReferenceManager(ReferenceManager referenceManager) {
      this.referenceManager = referenceManager;
   }

   public void setAsText(String text) {
      Reference ref = new Reference();
      try {
         int id = Integer.parseInt(text);
         if (id < 0)
            setValue(ref);
         else
            setValue(this.referenceManager.getReference(text));
      }
      catch (Exception e) {
         setValue(ref);
      }
   }

   public String getAsText(Object value) {
      Reference ref = (Reference) value;
      return ref.getItemName();
   }
}