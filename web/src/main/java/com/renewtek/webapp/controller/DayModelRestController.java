package com.renewtek.webapp.controller;

import com.renewtek.model.DayModel;
import com.renewtek.service.DayModelManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jerryshea
 * Basic REST controller for DayModel entity.
 */
@Controller
@RequestMapping(value = "/dayModels")
public class DayModelRestController extends GenericRestController<DayModel> {
   @Autowired
   public DayModelRestController(DayModelManager manager) {
      super(manager);
   }
}
