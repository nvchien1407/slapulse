//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.renewtek.Constants;
import com.renewtek.dao.SLACaseTypeManagerDAO;
import com.renewtek.model.BusinessProcess;
import com.renewtek.model.Reference;
import com.renewtek.service.BusinessProcessManager;
import com.renewtek.service.ReferenceManager;
//import com.renewtek.service.client.PickListDto;

public class BusinessProcessFormController extends BaseFormController {

   private BusinessProcessManager businessProcessManager = null;
   private ReferenceManager referenceManager;
   private SLACaseTypeManagerDAO slaCaseTypeManagerDAO;

   public void setReferenceManager(ReferenceManager referenceManager) {
      this.referenceManager = referenceManager;
   }
   
   public void setSlaCaseTypeManagerDAO(SLACaseTypeManagerDAO slaCaseTypeManager) {
      this.slaCaseTypeManagerDAO = slaCaseTypeManager;
   }
   public void setBusinessProcessManager(BusinessProcessManager businessProcessManager) {
      this.businessProcessManager = businessProcessManager;
   }
   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      String id = request.getParameter("id");
      BusinessProcess businessProcess = null;

      if (!StringUtils.isEmpty(id)) {
         businessProcess = businessProcessManager.getBusinessProcess(id);
      }
      else {
         businessProcess = new BusinessProcess();
         Reference typeRef = referenceManager.getReferenceByItemName(Constants.DEFAULT_BUSINESS_PROCESS_TYPE);
         if (typeRef != null)
            businessProcess.setType(typeRef);
      }      
      return businessProcess;
   }

   protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
      super.initBinder(request, binder);

      // this property editor does not seem to get used. There is custom code in
      // onBindAndValidate and onSubmit to coerce References to and from the JSP instead
      ReferencePropertyEditor editor = new ReferencePropertyEditor();
      editor.setReferenceManager(this.referenceManager);

      binder.registerCustomEditor(Reference.class, "name", editor);
      binder.registerCustomEditor(Reference.class, "type", editor);
      binder.registerCustomEditor(Reference.class, "txn", editor);
      binder.registerCustomEditor(Reference.class, "step", editor);
   }

   protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
      BusinessProcess businessProcess = (BusinessProcess)command;
      try {
         String selectedValue = request.getParameter("businessProcessName");
         if (selectedValue == null || selectedValue.equals("")) {
            errors.rejectValue("name", null, null, "You must select a BusinessProcess Name");
         }
         Integer bpNameId = Integer.valueOf(selectedValue);
         // custom conversion code - see comment in initBinder
         businessProcess.setName(referenceManager.getReference(bpNameId.toString()));

         selectedValue = request.getParameter("businessProcessType");
         if (selectedValue == null || selectedValue.equals("")) {
            errors.rejectValue("type", null, null, "You must select a BusinessProcess Type");
         }
         Integer bpTypeId = Integer.valueOf(selectedValue);
         // custom conversion code - see comment in initBinder
         businessProcess.setType(referenceManager.getReference(bpTypeId.toString()));

         selectedValue = request.getParameter("businessProcessTxn");
         if (StringUtils.isEmpty(selectedValue)) {
            businessProcess.setTxn(null);
         }
         else {
            Integer bpTxnId = Integer.valueOf(selectedValue);
            // custom conversion code - see comment in initBinder
            businessProcess.setTxn(referenceManager.getReference(bpTxnId.toString()));
         }

         selectedValue = request.getParameter("businessProcessStep");
         if (StringUtils.isEmpty(selectedValue)) {
            businessProcess.setStep(null);
         }
         else {
            Integer bpStepId = Integer.valueOf(selectedValue);
            // custom conversion code - see comment in initBinder
            businessProcess.setStep(referenceManager.getReference(bpStepId.toString()));
         }

         String bpDescription = request.getParameter("description");
         
         if(bpDescription == null || bpDescription.trim().equals("")) {
            errors.rejectValue("description", null, null, "You must input a BusinessProcess Description");
         }

         String bpId = request.getParameter("id");

         // don't check this param if we are deleting or updating
         if ((StringUtils.isEmpty(bpId)) && (request.getParameter("delete") == null)) {
            BusinessProcess existingBP = businessProcessManager.getBusinessProcess(
                  businessProcess.getName().getItemName(), 
                  businessProcess.getType().getItemName(), 
                  businessProcess.getTxn() != null ? businessProcess.getTxn().getItemName() : null,
                  businessProcess.getStep() != null ? businessProcess.getStep().getItemName() : null);
            if (existingBP != null) {
               errors.reject(null, null, "BusinessProcess with same name, type and transaction already exists.");
            }
         }
      } 
      catch (NumberFormatException e) {
         errors.reject(null, null, "Invalid Business Process name/type");
      }
   }

   public ModelAndView onSubmit(HttpServletRequest request,
                                HttpServletResponse response,
                                Object command,
                                BindException errors) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'onSubmit' method...");
      }

      BusinessProcess businessProcess = (BusinessProcess) command;
      boolean isNew = (businessProcess.getId() == null);
      Locale locale = request.getLocale();

      if (request.getParameter("delete") != null) {
         businessProcessManager.removeBusinessProcess(businessProcess.getId().toString());

         saveMessage(request, getText("businessProcess.deleted", locale));
      }
      else {

         String bpNameId = request.getParameter("businessProcessName");
         String bpTypeId = request.getParameter("businessProcessType");
         String bpTxnId = request.getParameter("businessProcessTxn");
         String bpStepId = request.getParameter("businessProcessStep");
         String emailNotification = request.getParameter("emailNotificationcb");

         // custom conversion code - see comment in initBinder
         businessProcess.setName(referenceManager.getReference(bpNameId));
         businessProcess.setType(referenceManager.getReference(bpTypeId));
         if (StringUtils.isNotEmpty(bpTxnId)) {
            businessProcess.setTxn(referenceManager.getReference(bpTxnId));
         }
         else {
            businessProcess.setTxn(null);
         }
         if (StringUtils.isNotEmpty(bpStepId)) {
            businessProcess.setStep(referenceManager.getReference(bpStepId));
         }
         else {
            businessProcess.setStep(null);
         }
         businessProcess.setEmailNotification(emailNotification == null ? Boolean.FALSE : Boolean.TRUE);
         businessProcessManager.saveBusinessProcess(businessProcess);

         String key = (isNew) ? "businessProcess.added" : "businessProcess.updated";
         saveMessage(request, getText(key, locale));

         if (!isNew) {
            return new ModelAndView("redirect:editBusinessProcess.html", "id", businessProcess.getId());
         }
      }

      return new ModelAndView(getSuccessView());
   }

   protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors)
         throws Exception {

      request.setAttribute("businessProcessNameList", referenceManager.getReferencesByParam(Constants.REFERENCE_BUSINESSPROCESS_NAME));
      request.setAttribute("businessProcessTypeList", referenceManager.getReferencesByParam(Constants.REFERENCE_BUSINESSPROCESS_TYPE));
      request.setAttribute("businessProcessTxnList", referenceManager.getReferencesByParam(Constants.REFERENCE_BUSINESSPROCESS_TXN));
      request.setAttribute("businessProcessStepList", referenceManager.getReferencesByParam(Constants.REFERENCE_BUSINESSPROCESS_STEP));
      //request.setAttribute("emailTemplate", slaCaseTypeManagerDAO.findPickListByName(Constants.EMAIL_TEMPLATE));
      //request.setAttribute("fromEmail", slaCaseTypeManagerDAO.findPickListByName(Constants.FROM_EMAIL));
      //request.setAttribute("toEmail", slaCaseTypeManagerDAO.findPickListByName(Constants.TO_EMAIL));     
      
      return super.showForm(request, response, errors);
   }
  
}
