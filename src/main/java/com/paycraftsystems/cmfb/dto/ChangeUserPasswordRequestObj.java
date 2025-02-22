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
public class ChangeUserPasswordRequestObj
{

  public ChangeUserPasswordRequestObj(ChangeUserPasswordRequest rx) 
  {
        this.pid= rx.pid();
        this.code= rx.code();
        this.codeLink= rx.codeLink();
        this.password= rx.password();
        this.verifyPassword= rx.verifyPassword();
        this.newPassword= rx.newPassword();
        this.channel= rx.channel();
  

  }


    public long pid;
    public String code;
    public String codeLink;
    public String password;
    public String verifyPassword;
    public String newPassword;
    public String channel; 

             
    
}
