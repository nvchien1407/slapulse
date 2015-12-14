//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import com.renewtek.model.ChangeLog;
import com.renewtek.service.ChangeLogManager;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class ChangeLogFormController extends BaseFormController {

   private ChangeLogManager changeLogManager = null;

   public void setChangeLogManager(ChangeLogManager changeLogManager) {
      this.changeLogManager = changeLogManager;
   }

   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      String id = request.getParameter("id");
      ChangeLog changeLog = null;

      if (!StringUtils.isEmpty(id)) {
         // Remove thousand separators commas if any
         id = StringUtils.replaceChars(id, ",", "");

         changeLog = changeLogManager.getChangeLog(id);
      }
      else {
         changeLog = new ChangeLog();
      }

      return changeLog;
   }

   public ModelAndView onSubmit(HttpServletRequest request,
                                HttpServletResponse response,
                                Object command,
                                BindException errors) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'onSubmit' method...");
      }

      ChangeLog changeLog = (ChangeLog) command;
      boolean isNew = (changeLog.getId() == null);
      Locale locale = request.getLocale();

      if (request.getParameter("delete") != null) {
         changeLogManager.removeChangeLog(changeLog.getId().toString());

         saveMessage(request, getText("changeLog.deleted", locale));
      }
      else {
         changeLogManager.saveChangeLog(changeLog);

         String key = (isNew) ? "changeLog.added" : "changeLog.updated";
         saveMessage(request, getText(key, locale));

         if (!isNew) {
            return new ModelAndView("redirect:editChangeLog.html", "id", changeLog.getId());
         }
      }

      return new ModelAndView(getSuccessView());
   }
}
