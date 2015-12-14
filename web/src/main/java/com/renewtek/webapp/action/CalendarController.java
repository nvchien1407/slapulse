//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.renewtek.service.CalendarDateManager;

public class CalendarController implements Controller {

   private final Logger log = LoggerFactory.getLogger(CalendarController.class);
   private CalendarDateManager calendarDateManager = null;

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'handleRequest' method...");
      }
      request.setAttribute("regions", this.calendarDateManager.getCalendars());
      return new ModelAndView("calendar");
   }

   public void setCalendarDateManager(CalendarDateManager calendarDateManager) {
      this.calendarDateManager = calendarDateManager;
   }
}
