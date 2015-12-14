//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.renewtek.Constants;
import com.renewtek.model.CalendarDays;
import com.renewtek.model.DefaultWeekDay;
import com.renewtek.model.Reference;
import com.renewtek.model.WorkHourRange;
import com.renewtek.service.CalendarDateManager;
import com.renewtek.service.CalendarManager;
import com.renewtek.service.ReferenceManager;

public class CalendarFormController extends BaseFormController {

   private CalendarDateManager calendarDateManager = null;
   private ReferenceManager referenceManager = null;
   private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
   private CalendarManager calendarManager = null;
   private HashMap<String, String> locationMap = new HashMap<String, String>();

   public CalendarManager getCalendarManager() {
      return calendarManager;
   }

   public void setCalendarManager(CalendarManager calendarManager) {
      this.calendarManager = calendarManager;
   }

   public void setReferenceManager(ReferenceManager referenceManager) {
      this.referenceManager = referenceManager;
   }

   public void setLocationMap(HashMap<String, String> locationMap) {
      this.locationMap = locationMap;
   }

   public HashMap<String, String> getLocationMap() {
      return locationMap;
   }

   protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
      CalendarDays calendar = (CalendarDays) command;
      if (calendar.getRegion().getSubGroupName() == null) {
         calendar.getRegion().setSubGroupName(Constants.REFERENCE_GROUPNAME_CALENDAR);
      }
      if (calendar.getRegion().getItemName().equals("")) {
         Object[] args = new Object[] { "Calendar Name" };
         errors.rejectValue("region.itemName", "errors.required", args, null);
      }

      List<Reference> l = this.referenceManager.getReferencesByTemplate(calendar.getRegion());
      if (l != null && l.size() > 0 && calendar.getRegion().getId() == null) {
         errors.rejectValue("region.itemName", "errors.calendar.exists");
      }
      int i = 0;
      boolean someWorkingDays = false;
      for (DefaultWeekDay day : calendar.getDefaultWeekDays()) {
         WorkHourRange range = day.getWorkHourRange();
         if (!day.getNonWorkingDay()) {
            someWorkingDays = true;
            if (range == null || range.getFromTime() == null) {
               errors.rejectValue("defaultWeekDays[" + i + "].fromTime", "errors.weekday.time.exist");
            }
            else {
               if (range == null || range.getToTime() == null) {
                  errors.rejectValue("defaultWeekDays[" + i + "].toTime", "errors.weekday.time.exist");
               }
               else {
                  // if(!WorkHourRange.isBeforeTimes(range.getFromTime(), range.getToTime()))
                  // errors.rejectValue("defaultWeekDays["+ i +"].toTime", "errors.starttime.after.endtime");
               }
            }
         }
         i++;
      }
      if (!someWorkingDays) {
         errors.rejectValue("defaultWeekDays[0].fromTime", "errors.no.working.days");
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
      CheckboxPropertyEditor chkEd = new CheckboxPropertyEditor();
      ed4.setReferenceManager(this.referenceManager);
      binder.registerCustomEditor(Reference.class, "region", ed4);
      binder.registerCustomEditor(Boolean.class, null, chkEd);
   }

   public void setCalendarDateManager(CalendarDateManager manager) {
      this.calendarDateManager = manager;
   }

   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      dateFormat.setLenient(false);
      String date = request.getParameter("calendarDate");
      String regionId = request.getParameter("region");
      List<Reference> regions = this.calendarDateManager.getCalendars();
      request.setAttribute("regions", regions);
      request.setAttribute("locationMap", locationMap);

      Reference region = null;

      if (regionId != null && !regionId.equals(""))
         region = this.referenceManager.getReference(regionId);
      Date dt = new Date();
      if (date != null) {
         dt = dateFormat.parse(date);
      }
      CalendarDays days = this.calendarManager.getCalendarDaysByMonthWithoutTimeZone(dt, request.getLocale(), region);
      //List ds = days.getDays();
      Calendar cal = Calendar.getInstance();
      cal.setTime(dt);
      cal.add(Calendar.YEAR, 1);
      String nextYear = dateFormat.format(cal.getTime());
      cal.add(Calendar.YEAR, -2);
      String prevYear = dateFormat.format(cal.getTime());
      request.setAttribute("nextYear", nextYear);
      request.setAttribute("prevYear", prevYear);
      if (days.getRegion() == null) {
         region = new Reference();
         region.setSubGroupName(Constants.REFERENCE_GROUPNAME_CALENDAR);
         region.setItemName("");
         days.setRegion(region);
      }
      return days;
   }

   public ModelAndView onSubmit(HttpServletRequest request,
                                HttpServletResponse response,
                                Object command,
                                BindException errors) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'onSubmit' method...");
      }

      CalendarDays calendar = (CalendarDays) command;
      if (request.getParameter("cancelBtn") != null) {
      }
      else {
         boolean isNew = (calendar.getRegion().getId() == null);
         Locale locale = request.getLocale();

         if (request.getParameter("delete") != null) {
            //this.calendarDateManager.removeCalendarDate(calendarDate.getId().toString());
            saveMessage(request, getText("calendarDate.deleted", locale));
         }
         else {
            this.calendarDateManager.saveCalendar(calendar);

            String key = (isNew) ? "calendarDate.added" : "calendarDate.updated";
            saveMessage(request, getText(key, locale));
            return new ModelAndView("redirect:editCalendar.html", "region", calendar.getRegion().getId());
         }
      }
      return new ModelAndView("redirect:calendar.html");
   }

}