package com.renewtek.event.jms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

//import com.renewtek.kata.core.process.CaseUpdate;
//import com.renewtek.kata.core.service.dto.CaseDto;
//import com.renewtek.kata.core.service.dto.PropertyResponseDto;
//import com.renewtek.kata.core.utils.PropertyUtils;

/**
 * 
 * This will implement the message listener which receive JMS message from
 * certain JMS Queue and determine the precondition of timer.
 * 
 * @author kietnguyen
 * 
 */

public class SlaMessageListener implements MessageListener {

   private HashMap<String, String> propertyNameValue;
   private Map<String, IEventHandler> messageHandlerMap;
   private boolean allowStepSla;
   
   private static final Logger log = LoggerFactory.getLogger(SlaMessageListener.class);
   private static final String TRANSACTION_PROPERTY_NAME = "TRANSACTION_PROPERTY_NAME"; 
   private static final String START_TIME_PROPERTY_NAME = "START_TIME_PROPERTY_NAME";
   private static final String SLA_ID_PROPERTY_NAME = "SLA_ID_PROPERTY_NAME";

   @Transactional(readOnly=true)
   public void onMessage(Message message) {

      if (message instanceof ObjectMessage) {
         try {
            ObjectMessage om = (ObjectMessage) message;
            processingMessage(om.getObject());
         } catch (JMSException e) {
            log.error("Cannot extract message", e);
         } catch (Exception e) {
            log.error("Error processing message", e);
         }
      } else {
         log.debug("This is not ObjectMessage, so ignore it");
      }

   }

   // TODO: refactor to emit commands, deduplicate and delay
   private void processingMessage(Object message) throws Exception {

      /*if (message instanceof CaseUpdate.CaseUpdateWithCase) {
         CaseUpdate.CaseUpdateWithCase cuWithCase = (CaseUpdate.CaseUpdateWithCase) message;
         log.info("Start processing the message: {}", cuWithCase);

         List<CaseUpdate> caseUpdateLst = cuWithCase.caseUpdates;
         for (CaseUpdate cu : caseUpdateLst) {
            log.debug("  Start processing the caseUpdate: {}", cu);
            String caseType = cu.getType().toString();
            IEventHandler eventHandle = messageHandlerMap.get(caseType);
            if (eventHandle != null) {
               CaseDto caseDto = getCaseDtoByExternalId(cuWithCase, cu.getExternalCaseId());
               eventHandle.handleMessage(caseDto.getId(), getWorkflowName(caseDto), getExternalCaseId(cu), 
                     getTransactionProperty(caseDto), getStartDateProperty(caseDto), getSlaIdProperty(caseDto));
            }
            else {
               log.warn("Ignoring message as we have no handler to process it: {}", cu);
            }
         }
      }
      else {
         log.warn("Ignoring message as it is not a CaseUpdateWithCase");
      }*/
   }

   /*private CaseDto getCaseDtoByExternalId(CaseUpdate.CaseUpdateWithCase cuWithCase, String externalId) {
      Map<String, CaseDto> caseMap = cuWithCase.caseMap;
      CaseDto caseDto = caseMap.get(externalId);
      return caseDto;
   }

   private String getWorkflowName(CaseDto caseDto) {
      String worflowName = caseDto.getCaseTypeVersion().getName();
      return worflowName;
   }

   private String getExternalCaseId(CaseUpdate caseUpdate) {
      String externalCaseId = caseUpdate.getExternalCaseId();
      return externalCaseId;
   }

   private String getTransactionProperty(CaseDto caseDto) {
      return getProperty(caseDto, TRANSACTION_PROPERTY_NAME);
   }

   private String getStartDateProperty(CaseDto caseDto) {
      return getProperty(caseDto, START_TIME_PROPERTY_NAME);
   }

   private Integer getSlaIdProperty(CaseDto caseDto) {
      String slaId = getWIProperty(caseDto, propertyNameValue.get(SLA_ID_PROPERTY_NAME));
      if (StringUtils.isNotEmpty(slaId)) {
         try {
            int i = Integer.parseInt(slaId);
            if (i != 0) {
               return i;
            }
         }
         catch (NumberFormatException e) {
            log.error("Can't parse "+slaId+" because "+e.getMessage());
         }
      }
      return null;
   }

   private String getWIProperty(CaseDto caseDto, String propertyName) {
      for (PropertyResponseDto pr : caseDto.getWorkItemPropertyResponses()) {
         if (propertyName.equals(pr.getProperty().getName())) {
            return pr.getValue();
         }
      }
      return null;
   }

   private String getProperty(CaseDto caseDto, String pn) {
      PropertyResponseDto pr = PropertyUtils.getProperty(caseDto, propertyNameValue.get(pn));
      return pr != null ? pr.getValue() : null;
   }*/

   public Map<String, IEventHandler> getMessageHandlerMap() {
      return messageHandlerMap;
   }

   public void setMessageHandlerMap(Map<String, IEventHandler> messageHandlerMap) {
      this.messageHandlerMap = messageHandlerMap;
   }

   public void setPropertyNameValue(HashMap<String, String> propertyNameValue) {
      this.propertyNameValue = propertyNameValue;
   }

   public void setAllowStepSla(boolean allowStepSla) {
      this.allowStepSla = allowStepSla;
   }
}
