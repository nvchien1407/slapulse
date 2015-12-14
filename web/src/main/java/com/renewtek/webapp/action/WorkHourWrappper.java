//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.text.SimpleDateFormat;

import org.displaytag.decorator.TableDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.renewtek.model.WorkHourRange;

public class WorkHourWrappper extends TableDecorator {

   private static final Logger LOG = LoggerFactory.getLogger(WorkHourWrappper.class);

   private WorkHourRange range = null;
   private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

   public WorkHourWrappper() {
   }

   public String getFromTime() {
      if (super.getCurrentRowObject() != null)
         range = (WorkHourRange) super.getCurrentRowObject();
      else
         return "-";
      String workHours = this.dateFormat.format(range.getFromTime());
      if (LOG.isDebugEnabled()) {
         LOG.debug("workHours: " + workHours);
      }
      return workHours;
   }

   public String getToTime() {
      if (super.getCurrentRowObject() != null)
         range = (WorkHourRange) super.getCurrentRowObject();
      else
         return "-";
      String workHours = this.dateFormat.format(range.getToTime());
      if (LOG.isDebugEnabled()) {
         LOG.debug("workHours: " + workHours);
      }
      return workHours;
   }

}
