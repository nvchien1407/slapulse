//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service.impl;

import java.util.List;

import com.renewtek.model.DayModel;
import com.renewtek.dao.DayModelDAO;
import com.renewtek.service.DayModelManager;

public class DayModelManagerImpl extends BaseManager implements DayModelManager {
    protected DayModelDAO getDayModelDAO() {
       return (DayModelDAO) this.dao;
    }

    /**
     * Set the DAO for communication with the data layer.
     *
     * @param dao
     */
    public void setDayModelDAO(DayModelDAO dao) {
        this.dao = dao;
    }

    /**
     * @see com.renewtek.service.DayModelManager#getDayModels(com.renewtek.model.DayModel)
     */
    public List<DayModel> getDayModels(final DayModel dayModel) {
        return getDayModelDAO().getDayModels(dayModel);
    }

    /**
     * @see com.renewtek.service.DayModelManager#getDayModel(String id)
     */
    public DayModel getDayModel(final String id) {
        return getDayModelDAO().getDayModel(new Integer(id));
    }

    /**
     * @see com.renewtek.service.DayModelManager#saveDayModel(DayModel dayModel)
     */
    public void saveDayModel(DayModel dayModel) {
        getDayModelDAO().saveDayModel(dayModel);
    }

    /**
     * @see com.renewtek.service.DayModelManager#removeDayModel(String id)
     */
    public void removeDayModel(final String id) {
        getDayModelDAO().removeDayModel(new Integer(id));
    }

    public List<DayModel> getCalendars() {
        DayModel d = new DayModel();
        d.setDefaultDay(true);
        return getDayModelDAO().getDayModels(d);
    }
}
