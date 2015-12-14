package com.renewtek.webapp.controller;

import com.renewtek.model.ServiceLevelAgreement;
import com.renewtek.service.ServiceLevelAgreementManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jerryshea
 * Basic REST controller for ServiceLevelAgreement entity.
 *
 * Examples:
 *  - http://localhost:8080/slapulse/rest/slas
 *  - http://localhost:8080/slapulse/rest/slas/1
 *  - http://localhost:8080/slapulse/rest/slas/1?expands=businessProcesses
 */
@Controller
@RequestMapping(value = "/slas")
public class SlaRestController extends GenericRestController<ServiceLevelAgreement> {
   @Autowired
   public SlaRestController(ServiceLevelAgreementManager manager) {
      super(manager);
   }
}
