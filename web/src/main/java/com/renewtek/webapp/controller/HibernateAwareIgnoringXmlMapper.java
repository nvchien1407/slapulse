package com.renewtek.webapp.controller;

import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.Module;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.NopAnnotationIntrospector;

/**
 * As Jackson is not smart enough to handle circular references we have to tell it to ignore
 * some properties. We could have done this with Jackson annotations in the model but I feel this
 * is less invasive
 * @author jerryshea
 */
public class HibernateAwareIgnoringXmlMapper extends HibernateAwareXmlMapper {
   private final Map<Class<?>, Set<String>> methodsToIgnore;

   public HibernateAwareIgnoringXmlMapper(Map<Class<?>, Set<String>> methodsToIgnore) {
      super();

      ConfigurableModule cm = new ConfigurableModule();
      registerModule(cm);

      this.methodsToIgnore = methodsToIgnore;
   }

   private final static String NAME = "ConfigurableModule";
   private final static Version VERSION = new Version(0, 1, 0, null);
   private class ConfigurableModule extends Module {
      @Override public String getModuleName() { return NAME; }
      @Override public Version version() { return VERSION; }

      @Override
      public void setupModule(SetupContext context) {
          AnnotationIntrospector ai = new ConfigurableAnnotationIntrospector();
          context.appendAnnotationIntrospector(ai);
      }
   }

   private class ConfigurableAnnotationIntrospector extends NopAnnotationIntrospector {
      String[] EMPTY = new String[0];
      
      @Override
      public String[] findPropertiesToIgnore(AnnotatedClass ac) {
         Class<?> clazz = ac.getAnnotated();
         Set<String> methods = methodsToIgnore.get(clazz);
         if (methods == null) {
            return EMPTY;
         }
         return methods.toArray(EMPTY);
      }
   }
}
