// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import java.util.List;

public class DefaultWeek {

   private List<DefaultWeekDay> weekDays;

   private Reference region;

   private boolean isSet;

   public Reference getRegion() {
      return region;
   }

   public void setRegion(Reference region) {
      this.region = region;
   }

   public List<DefaultWeekDay> getWeekDays() {
      return weekDays;
   }

   public void setWeekDays(List<DefaultWeekDay> weekDays) {
      this.weekDays = weekDays;
   }

   public boolean isSet() {
      return isSet;
   }

   public void setSet(boolean isSet) {
      this.isSet = isSet;
   }

}
