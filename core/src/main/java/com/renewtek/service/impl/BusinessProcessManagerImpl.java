// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service.impl;

import com.renewtek.Constants;
import com.renewtek.dao.BusinessProcessDAO;
import com.renewtek.model.*;
import com.renewtek.service.BusinessProcessManager;
import com.renewtek.service.CalendarManager;
import com.renewtek.service.ReferenceManager;
import com.renewtek.service.SLACalendar;
import com.renewtek.util.DateUtil;

import java.util.*;

public class BusinessProcessManagerImpl extends BaseManager implements BusinessProcessManager {

   private CalendarManager calendarManager;

   private ReferenceManager referenceManager;

   protected BusinessProcessDAO getBusinessProcessDAO() {
      return (BusinessProcessDAO) dao;   
   }

   public void setCalendarManager(CalendarManager calendarManager) {
      this.calendarManager = calendarManager;
   }

   public void setReferenceManager(ReferenceManager referenceManager) {
      this.referenceManager = referenceManager;
   }

   public BusinessProcess getBusinessProcessByIds(final Integer nameId, final Integer typeId) {
      List<BusinessProcess> list = getBusinessProcessDAO().getBusinessProcessById(nameId, typeId);
      if (list == null || list.size() == 0) {
         return null;
      }
      return list.get(0);
   }

   /**
    * Set the DAO for communication with the data layer.
    *
    * @param dao
    */
   public void setBusinessProcessDAO(BusinessProcessDAO dao) {
      this.dao = dao;
   }

   public List<BusinessProcess> getBusinessProcesses(final BusinessProcess businessProcess) {
      return getBusinessProcessDAO().getBusinessProcesses(businessProcess);
   }

   /**
    * @see com.renewtek.service.BusinessProcessManager#getUnassignedBusinessProcesses()
    */
   public List<BusinessProcess> getUnassignedBusinessProcesses() {
      return getBusinessProcessDAO().getUnassignedBusinessProcesses();
   }

   /**
    * @see com.renewtek.service.BusinessProcessManager#getBusinessProcess(String
    *      id)
    */
   public BusinessProcess getBusinessProcess(final String id) {
      return getBusinessProcessDAO().getBusinessProcess(new Integer(id));
   }

   /**
    * @see com.renewtek.service.BusinessProcessManager#saveBusinessProcess(BusinessProcess
    *      businessProcess)
    */
   public void saveBusinessProcess(BusinessProcess businessProcess) {
      getBusinessProcessDAO().saveBusinessProcess(businessProcess);
   }

   /**
    * @see com.renewtek.service.BusinessProcessManager#removeBusinessProcess(String
    *      id)
    */
   public void removeBusinessProcess(final String id) {
      getBusinessProcessDAO().removeBusinessProcess(new Integer(id));
   }

   public BusinessProcess getBusinessProcess(final String name, final String type) {
      return getBusinessProcess(name, type, null, null);
   }

   public BusinessProcess getBusinessProcess(final String name, final String type, final String transaction, final String step) {
      List<BusinessProcess> l = getBusinessProcessDAO().getBusinessProcess(name, type, transaction, step);
      if (l == null || l.size() == 0) {
         //START DE2560: Add more detail log
         //log.warn("could not find business process for name: {} type: {}", name, type);
         log.warn("could not find business process for name: " + name + " type: "+ type + " transaction: " + transaction + " step: " + step);
         //END DE2560
         return null;
      }
      return l.get(0);
   }

   /**
    * Calculates a deadline
    *
    * @param processName , processType, startTime, sleepTime
    * @return deadline
    */
   public Date getSLADeadline(final String processName, final String processType, final Date startTime, long sleepTime) {
      return getSLADeadline(processName, processType, null, null, startTime, sleepTime);
   }

   public Date getSLADeadline(String processName, String processType, String transaction, String step, Date startTime, long sleepTime) {
      Date deadLine = new Date();
      BusinessProcess process = getBusinessProcess(processName, processType, transaction, step);
      ServiceLevelAgreement sla = process.getServiceLevelAgreement();
      if (sla.getDurationType().equals(Constants.T_FORMULA_TYPE)) {
         deadLine = calculateDeadLineTFormula(startTime, sla, sleepTime);
      } else if (sla.getDurationType().equals(Constants.DURATION_TYPE)) {
         deadLine = calculateDeadLineDuration(startTime, sla, sleepTime);
      } else if (sla.getDurationType().equals(Constants.DEADLINE_TYPE)) {
         deadLine = calculateDeadLineFixedTime(startTime, sla, sleepTime);
      }
      Calendar c = this.getCalendar(deadLine);
      c.set(Calendar.SECOND, 0);
      c.set(Calendar.MILLISECOND, 0);
      return c.getTime();
   }

   private Date calculateDeadLineFixedTime(Date startTime, ServiceLevelAgreement sla, long sleepTime) {
      Reference calendarName = sla.getCalendar();
      String currentLocation = calendarName.getTimezone();
      Date fixedDeadline = DateUtil.convertLocalTimeWithSpecifiedTimeZoneToServerTime(sla.getFixedTimeDeadline(),
              currentLocation);
      Date deadlineThreshold = DateUtil.convertLocalTimeWithSpecifiedTimeZoneToServerTime(sla.getFixedTimeThreshold(),
              currentLocation);

      int daysToRoll = sla.getFixedTimeDaysToRoll();

      SLACalendar slaCalendar = this.calendarManager.getSLACalendar(calendarName);
      slaCalendar.setTimeInMillis(startTime.getTime() + sleepTime);

      while (slaCalendar.isNonBusinessDay()) {
         slaCalendar.add(SLACalendar.DATE, 1);
         slaCalendar.set(SLACalendar.HOUR_OF_DAY, 0);
         slaCalendar.set(SLACalendar.MINUTE, 0);
      }

      long currentMillis = DateUtil.getMillis(slaCalendar.get(SLACalendar.HOUR_OF_DAY), slaCalendar
              .get(SLACalendar.MINUTE));
      long thresholdMillis = DateUtil.getMillis(DateUtil.getHours(deadlineThreshold), DateUtil
              .getMinutes(deadlineThreshold));

      if (currentMillis > thresholdMillis) {
         slaCalendar.add(SLACalendar.BUSINESS_DATE, daysToRoll);
      }

      slaCalendar.set(SLACalendar.HOUR_OF_DAY, DateUtil.getHours(fixedDeadline));
      slaCalendar.set(SLACalendar.MINUTE, DateUtil.getMinutes(fixedDeadline));

      return slaCalendar.getTime();
   }

   private Date addSleepTime(Date date, ServiceLevelAgreement sla, long sleeptime) {
      if (sleeptime == 0) {
         return date;
      }
      Calendar cal = Calendar.getInstance();
      long correl = date.getTime() + sleeptime;
      cal.setTimeInMillis(correl);
      Date correctedDate = cal.getTime();
      Reference calendarName = sla.getCalendar();
      CalendarDays month = this.calendarManager.getCalendarDaysByMonth(correctedDate, null, calendarName);
      CalendarDate cd = getCalendarDate(correctedDate, month);
      if (cd.getNonWorkingDay() != null && cd.getNonWorkingDay()) {
         cd = findNextWorkDate(date, calendarName);
         cal.setTime(cd.getDateInfo());
      }

      if (sla.getDurationType().equals(Constants.T_FORMULA_TYPE)) {
         Date startTime = this.getStartOfBusinessDay(cd);// true
         Date startDate = this.setDateAndTime(cal.getTime(), startTime);
         if (cal.getTime().getTime() > startDate.getTime()) {
            Date endTime = this.getEndOfBusinessDay(cd);// false
            Date endDate = this.setDateAndTime(cal.getTime(), endTime);
            if (cal.getTime().getTime() <= endDate.getTime()
                    || sla.getDeadlineType().equals(Constants.ACTUAL_TIME_TYPE)) {
               return cal.getTime();
            } else {
               CalendarDate dt = this.findNextWorkDate(cal.getTime(), calendarName);
               startTime = this.getStartOfBusinessDay(dt);// true
               return setDateAndTime(dt.getDateInfo(), startTime);
            }
         } else {
            return startDate;
         }
      } else if (sla.getDurationType().equals(Constants.DURATION_TYPE)) {
         return findBusinessDay(cd, cal.getTime(), sla, true);
      }
      return null;
   }

   public Date setDateAndTime(Date date, Date time) {
      Calendar cal = getCalendar(date);
      cal.clear(Calendar.HOUR_OF_DAY);
      cal.clear(Calendar.MINUTE);
      cal.set(Calendar.HOUR_OF_DAY, DateUtil.getHours(time));
      cal.set(Calendar.MINUTE, DateUtil.getMinutes(time));
      return cal.getTime();
   }

   public Date findNextWorkDate(Date startTime, String calendarName) {
      Reference calendar = referenceManager.getReferenceByItemName(calendarName);
      return this.findNextWorkDate(startTime, calendar).getDateInfo();
   }

   private Date findBusinessDay(CalendarDate date, Date startTime, ServiceLevelAgreement sla, boolean isForward) {
      Reference calendarName = sla.getCalendar();
      Date openTime = getStartOfBusinessDay(date);// true
      Date closeTime = getEndOfBusinessDay(date);// false
      
      //DE2522: nothing
      closeTime = determineCloseTimeAccordingToCutOffTime(closeTime, sla);

      Calendar cal = Calendar.getInstance();
      Calendar cal2 = null;

      if (isForward) {
         cal.setTime(openTime);
         cal2 = (Calendar) cal.clone();
         cal.set(Calendar.HOUR_OF_DAY, DateUtil.getHours(openTime));
         cal.set(Calendar.MINUTE, DateUtil.getMinutes(openTime));
         cal2.set(Calendar.HOUR_OF_DAY, DateUtil.getHours(startTime));
         cal2.set(Calendar.MINUTE, DateUtil.getMinutes(startTime));

         if (cal.getTimeInMillis() > cal2.getTimeInMillis()) {
            // setWorkTime(cal2.getTime(), date, true);
            // modify spanning hours START
            return this.adjustSpanningDayForTimeBasedStartTime(date, openTime, startTime);
            // return openTime;
            // modify spanning hours END
         } else {
            cal.setTime(closeTime);
            if (cal.getTimeInMillis() < cal2.getTimeInMillis()) {
               CalendarDate cd = findNextWorkDate(date.getDateInfo(), calendarName);
               date.setWorkHourRanges(cd.getWorkHourRanges());

               openTime = getStartOfBusinessDay(cd);// true
               return openTime;
            }
         }
         setWorkTime(cal2.getTime(), date, true);
         return cal2.getTime();
      } else {
         cal.setTime(closeTime);
         cal2 = (Calendar) cal.clone();
         cal.set(Calendar.HOUR_OF_DAY, DateUtil.getHours(closeTime));
         cal.set(Calendar.MINUTE, DateUtil.getMinutes(closeTime));
         cal2.set(Calendar.HOUR_OF_DAY, DateUtil.getHours(startTime));
         cal2.set(Calendar.MINUTE, DateUtil.getMinutes(startTime));

         // cal opentime, cal2 startTime
         if (cal.getTimeInMillis() < cal2.getTimeInMillis()) {
            return this.adjustSpanningDayForTimeBasedEndTime(date, closeTime, startTime);
            // return closeTime;
         } else {
            cal.setTime(openTime);
            if (cal.getTimeInMillis() > cal2.getTimeInMillis()) {
               CalendarDate cd = findNextWorkDate(date.getDateInfo(), calendarName, true, false);
               date.setWorkHourRanges(cd.getWorkHourRanges());

               closeTime = getEndOfBusinessDay(cd);// false
               return closeTime;
            }
         }
         setWorkTime(cal2.getTime(), date, false);
         return cal2.getTime();
      }
   }
   
   //DE2522: add new method to calculate ReminderTime from Deadline instead of Cut-off
   private Date findBusinessDay4ReminderTime(CalendarDate date, Date startTime, ServiceLevelAgreement sla, boolean isForward) {
      Reference calendarName = sla.getCalendar();
      Date openTime = getStartOfBusinessDay(date);// true
      Date closeTime = getEndOfBusinessDay(date);// false

      Calendar cal = Calendar.getInstance();
      Calendar cal2 = null;

      if (isForward) {
         cal.setTime(openTime);
         cal2 = (Calendar) cal.clone();
         cal.set(Calendar.HOUR_OF_DAY, DateUtil.getHours(openTime));
         cal.set(Calendar.MINUTE, DateUtil.getMinutes(openTime));
         cal2.set(Calendar.HOUR_OF_DAY, DateUtil.getHours(startTime));
         cal2.set(Calendar.MINUTE, DateUtil.getMinutes(startTime));

         if (cal.getTimeInMillis() > cal2.getTimeInMillis()) {
            // setWorkTime(cal2.getTime(), date, true);
            // modify spanning hours START
            return this.adjustSpanningDayForTimeBasedStartTime(date, openTime, startTime);
            // return openTime;
            // modify spanning hours END
         } else {
            cal.setTime(closeTime);
            if (cal.getTimeInMillis() < cal2.getTimeInMillis()) {
               CalendarDate cd = findNextWorkDate(date.getDateInfo(), calendarName);
               date.setWorkHourRanges(cd.getWorkHourRanges());

               openTime = getStartOfBusinessDay(cd);// true
               return openTime;
            }
         }
         setWorkTime(cal2.getTime(), date, true);
         return cal2.getTime();
      } else {
         cal.setTime(closeTime);
         cal2 = (Calendar) cal.clone();
         cal.set(Calendar.HOUR_OF_DAY, DateUtil.getHours(closeTime));
         cal.set(Calendar.MINUTE, DateUtil.getMinutes(closeTime));
         cal2.set(Calendar.HOUR_OF_DAY, DateUtil.getHours(startTime));
         cal2.set(Calendar.MINUTE, DateUtil.getMinutes(startTime));

         // cal opentime, cal2 startTime
         if (cal.getTimeInMillis() < cal2.getTimeInMillis()) {
            return this.adjustSpanningDayForTimeBasedEndTime(date, closeTime, startTime);
            // return closeTime;
         } else {
            cal.setTime(openTime);
            if (cal.getTimeInMillis() > cal2.getTimeInMillis()) {
               CalendarDate cd = findNextWorkDate(date.getDateInfo(), calendarName, true, false);
               date.setWorkHourRanges(cd.getWorkHourRanges());

               closeTime = getEndOfBusinessDay(cd);// false
               return closeTime;
            }
         }
         setWorkTime(cal2.getTime(), date, false);
         cal2.set(Calendar.DAY_OF_MONTH, DateUtil.getDateInt(startTime));
         return cal2.getTime();
      }
   }

   private Date determineCloseTimeAccordingToCutOffTime(Date closeTime, ServiceLevelAgreement sla) {
      if (sla.getEnableCutOffTime() != null && sla.getEnableCutOffTime()) {
         Calendar calCloseTime = Calendar.getInstance();
         calCloseTime.setTime(closeTime);
         calCloseTime.set(Calendar.HOUR_OF_DAY, DateUtil.getHours(closeTime));
         calCloseTime.set(Calendar.MINUTE, DateUtil.getMinutes(closeTime));

         Calendar calCutOffTime = (Calendar)calCloseTime.clone();
         calCutOffTime.set(Calendar.HOUR_OF_DAY, DateUtil.getHours(sla.getFixedTimeThreshold()));
         calCutOffTime.set(Calendar.MINUTE, DateUtil.getMinutes(sla.getFixedTimeThreshold()));

         if (calCloseTime.getTimeInMillis() > calCutOffTime.getTimeInMillis()) {
            return calCutOffTime.getTime();
         }
      }
      return closeTime;
   }

   private Date adjustSpanningDayForTimeBasedStartTime(CalendarDate cd, Date openTime, Date startTime) {
      Set<WorkHourRange> hours = cd.getWorkHourRanges();
      List<WorkHourRange> list = new ArrayList<WorkHourRange>(hours);
      Collections.sort(list, new WorkHourRangeComparator());
      // Calendar cal = Calendar.getInstance();

      WorkHourRange lastRange = list.get(0);
      Date toDate = lastRange.getToTime();
      Date fromDate = lastRange.getFromTime();
      if (DateUtil.getHours(fromDate) > DateUtil.getHours(toDate)) {
         if ( (DateUtil.getHours(toDate) > DateUtil.getHours(startTime)) || ((DateUtil.getHours(toDate) == DateUtil.getHours(startTime)) && (DateUtil.getMinutes(toDate) > DateUtil.getMinutes(startTime))) ) {         
            return startTime;
         }
      }
      return openTime;
   }

   private Date adjustSpanningDayForTimeBasedEndTime(CalendarDate cd, Date closeTime, Date endTime) {
      Set<WorkHourRange> hours = cd.getWorkHourRanges();
      List<WorkHourRange> list = new ArrayList<WorkHourRange>(hours);
      Collections.sort(list, new WorkHourRangeComparator());
      // Calendar cal = Calendar.getInstance();

      WorkHourRange lastRange = list.get(0);
      Date toDate = lastRange.getToTime();
      Date fromDate = lastRange.getFromTime();
      if (DateUtil.getHours(fromDate) > DateUtil.getHours(toDate)) {
         if (DateUtil.getHours(closeTime) < DateUtil.getHours(endTime)) {
            return endTime;
         }
      }
      return closeTime;
   }

   public CalendarDate getCalendarDate(Date date, CalendarDays month) {
      //for (Iterator it = month.getDays().iterator(); it.hasNext();) {
      List<CalendarDate> days = month.getDays();
      for (CalendarDate cd : days) {
         //CalendarDate cd = (CalendarDate) it.next();

         if (cd != null && dateCmp(date, cd.getDateInfo()) == 0) {
            return cd;
         }
      }
      return null;
   }

   /**
    * This method compares dates without considering time.
    *
    * @param date1
    * @param date2
    * @return the millisecond difference between the two dates provided (without
    *         considering time)
    */
   public int dateCmp(Date date1, Date date2) {

      Calendar cal = Calendar.getInstance();

      cal.set(Calendar.YEAR, DateUtil.getYear(date1));
      cal.set(Calendar.MONTH, DateUtil.getMonth(date1));
      cal.set(Calendar.DATE, DateUtil.getDateInt(date1));
      cal.set(Calendar.HOUR, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);

      Calendar cal2 = Calendar.getInstance();

      cal2.set(Calendar.YEAR, DateUtil.getYear(date2));
      cal2.set(Calendar.MONTH, DateUtil.getMonth(date2));
      cal2.set(Calendar.DATE, DateUtil.getDateInt(date2));
      cal2.set(Calendar.HOUR, 0);
      cal2.set(Calendar.MINUTE, 0);
      cal2.set(Calendar.SECOND, 0);
      cal2.set(Calendar.MILLISECOND, 0);

      return cal.getTime().compareTo(cal2.getTime());
   }

   private Date calculateDeadLineDuration(Date startTime, ServiceLevelAgreement sla, long sleepTime) {
      Calendar cal = getCalendar(startTime);

      int h = sla.getDurationHours();
      int m = sla.getDurationMinutes();
      int durationInMinutes = m + (h * 60);

      Reference calendarName = sla.getCalendar();
      CalendarDays month = this.calendarManager.getCalendarDaysByMonth(cal.getTime(), null, calendarName);
      CalendarDate cd = getCalendarDate(cal.getTime(), month);
      Date dt = null;
      if (cd.getNonWorkingDay() != null && cd.getNonWorkingDay()) { // 261
         cd = findNextWorkDate(cal.getTime(), calendarName);
         dt = this.getStartOfBusinessDay(cd);// true
      } else {
         dt = findBusinessDay(cd, startTime, sla, true);
      }

      int daysToRoll = determineDaysToRoll(startTime, sla, calendarName);
      if (daysToRoll > 0) {
         cd = incrementWorkDays(dt, calendarName, false, true, daysToRoll);
         cal.setTime(cd.getDateInfo());
      } else {
         cal.setTime(dt);
      }

      if (sla.getWorkTime().equals(Constants.TWENTY_FOUR_HOUR_CLOCK_TYPE)) {
         cal.add(Calendar.MINUTE, durationInMinutes);
         month = this.calendarManager.getCalendarDaysByMonth(cal.getTime(), null, calendarName);
         cd = getCalendarDate(cal.getTime(), month);
         if (cd.getNonWorkingDay() != null && cd.getNonWorkingDay()) {// 274
            cd = findNextWorkDate(cal.getTime(), calendarName);
            Date openTime = getStartOfBusinessDay(cd);
            cal.setTime(openTime);
         } else {
            cal.setTime(findBusinessDay(cd, cal.getTime(), sla, true));
         }

      } else {

         boolean dateCalculated = false;
         // modify spanning hours START
         long initialSpanningRange = this.getInitialSpanningRange(cd, startTime);
         if (initialSpanningRange != 0) {
            durationInMinutes -= initialSpanningRange;
            cal.setTime(getStartOfBusinessDay(cd));
         }
         // modify spanning hours END
         while (true) {
            Set<WorkHourRange> workHours = cd.getWorkHourRanges();
            Iterator<WorkHourRange> i = workHours.iterator();
            long rangeTotal = 0;
            while (i.hasNext()) {
               WorkHourRange whr = i.next();
               Date cal1 = getTime(whr.getToTime());
               Date cal2 = getTime(whr.getFromTime());

               // modify spanning hours START
               cal1 = adjustSpanningDayForTimeBasedToTime(cal1, cal2);
               // modify spanning hours END
               rangeTotal += (cal1.getTime() - cal2.getTime()) / 1000 / 60; // in
               // minutes
            }
            // work out day units
            if (durationInMinutes <= rangeTotal) {
               // partial day
               cal.add(Calendar.MINUTE, durationInMinutes);
               dateCalculated = true;
            } else {
               // whole day
               durationInMinutes -= rangeTotal;
               cd = findNextWorkDate(cal.getTime(), calendarName);
               cal.setTime(getStartOfBusinessDay(cd));// true
            }
            if (dateCalculated) {
               break;
            }
         }
      }

      cal.setTime(addSleepTime(cal.getTime(), sla, sleepTime));
      return cal.getTime();
   }

   private long getInitialSpanningRange(CalendarDate cd, Date startTime) {
      Set<WorkHourRange> hours = cd.getWorkHourRanges();
      List<WorkHourRange> list = new ArrayList<WorkHourRange>(hours);
      Collections.sort(list, new WorkHourRangeComparator());

      WorkHourRange lastRange = list.get(0);
      Date fromDate = lastRange.getFromTime();
      Date toDate = lastRange.getToTime();

      int endTime = DateUtil.getHours(toDate);
      int dropInTime = DateUtil.getHours(startTime);

      if (DateUtil.getHours(fromDate) > DateUtil.getHours(toDate)) {
         if (endTime > dropInTime) {
            Date endCal = getTime(toDate);
            Date dropInCal = getTime(startTime);

            return ((endCal.getTime() - dropInCal.getTime()) / 1000 / 60);
         }
      }
      return 0;
   }

   private Date adjustSpanningDayForTimeBasedToTime(Date toCal, Date fromCal) {
      int toTime = DateUtil.getHours(toCal);
      int fromTime = DateUtil.getHours(fromCal);

      if (toTime < fromTime) {
         Calendar cal = Calendar.getInstance();
         cal.setTime(toCal);
         cal.add(Calendar.DATE, 1);
         return cal.getTime();
      }
      return toCal;
   }

   public Date getTime(Date dt) {
      try {
         Calendar cal = Calendar.getInstance();
         cal.clear();
         cal.set(Calendar.HOUR_OF_DAY, DateUtil.getHours(dt));
         cal.set(Calendar.MINUTE, DateUtil.getMinutes(dt));
         return cal.getTime();
      } catch (Exception e) {
         log.error("Exception ", e);
      }
      return null;
   }

   private Date calculateDeadLineTFormula(Date time, ServiceLevelAgreement sla, long sleepTime) {

      boolean include = true;
      if (sla.getIncludeSpecialDays().equals(Constants.NO_TYPE)) {
         include = false;
      }

      Date startTime = getStartTimeForDeadLineTFormula(sla, time, include);

      Calendar calendar = getCalendar(startTime);
      int days = sla.getTFormulaeDays();
      Reference calendarName = sla.getCalendar();
      int daysToRoll = determineDaysToRoll(time, sla, calendarName);
      CalendarDate cd;

      days += daysToRoll;
      cd = incrementWorkDays(calendar.getTime(), calendarName, include, true, days);
      calendar.setTime(cd.getDateInfo());

      if (sla.getDeadlineType().equals(Constants.END_OF_BUSINESS_TYPE)) {
         calendar.setTime(getEndOfBusinessDay(cd));
      } else if (sla.getDeadlineType().equals(Constants.END_OF_DAY_TYPE)) {
         calendar.setTime(cd.getDateInfo());
         Calendar endOfDateObjectInCurrentLocation = DateUtil.constructEndOfDateObjectForSpecificLocation(
                 calendarName.getTimezone());
         calendar.set(Calendar.HOUR_OF_DAY, endOfDateObjectInCurrentLocation.get(Calendar.HOUR_OF_DAY));
         calendar.set(Calendar.MINUTE, endOfDateObjectInCurrentLocation.get(Calendar.MINUTE));
      } else if (sla.getDeadlineType().equals(Constants.SAME_TIME_TYPE)) {
         calendar.setTime(findBusinessDay(cd, startTime, sla, true));
      }

      calendar.setTime(addSleepTime(calendar.getTime(), sla, sleepTime));
      return calendar.getTime();
   }

   private int determineDaysToRoll(Date time, ServiceLevelAgreement sla, Reference calendarName) {
      int daysToRoll = 0;

      if (sla.getEnableCutOffTime()) {
         SLACalendar slaCalendar = this.calendarManager.getSLACalendar(calendarName);
         slaCalendar.setTimeInMillis(time.getTime());

         while (slaCalendar.isNonBusinessDay()) {
            slaCalendar.add(SLACalendar.DATE, 1);
            slaCalendar.set(SLACalendar.HOUR_OF_DAY, 0);
            slaCalendar.set(SLACalendar.MINUTE, 0);
         }

         String currentLocation = calendarName.getTimezone();
         Date deadlineThreshold = DateUtil.convertLocalTimeWithSpecifiedTimeZoneToServerTime(
               sla.getFixedTimeThreshold(), currentLocation);

         long currentMillis = DateUtil.getMillis(slaCalendar.get(SLACalendar.HOUR_OF_DAY),
               slaCalendar.get(SLACalendar.MINUTE));
         long thresholdMillis = DateUtil.getMillis(DateUtil.getHours(deadlineThreshold),
               DateUtil.getMinutes(deadlineThreshold));

         if (currentMillis > thresholdMillis) {
            daysToRoll = sla.getFixedTimeDaysToRoll() - 1;
         }
      }
      return daysToRoll;
   }

   /**
    * Gets the start time for Deadline T-Formula.
    *
    * @param sla     SLA definition.
    * @param time    The original start time which is input by user, this time might
    *                be holiday or special working day.
    * @param include Include the special working days or not.
    * @return Start time.
    */
   private Date getStartTimeForDeadLineTFormula(ServiceLevelAgreement sla, Date time, boolean include) {
      Reference calendarName = sla.getCalendar();

      CalendarDays month = this.calendarManager.getCalendarDaysByMonth(time, null, calendarName);
      CalendarDate cd = getCalendarDate(time, month);
      Date startTime = null;

      if ((cd.getNonWorkingDay() != null && cd.getNonWorkingDay()) || (cd.getId() != null && !include) ||
      // startTime is later than EOB
      (sla.getDeadlineType().equals(Constants.END_OF_BUSINESS_TYPE) && this.getEndOfBusinessDay(cd).compareTo(time) < 0)) // The
      // time is (holiday) or (special working day and exclude special working day).
      {
         cd = findNextWorkDate(time, calendarName, include, true);
         startTime = this.getStartOfBusinessDay(cd);
      } else {
         startTime = findBusinessDay(cd, time, sla, true);
         // Ajust for spanning time
         startTime = this.adjustStartTimeForSpanningDay(sla, cd, startTime, include);
      }

      return startTime;
   }

   private Date adjustStartTimeForSpanningDay(ServiceLevelAgreement sla, CalendarDate cd, Date startTime, boolean include) {
      Set hours = cd.getWorkHourRanges();
      List list = new ArrayList(hours);
      Collections.sort(list, new WorkHourRangeComparator());

      WorkHourRange lastRange = (WorkHourRange) list.get(0);
      Date fromDate = lastRange.getFromTime();
      Date toDate = lastRange.getToTime();

      long fromTimeInMillis = DateUtil.getMillis(DateUtil.getHours(fromDate), DateUtil.getMinutes(fromDate));
      long toTimeInMillis = DateUtil.getMillis(DateUtil.getHours(toDate), DateUtil.getMinutes(toDate));
      long dropInTimeInMillis = DateUtil.getMillis(DateUtil.getHours(startTime), DateUtil.getMinutes(startTime));

      // this is spanning type.
      if (fromTimeInMillis > toTimeInMillis) {
         // maybe there is spanning time from previous day.
         if (dropInTimeInMillis < fromTimeInMillis) {
            //find previous day
            SLACalendar slaCal = this.calendarManager.getSLACalendar(sla.getCalendar());
            slaCal.setTime(startTime);
            slaCal.add(Calendar.DATE, -1);
            // yesterday is working day
            if (!slaCal.isNonBusinessDay()) {
               CalendarDays month = this.calendarManager.getCalendarDaysByMonth(slaCal.getTime(), null, sla.getCalendar());
               CalendarDate previousDate = getCalendarDate(slaCal.getTime(), month);
               // include special day?
               if (previousDate.getId() == null || include) {
                  hours = previousDate.getWorkHourRanges();
                  list = new ArrayList(hours);
                  Collections.sort(list, new WorkHourRangeComparator());

                  lastRange = (WorkHourRange) list.get(list.size() - 1);
                  Date spanningTime = lastRange.getToTime();
                  long spanningTimeInMilis = DateUtil.getMillis(DateUtil.getHours(spanningTime), DateUtil.getMinutes(spanningTime));
                  if (spanningTimeInMilis < dropInTimeInMillis) {
                     return this.getStartOfBusinessDay(cd);
                  }
               }
            } else {
               return this.getStartOfBusinessDay(cd);
            }
         }
      }
      return startTime;
   }

   private void setWorkTime(Date time, CalendarDate cd, boolean startTime) {
      Set<WorkHourRange> hours = cd.getWorkHourRanges();
      List<WorkHourRange> list = new ArrayList<WorkHourRange>(hours);
      Collections.sort(list, new WorkHourRangeComparator());
      if (startTime) {
         WorkHourRange lastRange = list.get(0);
         lastRange.setFromTime(time);

      } else {
         WorkHourRange lastRange = list.get(list.size() - 1);
         lastRange.setToTime(time);
      }
   }

   private Date getStartOfBusinessDay(CalendarDate cd) {
      Set<WorkHourRange> hours = cd.getWorkHourRanges();
      List<WorkHourRange> list = new ArrayList<WorkHourRange>(hours);
      Collections.sort(list, new WorkHourRangeComparator());
      Calendar cal = Calendar.getInstance();
      Date date = null;
      WorkHourRange lastRange = list.get(0);
      date = lastRange.getFromTime();
      mergeDateAndTime(cd, cal, date);
      return cal.getTime();
   }

   private Date getEndOfBusinessDay(CalendarDate cd) {
      Set<WorkHourRange> hours = cd.getWorkHourRanges();
      List<WorkHourRange> list = new ArrayList<WorkHourRange>(hours);
      Collections.sort(list, new WorkHourRangeComparator());
      Calendar cal = Calendar.getInstance();
      Date date = null;
      WorkHourRange lastRange = list.get(list.size() - 1);
      date = lastRange.getToTime();
      mergeDateAndTime(cd, cal, date);

      // modify spanning hours START
      Date fromDate = lastRange.getFromTime();
      if (DateUtil.getHours(fromDate) > DateUtil.getHours(date)) {
         cal.add(Calendar.DATE, 1);
      }
      // modify spanning hours END
      return cal.getTime();
   }

   private void mergeDateAndTime(CalendarDate cd, Calendar cal, Date date) {
      cal.setTime(cd.getDateInfo());
      cal.clear(Calendar.HOUR);
      cal.clear(Calendar.MINUTE);

      cal.set(Calendar.HOUR_OF_DAY, DateUtil.getHours(date));
      cal.set(Calendar.MINUTE, DateUtil.getMinutes(date));
   }

   private CalendarDate findNextWorkDate(Date date, Reference calendarName) {
      return findNextWorkDate(date, calendarName, true, true);
   }

   private CalendarDate findNextWorkDate(Date date, Reference calendarName, boolean includesSpecialDates,
           boolean isForward) {
      CalendarDays month = this.calendarManager.getCalendarDaysByMonth(date, null, calendarName);
      CalendarDate dt = null;
      boolean found = false;
      while (!found) {
         if (isForward) {
            for (CalendarDate calendarDate : month.getDays()) {
               dt = calendarDate;
               if (dt == null) {
                  continue;
               }
               if (this.dateCmp(dt.getDateInfo(), date) > 0
                       && (dt.getNonWorkingDay() == null || !dt.getNonWorkingDay())) {
                  if (dt.getId() != null && !includesSpecialDates) {
                     continue;
                  } else {
                     found = true;
                     break;
                  }
               }
            }
         } else {
            for (int i = month.getDays().size() - 1; i >= 0; i--) {
               dt = month.getDays().get(i);
               if (dt == null) {
                  continue;
               }
               // this.dateCmp(dt.getDateInfo(), date) < 0
               if (this.dateCmp(dt.getDateInfo(), date) < 0
                       && (dt.getNonWorkingDay() == null || !dt.getNonWorkingDay())) {
                  if (dt.getId() != null && !includesSpecialDates) {
                     continue;
                  } else {
                     found = true;
                     break;
                  }
               }
            }
         }
         if (!found) {
            Calendar cal = getCalendar(date);
            cal.add(Calendar.MONTH, isForward ? 1 : -1);
            month = this.calendarManager.getCalendarDaysByMonth(cal.getTime(), null, calendarName);

            // return findNextWorkDate(month, date, calendarName,
            // includesSpecialDates, isForward);
         }
      }
      return dt;
   }

   /**
    * This method allows a date to be incremented by the provided number of
    * working days.
    *
    * @param date                 the base date to increment
    * @param calendarName
    * @param includesSpecialDates
    * @param isForward            true if incrementing forward otherwise it will decrement
    * @param increment            the number of working days to increment the date by
    * @return
    */
   private CalendarDate incrementWorkDays(Date date, Reference calendarName, boolean includesSpecialDates,
           boolean isForward, int increment) {
      CalendarDays month = this.calendarManager.getCalendarDaysByMonth(date, null, calendarName);
      CalendarDate dt = null;
      boolean found = false;
      while (!found) {
         if (isForward) {
            for (CalendarDate calendarDate : month.getDays()) {
               dt = calendarDate;
               if (dt == null) {
                  continue;
               }
               if (this.dateCmp(dt.getDateInfo(), date) > 0
                       && (dt.getNonWorkingDay() == null || !dt.getNonWorkingDay())) {
                  if (dt.getId() != null && !includesSpecialDates) {
                     continue;
                  } else if (increment == 1) {
                     found = true;
                     break;
                  } else {
                     increment--;
                  }
               }
            }
         } else {
            for (int i = month.getDays().size() - 1; i >= 0; i--) {
               dt = month.getDays().get(i);
               if (dt == null) {
                  continue;
               }
               // this.dateCmp(dt.getDateInfo(), date) < 0
               if (this.dateCmp(dt.getDateInfo(), date) < 0
                       && (dt.getNonWorkingDay() == null || !dt.getNonWorkingDay())) {
                  if (dt.getId() != null && !includesSpecialDates) {
                     continue;
                  } else {
                     found = true;
                     break;
                  }
               }
            }
         }
         if (!found) {
            Calendar cal = getCalendar(dt.getDateInfo());
            cal.add(Calendar.MONTH, isForward ? 1 : -1);
            month = this.calendarManager.getCalendarDaysByMonth(cal.getTime(), null, calendarName);

            // return findNextWorkDate(month, date, calendarName,
            // includesSpecialDates, isForward);
         }
      }
      return dt;
   }

   /**
    * SLA Stops when Paused
    *
    * @param processName , processType
    * @return stopWhenPaused
    */
   public boolean getSLAStopWhenPaused(String processName, String processType) {
      BusinessProcess process = getBusinessProcess(processName, processType);
      ServiceLevelAgreement sla = process.getServiceLevelAgreement();
      return getStopWhenPaused(sla);
   }

   private boolean getStopWhenPaused(ServiceLevelAgreement sla) {
      return sla.getStopSlaWhenPaused();
   }

   /**
    * SLA will set Reminder if NotifyDeadlineApproaching is true
    *
    * @param processName , processType
    * @return notifyDeadlineApproaching
    */
   public boolean getSLANotifyDeadlineApproaching(String processName, String processType) {
      return getSLANotifyDeadlineApproaching(processName, processType, null, null);
   }

   public boolean getSLANotifyDeadlineApproaching(String processName, String processType, String transaction, String step) {
      BusinessProcess process = getBusinessProcess(processName, processType, transaction, step);
      if (process == null) {
         return false;
      }
      ServiceLevelAgreement sla = process.getServiceLevelAgreement();
      return getNotifyDeadlineApproaching(sla);
   }

   private boolean getNotifyDeadlineApproaching(ServiceLevelAgreement sla) {
      return sla.getNotifyDeadlineApproaching();
   }

   /**
    * Calculates a reminder time for deadline
    *
    * @param processName , processType, startTime, deadline
    * @return deadline
    */
   public Date getSLAReminderTime(String processName, String processType, Date startTime, Date deadline) {
      return getSLAReminderTime(processName, processType, null, null, startTime, deadline);
   }

   public Date getSLAReminderTime(String processName, String processType, String transaction, String step, Date startTime, Date deadline) {
      Calendar calendar = getCalendar(deadline);

      BusinessProcess process = getBusinessProcess(processName, processType, transaction, step);
      ServiceLevelAgreement sla = process.getServiceLevelAgreement();
      if (getNotifyDeadlineApproaching(sla)) {
         long reminderTime = sla.getNotificationThreshold();
         int seconds = (int) reminderTime / 1000;
         int durationInMinutes = seconds / 60;

         if (startTime != null && sla.getNotifyDeadlineApproachingForwarding() != null
               && sla.getNotifyDeadlineApproachingForwarding().booleanValue()) {
            if (sla.getDurationType().equals(Constants.T_FORMULA_TYPE)) {
               calendar.setTime(calculateForwardReminderTFormula(startTime, deadline, sla, durationInMinutes));
            }
            else if (sla.getDurationType().equals(Constants.DURATION_TYPE)) {
               calendar.setTime(calculateForwardReminderDuration(startTime, sla, durationInMinutes));
            }
            else if (sla.getDurationType().equals(Constants.DEADLINE_TYPE)) {
               calendar.setTime(calculateForwardReminderFixedTime(startTime, sla, durationInMinutes));
            }
         } else {
            // calendar.add(Calendar.SECOND, -seconds);
            if (sla.getDurationType().equals(Constants.DEADLINE_TYPE)) {
               // For Deadline Types
               calendar.add(Calendar.MINUTE, (0 - durationInMinutes));
            } else {
               // Ensure the reminderTime falls on a workday.
               calendar.setTime(calculateReminderTime(deadline, sla, durationInMinutes));
            }
         }
      }

      return calendar.getTime();
   }

   private long getSpanningRangeForStartTime(ServiceLevelAgreement sla, CalendarDate cd, Date startTime, boolean include) {
      // Couldn't use getInitialSpanningRange, because it didn't cover the special days scenario. 
      // Refer to testForwardReminder21 in SLAOperationForwardReminderDayBasedEOBTest.java
      Set hours = cd.getWorkHourRanges();
      List list = new ArrayList(hours);
      Collections.sort(list, new WorkHourRangeComparator());

      WorkHourRange lastRange = (WorkHourRange) list.get(0);
      Date fromDate = lastRange.getFromTime();
      Date toDate = lastRange.getToTime();

      long fromTimeInMillis = DateUtil.getMillis(DateUtil.getHours(fromDate), DateUtil.getMinutes(fromDate));
      long toTimeInMillis = DateUtil.getMillis(DateUtil.getHours(toDate), DateUtil.getMinutes(toDate));
      long dropInTimeInMillis = DateUtil.getMillis(DateUtil.getHours(startTime), DateUtil.getMinutes(startTime));

      // this is spanning type.
      if (fromTimeInMillis > toTimeInMillis) {
         // maybe there is spanning time from previous day.
         if (dropInTimeInMillis < fromTimeInMillis) {
            //find previous day
            SLACalendar slaCal = this.calendarManager.getSLACalendar(sla.getCalendar());
            slaCal.setTime(startTime);
            slaCal.add(Calendar.DATE, -1);
            // yesterday is working day
            if (!slaCal.isNonBusinessDay()) {
               CalendarDays month = this.calendarManager.getCalendarDaysByMonth(slaCal.getTime(), null, sla.getCalendar());
               CalendarDate previousDate = getCalendarDate(slaCal.getTime(), month);
               // include special day?
               if (previousDate.getId() == null || include) {
                  hours = previousDate.getWorkHourRanges();
                  list = new ArrayList(hours);
                  Collections.sort(list, new WorkHourRangeComparator());

                  lastRange = (WorkHourRange) list.get(list.size() - 1);
                  Date spanningTime = lastRange.getToTime();
                  long spanningTimeInMilis = DateUtil.getMillis(DateUtil.getHours(spanningTime), DateUtil.getMinutes(spanningTime));
                  if (spanningTimeInMilis > dropInTimeInMillis) {
                     return (spanningTimeInMilis - dropInTimeInMillis) / 1000 / 60;
                  }
               }
            }
         }
      }
      return 0;
   }

   private Date calculateForwardReminderTimeInWorkingHourRange(Calendar startTime, CalendarDate cd, Reference calendarName, 
         boolean includeSpecialDays, int durationInMinutes) {
      boolean dateCalculated = false;
      while (true) {
         Set workHours = cd.getWorkHourRanges();
         Iterator i = workHours.iterator();
         long rangeTotal = 0;
         while (i.hasNext()) {
            WorkHourRange whr = (WorkHourRange) i.next();
            Date cal1 = getTime(whr.getToTime());
            Date cal2 = getTime(whr.getFromTime());
            long difference = this.getHourRangeCheckSpan(cal1, cal2);
            rangeTotal += difference / 1000 / 60; // in minutes
         }
         // work out day units
         if (durationInMinutes <= rangeTotal) {
            // partial day
            startTime.add(Calendar.MINUTE, durationInMinutes);
            dateCalculated = true;
         } else {
            // whole day
            durationInMinutes -= rangeTotal;
            cd = findNextWorkDate(startTime.getTime(), calendarName, includeSpecialDays, true); // find
            // next
            // work
            // date
            startTime.setTime(this.getStartOfBusinessDay(cd));// false
         }
         if (dateCalculated)
            break;
      }
      return startTime.getTime();
   }

   private Date calculateForwardReminderTFormula(Date startTime, Date deadline, ServiceLevelAgreement sla, int durationInMinutes) {

      boolean include = true;
      if (sla.getIncludeSpecialDays().equals(Constants.NO_TYPE))
         include = false;

      Date realStartTime = this.getStartTimeForDeadLineTFormula(sla, startTime, include);
      Calendar cal = getCalendar(realStartTime);

      Reference calendarName = sla.getCalendar();
      CalendarDays month = this.calendarManager.getCalendarDaysByMonth(cal.getTime(), null, calendarName);
      CalendarDate cd = getCalendarDate(cal.getTime(), month);
      // This is to determine the start business time of cd
      cal.setTime(findBusinessDay(cd, realStartTime, sla, true));

      // modify spanning hours START
      long initialSpanningRange = this.getSpanningRangeForStartTime(sla, cd, realStartTime, include);
      if (initialSpanningRange != 0) {
         durationInMinutes -= initialSpanningRange;
         cal.setTime(getStartOfBusinessDay(cd));
      }

      if (sla.getDeadlineType().equals(Constants.END_OF_BUSINESS_TYPE) || sla.getDeadlineType().equals(Constants.SAME_TIME_TYPE)) {
         this.calculateForwardReminderTimeInWorkingHourRange(cal, cd, calendarName, include, durationInMinutes);
      } else if (sla.getDeadlineType().equals(Constants.END_OF_DAY_TYPE)) {
         cal.add(Calendar.MINUTE, durationInMinutes);

         month = this.calendarManager.getCalendarDaysByMonth(cal.getTime(), null, calendarName);
         cd = getCalendarDate(cal.getTime(), month);

         // Reminder Time should be fallen in working days.
         if ((cd.getNonWorkingDay() != null && cd.getNonWorkingDay().booleanValue()) || (cd.getId() != null && !include)) {
            cd = findNextWorkDate(cal.getTime(), calendarName, include, true);
            cal.setTime(cd.getDateInfo());
         }

         // Check for case reminder after 00:00 AM should be reminded in working hours.
         ensureEODReminderAfterMidnightFallingInWorkingHours(cal, cd, calendarName, include);
      }

      if (cal.getTime().compareTo(deadline) >= 0) {
         throw new IllegalArgumentException("Reminder time is later than deadline! Please review your SLA configuration.");
      }

      return cal.getTime();
   }

   private void ensureEODReminderAfterMidnightFallingInWorkingHours(Calendar cal, CalendarDate cd,
         Reference calendarName, boolean include) {
      Date startOfBussiness = this.getStartOfBusinessDay(cd);
      if (cal.getTime().compareTo(startOfBussiness) < 0) {
         // Check with yesterday EOB
         SLACalendar slaCal = this.calendarManager.getSLACalendar(calendarName);
         slaCal.setTime(cal.getTime());
         slaCal.add(Calendar.DATE, -1);
         // yesterday is working day
         if (!slaCal.isNonBusinessDay()) {
            CalendarDays month = this.calendarManager.getCalendarDaysByMonth(slaCal.getTime(), null, calendarName);
            CalendarDate previousDate = getCalendarDate(slaCal.getTime(), month);
            // include special day?
            if (previousDate.getId() == null || include) {
               Date yesterDayEOB = this.getEndOfBusinessDay(previousDate);
               // The reminder belongs to yesterday working hours.
               if (cal.getTime().compareTo(yesterDayEOB) < 0) {
                  return;
               }
            }
         }
         Calendar remindTime = getCalendar(startOfBussiness);
         remindTime.add(Calendar.MINUTE, cal.get(Calendar.MINUTE));
         remindTime.add(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
         cal.setTime(remindTime.getTime());
      }
   }

   private Date calculateForwardReminderFixedTime(Date startTime, ServiceLevelAgreement sla,
         int durationInMinutes) {
      Reference calendarName = sla.getCalendar();
      Date deadlineThreshold = sla.getFixedTimeThreshold();
      int daysToRoll = sla.getFixedTimeDaysToRoll().intValue();

      SLACalendar slaCalendar = this.calendarManager.getSLACalendar(calendarName);
      slaCalendar.setTimeInMillis(startTime.getTime());

      while (slaCalendar.isNonBusinessDay()) {
         slaCalendar.add(SLACalendar.DATE, 1);
         if (!slaCalendar.isNonBusinessDay()) {
            CalendarDays month = this.calendarManager.getCalendarDaysByMonth(slaCalendar.getTime(), null, calendarName);
            CalendarDate cd = getCalendarDate(slaCalendar.getTime(), month);
            slaCalendar.setTime(this.getStartOfBusinessDay(cd));
         }
      }

      CalendarDays month = this.calendarManager.getCalendarDaysByMonth(slaCalendar.getTime(), null, calendarName);
      CalendarDate cd = getCalendarDate(slaCalendar.getTime(), month);
      slaCalendar.setTime(this.adjustStartTimeForSpanningDay(sla, cd, slaCalendar.getTime(), true));

      Calendar calendar = getCalendar(slaCalendar.getTime());

      long currentMillis = DateUtil.getMillis(slaCalendar.get(SLACalendar.HOUR_OF_DAY), slaCalendar
            .get(SLACalendar.MINUTE));
      long thresholdMillis = DateUtil.getMillis(DateUtil.getHours(deadlineThreshold), DateUtil
            .getMinutes(deadlineThreshold));

      if (currentMillis > thresholdMillis) {
         slaCalendar.add(SLACalendar.BUSINESS_DATE, daysToRoll);
         month = this.calendarManager.getCalendarDaysByMonth(slaCalendar.getTime(), null, calendarName);
         cd = getCalendarDate(slaCalendar.getTime(), month);
         calendar.setTime(this.getStartOfBusinessDay(cd));
      } 

      calendar.add(Calendar.MINUTE, durationInMinutes);
      
      return calendar.getTime();
   }

   private Date calculateForwardReminderDuration(Date startTime, ServiceLevelAgreement sla, int durationInMinutes) {

      Date realStartTime = this.getStartTimeForDeadLineTFormula(sla, startTime, true);
      Calendar cal = getCalendar(realStartTime);

      Reference calendarName = sla.getCalendar();
      CalendarDays month = this.calendarManager.getCalendarDaysByMonth(cal.getTime(), null, calendarName);
      CalendarDate cd = getCalendarDate(cal.getTime(), month);
      // This is to determine the start business time of cd
      cal.setTime(findBusinessDay(cd, realStartTime, sla, true));

      long initialSpanningRange = this.getSpanningRangeForStartTime(sla, cd, realStartTime, true);
      if (initialSpanningRange != 0) {
         durationInMinutes -= initialSpanningRange;
         cal.setTime(getStartOfBusinessDay(cd));
      }
      
      if (sla.getWorkTime().equals(Constants.TWENTY_FOUR_HOUR_CLOCK_TYPE)) {
         month = this.calendarManager.getCalendarDaysByMonth(cal.getTime(), null, calendarName);
         cd = getCalendarDate(cal.getTime(), month);
         if (cal.getTime().compareTo(this.getEndOfBusinessDay(cd)) > 0) {
            cd = findNextWorkDate(cal.getTime(), calendarName);
            Date openTime = getStartOfBusinessDay(cd);
            cal.setTime(openTime);
         }
         cal.add(Calendar.MINUTE, durationInMinutes);
      } else {
         this.calculateForwardReminderTimeInWorkingHourRange(cal, cd, calendarName, true, durationInMinutes);
      }

      return cal.getTime();
   }

   private Date calculateReminderTime(Date startTime, ServiceLevelAgreement sla, int durationInMinutes) {
      Calendar cal = getCalendar(startTime);

      Reference calendarName = sla.getCalendar();
      CalendarDays month = this.calendarManager.getCalendarDaysByMonth(cal.getTime(), null, calendarName);
      CalendarDate cd = getCalendarDate(cal.getTime(), month);
      Date dt = null;
      if (cd.getNonWorkingDay() != null && cd.getNonWorkingDay()) {
         cd = findNextWorkDate(cal.getTime(), calendarName, true, false);
         dt = this.getEndOfBusinessDay(cd);// false
      } else {
         dt = findBusinessDay4ReminderTime(cd, startTime, sla, false);
      }

      cal.setTime(dt);
      boolean dateCalculated = false;
      while (true) {
         Set<WorkHourRange> workHours = cd.getWorkHourRanges();
         Iterator<WorkHourRange> i = workHours.iterator();
         long rangeTotal = 0;
         while (i.hasNext()) {
            WorkHourRange whr = i.next();
            Date cal1 = getTime(whr.getToTime());
            Date cal2 = getTime(whr.getFromTime());
            long difference = this.getHourRangeCheckSpan(cal1, cal2);
            rangeTotal += difference / 1000 / 60; // in minutes
         }
         // work out day units
         if (durationInMinutes <= rangeTotal) {
            // partial day
            cal.add(Calendar.MINUTE, 0 - durationInMinutes);
            dateCalculated = true;
         } else {
            // whole day
            durationInMinutes -= rangeTotal;
            cd = findNextWorkDate(cal.getTime(), calendarName, true, false); // find
            // previous
            // work
            // date
            cal.setTime(getEndOfBusinessDay(cd));// false
         }
         if (dateCalculated) {
            break;
         }
      }

      return cal.getTime();
   }

   private long getHourRangeCheckSpan(Date to, Date from) {
      // added "=" to cover the situation that deadline = start of business
      if (to.getTime() >= from.getTime()) {
         return (to.getTime() - from.getTime());
      }
      // Spanning calendar
      else {
         Calendar cal = this.getCalendar(to);
         cal.add(Calendar.DATE, 1);
         return (cal.getTimeInMillis() - from.getTime());
      }
   }

   /**
    * Calculates the accumulated sleep time
    *
    * @param processName , processType, toDate, fromDate, accumSleepTime
    * @return deadline
    * @todo Need to confirm if we need to remove non-business time from this
    * equation. if so, get appropriate SLA calendar, from hiberate. add
    * logic to remove non-business days.
    */
   public float getSLAAccumSleepTime(String processName, String processType, Date fromDate, Date toDate,
           float accumSleepTime) {
      BusinessProcess process = getBusinessProcess(processName, processType);
      ServiceLevelAgreement sla = process.getServiceLevelAgreement();

      // only increase accumulated sleep time if pause threshold is OK.
      if (checkPauseThreshold(sla, fromDate, toDate)) {
         float diffMills = toDate.getTime() - fromDate.getTime();
         accumSleepTime += diffMills;
      }

      return accumSleepTime;
   }

   private boolean checkPauseThreshold(ServiceLevelAgreement sla, Date fromDate, Date toDate) {
      boolean threshold = false;
      if (getStopWhenPaused(sla)) {
         Calendar fCal = getCalendar(fromDate);
         Calendar tCal = getCalendar(toDate);
         int h = sla.getPauseThresholdHours();
         int m = sla.getPauseThresholdMinutes();
         fCal.add(Calendar.HOUR, h);
         fCal.add(Calendar.MINUTE, m);

         if (fCal.before(tCal)) {
            threshold = true;
         }
      }
      return threshold;
   }

   private Calendar getCalendar(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      return cal;
   }

   /**
    * Calculates a status
    *
    * @param processName , processType, pointInTime, deadline, reminderTime
    * @return status
    * @todo review benefits of SLAStatus maintenance. 1. Use References for now.
    * 2. Consider SLAStatusDAO. 3. Go back to using properties/constants.
    */
   public String getSLAStatus(String processName, String processType, Date pointInTime, Date deadline,
           Date reminderTime) {

      String referenceGroupName = Constants.REFERENCE_SLASTATUS_EXCEEDED;

      if (pointInTime.before(deadline)) {
         referenceGroupName = Constants.REFERENCE_SLASTATUS_APPROACHING;
      }

      if (pointInTime.before(reminderTime)) {
         referenceGroupName = Constants.REFERENCE_SLASTATUS_OK;
      }

      List<Reference> referenceList = referenceManager.getReferencesByParam(referenceGroupName);

      String status = "";
      if (referenceList.size() > 0) {
         Reference reference = referenceList.get(0);
         status = reference.getItemName();
      } else {
         // Reference data has been deleted, get status from Constants
         status = getDefaultSLAStatus(processName, processType, pointInTime, deadline, reminderTime);
      }

      return status;
   }

   /**
    * Calculates a status using Constants
    *
    * @param processName , processType, pointInTime, deadline, reminderTime
    * @return status
    */
   private String getDefaultSLAStatus(String processName, String processType, Date pointInTime, Date deadline,
           Date reminderTime) {
      String status = Constants.DEFAULT_SLASTATUS_EXCEEDED;

      if (pointInTime.before(deadline)) {
         status = Constants.DEFAULT_SLASTATUS_APPROACHING;
      }

      if (pointInTime.before(reminderTime)) {
         status = Constants.DEFAULT_SLASTATUS_OK;
      }

      return status;
   }

   /**
    * Calculates a start time
    *
    * @param processName , processType, startTime
    * @return startTime
    */
   public Date getSLAStartTime(String processName, String processType, Date startTime) {
      if (null == startTime) {
         startTime = new Date();
      }
      return startTime;
   }

   public void removeServiceLevelAgreementReference(ServiceLevelAgreement serviceLevelAgreement) {
      Set<BusinessProcess> businessProcesses = serviceLevelAgreement.getBusinessProcesses();
      for (BusinessProcess bp : businessProcesses) {
         bp.setServiceLevelAgreement(null);
         getBusinessProcessDAO().saveBusinessProcess(bp);
      }
   }
}
