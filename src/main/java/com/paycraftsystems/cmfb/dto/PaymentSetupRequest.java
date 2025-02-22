 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



/**
 *
 * @author paycraftsystems-i
 */

public record PaymentSetupRequest(@NotBlank @Size(min=1, max=100, message="payment description must be valid") String paymentDesc,@NotNull boolean amountFixed,@NotBlank @Size(min=1, max=100, message="payment Label must be valid") String payeeIdLabel, @NotNull boolean taxInc,  double taxAmount, @Min(50) double min_amount,@Min(1) double amount,long actionBy,@NotBlank @Size(min=1, max=100, message="action by  must be valid") @Email String actionByStr,@Min(value=1, message="the beneficiary id must be valid") long beneficiaryId)
{
  
   
}
