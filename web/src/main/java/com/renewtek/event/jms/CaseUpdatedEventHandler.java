package com.renewtek.event.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaseUpdatedEventHandler extends SlaEventHandler {

   private static final Logger log = LoggerFactory.getLogger(CaseUpdatedEventHandler.class);
   
   private CaseCreatedEventHandler caseCreatedEventHandler;
   private CaseCompletedEventHandler caseCompletedEventHandler;
   private boolean allowSlaChange;

   public void handleMessage(long caseId, String workflowName, String externalCaseId, String transaction, String startDate, Integer currentSlaId) throws Exception {
      if (allowSlaChange) {
         int slaId = getDefault(isSlaConfigured(workflowName, transaction), 0);
         int existingSlaId = getDefault(currentSlaId, 0);
         
         log.info("CaseUpdate has occured for: {} old sla: {} new sla {}", new Object[] { externalCaseId, existingSlaId, slaId });
         if (slaId != existingSlaId) {
            this.checkSlaChanged(externalCaseId, workflowName, caseId, transaction, startDate, slaId);
         }
      }
   }

   private int getDefault(Integer i, int def) {
      return (i == null) ? def : i;
   }

   private void checkSlaChanged(String externalCaseId, String processName, long caseId, String transaction, String startDate, int slaId) throws Exception {
      caseCompletedEventHandler.terminateSla(processName, externalCaseId, transaction);
      if (slaId != 0) {
         caseCreatedEventHandler.initalizeSlaParameters(externalCaseId, processName, caseId, transaction, startDate, slaId);
      }
   }

   public void setCaseCreatedEventHandler(CaseCreatedEventHandler caseCreatedEventHandler) {
      this.caseCreatedEventHandler = caseCreatedEventHandler;
   }

   public void setCaseCompletedEventHandler(CaseCompletedEventHandler caseCompletedEventHandler) {
      this.caseCompletedEventHandler = caseCompletedEventHandler;
   }

   public void setAllowSlaChange(boolean allowSlaChange) {
      this.allowSlaChange = allowSlaChange;
   }
}
