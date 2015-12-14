//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import com.renewtek.Constants;
import com.renewtek.model.DayModel;
import com.renewtek.model.Reference;
import com.renewtek.service.CalendarDateManager;
import com.renewtek.service.ReferenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class DayModelController implements Controller {

   private final Logger log = LoggerFactory.getLogger(DayModelController.class);
   private CalendarDateManager calendarDateManager = null;
   private ReferenceManager referenceManager = null;

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'handleRequest' method...");
      }
      String name = request.getParameter("name") == null ? "" : request.getParameter("name");
      Reference ref = new Reference();
      ref.setSubGroupName(Constants.REFERENCE_GROUPNAME_CALENDAR);
      ref.setItemName(name);
      List<Reference> refs = this.referenceManager.getReferencesByTemplate(ref);
      if (refs != null && refs.size() > 0) {
         ref = refs.get(0);
      }
      DayModel d = new DayModel();
      d.setDefaultDay(true);
      d.setRegion(ref);
      List<DayModel> days = this.calendarDateManager.getDayModelByTemplate(d);

      return new ModelAndView("dayModelForm", "defaultDay", days.get(0));
   }

   public void setCalendarDateManager(CalendarDateManager calendarDateManager) {
      this.calendarDateManager = calendarDateManager;
   }

   public void setReferenceManager(ReferenceManager referenceManager) {
      this.referenceManager = referenceManager;
   }
}
