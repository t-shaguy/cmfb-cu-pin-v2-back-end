/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto.response;

//import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 *
 * @author root
 */


public record TransactionLogObj(long tid,String srcAccount, String destAccount, double paymentAmount,double paymentFee,String respCode,long createBy,String createByStr,LocalDateTime createdDate,String customerMail,String customerAddress,String customerMobile,long updatedBy,String updatedByStr, LocalDateTime authDate,long authorizedBy,String authorizedByStr,String requestStr,String responseStr,String transStatus,String cbaStatus,String srcBank,String destBank,String sourceInstitutionCode,String transactionReference,String destBankName,String payerId,String transactionId,String tenantCode,String transTypeStr,String paymentCode,String extResponseCode,String extResponseDesc,double trxFee,String requestId,String exTransId,String transDesc,String paymentReference,String billerId)
{
    
    
    
    
    
    
    
   

}
