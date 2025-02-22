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
public class UserLoginRequestV2Obj
{
      
   public UserLoginRequestV2Obj(UserLoginRequestV2 rx)
   {
        this.code = rx.code();
        this.codeLink = rx.codeLink();
        this.password = rx.password();
        this.channel = rx.channel();
        this.pid = rx.pid();
   }


public String code;
public String codeLink;
public String password;
public String channel;
public long pid;
    
}
