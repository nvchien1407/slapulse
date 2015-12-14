//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.renewtek.model.DefaultWeek;
import com.renewtek.model.Reference;
import com.renewtek.service.CalendarDateManager;
import com.renewtek.service.DefaultWeekManager;
import com.renewtek.service.ReferenceManager;

public class DefaultWeekController implements Controller {

   private DefaultWeekManager defaultWeekManager = null;
   private ReferenceManager referenceManager = null;
   private CalendarDateManager calendarDateManager = null;

   public void setDefaultWeekManager(DefaultWeekManager defaultWeekManager) {
      this.defaultWeekManager = defaultWeekManager;
   }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
      String regionId = request.getParameter("region");
      Reference region = null;
      DefaultWeek defaultWeek = null;
      if (regionId != null) {
         region = this.referenceManager.getReference(regionId);
      }
      else {
         request.setAttribute("regions", this.calendarDateManager.getCalendars());
      }
      defaultWeek = defaultWeekManager.getDefaultWeek(region);
      return new ModelAndView("defaultWeekList", "defaultWeekList", defaultWeek);
   }

   public void setReferenceManager(ReferenceManager referenceManager) {
      this.referenceManager = referenceManager;
   }

   public void setCalendarDateManager(CalendarDateManager calendarDateManager) {
      this.calendarDateManager = calendarDateManager;
   }
}
