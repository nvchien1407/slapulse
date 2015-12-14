package com.renewtek.event.jms;

import java.util.Date;

public class MockUpdatePropsWebService implements IUpdatePropertiesService {
    
    private String slaStatus = "";
    private int statusUpdateCount = 0;

    public void initialiseSlaParameters(long caseId, Date slaStartTime, Date slaDeadline, Date slaReminderTime, String slaStatus, boolean slaNotifyDeadlineApproaching, int slaId, boolean isStep) {
        this.slaStatus = slaStatus;
    }

    public void updateSlaStatusInSpecifyTime(long caseId, String processName, Date slaDeadline, Date slaReminderTime, boolean isStep, int bpmId) {
        statusUpdateCount = statusUpdateCount + 1 ;  
    }

    public String getSlaStatus() {
        return slaStatus;
    }

    public void setSlaStatus(String slaStatus) {
        this.slaStatus = slaStatus;
    }

    public int getStatusUpdateCount() {
        return statusUpdateCount;
    }

    public void setStatusUpdateCount(int statusUpdateCount) {
        this.statusUpdateCount = statusUpdateCount;
    }
}
