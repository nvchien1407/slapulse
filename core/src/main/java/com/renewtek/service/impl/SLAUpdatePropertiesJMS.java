package com.renewtek.service.impl;

//import com.renewtek.kata.api.service.jms.ServiceCommand;
//import com.renewtek.kata.api.service.jms.ServiceCommandCaseManager;
import com.renewtek.service.SLAUpdatePropertiesAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class SLAUpdatePropertiesJMS implements SLAUpdatePropertiesAdapter {

   private static final Log LOG = LogFactory.getLog(SLAUpdatePropertiesJMS.class);
   
   private JmsTemplate jmsTemplate;
   private Destination topic;
   private boolean enabled = false;
   private String username;
   private String password;

   public void lockUpdateCasePropertyAndUnlock(long caseId, String propertyName, String propValue) {
      Object[] params = new Object[] { caseId, propertyName, propValue };
      //ServiceCommand sc = new ServiceCommandCaseManager(params, ServiceCommandCaseManager.LOCK_UPDATE_CASE_PROPERTY_AND_UNLOCK);
      //send(sc);
   }
   
   public void lockUpdateCasePropertyAndUnlockAndNotify(long caseId, String propertyName, String propValue, String fromEmail, String toEmail, String emailTemplate)
   {
      Object[] params = new Object[] { caseId, new String[]{propertyName},new String[]{propValue}, fromEmail, toEmail, emailTemplate };
      //ServiceCommand sc = new ServiceCommandCaseManager(params, ServiceCommandCaseManager.LOCK_UPDATE_CASE_PROPERTY_AND_UNLOCK_MULTIPLE_AND_NOTIFY);
      //send(sc);
   }
   
   public void lockUpdateCasePropertyAndUnlockMultiple(long caseId, String[] propertyNames, String[] propValues) {
      Object[] params = new Object[] { caseId, propertyNames, propValues };
      //ServiceCommand sc = new ServiceCommandCaseManager(params, ServiceCommandCaseManager.LOCK_UPDATE_CASE_PROPERTY_AND_UNLOCK_MULTIPLE);
      //send(sc);
   }

   /*private void send(final ServiceCommand sc) {
      LOG.debug("Publishing message to Saigon: "+sc);

      jmsTemplate.send(topic, new MessageCreator() {
         public Message createMessage(Session transaction) throws JMSException {
            final Message message = transaction.createObjectMessage(sc);
            message.setStringProperty(ServiceCommand.JMS_USERNAME, username);
            message.setStringProperty(ServiceCommand.JMS_PASSWORD, password);
            return message;
         }
      });
   }*/

   public void setJmsTemplate(JmsTemplate jmsTemplate) {
      this.jmsTemplate = jmsTemplate;
   }

   public void setTopic(Destination topic) {
      this.topic = topic;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public void setPassword(String password) {
      this.password = password;
   }
   //public com.renewtek.service.client.CaseDto findCaseById(Long caseId){return null;}
}
