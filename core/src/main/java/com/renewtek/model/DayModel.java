//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.renewtek.Constants;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import static org.hibernate.annotations.CascadeType.DELETE_ORPHAN;

@Entity
@Table(name = "defaultday")
@Inheritance(strategy=InheritanceType.JOINED)
public class DayModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4811096930040380375L;

    @Id
    @GeneratedValue(generator="defaultday_gen")
    @GenericGenerator(name="defaultday_gen", strategy="com.renewtek.dao.hibernate.AssignedHiLoIdGenerator",  
          parameters = { 
          @Parameter(name="table", value="sequence_list"), 
          @Parameter(name="pkColumnName", value="name"), 
          @Parameter(name="valueColumnName", value="next_value"), 
          @Parameter(name="allocationSize", value="50"), 
          @Parameter(name="initialValue", value="3000"), 
          @Parameter(name="pkColumnValue", value="DefaultDay") } )
    @Column(name = "id")
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "regionid", nullable = false)
    private Reference region;
    
    @Column(name = "nonworkingday", nullable = true)
    private Boolean nonWorkingDay = false;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = WorkHourRange.class)
    @Cascade(DELETE_ORPHAN)
    private Set<WorkHourRange> workHourRanges;

    @Column(name = "defaultdayflag", nullable = false )
    private Boolean defaultDay;

    //@Column(name = "isDefault", nullable = true )
    @Transient
    private boolean isDefault;

    @Transient
    private SimpleDateFormat format = new SimpleDateFormat(Constants.DEFAULT_TIME_FORMAT);

    public Set<WorkHourRange> getWorkHourRanges() {
        return workHourRanges;
    }

    public void setWorkHourRanges(Set<WorkHourRange> workHourRanges) {
        this.workHourRanges = workHourRanges;
    }

    public Reference getRegion() {
        return region;
    }

    public void setRegion(Reference region) {
        this.region = region;
    }

    public Boolean getNonWorkingDay() {
        return nonWorkingDay;
    }

    public void setNonWorkingDay(Boolean nonWorkingDay) {
        this.nonWorkingDay = nonWorkingDay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Boolean getDefaultDay() {
        return defaultDay;
    }

    public void setDefaultDay(Boolean defaultDay) {
        this.defaultDay = defaultDay;
    }

    public WorkHourRange getWorkHourRange() {
        Set<WorkHourRange> worktime = this.getWorkHourRanges();
        if (worktime == null || worktime.size() == 0)
            return null;
        WorkHourRange range = null;
        if(worktime.size()>0) {
            range = worktime.iterator().next();
        }
        return range;
    }

    public String getFromTime() {
        return getTime(true);
    }

    private String getTime(boolean startTime) {
        WorkHourRange range = this.getWorkHourRange();
        if (range == null)
            return "";
        Date time = range.getFromTime();
        if (!startTime)
            time = range.getToTime();
        if (time == null)
            return "";
        return format.format(time);
    }

    public void setFromTime(String fromTime) throws Exception {
        setTime(true, fromTime);
    }

    private void setTime(boolean startTime, String time) throws Exception {
        Date dt = null;
        if (time != null && !time.equals(""))
            dt = format.parse(time);
        WorkHourRange range = this.getWorkHourRange();
        if (range == null) {
            range = createWorkRange();
        }
        if (startTime) {
            range.setFromTime(dt);
        } else {
            range.setToTime(dt);
        }
    }

    public WorkHourRange createWorkRange() {
        WorkHourRange range = new WorkHourRange();
        range.setDay(this);
        if (this.getWorkHourRanges() == null)
            this.setWorkHourRanges(new LinkedHashSet<WorkHourRange>());
        this.getWorkHourRanges().add(range);
        return range;
    }

    public String getToTime() {
        return getTime(false);
    }

    public void setToTime(String toTime) throws Exception {
        setTime(false, toTime);
    }

}
