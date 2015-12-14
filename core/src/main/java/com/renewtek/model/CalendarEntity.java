// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import java.util.Set;

@Deprecated
public class CalendarEntity {

   private Reference name;

   private Set<DefaultWeekDay> weekDays;

   private DayModel defaultDay;

   public DayModel getDefaultDay() {
      return defaultDay;
   }

   public void setDefaultDay(DayModel defaultDay) {
      this.defaultDay = defaultDay;
   }

   public Reference getName() {
      return name;
   }

   public void setName(Reference name) {
      this.name = name;
   }

   public Set<DefaultWeekDay> getWeekDays() {
      return weekDays;
   }

   public void setWeekDays(Set<DefaultWeekDay> weekDays) {
      this.weekDays = weekDays;
   }
}
