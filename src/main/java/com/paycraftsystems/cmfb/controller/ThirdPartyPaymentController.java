/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.controller;

import com.paycraftsystems.cmfb.dto.InitPaymentRequest;
import com.paycraftsystems.cmfb.dto.OtherPaymentParams;
import com.paycraftsystems.cmfb.dto.response.ValidateCheckResponse;
import com.paycraftsystems.cmfb.services.CUService;
import com.paycraftsystems.cu.dto.VerifyPaymentRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author paycraftsystems-i
 */
@ApplicationScoped
@Slf4j
public class ThirdPartyPaymentController {
    
    
    @Inject
    CUService cuservice;
    
    
    
    public ValidateCheckResponse doInitiateCUPayment(InitPaymentRequest pay, OtherPaymentParams payparams)
    {   
        ValidateCheckResponse doValidityCheck  = null;
        try 
        {
            //BCrypt.hashpw(CU_PASSWORD, BCrypt.gensalt(12))
            VerifyPaymentRequest verifyPaymentRequest = new VerifyPaymentRequest(pay.payeeId(), payparams.username(), payparams.doPassword(), payparams.transid(), pay.doPayeeName() , pay.payeeMobileNo(), payparams.paymenttype(), payparams.tenantcode(), LocalDate.now().toString(), ""+pay.amount());
            
            log.info(" -- doInitiateCUPayment ");
            
            doValidityCheck = cuservice.doValidityCheck(verifyPaymentRequest);
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
            log.error("Exception @ doInitiateCUPayment ",e);
        }
        
       return doValidityCheck;
    }
    
}
