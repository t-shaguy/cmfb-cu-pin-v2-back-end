 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;



/**
 *
 * @author paycraftsystems-i
 */

public record PaymentResponse(long paymentId,  double amount, long actionBy, String actionByStr,String beneficiaryName,String payeeName,String payeeEmail,String payeeAddress,  String payeeMobileNo, String payeeId, String pin, String receiptNo,  String processorAction)
{
  
   
   
}
