// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2007 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.renewtek.model.CalendarDate;
import com.renewtek.model.DefaultWeekDay;
import com.renewtek.model.WorkHourRange;
import com.renewtek.service.SLACalendar;

/**
 * <code>SLACalendarImpl</code> is a concrete subclass of {@link SLACalendar}
 * which provides additional functionality to support business day operations in
 * conjunction with the SLA Pulse services.
 * <p/>
 * This implementation handles business and non-business day operations
 * including non-business day, start-of-business and end-of-business time
 * retrieval.
 * 
 * @author juliancowan
 */
public class SLACalendarImpl extends SLACalendar {

   /**
     *
     */
   private static final long serialVersionUID = -1919837987167196445L;

   /**
    * Default Constructor
    */
   public SLACalendarImpl() {
      this(TimeZone.getDefault(), Locale.getDefault());
   }

   public SLACalendarImpl(TimeZone zone, Locale locale) {
      super(zone, locale);
      computeSLAFields();
   }

   /**
     *
     */
   public boolean isNonBusinessDay() {
      computeSLAFields();

      return nonBusinessDay;
   }

   /**
     *
     */
   public String getCalendarName() {
      return calendarName;
   }

   /**
     *
     */
   public void setCalendarName(String calendarName) {
      this.calendarName = calendarName;
   }

   /**
     *
     */
   public void setDefaultWeek(List<DefaultWeekDay> defaultWeek) {
      defaultWeekDays.clear();
      //for (Iterator it = defaultWeek.iterator(); it.hasNext();) {
      for(DefaultWeekDay defaultWeekDay : defaultWeek) {
         //DefaultWeekDay defaultWeekDay = (DefaultWeekDay) it.next();
         defaultWeekDays.put(defaultWeekDay.getDay(), defaultWeekDay);
      }
   }

   /**
     *
     */
   public void setCalendarDays(List<CalendarDate> calendarDays) {
      days.clear();
      //for (Iterator it = calendarDays.iterator(); it.hasNext();) {
      for(CalendarDate calendarDate : calendarDays) {
         //CalendarDate calendarDate = (CalendarDate) it.next();
         days.put(dateFormat.format(calendarDate.getDateInfo()), calendarDate);
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.renewtek.service.impl.SLACalendar#get(int)
    */
   public int get(int field) {
      int fieldValue = 0;

      switch (field) {
         case SLACalendar.START_OF_BUSINESS_HOUR:
            // Fall Through
         case SLACalendar.START_OF_BUSINESS_MINUTE:
            // Fall Through
         case SLACalendar.END_OF_BUSINESS_HOUR:
            // Fall Through
         case SLACalendar.END_OF_BUSINESS_MINUTE:
            fieldValue = getSLAFieldValue(field);
            break;
         default:
            // NON-SLA Field
            fieldValue = super.get(field);
            break;
      }

      return fieldValue;
   }

   public void add(int field, int amount) {
      if (amount == 0)
         return;

      switch (field) {
         case SLACalendar.BUSINESS_DATE:
            addSLAFieldValue(field, amount);
            break;
         default:
            // NON-SLA Field
            super.add(field, amount);
            break;
      }
   }

   private void addSLAFieldValue(int field, int amount) {

      switch (field) {
         case SLACalendar.BUSINESS_DATE:
            int sign = (amount > 0) ? 1 : -1;

            // Start on a business day
            while (isNonBusinessDay()) {
               super.add(SLACalendar.DATE, sign);
            }

            // Add (or Subtract) a business day
            for (int i = 0; i < Math.abs(amount); i++) {
               super.add(SLACalendar.DATE, sign);
               while (isNonBusinessDay()) {
                  super.add(SLACalendar.DATE, sign);
               }
            }
            break;
         default:
            // NON-SLA Field
            break;
      }
   }

   /**
    * Returns the value of the specified field
    * 
    * @param field
    */
   private int getSLAFieldValue(int field) {
      computeSLAFields();

      int fieldValue = 0;
      switch (field) {
         case SLACalendar.START_OF_BUSINESS_HOUR:
            fieldValue = this.startOfBusinessHour;
            break;
         case SLACalendar.START_OF_BUSINESS_MINUTE:
            fieldValue = this.startOfBusinessMinute;
            break;
         case SLACalendar.END_OF_BUSINESS_HOUR:
            fieldValue = this.endOfBusinessHour;
            break;
         case SLACalendar.END_OF_BUSINESS_MINUTE:
            fieldValue = this.endOfBusinessMinute;
            break;
         default:
            // Do Nothing
            break;
      }
      return fieldValue;
   }

   /**
    * Sets business specific field values for the current day
    */
   private void computeSLAFields() {
      complete();

      if (!areSLAFieldsSet)
         setSLACalendarFields(this.getTime());
   }

   /**
    * Sets this Calendar's business fields using the given date.
    * 
    * @param time
    *           this calender's date.
    */
   private void setSLACalendarFields(Date time) {
      String dayId = dateFormat.format(time);
      Object dayObject = days.get(dayId);

      if (null == dayObject) {
         setDefaultWeekDay(time);
      }
      else {
         CalendarDate calendarDate = (CalendarDate) dayObject;
         boolean nonBusinessDay = calendarDate.getNonWorkingDay();
         setSLACalendarFields(calendarDate.getWorkHourRange(), nonBusinessDay);
      }
      areSLAFieldsSet = true;
   }

   /**
    * Sets this Calendar's business fields using default weekday values.
    * Retrieves the relevant default week day based on the date given.
    * 
    * @param time
    *           this calender's date.
    */
   private void setDefaultWeekDay(Date time) {
      String weekDayId = this.weekDays[this.get(Calendar.DAY_OF_WEEK) - 1];
      Object weekdayObject = defaultWeekDays.get(weekDayId);

      if (null == weekdayObject) {
         setSLACalendarFields(null, false);
      }
      else {
         DefaultWeekDay defaultWeekDay = (DefaultWeekDay) weekdayObject;
         boolean nonBusinessDay = defaultWeekDay.getNonWorkingDay();
         setSLACalendarFields(defaultWeekDay.getWorkHourRange(), nonBusinessDay);
      }
   }

   /**
    * Sets this Calendar's business fields using values from WorkHourRange.
    * 
    * @param workHourRange
    *           Start and End times.
    * @param nonBusinessDay
    *           Non-Business day indicator.
    */
   private void setSLACalendarFields(WorkHourRange workHourRange, boolean nonBusinessDay) {

      this.nonBusinessDay = nonBusinessDay;
      if (workHourRange != null) {
         this.startOfBusinessHour = Integer.parseInt(workHourRange.getStartHour());
         this.startOfBusinessMinute = Integer.parseInt(workHourRange.getStartMinute());
         this.endOfBusinessHour = Integer.parseInt(workHourRange.getEndHour());
         this.endOfBusinessMinute = Integer.parseInt(workHourRange.getEndMinute());
      }
      else {
         this.startOfBusinessHour = 0;
         this.startOfBusinessMinute = 0;
         this.endOfBusinessHour = 0;
         this.endOfBusinessMinute = 0;
      }
   }

   public int[] getWorkingHourRange() {
      computeSLAFields();
      int[] workingHourRange = new int[4];
      workingHourRange[0] = this.startOfBusinessHour;
      workingHourRange[1] = this.startOfBusinessMinute;
      workingHourRange[2] = this.endOfBusinessHour;
      workingHourRange[3] = this.endOfBusinessMinute;

      return workingHourRange;
   }
}
