// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class CalendarDays {

   private Calendar calendar;

   private Reference region;

   private List<CalendarDate> exceptionalDays;

   protected String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
         "October", "November", "December" };

   protected String[] weekdaysEN = { "M", "T", "W", "T", "F", "S", "S" };

   private static SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("yyyy");

   private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

   private List<DefaultWeekDay> defaultWeekDays;

   private List<CalendarDate> days;

   public CalendarDays(Calendar cal) {
      calendar = cal;
   }

   public Calendar getCalendar() {
      return calendar;
   }

   public void setCalendar(Calendar calendar) {
      this.calendar = calendar;
   }

   public void setDays(List<CalendarDate> days) {
      this.days = days;
   }

   public List<CalendarDate> getDays() {
      return days;
   }

   public List<String> getWeekDays() {
      ArrayList<String> list = new ArrayList<String>();
      list.addAll(Arrays.asList(weekdaysEN).subList(0, Calendar.DAY_OF_WEEK));
      return list;
   }

   public String getNextMonth() {
      Calendar cal = (Calendar) calendar.clone();
      cal.add(Calendar.MONTH, 1);
      return this.dateFormat.format(cal.getTime());
   }

   public String getPreviousMonth() {
      Calendar cal = (Calendar) calendar.clone();
      cal.add(Calendar.MONTH, -1);
      return this.dateFormat.format(cal.getTime());
   }

   public String getMonthYear() {
      return months[calendar.get(Calendar.MONTH)] + " " + dateOnlyFormat.format(calendar.getTime());
   }

   public Reference getRegion() {
      return region;
   }

   public void setRegion(Reference region) {
      this.region = region;
   }

   public List<CalendarDate> getExceptionalDays() {
      return exceptionalDays;
   }

   public void setExceptionalDays(List<CalendarDate> exceptionalDays) {
      this.exceptionalDays = exceptionalDays;
   }

   public String getYear() {
      return dateOnlyFormat.format(calendar.getTime());
   }

   public List<DefaultWeekDay> getDefaultWeekDays() {
      return defaultWeekDays;
   }

   public void setDefaultWeekDays(List<DefaultWeekDay> defaultWeekDays) {
      this.defaultWeekDays = defaultWeekDays;
   }

}
