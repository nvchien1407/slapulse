package com.renewtek.service;

import java.util.Date;

import org.jmock.Mock;

import com.renewtek.dao.DefaultWeekDAO;
import com.renewtek.model.DayModel;
import com.renewtek.model.DefaultWeek;
import com.renewtek.model.Reference;
import com.renewtek.service.impl.DefaultWeekManagerImpl;

public class DefaultWeekManagerTest extends BaseManagerTestCase {

   private DefaultWeekManager objectUnderTest;

   private Mock mockDefaultWeekDao;

   public void setUp() throws Exception {
      objectUnderTest = new DefaultWeekManagerImpl();
      mockDefaultWeekDao = new Mock(DefaultWeekDAO.class);
      objectUnderTest.setDefaultWeekDAO((DefaultWeekDAO) mockDefaultWeekDao.proxy());
   }

   public void testGetDefaultWeek() throws Exception {
      DefaultWeek defaultWeek = new DefaultWeek();
      Reference region = new Reference();

      mockDefaultWeekDao.expects(once()).method("getDefaultWeek").with(isA(Reference.class)).will(returnValue(defaultWeek));

      DefaultWeek actual = objectUnderTest.getDefaultWeek(region);
      
      mockDefaultWeekDao.verify();
      assertNotNull(actual);

   }
   
   public void testGetDefaultWeekById() throws Exception {
      DefaultWeek defaultWeek = new DefaultWeek();
      defaultWeek.setRegion(null);
      defaultWeek.setSet(true);
      
      mockDefaultWeekDao.expects(once()).method("getDefaultWeekById").with(eq(123)).will(returnValue(defaultWeek));
      
      DefaultWeek actual = objectUnderTest.getDefaultWeekById("123");
      
      assertNotNull(actual);
      assertTrue(actual.isSet());
   }
   
   public void testSaveDefaultWeek() throws Exception {
      DefaultWeek defaultWeek = new DefaultWeek();
      
      mockDefaultWeekDao.expects(once()).method("saveDefaultWeek").with(isA(DefaultWeek.class));
      objectUnderTest.saveDefaultWeek(defaultWeek);
   }
   
   public void testRemoveDefaultWeek() throws Exception {
      DefaultWeek defaultWeek = new DefaultWeek();
      
      mockDefaultWeekDao.expects(once()).method("removeDefaultWeek").with(eq(1234));
      objectUnderTest.removeDefaultWeek("1234");
   }
   
   public void testGetDayByRegion() {
      Date date = new Date();
      Reference region = new Reference();
      DayModel dayModel = new DayModel();
      
      mockDefaultWeekDao.expects(once()).method("getDefaultDay").with(eq(date), isA(Reference.class)).will(returnValue(dayModel));
      
      DayModel actual = objectUnderTest.getDayByRegion(date, region);
      
   }
   

}
