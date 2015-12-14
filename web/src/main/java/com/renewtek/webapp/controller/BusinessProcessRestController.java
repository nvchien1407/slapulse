package com.renewtek.webapp.controller;

import com.renewtek.model.BusinessProcess;
import com.renewtek.service.BusinessProcessManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author jerryshea
 * Basic REST controller for BusinessProcess entity.
 */
@Controller
@RequestMapping(value = "/bps")
public class BusinessProcessRestController extends GenericRestController<BusinessProcess> {
   @Autowired
   public BusinessProcessRestController(BusinessProcessManager manager) {
      super(manager);
   }
}
