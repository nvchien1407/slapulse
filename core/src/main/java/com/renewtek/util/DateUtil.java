//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import com.renewtek.Constants;

/**
 * Date Utility Class
 * This is used to convert Strings to Dates and Timestamps
 * <p/>
 * <p>
 * <a href="DateUtil.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 *         to correct time pattern. Minutes should be mm not MM
 *         (MM is month).
 * @version $Revision: 1.7 $ $Date: 2005/05/04 04:57:41 $
 */
public class DateUtil {

   //~ Static fields/initializers =============================================

   private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

   private static String defaultDatePattern = null;
   private static String timePattern = "HH:mm";

   //~ Methods ================================================================

   /**
    * Return default datePattern (dd/MM/yyyy)
    *
    * @return a string representing the date pattern on the UI
    */
   public static synchronized String getDatePattern() {
      Locale locale = LocaleContextHolder.getLocale();
      try {
         defaultDatePattern = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).getString("date.format");
      }
      catch (MissingResourceException mse) {
         defaultDatePattern = "dd/MM/yyyy";
      }

      return defaultDatePattern;
   }

   /**
    * This method attempts to convert an Oracle-formatted date
    * in the form dd-MMM-yyyy to mm/dd/yyyy.
    *
    * @param aDate date from database as a string
    * @return formatted string for the ui
    */
   public static String getDate(Date aDate) {
      SimpleDateFormat df;
      String returnValue = "";

      if (aDate != null) {
         df = new SimpleDateFormat(getDatePattern());
         returnValue = df.format(aDate);
      }

      return (returnValue);
   }

   /**
    * This method generates a string representation of a date/time
    * in the format you specify on input
    *
    * @param aMask   the date pattern the string is in
    * @param strDate a string representation of a date
    * @return a converted Date object
    * @throws ParseException
    * @see java.text.SimpleDateFormat
    */
   public static Date convertStringToDate(String aMask, String strDate) throws ParseException {
      Date date;
      SimpleDateFormat df = new SimpleDateFormat(aMask);

      if (log.isDebugEnabled()) {
         log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
      }

      try {
         date = df.parse(strDate);
      }
      catch (ParseException pe) {
         //log.error("ParseException: " + pe);
         throw new ParseException(pe.getMessage(), pe.getErrorOffset());
      }

      return (date);
   }

   /**
    * This method returns the current date time in the format:
    * dd/MM/yyyy HH:MM a
    *
    * @param theTime the current time
    * @return the current date/time
    */
   public static String getTimeNow(Date theTime) {
      return getDateTime(timePattern, theTime);
   }

   /**
    * This method returns the current date in the format: MM/dd/yyyy
    *
    * @return the current date
    * @throws ParseException
    */
   public static Calendar getToday() throws ParseException {
      Date today = new Date();
      SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

      // This seems like quite a hack (date -> string -> date),
      // but it works ;-)
      String todayAsString = df.format(today);
      Calendar cal = new GregorianCalendar();
      cal.setTime(convertStringToDate(todayAsString));

      return cal;
   }

   /**
    * This method generates a string representation of a date's date/time
    * in the format you specify on input
    *
    * @param aMask the date pattern the string is in
    * @param aDate a date object
    * @return a formatted string representation of the date
    * @see java.text.SimpleDateFormat
    */
   public static String getDateTime(String aMask, Date aDate) {
      SimpleDateFormat df;
      String returnValue = "";

      if (aDate == null) {
         log.error("aDate is null!");
      }
      else {
         df = new SimpleDateFormat(aMask);
         returnValue = df.format(aDate);
      }

      return (returnValue);
   }

   /**
    * This method generates a string representation of a date based
    * on the System Property 'dateFormat'
    * in the format you specify on input
    *
    * @param aDate A date to convert
    * @return a string representation of the date
    */
   public static String convertDateToString(Date aDate) {
      return getDateTime(getDatePattern(), aDate);
   }

   /**
    * This method converts a String to a date using the datePattern
    *
    * @param strDate the date to convert (in format MM/dd/yyyy)
    * @return a date object
    * @throws ParseException
    */
   public static Date convertStringToDate(String strDate) throws ParseException {
      Date aDate;

      try {
         if (log.isDebugEnabled()) {
            log.debug("converting date with pattern: " + getDatePattern());
         }

         aDate = convertStringToDate(getDatePattern(), strDate);
      }
      catch (ParseException pe) {
         log.error("Could not convert '" + strDate + "' to a date, throwing exception", pe);
         throw new ParseException(pe.getMessage(), pe.getErrorOffset());

      }

      return aDate;
   }

   public static int getYear(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      return cal.get(Calendar.YEAR);
   }

   public static int getMonth(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      return cal.get(Calendar.MONTH);
   }

   public static int getDateInt(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      return cal.get(Calendar.DAY_OF_MONTH);
   }

   public static int getHours(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      return cal.get(Calendar.HOUR_OF_DAY);
   }

   public static int getMinutes(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      return cal.get(Calendar.MINUTE);
   }

   public static boolean isBefore(Date dateA, Date dateB) {
      Calendar calendarA = Calendar.getInstance();
      Calendar calendarB = Calendar.getInstance();
      calendarA.setTime(dateA);
      calendarB.setTime(dateB);
      return calendarA.before(calendarB);
   }

   public static boolean isAfter(Date dateA, Date dateB) {
      Calendar calendarA = Calendar.getInstance();
      Calendar calendarB = Calendar.getInstance();
      calendarA.setTime(dateA);
      calendarB.setTime(dateB);
      return calendarA.after(calendarB);
   }

   public static long getMillis(int days, int hours, int minutes) {
      long total = (long) days * 1000 * 60 * 60 * 24;
      total += (long) hours * 1000 * 60 * 60;
      total += (long) minutes * 1000 * 60;
      return total;
   }

   public static long getMillis(int hours, int minutes) {
      return getMillis(0, hours, minutes);
   }

   public static Date convertLocalTimeWithSpecifiedTimeZoneToServerTime(Date date, String timezone) {
      Calendar localTime = new GregorianCalendar();
      localTime.setTime(date);

      return convertLocalTimeWithSpecifiedTimeZoneToServerTime(localTime, timezone);
   }
   
   //fix DE2522
   public static Date convertLocalTimeWithSpecifiedTimeZoneToServerTime4WorkHourRange(Date date, String timezone) {
      Calendar now = Calendar.getInstance();
      
      Calendar localTime = new GregorianCalendar();
      localTime.setTime(date);
      localTime.set(Calendar.YEAR, now.get(Calendar.YEAR));
      localTime.set(Calendar.MONTH, now.get(Calendar.MONTH));
      localTime.set(Calendar.DATE, now.get(Calendar.DATE));
      
      return convertLocalTimeWithSpecifiedTimeZoneToServerTime(localTime, timezone);
   }

   public static Date convertLocalTimeWithSpecifiedTimeZoneToServerTime(Calendar localTime, String timezone) {
      if (StringUtils.isEmpty(timezone)) {
         return localTime.getTime();
      }
      Calendar serverTime = new GregorianCalendar();
      serverTime.setTimeZone(TimeZone.getTimeZone(timezone));
      serverTime.set(Calendar.YEAR, localTime.get(Calendar.YEAR));
      serverTime.set(Calendar.MONTH, localTime.get(Calendar.MONTH));
      serverTime.set(Calendar.DATE, localTime.get(Calendar.DATE));
      serverTime.set(Calendar.HOUR_OF_DAY,  localTime.get(Calendar.HOUR_OF_DAY));
      serverTime.set(Calendar.MINUTE, localTime.get(Calendar.MINUTE));

      return serverTime.getTime();
   }

   public static Calendar constructEndOfDateObjectForSpecificLocation(String timezone) {
      Calendar endOfDateInCurrentLocation = new GregorianCalendar();
      if (timezone != null) {
         endOfDateInCurrentLocation.setTimeZone(TimeZone.getTimeZone(timezone));
      }

      endOfDateInCurrentLocation.set(Calendar.HOUR_OF_DAY,  23);
      endOfDateInCurrentLocation.set(Calendar.MINUTE, 59);

      Calendar serverTimeAccordingToEndOfDateInLocal = Calendar.getInstance();
      serverTimeAccordingToEndOfDateInLocal.setTime(endOfDateInCurrentLocation.getTime());
      return serverTimeAccordingToEndOfDateInLocal;
   }

}
