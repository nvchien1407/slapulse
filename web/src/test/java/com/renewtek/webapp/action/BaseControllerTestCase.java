// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.renewtek.model.BaseObject;
import com.renewtek.model.User;
import com.renewtek.service.UserManager;
import com.renewtek.util.DateUtil;

public abstract class BaseControllerTestCase extends TestCase {

   public static final String ERROR_KEY_PREFIX = "org.springframework.validation.BindingResult.";

   protected transient final Log log = LogFactory.getLog(getClass());

   protected static XmlWebApplicationContext ctx;

   protected User user;

   protected SessionFactory sessionFactory;

   private Session session;
   // This static block ensures that Spring's BeanFactory is only loaded
   // once for all tests
   static {
      // String pkg = ClassUtils.classPackageAsResourcePath(Constants.class);
      String[] paths = { "classpath*:test-applicationContext-*.xml", "classpath*:test-action-servlet.xml", "classpath*:test-applicationContextWS-hibernate.xml"};

      ctx = new XmlWebApplicationContext();
      ctx.setConfigLocations(paths);
      ctx.setServletContext(new MockServletContext(""));
      ctx.refresh();
   }

   protected void setUp() throws Exception {
      log.debug("\tSETUP for test class " + this.getClass().getName());
      // populate the userForm and place into session
      UserManager userMgr = (UserManager) ctx.getBean("userManager");
      user = (User) userMgr.getUser("tomcat");

      sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");

      session = SessionFactoryUtils.getSession(this.sessionFactory, true);
      TransactionSynchronizationManager.bindResource(this.sessionFactory, new SessionHolder(session));

      // change the port on the mailSender so it doesn't conflict with an
      // existing SMTP server on localhost
      // JavaMailSenderImpl mailSender = (JavaMailSenderImpl)
      // ctx.getBean("mailSender");
      // mailSender.setPort(2525);
      // mailSender.setHost("localhost");
   }

   protected void tearDown() throws Exception {
      log.debug("\tTEAR DOWN for test class " + this.getClass().getName());
      TransactionSynchronizationManager.unbindResource(sessionFactory);
      SessionFactoryUtils.releaseSession(session, sessionFactory);
      // BasicConfigurator.resetConfiguration();
   }

   /**
    * Convenience methods to make tests simpler
    */
   public MockHttpServletRequest newPost(String url) {
      return new MockHttpServletRequest("POST", url);
   }

   public MockHttpServletRequest newGet(String url) {
      return new MockHttpServletRequest("GET", url);
   }

   public void objectToRequestParameters(Object o, MockHttpServletRequest request) throws Exception {
      objectToRequestParameters(o, request, null);
   }

   @SuppressWarnings("unchecked")
   public void objectToRequestParameters(Object o, MockHttpServletRequest request, String prefix) throws Exception {
      Class clazz = o.getClass();
      Field[] fields = getDeclaredFields(clazz);
      AccessibleObject.setAccessible(fields, true);

      for (Field field1 : fields) {
         Object field = (field1.get(o));
         if (field instanceof BaseObject) {
            objectToRequestParameters(field, request, field1.getName());
         }
         else if (!(field instanceof List) && !(field instanceof Set)) {
            String paramName = field1.getName();

            if (prefix != null) {
               paramName = prefix + "." + paramName;
            }

            String paramValue = String.valueOf(field1.get(o));

            // handle Dates
            if (field instanceof Date) {
               paramValue = DateUtil.convertDateToString((Date) field1.get(o));
            }

            request.addParameter(paramName, paramValue);
         }
      }
   }

   @SuppressWarnings("unchecked")
   private Field[] getDeclaredFields(Class clazz) {
      Field[] f = new Field[0];
      Class superClazz = clazz.getSuperclass();
      Collection rval = new ArrayList();

      if (superClazz != null) {
         rval.addAll(Arrays.asList(getDeclaredFields(superClazz)));
      }

      rval.addAll(Arrays.asList(clazz.getDeclaredFields()));
      return (Field[]) rval.toArray(f);
   }
}
