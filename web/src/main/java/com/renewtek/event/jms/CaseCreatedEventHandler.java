package com.renewtek.event.jms;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.renewtek.kata.core.Utils.Time.ThreadSafeSimpleDateFormat;
import com.renewtek.model.BusinessProcess;
import com.renewtek.service.BusinessProcessManager;

/**
 * Case Created Event Handler class.
 * 
 * @author kietnguyen
 * 
 */
public class CaseCreatedEventHandler extends SlaEventHandler {

   private static String UPDATE_PROPS_WEB_SERVICES = "UPDATE_PROPS_WEB_SERVICES";

   private static final Logger log = LoggerFactory.getLogger(CaseCreatedEventHandler.class);

//   private ThreadSafeSimpleDateFormat sdf;
   private BusinessProcessManager businessProcessManager;
   
   public void setBusinessProcessManager(BusinessProcessManager businessProcessManager) {
      this.businessProcessManager = businessProcessManager;
   }
   
   public void handleMessage(long caseId, String workflowName, String externalCaseId, String transaction, String startFrom, Integer existingSlaId) throws Exception {
      log.info("Start Handling the CaseUpdate");

      Integer slaId = isSlaConfigured(workflowName, transaction);
      if (slaId != null) {
         this.initalizeSlaParameters(externalCaseId, workflowName, caseId, transaction, startFrom, slaId);
      }
   }

   /*package*/ void initalizeSlaParameters(String externalCaseId, String processName, long caseId, String transaction, String startFrom, int slaId) throws Exception {
      log.info("Call SaiGon to initialise parameters for "+externalCaseId+" "+caseId);

      //Date slaStartTime = convertToDate(startFrom);
      Date slaStartTime = new Date();
      long slaAccumSleepTime = 0l;

      slaStartTime = this.slaService.getSLAStartTime(processName, SlaEventHandler.PROCESS_TYPE, slaStartTime);
      Date slaDeadline = this.slaService.getSLADeadline(processName, SlaEventHandler.PROCESS_TYPE, transaction, CASE_SLA_STEP_NULL, slaStartTime,
            slaAccumSleepTime);
      Date slaReminderTime = this.slaService.getSLAReminderTime(processName, SlaEventHandler.PROCESS_TYPE, transaction, CASE_SLA_STEP_NULL, slaStartTime, slaDeadline);
      String slaStatus = this.slaService.getSLAStatus(processName, SlaEventHandler.PROCESS_TYPE, slaStartTime, slaDeadline,
            slaReminderTime);
      boolean slaNotifyDeadlineAppoaching = this.slaService.getSLANotifyDeadlineApproaching(processName,
            SlaEventHandler.PROCESS_TYPE, transaction, CASE_SLA_STEP_NULL);
      
      // Calling WS to update the SLA Parameters.
      this.updatePropsService.initialiseSlaParameters(caseId, slaStartTime, slaDeadline, slaReminderTime, slaStatus,
            slaNotifyDeadlineAppoaching, slaId, false);
      BusinessProcess bp = this.businessProcessManager.getBusinessProcess(processName, SlaEventHandler.PROCESS_TYPE, transaction, CASE_SLA_STEP_NULL);
      Object[] arguments = new Object[] { caseId, processName, slaDeadline, slaReminderTime, bp.getId() };

      // 1: Trigger Job Details for Reminder time
      if (slaNotifyDeadlineAppoaching) {
         this.triggerJobDetails(processName + SEPARATOR + GROUP_NAME_REMIDER, externalCaseId,
               UPDATE_PROPS_WEB_SERVICES, METHOD_UPDATE_SLA_STATUS_IN_TIME, arguments, slaReminderTime, null);
      }

      // 2: Trigger Job Details for Expire time
      this.triggerJobDetails(processName + SEPARATOR + GROUP_NAME_EXPIRE, externalCaseId,
            UPDATE_PROPS_WEB_SERVICES, METHOD_UPDATE_SLA_STATUS_IN_TIME, arguments, slaDeadline, null);
   }

   /*private Date convertToDate(String s) {
      if (! StringUtils.isEmpty(s)) {
         try {
            return sdf.parse(s);
         } catch (ParseException e) {
            log.error("Can't parse "+s+" because "+e.getMessage());
         }
      }
      return null;
   }*/

   //public void setDatetimeFormat(String datetimeFormat) {
   //   this.sdf = new ThreadSafeSimpleDateFormat(datetimeFormat);
   //}
}
