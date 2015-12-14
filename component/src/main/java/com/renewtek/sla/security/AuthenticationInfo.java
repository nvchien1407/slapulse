package com.renewtek.sla.security;

//import com.renewtek.security.PasswordEncoderDecoder;

public final class AuthenticationInfo {

   private String userName;

   private String encryptedPassword;

   private String decryptedPassword;

   /*public AuthenticationInfo(String userName, String encryptedPassword, PasswordEncoderDecoder pbePasswordEncoder) {
      this.userName = userName;
      this.encryptedPassword = encryptedPassword;
      this.decryptedPassword = pbePasswordEncoder.decodePassword(this.encryptedPassword, this.userName);
   }*/

   public String getUserName() {
      return this.userName;
   }

   public String getEncryptedPassword() {
      return this.encryptedPassword;
   }

   public String getDecryptedPassword() {
      return this.decryptedPassword;
   }

}
