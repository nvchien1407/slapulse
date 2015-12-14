//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.renewtek.Constants;
import com.renewtek.model.BusinessProcess;
import com.renewtek.model.Reference;
import com.renewtek.model.ServiceLevelAgreement;
import com.renewtek.service.BusinessProcessManager;
import com.renewtek.service.ReferenceManager;
import com.renewtek.service.ServiceLevelAgreementManager;
import com.renewtek.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

public class ServiceLevelAgreementFormController extends BaseFormController {

   private ServiceLevelAgreementManager serviceLevelAgreementManager = null;
   private ReferenceManager referenceManager;
   private BusinessProcessManager businessProcessManager;

   public void setServiceLevelAgreementManager(ServiceLevelAgreementManager serviceLevelAgreementManager) {
      this.serviceLevelAgreementManager = serviceLevelAgreementManager;
   }

   protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
      super.initBinder(request, binder);

      ServiceLevelAgreementPropertyEditor slaEditor = new ServiceLevelAgreementPropertyEditor();
      slaEditor.setServiceLevelAgreementManager(this.serviceLevelAgreementManager);

      ReferencePropertyEditor editor = new ReferencePropertyEditor();
      editor.setReferenceManager(this.referenceManager);

      SimpleDateFormat timeFormat = new SimpleDateFormat(Constants.DEFAULT_TIME_FORMAT);
      timeFormat.setLenient(false);
      CustomDateEditor cde = new CustomDateEditor(timeFormat, true);

      binder.registerCustomEditor(ServiceLevelAgreement.class, "serviceLevelAgreement", slaEditor);
      binder.registerCustomEditor(Reference.class, "calendar", editor);
      binder.registerCustomEditor(Date.class, "fixedTimeDeadline", cde);
      binder.registerCustomEditor(Date.class, "fixedTimeThreshold", cde);
   }

   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      String id = request.getParameter("id");
      ServiceLevelAgreement serviceLevelAgreement = null;

      if (!StringUtils.isEmpty(id)) {
         serviceLevelAgreement = serviceLevelAgreementManager.getServiceLevelAgreement(id);
         if (serviceLevelAgreement.getNotificationThreshold() != null) {
            long millis = serviceLevelAgreement.getNotificationThreshold();
            long days = new Long("86400000");
            long hours = new Long("3600000");
            long minutes = new Long("60000");
            long x = millis / days;
            serviceLevelAgreement.setNotificationThresholdDays(new Integer(x + ""));
            x = (millis % days) / hours;
            serviceLevelAgreement.setNotificationThresholdHours(new Integer(x + ""));
            x = ((millis % days) % hours) / minutes;
            serviceLevelAgreement.setNotificationThresholdMinutes(new Integer(x + ""));
         }
      }
      else {
         serviceLevelAgreement = new ServiceLevelAgreement();
         serviceLevelAgreement.setDeadlineType(Constants.END_OF_BUSINESS_TYPE);
         serviceLevelAgreement.setDurationType(Constants.T_FORMULA_TYPE);
         serviceLevelAgreement.setWorkTime(Constants.WORKING_HOURS_TYPE);
         serviceLevelAgreement.setStopSlaWhenPaused(false);
         serviceLevelAgreement.setIncludeSpecialDays(Constants.NO_TYPE);
         serviceLevelAgreement.setNotifyDeadlineApproaching(false);
         serviceLevelAgreement.setNotifyDeadlineApproachingForwarding(false);
      }

      return serviceLevelAgreement;
   }

   protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors)
         throws Exception {

      List<String> durationMinutesList = this.serviceLevelAgreementManager.getMinutes();
      List<String> pauseThresholdMinutesList = this.serviceLevelAgreementManager.getMinutes();
      List<String> notificationThresholdMinutesList = this.serviceLevelAgreementManager.getMinutes();

      request.setAttribute("durationMinutesList", durationMinutesList);
      request.setAttribute("pauseThresholdMinutesList", pauseThresholdMinutesList);
      request.setAttribute("notificationThresholdMinutesList", notificationThresholdMinutesList);

      request.setAttribute("unassignedBusinessProcesses", businessProcessManager.getUnassignedBusinessProcesses());
      request.setAttribute("calendars", referenceManager.getReferencesByParam("Calendar"));

      return super.showForm(request, response, errors);
   }

   protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {

      /**
       * We have to set some of the model data at this point to that if we are
       * forced to return to display error messages, then the components are not
       * returned to thier original state.
       * 
       * The text boxes are bound to display the existing text, but the radio
       * buttons and checkboxes have to be set manually.
       * 
       */
      ServiceLevelAgreement sla = (ServiceLevelAgreement) command;

      if (sla.getName() == null || sla.getName().trim().equals(""))
         errors.rejectValue("name", null, null, "You must enter a Name");

      long totalDurationMillis = 0;

      String durationType = request.getParameter("durationGroup");
      String deadlineType = request.getParameter("deadlineGroup");
      String workTime = request.getParameter("workTimeGroup");
      if (durationType.equals(Constants.T_FORMULA_TYPE)) {
         sla.setDurationType(Constants.T_FORMULA_TYPE);
         if (deadlineType == null) {
            errors.rejectValue("deadlineType", null, null, "You must enter a Deadline Type");
         }
         else {
            sla.setDeadlineType(deadlineType);
         }
         if (sla.getTFormulaeDays() == null)
            errors.rejectValue("TFormulaeDays", null, null, "You must enter a number of Days");
         else {
            totalDurationMillis = DateUtil.getMillis(sla.getTFormulaeDays(), 0, 0);
            if (sla.getTFormulaeDays() < 1) {
               errors.rejectValue("TFormulaeDays", null, null, "You must enter a number of Days greater than zero");
            }
         }
      }
      else if (durationType.equals(Constants.DEADLINE_TYPE)) {
         String enableCutOffTime = request.getParameter("enableCutOffTimeCheck");
         if (StringUtils.isEmpty(enableCutOffTime)) {
            errors.rejectValue("enableCutOffTime", null, null, "Cut-Off Time must be enabled");
         }

         sla.setDurationType(Constants.DEADLINE_TYPE);

         Date ftd = sla.getFixedTimeDeadline();
         if (ftd == null) {
            errors.rejectValue("fixedTimeDeadline", null, null, "Deadline Time must be entered");
         }
         else {
            totalDurationMillis = DateUtil.getMillis(DateUtil.getHours(ftd), DateUtil.getMinutes(ftd));
         }

         Date ftt = sla.getFixedTimeThreshold();
         if (ftd != null && ftt != null) {
            if (DateUtil.isBefore(ftd, ftt)) {
               errors.rejectValue("fixedTimeDeadline", null, null, "Deadline Time is less than Cut-Off Time");
            }
         }
      }
      else {
         sla.setDurationType(Constants.DURATION_TYPE);
         sla.setWorkTime(workTime);
         Integer hours = sla.getDurationHours();
         Integer mins = sla.getDurationMinutes();
         if (hours == null) {
            errors.rejectValue("durationHours", null, null, "Duration Hours must be entered");
         }
         else if (hours < 1 && mins < 1) {
            errors.rejectValue("durationHours", null, null, "You must enter a Duration greater than zero");
         }
         else {
            totalDurationMillis = DateUtil.getMillis(hours, mins);
         }
      }

      String includeSpecialDays = request.getParameter("includeSpecialDaysCheck");
      if (includeSpecialDays == null) {
         sla.setIncludeSpecialDays(Constants.NO_TYPE);
      }
      else {
         sla.setIncludeSpecialDays(Constants.YES_TYPE);
      }

      String enableCutOffTime = request.getParameter("enableCutOffTimeCheck");
      sla.setEnableCutOffTime(enableCutOffTime != null);
      if (sla.getEnableCutOffTime()) {
         Integer days = sla.getFixedTimeDaysToRoll();
         if (days == null) {
            errors.rejectValue("fixedTimeDaysToRoll", null, null, "Days to roll must be entered");
         }
         else {
            if (days < 1) {
               errors.rejectValue("fixedTimeDaysToRoll", null, null, "Enter a number of days greater than zero");
            }
         }

         Date ftt = sla.getFixedTimeThreshold();
         if (ftt == null) {
            errors.rejectValue("fixedTimeThreshold", null, null, "Cut-Off Time must be entered");
         }
      }

      String stopSlaWhenPaused = request.getParameter("stopSlaCheck");
      sla.setStopSlaWhenPaused(stopSlaWhenPaused != null);
      if (stopSlaWhenPaused != null) {
         Integer pauseHours = sla.getPauseThresholdHours();
         Integer pauseMins = sla.getPauseThresholdMinutes();
         if (pauseHours == null) {
            pauseHours = 0;
            sla.setPauseThresholdHours(pauseHours);
         }

         if (DateUtil.getMillis(pauseHours, pauseMins) >= totalDurationMillis) {
            if (durationType.equals(Constants.DEADLINE_TYPE)) {
               errors.rejectValue("pauseThresholdHours", null, null,
                     "The Pause Threshold must be less than the SLA Deadline Time.");
            }
            else {
               errors.rejectValue("pauseThresholdHours", null, null,
                     "The Pause Threshold must be less than the SLA duration.");
            }
         }
      }

      String notifyDeadlineApproaching = request.getParameter("notifyDeadlineCheck");
      sla.setNotifyDeadlineApproaching(notifyDeadlineApproaching != null);
      if (notifyDeadlineApproaching != null) {
         Integer notifyDays = sla.getNotificationThresholdDays();
         Integer notifyHours = sla.getNotificationThresholdHours();
         Integer notifyMinutes = sla.getNotificationThresholdMinutes();
         if (notifyDays == null || notifyHours == null) {
            errors.rejectValue("notificationThreshold", null, null,
                  "You must enter a Notification Threshold greater than zero.");
         }
         else if (notifyDays < 1 && notifyHours < 1 && notifyMinutes < 1) {
            errors.rejectValue("notificationThreshold", null, null,
                  "You must enter a Notification Threshold greater than zero.");
         }
         else if (DateUtil.getMillis(notifyDays, notifyHours, notifyMinutes) >= totalDurationMillis) {
            if (durationType.equals(Constants.DEADLINE_TYPE)) {
               errors.rejectValue("notificationThreshold", null, null,
                     "The Notification Threshold must be less than the SLA Deadline Time.");
            }
            else {
               errors.rejectValue("notificationThreshold", null, null,
                     "The Notification Threshold must be less than the SLA duration.");
            }
         }
      }

      Reference calendar = sla.getCalendar();
      if (calendar == null || calendar.getId() == null) {
         errors.rejectValue("calendar", null, null, "You must select a Calendar!");
      }

   }

   public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
         BindException errors) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'onSubmit' method...");
      }

      ServiceLevelAgreement serviceLevelAgreement = (ServiceLevelAgreement) command;
      boolean isNew = (serviceLevelAgreement.getId() == null);
      Locale locale = request.getLocale();

      if (request.getParameter("delete") != null) {
         businessProcessManager.removeServiceLevelAgreementReference(serviceLevelAgreement);
         serviceLevelAgreementManager.removeServiceLevelAgreement(serviceLevelAgreement.getId().toString());

         saveMessage(request, getText("serviceLevelAgreement.deleted", locale));
      }
      else if (request.getParameter("addBusinessProcess") != null) {
         String bpId = request.getParameter("selectedBusinessProcess");

         if (bpId != null && !bpId.equals("")) {
            BusinessProcess bp = businessProcessManager.getBusinessProcess(bpId);
            bp.setServiceLevelAgreement(serviceLevelAgreement);
            businessProcessManager.saveBusinessProcess(bp);
         }

         return new ModelAndView("redirect:editServiceLevelAgreement.html", "id", serviceLevelAgreement.getId());
      }
      else if (request.getParameter("save") != null) {

         String durationType = request.getParameter("durationGroup");
         String deadlineType = request.getParameter("deadlineGroup");
         String workTime = request.getParameter("workTimeGroup");
         String includeSpecialDays = request.getParameter("includeSpecialDaysCheck");

         String enableCutOffTimeCheck = request.getParameter("enableCutOffTimeCheck");
         String stopSlaWhenPaused = request.getParameter("stopSlaCheck");
         String notifyDeadlineApproaching = request.getParameter("notifyDeadlineCheck");
         String notifyDeadlineApproachingForwarding = request.getParameter("notifyDeadlineForwardingCheck");

         serviceLevelAgreement.setEnableCutOffTime(enableCutOffTimeCheck == null ? Boolean.FALSE : Boolean.TRUE);
         serviceLevelAgreement.setStopSlaWhenPaused(stopSlaWhenPaused == null ? Boolean.FALSE : Boolean.TRUE);
         serviceLevelAgreement.setNotifyDeadlineApproaching(notifyDeadlineApproaching == null ? Boolean.FALSE
               : Boolean.TRUE);
         serviceLevelAgreement
               .setNotifyDeadlineApproachingForwarding(notifyDeadlineApproachingForwarding == null ? new Boolean(false)
                     : new Boolean(true));

         if (!serviceLevelAgreement.getEnableCutOffTime()) {
            serviceLevelAgreement.setFixedTimeDaysToRoll(null);
            serviceLevelAgreement.setFixedTimeThreshold(null);
         }

         if (durationType.equals(Constants.T_FORMULA_TYPE)) {
            serviceLevelAgreement.setDurationType(Constants.T_FORMULA_TYPE);
            serviceLevelAgreement.setDeadlineType(deadlineType);
            serviceLevelAgreement.setDurationHours(null);
            serviceLevelAgreement.setDurationMinutes(null);
            serviceLevelAgreement.setWorkTime(Constants.WORKING_HOURS_TYPE);
            serviceLevelAgreement.setFixedTimeDeadline(null);
         }
         else if (durationType.equals(Constants.DEADLINE_TYPE)) {
            serviceLevelAgreement.setDurationType(Constants.DEADLINE_TYPE);
            serviceLevelAgreement.setDeadlineType(Constants.FIXED_TIME_TYPE);
            serviceLevelAgreement.setDurationHours(null);
            serviceLevelAgreement.setDurationMinutes(null);
            serviceLevelAgreement.setTFormulaeDays(null);
            serviceLevelAgreement.setWorkTime(Constants.WORKING_HOURS_TYPE);
         }
         else {
            serviceLevelAgreement.setDurationType(Constants.DURATION_TYPE);
            serviceLevelAgreement.setDeadlineType(Constants.ACTUAL_TIME_TYPE);
            serviceLevelAgreement.setTFormulaeDays(null);
            serviceLevelAgreement.setWorkTime(workTime);
            serviceLevelAgreement.setFixedTimeDeadline(null);
         }

         if (!serviceLevelAgreement.getStopSlaWhenPaused()) {
            serviceLevelAgreement.setPauseThresholdHours(null);
            serviceLevelAgreement.setPauseThresholdMinutes(null);
         }

         if (includeSpecialDays == null || includeSpecialDays.equals("false")) {
            serviceLevelAgreement.setIncludeSpecialDays(Constants.NO_TYPE);
         }
         else {
            serviceLevelAgreement.setIncludeSpecialDays(Constants.YES_TYPE);
         }

         if (serviceLevelAgreement.getNotifyDeadlineApproaching()) {
            int d = serviceLevelAgreement.getNotificationThresholdDays();
            int h = serviceLevelAgreement.getNotificationThresholdHours();
            int m = serviceLevelAgreement.getNotificationThresholdMinutes();
            serviceLevelAgreement.setNotificationThreshold(DateUtil.getMillis(d, h, m));
         }
         else {
            serviceLevelAgreement.setNotificationThreshold(null);
         }

         serviceLevelAgreementManager.saveServiceLevelAgreement(serviceLevelAgreement);

         String key = (isNew) ? "serviceLevelAgreement.added" : "serviceLevelAgreement.updated";
         saveMessage(request, getText(key, locale));

         return new ModelAndView("redirect:editServiceLevelAgreement.html", "id", serviceLevelAgreement.getId());
      }
      else if (request.getParameter("removeBusinessProcessName") != null) {
         /**
          * @todo
          * 
          *       IE is associating the <img> tag as the parameter associated
          *       with 'removeBusinessProcessName' so the value is incorrectly
          *       non null when we are trying to perform other actions.
          * 
          *       Have added last for now so that everything else gets a chance
          *       to be triggered before we do the check.
          * 
          *       May have to change the html.
          */
         String bpId = request.getParameter("businessProcessToBeDeleted");

         if (bpId != null) {
            BusinessProcess bp = businessProcessManager.getBusinessProcess(bpId);
            bp.setServiceLevelAgreement(null);
            businessProcessManager.saveBusinessProcess(bp);
         }

         return new ModelAndView("redirect:editServiceLevelAgreement.html", "id", serviceLevelAgreement.getId());
      }

      return new ModelAndView(getSuccessView());
   }

   public void setReferenceManager(ReferenceManager referenceManager) {
      this.referenceManager = referenceManager;
   }

   public void setBusinessProcessManager(BusinessProcessManager businessProcessManager) {
      this.businessProcessManager = businessProcessManager;
   }

}
