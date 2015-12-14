package com.renewtek.event.jms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.easymock.EasyMock;
import org.powermock.api.easymock.PowerMock;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.renewtek.event.jms.scheduling.ScheduleFactory;
import com.renewtek.event.jms.scheduling.SimpleTriggerFactory;
import com.renewtek.service.SLAServices;

public class CaseCompletedEventHandlerTest extends SlaBaseTestCase {
   private CaseCompletedEventHandler caseCompletedEventHandler;
   private long caseId = 460l;
   private ScheduleFactory scheduleFactory;

   private static final Logger log = LoggerFactory.getLogger(CaseCreatedEventHandlerTestCase.class);
   private static final String SAMPLE_GROUP = "SAMPLE_GROUP";
   private static final String SAMPLE_JOB = "SAMPLE_JOB";
   private static final String WORKFLOW_SLA = "Workflow Event";
   private static final String TXN = null, STEP = null;
   private int SLAID = 99;
   private SimpleTrigger triggerUpdate;
   private JobDetail sampleJob;
   private SLAServices slaServices;

   protected void setUp() throws Exception {
      super.setUp();
      caseCompletedEventHandler = (CaseCompletedEventHandler) ctx.getBean("caseCompletedEventHandler");

      scheduleFactory = (ScheduleFactory) ctx.getBean("schedulerFactory");
      scheduleFactory.setScheduler(scheduler);

      slaServices = PowerMock.createMock(SLAServices.class);

      // job for test 1
      sampleJob = new JobDetailBean();
      sampleJob.setJobClass(SampleJobBean.class);
      sampleJob.setName(SAMPLE_JOB + "1");
      sampleJob.setGroup(SAMPLE_GROUP);
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.MINUTE, 5);

      triggerUpdate = SimpleTriggerFactory
            .createTrigger(cal.getTime(), null, SAMPLE_GROUP, SAMPLE_JOB + "1");
      scheduleFactory.scheduleJob(sampleJob, triggerUpdate);

      // job for test 2
      sampleJob = new JobDetailBean();
      sampleJob.setJobClass(SampleJobBean.class);
      sampleJob.setName(SAMPLE_JOB + "2");
      sampleJob.setGroup(SAMPLE_GROUP);
      cal.add(Calendar.SECOND, 5);
      triggerUpdate = SimpleTriggerFactory
            .createTrigger(cal.getTime(), null, SAMPLE_GROUP, SAMPLE_JOB + "2");
      scheduleFactory.scheduleJob(sampleJob, triggerUpdate);

      // job for test 3
      sampleJob = new JobDetailBean();
      sampleJob.setJobClass(SampleJobBean.class);
      sampleJob.setName(SAMPLE_JOB + "3");
      sampleJob.setGroup(SAMPLE_GROUP);
      cal.add(Calendar.SECOND, 5);
      triggerUpdate = SimpleTriggerFactory
            .createTrigger(cal.getTime(), null, SAMPLE_GROUP, SAMPLE_JOB + "3");
      scheduleFactory.scheduleJob(sampleJob, triggerUpdate);
   }

   public void testHandleMessageCase1() {
      EasyMock.expect(slaServices.isSlaConfiguration(SAMPLE_GROUP, WORKFLOW_SLA, TXN, STEP)).andReturn(SLAID);
      EasyMock.expect(slaServices.getSLANotifyDeadlineApproaching(SAMPLE_GROUP, WORKFLOW_SLA, TXN, STEP)).andReturn(false);
      caseCompletedEventHandler.setSlaService(slaServices);
      PowerMock.replay(slaServices);
      boolean isPass = true;
      try {
         caseCompletedEventHandler.handleMessage(caseId, SAMPLE_GROUP, SAMPLE_JOB + "1", null, null, SLAID);

      } catch (Exception e) {
         isPass = false;
         log.info("Exception is thrown");
      }
      PowerMock.verifyAll();
      assertTrue(isPass);
   }

   public void testHandleMessageCase2() {
      EasyMock.expect(slaServices.isSlaConfiguration(SAMPLE_GROUP, WORKFLOW_SLA, TXN, STEP)).andReturn(SLAID);
      EasyMock.expect(slaServices.getSLANotifyDeadlineApproaching(SAMPLE_GROUP, WORKFLOW_SLA, TXN, STEP)).andReturn(false);
      caseCompletedEventHandler.setSlaService(slaServices);
      PowerMock.replay(slaServices);
      boolean isPass = true;
      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

      try {
         log.debug("===============start sleep" + dateFormat.format(Calendar.getInstance().getTime()));
         Thread.sleep(5000);
         log.debug("===============end sleep" + dateFormat.format(Calendar.getInstance().getTime()));
         caseCompletedEventHandler.handleMessage(caseId, SAMPLE_GROUP, SAMPLE_JOB + "2", null, null, SLAID);
      } catch (Exception e) {
         isPass = false;
         log.info("Exception is thrown");
      }
      PowerMock.verifyAll();
      assertTrue(isPass);
   }

   public void testHandleMessageCase3() {
      EasyMock.expect(slaServices.isSlaConfiguration(SAMPLE_GROUP, WORKFLOW_SLA, TXN, STEP)).andReturn(SLAID);
      EasyMock.expect(slaServices.getSLANotifyDeadlineApproaching(SAMPLE_GROUP, WORKFLOW_SLA, TXN, STEP)).andReturn(false);
      caseCompletedEventHandler.setSlaService(slaServices);
      PowerMock.replay(slaServices);
      boolean isPass = true;
      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

      try {
         log.debug("===============start sleep" + dateFormat.format(Calendar.getInstance().getTime()));
         Thread.sleep(7000);
         log.debug("===============end sleep" + dateFormat.format(Calendar.getInstance().getTime()));
         caseCompletedEventHandler.handleMessage(caseId, SAMPLE_GROUP, SAMPLE_JOB + "3", null, null, SLAID);
      } catch (Exception e) {
         isPass = false;
         log.info("Exception is thrown");
      }
      PowerMock.verifyAll();
      assertTrue(isPass);
   }

   private class SampleJobBean extends QuartzJobBean {
      @Override
      protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
         // Executed
      }
   };
}
