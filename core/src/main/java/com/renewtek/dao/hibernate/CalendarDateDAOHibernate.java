//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.renewtek.dao.CalendarDateDAO;
import com.renewtek.model.CalendarDate;
import com.renewtek.model.DayModel;
import com.renewtek.model.DefaultWeekDay;
import com.renewtek.model.Reference;
import com.renewtek.util.DateUtil;

public class CalendarDateDAOHibernate extends BaseDAOHibernate implements CalendarDateDAO {

   /**
    * @see com.renewtek.dao.CalendarDateDAO#getCalendarDates(com.renewtek.model.CalendarDate)
    */
   @SuppressWarnings("unchecked")
   public List<CalendarDate> getCalendarDates(final CalendarDate calendarDate) {
      if (calendarDate == null) {
         return getHibernateTemplate().find("from CalendarDate");
      }
      else {
         // filter on properties set in the calendarDate
         HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {
               Example ex = Example.create(calendarDate).ignoreCase().enableLike(MatchMode.ANYWHERE);
               return session.createCriteria(CalendarDate.class).add(ex).list();
            }
         };
         return (List<CalendarDate>) getHibernateTemplate().execute(callback);
      }
   }

   /**
    * @see com.renewtek.dao.CalendarDateDAO#getCalendarDate(Integer id)
    */
   public CalendarDate getCalendarDate(final Integer id) {
      CalendarDate calendarDate = (CalendarDate) getHibernateTemplate().get(CalendarDate.class, id);
      if (calendarDate == null) {
         log.warn("uh oh, calendarDate with id '" + id + "' not found...");
         throw new ObjectRetrievalFailureException(CalendarDate.class, id);
      }

      return calendarDate;
   }

   /**
    * @see com.renewtek.dao.CalendarDateDAO#saveCalendarDate(CalendarDate
    *      calendarDate)
    */
   public void saveCalendarDate(final DayModel calendarDate) {
      getHibernateTemplate().saveOrUpdate(calendarDate);
   }

   /**
    * @see com.renewtek.dao.CalendarDateDAO#removeCalendarDate(Integer id)
    */
   public void removeCalendarDate(final Integer id) {
      getHibernateTemplate().delete(getCalendarDate(id));
   }

   @SuppressWarnings("unchecked")
   public List<CalendarDate> getCalendarDates(final Calendar startDate, final Calendar endDate, final Reference region) {
      String timezone = null;
      if (region != null) {
         timezone = region.getTimezone();
      }
      final Date startTimeCalculatedByServer = DateUtil.convertLocalTimeWithSpecifiedTimeZoneToServerTime(startDate, timezone);
      final Date endTimeCalculatedByServer = DateUtil.convertLocalTimeWithSpecifiedTimeZoneToServerTime(endDate, timezone);

      HibernateCallback callback = new HibernateCallback() {

         public Object doInHibernate(Session session) throws HibernateException {
            if (region != null) {
               return session
                     .createQuery(
                           "from CalendarDate as calDate where calDate.dateInfo >= ? and calDate.dateInfo < ? and calDate.region = ?")
                     .setDate(0, startTimeCalculatedByServer).setDate(1, endTimeCalculatedByServer).setParameter(2, region).list();
            }
            else {
               // fix to take care of Oracle DB not understanding null param for region parameter error
               return new ArrayList<CalendarDate>();
            }
         }
      };
      return (List<CalendarDate>) getHibernateTemplate().execute(callback);
   }

   public DayModel getDay(Integer id, String type) {
      if (type.equals("CalendarDate"))
         return (CalendarDate) getHibernateTemplate().get(CalendarDate.class, id);
      if (type.equals("DefaultWeekDay"))
         return (DefaultWeekDay) getHibernateTemplate().get(DefaultWeekDay.class, id);
      if (type.equals("DefaultDay"))
         return (DayModel) getHibernateTemplate().get(DayModel.class, id);
      return null;
   }

   @SuppressWarnings("unchecked")
   public List<DayModel> getDayModelByTemplate(final DayModel day) {
      if (day == null) {
         return getHibernateTemplate().find("from DayModel");
      }
      else {
         // filter on properties set in the calendarDate
         HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {
               Example ex = Example.create(day).ignoreCase().enableLike(MatchMode.ANYWHERE);
               Criteria criteria = session.createCriteria(DayModel.class).add(ex);
               if (day.getRegion() != null) {
                  criteria.createCriteria("region").add(Restrictions.eq("id", day.getRegion().getId()));
               }

               return criteria.list();
            }
         };
         return (List<DayModel>) getHibernateTemplate().execute(callback);
      }
   }

   @SuppressWarnings("unchecked")
   public List<CalendarDate> getCalendarDates(final Reference region) {
      HibernateCallback callback = new HibernateCallback() {

         public Object doInHibernate(Session session) throws HibernateException {
            return session.createQuery("from CalendarDate as calDate where calDate.region = ?").setParameter(0, region)
                  .list();
         }
      };
      return (List<CalendarDate>) getHibernateTemplate().execute(callback);
   }

   @SuppressWarnings("unchecked")
   public List<CalendarDate> getCalendarDatesWithoutTimeZone(final Calendar startDate, final Calendar endDate, final Reference region) {
      HibernateCallback callback = new HibernateCallback() {

         public Object doInHibernate(Session session) throws HibernateException {
            if (region != null) {
               return session
                     .createQuery(
                           "from CalendarDate as calDate where calDate.dateInfo >= ? and calDate.dateInfo < ? and calDate.region = ?")
                     .setDate(0, startDate.getTime()).setDate(1, endDate.getTime()).setParameter(2, region).list();
            }
            else {
               // fix to take care of Oracle DB not understanding null param for region parameter error
               return new ArrayList<CalendarDate>();
            }
         }
      };
      return (List<CalendarDate>) getHibernateTemplate().execute(callback);
   }
}
