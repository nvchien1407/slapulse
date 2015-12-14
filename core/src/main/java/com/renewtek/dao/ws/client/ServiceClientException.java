package com.renewtek.dao.ws.client;

public class ServiceClientException extends RuntimeException {

   private static final long serialVersionUID = 7940505552615411923L;

   public ServiceClientException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
