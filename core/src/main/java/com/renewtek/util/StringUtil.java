//********* DO NOT REMOVE THIS NOTICE **********
//COPYRIGHT�2006 RENEWTEK PTY LTD
//********* DO NOT REMOVE THIS NOTICE **********

package com.renewtek.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * String Utility Class This is used to encode passwords programmatically
 * <p/>
 * <p>
 * <a h ref="StringUtil.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class StringUtil {

   // ~ Static fields/initializers =============================================

   private final static Logger log = LoggerFactory.getLogger(StringUtil.class);

   private static final String regex = ",";

   // ~ Methods ================================================================

   /**
    * Encode a string using algorithm specified in web.xml and return the
    * resulting encrypted password. If exception, the plain credentials string
    * is returned
    * 
    * @param password
    *           Password or other credentials to use in authenticating this
    *           username
    * @param algorithm
    *           Algorithm used to do the digest
    * @return encypted password based on the algorithm.
    */
   public static String encodePassword(String password, String algorithm) {
      byte[] unencodedPassword = password.getBytes();

      MessageDigest md;

      try {
         // first create an instance, given the provider
         md = MessageDigest.getInstance(algorithm);
      } catch (Exception e) {
         log.error("Exception: " + e);

         return password;
      }

      md.reset();

      // call the update method one or more times
      // (useful when you don't know the size of your data, eg. stream)
      md.update(unencodedPassword);

      // now calculate the hash
      byte[] encodedPassword = md.digest();

      StringBuffer buf = new StringBuffer();

      for (byte anEncodedPassword : encodedPassword) {
         if ((anEncodedPassword & 0xff) < 0x10) {
            buf.append("0");
         }

         buf.append(Long.toString(anEncodedPassword & 0xff, 16));
      }

      return buf.toString();
   }

   /**
    * Encode a string using Base64 encoding. Used when storing passwords as
    * cookies.
    * <p/>
    * This is weak encoding in that anyone can use the decodeString routine to
    * reverse the encoding.
    * 
    * @param str
    * @return String
    */
   public static String encodeString(String str) {
      //sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
      //return encoder.encodeBuffer(str.getBytes()).trim();
      return Base64.encodeBase64String(str.getBytes()).trim();
   }

   /**
    * Decode a string using Base64 encoding.
    * 
    * @param str
    * @return String
    */
   public static String decodeString(String str) {
      //sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
      //try {
         //return new String(dec.decodeBuffer(str));
      //} catch (IOException io) {
         //throw new RuntimeException(io.getMessage(), io.getCause());
      //}
      return new String(Base64.decodeBase64(str));
   }

   public static Integer purifyStringIdFromWeb(String id) {
      log.debug("Replacing the id " + id + " with regex " + regex);
      String temp = id.replaceAll(regex, "");
      return new Integer(temp);
   }
}
