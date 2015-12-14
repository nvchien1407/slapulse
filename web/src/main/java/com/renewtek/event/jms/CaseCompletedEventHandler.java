package com.renewtek.event.jms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;

/**
 * The handler due to CaseCompletedEvent in which the Quartz Timer associate
 * with the Source WorkItem of event will be terminated
 * 
 * @author hanhluu
 * 
 */
public class CaseCompletedEventHandler extends SlaEventHandler {
   private static final Log LOG = LogFactory.getLog(CaseCompletedEventHandler.class);

   public void handleMessage(long caseId, String workflowName, String externalCaseId, String transaction, String startDate, Integer existingSlaId) throws SchedulerException {

      Integer slaId = isSlaConfigured(workflowName, transaction);
      if (slaId != null) {
         LOG.info("Start Completing the Case: "+externalCaseId+" "+caseId);
         terminateSla(workflowName, externalCaseId, transaction);
      }
   }

   /*package*/ void terminateSla(String workflowName, String externalCaseId, String transaction) throws SchedulerException {
      boolean slaNotifyDeadlineAppoaching = this.slaService.getSLANotifyDeadlineApproaching(workflowName, SlaEventHandler.PROCESS_TYPE, transaction, CASE_SLA_STEP_NULL);
      if (slaNotifyDeadlineAppoaching) {
         this.terminateTrigger(externalCaseId, workflowName + SEPARATOR + GROUP_NAME_REMIDER);
      }

      this.terminateTrigger(externalCaseId, workflowName + SEPARATOR + GROUP_NAME_EXPIRE);
   }

   private void terminateTrigger(String triggerName, String groupName) throws SchedulerException {

      LOG.debug("Terminating trigger: "+triggerName+" "+groupName);
      this.getScheduleFactory().terminateJob(triggerName, groupName);
   }

}
