 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



/**
 *
 * @author paycraftsystems-i
 */

public record MakePaymentRequest(@NotBlank @Size(min=3, max=250, message="the code must be valid ") String code)
{
  
    
   
    
    public String doTransDesc() {
      
        
      return null;
    }
    
   
}
