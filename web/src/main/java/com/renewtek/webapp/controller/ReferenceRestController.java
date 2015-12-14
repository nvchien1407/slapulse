package com.renewtek.webapp.controller;

import com.renewtek.model.Reference;
import com.renewtek.service.ReferenceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jerryshea
 * Basic REST controller for DayModel entity.
 */
@Controller
@RequestMapping(value = "/references")
public class ReferenceRestController extends GenericRestController<Reference> {
   @Autowired
   public ReferenceRestController(ReferenceManager manager) {
      super(manager);
   }
}
