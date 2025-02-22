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
public class UserProfileRequestObj{
    
    public UserProfileRequestObj(UserProfileRequest rx)
    {
        
        ///this. otp = rx.otp();
        this. firstName = rx.firstName();
        this. middleName = rx.middleName();
        this. lastName = rx.lastName();
        this. mobileNo = rx.mobileNo();
        this. accountNo = rx.tilAccountNo();
        this. emailAddress = rx.emailAddress();
        this.tilAccount = rx.tilAccountNo();
       // this. newPin = rx.newPin();
        //this. pin = rx.pin();
        //this. verifyPin = rx.verifyPin();
        //this. deviceFingerprint = rx.deviceFingerprint();
        this.userRole = rx.userRole();
        //this.tid = rx.tid();
        
    }
    
    public String tilAccount;
    public String otp;
    public String firstName;
    public String middleName;
    public String lastName;
    public String mobileNo;
    public String accountNo;
    public String emailAddress;
    public String newPin;
    public String pin;
    public String verifyPin;
    public String deviceFingerprint;
    public int userRole;
    public long tid;
  
}
