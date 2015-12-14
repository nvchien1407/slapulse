//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service.impl;

import java.util.Date;
import java.util.List;

import com.renewtek.dao.ChangeLogDAO;
import com.renewtek.model.ChangeLog;
import com.renewtek.service.ChangeLogManager;

public class ChangeLogManagerImpl extends BaseManager implements ChangeLogManager {
    protected ChangeLogDAO getChangeLogDAO() {
       return (ChangeLogDAO) this.dao;
    }

    /**
     * Set the DAO for communication with the data layer.
     *
     * @param dao
     */
    public void setChangeLogDAO(ChangeLogDAO dao) {
        this.dao = dao;
    }

    /**
     * @see com.renewtek.service.ChangeLogManager#getChangeLogs(com.renewtek.model.ChangeLog)
     */
    public List<ChangeLog> getChangeLogs(final ChangeLog changeLog) {
        return getChangeLogDAO().getChangeLogs(changeLog);
    }

    /**
     * @see com.renewtek.service.ChangeLogManager#getChangeLog(String id)
     */
    public ChangeLog getChangeLog(final String id) {
        return getChangeLogDAO().getChangeLog(new Integer(id));
    }

    /**
     * @see com.renewtek.service.ChangeLogManager#saveChangeLog(ChangeLog changeLog)
     */
    public void saveChangeLog(ChangeLog changeLog) {
        getChangeLogDAO().saveChangeLog(changeLog);
    }

    /**
     * @see com.renewtek.service.ChangeLogManager#removeChangeLog(String id)
     */
    public void removeChangeLog(final String id) {
        getChangeLogDAO().removeChangeLog(new Integer(id));
    }
    
    public List<ChangeLog> getChangeLogsSince(Date operateTime) {
       return getChangeLogDAO().getChangeLogsSince(operateTime);
   }
}
