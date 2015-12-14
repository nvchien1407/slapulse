package com.renewtek.webapp.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XssFilter implements Filter {

   private static final Logger LOG = LoggerFactory.getLogger("XssFilter.class");

   public void destroy() {
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("Invoked XssFilter...");
      }
      XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
         (HttpServletRequest) request);
      chain.doFilter(xssRequest, response);
   }

   public void init(FilterConfig filterConfig) throws ServletException {
   }

}