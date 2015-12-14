// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import com.renewtek.model.ServiceLevelAgreement;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

public class ServiceLevelAgreementFormControllerTest extends BaseControllerTestCase {

   private ServiceLevelAgreementFormController c;

   private MockHttpServletRequest request;

   private ModelAndView mv;

   protected void setUp() throws Exception {
      // needed to initialize a user
      super.setUp();
      c = (ServiceLevelAgreementFormController) ctx.getBean("serviceLevelAgreementFormController");
   }

   protected void tearDown() throws Exception {
      super.tearDown();
      c = null;
   }

   public void testEdit() throws Exception {
      log.debug("testing edit...");
      request = newGet("/editServiceLevelAgreement.html");
      request.addParameter("id", "1");

      mv = c.handleRequest(request, new MockHttpServletResponse());

      assertEquals("serviceLevelAgreementForm", mv.getViewName());
   }

   @SuppressWarnings("deprecation")
   public void testSave() throws Exception {
      request = newGet("/editServiceLevelAgreement.html");
      request.addParameter("id", "1");

      mv = c.handleRequest(request, new MockHttpServletResponse());

      ServiceLevelAgreement serviceLevelAgreement = (ServiceLevelAgreement) mv.getModel().get(c.getCommandName());
      assertNotNull(serviceLevelAgreement);
      request = newPost("/editServiceLevelAgreement.html");
      super.objectToRequestParameters(serviceLevelAgreement, request);

      // update the form's fields and add it back to the request
      serviceLevelAgreement.setDeadlineType("NfEsMtRbYnOtLyXlQiDqHzRiD");
      serviceLevelAgreement.setDurationType("VaGzAwVmZzRwEmJaIuVzCtZnB");
      serviceLevelAgreement.setName("KhFdZpPgJiTeDsLfJqMwAnHyP");
      serviceLevelAgreement.setWorkTime("HjDhGyPaSqXsOoYxEdXcSnErO");
      serviceLevelAgreement.setStopSlaWhenPaused(false);
      
      // in the real situation the JSP page uses the name durationGroup for the radio button
      request.addParameter("durationGroup", request.getParameter("durationType"));
      mv = c.handleRequest(request, new MockHttpServletResponse());
      Errors errors = (Errors) mv.getModel().get(ERROR_KEY_PREFIX + "serviceLevelAgreement");

      if (errors != null) {
         log.debug(errors);
      }
      // Change from 9 to 10 because of adding notifyDeadlineApproachingForwarding 
      // which type is boolean was not handled by objectToRequestParameters
      assertEquals(10, errors.getAllErrors().size());
      assertNull(request.getSession().getAttribute("messages"));
   }

   public void testRemove() throws Exception {
      request = newPost("/editServiceLevelAgreement.html");
      request.addParameter("delete", "");
      request.addParameter("id", "2");
      
      request.addParameter("durationGroup", "T_Formulae");
      mv = c.handleRequest(request, new MockHttpServletResponse());
      assertNull(request.getSession().getAttribute("messages"));
   }
}
