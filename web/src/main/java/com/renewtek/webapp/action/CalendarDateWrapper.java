//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.displaytag.decorator.TableDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.renewtek.model.CalendarDate;
import com.renewtek.model.DefaultWeekDay;
import com.renewtek.model.WorkHourRange;

public class CalendarDateWrapper extends TableDecorator {

   protected final transient Logger log = LoggerFactory.getLogger(getClass());

   public CalendarDateWrapper() {
   }

   public String getLink() {
      Object temp = super.getCurrentRowObject();
      if (temp != null) {
         if (temp instanceof CalendarDate) {
            CalendarDate cd = (CalendarDate) temp;
            return "<a href=\"editCalendarDate.html?date=" + cd.getDateInfoString() + "&region="
                  + cd.getRegion().getId() + "\">" + cd.getName() + "</a>";
         }
         else if (temp instanceof DefaultWeekDay) {
            DefaultWeekDay cd = (DefaultWeekDay) temp;
            String url = null;
            if (cd.getId() != null)
               url = "<a href=\"editDefaultWeek.html?id=" + cd.getId() + "\">";
            else
               url = "<a href=\"editDefaultWeek.html?day=" + cd.getDay() + "&region=" + cd.getRegion().getId() + "\">";
            return url + cd.getDay() + "</a>";
         }
      }
      return "";
   }

   public String getWorkHours() {
      Object temp = super.getCurrentRowObject();
      if (temp != null) {
         if (temp instanceof CalendarDate) {
            CalendarDate cd = (CalendarDate) temp;
            List<WorkHourRange> workHourRanges = new ArrayList<WorkHourRange>(cd.getWorkHourRanges());
            if (workHourRanges.size() > 1) {
               return "Multiple Ranges";
            }
            else if (workHourRanges.size() == 1) {

               WorkHourRange range = workHourRanges.get(0);
               return range.getStartHour() + ":" + range.getStartMinute() + " - " + range.getEndHour() + ":"
                     + range.getEndMinute();
            }
            else {
               return "";
            }
         }
      }
      return "";
   }
}
