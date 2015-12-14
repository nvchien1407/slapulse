// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.renewtek.Constants;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.mock.web.MockHttpServletRequest;

public class ServiceLevelAgreementControllerTest extends BaseControllerTestCase {

   @SuppressWarnings("unchecked")
   public void testHandleRequest() throws Exception {
      ServiceLevelAgreementController c = (ServiceLevelAgreementController) ctx
            .getBean("serviceLevelAgreementController");
      ModelAndView mav = c.handleRequest(new MockHttpServletRequest(), (HttpServletResponse) null);
      Map m = mav.getModel();
      assertNotNull(m.get(Constants.SERVICELEVELAGREEMENT_LIST));
      assertEquals(mav.getViewName(), "serviceLevelAgreementList");
   }
}
