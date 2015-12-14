package com.renewtek.webapp.controller;

import com.fasterxml.jackson.module.hibernate.HibernateModule;
import com.fasterxml.jackson.xml.XmlMapper;

/**
 * Used to enable jackson-module-hibernate.
 * Using this will make sure that jackson will not try and lazy-load entities and lists if they
 * are not already loaded. 
 * @author jerryshea
 */
public class HibernateAwareXmlMapper extends XmlMapper {
  public HibernateAwareXmlMapper() {
     // HibernateModule by default will not lazy-load entities and return null for them
     HibernateModule hm = new HibernateModule();
     registerModule(hm);
  }
}
