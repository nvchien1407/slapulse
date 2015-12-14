// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class DefaultWeekFormControllerTest extends BaseControllerTestCase {

   private DefaultWeekFormController c;

   private MockHttpServletRequest request;

   private ModelAndView mv;

   protected void setUp() throws Exception {
      // needed to initialize a user
      super.setUp();
      c = (DefaultWeekFormController) ctx.getBean("defaultWeekFormController");
   }

   protected void tearDown() throws Exception {
      super.tearDown();
      c = null;
   }

   public void testEdit() throws Exception {
      log.debug("testing edit...");
      request = newGet("/editDefaultWeek.html");
      request.addParameter("id", "2");

      mv = c.handleRequest(request, new MockHttpServletResponse());

      assertEquals("defaultWeekForm", mv.getViewName());
   }

   // comment out for further investigation
//   public void testRemove() throws Exception {
//      request = newPost("/editDefaultWeek.html");
//      request.addParameter("delete", "");
//      request.addParameter("id", "3");
//      mv = c.handleRequest(request, new MockHttpServletResponse());
//      assertNotNull(request.getSession().getAttribute("messages"));
//   }
}
