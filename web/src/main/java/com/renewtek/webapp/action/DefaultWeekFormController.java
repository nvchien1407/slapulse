//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashSet;
import java.util.Locale;

import com.renewtek.model.DayModel;
import com.renewtek.model.DefaultWeekDay;
import com.renewtek.model.Reference;
import com.renewtek.model.WorkHourRange;
import com.renewtek.service.CalendarDateManager;
import com.renewtek.service.DefaultWeekManager;
import com.renewtek.service.ReferenceManager;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

public class DefaultWeekFormController extends BaseFormController {

   private DefaultWeekManager defaultWeekManager = null;
   private ReferenceManager referenceManager = null;
   private CalendarDateManager calendarDateManager = null;

   public void setCalendarDateManager(CalendarDateManager calendarDateManager) {
      this.calendarDateManager = calendarDateManager;
   }

   public void setDefaultWeekManager(DefaultWeekManager defaultWeekManager) {
      this.defaultWeekManager = defaultWeekManager;
   }

   protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
      super.initBinder(request, binder);

      ReferencePropertyEditor ed4 = new ReferencePropertyEditor();
      CheckboxPropertyEditor chkEd = new CheckboxPropertyEditor();
      ed4.setReferenceManager(this.referenceManager);
      binder.registerCustomEditor(Reference.class, "region", ed4);
      binder.registerCustomEditor(Boolean.class, null, chkEd);
   }

   protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors)
         throws Exception {
      request.setAttribute("hours", this.calendarDateManager.getHours());
      request.setAttribute("minutes", this.calendarDateManager.getMinutes());
      return super.showForm(request, response, errors);
   }

   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      String id = request.getParameter("id");
      DefaultWeekDay day = null;
      if (id != null) {
         day = (DefaultWeekDay) this.calendarDateManager.getDayModel(id, "DefaultWeekDay");
         if ((day.getWorkHourRanges() == null || day.getWorkHourRanges().size() == 0)
               && (!day.getNonWorkingDay())) {
            DayModel defaultDay = getDefaultDay(day.getRegion());
            assignWorkHourRanges(day, defaultDay);
         }
      }
      else {
         String regionId = request.getParameter("region");
         String name = request.getParameter("day");
         Reference region = this.referenceManager.getReference(regionId);
         DayModel defaultDay = getDefaultDay(region);
         day = new DefaultWeekDay();
         day.setDay(name);
         day.setRegion(region);
         day.setDefaultDay(false);
         assignWorkHourRanges(day, defaultDay);
      }
      return day;
   }

   private void assignWorkHourRanges(DayModel day, DayModel defaultDay) {
      day.setWorkHourRanges(new LinkedHashSet<WorkHourRange>());
      for (WorkHourRange range : defaultDay.getWorkHourRanges()) {
         WorkHourRange newRange = new WorkHourRange();
         newRange.setDay(day);
         newRange.setFromTime(range.getFromTime());
         newRange.setToTime(range.getToTime());
         day.getWorkHourRanges().add(newRange);
      }
   }

   public DayModel getDefaultDay(Reference cal) {

      for (DayModel day : cal.getDefaultWeekDays()) {
         if (day.getDefaultDay()) {
            return day;
         }
      }
      return null;
   }

   public ModelAndView onSubmit(HttpServletRequest request,
                                HttpServletResponse response,
                                Object command,
                                BindException errors) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'onSubmit' method...");
      }

      DefaultWeekDay defaultWeek = (DefaultWeekDay) command;
      Locale locale = request.getLocale();
      boolean isNew = defaultWeek.getId() == null;
      if (request.getParameter("delete") != null) {
         Reference cal = defaultWeek.getRegion();
         cal.getDefaultWeekDays().remove(defaultWeek);
         defaultWeek.setRegion(null);
         this.referenceManager.saveReference(cal);

         saveMessage(request, getText("defaultWeek.deleted", locale));
         return new ModelAndView("redirect:defaultWeeks.html", "region", cal.getId());
      }
      else {
         if (defaultWeek.getNonWorkingDay() == null)
            defaultWeek.setNonWorkingDay(false);
         if (!isNew && defaultWeek.getNonWorkingDay() && defaultWeek.getWorkHourRanges() != null) {
            for (WorkHourRange w : defaultWeek.getWorkHourRanges()) {
               w.setDay(null);
            }
            defaultWeek.getWorkHourRanges().clear();
         }

         this.calendarDateManager.saveCalendarDate(defaultWeek);

         String key = (isNew) ? "defaultWeek.added" : "defaultWeek.updated";
         saveMessage(request, getText(key, locale));

         return new ModelAndView("redirect:editDefaultWeek.html", "id", defaultWeek.getId());
      }
   }

   public void setReferenceManager(ReferenceManager referenceManager) {
      this.referenceManager = referenceManager;
   }

   public DefaultWeekManager getDefaultWeekManager() {
      return defaultWeekManager;
   }

}
