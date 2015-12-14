//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.renewtek.model.CalendarDate;
import com.renewtek.model.DayModel;
import com.renewtek.model.Reference;
import com.renewtek.model.WorkHourRange;
import com.renewtek.service.CalendarDateManager;
import com.renewtek.service.DefaultWeekManager;
import com.renewtek.service.ReferenceManager;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

public class CalendarDateFormController extends BaseFormController {

   private CalendarDateManager calendarDateManager = null;
   private ReferenceManager referenceManager = null;
   private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
   private DefaultWeekManager defaultWeekManager = null;

   public void setDefaultWeekManager(DefaultWeekManager defaultWeekManager) {
      this.defaultWeekManager = defaultWeekManager;
   }

   public void setReferenceManager(ReferenceManager referenceManager) {
      this.referenceManager = referenceManager;
   }

   protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
      CalendarDate day = (CalendarDate) command;
      WorkHourRange range = day.getWorkHourRange();
      if (!day.getNonWorkingDay())
         if (range == null || range.getFromTime() == null) {
            errors.rejectValue("fromTime", "errors.weekday.time.exist");
         }
         else {
            if (range == null || range.getToTime() == null) {
               errors.rejectValue("toTime", "errors.weekday.time.exist");
            }
            else {
               //spanning time is allow, so this rule does not hold
//               if (!WorkHourRange.isBeforeTimes(range.getFromTime(), range.getToTime()))
//                  errors.rejectValue("toTime", "errors.starttime.after.endtime");
            }
         }
   }

   protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors)
         throws Exception {
      request.setAttribute("hours", this.calendarDateManager.getHours());
      request.setAttribute("minutes", this.calendarDateManager.getMinutes());
      return super.showForm(request, response, errors);
   }

   protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
      super.initBinder(request, binder);

      ReferencePropertyEditor ed4 = new ReferencePropertyEditor();
      //CheckboxPropertyEditor chkEd = new CheckboxPropertyEditor();
      ed4.setReferenceManager(this.referenceManager);
      binder.registerCustomEditor(Reference.class, "region", ed4);
      //binder.registerCustomEditor(Boolean.class, "nonWorkingDay", chkEd);
   }

   public void setCalendarDateManager(CalendarDateManager manager) {
      this.calendarDateManager = manager;
   }

   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      this.dateFormat.setLenient(false);
      Date date = this.dateFormat.parse(request.getParameter("date"));
      String regionId = request.getParameter("region");
      Reference region = this.referenceManager.getReference(regionId);

      CalendarDate calendarDate = new CalendarDate();
      calendarDate.setDateInfo(date);
      calendarDate.setRegion(region);
      calendarDate.setDefaultDay(false);
      List<CalendarDate> dates = this.calendarDateManager.getCalendarDateWithoutTimeZone(date, region);
      if (dates != null && dates.size() > 0) {
         calendarDate = dates.get(0);
         if (!calendarDate.getNonWorkingDay()
               && (calendarDate.getWorkHourRanges() == null || calendarDate.getWorkHourRanges().size() == 0)) {
            DayModel defaultDay = this.defaultWeekManager.getDayByRegion(date, region);
            if (defaultDay != null) {
               if (!calendarDate.getNonWorkingDay()) {
                  setWorkingHours(calendarDate, defaultDay);
               }
            }
         }
         else {
            calendarDate.setIsDefault(false);
         }
      }
      else {
         DayModel defaultDay = this.defaultWeekManager.getDayByRegion(date, region);
         calendarDate.setIsDefault(true);
         if (defaultDay != null) {
            calendarDate.setNonWorkingDay(defaultDay.getNonWorkingDay());
            if (!calendarDate.getNonWorkingDay()) {
               setWorkingHours(calendarDate, defaultDay);
            }
         }
      }
      return calendarDate;
   }

   private void setWorkingHours(CalendarDate day, DayModel defaultDay) {
      if (defaultDay.getWorkHourRanges() == null)
         return;
      if (day.getWorkHourRanges() == null)
         day.setWorkHourRanges(new HashSet<WorkHourRange>());
      day.setIsDefault(true);
      for (WorkHourRange range : defaultDay.getWorkHourRanges()) {
         WorkHourRange newRange = new WorkHourRange();
         newRange.setFromTime(range.getFromTime());
         newRange.setToTime(range.getToTime());
         newRange.setDay(day);
         day.getWorkHourRanges().add(newRange);
      }
   }

   public ModelAndView onSubmit(HttpServletRequest request,
                                HttpServletResponse response,
                                Object command,
                                BindException errors) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'onSubmit' method...");
      }

      CalendarDate calendarDate = (CalendarDate) command;
      if (request.getParameter("cancelBtn") != null) {
      }
      else {
         boolean isNew = (calendarDate.getId() == null);
         Locale locale = request.getLocale();

         if (request.getParameter("delete") != null) {
            this.calendarDateManager.removeCalendarDate(calendarDate.getId().toString());

            saveMessage(request, getText("calendarDate.deleted", locale));
         }
         else {
            //if(!isNew && calendarDate.getNonWorkingDay().booleanValue() && calendarDate.getWorkHourRanges() != null){
            if (calendarDate.getNonWorkingDay() && calendarDate.getWorkHourRanges() != null) {
               for (WorkHourRange w : calendarDate.getWorkHourRanges()) {
                  w.setDay(null);
               }
               calendarDate.getWorkHourRanges().clear();
            }

            this.calendarDateManager.saveCalendarDate(calendarDate);

            String key = (isNew) ? "calendarDate.added" : "calendarDate.updated";
            saveMessage(request, getText(key, locale));
         }
      }
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("region", calendarDate.getRegion().getId());
      map.put("date", calendarDate.getDateInfoString());

      return new ModelAndView("redirect:editCalendar.html", map);
   }
}
