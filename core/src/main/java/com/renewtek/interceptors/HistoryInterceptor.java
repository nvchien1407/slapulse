// ********* DO NOT REMOVE THIS NOTICE **********
// COPYRIGHT�2006 RENEWTEK PTY LTD
// ********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.interceptors;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.CallbackException;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.renewtek.model.Auditable;
import com.renewtek.model.ChangeLog;

/**
 * @author Djail Turgumbaev, Jerry Shea updated for later Hibernate
 */
public class HistoryInterceptor implements Interceptor, ApplicationContextAware {

   protected static final Log log = LogFactory.getLog(HistoryInterceptor.class);

   private SessionFactory sessionFactory;

   private ApplicationContext applicationContext;

   // we write out a newline between each field that has been changed
   public static String NEWLINE = System.getProperty("line.separator");

   public static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

   private static final String UPDATE = "update";

   private static final String INSERT = "insert";

   private static final String DELETE = "delete";

   private static final ThreadLocal<HistoryHolder> historyThread = new ThreadLocal<HistoryHolder>() {
      @Override
      protected HistoryHolder initialValue() {
         return new HistoryHolder();
      }
   };

   public String getUserName() {
      return historyThread.get().userName;
   }

   public static void setUserName(String userName) {
      historyThread.get().userName = userName;
   }

   /**
    * Create a new Interceptor
    */
   public HistoryInterceptor() {
   }

   /**
    * @see Interceptor#onLoad(java.lang.Object, java.io.Serializable,
    *      java.lang.Object[], java.lang.String[], net.sf.hibernate.type.Type[])
    */
   public boolean onLoad(Object obj, Serializable id, Object[] values, String[] properties, Type[] types)
         throws CallbackException {
      return false;
   }

   /**
    * Record the changes in the HashMap. Unfortunately, these changes can't be
    * done immediately (TransientObjectException), so they are recorded in a
    * separate Set.
    * 
    * @see Interceptor#onFlushDirty(java.lang.Object, java.io.Serializable,
    *      java.lang.Object[], java.lang.Object[], java.lang.String[],
    *      net.sf.hibernate.type.Type[])
    */
   public boolean onFlushDirty(Object obj, Serializable id, Object[] newValues, Object[] oldValues,
         String[] properties, Type[] types) throws CallbackException {
      if (obj instanceof Auditable) {
         SessionFactory factory = this.obtainSessionFactory();
         Session session = factory.openSession();
         String className = getClassName(obj);

         // Use the id and class to get the pre-update state from the
         // database
         Serializable persistedObjectId = getObjectId(obj);
         Object preUpdateState = session.get(obj.getClass(), persistedObjectId);
         try {
            log.debug("enter the onFlushDirty");
            logChanges(obj, preUpdateState, persistedObjectId.toString(), UPDATE, className);
         }
         catch (IllegalArgumentException e) {
            throw new CallbackException(e);
         }
         catch (IllegalAccessException e) {
            throw new CallbackException(e);
         }
         catch (InvocationTargetException e) {
            throw new CallbackException(e);
         }
         finally {
            session.close();
         }
      }
      return false;
   }

   /**
    * Record the creation of the object.
    * 
    * @see net.sf.hibernate.Interceptor#onSave(java.lang.Object,
    *      java.io.Serializable, java.lang.Object[], java.lang.String[],
    *      net.sf.hibernate.type.Type[])
    */
   public boolean onSave(Object obj, Serializable id, Object[] newValues, String[] properties, Type[] types)
         throws CallbackException {
      if (obj instanceof Auditable) {
         String className = getClassName(obj);

         try {
            logChanges(obj, null, null, INSERT, className);
         }
         catch (IllegalArgumentException e) {
            throw new CallbackException(e);
         }
         catch (IllegalAccessException e) {
            throw new CallbackException(e);
         }
         catch (InvocationTargetException e) {
            throw new CallbackException(e);
         }
      }
      return false;
   }

   /**
    * @param obj
    * @return
    */
   private String getClassName(Object obj) {
      Class<?> objectClass = obj.getClass();
      String className = objectClass.getName();
      String[] tokens = className.split("\\.");
      int lastToken = tokens.length - 1;
      className = tokens[lastToken];
      return className;
   }

   public void onDelete(Object obj, Serializable id, Object[] newValues, String[] properties, Type[] types)
         throws CallbackException {
      if (obj instanceof Auditable) {
         String className = getClassName(obj);

         try {
            logChanges(obj, null, id.toString(), DELETE, className);
         }
         catch (IllegalArgumentException e) {
            throw new CallbackException(e);
         }
         catch (IllegalAccessException e) {
            throw new CallbackException(e);
         }
         catch (InvocationTargetException e) {
            throw new CallbackException(e);
         }
      }
   }

   public void preFlush(@SuppressWarnings("rawtypes") Iterator it) throws CallbackException {
   }

   public void postFlush(@SuppressWarnings("rawtypes") Iterator iterator) throws CallbackException {
      HistoryHolder hh = historyThread.get();
      if (hh.inserts.size() == 0 && hh.updates.size() == 0 && hh.deletes.size() == 0)
         return;

      SessionFactory factory = this.obtainSessionFactory();
      Session session = factory.openSession();
      log.debug("updates size:" + hh.updates.size());
      try {
         for (ChangeLog logRecord : hh.inserts) {
            logRecord.setEntityId(getObjectId(logRecord.getEntity()).toString());
            log.debug(logRecord);
            session.saveOrUpdate(logRecord);
         }
         for (ChangeLog logRecord : hh.updates) {
            log.debug(logRecord);
            session.saveOrUpdate(logRecord);
         }
         for (ChangeLog logRecord : hh.deletes) {
            log.debug(logRecord);
            session.saveOrUpdate(logRecord);
         }

      }
      catch (HibernateException e) {
         throw new CallbackException(e);
      }
      finally {
         hh.inserts.clear();
         hh.updates.clear();
         hh.deletes.clear();
         session.flush();
         session.close();
      }
   }

   public Boolean isUnsaved(Object arg0) {
      return null;
   }

   public int[] findDirty(Object obj, Serializable id, Object[] newValues, Object[] oldValues, String[] properties,
         Type[] types) {
      return null;
   }

   public Object instantiate(Class<?> arg0, Serializable arg1) throws CallbackException {
      return null;
   }

   public void afterTransactionBegin(Transaction tx) {
   }

   public Object instantiate(String entityName, EntityMode entityMode, Serializable id) throws CallbackException {
      return null;
   }

   public void afterTransactionCompletion(Transaction tx) {
      HistoryHolder hh = historyThread.get();
      hh.inserts.clear();
      hh.updates.clear();
      hh.deletes.clear();
   }

   public Object getEntity(String entityName, Serializable id) throws CallbackException {
      return null;
   }

   public String getEntityName(Object object) throws CallbackException {
      return null;
   }

   public void beforeTransactionCompletion(Transaction tx) {
   }

   public Boolean isTransient(Object entity) {
      return null;
   }

   public SessionFactory obtainSessionFactory() {
      if (this.sessionFactory == null) {
         // this could be called by multiple threads but I don't think it matters
         this.sessionFactory = applicationContext.getBean(SessionFactory.class);
      }
      return sessionFactory;
   }

   /**
    * Logs changes to persistent data
    * 
    * @param newObject
    *           the object being saved, updated or deleted
    * @param existingObject
    *           the existing object in the database. Used only for updates
    * @param persistedObjectId
    *           the id of the persisted object. Used only for update and delete
    * @param event
    *           the type of event being logged. Valid values are "update",
    *           "delete", "save"
    * @param className
    *           the name of the class being logged. Used as a reference in the
    *           auditLogRecord
    * @throws IllegalArgumentException
    * @throws IllegalAccessException
    * @throws InvocationTargetException
    */
   private void logChanges(Object newObject, Object existingObject, String persistedObjectId, String event,
         String className) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
      Class<?> objectClass = newObject.getClass();
      Field[] fields = objectClass.getDeclaredFields(); 
      // @todo: should be public properties not fields?

      ChangeLog logRecord = createChangeLog(event, className, newObject, persistedObjectId);
      boolean changed = false;

      // Iterate through all the fields in the object
      fieldIteration:
      for (Field field : fields) {
         // make private fields accessible so we can access their values
         field.setAccessible(true);
         String fieldName = field.getName();
         // log.debug("fieldName=" + fieldName +" ii="+ii);
         if (!fieldName.equals("id") && !fieldName.equals("hashcodeValue")) {
            if ((field.getModifiers() & Modifier.STATIC) != 0) {
               continue fieldIteration;
            }
            Class<?> interfaces[] = field.getType().getInterfaces();
            for (int i = 0; i < interfaces.length;) {
               if (interfaces[i].getName().equals("java.util.Collection")) {
                  continue fieldIteration;
               }
               i++;
            }

            try {
               Object propertyNewState = field.get(newObject);

               if (event.equals(UPDATE)) {
                  Object propertyPreUpdateState = field.get(existingObject);
                  changed |= addToChanges(logRecord, fieldName, propertyPreUpdateState, propertyNewState);
               }
               else if (event.equals(DELETE)) {
                  changed |= addToChanges(logRecord, fieldName, propertyNewState, null);
               }
               else if (event.equals(INSERT)) {
                  changed |= addToChanges(logRecord, fieldName, null, propertyNewState);
               }
            }
            catch (Exception e) {
               log.debug("Problem getting field value", e);
            }
         }
      }

      if (changed) {
         HistoryHolder hh = historyThread.get();
         if (event.equals(UPDATE)) {
            unDuplicate(hh.updates, logRecord);
         }
         else if (event.equals(DELETE)) {
            unDuplicate(hh.deletes, logRecord);
         }
         else if (event.equals(INSERT)) {
            unDuplicate(hh.inserts, logRecord);
         }
      }
   }

   /**
    * If the new changelog exists in the updates deletes or inserts ArrayList
    * already, it's ignored. Otherwise, add the new changelog to updates,deletes
    * or inserts ArrayList, and add it to the changeLogs buffer that will be
    * used to construct email message in postFlust method.
    * 
    * @param buff
    *           refer to updates deletes or inserts ArrayList
    * @param logRecord
    *           the new ChangeLog object
    */
   private void unDuplicate(List<ChangeLog> buff, ChangeLog logRecord) {
      ChangeLog lastChangeLog;
      if (buff.size() > 0) {
         lastChangeLog = buff.get(buff.size() - 1);
      }
      else {
         lastChangeLog = new ChangeLog();
      }
      if (!logRecord.toStringValue().equals(lastChangeLog.toStringValue())) {

         ChangeLog aChangeLog = new ChangeLog();
         aChangeLog.setUserId(logRecord.getUserId());
         aChangeLog.setTableName(logRecord.getTableName());
         aChangeLog.setOperateType(logRecord.getOperateType());
         aChangeLog.setOperateTime(logRecord.getOperateTime());
         aChangeLog.setId(logRecord.getId());
         if (logRecord.getEntityId() != null) {
            aChangeLog.setEntityId(logRecord.getEntityId());
         }
         aChangeLog.setChanges(logRecord.getChanges());
         // add to changeLogs to build email message
         HistoryHolder hh = historyThread.get();
         hh.changeLogs.add(aChangeLog);

         // add to list buffer to save to database
         buff.add(logRecord);
      }
   }

   /**
    * @param logRecord
    * @param fieldName
    */
   private boolean addToChanges(ChangeLog logRecord, String fieldName, Object oldValue, Object newValue) {
      if (newValue == null && oldValue == null) {
         return false;
      }

      newValue = getEntityIdOrStringValue(newValue);
      oldValue = getEntityIdOrStringValue(oldValue);

      if (newValue != null && newValue.equals(oldValue)) {
         return false; // Values haven't changed so loop to next property
      }

      String message = fieldName + ": ";
      if (oldValue != null) {
         message += oldValue.toString();
      }
      if (newValue != null) {
         if (oldValue != null) {
            message += " -> ";
         }
         message += newValue.toString();
      }
      message += NEWLINE;

      logRecord.setChanges(logRecord.getChanges() + message);
      return true;
   }

   // if the field is entity object (instance of auditable) return Id of the
   // object;
   // if the field is of type Date return long value of it;
   // otherwise return string vaue of the object
   public String getEntityIdOrStringValue(Object obj) {
      if (obj == null)
         return null;

      if (!(obj instanceof Auditable)) {
         if (obj instanceof Date) {
            return DATE_FORMAT.format((Date) obj);
         }
         return obj.toString();
      }

      Serializable id = getObjectId(obj);
      return id == null ? "" : id.toString();
   }

   private ChangeLog createChangeLog(String event, String className, Object newObject, String persistedObjectId) {

      ChangeLog logRecord = new ChangeLog();
      logRecord.setTableName(className);

      logRecord.setChanges("");

      logRecord.setOperateType(event);
      logRecord.setUserId(this.getUserName());
      logRecord.setOperateTime(new Date());

      if (persistedObjectId != null)
         logRecord.setEntityId(persistedObjectId);
      logRecord.setEntity(newObject);

      return logRecord;
   }

   /**
    * Gets the id of the persisted object
    * 
    * @param obj
    *           the object to get the id from
    * @return object Id
    */
   private Serializable getObjectId(Object obj) {

      Class<?> objectClass = obj.getClass();
      Method[] methods = objectClass.getMethods();

      Serializable persistedObjectId = null;
      for (Method method : methods) {
         // If the method name equals 'getId' then invoke it to get the id of
         // the object.
         if (method.getName().equals("getId")) {
            try {
               persistedObjectId = (Serializable) method.invoke(obj, (Object[]) null);
               break;
            }
            catch (Exception e) {
               log.warn("Audit Log - Could not get persisted object id: ", e);
            }
         }
      }
      return persistedObjectId;
   }
   public void onCollectionRecreate(Object arg0, Serializable arg1) throws CallbackException {
   }

   public void onCollectionRemove(Object arg0, Serializable arg1) throws CallbackException {
   }

   public void onCollectionUpdate(Object arg0, Serializable arg1) throws CallbackException {
   }

   public String onPrepareStatement(String sql) {
      return sql;
   }

   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      this.applicationContext = applicationContext;
   }
   
   static class HistoryHolder {
      public final List<ChangeLog> deletes = new ArrayList<ChangeLog>();

      public final List<ChangeLog> inserts = new ArrayList<ChangeLog>();

      public final List<ChangeLog> updates = new ArrayList<ChangeLog>();

      public final List<ChangeLog> changeLogs = new ArrayList<ChangeLog>();

      public String userName = "xxxxxxx";
   }
}
