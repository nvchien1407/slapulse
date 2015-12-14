//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.displaytag.decorator.TableDecorator;

import com.renewtek.Constants;
import com.renewtek.model.ServiceLevelAgreement;

public class ServiceLevelAgreementWrapper extends TableDecorator {

   public ServiceLevelAgreementWrapper() {

   }

   public String getDurationType() {
      Object temp = super.getCurrentRowObject();
      if (temp != null) {
         if (temp instanceof ServiceLevelAgreement) {
            return formatDurationType((ServiceLevelAgreement) temp);
         }
      }
      return "";
   }

   private String formatDurationType(ServiceLevelAgreement sla) {
      String durationType = sla.getDurationType();
      if (durationType.equals(Constants.T_FORMULA_TYPE)) {
         Integer days = sla.getTFormulaeDays();
         String daysStr = (days == 1) ? " day" : " days";
         return "Day Based + " + days + daysStr;
      }
      else if (durationType.equals(Constants.DURATION_TYPE)) {
         Integer hours = sla.getDurationHours();
         Integer mins = sla.getDurationMinutes();
         String hoursStr = (hours == 1) ? " hour " : " hours ";
         String minsStr = (mins == 1) ? " minute" : " minutes";
         return "Time Based + " + hours + hoursStr + mins + minsStr;
      }
      else if (durationType.equals(Constants.DEADLINE_TYPE)) {
         Date fixedTimeDeadline = sla.getFixedTimeDeadline();
         SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
         return "Fixed Deadline @ " + sdf.format(fixedTimeDeadline);
      }
      return "";
   }
}
