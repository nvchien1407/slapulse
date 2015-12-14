// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import com.renewtek.dao.SLACaseTypeManagerDAO;
import com.renewtek.model.BusinessProcess;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

public class BusinessProcessFormControllerTest extends BaseControllerTestCase {

   private BusinessProcessFormController c;

   private MockHttpServletRequest request;

   private ModelAndView mv;

   protected void setUp() throws Exception {
      // needed to initialize a user
      super.setUp();
      c = (BusinessProcessFormController) ctx.getBean("businessProcessFormController");
      c.setSlaCaseTypeManagerDAO((SLACaseTypeManagerDAO) ctx.getBean("slaCaseTypeManagerDAO"));
   }

   protected void tearDown() throws Exception {
      super.tearDown();
      c = null;
   }

   public void testEdit() throws Exception {
      log.debug("testing edit...");
      request = newGet("/editBusinessProcess.html");
      request.addParameter("id", "1");

      mv = c.handleRequest(request, new MockHttpServletResponse());

      assertEquals("businessProcessForm", mv.getViewName());
   }

   @SuppressWarnings("deprecation")
   public void testSave() throws Exception {
      request = newGet("/editBusinessProcess.html");
      request.addParameter("id", "1");

      mv = c.handleRequest(request, new MockHttpServletResponse());

      BusinessProcess businessProcess = (BusinessProcess) mv.getModel().get(c.getCommandName());
      assertNotNull(businessProcess);
      request = newPost("/editBusinessProcess.html");
      super.objectToRequestParameters(businessProcess, request);

      // update the form's fields and add it back to the request
      businessProcess
            .setDescription("UuXqMtLsVeKyKcGnHgNmVvPlDgItUtInJvDtWgDjWeFdIvJgUjIiHtJkJiMlAgGrXnOzSrAlTqSvVuMxAwNsScGiIjAsNoVoReWsQnVuHdNnHrIoXrZsGaLzKiFpKjAjTfVxRcPyOiDnZnYkOmXlOdJaDpIsJhZkTbOoKiTyAdJdPkVoGqByZnAnRgIpYlNoIzGyGeJwUiBaGzArCdUeZhAuBxSiFkGpPiDqDwKyIgBfMsViLbAlVyMtCnPpFiH");
      request.addParameter("businessProcessName", businessProcess.getName().getId().toString());
      request.addParameter("businessProcessType", businessProcess.getType().getId().toString());
      request.addParameter("description", businessProcess.getDescription());
      
      mv = c.handleRequest(request, new MockHttpServletResponse());
      Errors errors = (Errors) mv.getModel().get(ERROR_KEY_PREFIX + "businessProcess");

      if (errors != null) {
         log.debug(errors);
      }
      assertEquals(2, errors.getAllErrors().size());
      assertNull(request.getSession().getAttribute("messages"));
   }

   public void testRemove() throws Exception {
      request = newPost("/editBusinessProcess.html");
      request.addParameter("delete", "");
      request.addParameter("id", "2");
      mv = c.handleRequest(request, new MockHttpServletResponse());
      assertNull(request.getSession().getAttribute("messages"));
   }
}
