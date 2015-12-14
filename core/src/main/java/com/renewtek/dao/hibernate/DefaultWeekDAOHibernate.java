//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.renewtek.dao.DefaultWeekDAO;
import com.renewtek.model.DayModel;
import com.renewtek.model.DefaultWeek;
import com.renewtek.model.DefaultWeekDay;
import com.renewtek.model.Reference;
import com.renewtek.model.WorkHourRange;

public class DefaultWeekDAOHibernate extends BaseDAOHibernate implements DefaultWeekDAO {

   //private CalendarDateDAO calendarDAO;

   public static String[] weekDays = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
   private SimpleDateFormat dayFormat = new SimpleDateFormat("EEEEEE");

   public DayModel getDefaultDay(Reference name) {
      if (name == null || name.getDefaultWeekDays() == null)
         return null;
      //for (Iterator it = name.getDefaultWeekDays().iterator(); it.hasNext();) {
      Set<DayModel> dayModels = name.getDefaultWeekDays();
      for(DayModel day : dayModels) {
         //DayModel day = (DayModel) it.next();
         if (day.getDefaultDay()) {
            return day;
         }
      }

      return null;
   }

   @SuppressWarnings("unchecked")
   public DefaultWeek getDefaultWeek(final Reference region) {
      HibernateCallback callback = new HibernateCallback() {

         public Object doInHibernate(Session session) throws HibernateException {
            return session.createQuery("from DefaultWeekDay day where day.region=?").setParameter(0, region).list();
         }
      };
      DefaultWeek week = new DefaultWeek();
      if (region == null || region.getId() == null) {
         week.setWeekDays(generateWeekDays(region, week.getWeekDays(), null));
      }
      else {
         DayModel defaultDay = this.getDefaultDay(region);
         week.setRegion(region);
         List<DefaultWeekDay> weekDays = (List<DefaultWeekDay>) getHibernateTemplate().execute(callback);
         week.setWeekDays(generateWeekDays(region, weekDays, defaultDay));
         if (week.getWeekDays() == null || week.getWeekDays().size() == 0) {
            week.setSet(false);
         }
         else {
            week.setSet(true);
         }
      }
      return week;
   }

   private List<DefaultWeekDay> generateWeekDays(Reference region, List<DefaultWeekDay> storedDays, DayModel defaultDay) {
      List<DefaultWeekDay> days = new ArrayList<DefaultWeekDay>();
      for (String weekDay : weekDays) {
         boolean found = false;
         DefaultWeekDay d = null;
         if (storedDays != null) {
            for (DefaultWeekDay storedDay : storedDays) {
               d = storedDay;
               if (d.getDay().equals(weekDay)) {
                  found = true;
                  break;
               }
            }
         }
         if (!found) {
            d = new DefaultWeekDay();
            d.setDay(weekDay);
            d.setRegion(region);
            d.setDefaultDay(false);
            d.setNonWorkingDay(false);
            d.setWorkHourRanges(new LinkedHashSet<WorkHourRange>());
            if (defaultDay != null) {
               //for (Iterator it = defaultDay.getWorkHourRanges().iterator(); it.hasNext();) {
               Set<WorkHourRange> workHourRanges = defaultDay.getWorkHourRanges();
               for (WorkHourRange range : workHourRanges) {
                  WorkHourRange r = new WorkHourRange();
                  //WorkHourRange range = (WorkHourRange) it.next();
                  r.setFromTime(range.getFromTime());
                  r.setToTime(range.getToTime());
                  r.setDay(d);
                  d.getWorkHourRanges().add(r);
               }
            }
         }
         days.add(d);
      }
      return days;
   }

   /**
    * @see com.renewtek.dao.DefaultWeekDAO#getDefaultWeek(Integer id)
    */
   @Deprecated
   public DefaultWeek getDefaultWeekById(final Integer id) {
      DefaultWeek defaultWeek = (DefaultWeek) getHibernateTemplate().get(DefaultWeek.class, id);
      if (defaultWeek == null) {
         log.warn("uh oh, defaultWeek with id '" + id + "' not found...");
         throw new ObjectRetrievalFailureException(DefaultWeek.class, id);
      }

      return defaultWeek;
   }

   /**
    * @see com.renewtek.dao.DefaultWeekDAO#saveDefaultWeek(DefaultWeek defaultWeek)
    */
   public void saveDefaultWeek(final DefaultWeek defaultWeek) {
      //for (Iterator it = defaultWeek.getWeekDays().iterator(); it.hasNext();) {
      List<DefaultWeekDay> defaultWeekDays = defaultWeek.getWeekDays();
      for(DefaultWeekDay day : defaultWeekDays) {
         //DefaultWeekDay day = (DefaultWeekDay) it.next();
         getHibernateTemplate().saveOrUpdate(day);
      }
   }

   /**
    * @see com.renewtek.dao.DefaultWeekDAO#removeDefaultWeek(Integer id)
    */
   @Deprecated
   public void removeDefaultWeek(final Integer id) {
      getHibernateTemplate().delete(getDefaultWeekById(id));
   }

   public DefaultWeekDay getDefaultDay(Date date, List<DefaultWeekDay> week) {
      if (week == null)
         return null;
      String dayName = dayFormat.format(date);

      //for (Iterator it = week.iterator(); it.hasNext();) {
      for(DefaultWeekDay df : week) {
         //DefaultWeekDay df = (DefaultWeekDay) it.next();
         if (df.getDay().contains(dayName))
            return df;
      }
      return null;
   }

   @SuppressWarnings("unchecked")
   public DayModel getDefaultDay(final Date date, final Reference region) {
      HibernateCallback callback = new HibernateCallback() {

         public Object doInHibernate(Session session) throws HibernateException {
            String dayName = dayFormat.format(date);
            return session.createQuery("from DefaultWeekDay day where  day.region=? and day.day like ?")
                  .setParameter(0, region).setString(1, dayName).list();
         }
      };
      DayModel d = this.getDefaultDay(region);
      List<DayModel> day = (List<DayModel>) getHibernateTemplate().execute(callback);
      if (day == null || day.size() == 0) {
         return d;
      }
      DayModel weekDay = day.get(0);
      if (!weekDay.getNonWorkingDay()
            && (weekDay.getWorkHourRanges() == null || weekDay.getWorkHourRanges().size() == 0)) {
         List<WorkHourRange> ranges = new ArrayList<WorkHourRange>();
         //for (Iterator it = d.getWorkHourRanges().iterator(); it.hasNext();) {
         for (WorkHourRange range : ranges) {
            WorkHourRange r = new WorkHourRange();
            //WorkHourRange range = (WorkHourRange) it.next();
            r.setFromTime(range.getFromTime());
            r.setToTime(range.getFromTime());
            r.setDay(weekDay);
            ranges.add(r);
         }
         weekDay.setWorkHourRanges(new LinkedHashSet<WorkHourRange>(ranges));
      }
      return weekDay;
   }

}
