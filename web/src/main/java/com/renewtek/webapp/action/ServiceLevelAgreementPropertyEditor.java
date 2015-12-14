//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.beans.PropertyEditorSupport;

import com.renewtek.model.ServiceLevelAgreement;
import com.renewtek.service.ServiceLevelAgreementManager;

public class ServiceLevelAgreementPropertyEditor extends PropertyEditorSupport {

   private ServiceLevelAgreementManager serviceLevelAgreementManager = null;

   public void setServiceLevelAgreementManager(ServiceLevelAgreementManager serviceLevelAgreementManager) {
      this.serviceLevelAgreementManager = serviceLevelAgreementManager;
   }

   public void setAsText(String text) {
      try {
         int id = Integer.parseInt(text);

         if (id < 0)
            setValue(null);
         else
            setValue(this.serviceLevelAgreementManager.getServiceLevelAgreement(text));

      }
      catch (Exception e) {
         setValue(null);
      }
   }

   public String getAsText(Object value) {
      ServiceLevelAgreement sla = (ServiceLevelAgreement) value;
      return sla.getId().toString();
   }
}
