// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "workhourrange")
public class WorkHourRange implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = -7488810158901767156L;
   
   @Id
   @GeneratedValue(generator = "workrange_gen")
   @GenericGenerator(name="workrange_gen", strategy="com.renewtek.dao.hibernate.AssignedHiLoIdGenerator",  
         parameters = { 
         @Parameter(name="table", value="sequence_list"), 
         @Parameter(name="pkColumnName", value="name"), 
         @Parameter(name="valueColumnName", value="next_value"), 
         @Parameter(name="allocationSize", value="50"), 
         @Parameter(name="initialValue", value="3000"), 
         @Parameter(name="pkColumnValue", value="WorkHourRange") } )
   @Column(name = "id")
   private Integer id;
   
   @Column(name = "fromtime")
   private Date fromTime;
   
   @Column(name = "totime")
   private Date toTime;
   
   @ManyToOne()
   @JoinColumn(name = "dayid", nullable = false)
   private DayModel day;

   
   public Date getFromTime() {
      return fromTime;
   }

   public void setFromTime(Date from) {
      this.fromTime = from;
   }

   
   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }
   
   public Date getToTime() {
      return toTime;
   }

   public void setToTime(Date to) {
      this.toTime = to;
   }

   
   public DayModel getDay() {
      return day;
   }

   public void setDay(DayModel day) {
      this.day = day;
   }

   public String getStartMinute() {
      if (fromTime == null)
         return "";

      Calendar cal = getCalendar(fromTime);
      int minutes = cal.get(Calendar.MINUTE);
      return (minutes < 10) ? "0" + String.valueOf(minutes) : String.valueOf(minutes);
   }

   public void setStartMinute(String minute) {
      if (fromTime == null)
         fromTime = new Date();

      Calendar cal = getCalendar(fromTime);
      cal.set(Calendar.MINUTE, Integer.parseInt(minute));
      fromTime = cal.getTime();
   }

   public String getStartHour() {
      if (fromTime == null)
         return "";

      Calendar cal = getCalendar(fromTime);
      int hour = cal.get(Calendar.HOUR_OF_DAY);
      return (hour < 10) ? "0" + String.valueOf(hour) : String.valueOf(hour);
   }

   public void setStartHour(String hour) {
      if (fromTime == null)
         fromTime = new Date();

      Calendar cal = getCalendar(fromTime);
      cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
      fromTime = cal.getTime();
   }

   public String getEndMinute() {
      if (toTime == null)
         return "";

      Calendar cal = getCalendar(toTime);
      int minutes = cal.get(Calendar.MINUTE);
      return (minutes < 10) ? "0" + String.valueOf(minutes) : String.valueOf(minutes);
   }

   public void setEndMinute(String minute) {
      if (toTime == null)
         toTime = new Date();

      Calendar cal = getCalendar(toTime);
      cal.set(Calendar.MINUTE, Integer.parseInt(minute));
      toTime = cal.getTime();
   }

   public String getEndHour() {
      if (toTime == null)
         return "";

      Calendar cal = getCalendar(toTime);
      int hour = cal.get(Calendar.HOUR_OF_DAY);
      return (hour < 10) ? "0" + String.valueOf(hour) : String.valueOf(hour);
   }

   public void setEndHour(String hour) {
      if (toTime == null)
         toTime = new Date();

      Calendar cal = getCalendar(toTime);
      cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
      toTime = cal.getTime();
   }

   public static boolean isBeforeTimes(Date dt1, Date dt2) {
      Calendar cal1 = getCalendar(dt1);
      Calendar cal2 = getCalendar(dt2);
      Calendar cal3 = (Calendar) cal1.clone();

      cal3.clear(Calendar.HOUR_OF_DAY);
      cal3.set(Calendar.HOUR_OF_DAY, cal2.get(Calendar.HOUR_OF_DAY));

      cal3.clear(Calendar.MINUTE);
      cal3.set(Calendar.MINUTE, cal2.get(Calendar.MINUTE));

      return cal1.before(cal3);
   }

   private static Calendar getCalendar(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      return cal;
   }

}
