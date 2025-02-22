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
public class SyncPasswordRequestObj
{
    
    public SyncPasswordRequestObj(SyncPasswordRequest rx)
    {
        
        this.code = rx.code();
        this.codeLink= rx.codeLink();
        this.password= rx.password();
        this.verifyPassword= rx.verifyPassword();
        //this.pin= rx.
       // this.verifyPin=
        //this.newPassword= rx.newPassword();
       // this.newPin=
        this.msisdn= rx.msisdn();
        //this.userCode= rx.userCode();
        this.pid= rx.pid();
        this.controlCode= rx.controlCode();
        //this.principal= rx.principal();
        //this.principalControlCode= rx.principalControlCode();
        
    }
    
    public String code;
    public String codeLink;
    public String password;
    public String verifyPassword;
    public String pin;
    public String verifyPin;
    public String newPassword;
    public String newPin;
    public String msisdn;
    public String userCode;
    public long pid;
    public String controlCode;
    public String principal;
    public String principalControlCode;
    
    
}
