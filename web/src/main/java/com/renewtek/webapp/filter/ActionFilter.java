//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

import com.renewtek.Constants;
import com.renewtek.interceptors.HistoryInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to filter all requests to the <code>Action</code>
 * servlet and detect if a user is authenticated.  If a user is authenticated,
 * but no user object exists, this class populates the <code>UserForm</code>
 * from the user store.
 *
 * <p><a href="ActionFilter.java.html"><i>View Source</i></a></p>
 *
 * @author  Matt Raible
 * @version $Revision: 1.13 $ $Date: 2005/04/16 22:17:21 $
 *
 * @web.filter name="actionFilter"
 *
 * <p>Change this value to true if you want to secure your entire application.
 * This can also be done in web-security.xml by setting <transport-guarantee>
 * to CONFIDENTIAL.</p>
 *
 * @web.filter-init-param name="isSecure" value="${secure.application}"
 */
public class ActionFilter implements Filter {

   private final Logger log = LoggerFactory.getLogger(getClass());

   @SuppressWarnings("unused")
   private static Boolean secure = Boolean.FALSE;
   private FilterConfig config = null;

   public void init(FilterConfig config) throws ServletException {
      this.config = config;

      /* This determines if the application uconn SSL or not */
      secure = Boolean.valueOf(config.getInitParameter("isSecure"));
   }

   /**
    * Destroys the filter.
    */
   public void destroy() {
      config = null;
   }

   public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
         ServletException {
      // cast to the types I want to use
      HttpServletRequest request = (HttpServletRequest) req;
      HttpServletResponse response = (HttpServletResponse) resp;
      HttpSession session = request.getSession(true);
      Principal principal = request.getUserPrincipal();
      if (principal != null) {
         log.debug("username set:  " + principal.getName());
         HistoryInterceptor.setUserName(principal.getName());
         session.setAttribute("username", principal.getName());
         session.setAttribute("isPowerUser", checkIfPowerGroup(request));
      }
      chain.doFilter(request, response);
   }

   private String checkIfPowerGroup(HttpServletRequest request) {
      String str = config.getServletContext().getInitParameter(Constants.POWER_GROUP);
      if (request.isUserInRole(str))
         return "true";
      return "false";
   }
}
