package com.renewtek.event.jms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.easymock.EasyMock;
//import org.joda.time.DateTime;
import org.powermock.api.easymock.PowerMock;

//import com.renewtek.kata.core.process.CaseUpdate;
//import com.renewtek.kata.core.process.CaseUpdate.CaseUpdateWithCase;
//import com.renewtek.kata.core.process.EventIdentifier;
//import com.renewtek.kata.core.service.dto.CaseDto;
//import com.renewtek.kata.core.service.dto.CaseTypeVersionDto;


public class SlaMessageListenerTest extends SlaBaseTestCase {

   private static final String EXTERNAL_ID = "EXTERNAL_ID";

   private static final String USERNAME = "USERNAME";

   private static final String WORKFLOW_NAME = "WORKFLOW_NAME";
   
   private static final Long CASE_ID = 0l;

   private static final Integer SLAID = null;

   private SlaMessageListener service;

   private ObjectMessage message;

   //private CaseUpdate.CaseUpdateWithCase cuWUpdate;

   //private EventIdentifier eventIdentifier;

   //private CaseUpdate cu;

   public void setUp() throws Exception {
      super.setUp();
      service = (SlaMessageListener) ctx.getBean("slaMessageListener");

      message = PowerMock.createMock(ObjectMessage.class);

      // sample EventIdentifier
      //eventIdentifier = new EventIdentifier(new DateTime(), 1);
   }

   public void testCaseCreatedHandling() {
      /*IEventHandler caseCreatedHandler = PowerMock.createMock(IEventHandler.class);
      Map<String, IEventHandler> handlerMapping = new HashMap<String, IEventHandler>();
      handlerMapping.put(CaseUpdate.Type.CREATED.toString(), caseCreatedHandler);

      service.setMessageHandlerMap(handlerMapping);

      // Produce message
      ArrayList<CaseUpdate> cuList = new ArrayList<CaseUpdate>();
      cu = new CaseUpdate(eventIdentifier, EXTERNAL_ID, USERNAME);
      cu.setType(CaseUpdate.Type.CREATED);
      cuList.add(cu);

      // init caseTypeVersion
      CaseTypeVersionDto caseTypeVersion = new CaseTypeVersionDto();
      caseTypeVersion.setName(WORKFLOW_NAME);
      // init caseMap
      Map<String, CaseDto> caseMap = new HashMap<String, CaseDto>();
      CaseDto caseDto = new CaseDto();
      caseDto.setId(CASE_ID);
      caseDto.setCaseTypeVersion(caseTypeVersion);
      caseMap.put(EXTERNAL_ID, caseDto);
      
      cuWUpdate = new CaseUpdateWithCase(cuList, caseMap);

      // Expect handleMesaage to be called
      try {
         caseCreatedHandler.handleMessage(CASE_ID, WORKFLOW_NAME, EXTERNAL_ID, null, null, SLAID);
      }
      catch (Exception e) {
         fail("Failed to mock method call");
      }
      // Expect getObject to be called;
      try {
         EasyMock.expect(message.getObject()).andReturn(cuWUpdate);
         
      }
      catch (JMSException e) {
         fail("Failed to set up message object");
      }

      PowerMock.replayAll(message, caseCreatedHandler);

      // Actual test
      service.onMessage(message);

      PowerMock.verifyAll();*/
   }


   public void testCaseCompletedHandling() {
      /*IEventHandler caseCompletedHandler = PowerMock.createMock(IEventHandler.class);
      Map<String, IEventHandler> handlerMapping = new HashMap<String, IEventHandler>();
      handlerMapping.put(CaseUpdate.Type.TERMINATED.toString(), caseCompletedHandler);

      service.setMessageHandlerMap(handlerMapping);

      // Produce message
      ArrayList<CaseUpdate> cuList = new ArrayList<CaseUpdate>();
      cu = new CaseUpdate(eventIdentifier, EXTERNAL_ID, USERNAME);
      cu.setType(CaseUpdate.Type.TERMINATED);
      cuList.add(cu);

      // init caseTypeVersion
      CaseTypeVersionDto caseTypeVersion = new CaseTypeVersionDto();
      caseTypeVersion.setName(WORKFLOW_NAME);
      // init caseMap
      Map<String, CaseDto> caseMap = new HashMap<String, CaseDto>();
      CaseDto caseDto = new CaseDto();
      caseDto.setId(CASE_ID);
      caseDto.setCaseTypeVersion(caseTypeVersion);
      caseMap.put(EXTERNAL_ID, caseDto);
      
      cuWUpdate = new CaseUpdateWithCase(cuList, caseMap);

      // Expect handleMesaage to be called
      try {
         caseCompletedHandler.handleMessage(CASE_ID, WORKFLOW_NAME, EXTERNAL_ID, null, null, SLAID);
      }
      catch (Exception e) {
         fail("Failed to mock method call");
      }
      // Expect getObject to be called;
      try {
         EasyMock.expect(message.getObject()).andReturn(cuWUpdate);
         
      }
      catch (JMSException e) {
         fail("Failed to set up message object");
      }

      PowerMock.replayAll(message, caseCompletedHandler);

      // Actual test
      service.onMessage(message);

      PowerMock.verifyAll();*/
   }
}
