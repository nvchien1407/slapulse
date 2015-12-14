// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao;

import java.util.Calendar;
import java.util.List;

import com.renewtek.model.CalendarDays;
import com.renewtek.model.Reference;

public class CalendarDAOTest extends BaseDAOTestCase {

   private CalendarDAO calendarDAO = null;

   private ReferenceDAO referenceDAO = null;

   public CalendarDAOTest() {
      referenceDAO = (ReferenceDAO)ctx.getBean("referenceDAO");
      calendarDAO = (CalendarDAO)ctx.getBean("calendarDAO");
   }

   public void testGetCalendarDays() throws Exception {
      // setup
      List<Reference> regions = referenceDAO.getReferences("Calendar");
      Reference region = regions.get(0);
      Calendar cal = Calendar.getInstance();
      
      CalendarDays days = calendarDAO.getCalendarDays(cal, region);
      assertNotNull(days);
      assertEquals("Victoria", days.getRegion().getItemName());
      assertEquals("Calendar", days.getRegion().getSubGroupName());
   }

}
