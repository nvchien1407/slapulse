package com.renewtek.service;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Mock;

import com.renewtek.dao.DayModelDAO;
import com.renewtek.model.DayModel;
import com.renewtek.service.impl.DayModelManagerImpl;

public class DayModelManagerTest extends BaseManagerTestCase {

   private DayModelManager objectUnderTest;

   private Mock mockDayModelDAO;

   protected void setUp() throws Exception {
      objectUnderTest = new DayModelManagerImpl();
      mockDayModelDAO = new Mock(DayModelDAO.class);

      objectUnderTest.setDayModelDAO((DayModelDAO) mockDayModelDAO.proxy());
   }

   public void testGetDayModel() throws Exception {
      List<DayModel> dayModels = new ArrayList<DayModel>();
      DayModel dayModel = new DayModel();

      mockDayModelDAO.expects(once()).method("getDayModels").with(isA(DayModel.class)).will(returnValue(dayModels));

      dayModels = objectUnderTest.getDayModels(dayModel);

      mockDayModelDAO.verify();
      assertNotNull(dayModel);
   }

   public void testGetDayModelUsingId() throws Exception {
      DayModel result = new DayModel();
      mockDayModelDAO.expects(once()).method("getDayModel").with(eq(1234)).will(returnValue(result));
      DayModel actual = objectUnderTest.getDayModel("1234");

      mockDayModelDAO.verify();
      assertNotNull(result);
   }

   public void testSaveDayModel() throws Exception {

      DayModel dayModel = new DayModel();
      mockDayModelDAO.expects(once()).method("saveDayModel").with(isA(DayModel.class));
      objectUnderTest.saveDayModel(dayModel);

      mockDayModelDAO.verify();
   }

   public void testRemoveDayModel() throws Exception {
      DayModel dayModel = new DayModel();
      mockDayModelDAO.expects(once()).method("removeDayModel").with(eq(1234));
      objectUnderTest.removeDayModel("1234");

      mockDayModelDAO.verify();
   }

   public void testGetCalendar() throws Exception {
      List<DayModel> result = new ArrayList<DayModel>();
      DayModel model = new DayModel();
      
      result.add(model);
      
      mockDayModelDAO.expects(once()).method("getDayModels").with(isA(DayModel.class)).will(returnValue(result ));
      
      List<DayModel> actual = objectUnderTest.getCalendars();
      mockDayModelDAO.verify();
      
      assertTrue(actual.size() ==1);
   }

}
