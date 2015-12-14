package com.renewtek.event.jms;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;

import java.util.Calendar;
import java.util.Date;

import org.powermock.api.easymock.PowerMock;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.renewtek.service.SLAServices;

/**
 * 
 * @author kietnguyen
 * 
 */
public class CaseCreatedEventHandlerTestCase extends SlaBaseTestCase {

   private CaseCreatedEventHandler caseCreatedEventHandler;
   private long caseId = 460l;
   private long slaAccumSleepTime = 0l;
   private static String WORKFLOWNAME = "SS_TASK1";
   private static String WORKFLOWEVENT = "Workflow Event";
   private static String EXTERNALCASEID = "425AD71D6336B346A04ADA3D9EE16365";
   private static String TXN = null, STEP = null;
   private int SLAID = 99;
   private SLAServices slaServices;
   private Date startTime;
   private Date deadTime;
   private Date reminderTime;
   private static final Logger log = LoggerFactory.getLogger(CaseCreatedEventHandlerTestCase.class);

   protected void setUp() throws Exception {
      super.setUp();

      slaServices = PowerMock.createMock(SLAServices.class);
      caseCreatedEventHandler = (CaseCreatedEventHandler) ctx.getBean("caseCreatedEventHandler");
      caseCreatedEventHandler.setSlaService(slaServices);
      caseCreatedEventHandler.getScheduleFactory().setScheduler(scheduler);
      caseCreatedEventHandler.setUpdatePropsService(updatePropsService);
   }

   public void testHandleMessageTriggerJobs() {

      Calendar calStartTime = Calendar.getInstance();
      calStartTime.add(Calendar.MINUTE, 1);
      startTime = calStartTime.getTime();

      Calendar calDeadTime = Calendar.getInstance();
      calDeadTime.add(Calendar.MINUTE, 5);
      deadTime = calDeadTime.getTime();

      Calendar calReminderTime = Calendar.getInstance();
      calReminderTime.add(Calendar.MINUTE, 3);
      reminderTime = calReminderTime.getTime();

      expect(slaServices.isSlaConfiguration(WORKFLOWNAME, WORKFLOWEVENT, TXN, STEP)).andReturn(SLAID);
      expect(slaServices.getSLAStartTime(eq(WORKFLOWNAME), eq(WORKFLOWEVENT), (Date) anyObject())).andReturn(startTime);
      expect(slaServices.getSLADeadline(WORKFLOWNAME, WORKFLOWEVENT, TXN, STEP, startTime, slaAccumSleepTime)).andReturn(deadTime);
      expect(slaServices.getSLAReminderTime(WORKFLOWNAME, WORKFLOWEVENT, TXN, STEP, startTime, deadTime)).andReturn(reminderTime);
      expect(
            slaServices.getSLAStatus(eq(WORKFLOWNAME), eq(WORKFLOWEVENT), (Date) anyObject(), eq(deadTime),
                  eq(reminderTime))).andReturn("OK");
      expect(slaServices.getSLANotifyDeadlineApproaching(WORKFLOWNAME, WORKFLOWEVENT, TXN, STEP)).andReturn(true);

      PowerMock.replayAll(slaServices);
      try {
         caseCreatedEventHandler.handleMessage(caseId, WORKFLOWNAME, EXTERNALCASEID, null, null, SLAID);
         PowerMock.verifyAll();

         String[] jobGroups = scheduler.getJobGroupNames();
         assertEquals(2, jobGroups.length);

      } catch (Exception e) {
         log.error("Exception is thrown " + e);
      }
   }

   public void testHandleMessageTriggerJobsWithoutNotify() {

      Calendar calStartTime = Calendar.getInstance();
      calStartTime.add(Calendar.MINUTE, 1);
      startTime = calStartTime.getTime();

      Calendar calDeadTime = Calendar.getInstance();
      calDeadTime.add(Calendar.MINUTE, 5);
      deadTime = calDeadTime.getTime();

      Calendar calReminderTime = Calendar.getInstance();
      calReminderTime.add(Calendar.MINUTE, 3);
      reminderTime = calReminderTime.getTime();

      expect(slaServices.isSlaConfiguration(WORKFLOWNAME, WORKFLOWEVENT, TXN, STEP)).andReturn(SLAID);
      expect(slaServices.getSLAStartTime(eq(WORKFLOWNAME), eq(WORKFLOWEVENT), (Date) anyObject())).andReturn(startTime);
      expect(slaServices.getSLADeadline(WORKFLOWNAME, WORKFLOWEVENT, TXN, STEP, startTime, slaAccumSleepTime)).andReturn(deadTime);
      expect(slaServices.getSLAReminderTime(WORKFLOWNAME, WORKFLOWEVENT, TXN, STEP, startTime, deadTime)).andReturn(reminderTime);
      expect(
            slaServices.getSLAStatus(eq(WORKFLOWNAME), eq(WORKFLOWEVENT), (Date) anyObject(), eq(deadTime),
                  eq(reminderTime))).andReturn("OK");
      expect(slaServices.getSLANotifyDeadlineApproaching(WORKFLOWNAME, WORKFLOWEVENT, TXN, STEP)).andReturn(false);

      PowerMock.replayAll(slaServices);
      try {
         caseCreatedEventHandler.handleMessage(caseId, WORKFLOWNAME, EXTERNALCASEID, TXN, null, SLAID);
         PowerMock.verifyAll();

         String[] jobGroups = scheduler.getJobGroupNames();
         assertEquals(1, jobGroups.length);

      } catch (Exception e) {
         log.error("Exception is thrown " + e);
      }
   }

   public void testHandleMessageTriggerJobExpire() {

      Calendar calStartTime = Calendar.getInstance();
      calStartTime.add(Calendar.SECOND, 1);
      startTime = calStartTime.getTime();

      Calendar calDeadTime = Calendar.getInstance();
      calDeadTime.add(Calendar.SECOND, 15);
      deadTime = calDeadTime.getTime();

      Calendar calReminderTime = Calendar.getInstance();
      calReminderTime.add(Calendar.SECOND, 5);
      reminderTime = calReminderTime.getTime();

      expect(slaServices.isSlaConfiguration(WORKFLOWNAME, WORKFLOWEVENT, TXN, STEP)).andReturn(SLAID);
      expect(slaServices.getSLAStartTime(eq(WORKFLOWNAME), eq(WORKFLOWEVENT), (Date) anyObject())).andReturn(startTime);
      expect(slaServices.getSLADeadline(WORKFLOWNAME, WORKFLOWEVENT, TXN, STEP, startTime, slaAccumSleepTime)).andReturn(deadTime);
      expect(slaServices.getSLAReminderTime(WORKFLOWNAME, WORKFLOWEVENT, TXN, STEP, startTime, deadTime)).andReturn(reminderTime);
      expect(
            slaServices.getSLAStatus(eq(WORKFLOWNAME), eq(WORKFLOWEVENT), (Date) anyObject(), eq(deadTime),
                  eq(reminderTime))).andReturn("OK");
      expect(slaServices.getSLANotifyDeadlineApproaching(WORKFLOWNAME, WORKFLOWEVENT, TXN, STEP)).andReturn(true);

      PowerMock.replayAll(slaServices);
      try {
         caseCreatedEventHandler.handleMessage(caseId, WORKFLOWNAME, EXTERNALCASEID, TXN, null, SLAID);
         PowerMock.verifyAll();

         Thread.sleep(8000);

         int serviceCount = updatePropsService.getStatusUpdateCount();
         assertEquals(1, serviceCount);

      } catch (Exception e) {
         log.error("Exception is thrown " + e);
      }
   }

   public void testHandleMessageTriggerJobRemiderTime() {

      Calendar calStartTime = Calendar.getInstance();
      calStartTime.add(Calendar.SECOND, 1);
      startTime = calStartTime.getTime();

      Calendar calDeadTime = Calendar.getInstance();
      calDeadTime.add(Calendar.SECOND, 10);
      deadTime = calDeadTime.getTime();

      Calendar calReminderTime = Calendar.getInstance();
      calReminderTime.add(Calendar.SECOND, 5);
      reminderTime = calReminderTime.getTime();

      expect(slaServices.isSlaConfiguration(WORKFLOWNAME, WORKFLOWEVENT, TXN, STEP)).andReturn(SLAID);
      expect(slaServices.getSLAStartTime(eq(WORKFLOWNAME), eq(WORKFLOWEVENT), (Date) anyObject())).andReturn(startTime);
      expect(slaServices.getSLADeadline(WORKFLOWNAME, WORKFLOWEVENT, TXN, STEP, startTime, slaAccumSleepTime)).andReturn(deadTime);
      expect(slaServices.getSLAReminderTime(WORKFLOWNAME, WORKFLOWEVENT, TXN, STEP, startTime, deadTime)).andReturn(reminderTime);
      expect(
            slaServices.getSLAStatus(eq(WORKFLOWNAME), eq(WORKFLOWEVENT), (Date) anyObject(), eq(deadTime),
                  eq(reminderTime))).andReturn("OK");
      expect(slaServices.getSLANotifyDeadlineApproaching(WORKFLOWNAME, WORKFLOWEVENT, TXN, STEP)).andReturn(true);

      PowerMock.replayAll(slaServices);
      try {
         caseCreatedEventHandler.handleMessage(caseId, WORKFLOWNAME, EXTERNALCASEID, TXN, null, SLAID);
         PowerMock.verifyAll();

         Thread.sleep(12000);

         int serviceCount = updatePropsService.getStatusUpdateCount();
         printJobAvaiable();

         assertEquals(2, serviceCount);

      } catch (Exception e) {
         log.error("Exception is thrown " + e);
      }
   }

   private void printJobAvaiable() throws SchedulerException {
      String[] jobGroups = scheduler.getJobGroupNames();
      for (int i = 0; i < jobGroups.length; i++) {
         log.debug("Group: " + jobGroups[i] + " contains the following jobs");
         String[] jobsInGroup = scheduler.getJobNames(jobGroups[i]);

         for (int j = 0; j < jobsInGroup.length; j++) {
            log.debug(jobsInGroup[j]);
         }
      }
   }

}
