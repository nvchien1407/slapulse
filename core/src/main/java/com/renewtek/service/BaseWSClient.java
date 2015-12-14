package com.renewtek.service;

import javax.xml.soap.SOAPException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.PrefixedQName;
import org.apache.axis.message.SOAPHeaderElement;

/**
 * @author: hanhluu
 * */

public class BaseWSClient {

   private final String username;

   private final String password;

   private final String wsBaseUrl;

   private final boolean cacheManager;

   public BaseWSClient(String user, String pass, String url, boolean cm) {
      username = user;
      password = pass;
      wsBaseUrl = url;
      cacheManager = cm;

      if (wsBaseUrl != null && (!wsBaseUrl.endsWith("/"))) {
         throw new RuntimeException("wsBaseUrl must end with /");
      }
   }

   public BaseWSClient(String user, String pass, String url, String cm) {
      Boolean value = new Boolean(cm);
      username = user;
      password = pass;
      wsBaseUrl = url;
      cacheManager = value.booleanValue();

      if (wsBaseUrl != null && (!wsBaseUrl.endsWith("/"))) {
         throw new RuntimeException("wsBaseUrl must end with /");
      }
   }

   protected String getUsername() {
      return username;
   }

   protected String getPassword() {
      return password;
   }

   public String getWsBaseUrl() {
      return wsBaseUrl;
   }

   public boolean isCacheManager() {
      return cacheManager;
   }

   public void injectSOAPHeader(Stub service) {
      SOAPHeaderElement wsseSecurity = new SOAPHeaderElement(new PrefixedQName(
            "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security", "wsse"));
      wsseSecurity.setActor(null);
      wsseSecurity.setMustUnderstand(true);

      String ns = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
      MessageElement wsseUsernameToken = new MessageElement(ns, "wsse:UsernameToken");
      MessageElement wsseUsername = new MessageElement(ns, "wsse:Username");
      MessageElement wssePassword = new MessageElement(ns, "wsse:Password");

      try {
         wsseUsername.setObjectValue(getUsername());
         wsseUsernameToken.addChild(wsseUsername);
         wssePassword.setAttribute("Type",
               "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
         wssePassword.setObjectValue(getPassword());
         wsseUsernameToken.addChild(wssePassword);
         wsseSecurity.addChild(wsseUsernameToken);

         service.setHeader(wsseSecurity);

      } catch (SOAPException exception) {
         throw new RuntimeException("Exception while setting user name and password for WSSE security in SOAP header",
               exception);
      }
   }

}
