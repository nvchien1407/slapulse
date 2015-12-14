//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @hibernate.class table="ServiceLevelAgreement"
 */
@Entity
@Table(name = "servicelevelagreement")
public class ServiceLevelAgreement implements Auditable {
	
    @Id
    @GeneratedValue(generator="serveiceagreement_gen")
    @GenericGenerator(name="serveiceagreement_gen", strategy="com.renewtek.dao.hibernate.AssignedHiLoIdGenerator",  
          parameters = { 
          @Parameter(name="table", value="sequence_list"), 
          @Parameter(name="pkColumnName", value="name"), 
          @Parameter(name="valueColumnName", value="next_value"), 
          @Parameter(name="allocationSize", value="50"), 
          @Parameter(name="initialValue", value="3000"), 
          @Parameter(name="pkColumnValue", value="ServiceLevelAgreement") } )
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "durationtype", nullable = false)
    private String durationType;
    
    @Column(name = "tformulaedays", nullable = true)
    private Integer TFormulaeDays;

    @Column(name = "durationhours", nullable = true)
    private Integer durationHours;

    @Column(name = "durationminutes", nullable = true)
    private Integer durationMinutes;

    @Column(name = "deadlinetype", nullable = false)
    private String deadlineType;

    @Column(name = "worktime", nullable = false)
    private String workTime;

    @Column(name = "pausethresholdhours", nullable = true)
    private Integer pauseThresholdHours;

    @Column(name = "pausethresholdminutes", nullable = true)
    private Integer pauseThresholdMinutes;

    @Column(name = "enablecutofftime", nullable = false)
    private Boolean enableCutOffTime;

   @Column(name = "stopslawhenpaused", nullable = false)
    private Boolean stopSlaWhenPaused;

    @Column(name = "includespecialdays", nullable = true)
    private String includeSpecialDays;

    @Column(name = "notifydeadlineapproaching", nullable = true)
    private Boolean notifyDeadlineApproaching;

    @Column(name = "notifydeadlineapproachingfw", nullable = true)
    private Boolean notifyDeadlineApproachingForwarding;

    @Column(name = "notificationthreshold", nullable = true)
    private Long notificationThreshold;

    @OneToMany(mappedBy = "serviceLevelAgreement", fetch = FetchType.LAZY, targetEntity = BusinessProcess.class)
    private Set<BusinessProcess> businessProcesses;

    @ManyToOne()
    @JoinColumn(name = "calendarid", nullable = false)
    private Reference calendar;

    @Column(name = "notificationthresholddays", nullable = true)
    private Integer notificationThresholdDays;
    
    @Column(name = "notificationthresholdhours", nullable = true)
    private Integer notificationThresholdHours;

    @Column(name = "notificationthresholdminutes", nullable = true)
    private Integer notificationThresholdMinutes;

    @Column(name = "fixedtimedeadline")
    private Date fixedTimeDeadline;

    @Column(name = "fixedtimethreshold")
    private Date fixedTimeThreshold;

    @Column(name = "fixedtimedaystoroll", nullable = true)
    private Integer fixedTimeDaysToRoll;

    @Transient
    private Integer hashcodeValue = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<BusinessProcess> getBusinessProcesses() {
        return businessProcesses;
    }

    public void setBusinessProcesses(Set<BusinessProcess> businessProcesses) {
        this.businessProcesses = businessProcesses;
    }

    public String getDeadlineType() {
        return deadlineType;
    }

    public void setDeadlineType(String deadlineType) {
        this.deadlineType = deadlineType;
    }

    public Integer getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getDurationType() {
        return durationType;
    }

    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }

    public String getName() {
        return name;
    }

    /**
     * @spring.validator type="required" msgkey="errors.required"
     */
    public void setName(String name) {
        this.name = name;
    }

    public Integer getPauseThresholdHours() {
        return pauseThresholdHours;
    }

    public void setPauseThresholdHours(Integer pauseThresholdHours) {
        this.pauseThresholdHours = pauseThresholdHours;
    }

    public Integer getPauseThresholdMinutes() {
        return pauseThresholdMinutes;
    }

    public void setPauseThresholdMinutes(Integer pauseThresholdMinutes) {
        this.pauseThresholdMinutes = pauseThresholdMinutes;
    }

    public Integer getTFormulaeDays() {
        return TFormulaeDays;
    }

    /**
     * @spring.validator-var name="min" value="1"
     * @spring.validator-var name="max" value="365"
     * @spring.validator type = "intRange"
     * @spring.validator type = "integer"
     * @spring.validator-args arg1resource="${var:min}"
     * @spring.validator-args arg2resource="${var:max}"
     */
    public void setTFormulaeDays(Integer TFormulaeDays) {
        this.TFormulaeDays = TFormulaeDays;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public Boolean getEnableCutOffTime() {
       return enableCutOffTime;
    }

    public void setEnableCutOffTime(Boolean enableCutOffTime) {
       this.enableCutOffTime = enableCutOffTime;
    }

    public Boolean getStopSlaWhenPaused() {
        return stopSlaWhenPaused;
    }

    public void setStopSlaWhenPaused(Boolean stopSlaWhenPaused) {
        this.stopSlaWhenPaused = stopSlaWhenPaused;
    }

    public Reference getCalendar() {
        return calendar;
    }

    /**
     * @spring.validator type="required"
     */
    public void setCalendar(Reference calendar) {
        this.calendar = calendar;
    }

    public String getIncludeSpecialDays() {
        return includeSpecialDays;
    }

    public void setIncludeSpecialDays(String includeSpecialDays) {
        this.includeSpecialDays = includeSpecialDays;
    }

    public Boolean getNotifyDeadlineApproaching() {
        return notifyDeadlineApproaching;
    }

    public void setNotifyDeadlineApproaching(Boolean notifyDeadlineApproaching) {
        this.notifyDeadlineApproaching = notifyDeadlineApproaching;
    }

    public Boolean getNotifyDeadlineApproachingForwarding() {
       return notifyDeadlineApproachingForwarding;
    }

    public void setNotifyDeadlineApproachingForwarding(Boolean notifyDeadlineApproachingForwarding) {
       this.notifyDeadlineApproachingForwarding = notifyDeadlineApproachingForwarding;
    }

    public Long getNotificationThreshold() {
        return notificationThreshold;
    }

    public void setNotificationThreshold(Long notificationThreshold) {
        this.notificationThreshold = notificationThreshold;
    }

    public Integer getNotificationThresholdDays() {
        return notificationThresholdDays;
    }

    public void setNotificationThresholdDays(Integer notificationThresholdDays) {
        this.notificationThresholdDays = notificationThresholdDays;
    }

    public Integer getNotificationThresholdHours() {
        return notificationThresholdHours;
    }

    public void setNotificationThresholdHours(Integer notificationThresholdHours) {
        this.notificationThresholdHours = notificationThresholdHours;
    }

    public Integer getNotificationThresholdMinutes() {
        return notificationThresholdMinutes;
    }

    public void setNotificationThresholdMinutes(Integer notificationThresholdMinutes) {
        this.notificationThresholdMinutes = notificationThresholdMinutes;
    }

    public Integer getFixedTimeDaysToRoll() {
        return fixedTimeDaysToRoll;
    }

    public void setFixedTimeDaysToRoll(Integer fixedTimeDaysToRoll) {
        this.fixedTimeDaysToRoll = fixedTimeDaysToRoll;
    }

    public Date getFixedTimeDeadline() {
        return fixedTimeDeadline;
    }

    /**
     * @spring.validator type="required"
     * @spring.validator-var name="datePattern" value="HH:mm"
     * @spring.validator type="date"
     */
    public void setFixedTimeDeadline(Date fixedTimeDeadline) {
        this.fixedTimeDeadline = fixedTimeDeadline;
    }

    public Date getFixedTimeThreshold() {
        return fixedTimeThreshold;
    }

    /**
     * @spring.validator type="required"
     * @spring.validator-var name="datePattern" value="HH:mm"
     * @spring.validator type="date"
     */
    public void setFixedTimeThreshold(Date fixedTimeThreshold) {
		this.fixedTimeThreshold = fixedTimeThreshold;
    }

    public boolean equals(Object other) {
       if (!(other instanceof ServiceLevelAgreement)) {
          return false;
       }
       ServiceLevelAgreement castOther = (ServiceLevelAgreement) other;
       return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }

    public int hashCode() {
       if (hashcodeValue == null) {
          if (id == null) {
             hashcodeValue = super.hashCode();
          }
          else {
             hashcodeValue = this.generateHashCode();
          }
       }
       return hashcodeValue;
    }

    public int generateHashCode() {
       return new HashCodeBuilder().append(getId()).toHashCode();
    }
}
