//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service;

import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.renewtek.util.ConvertUtil;
import org.jmock.MockObjectTestCase;


public abstract class BaseManagerTestCase extends MockObjectTestCase {
    //~ Static fields/initializers =============================================

    protected final Log log = LogFactory.getLog(getClass());
    protected ResourceBundle rb;

    //~ Constructors ===========================================================

    public BaseManagerTestCase() {
        // Since a ResourceBundle is not required for each class, just
        // do a simple check to see if one exists
        //String className = this.getClass().getName();
       String className = this.getClass().getSimpleName();

        try {
            rb = ResourceBundle.getBundle(className);
        } catch (MissingResourceException mre) {
            //log.warn("No resource bundle found for: " + className);
        }
    }

    //~ Methods ================================================================

    /**
     * Utility method to populate a javabean-style object with values
     * from a Properties file
     *
     * @param obj
     * @return
     * @throws Exception
     */
    protected Object populate(Object obj) throws Exception {
        // loop through all the beans methods and set its properties from
        // its .properties file
        Map<String, String> map = ConvertUtil.convertBundleToMap(rb);

        BeanUtils.copyProperties(obj, map);

        return obj;
    }
}
