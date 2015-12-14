package com.renewtek.dao.ws;

//import com.renewtek.service.client.CaseTypeVersionDto;
import com.renewtek.dao.SLACaseTypeManagerDAO;
import com.renewtek.dao.ws.client.CaseTypeManagerClient;
import java.util.*;

/**
 * @author hantruong
 *
 */
public class SLACaseTypeManagerWS implements SLACaseTypeManagerDAO{

   private final CaseTypeManagerClient client;

   public SLACaseTypeManagerWS(CaseTypeManagerClient client) {
      this.client = client;
   }

   /*public com.renewtek.service.client.PickListDto findPickListByName(String pickListName)
   {
      return client.findPickListByName(pickListName);
   }
   */
   
}
