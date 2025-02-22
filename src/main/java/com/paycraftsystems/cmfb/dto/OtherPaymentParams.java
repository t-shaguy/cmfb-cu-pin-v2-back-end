/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

import com.paycraftsystems.cmfb.controller.BCrypt;

/**
 *
 * @author paycraftsystems-i
 */
public record OtherPaymentParams(String username, String plainpassword, String transid, String tenantcode, String paymenttype) {
    
    
    
    public String  doPassword() 
    {
     return BCrypt.hashpw(this.plainpassword, BCrypt.gensalt(12));
    }
    
    
    
}
