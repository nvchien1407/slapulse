//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import com.renewtek.model.User;
import com.renewtek.service.MailEngine;
import com.renewtek.service.UserManager;
import com.renewtek.webapp.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Simple class to retrieve and send a password hint to users.
 *
 * <p>
 * <a href="PasswordHintController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class PasswordHintController implements Controller {

   private final Logger log = LoggerFactory.getLogger(getClass());
   private UserManager mgr = null;
   private MessageSource messageSource = null;
   protected MailEngine mailEngine = null;
   protected SimpleMailMessage message = null;

   public void setUserManager(UserManager userManager) {
      this.mgr = userManager;
   }

   public void setMessageSource(MessageSource messageSource) {
      this.messageSource = messageSource;
   }

   public void setMailEngine(MailEngine mailEngine) {
      this.mailEngine = mailEngine;
   }

   public void setMessage(SimpleMailMessage message) {
      this.message = message;
   }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'handleRequest' method...");
      }

      String username = request.getParameter("username");
      MessageSourceAccessor text = new MessageSourceAccessor(messageSource, request.getLocale());

      // ensure that the username has been sent
      if (username == null) {
         log.warn("Username not specified, notifying user that it's a required field.");

         request.setAttribute("error",
               text.getMessage("errors.required", new Object[] { text.getMessage("user.username") }));

         return new ModelAndView("login");
      }

      if (log.isDebugEnabled()) {
         log.debug("Processing Password Hint...");
      }

      // look up the user's information
      try {
         User user = mgr.getUser(username);

         StringBuffer msg = new StringBuffer();
         msg.append("Your password hint is: " + user.getPasswordHint());
         msg.append("\n\nLogin at: " + RequestUtil.getAppURL(request));

         message.setTo(user.getEmail());
         String subject = text.getMessage("webapp.prefix") + text.getMessage("user.passwordHint");
         message.setSubject(subject);
         message.setText(msg.toString());
         mailEngine.send(message);

         saveMessage(request, text.getMessage("login.passwordHint.sent", new Object[] { username, user.getEmail() }));
      }
      catch (Exception e) {
         saveError(request, text.getMessage("login.passwordHint.error", new Object[] { username }));
      }

      return new ModelAndView(new RedirectView(request.getContextPath()));
   }

   @SuppressWarnings("unchecked")
   public void saveError(HttpServletRequest request, String error) {
      List<Object> errors = (List<Object>) request.getSession().getAttribute("errors");
      if (errors == null) {
         errors = new ArrayList<Object>();
      }
      errors.add(error);
      request.getSession().setAttribute("errors", errors);
   }

   // this method is also in BaseForm Controller
   @SuppressWarnings("unchecked")
   public void saveMessage(HttpServletRequest request, String msg) {
      List<String> messages = (List<String>) request.getSession().getAttribute("messages");
      if (messages == null) {
         messages = new ArrayList<String>();
      }
      messages.add(msg);
      request.getSession().setAttribute("messages", messages);
   }
}
