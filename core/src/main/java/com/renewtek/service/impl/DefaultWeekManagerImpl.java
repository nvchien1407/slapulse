//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT©2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service.impl;

import java.util.Date;

import com.renewtek.dao.DefaultWeekDAO;
import com.renewtek.model.DayModel;
import com.renewtek.model.DefaultWeek;
import com.renewtek.model.Reference;
import com.renewtek.service.DefaultWeekManager;

public class DefaultWeekManagerImpl extends BaseManager implements DefaultWeekManager {
    protected DefaultWeekDAO getDefaultWeekDAO() {
       return (DefaultWeekDAO) this.dao;
    }

    /**
     * Set the DAO for communication with the data layer.
     *
     * @param dao
     */
    public void setDefaultWeekDAO(DefaultWeekDAO dao) {
        this.dao = dao;
    }

    public DefaultWeek getDefaultWeek(final Reference region) {
        return getDefaultWeekDAO().getDefaultWeek(region);
    }

    public DefaultWeek getDefaultWeekById(final String id) {
        return getDefaultWeekDAO().getDefaultWeekById(new Integer(id));
    }

    /**
     * @see com.renewtek.service.DefaultWeekManager#saveDefaultWeek(DefaultWeek defaultWeek)
     */
    public void saveDefaultWeek(DefaultWeek defaultWeek) {
        getDefaultWeekDAO().saveDefaultWeek(defaultWeek);
    }

    /**
     * @see com.renewtek.service.DefaultWeekManager#removeDefaultWeek(String id)
     */
    public void removeDefaultWeek(final String id) {
        getDefaultWeekDAO().removeDefaultWeek(new Integer(id));
    }

    public DayModel getDayByRegion(Date date, Reference region) {
        return getDefaultWeekDAO().getDefaultDay(date, region);
    }

}
