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
public class MakePaymentRequestObj//(@NotBlank @Size(min=3, max=250, message="the code must be valid ") String code)
{
  
    
   
    
    public MakePaymentRequestObj(MakePaymentRequest rx) {
      
      this.code  = rx.code();
    }
    
    
    
    public String code;
   
}
