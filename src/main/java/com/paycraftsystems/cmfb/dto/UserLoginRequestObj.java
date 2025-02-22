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
public class UserLoginRequestObj
{
    public UserLoginRequestObj(UserLoginRequest rx) {
    
        this.emailAddress = rx.emailAddress();
        this.mobileNo = rx.mobileNo();
        this.password = rx.password();
   
    
    }
    
    
    public String emailAddress;
    public String mobileNo;
    public String password;

}
