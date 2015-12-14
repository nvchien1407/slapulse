//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT©2007 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.renewtek.Constants;
import com.renewtek.model.CalendarDate;
import com.renewtek.model.DefaultWeekDay;

public abstract class SLACalendar extends GregorianCalendar {

   /**
    * 
    */
   private static final long serialVersionUID = 7100839317326854818L;

   /**
    * Field identifier for <code>get</code> indicating the starting hour
    * of the current business day.
    * <p>
    * The value <code>0</code> is returned for Non-Business days.
    */
   public static final int START_OF_BUSINESS_HOUR = 1001;

   /**
    * Field identifier for <code>get</code> indicating the starting minute
    * of the current business day.
    * <p>
    * The value <code>0</code> is returned for Non-Business days.
    */
   public static final int START_OF_BUSINESS_MINUTE = 1002;

   /**
    * Field identifier for <code>get</code> indicating the ending hour
    * of the current business day.
    * <p>
    * The value <code>0</code> is returned for Non-Business days.
    */
   public static final int END_OF_BUSINESS_HOUR = 1003;

   /**
    * Field identifier for <code>get</code> indicating the ending minute
    * of the current business day.
    * <p>
    * The value <code>0</code> is returned for Non-Business days.
    */
   public static final int END_OF_BUSINESS_MINUTE = 1004;

   /**
    * Field identifier for <code>add</code> indicating the number of
    * of the business days to add.
    */
   public static final int BUSINESS_DATE = 1005;

   /**
    * Internal indicator to trigger for computing SLA field values.
    */
   protected boolean areSLAFieldsSet = false;

   /**
    * Container for default weekday data
    */
   protected HashMap<String, DefaultWeekDay> defaultWeekDays = new HashMap<String, DefaultWeekDay>();

   /**
    * Container for configured calendar days
    */
   protected HashMap<String, CalendarDate> days = new HashMap<String, CalendarDate>();

   /**
    * Internal indicator to trigger for computing SLA field values.
    */
   protected SimpleDateFormat dateFormat;

   /**
    * Internal property for the starting hour of the current business day.
    * <p>
    * The value <code>0</code> is returned for Non-Business days.
    */
   protected int startOfBusinessHour = 0;

   /**
    * Internal property for the starting minute of the current business day.
    * <p>
    * The value <code>0</code> is returned for Non-Business days.
    */
   protected int startOfBusinessMinute = 0;

   /**
    * Internal property for the ending hour of the current business day.
    * <p>
    * The value <code>0</code> is returned for Non-Business days.
    */
   protected int endOfBusinessHour = 0;

   /**
    * Internal property for the ending minute of the current business day.
    * <p>
    * The value <code>0</code> is returned for Non-Business days.
    */
   protected int endOfBusinessMinute = 0;

   /**
    * Indicator for Non-Business days It has default value of <code>true</code>.
    */
   protected boolean nonBusinessDay = true;

   /**
    * Field name for the calendar name.
    */
   protected String calendarName;

   /**
    * Array of Human readable week day names.
    */
   protected String[] weekDays = { "Sunday", "Monday", "Tuesday", "Wednesday",
         "Thursday", "Friday", "Saturday" };

   /**
    * Protected default constructor
    */
   protected SLACalendar(TimeZone zone, Locale locale) {
      super(zone,locale);
      dateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
   }

    /**
    * Overrides java.util.GregorianCalendar.computeFields()
    * Sets trigger to refresh SLA fields.
    */
   protected void computeFields() {
      areSLAFieldsSet = false;
      super.computeFields();
   }

   /**
    * Overrides java.util.GregorianCalendar.computeTime()
    * Sets trigger to refresh SLA fields.
    */
   protected void computeTime() {
      areSLAFieldsSet = false;
      super.computeTime();
   }

   /**
    * Returns true if current date is a Non-business day
    */
   public abstract boolean isNonBusinessDay();

   /**
    * Returns the name of this SLA Calendar
    */
   public abstract String getCalendarName();

   /**
    * Sets the name of this SLA Calendar
    */
   public abstract void setCalendarName(String calendarName);

   /**
    * Populates a Map of default week days for this calendar.
    * 
    * @param defaultWeek
    *            a list of DefaultWeekDay objects.
    */
   public abstract void setDefaultWeek(List<DefaultWeekDay> defaultWeek);

   /**
    * Populates a Map of Calendar days for this calendar.
    * 
    * @param calendarDays
    *            a list of CalendarDate objects.
    */
   public abstract void setCalendarDays(List<CalendarDate> calendarDays);

   /**
    * Returns an array that includes start and end working hours and minutes
    */
   public abstract int[] getWorkingHourRange();
}