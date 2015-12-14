package com.renewtek.service.impl;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.renewtek.service.BaseWSClient;
import com.renewtek.service.SLAUpdatePropertiesAdapter;
//import com.renewtek.service.client.CaseManager;
//import com.renewtek.service.client.CaseManagerRemoteLocator;

public class SLAUpdatePropertiesWS implements SLAUpdatePropertiesAdapter {

   private static final Log log = LogFactory.getLog(SLAUpdatePropertiesWS.class);
   private BaseWSClient wsClient;
   //private CaseManager wsCaseType;

   public SLAUpdatePropertiesWS(BaseWSClient wsClient) {
      this.wsClient = wsClient;
      //this.wsCaseType = createManager();
   }

   public void lockUpdateCasePropertyAndUnlock(long caseId, String propertyName, String propValue) {
      try {
         //wsCaseType = createCaseManager();
         //wsCaseType.lockUpdateCasePropertyAndUnlock(caseId, propertyName, propValue);
      } catch (Exception e) {
         throw new RuntimeException("Problem calling web service", e);
      }
   }
   public void lockUpdateCasePropertyAndUnlockAndNotify(long caseId, String propertyName, String propValue, String fromEmail, String toEmail, String emailTemplate)
   {
      try {
         //wsCaseType = createCaseManager();
         //wsCaseType.lockUpdateCasePropertyAndUnlockAndNotify(caseId, new String[]{propertyName}, new String[]{propValue},fromEmail, toEmail, emailTemplate);
      } catch (Exception e) {
         throw new RuntimeException("Problem calling web service to update the case", e);
      }
      
      
   }
   /*public com.renewtek.service.client.CaseDto findCaseById(Long caseId)
   {
      try {
         wsCaseType = createCaseManager();
         return wsCaseType.findCaseById(caseId);
      } catch (Exception e) {
         throw new RuntimeException("Fail to findCaseById", e);
      }
   }*/
  /* private CaseManager createCaseManager() {
      if (!wsClient.isCacheManager()) {
         wsCaseType = createManager();
      }
      return wsCaseType;
   }*/

   public void lockUpdateCasePropertyAndUnlockMultiple(long caseId, String [] propertyNames, String [] propValues) {
      try {
         //wsCaseType = createCaseManager();
         //wsCaseType.lockUpdateCasePropertyAndUnlockMultiple(caseId, propertyNames, propValues);
      } catch (Exception e) {
         throw new RuntimeException("Problem calling web service", e);
      }
   }

   /*private CaseManager createManager() {
      log.debug("CaseManagerClient initializing");
      CaseManagerRemoteLocator locator = new CaseManagerRemoteLocator();
      try {
         wsCaseType = createManager(locator);
      } catch (RuntimeException e) {
         throw new RuntimeException("Failed to initialize CaseManagerClient.", e);
      } catch (Throwable e) {
         throw new RuntimeException("Failed to initialize CaseManagerClient.", e);
      }

      log.debug("CaseManagerClient has been initialized");

      return wsCaseType;
   }*/

   /*private CaseManager createManager(CaseManagerRemoteLocator locator) throws ServiceException {
      String serviceUrl = locator.getCaseManagerRemotingImplPortAddress();
      if (wsClient.getWsBaseUrl() != null) {
         serviceUrl = wsClient.getWsBaseUrl() + locator.getServiceName().getLocalPart();
      }
      log.debug("CaseManagerClient serviceUrl=" + serviceUrl);

      URL validUrl = null;
      try {
         validUrl = new URL(serviceUrl);
      } catch (MalformedURLException e) {
         log.error("Invalid URL '" + serviceUrl + "' . Trying default service now...");
         return locator.getCaseManagerRemotingImplPort();
      }

      CaseManager wsCaseManager = locator.getCaseManagerRemotingImplPort(validUrl);
      log.info("Found  CaseManager service at '" + validUrl.toString() + "'.");

      wsClient.injectSOAPHeader((Stub) wsCaseManager);

      return wsCaseManager;
   }*/

   public BaseWSClient getWsClient() {
      return wsClient;
   }

   public void setWsClient(BaseWSClient wsClient) {
      this.wsClient = wsClient;
   }

  /* public CaseManager getCaseManager() {
      return this.wsCaseType;
   }*/

}
