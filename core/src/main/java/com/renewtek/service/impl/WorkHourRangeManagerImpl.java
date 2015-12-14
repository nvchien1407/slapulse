//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service.impl;

import java.util.List;

import com.renewtek.model.WorkHourRange;
import com.renewtek.dao.WorkHourRangeDAO;
import com.renewtek.service.WorkHourRangeManager;

public class WorkHourRangeManagerImpl extends BaseManager implements WorkHourRangeManager {
    protected WorkHourRangeDAO getWorkHourRangeDAO() {
       return (WorkHourRangeDAO) this.dao;
    }

    /**
     * Set the DAO for communication with the data layer.
     *
     * @param dao
     */
    public void setWorkHourRangeDAO(WorkHourRangeDAO dao) {
        this.dao = dao;
    }

    /**
     * @see com.renewtek.service.WorkHourRangeManager#getWorkHourRanges(com.renewtek.model.WorkHourRange)
     */
    public List<WorkHourRange> getWorkHourRanges(final WorkHourRange workHourRange) {
        return getWorkHourRangeDAO().getWorkHourRanges(workHourRange);
    }

    /**
     * @see com.renewtek.service.WorkHourRangeManager#getWorkHourRange(String id)
     */
    public WorkHourRange getWorkHourRange(final String id) {
        return getWorkHourRangeDAO().getWorkHourRange(new Integer(id));
    }

    /**
     * @see com.renewtek.service.WorkHourRangeManager#saveWorkHourRange(WorkHourRange workHourRange)
     */
    public void saveWorkHourRange(WorkHourRange workHourRange) {
        getWorkHourRangeDAO().saveWorkHourRange(workHourRange);
    }

    /**
     * @see com.renewtek.service.WorkHourRangeManager#removeWorkHourRange(String id)
     */
    public void removeWorkHourRange(final String id) {
        getWorkHourRangeDAO().removeWorkHourRange(new Integer(id));
    }
}
