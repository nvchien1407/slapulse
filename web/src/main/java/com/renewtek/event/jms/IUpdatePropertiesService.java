package com.renewtek.event.jms;

import java.util.Date;

/**
 * Interface of UpdateProperties Service Implementation class:
 * UpdatePropertiesWebService, UpdatePropertiesJMSServices
 * 
 * @author kietnguyen
 * 
 */
public interface IUpdatePropertiesService {

   /**
    * Initialize the SLAStartTime, SLADeadline, SLAReminderTime, SLAStatus,
    * SLANotifyDeadlineApproaching SLA workflow definition
    * 
    * @param caseId
    *           Case ID
    * @param slaStartTime
    *           SLA Start time
    * @param slaDeadline
    *           Deadline
    * @param slaReminderTime
    *           SLA Reminder Time
    * @param slaStatus
    *           SLA Status
    * @param slaNotifyDeadlineApproaching
    *           SLA Notify Deadline Approaching
    * @param slaId 
    */
   public void initialiseSlaParameters(long caseId, Date slaStartTime, Date slaDeadline, Date slaReminderTime,
         String slaStatus, boolean slaNotifyDeadlineApproaching, int slaId, boolean isStep);

   /**
    * Update SLAStatus in SLA workflow definition
    */
   public void updateSlaStatusInSpecifyTime(long caseId, String processName, Date slaDeadline, Date slaReminderTime, boolean isStep, int bpmId);

}
