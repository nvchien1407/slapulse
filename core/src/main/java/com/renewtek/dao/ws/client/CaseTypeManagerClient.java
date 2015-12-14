/**
 * 
 */
package com.renewtek.dao.ws.client;



//import com.renewtek.service.client.CaseTypeManagerRemoteLocator;
//import com.renewtek.service.client.CaseTypeVersionDto;
import com.renewtek.service.BaseWSClient;
import com.renewtek.dao.ws.client.CaseTypeManagerClient;
//import com.renewtek.kata.core.dao.PickListDao;
//import com.renewtek.service.client.CaseTypeManager;
import com.renewtek.dao.ws.client.ServiceClientException;



import org.apache.axis.client.Stub;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hantruong
 *
 */
public class CaseTypeManagerClient extends com.renewtek.service.BaseWSClient {

   private static final Log LOG = LogFactory.getLog(CaseTypeManagerClient.class);

   //private CaseTypeManager wsCaseType = null;

   public CaseTypeManagerClient(String user, String pass, String url, boolean cm) {
      super(user, pass, url, cm);
      //createManager();
   }

   /*private void createManager() {
      LOG.debug("CaseTypeManagerClient initializing");
      CaseTypeManagerRemoteLocator locator = new CaseTypeManagerRemoteLocator();
      try {
         wsCaseType = createCaseTypeManager(locator);
      }
      catch (ServiceException e) {
         throw new ServiceClientException("Failed to initialize CaseTypeManagerClient.", e);
      } catch (RuntimeException e) {
         throw new ServiceClientException("Failed to initialize CaseTypeManagerClient.", e);
      }
      catch (Throwable e) {
         throw new ServiceClientException("Failed to initialize CaseTypeManagerClient.", e);
      }

      LOG.debug("CaseTypeManagerClient has been initialized");
   }

   private CaseTypeManager createCaseTypeManager(CaseTypeManagerRemoteLocator locator) throws ServiceException {
      String serviceUrl = locator.getCaseTypeManagerRemotingImplPortAddress();
      if (getWsBaseUrl() != null) {
         serviceUrl = getWsBaseUrl() + locator.getServiceName().getLocalPart();
      }
      LOG.debug("CaseTypeManagerClient serviceUrl=" + serviceUrl);

      URL validUrl = null;
      try {
         validUrl = new URL(serviceUrl);
      }
      catch (MalformedURLException e) {
         LOG.error("Invalid URL '" + serviceUrl + "' . Trying default service now...");
         return locator.getCaseTypeManagerRemotingImplPort();
                        
      }

      CaseTypeManager wsCaseTypeManager = locator.getCaseTypeManagerRemotingImplPort(validUrl);
      LOG.info("Found  CaseTypeManager service at '" + validUrl.toString() + "'.");

      injectSOAPHeader((Stub) wsCaseTypeManager);
      return wsCaseTypeManager;
   }

   public CaseTypeManager getCaseTypeManager() {
      if (! isCacheManager()) {
         createManager();
      }
      return wsCaseType;
   }
   public com.renewtek.service.client.PickListDto findPickListByName(String pickListName)
   {
      try {
         return getCaseTypeManager().findPickListByName(pickListName); 
      } catch (Exception e) {
         throw new ServiceClientException("Fail to find the Pick List", e);
      }
      
   }  */

//   public static void main(String[] args) {
//      if (args.length == 3) {
//         CaseTypeManagerClient c = new CaseTypeManagerClient(args[0], args[1], args[2], true);
//         System.out.println("There are "+c.findPickListByName().size()+" active case type versions.");
//      }
//      else {
//         System.err.println("Usage: to test client, run this with user pass url");
//      }
//   }

}
