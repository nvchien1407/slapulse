package com.renewtek.event.jms;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.google.common.base.Strings;
import com.renewtek.Constants;
//import com.renewtek.kata.api.dto.rest.CaseDto;
//import com.renewtek.kata.api.dto.rest.RestResponse;
//import com.renewtek.kata.api.service.rest.CaseRestService;
import com.renewtek.model.BusinessProcess;
import com.renewtek.service.BusinessProcessManager;
import com.renewtek.service.SLAUpdatePropertiesAdapter;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import com.renewtek.service.SLAServices;
//import com.renewtek.service.client.PropertyResponseDto;
import com.renewtek.util.StringUtil;

/**
 * Implementation of IUpdatePropertiesService
 * 
 * @author kietnguyen
 * 
 */
public class UpdatePropertiesWebService implements IUpdatePropertiesService, InitializingBean {

   private static final Logger log = LoggerFactory.getLogger(UpdatePropertiesWebService.class);
   private HashMap<String, String> propertyNameValue;
   private String datetimeFormat;
   private SLAUpdatePropertiesAdapter slaUpdatePropertiesDAO;
   private SLAUpdatePropertiesAdapter slaUpdatePropertiesWS;
   private BusinessProcessManager businessProcessManager;
   private SLAServices slaService;
   private boolean allowSlaChange;
   private String[] propertyNames;
   private String[] propertyNamesStep;
   
   private static String START_TIME_PROPERTY_NAME = "START_TIME_PROPERTY_NAME";
   private static String DEADLINE_TIME_PROPERTY_NAME = "DEADLINE_TIME_PROPERTY_NAME";
   private static String REMINDER_TIME_PROPERTY_NAME = "REMINDER_TIME_PROPERTY_NAME";
   private static String STATUS_PROPERTY_NAME = "STATUS_PROPERTY_NAME";
   private static String STEP_START_TIME_PROPERTY_NAME = "STEP_START_TIME_PROPERTY_NAME";
   private static String STEP_DEADLINE_TIME_PROPERTY_NAME = "STEP_DEADLINE_TIME_PROPERTY_NAME";
   private static String STEP_REMINDER_TIME_PROPERTY_NAME = "STEP_REMINDER_TIME_PROPERTY_NAME";
   private static String STEP_STATUS_PROPERTY_NAME = "STEP_STATUS_PROPERTY_NAME";
   private static String NOTIFY_DEADLINE_APPROACHING_PROPERTY_NAME = "NOTIFY_DEADLINE_APPROACHING_PROPERTY_NAME";
   private static String SLA_ID_PROPERTY_NAME = "SLA_ID_PROPERTY_NAME";

   public void afterPropertiesSet() throws Exception {
      propertyNames = new String[]{ propertyNameValue.get(START_TIME_PROPERTY_NAME),
            propertyNameValue.get(DEADLINE_TIME_PROPERTY_NAME), propertyNameValue.get(REMINDER_TIME_PROPERTY_NAME),
            propertyNameValue.get(STATUS_PROPERTY_NAME), propertyNameValue.get(NOTIFY_DEADLINE_APPROACHING_PROPERTY_NAME) };
      if (allowSlaChange) {
         propertyNames = (String[]) ArrayUtils.add(propertyNames, propertyNameValue.get(SLA_ID_PROPERTY_NAME));
      }
      propertyNamesStep = new String[]{ propertyNameValue.get(STEP_START_TIME_PROPERTY_NAME),
            propertyNameValue.get(STEP_DEADLINE_TIME_PROPERTY_NAME), propertyNameValue.get(STEP_REMINDER_TIME_PROPERTY_NAME),
            propertyNameValue.get(STEP_STATUS_PROPERTY_NAME) };
   }

   public void initialiseSlaParameters(long caseId, Date slaStartTime, Date slaDeadline, Date slaReminderTime,
         String slaStatus, boolean slaNotifyDeadlineApproaching, int slaId, boolean isStep) {

      log.info("Initialise SlaParameters for caseId: {}", caseId);
      SimpleDateFormat sdf = new SimpleDateFormat(datetimeFormat);

      String[] propertyValues;
      if (allowSlaChange) {
         propertyValues = new String[]{ sdf.format(slaStartTime), sdf.format(slaDeadline), sdf.format(slaReminderTime),
            slaStatus, Boolean.toString(slaNotifyDeadlineApproaching), Integer.toString(slaId) };
      }
      else {
         propertyValues = new String[]{ sdf.format(slaStartTime), sdf.format(slaDeadline), sdf.format(slaReminderTime),
            slaStatus, Boolean.toString(slaNotifyDeadlineApproaching) };
      }
      slaUpdatePropertiesDAO.lockUpdateCasePropertyAndUnlockMultiple(caseId, propertyNames, propertyValues);
      
   }

   public void updateSlaStatusInSpecifyTime(long caseId, String processName, Date slaDeadline, Date slaReminderTime, boolean isStep, int bpmId) {

      log.info("Update the SLA Status for caseId: {}", caseId);
      Date now = Calendar.getInstance().getTime();
      String slaStatusDeadLine = this.slaService.getSLAStatus(processName, SlaEventHandler.PROCESS_TYPE, now,
            slaDeadline, slaReminderTime);
      
      BusinessProcess bp = businessProcessManager.getBusinessProcess(String.valueOf(bpmId));

      if (!bp.getEmailNotification())
         slaUpdatePropertiesDAO.lockUpdateCasePropertyAndUnlock(caseId, propertyNameValue.get(STATUS_PROPERTY_NAME),
            slaStatusDeadLine);
      else
      {
         log.info("Start sending notification to Saigon");
         if( !StringUtils.isEmpty(bp.getToEmail()) )
         slaUpdatePropertiesDAO.lockUpdateCasePropertyAndUnlockAndNotify(caseId, propertyNameValue.get(STATUS_PROPERTY_NAME),
               slaStatusDeadLine, bp.getFromEmail(), bp.getToEmail(), bp.getEmailTemplate());
      }
   }

   public HashMap<String, String> getPropertyNameValue() {
      return propertyNameValue;
   }

   public void setPropertyNameValue(HashMap<String, String> propertyNameValue) {
      this.propertyNameValue = propertyNameValue;
   }

   public String getDatetimeFormat() {
      return datetimeFormat;
   }

   public void setDatetimeFormat(String datetimeFormat) {
      this.datetimeFormat = datetimeFormat;
   }

   public SLAUpdatePropertiesAdapter getSlaUpdatePropertiesDAO() {
      return slaUpdatePropertiesDAO;
   }

   public void setSlaUpdatePropertiesDAO(SLAUpdatePropertiesAdapter slaUpdatePropertiesDAO) {
      this.slaUpdatePropertiesDAO = slaUpdatePropertiesDAO;
   }

   public SLAServices getSlaService() {
      return slaService;
   }

   public void setSlaService(SLAServices slaService) {
      this.slaService = slaService;
   }

   public void setAllowSlaChange(boolean allowSlaChange) {
      this.allowSlaChange = allowSlaChange;
   }
   public void setBusinessProcessManager(BusinessProcessManager businessProcessManager) {
      this.businessProcessManager = businessProcessManager;
   }
   public void setSlaUpdatePropertiesWS(SLAUpdatePropertiesAdapter slaUpdatePropertiesWS) {
      this.slaUpdatePropertiesWS = slaUpdatePropertiesWS;
   }

}
