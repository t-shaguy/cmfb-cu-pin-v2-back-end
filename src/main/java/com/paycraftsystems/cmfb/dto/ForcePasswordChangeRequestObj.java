/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

import lombok.ToString;

/**
 *
 * @author paycraftsystems-i
 */

@ToString
public class ForcePasswordChangeRequestObj
{
   public ForcePasswordChangeRequestObj(ForcePasswordChangeRequest rx) {
       
       this.emailAddress = rx.emailAddress();
       this.otp = rx.otp();
       this.password = rx.password();
       this.verifyPassword = rx.verifyPassword();
    
   }
   
 public String emailAddress;
 public String otp;
 public String password;
 public String verifyPassword;
}
