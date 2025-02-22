 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

import com.paycraftsystems.validators.E164PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



/**
 *
 * @author paycraftsystems-i
 */

public record InitPaymentRequest(@Min(1) long paymentId, @Min(1)  double fee, double taxAmount,  @Min(1) double amount, @Min(1) long actionBy,@NotBlank @Size(min=3, max=100, message="the action by str must be valid ") String actionByStr,@NotBlank @Size(min=3, max=100, message="the payee first name must be valid ")String payeeFirstName, @NotBlank @Size(min=3, max=100, message="the payee middle name must be valid ")String payeeMiddleName, @NotBlank @Size(min=3, max=100, message="the payee last name  must be valid ") String payeeLastName, @NotBlank @Size(min=3, max=100, message="the payee email must be valid ")@Email String payeeEmail,  @NotBlank @Size(min=3, max=200, message="the payee address must be valid ")String payeeAddress, @NotBlank @E164PhoneNumber String payeeMobileNo,@NotBlank @Size(min=3, max=100, message="the payee Id must be valid ") String payeeId)
{
  
    
    public String doPayeeName()
    {
        return (this.payeeLastName+" "+this.payeeMiddleName== null?"":this.payeeMiddleName+" "+this.payeeFirstName).toUpperCase();
    }
    
    
    public String doTransDesc() {
      
        
      return null;
    }
    
   
}
