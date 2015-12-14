package com.renewtek.webapp.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

   private static final Logger LOG = LoggerFactory.getLogger("XssHttpServletRequestWrapper.class");

   HttpServletRequest orgRequest = null;

   public XssHttpServletRequestWrapper(HttpServletRequest request) {
      super(request);
      orgRequest = request;
   }

   public String getParameter(String name) {
      if (LOG.isDebugEnabled()) {
         LOG.debug("Invoked getParameter with param: " + name);
      }
      String value = super.getParameter(cleanXss(name));
      if (value != null) {
         value = cleanXss(value);
      }
      return value;
   }

   public String getHeader(String name) {
      if (LOG.isDebugEnabled()) {
         LOG.debug("Invoked getHeader with param: " + name);
      }
      String value = super.getHeader(cleanXss(name));
      if (value != null) {
         value = cleanXss(value);
      }
      return value;
   }

   public String[] getParameterValues(String name) {
      if (LOG.isDebugEnabled()) {
         LOG.debug("Invoked getParameterValues with param: " + name);
      }
      String[] values = super.getParameterValues(name);
      if (values == null) {
         return null;
      }
      int count = values.length;
      String[] encodedValues = new String[count];
      for (int i = 0; i < count; i++) {
         encodedValues[i] = cleanXss(values[i]);
      }
      return encodedValues;
   }

   private static String cleanXss(String s) {
      if (LOG.isDebugEnabled()) {
         LOG.debug("Invoked cleanXss value: " + s);
      }
      if (StringUtils.isEmpty(s)) {
         return s;
      }
      String value = s;
      value = value.replaceAll("<", "?").replaceAll(">", "?");
      value = value.replaceAll("\\(", "?").replaceAll("\\)", "?");
      value = value.replaceAll("'", "?");
      //DE2316
      value = value.replaceAll("\"", "?");
      value = value.replaceAll("&", "?");
      value = value.replaceAll("eval\\((.*)\\)", "");
      value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
      value = value.replaceAll("(?<![a-zA-z])script(?![a-zA-z])|javascript", "");
      if (LOG.isDebugEnabled()) {
         LOG.debug("xssEncode returned value: " + value);
      }
      return value;
   }

   public HttpServletRequest getOrgRequest() {
      return orgRequest;
   }

   public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
      if (req instanceof XssHttpServletRequestWrapper) {
         return ((XssHttpServletRequestWrapper) req).getOrgRequest();
      }
      return req;
   }

}
