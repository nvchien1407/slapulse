//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.renewtek.Constants;
import com.renewtek.model.ServiceLevelAgreement;
import com.renewtek.service.ServiceLevelAgreementManager;

public class ServiceLevelAgreementController implements Controller {

   private final Logger log = LoggerFactory.getLogger(getClass());
   private ServiceLevelAgreementManager serviceLevelAgreementManager = null;

   public void setServiceLevelAgreementManager(ServiceLevelAgreementManager serviceLevelAgreementManager) {
      this.serviceLevelAgreementManager = serviceLevelAgreementManager;
   }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'handleRequest' method...");
      }

      ServiceLevelAgreement serviceLevelAgreement = new ServiceLevelAgreement();
      // populate object with request parameters
      BeanUtils.populate(serviceLevelAgreement, request.getParameterMap());

      return new ModelAndView("serviceLevelAgreementList", Constants.SERVICELEVELAGREEMENT_LIST,
            serviceLevelAgreementManager.getServiceLevelAgreements(serviceLevelAgreement));
   }
}
