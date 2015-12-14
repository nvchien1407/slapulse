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
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Intercepts Login requests Single Sign On.
 * </p>
 *
 *
 * @web.filter name="loginFilter"
 * @web.filter-init-param name="enabled" value="${rememberMe.enabled}"
 */
public final class LoginFilter implements Filter {

   // ~ Instance fields
   // ========================================================

   private final Logger log = LoggerFactory.getLogger(getClass());

   @SuppressWarnings("unused")
   private FilterConfig config = null;
   private boolean enabled = true;

   // ~ Methods
   // ================================================================

   /**
    * @todo Integrate with WebLogic See
    *       http://dev2dev.bea.com/blog/pmalani/archive
    *       /2005/08/weblogic_and_sp.html
    * @todo Integrate with Websphere??
    */
   public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
         ServletException {

      HttpServletRequest request = (HttpServletRequest) req;
      //      HttpServletResponse response = (HttpServletResponse) resp;

      //      WebApplicationContext context = (WebApplicationContext) config
      //            .getServletContext()
      //            .getAttribute(
      //                  WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
      //      UserManager mgr = (UserManager) context.getBean("userManager");

      /* Old Auto Sign-in for FileNet - redundant */
      // if(request.getRemoteUser() == null) {
      // //Attempt auto-logins
      // //1. Try SSO with Workplace
      // //2. Try Remember me Cookie
      //
      // //Looking for Credentials Token from Workplace application
      // String credentialParam = request.getParameter("ut");
      //
      // if( null != credentialParam && enabled) {
      // //Found Credentials Token, trying SSO
      // if (log.isDebugEnabled())
      // log.debug("Detected FileNet Token: ["+credentialParam+"]");
      //
      // String uid = "";
      // String pwd = "";
      //
      // try {
      // com.filenet.wcm.api.Session fnsession =
      // ObjectFactory.getSession("UserToken");
      // HashMap credentialMap = fnsession.fromToken(credentialParam);
      // if (log.isDebugEnabled()) {
      // Iterator i = credentialMap.keySet().iterator();
      // while(i.hasNext()) {
      // String k = i.next().toString();
      // log.debug(k+"="+credentialMap.get(k).toString());
      // }
      // }
      // uid = (String)credentialMap.get(com.filenet.wcm.api.Session.USERID);
      // pwd =
      // (String)credentialMap.get(com.filenet.wcm.api.Session.PASSWORD);
      //
      // // authenticate user without displaying login page
      // String redirectURI = "/authorize?j_username=" + uid +
      // "&j_password=" + pwd;
      // //+ "&j_uri=";
      //
      // request.setAttribute("encrypt", "false");
      // request.getSession(true).setAttribute("FileNetLogin","true");
      //
      // RequestDispatcher dispatcher =
      // request.getRequestDispatcher(redirectURI);
      // dispatcher.forward(request, response);
      //
      // return;
      // } catch (CannotDetokenizeException e) {
      // // Do Nothing - fallthrough to login page
      // e.printStackTrace();
      // }
      // } else {
      // /*
      // * Need to implement UserDAO for this to work.
      // * No requirement at this stage to store cookies in DB.
      //
      // Cookie c = RequestUtil.getCookie(request, Constants.LOGIN_COOKIE);
      // String loginCookie = mgr.checkLoginCookie(c.getValue());
      //
      // if (loginCookie != null) {
      // RequestUtil.setCookie(response, Constants.LOGIN_COOKIE,
      // loginCookie,
      // request.getContextPath());
      // loginCookie = StringUtil.decodeString(loginCookie);
      //
      // String[] value = StringUtils.split(loginCookie, '|');
      //
      // User user = mgr.getUser(value[0]);
      //
      // // authenticate user without displaying login page
      // String route = "/authorize?j_username=" +
      // user.getUsername() + "&j_password=" +
      // user.getPassword();
      //
      // request.setAttribute("encrypt", "false");
      // request.getSession(true).setAttribute("cookieLogin",
      // "true");
      //
      // if (log.isDebugEnabled()) {
      // log.debug("I remember you '" + user.getUsername() +
      // "', attempting to authenticate...");
      // }
      //
      // RequestDispatcher dispatcher =
      // request.getRequestDispatcher(route);
      // dispatcher.forward(request, response);
      //
      // return;
      // }
      // */
      // }
      // }

      if (request.getRemoteUser() != null) {
         if (request.getRequestURI().contains("logout")) {
            // logout request
            if (log.isDebugEnabled())
               log.debug("logging out '" + request.getRemoteUser() + "'");

            // mgr.removeLoginCookies(request.getRemoteUser());
            // RequestUtil.deleteCookie(response,
            // RequestUtil.getCookie(request, Constants.LOGIN_COOKIE),
            // request.getContextPath());
            request.getSession().invalidate();
         }
      }

      chain.doFilter(req, resp);
   }

   /**
    * Initialize controller values of filter.
    */
   public void init(FilterConfig config) {
      this.config = config;

      String param = config.getInitParameter("enabled");
      enabled = Boolean.valueOf(param);
      if (log.isDebugEnabled()) {
         log.debug("Remember Me enabled: " + enabled);

      }

      config.getServletContext().setAttribute("rememberMeEnabled", config.getInitParameter("enabled"));
   }

   /**
    * destroy any instance values other than config *
    */
   public void destroy() {
   }
}
