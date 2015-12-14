//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********


package com.renewtek.dao;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.renewtek.model.DefaultWeek;
import com.renewtek.model.DefaultWeekDay;
import com.renewtek.model.Reference;

public class DefaultWeekDAOTest extends BaseDAOTestCase {

   private DefaultWeekDAO defaultWeekDAO = null;
   
   private ReferenceDAO referenceDAO = null;

   public DefaultWeekDAOTest() {
      referenceDAO = (ReferenceDAO)ctx.getBean("referenceDAO");
      defaultWeekDAO = (DefaultWeekDAO)ctx.getBean("defaultWeekDAO");
   }

   public void testGetDefaultWeekById() {
      this.startTransaction();
      
      List<Reference> regions = referenceDAO.getReferences("Calendar");
      Reference region = regions.get(0);
      DefaultWeek week = defaultWeekDAO.getDefaultWeek(region);
      assertNotNull(week);
      assertEquals(region.getItemName(), week.getRegion().getItemName());
      
      this.endTransaction();
   }
   
   public void testFindDefaultWeekByRegionAndDate() {
      Reference region = new Reference();
      region.setId(1);
      Calendar cal = new GregorianCalendar(2011, 05, 29);
      DefaultWeekDay day = (DefaultWeekDay) defaultWeekDAO.getDefaultDay(cal.getTime(), region);
      assertNotNull(day);
      assertEquals(1, day.getRegion().getId().intValue());
      assertEquals("Wednesday", day.getDay());
   }
   
   public void testFindDefaultWeekByRegionOnNonWorkingDay() {
      Reference region = new Reference();
      region.setId(1);
      Calendar cal = new GregorianCalendar(2011, 05, 25);
      DefaultWeekDay day = (DefaultWeekDay) defaultWeekDAO.getDefaultDay(cal.getTime(), region);
      assertNotNull(day);
      assertEquals(1, day.getRegion().getId().intValue());
      assertEquals("Saturday", day.getDay());
   }
}
