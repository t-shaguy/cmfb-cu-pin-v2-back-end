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

public record PaymentBeneficiaryEditRequest(@Min(1)long tid, @NotBlank @Size(min=3, max=100, message="beneficiary description must be valid")String beneficiaryDesc,@NotBlank @Size(min=3, max=100, message="beneficiary name must be valid") String beneficiaryName,@NotBlank @Size(min=3, max=240, message="beneficiary address must be valid") String beneficiaryAddress,String beneficiaryContactPersonName,String beneficiaryContactPersonEmail,String beneficiaryContactPersonMobile,String status,long actionBy,String actionByStr)
{
    
   
    
   
   
}
