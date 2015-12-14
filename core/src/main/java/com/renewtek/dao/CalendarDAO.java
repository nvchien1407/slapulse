//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT©2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao;

import java.util.Calendar;

import com.renewtek.model.CalendarDays;
import com.renewtek.model.Reference;

public interface CalendarDAO extends DAO {
    public CalendarDays getCalendarDays(Calendar cal, Reference region);
}
