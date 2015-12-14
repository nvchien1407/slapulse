// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Class for sending e-mail messages based on Velocity templates or with
 * attachments.
 * <p/>
 * <p>
 * <a href="MailEngine.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Matt Raible
 */
public class MailEngine {

   protected static final Log log = LogFactory.getLog(MailEngine.class);

   private JavaMailSender mailSender;

   private VelocityEngine velocityEngine;

   public void setMailSender(JavaMailSender mailSender) {
      this.mailSender = mailSender;
   }

   public void setVelocityEngine(VelocityEngine velocityEngine) {
      this.velocityEngine = velocityEngine;
   }

   /**
    * Send a simple message based on a Velocity template.
    * 
    * @param msg
    * @param templateName
    * @param model
    */
   @SuppressWarnings("unchecked")
   public void sendMessage(SimpleMailMessage msg, String templateName, Map model) {
      String result = null;

      try {
         result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, model);
      }
      catch (VelocityException e) {
         log.error("Failed to merge template: ", e);
      }
      msg.setText(result);
      send(msg);
   }

   /**
    * Send a simple message based on a Velocity template.
    * 
    * @param templateName
    * @param model
    */
   @SuppressWarnings("unchecked")
   public void sendMessage(String[] to, String templateName, Map model) {
      String result = null;

      try {
         result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, model);
      }
      catch (VelocityException e) {
         log.error("Failed to merge template", e);
      }

      MimeMessage message = mailSender.createMimeMessage();

      // use the true flag to indicate you need a multipart message
      MimeMessageHelper helper;
      try {
         helper = new MimeMessageHelper(message, true);
         helper.setTo(to);
         helper.setText(result, true);
         helper.setSubject("Suppliers Update Notify");
      }
      catch (MessagingException e) {
         // TODO Auto-generated catch block
         log.error(e.getMessage());
         // e.printStackTrace();
      }
      // helper.addAttachment(attachmentName, resource);

      try {
         mailSender.send(message);
      }
      catch (MailException ex) {
         // log it and go on
         log.error(ex.getMessage());
      }

   }

   /**
    * Send a simple message with pre-populated values.
    * 
    * @param msg
    */
   public void send(SimpleMailMessage msg) {
      try {
         mailSender.send(msg);
      }
      catch (MailException ex) {
         // log it and go on
         log.error(ex.getMessage());
      }
   }

   /**
    * Convenience method for sending messages with attachments.
    * 
    * @param emailAddresses
    * @param resource
    * @param bodyText
    * @param subject
    * @param attachmentName
    * @throws MessagingException
    * @author Ben Gill
    */
   public void sendMessage(String[] emailAddresses, ClassPathResource resource, String bodyText, String subject,
         String attachmentName) throws MessagingException {
      MimeMessage message = mailSender.createMimeMessage();

      // use the true flag to indicate you need a multipart message
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setTo(emailAddresses);
      helper.setText(bodyText);
      helper.setSubject(subject);

      helper.addAttachment(attachmentName, resource);

      mailSender.send(message);
   }

}
