// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.service;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Mock;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.renewtek.dao.WorkHourRangeDAO;
import com.renewtek.model.WorkHourRange;
import com.renewtek.service.impl.WorkHourRangeManagerImpl;

public class WorkHourRangeManagerTest extends BaseManagerTestCase {

   private final String workHourRangeId = "1";

   private WorkHourRangeManager workHourRangeManager = new WorkHourRangeManagerImpl();

   private Mock workHourRangeDAO = null;

   private WorkHourRange workHourRange = null;

   protected void setUp() throws Exception {
      super.setUp();
      workHourRangeDAO = new Mock(WorkHourRangeDAO.class);
      workHourRangeManager.setWorkHourRangeDAO((WorkHourRangeDAO) workHourRangeDAO.proxy());
   }

   protected void tearDown() throws Exception {
      super.tearDown();
      workHourRangeManager = null;
   }

   public void testGetWorkHourRanges() throws Exception {
      workHourRange = new WorkHourRange();

      
      List<WorkHourRange> result = new ArrayList<WorkHourRange>();
      workHourRangeDAO.expects(once()).method("getWorkHourRanges").with(same(workHourRange)).will(returnValue(result));

      List<WorkHourRange> actual = workHourRangeManager.getWorkHourRanges(workHourRange);

      workHourRangeDAO.verify();

      assertNotNull(actual);
   }

   public void testGetWorkHourRange() throws Exception {
      // set expected behavior on dao
      workHourRangeDAO.expects(once()).method("getWorkHourRange").will(returnValue(new WorkHourRange()));
      workHourRange = workHourRangeManager.getWorkHourRange(workHourRangeId);
      assertTrue(workHourRange != null);
      workHourRangeDAO.verify();
   }

   public void testSaveWorkHourRange() throws Exception {
      // set expected behavior on dao
      workHourRangeDAO.expects(once()).method("saveWorkHourRange").with(same(workHourRange)).isVoid();

      workHourRangeManager.saveWorkHourRange(workHourRange);
      workHourRangeDAO.verify();
   }

   public void testAddAndRemoveWorkHourRange() throws Exception {
      workHourRange = new WorkHourRange();

      // set required fields

      // set expected behavior on dao
      workHourRangeDAO.expects(once()).method("saveWorkHourRange").with(same(workHourRange)).isVoid();
      workHourRangeManager.saveWorkHourRange(workHourRange);
      workHourRangeDAO.verify();

      // reset expectations
      workHourRangeDAO.reset();

      workHourRangeDAO.expects(once()).method("removeWorkHourRange").with(eq(new Integer(workHourRangeId)));
      workHourRangeManager.removeWorkHourRange(workHourRangeId);
      workHourRangeDAO.verify();

      // reset expectations
      workHourRangeDAO.reset();
      // remove
      Exception ex = new ObjectRetrievalFailureException(WorkHourRange.class, workHourRange.getId());
      workHourRangeDAO.expects(once()).method("removeWorkHourRange").isVoid();
      workHourRangeDAO.expects(once()).method("getWorkHourRange").will(throwException(ex));
      workHourRangeManager.removeWorkHourRange(workHourRangeId);
      try {
         workHourRangeManager.getWorkHourRange(workHourRangeId);
         fail("WorkHourRange with identifier '" + workHourRangeId + "' found in database");
      }
      catch (ObjectRetrievalFailureException e) {
         assertNotNull(e.getMessage());
      }
      workHourRangeDAO.verify();
   }
}
