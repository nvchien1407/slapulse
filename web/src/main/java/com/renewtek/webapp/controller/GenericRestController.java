package com.renewtek.webapp.controller;

import com.renewtek.service.Manager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessor;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * @author jerryshea
 * Generic rest controller which provides basic CRUD for any entity that follows the standard
 * entity/dao/manager convention.
 * To use this you need to subclass it @see BusinessProcessRestController for an example  
 */
public class GenericRestController<T> extends AbstractRestController {
   private static final Log LOG = LogFactory.getLog(GenericRestController.class);

   private final Manager manager;

   public GenericRestController(Manager manager) {
      this.manager = manager;
   }

   /**
    * Get all objects of type T
    * @param expands optional parameters can be used to "fill out" the object's structure and force lazy loading
    * @return
    */
   @RequestMapping(method = RequestMethod.GET)
   @ResponseBody
   public List<T> getAll(@RequestParam(required = false) String[] expands) {
      return processExpands((List<T>) manager.getObjects(getResourceClass()), expands);
   }

   /**
    * Get object by id
    * @param id
    * @param expands optional parameters can be used to "fill out" the object's structure and force lazy loading
    * @return
    */
   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
   @ResponseBody
   public T getById(@PathVariable int id, @RequestParam(required = false) String[] expands) {
      return processExpands((T) manager.getObject(getResourceClass(), id), expands);
   }

   /**
    * Create a new object and return its contents including its new id
    * @param sla
    * @return
    */
   @RequestMapping(method = RequestMethod.POST)
   @ResponseBody
   public T create(@RequestBody T sla) {
      manager.saveObject(sla);
      return sla;
   }

   /**
    * Update the passed object as identified by id
    * @param id
    * @param sla
    * @return
    */
   @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
   @ResponseBody
   public T update(@PathVariable int id, @RequestBody T sla) {
      manager.saveObject(sla);
      return sla;
   }

   /**
    * Does what it says on the tin
    * @param id
    */
   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   @ResponseBody
   public void delete(@PathVariable int id) {
      manager.removeObject(getResourceClass(), id);
   }

   @Override
   protected String getResourceDisplayName() {
      return getResourceClass().getSimpleName();
   }

   private Class<?> getResourceClass() {
      return (Class<?>) getParameterisedType(this.getClass());
   }

   // this method works out what <T> is for this instance
   private Type getParameterisedType(final Class<?> klass) {
      final Type superclass = klass.getGenericSuperclass();
      final Type[] types = ((ParameterizedType) superclass).getActualTypeArguments();
      return types[0];
   }

   /**
    * expands is an optional parameter that can be passed in by REST client. This function gets every
    * property named in the expands array for the passed src object, thus causing hibernate lazy loading to
    * occur if the property has not been already loaded. 
    * @param src
    * @param expands
    * @return
    */
   protected T processExpands(T src, String[] expands) {
      if (expands != null) {
         PropertyAccessor pa = new BeanWrapperImpl(src);
         for (String e : expands) {
            Object o = pa.getPropertyValue(e);
            // the result of the expansion might be a hibernate proxy or a lazy-loaded list. Force it to be loaded.
            if (o instanceof Collection<?>) {
               o = ((Collection<?>) o).size();
            }
            else if (o instanceof HibernateProxy) {
               o = ((HibernateProxy) o).getHibernateLazyInitializer().getImplementation();
            }
            LOG.trace("expanded "+e+" to "+o);
         }
      }
      return src;
   }

   /**
    * calls @see processExpands for each list member
    * @param src
    * @param expands
    * @return
    */
   protected List<T> processExpands(List<T> src, String[] expands) {
      for (T o : src) {
         processExpands(o, expands);
      }
      return src;
   }
}
