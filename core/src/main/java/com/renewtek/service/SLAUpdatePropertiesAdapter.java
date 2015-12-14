package com.renewtek.service;

public interface SLAUpdatePropertiesAdapter {

   public void lockUpdateCasePropertyAndUnlock(long caseId, String propertyName, String propValue);

   public void lockUpdateCasePropertyAndUnlockMultiple(long caseId, String [] propertyNames, String [] propValues);
   
   public void lockUpdateCasePropertyAndUnlockAndNotify(long caseId, String propertyName, String propValue, String fromEmail, String toEmail, String emailTemplate);
   
   //public com.renewtek.service.client.CaseDto findCaseById(Long caseId);
}
