 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



/**
 *
 * @author paycraftsystems-i
 */

public record PaymentSetupEditRequest(@Min(1)long tid,@Min(1)long beneficiaryId,Boolean amountFixed, Boolean taxInc,  double taxAmount, @Min(50) double min_amount,@Min(1)double amount,@Min(1)long actionBy,@NotBlank @Size(min=1, max=100, message="action by str cannot be null") @Email String actionByStr,@NotBlank @Size(min=1, max=100, message="payment description cannot be null") String paymentDesc)
{
  
   
}
