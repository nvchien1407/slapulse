//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.renewtek.webapp.listener.StartupListener;

/**
 * This class is used to reload the drop-downs initialized in the
 * StartupListener.
 *
 * <p>
 * <a href="ReloadController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class ReloadController implements Controller {

   private final Logger log = LoggerFactory.getLogger(getClass());

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
      if (log.isDebugEnabled()) {
         log.debug("Entering 'execute' method");
      }

      StartupListener.setupContext(request.getSession().getServletContext());

      String referer = request.getHeader("Referer");

      if (referer != null) {
         log.info("reload complete, reloading user back to: " + referer);
         List<String> messages = new ArrayList<String>();
         messages.add("Reloading options completed successfully.");
         request.getSession().setAttribute("messages", messages);
         response.sendRedirect(response.encodeRedirectURL(referer));
         return null;
      }
      else {
         response.setContentType("text/html");

         PrintWriter out = response.getWriter();

         out.println("<html>");
         out.println("<head>");
         out.println("<title>Context Reloaded</title>");
         out.println("</head>");
         out.println("<body bgcolor=\"white\">");
         out.println("<script type=\"text/javascript\">");
         out.println("alert('Context Reload Succeeded! Click OK to continue.');");
         out.println("history.back();");
         out.println("</script>");
         out.println("</body>");
         out.println("</html>");
      }

      return null;
   }

}
