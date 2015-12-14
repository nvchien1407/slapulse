package com.renewtek.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.JmsException;

/**
 * Using @see com.renewtek.util.LazyBeanInitializer is not enough to disable JMS as
 * org.springframework.jms.listener.DefaultMessageListenerContainer implements
 * @see org.springframework.context.Lifecycle and the spring container still calls start on it.
 * This class enables the message listener container to be disabled
 * @author jerryshea
 */
public class MessageListenerContainer extends org.springframework.jms.listener.DefaultMessageListenerContainer implements ApplicationContextAware {
   private boolean enabled = true;
   private ApplicationContext applicationContext;
   private String messageListenerBean;

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   /**
    * "lazy-init" is not enough to disable dependency injection. We will manually inject messageListener when need.
    * @param messageListenerBean
    */
   public void setMessageListenerBean(String messageListenerBean) {
      this.messageListenerBean = messageListenerBean;
   }

   @Override
   public void start() throws JmsException {
      if (enabled) {
         super.start();
      }
   }

   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      this.applicationContext = applicationContext;
   }

   @Override
   public void afterPropertiesSet() {
      if (enabled) {
         Object messageListener = applicationContext.getBean(this.messageListenerBean);
         if (messageListener != null) {
            this.setMessageListener(messageListener);
         }
      }
      super.afterPropertiesSet();
   }
}
