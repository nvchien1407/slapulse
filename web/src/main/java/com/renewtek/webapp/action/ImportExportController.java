//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT(c)2011 SMS MANAGEMENT & TECHNOLOGY
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.renewtek.service.ImportExportManager;
import com.renewtek.service.SomethingHappenedInterceptor;

public class ImportExportController extends AbstractController {

   private final Logger log = LoggerFactory.getLogger(getClass());

   private ImportExportManager importExportManager = null;

   public void setImportExportManager(ImportExportManager importExportManager) {
      this.importExportManager = importExportManager;
   }

   private String upload(MultipartFile multipartFile) throws IOException {
      String filename = multipartFile.getOriginalFilename();
      String ct = multipartFile.getContentType();
      log.debug("Uploading filename {} ct {}", filename, ct);
      byte[] bytes = multipartFile.getBytes();
      String xml = new String(bytes);
      return xml;
   }

   private ModelAndView renderFile(String filename, String xml, HttpServletResponse response) throws IOException {
      response.setContentType("application/xml");
      response.setContentLength(xml.length());
      filename = filename + ".xml";
      response.setHeader("Content-Disposition","attachment; filename=\"" + filename  +"\"");
      response.getOutputStream().print(xml);
      return null;
   }

   @Override
   protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'handleRequestInternal' method...");
      }

      boolean doImport = (request instanceof MultipartHttpServletRequest);
      if (doImport) {
         return uploadAndImport(request);
      }

      // TODO: this is horrible - better to do with multiController
      boolean doExport = false;
      @SuppressWarnings("unchecked") Enumeration<String> paramNames = request.getParameterNames();
      while(paramNames.hasMoreElements()) {
         String paramName = paramNames.nextElement();
         if (paramName.startsWith("export")) {
            doExport = true;
            break;
         }
      }

      if (doExport) {
         return export(request, response);
      }
      else {
         Map<String, Object> model = new HashMap<String, Object>();
         return new ModelAndView("importExport", model);
      }
   }

   private ModelAndView uploadAndImport(HttpServletRequest request) {
      String message;
      try {
         MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
         MultipartFile multipartFile = multipartRequest.getFile("fileWorkflow");
         message = importExportManager.importWorkflows(upload(multipartFile));
         TransactionSynchronizationManager.bindResource(SomethingHappenedInterceptor.SOMETHING_HAPPENED, Boolean.TRUE);
      } catch (Exception e) {
         log.error("Error caught during import", e);
         return error(e);
      }
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("message", message);
      return new ModelAndView("importExport", model);
   }

   private ModelAndView export(HttpServletRequest request, HttpServletResponse response) {
      try {
         String xml = importExportManager.exportWorkflows();
         ModelAndView rv = renderFile("workflow_sla", xml, response);
         TransactionSynchronizationManager.bindResource(SomethingHappenedInterceptor.SOMETHING_HAPPENED, Boolean.TRUE);
         return rv;
      } catch (Exception e) {
         log.error("Error caught during export", e);
         return error(e);
      }
   }

   private ModelAndView error(Exception e) {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("message", "<b>ERROR</b>: " + e.getMessage());
      return new ModelAndView("importExport", model);
   }
}
