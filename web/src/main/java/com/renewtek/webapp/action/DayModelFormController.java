//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import com.renewtek.model.DayModel;
import com.renewtek.service.DayModelManager;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class DayModelFormController extends BaseFormController {

   private DayModelManager dayModelManager = null;

   public void setDayModelManager(DayModelManager dayModelManager) {
      this.dayModelManager = dayModelManager;
   }

   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      String id = request.getParameter("id");
      DayModel dayModel = null;

      if (!StringUtils.isEmpty(id)) {
         dayModel = dayModelManager.getDayModel(id);
      }
      else {
         dayModel = new DayModel();
      }

      return dayModel;
   }

   public ModelAndView onSubmit(HttpServletRequest request,
                                HttpServletResponse response,
                                Object command,
                                BindException errors) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'onSubmit' method...");
      }

      DayModel dayModel = (DayModel) command;
      boolean isNew = (dayModel.getId() == null);
      Locale locale = request.getLocale();

      if (request.getParameter("delete") != null) {
         dayModelManager.removeDayModel(dayModel.getId().toString());

         saveMessage(request, getText("dayModel.deleted", locale));
      }
      else {
         dayModelManager.saveDayModel(dayModel);

         String key = (isNew) ? "dayModel.added" : "dayModel.updated";
         saveMessage(request, getText(key, locale));

         if (!isNew) {
            return new ModelAndView("redirect:editDayModel.html", "id", dayModel.getId());
         }
         else {
            return new ModelAndView("redirect:editDayModel.html", "id", dayModel.getId());
         }
      }

      return new ModelAndView(getSuccessView());
   }
}
