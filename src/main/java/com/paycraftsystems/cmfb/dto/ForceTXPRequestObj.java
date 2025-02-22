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
public class ForceTXPRequestObj
{
   public ForceTXPRequestObj(ForceTXPRequest rx) {
       
       this.mobileNo = rx.mobileNo();
    
   }
   
 public String mobileNo;
}
