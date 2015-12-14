//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.renewtek.Constants;
import com.renewtek.model.User;
import com.renewtek.service.MailEngine;
import com.renewtek.service.SomethingHappenedInterceptor;
import com.renewtek.service.UserManager;
import com.renewtek.util.DateUtil;

/**
 * Implementation of <strong>SimpleFormController</strong> that contains
 * convenience methods for subclasses.  For example, getting the current
 * user and saving messages/errors. This class is intended to
 * be a base class for all Form controllers.
 *
 * <p><a href="BaseFormController.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseFormController extends SimpleFormController {

   protected final transient Logger log = LoggerFactory.getLogger(getClass());

   private UserManager userManager = null;
   protected MailEngine mailEngine = null;
   protected SimpleMailMessage message = null;
   protected String templateName = null;
   protected String cancelView;

   public void setUserManager(UserManager userManager) {
      this.userManager = userManager;
   }

   public UserManager getUserManager() {
      return this.userManager;
   }

   @SuppressWarnings("unchecked")
   public void clearMessage(HttpServletRequest request) {
      List<String> messages = (List<String>) request.getSession().getAttribute("messages");

      if (messages == null) {
         return;
      }
      messages.clear();
   }

   @SuppressWarnings("unchecked")
   public void saveMessage(HttpServletRequest request, String msg) {

      List<String> messages = (List<String>) request.getSession().getAttribute("messages");

      if (messages == null) {
         messages = new ArrayList<String>();
      }

      messages.add(msg);
      log.debug("message added");
      request.getSession().setAttribute("messages", messages);
   }

   /**
    * Convenience method for getting a i18n key's value.  Calling
    * getMessageSourceAccessor() is used because the RequestContext variable
    * is not set in unit tests b/c there's no DispatchServlet Request.
    *
    * @param msgKey
    * @param locale the current locale
    * @return
    */
   public String getText(String msgKey, Locale locale) {
      return getMessageSourceAccessor().getMessage(msgKey, locale);
   }

   /**
    * Convenient method for getting a i18n key's value with a single
    * string argument.
    *
    * @param msgKey
    * @param arg
    * @param locale the current locale
    * @return
    */
   public String getText(String msgKey, String arg, Locale locale) {
      return getText(msgKey, new Object[] { arg }, locale);
   }

   /**
    * Convenience method for getting a i18n key's value with arguments.
    *
    * @param msgKey
    * @param args
    * @param locale the current locale
    * @return
    */
   public String getText(String msgKey, Object[] args, Locale locale) {
      return getMessageSourceAccessor().getMessage(msgKey, args, locale);
   }

   /**
    * Convenience method to get the user object from the session
    *
    * @param request the current request
    * @return the user's populated object from the session
    */
   protected User getUser(HttpServletRequest request) {
      return (User) request.getSession().getAttribute(Constants.USER_KEY);
   }

   /**
    * Convenience method to get the Configuration HashMap
    * from the servlet context.
    *
    * @return the user's populated form from the session
    */
   @SuppressWarnings("unchecked")
   public Map<String, Object> getConfiguration() {
      Map<String, Object> config = (HashMap<String, Object>) getServletContext().getAttribute(Constants.CONFIG);

      // so unit tests don't puke when nothing's been set
      if (config == null) {
         return new HashMap<String, Object>();
      }

      return config;
   }

   /**
    * Default behavior for FormControllers - redirect to the successView
    * when the cancel button has been pressed.
    */
   public ModelAndView processFormSubmission(HttpServletRequest request,
                                             HttpServletResponse response,
                                             Object command,
                                             BindException errors) throws Exception {
      if (request.getParameter("cancel") != null) {
         return new ModelAndView(getCancelView());
      }

      // This code copied from this method in base class as we want to change the behaviour of onSubmit.
      // The interceptor we wrote stopped intercepting onSubmit for some reason.....
      // TODO: come back to and do nicer?
      if (errors.hasErrors()) {
         if (logger.isDebugEnabled()) {
            logger.debug("Data binding errors: " + errors.getErrorCount());
         }
         return showForm(request, response, errors);
      }
      else if (isFormChangeRequest(request)) {
         logger.debug("Detected form change request -> routing request to onFormChange");
         onFormChange(request, response, command);
         return showForm(request, response, errors);
      }
      else {
         logger.debug("No errors -> processing submit");
         ModelAndView rv = onSubmit(request, response, command, errors);
         TransactionSynchronizationManager.bindResource(SomethingHappenedInterceptor.SOMETHING_HAPPENED, Boolean.TRUE);
         return rv;
      }
   }

   /**
    * Set up a custom property editor for converting form inputs to real objects
    */
   protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
      NumberFormat nf = NumberFormat.getNumberInstance();

      binder.registerCustomEditor(Boolean.class, null, new CustomBooleanEditor("true", "false", true));
      binder.registerCustomEditor(Integer.class, null, new CustomNumberEditor(Integer.class, nf, true));
      binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, nf, true));
      binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
      SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.getDatePattern());
      dateFormat.setLenient(false);
      binder.registerCustomEditor(Date.class, null, new DatePropertyEditor(dateFormat, true));
   }

   /**
    * Convenience message to send messages to users, includes app URL as footer.
    */
   protected void sendUserMessage(String name, String email, String msg, String url, Map<String, Object> model) {
      if (log.isDebugEnabled()) {
         log.debug("sending e-mail to user [" + email + "]...");
      }

      message.setTo(name + "<" + email + ">");

      model.put("message", msg);
      model.put("applicationURL", url);
      mailEngine.sendMessage(message, templateName, model);
   }

   protected void sendUserMessage(User user, String msg, String url) {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", user);
      this.sendUserMessage(user.getFullName(), user.getEmail(), msg, url, model);
   }

   public void setMailEngine(MailEngine mailEngine) {
      this.mailEngine = mailEngine;
   }

   public void setMailMessage(SimpleMailMessage message) {
      this.message = message;
   }

   public void setTemplateName(String templateName) {
      this.templateName = templateName;
   }

   /**
    * Indicates what view to use when the cancel button has been pressed.
    */
   public final void setCancelView(String cancelView) {
      this.cancelView = cancelView;
   }

   public final String getCancelView() {
      // Default to successView if cancelView is invalid
      if (this.cancelView == null || this.cancelView.length() == 0) {
         return getSuccessView();
      }
      return this.cancelView;
   }

}
