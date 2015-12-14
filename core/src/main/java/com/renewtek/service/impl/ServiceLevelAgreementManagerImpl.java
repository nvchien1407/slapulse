//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.renewtek.model.ServiceLevelAgreement;
import com.renewtek.dao.ServiceLevelAgreementDAO;
import com.renewtek.service.ServiceLevelAgreementManager;
import com.renewtek.util.StringUtil;

public class ServiceLevelAgreementManagerImpl extends BaseManager implements ServiceLevelAgreementManager {

    protected ServiceLevelAgreementDAO getServiceLevelAgreementDAO() {
       return (ServiceLevelAgreementDAO) this.dao;
    }

    /**
     * Set the DAO for communication with the data layer.
     *
     * @param dao
     */
    public void setServiceLevelAgreementDAO(ServiceLevelAgreementDAO dao) {
        this.dao = dao;
    }

    /**
     * @see com.renewtek.service.ServiceLevelAgreementManager#getServiceLevelAgreements(com.renewtek.model.ServiceLevelAgreement)
     */
    public List<ServiceLevelAgreement> getServiceLevelAgreements(final ServiceLevelAgreement serviceLevelAgreement) {
        return getServiceLevelAgreementDAO().getServiceLevelAgreements(serviceLevelAgreement);
    }

    /**
     * @see com.renewtek.service.ServiceLevelAgreementManager#getServiceLevelAgreement(String id)
     */
    public ServiceLevelAgreement getServiceLevelAgreement(final String id) {
      Integer dbID = StringUtil.purifyStringIdFromWeb(id);
      return getServiceLevelAgreementDAO().getServiceLevelAgreement(dbID);
    }

    /**
     * @see com.renewtek.service.ServiceLevelAgreementManager#saveServiceLevelAgreement(ServiceLevelAgreement serviceLevelAgreement)
     */
    public void saveServiceLevelAgreement(ServiceLevelAgreement serviceLevelAgreement) {
        getServiceLevelAgreementDAO().saveServiceLevelAgreement(serviceLevelAgreement);
    }

    /**
     * @see com.renewtek.service.ServiceLevelAgreementManager#removeServiceLevelAgreement(String id)
     */
    public void removeServiceLevelAgreement(final String id) {
      Integer dbID = StringUtil.purifyStringIdFromWeb(id);
      getServiceLevelAgreementDAO().removeServiceLevelAgreement(new Integer(dbID));
    }

    public List<String> getMinutes() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 60; i += 5) {
            list.add(i + "");
        }
        return list;
    }
}
