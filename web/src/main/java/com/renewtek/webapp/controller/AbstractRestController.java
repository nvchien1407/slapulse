package com.renewtek.webapp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * @author jerryshea
 * Base controller for restful controllers. Mainly contains common exception mapping with correct
 * status code and json response messages.
 */
public abstract class AbstractRestController {
   private static final Log LOG = LogFactory.getLog(AbstractRestController.class);

   public static final String EXCEPTION_VIEW_NAME = "exceptionView";

   @ExceptionHandler(HibernateOptimisticLockingFailureException.class)
   public ModelAndView handleHibernateOptimisticLockingFailure(HttpServletResponse response) {

      String message = "You have attempted to save a " + getResourceDisplayName() + " that has been updated " +
            "by someone else. Please close the " + getResourceDisplayName() + " and reapply your changes";

      response.setStatus(HttpServletResponse.SC_CONFLICT);

      return new ModelAndView(EXCEPTION_VIEW_NAME, createExceptionModelMap(message));
   }


   @ExceptionHandler({EmptyResultDataAccessException.class, ObjectRetrievalFailureException.class})
   public ModelAndView handleResourceNotFound(HttpServletResponse response) {

      response.setStatus(HttpServletResponse.SC_NOT_FOUND);

      return new ModelAndView(EXCEPTION_VIEW_NAME, createExceptionModelMap("The requested " + getResourceDisplayName() + " is not available"));
   }

   @ExceptionHandler(DataIntegrityViolationException.class)
   public ModelAndView handleDataIntegrityViolation(HttpServletResponse response) {

      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

      return new ModelAndView(EXCEPTION_VIEW_NAME, createExceptionModelMap("There was a problem with the " + getResourceDisplayName() + " data you submitted. "));
   }


   @ExceptionHandler({ Exception.class, RuntimeException.class })
   public ModelAndView handleGenericException(Exception exception, HttpServletResponse response) {

      LOG.error("handleGenericException", exception);
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

      return new ModelAndView(EXCEPTION_VIEW_NAME, createExceptionModelMap("An Error occurred while processing a " + getResourceDisplayName() + ": " + exception.toString()));
   }


   protected ModelMap createExceptionModelMap(String message) {
      ModelMap model = new ModelMap();


      model.addAttribute("success", false);
      model.addAttribute("message", message);

      return model;
   }


   /**
    * Return a name for the core controller rest resource that can be used for user consumable error messages
    *
    * @return displayName
    */
   protected abstract String getResourceDisplayName();
}
