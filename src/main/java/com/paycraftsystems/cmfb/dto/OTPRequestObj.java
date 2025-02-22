/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;

import lombok.ToString;

/**
 *
 * @author root
 */
@ToString
public class OTPRequestObj
{
  
     public OTPRequestObj(OTPRequest rx)
     {
         
            this.otp= rx.otp();
            //this.pin= rx.pin();
           // this.verifyPin= rx.verifyPin();
            this.password= rx.password();
            this.verifyPassword= rx.verifyPassword();
            //this.deviceFingerprint= rx.deviceFingerprint();
            this.emailAddress= rx.emailAddress();
         
     }
     
     public String otp;
     public String mobileNo;
     public String accountNo;
     public String pin;
     public String verifyPin;
     public String password;
     public String verifyPassword;
     public String deviceFingerprint;
     public String profileCode;
     public String emailAddress;

}
