//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Table(name = "calendardate")
@PrimaryKeyJoinColumn(name = "calendardateid")
public class CalendarDate extends DayModel {

    private static final long serialVersionUID = -2788408168865398079L;

    @Column(name = "dateinfo", nullable = false)
    private Date dateInfo;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Transient
    private boolean timezoneConverted = false;

    public String getName() {
        return name;
    }

    /**
     * @spring.validator type="required" msgkey="errors.required"
     */
    public void setName(String name) {
        this.name = name;
    }

    public Date getDateInfo() {
        return dateInfo;
    }

    public void setDateInfo(Date dateInfo) {
        this.dateInfo = dateInfo;
    }

    public String getDate() {
       Calendar cal = GregorianCalendar.getInstance();
       cal.setTime(dateInfo);
       return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
    }

    public String getDateInfoString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        return dateFormat.format(this.dateInfo);
    }

    public String getDescription() {
        return description;
    }

    /**
     * @spring.validator type="required" msgkey="errors.required"
     */
    public void setDescription(String description) {
        this.description = description;
    }

   public boolean getTimezoneConverted() {
      return timezoneConverted;
   }

   public void setTimezoneConverted(boolean timezoneConverted) {
      this.timezoneConverted = timezoneConverted;
   }
    
    
}
