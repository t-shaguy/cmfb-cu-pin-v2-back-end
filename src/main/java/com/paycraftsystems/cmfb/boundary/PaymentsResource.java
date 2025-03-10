/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.boundary;

import com.paycraftsystems.cmfb.controller.PaymentsController;
import com.paycraftsystems.cmfb.controller.SysDataController;
import com.paycraftsystems.cmfb.dto.ApproveOrDeleteRequest;
import com.paycraftsystems.cmfb.dto.CompletePaymentRequest;
import com.paycraftsystems.cmfb.dto.FilterRequest;
import com.paycraftsystems.cmfb.dto.InitPaymentRequest;
import com.paycraftsystems.cmfb.dto.PaymentSetupEditRequest;
import com.paycraftsystems.cmfb.dto.PaymentSetupRequest;
import com.paycraftsystems.cmfb.dto.response.PaymentProcessResponse;
import com.paycraftsystems.cmfb.dto.response.PaymentSetupResponse;
import com.paycraftsystems.cmfb.filters.JWTTokenNeeded;
import io.smallrye.common.constraint.NotNull;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author paycraftsystems-i
 */
@Path("payments")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class PaymentsResource implements Serializable {
    
 
    @Inject
    SysDataController sysData;
    
    @Inject
    PaymentsController payments;
    
    @POST
    @Path("/create")
    @JWTTokenNeeded
    public PaymentSetupResponse doCreateUSX(@Valid PaymentSetupRequest request ) {
    
       return payments.doCreate(request);
        
    }
    
    
    @POST
    @Path("/edit")
    @JWTTokenNeeded
    public PaymentSetupResponse doEditUSX(@Valid @NotNull PaymentSetupEditRequest request) {
        
      return payments.doEdit(request);
    }
    
    @POST
    @Path("approve")
    @JWTTokenNeeded
    public PaymentSetupResponse doApproveUSX(@Valid @NotNull ApproveOrDeleteRequest request) {
    
       return payments.doApprove(request);
          
    }
    
    @POST
    @Path("delete")
    @JWTTokenNeeded
    public PaymentSetupResponse doDeleteUSX(@Valid @NotNull ApproveOrDeleteRequest request) {
    
     return payments.doDelete(request);
    
    }
    
    @POST
    @Path("list")
    @JWTTokenNeeded
    public PaymentSetupResponse doLoadSearchUSX(@Valid FilterRequest json) {
        log.info("- doLoadSearchXOX -- "+json);
       
       return  payments.doLoadList(json);
           
    }
    
    @POST
    @Path("list-teller")
    @JWTTokenNeeded
    public PaymentSetupResponse doLoadSearchTellerUSX(@Valid FilterRequest json) {
        log.info("- doLoadSearchTellerUSX -- "+json);
       
       return  payments.doLoadListTeller(json);
           
    }
    
    
    @POST
    @Path("init-payment")
    @JWTTokenNeeded
    public PaymentProcessResponse doVerifyPayeeUSX(@Valid InitPaymentRequest json) {
        log.info("- doVerifyPayeeUSX -- "+json);
       
       return  payments.doInitPayment(json);
           
    }
    
    
    @POST
    @Path("make-payment")
    @JWTTokenNeeded
    public PaymentProcessResponse doCompletePaymentUSX(@Valid CompletePaymentRequest json) {
        log.info("- doCompletePaymentUSX -- "+json);
       
       return  payments.doCompletePayment(json);
           
    }
    
    
}
