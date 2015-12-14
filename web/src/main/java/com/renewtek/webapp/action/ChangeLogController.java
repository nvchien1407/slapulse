//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.renewtek.Constants;
import com.renewtek.model.ChangeLog;
import com.renewtek.service.ChangeLogManager;

public class ChangeLogController implements Controller {

   private final Logger log = LoggerFactory.getLogger(ChangeLogController.class);
   private ChangeLogManager changeLogManager = null;

   public void setChangeLogManager(ChangeLogManager changeLogManager) {
      this.changeLogManager = changeLogManager;
   }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'handleRequest' method...");
      }

      List<ChangeLog> changeLogs;
      ChangeLog changeLog = new ChangeLog();
      // populate object with request parameters
      BeanUtils.populate(changeLog, request.getParameterMap());
      if (changeLog.getOperateTime() != null) {
         changeLogs = changeLogManager.getChangeLogsSince(changeLog.getOperateTime());
      }
      else {
         changeLogs = changeLogManager.getChangeLogs(changeLog);
      }
      
      return new ModelAndView("changelogList", Constants.CHANGELOG_LIST, changeLogs);
   }
}
