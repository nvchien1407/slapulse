//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service;

import java.util.Date;
import java.util.List;

import com.renewtek.dao.BusinessProcessDAO;
import com.renewtek.model.BusinessProcess;
import com.renewtek.model.ServiceLevelAgreement;

public interface BusinessProcessManager extends Manager {

    /**
     * Setter for DAO, convenient for unit testing
     */
    public void setBusinessProcessDAO(BusinessProcessDAO businessProcessDAO);

    /**
     * Retrieves all of the businessProcesss
     */
    public List<BusinessProcess> getBusinessProcesses(BusinessProcess businessProcess);

    /**
     * Retrieves all of the businessProcess that have not yet
     * been assigned to an SLA.
     */
    public List<BusinessProcess> getUnassignedBusinessProcesses();

    /**
     * Gets unique businessProcess based on name and type.
     */
    public BusinessProcess getBusinessProcess(final String name, final String type);

    public BusinessProcess getBusinessProcess(final String processName, final String processType, final String transaction, final String step);

    /**
     * Gets unique businessProcess based on nameId and typeId
     *
     * @param nameId foreign key reference to the BusinessProcessName Reference data.
     * @param typeId foreign key reference to the BusinessProcessType Reference data.
     * @return
     */
    public BusinessProcess getBusinessProcessByIds(final Integer nameId, final Integer typeId);

    /**
     * Gets businessProcess's information based on id.
     *
     * @param id the businessProcess's id
     * @return businessProcess populated businessProcess object
     */
    public BusinessProcess getBusinessProcess(final String id);

    /**
     * Saves a businessProcess's information
     *
     * @param businessProcess the object to be saved
     */
    public void saveBusinessProcess(BusinessProcess businessProcess);

    /**
     * Removes a businessProcess from the database by id
     *
     * @param id the businessProcess's id
     */
    public void removeBusinessProcess(final String id);

    /**
     * Calculates a deadline
     *
     * @param processName, processType, startTime, sleepTime
     * @return deadline
     */
    public Date getSLADeadline(final String processName, final String processType, final Date startTime, long sleepTime);

    public Date getSLADeadline(final String processName, final String processType, final String transaction, final String step, final Date startTime, long sleepTime);

    /**
     * Calculates a reminder time for deadline
     *
     * @param processName, processType, startTime, sleepTime
     * @return deadline
     */
    public Date getSLAReminderTime(String processName, String processType, Date startTime, Date deadline);

    public Date getSLAReminderTime(String processName, String processType, String transaction, String step, Date startTime, Date deadline);

    /**
     * Calculates a start time
     *
     * @param processName, processType, startTime
     * @return startTime
     */
    public Date getSLAStartTime(String processName, String processType, Date startTime);

    /**
     * SLA Stops when Paused
     *
     * @param processName, processType
     * @return stopWhenPaused
     */
    public boolean getSLAStopWhenPaused(String processName, String processType);

    /**
     * Calculates the accumulated sleep time
     *
     * @param processName, processType, toDate, fromDate, accumSleepTime
     * @return deadline
     */
    public float getSLAAccumSleepTime(String processName, String processType, Date fromDate, Date toDate, float accumSleepTime);

    /**
     * Calculates a status
     *
     * @param processName, processType, pointInTime, deadline, reminderTime
     * @return status
     */
    public String getSLAStatus(String processName, String processType, Date pointInTime, Date deadline, Date reminderTime);

    /**
     * Notify Deadline Approaching
     *
     * @param processName, processType, pointInTime, deadline, reminderTime
     * @return status
     */
    public boolean getSLANotifyDeadlineApproaching(String processName, String processType);

    public boolean getSLANotifyDeadlineApproaching(String processName, String processType, String transaction, String step);

    /**
     * remove this SLA reference in the list of business process of this SLA
     *
     * @param serviceLevelAgreement
     */
    public void removeServiceLevelAgreementReference(ServiceLevelAgreement serviceLevelAgreement);

   /**
   * check if this is a business day or not if true return the startTime else return the next available business day
   *
   * @param date
   * @param startTime
   * @param calendarName
   * @param isForward
   * @return
   */
   public Date findNextWorkDate(Date startTime, String calendarName);

}

