//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.taglib;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.renewtek.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of <code>TagExtraInfo</code> for the <b>constants</b>
 * tag, identifying the scripting object(s) to be made visible.
 *
 * @author Matt Raible
 * @version $Revision: 1.4 $ $Date: 2004/08/19 00:13:58 $
 */
public class ConstantsTei extends TagExtraInfo {

   private final Logger log = LoggerFactory.getLogger(ConstantsTei.class);

   /**
    * Return information about the scripting variables to be created.
    */
   public VariableInfo[] getVariableInfo(TagData data) {
      // loop through and expose all attributes
      List<VariableInfo> vars = new ArrayList<VariableInfo>();

      try {
         String clazz = data.getAttributeString("className");

         if (clazz == null) {
            clazz = Constants.class.getName();
         }

         Class<?> c = Class.forName(clazz);

         // if no var specified, get all
         if (data.getAttributeString("var") == null) {
            Field[] fields = c.getDeclaredFields();

            AccessibleObject.setAccessible(fields, true);

            for (Field field : fields) {
               vars.add(new VariableInfo(field.getName(), "java.lang.String", true, VariableInfo.AT_END));
            }
         }
         else {
            String var = data.getAttributeString("var");
            vars.add(new VariableInfo(c.getField(var).getName(), "java.lang.String", true, VariableInfo.AT_END));
         }
      }
      catch (Exception ex) {
         log.error("getVariableInfo failed to process: ", ex);
      }

      return vars.toArray(new VariableInfo[] {});
   }
}
