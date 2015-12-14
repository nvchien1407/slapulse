//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.webapp.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wraps Response for GZipFilter
 *
 * @author  Matt Raible, cmurphy@intechtual.com
 */
public class GZIPResponseWrapper extends HttpServletResponseWrapper {

   private final Logger log = LoggerFactory.getLogger(getClass());

   protected HttpServletResponse origResponse = null;
   protected ServletOutputStream stream = null;
   protected PrintWriter writer = null;
   protected int error = 0;

   public GZIPResponseWrapper(HttpServletResponse response) {
      super(response);
      origResponse = response;
   }

   public ServletOutputStream createOutputStream() throws IOException {
      return (new GZIPResponseStream(origResponse));
   }

   public void finishResponse() {
      try {
         if (writer != null) {
            writer.close();
         }
         else {
            if (stream != null) {
               stream.close();
            }
         }
      }
      catch (IOException e) {
         log.error("Failed to process: ", e);
      }
   }

   public void flushBuffer() throws IOException {
      if (stream != null) {
         stream.flush();
      }
   }

   public ServletOutputStream getOutputStream() throws IOException {
      if (writer != null) {
         throw new IllegalStateException("getWriter() has already been called!");
      }

      if (stream == null) {
         stream = createOutputStream();
      }

      return (stream);
   }

   public PrintWriter getWriter() throws IOException {
      // From cmurphy@intechtual.com to fix:
      // https://appfuse.dev.java.net/issues/show_bug.cgi?id=59
      if (this.origResponse != null && this.origResponse.isCommitted()) {
         return super.getWriter();
      }

      if (writer != null) {
         return (writer);
      }

      if (stream != null) {
         throw new IllegalStateException("getOutputStream() has already been called!");
      }

      stream = createOutputStream();
      writer = new PrintWriter(new OutputStreamWriter(stream, origResponse.getCharacterEncoding()));

      return (writer);
   }

   public void setContentLength(int length) {
   }

   /**
    * @see javax.servlet.http.HttpServletResponse#sendError(int, java.lang.String)
    */
   public void sendError(int error, String message) throws IOException {
      super.sendError(error, message);
      this.error = error;

      if (log.isDebugEnabled()) {
         log.debug("sending error: " + error + " [" + message + "]");
      }
   }
}
