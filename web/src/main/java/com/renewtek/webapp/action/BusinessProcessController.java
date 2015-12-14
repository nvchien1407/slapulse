//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.renewtek.Constants;
import com.renewtek.model.BusinessProcess;
import com.renewtek.service.BusinessProcessManager;

public class BusinessProcessController implements Controller {

   private final Log log = LogFactory.getLog(BusinessProcessController.class);
   private BusinessProcessManager businessProcessManager = null;

   public void setBusinessProcessManager(BusinessProcessManager businessProcessManager) {
      this.businessProcessManager = businessProcessManager;
   }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'handleRequest' method...");
      }

      BusinessProcess businessProcess = new BusinessProcess();
      // populate object with request parameters
      BeanUtils.populate(businessProcess, request.getParameterMap());

      return new ModelAndView("businessProcessList", Constants.BUSINESSPROCESS_LIST,
            businessProcessManager.getBusinessProcesses(businessProcess));
   }
}
