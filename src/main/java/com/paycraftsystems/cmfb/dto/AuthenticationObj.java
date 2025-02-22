/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

/**
 *
 * @author paycraftsystems-i
 */
public class AuthenticationObj {
    
   public String token_type;
   public long expires_in;
   public String ext_expires_in;
   public String access_token;

    @Override
    public String toString() {
        return "AuthenticationObj{" + "token_type=" + token_type + ", expires_in=" + expires_in + ", ext_expires_in=" + ext_expires_in + ", access_token=" + access_token + '}';
    }

   
   
    
}
