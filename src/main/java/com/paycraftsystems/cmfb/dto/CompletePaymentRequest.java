 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



/**
 *
 * @author paycraftsystems-i
 */

public record CompletePaymentRequest(@Min(1) long paymentId, @Min(1)  double fee, double taxAmount,  @Min(1) double amount, @Min(1) long actionBy,@NotBlank @Size(min=3, max=100, message="the action by str must be valid ") String actionByStr, @NotBlank @Size(min=3, max=100, message="the payment reference must be valid ") String paymentReference)
{
  
    
   
    
    public String doTransDesc() {
      
        
      return null;
    }
    
   
}
