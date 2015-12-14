//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;

import com.renewtek.model.CalendarDate;
import com.renewtek.model.DayModel;
import com.renewtek.model.WorkHourRange;
import com.renewtek.service.CalendarDateManager;
import com.renewtek.service.WorkHourRangeManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class WorkHourRangeFormController extends BaseFormController {

   private WorkHourRangeManager workHourRangeManager = null;
   private CalendarDateManager calendarDateManager = null;

   public void setWorkHourRangeManager(WorkHourRangeManager workHourRangeManager) {
      this.workHourRangeManager = workHourRangeManager;
   }

   private boolean isOverlap(WorkHourRange range) {
      for (WorkHourRange r : range.getDay().getWorkHourRanges()) {
         if (range.getId() != null && range.getId().intValue() != r.getId().intValue()
            || (range.getId() == null && r.getId() != null)) {
            if ((range.getFromTime().getTime() > r.getFromTime().getTime() && range.getFromTime().getTime() < r
               .getToTime().getTime())
               || (range.getToTime().getTime() > r.getFromTime().getTime() && range.getFromTime().getTime() < r
               .getToTime().getTime())) {
               return true;
            }
         }
      }
      return false;
   }

   protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
      WorkHourRange range = (WorkHourRange) command;
      if (range.getFromTime().getTime() - range.getToTime().getTime() >= 0) {
         String[] args = { "Start Time" };
         errors.rejectValue("startMinute", "errors.StartEndTime", args, "");
      }
      boolean overlap = this.isOverlap(range);
      if (overlap) {
         String[] args = { "Start Time" };
         errors.rejectValue("startMinute", "errors.overlap", args, "");
      }
   }

   protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors)
         throws Exception {
      request.setAttribute("hours", this.calendarDateManager.getHours());
      request.setAttribute("minutes", this.calendarDateManager.getMinutes());
      return super.showForm(request, response, errors);
   }

   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      String id = request.getParameter("id");
      String dayType = request.getParameter("dayType");
      DayModel day;
      WorkHourRange workHourRange;
      if (!StringUtils.isEmpty(id)) {
         workHourRange = workHourRangeManager.getWorkHourRange(id);
         day = this.calendarDateManager.getDayModel(workHourRange.getDay().getId().toString(), dayType);
      }
      else {
         workHourRange = new WorkHourRange();
         String dayId = request.getParameter("dayId");
         day = this.calendarDateManager.getDayModel(dayId, dayType);
         workHourRange.setDay(day);
      }

      try {
         request.setAttribute("calendarDate", day);
      }
      catch (Exception e) {
         request.setAttribute("weekDay", day);
      }
      return workHourRange;
   }

   public ModelAndView onSubmit(HttpServletRequest request,
                                HttpServletResponse response,
                                Object command,
                                BindException errors) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'onSubmit' method...");
      }

      WorkHourRange workHourRange = (WorkHourRange) command;
      boolean isNew = (workHourRange.getId() == null);
      Locale locale = request.getLocale();
      String dayType = request.getParameter("dayType");
      HashMap<String, Object> map = new HashMap<String, Object>();
      String url = null;
      if (dayType.equals("CalendarDate")) {
         CalendarDate day = (CalendarDate) calendarDateManager.getDayModel(workHourRange.getDay().getId().toString(),
               dayType);
         map.put("region", day.getRegion().getId());
         map.put("date", day.getDateInfoString());
         url = "redirect:editCalendarDate.html";
      }
      else {
         if (dayType.equals("DefaultWeekDay")) {
            map.put("id", workHourRange.getDay().getId());
            url = "redirect:editDefaultWeek.html";
         }
         else {
            if (dayType.equals("DefaultDay")) {
               map.put("name", workHourRange.getDay().getRegion().getItemName());
               url = "redirect:editDayModel.html";
            }
         }

      }

      if (request.getParameter("delete") != null) {
         DayModel d = workHourRange.getDay();
         workHourRange.setDay(null);
         d.getWorkHourRanges().remove(workHourRange);
         this.calendarDateManager.saveCalendarDate(d);
         saveMessage(request, getText("workHourRange.deleted", locale));
      }
      else {
         workHourRangeManager.saveWorkHourRange(workHourRange);

         String key = (isNew) ? "workHourRange.added" : "workHourRange.updated";
         saveMessage(request, getText(key, locale));
      }

      return new ModelAndView(url, map);
   }

   public void setCalendarDateManager(CalendarDateManager calendarDateManager) {
      this.calendarDateManager = calendarDateManager;
   }
}
