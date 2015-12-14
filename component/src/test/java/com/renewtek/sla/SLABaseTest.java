package com.renewtek.sla;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.renewtek.sla.operations.SLAOperations;

public class SLABaseTest {

   protected SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");

   protected static SLAOperations operations;

   private static HashMap<String, String> dbUnitDetailMap;

   public static final Logger logger = LoggerFactory.getLogger(SLABaseTest.class);

   @BeforeClass
   public static void setUp() throws Exception {
      operations = new SLAOperations();
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
         logger.error("error: ", e);
         throw e;
      }
   }

   //   @AfterClass
   //   public static void tearDown() {
   //      logger.info("TEAR DOWN test");
   //      System.out.println("TEAR DOWN test");
   //      try {
   //         IDatabaseConnection connection = getConnection();
   //         IDataSet dataSet = getDataSet();
   //         DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
   //      }
   //      catch (Exception e) {
   //         // TODO Auto-generated catch block
   //         e.printStackTrace();
   //      }
   //   }

   @SuppressWarnings("unchecked")
   protected static IDatabaseConnection getConnection() throws Exception {
      ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:dbUnitContext.xml");
      dbUnitDetailMap = (HashMap<String, String>) appContext.getBean("dbUnitDetail");

      @SuppressWarnings("unused")
      Class driverClass = Class.forName(dbUnitDetailMap.get("driverClassName"));
      Connection jdbcConnection = DriverManager.getConnection(dbUnitDetailMap.get("url"),
            dbUnitDetailMap.get("username"), dbUnitDetailMap.get("password"));
      return new DatabaseConnection(jdbcConnection);
   }

   protected static IDataSet getDataSet() throws Exception {
      String dataSet = dbUnitDetailMap.get("dataSet");
      FlatXmlDataSet initialDataSet = new FlatXmlDataSet(SLABaseTest.class.getClassLoader().getResourceAsStream(dataSet));
      ReplacementDataSet finalDataSet = new ReplacementDataSet(initialDataSet);
      finalDataSet.addReplacementObject("[NULL]", null);
      return finalDataSet;
      //return loadedDataSet;
   }

   @Test
   public void dummyTest() {

   }
}
