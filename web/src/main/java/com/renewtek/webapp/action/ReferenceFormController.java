//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

import com.renewtek.model.Reference;
import com.renewtek.service.ReferenceManager;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class ReferenceFormController extends BaseFormController {

   private static final String ADD_ITEM = "addItem";
   private static final String EDIT_GROUP = "editGroup";
   private static final String ADD_GROUP = "addGroup";
   private static final String EDIT_ITEM = "editItem";
   private static final String ACTION_TYPE = "actionType";
   private static final String ITEM = "ITEM";
   private static final String GROUP = "GROUP";
   private ReferenceManager referenceManager = null;

   public void setReferenceManager(ReferenceManager referenceManager) {
      this.referenceManager = referenceManager;
   }

   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      String id = request.getParameter("id");
      Reference reference = null;

      if (!StringUtils.isEmpty(id)) {
         // Remove thousand separators commas if any
         id = StringUtils.replaceChars(id, ",", "");
         reference = referenceManager.getReference(id);
         request.setAttribute(ACTION_TYPE, EDIT_ITEM);
      }
      else {
         if (null != request.getParameter("isGroup")) {
            request.setAttribute(ACTION_TYPE, ADD_GROUP);

         }
         else {
            if (null != request.getParameter(EDIT_GROUP)) {
               request.setAttribute(ACTION_TYPE, EDIT_GROUP);
            }
            else {
               request.setAttribute(ACTION_TYPE, ADD_ITEM);
            }
         }
         reference = new Reference();
      }

      request.setAttribute("groups", referenceManager.getSubgroups());

      return reference;
   }

   protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
      Reference reference = (Reference) command;
      String type = request.getParameter(ACTION_TYPE);
      log.debug("onBindAndValidate:: type=" + type);
      //log.debug("onBindAndValidate:: typetest=" + typetest);

      boolean isNew = (reference.getId() == null);
      if (isNew) {
         //new Item is Added
         if (type.equals(ADD_ITEM) || type.equals(EDIT_ITEM)) {
            if (checkIfGroupOrItemExists(reference, ITEM)) {
               errors.rejectValue("itemName", null, null, "Item name with this group already exists!");
            }
         }
         else {
            //new Group is added or existing group name is updated
            if (type.equals(ReferenceFormController.EDIT_GROUP) || type.equals(ReferenceFormController.ADD_GROUP)) {
               if (checkIfGroupOrItemExists(reference, GROUP)) {
                  request.setAttribute(ACTION_TYPE, type);
                  errors.rejectValue("subGroupName", null, null, "Group Name already exists!");

               }
            }
         }
      }
   }

   public ModelAndView onSubmit(HttpServletRequest request,
                                HttpServletResponse response,
                                Object command,
                                BindException errors) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("entering 'onSubmit' method...");
      }

      Reference reference = (Reference) command;
      boolean isNew = (reference.getId() == null);
      boolean isReferenced = false;
      Locale locale = request.getLocale();
      if (request.getParameter("delete") != null) {
         try {
            
            if(referenceManager.isReferenced(reference.getId().toString())){
               isReferenced = true;
            }
            else
               referenceManager.removeReference(reference.getId().toString());
         }
         catch (HibernateException e) {
            isReferenced = true;
         }
         if(isReferenced){
            String[] codes = { "Can not delete reference, since other objects reference it" };
            request.setAttribute("errorMessages", codes);
            return new ModelAndView("referenceForm", "reference", reference);
         }
         saveMessage(request, getText("reference.deleted", locale));
      }
      else {
         if (isNew && null != request.getParameter("dropDownGroupName")) {
            List<Reference> res = referenceManager.getReferencesByParam(request
                  .getParameter("dropDownGroupName"));
            for (Reference ref : res) {
               ref.setSubGroupName(reference.getSubGroupName());
               referenceManager.saveReference(ref);
            }
         }
         else
            referenceManager.saveReference(reference);
         String key = (isNew) ? "reference.added" : "reference.updated";
         saveMessage(request, getText(key, locale));
      }
      return new ModelAndView(getSuccessView());
   }

   private boolean checkIfGroupOrItemExists(Reference reference, String type) {
      List<Reference> items;
      //check if group exists
      if (type.equals(ReferenceFormController.GROUP)) {
         items = referenceManager.getRefernceByGroup(reference.getSubGroupName());
      }
      else {
         //check if item with this group already exists
         Reference template = new Reference();
         template.setSubGroupName(reference.getSubGroupName());
         template.setItemName(reference.getItemName());
         items = referenceManager.getReferencesByTemplate(template);
      }
      return items != null && items.size() > 0;
   }
}
