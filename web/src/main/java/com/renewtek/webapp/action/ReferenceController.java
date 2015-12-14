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
import com.renewtek.model.Reference;
import com.renewtek.service.ReferenceManager;

public class ReferenceController implements Controller {

   private final Logger log = LoggerFactory.getLogger(getClass());
   private ReferenceManager referenceManager = null;

   public void setReferenceManager(ReferenceManager referenceManager) {
      this.referenceManager = referenceManager;
   }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'handleRequest' method...");
      }

      Reference reference = new Reference();
      // populate object with request parameters
      BeanUtils.populate(reference, request.getParameterMap());

      return new ModelAndView("referenceList", Constants.REFERENCE_LIST, referenceManager.getReferences(reference));
   }
}
