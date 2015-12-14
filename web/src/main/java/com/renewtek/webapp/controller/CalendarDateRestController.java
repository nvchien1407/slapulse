package com.renewtek.webapp.controller;

import com.renewtek.model.CalendarDate;
import com.renewtek.model.ServiceLevelAgreement;
import com.renewtek.service.CalendarDateManager;
import com.renewtek.service.ServiceLevelAgreementManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jerryshea
 * Basic REST controller for CalendarDate entity.
 */
@Controller
@RequestMapping(value = "/calendarDates")
public class CalendarDateRestController extends GenericRestController<CalendarDate> {
   @Autowired
   public CalendarDateRestController(CalendarDateManager manager) {
      super(manager);
   }
}
