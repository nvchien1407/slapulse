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

import com.renewtek.Constants;
import com.renewtek.model.CalendarDate;
import com.renewtek.service.Manager;

public class CalendarDateController implements Controller {

   private final Logger log = LoggerFactory.getLogger(CalendarDateController.class);
   private Manager manager = null;

   public void setManager(Manager manager) {
      this.manager = manager;
   }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'handleRequest' method...");
      }

      return new ModelAndView("calendarDateList", Constants.CALENDARDATE_LIST, manager.getObjects(CalendarDate.class));
   }
}
