// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import junit.framework.TestCase;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Base class for running DAO tests.
 * 
 * @author mraible
 */
//public abstract class BaseDAOTestCase extends AbstractDependencyInjectionSpringContextTests {
public abstract class BaseDAOTestCase extends TestCase {

   protected final Log log = LogFactory.getLog(getClass());

   protected ResourceBundle rb;

   private static HashMap<String, String> dbUnitDetailMap;

   public static final Logger logger = Logger.getLogger(BaseDAOTestCase.class);
   
   protected SessionFactory sessionFactory = null;
   
   protected Session session;
   
   protected static ApplicationContext ctx;
   
   static {
      ctx = new ClassPathXmlApplicationContext(new String[] {
            "classpath:applicationContext-resources.xml", "classpath:applicationContext-hibernate.xml", });
      logger.info("Init beans and liquidbase");
      setUpDB();
   }
   
   public static void setUpDB() {
      // TODO beware: Another potential pitfall is that some columns may appear
      // not to be inserted. This is almost always because the first row in your
      // FlatXmlDataSet has a missing column. It seems like DbUnit then fails to
      // recognize this column in all other rows

      // Always make sure your first row description contains all the table's
      // columns. If you need to insert a NULL value, make that row the second
      // row,
      logger.info("SET UP test");
      try {
         IDatabaseConnection connection = getConnection();
         IDataSet dataSet = getDataSet();

         try {
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
         }
         finally {
            connection.close();
         }
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }
   
//   @AfterClass
//   public static void tearDownDB() {
//      logger.info("TEAR DOWN test");
//      try {
//         IDatabaseConnection connection = getConnection();
//         IDataSet dataSet = getDataSet();
//         DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
//      }
//      catch (Exception e) {
//         e.printStackTrace();
//      }
//   }

   @SuppressWarnings("unchecked")
   protected static IDatabaseConnection getConnection() throws Exception {
      ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:dbUnitContext.xml");
      dbUnitDetailMap = (HashMap<String, String>) appContext.getBean("dbUnitDetail");

      @SuppressWarnings("unused")
      Class driverClass = Class.forName(dbUnitDetailMap.get("driverClassName"));
      Connection jdbcConnection = DriverManager.getConnection(dbUnitDetailMap.get("url"), dbUnitDetailMap
            .get("username"), dbUnitDetailMap.get("password"));
      return new DatabaseConnection(jdbcConnection);
   }

   protected static IDataSet getDataSet() throws Exception {
      FlatXmlDataSet initialDataSet = new FlatXmlDataSet(BaseDAOTestCase.class.getClassLoader().getResourceAsStream(
            "test-data.xml"));
      ReplacementDataSet finalDataSet = new ReplacementDataSet(initialDataSet);
      finalDataSet.addReplacementObject("[NULL]", null);
      return finalDataSet;
   }

   protected String[] getConfigLocations() {
      // return new String[]{"classpath*:/**/dao/applicationContext-*.xml",
      // "classpath*:META-INF/applicationContext-*.xml"};

      return new String[] { "classpath:applicationContext-hibernate.xml", "classpath:applicationContext-resources.xml" };
   }
   
//   /**
//    * Spring will automatically inject the Hibernate session factory on startup
//    * @param sessionFactory
//    */
//   public void setSessionFactory(SessionFactory sessionFactory) {
//       this.sessionFactory = sessionFactory;
//   }

   /**
    * to be called at the very beginning of a test function if transaction
    * management is needed(lazy loading... session closed
    */
   public void startTransaction() {
      this.sessionFactory = (SessionFactory)ctx.getBean("sessionFactory");
      Session session = SessionFactoryUtils.getSession(this.sessionFactory, true);
      TransactionSynchronizationManager.bindResource(this.sessionFactory, new SessionHolder(session));
   }
   
   /**
    * to be called at the end of the test function if startTransaction was called
    */
   public void endTransaction() {
      this.sessionFactory = (SessionFactory)ctx.getBean("sessionFactory");
      TransactionSynchronizationManager.unbindResource(sessionFactory);
      SessionFactoryUtils.releaseSession(session, sessionFactory);
   }

   public BaseDAOTestCase() {
      // Since a ResourceBundle is not required for each class, just
      // do a simple check to see if one exists
      String className = this.getClass().getName();

      try {
         rb = ResourceBundle.getBundle(className);
      }
      catch (MissingResourceException mre) {
         // log.warn("No resource bundle found for: " + className);
      }
   }

   /**
    * Utility method to populate a javabean-style object with values from a
    * Properties file
    * 
    * @param obj
    * @return
    * @throws Exception
    */
   protected Object populate(Object obj) throws Exception {
      // loop through all the beans methods and set its properties from
      // its .properties file
      Map<String, String> map = new HashMap<String, String>();

      for (Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements();) {
         String key = keys.nextElement();
         map.put(key, rb.getString(key));
      }

      BeanUtils.copyProperties(obj, map);

      return obj;
   }
}
