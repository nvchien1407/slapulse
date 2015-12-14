//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Column;
import javax.persistence.Table;


@Entity
@Table(name = "defaultweekday")
@PrimaryKeyJoinColumn(name="defaultweekdayid")
public class DefaultWeekDay extends DayModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1320206926775193503L;
	
	@Column(name = "day", nullable = false)
    private String day;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
