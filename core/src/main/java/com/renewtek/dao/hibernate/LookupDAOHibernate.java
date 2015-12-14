//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao.hibernate;

import java.util.List;

import com.renewtek.dao.LookupDAO;
import com.renewtek.model.Role;


/**
 * Hibernate implementation of LookupDAO.
 * <p/>
 * <p><a href="LookupDAOHibernate.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class LookupDAOHibernate extends BaseDAOHibernate implements LookupDAO {

    /**
     * @see com.renewtek.dao.LookupDAO#getRoles()
     */
    @SuppressWarnings("unchecked")
   public List<Role> getRoles() {
        if (log.isDebugEnabled()) {
            log.debug("retrieving all role names...");
        }

        return getHibernateTemplate().find("from Role order by name");
    }
}
