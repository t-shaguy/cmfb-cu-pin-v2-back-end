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

public record PaymentBeneficiaryRequest(@NotBlank @Size(min=1, max=200, message="Beneficiary description cannot be null")String beneficiaryDesc,@NotBlank @Size(min=1, max=200, message=" Beneficiaty name cannot be null")String  beneficiaryName,@NotBlank @Size(min=1, max=200, message="Beneficiary Address cannot be null") String beneficiaryAddress,@NotBlank @Size(min=1, max=200, message="Beneficiary contact person cannot be null")String beneficiaryContactPersonName,@NotBlank @Size(min=1, max=200, message="Beneficiary contact person email cannot be null") @Email String beneficiaryContactPersonEmail,@NotBlank @Size(min=1, max=200, message="Beneficiary contact person mobile cannot be null") String beneficiaryContactPersonMobile,@Min(1) long actionBy,String actionByStr)
{
    
   
    
   
   
}
