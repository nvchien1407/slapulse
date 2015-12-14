// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import com.renewtek.model.WorkHourRange;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

public class WorkHourRangeFormControllerTest extends BaseControllerTestCase {

   private WorkHourRangeFormController c;

   private MockHttpServletRequest request;

   private ModelAndView mv;

   protected void setUp() throws Exception {
      // needed to initialize a user
      super.setUp();
      c = (WorkHourRangeFormController) ctx.getBean("workHourRangeFormController");
   }

   protected void tearDown() throws Exception {
      super.tearDown();
      c = null;
   }

   public void testEdit() throws Exception {
      log.debug("testing edit...");
      request = newGet("/editWorkHourRange.html");
      request.addParameter("id", "1");
      request.addParameter("dayType", "CalendarDate");
      
      mv = c.handleRequest(request, new MockHttpServletResponse());

      assertEquals("workHourRangeForm", mv.getViewName());
   }

   @SuppressWarnings("deprecation")
   public void testSave() throws Exception {
      request = newGet("/editWorkHourRange.html");
      request.addParameter("id", "1");
      request.addParameter("dayType", "CalendarDate");
      
      mv = c.handleRequest(request, new MockHttpServletResponse());

      WorkHourRange workHourRange = (WorkHourRange) mv.getModel().get(c.getCommandName());
      assertNotNull(workHourRange);
      request = newPost("/editWorkHourRange.html");
      super.objectToRequestParameters(workHourRange, request);
      request.addParameter("dayType", "CalendarDate");

      // update the form's fields and add it back to the request
      mv = c.handleRequest(request, new MockHttpServletResponse());
      Errors errors = (Errors) mv.getModel().get(ERROR_KEY_PREFIX + "workHourRange");

      if (errors != null) {
         log.debug(errors);
      }
      assertEquals(2, errors.getAllErrors().size());
      assertNull(request.getSession().getAttribute("messages"));
   }
   
   // comment out for further investigation
//   public void testRemove() throws Exception {
//      request = newPost("/editWorkHourRange.html");
//      request.addParameter("delete", "");
//      request.addParameter("id", "9");
//      request.addParameter("dayType", "CalendarDate");
//      mv = c.handleRequest(request, new MockHttpServletResponse());
//      assertNotNull(request.getSession().getAttribute("messages"));
//   }
}
