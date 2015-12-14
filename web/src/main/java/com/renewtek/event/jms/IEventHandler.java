package com.renewtek.event.jms;

/**
 * 
 * Interface of Event Handler
 * 
 * @author kietnguyen
 * 
 */

public interface IEventHandler {

   /**
    * Method to handle the message This method will: 1. Check the case against
    * with configuration in Database 2. Do update on the Workflow properties 3.
    * Schedule the jobs on Quartz.
    */
   public void handleMessage(long caseId, String workflowName, String externalCaseId, 
         String transaction, String startDate, Integer existingSlaId) throws Exception;

}
