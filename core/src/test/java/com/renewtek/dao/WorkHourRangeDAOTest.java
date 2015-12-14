// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.renewtek.model.DayModel;
import com.renewtek.model.WorkHourRange;

public class WorkHourRangeDAOTest extends BaseDAOTestCase {

   private static Integer workHourRangeId = new Integer("10");

   private WorkHourRange workHourRange = null;

   private WorkHourRangeDAO dao = null;
   
   private DayModel dayModel;
   
   public WorkHourRangeDAOTest() {
      dao = (WorkHourRangeDAO)ctx.getBean("workHourRangeDAO");
   }

//   public void setWorkHourRangeDAO(WorkHourRangeDAO dao) {
//      //this.dao = dao;
//      
//      dayModel = new DayModel();
//      dayModel.setId(20);
//   }
   
   private void setUpData() {
      dayModel = new DayModel();
      dayModel.setId(20);
      
      workHourRange = new WorkHourRange();

      // set required fields
      workHourRange.setDay(dayModel);
      workHourRange.setStartHour("09");
      workHourRange.setEndHour("17");
      dao.saveWorkHourRange(workHourRange);
      
      workHourRangeId = workHourRange.getId();
   }

   public void testAddWorkHourRange() throws Exception {
      this.setUpData();

      // verify a primary key was assigned
      assertNotNull(workHourRange.getId());

      // verify set fields are same after save
   }

   public void testGetWorkHourRange() throws Exception {
      this.setUpData();
      //workHourRange = dao.getWorkHourRange(new Integer(1));
      workHourRange = dao.getWorkHourRange(workHourRangeId);
      // DayModel day = workHourRange.getDay();
      // HibernateProxy proxy = (HibernateProxy) day;
      // Class t=proxy.getClass();
      // if(proxy instanceof CalendarDate){
      // String er = "";
      // }
      assertNotNull(workHourRange);
   }

   public void testGetWorkHourRanges() throws Exception {
      workHourRange = new WorkHourRange();
      
      List<WorkHourRange> results = dao.getWorkHourRanges(workHourRange);
      assertTrue(results.size() > 0);
   }

   public void testSaveWorkHourRange() throws Exception {
      this.setUpData();
      
      workHourRange = dao.getWorkHourRange(workHourRangeId);

      // update required fields
      dao.saveWorkHourRange(workHourRange);

   }

   public void testRemoveWorkHourRange() throws Exception {
      dao.removeWorkHourRange(workHourRangeId);
      try {
         dao.getWorkHourRange(workHourRangeId);
         fail("workHourRange found in database");
      }
      catch (ObjectRetrievalFailureException e) {
         assertNotNull(e.getMessage());
      }
   }
}
