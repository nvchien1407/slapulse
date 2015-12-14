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
import com.renewtek.model.WorkHourRange;
import com.renewtek.service.CalendarDateManager;
import com.renewtek.service.WorkHourRangeManager;

public class WorkHourRangeController implements Controller {

   private final Logger log = LoggerFactory.getLogger(getClass());

   private WorkHourRangeManager workHourRangeManager = null;
   private CalendarDateManager calendarDateManager = null;

   public void setWorkHourRangeManager(WorkHourRangeManager workHourRangeManager) {
      this.workHourRangeManager = workHourRangeManager;
   }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'handleRequest' method...");
      }
      //String dayId = request.getParameter("dayId");
      //DayModel day = this.calendarDateManager.getDayModel(dayId, "");
      WorkHourRange workHourRange = new WorkHourRange();
      // populate object with request parameters
      BeanUtils.populate(workHourRange, request.getParameterMap());

      return new ModelAndView("workHourRangeList", Constants.WORKHOURRANGE_LIST,
            workHourRangeManager.getWorkHourRanges(workHourRange));
   }

   public void setCalendarDateManager(CalendarDateManager calendarDateManager) {
      this.calendarDateManager = calendarDateManager;
   }

   public CalendarDateManager getCalendarDateManager() {
      return calendarDateManager;
   }

}
