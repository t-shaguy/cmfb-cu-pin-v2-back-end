/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.boundary;

import com.paycraftsystems.cmfb.controller.PaymentsBeneficiaryController;
import com.paycraftsystems.cmfb.controller.PaymentsController;
import com.paycraftsystems.cmfb.controller.SysDataController;
import com.paycraftsystems.cmfb.dto.ApproveOrDeleteRequest;
import com.paycraftsystems.cmfb.dto.FilterRequest;
import com.paycraftsystems.cmfb.dto.InitPaymentRequest;
import com.paycraftsystems.cmfb.dto.PaymentBeneficiaryEditRequest;
import com.paycraftsystems.cmfb.dto.PaymentBeneficiaryRequest;
import com.paycraftsystems.cmfb.dto.PaymentSetupEditRequest;
import com.paycraftsystems.cmfb.dto.PaymentSetupRequest;
import com.paycraftsystems.cmfb.dto.response.PaymentBeneficiaryResponse;
import com.paycraftsystems.cmfb.dto.response.PaymentProcessResponse;
import com.paycraftsystems.cmfb.dto.response.PaymentSetupResponse;
import com.paycraftsystems.cmfb.filters.JWTTokenNeeded;
import com.paycraftsystems.cmfb.repositories.PaymentsBeneficiaryRepository;
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
@Path("payments-beneficiary")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class PaymentsBeneficiaryResource implements Serializable {
    
 
    @Inject
    SysDataController sysData;
    
    @Inject
    PaymentsBeneficiaryController payments;
    
    @POST
    @Path("/create")
    //@JWTTokenNeeded
    public PaymentBeneficiaryResponse doCreateUSX(@Valid PaymentBeneficiaryRequest request ) {
    
       return payments.doCreate(request);
        
    }
    
    
    @POST
    @Path("/edit")
    @JWTTokenNeeded
    public PaymentBeneficiaryResponse doEditUSX(@Valid  PaymentBeneficiaryEditRequest request) {
        
      return payments.doEdit(request);
    }
    
    @POST
    @Path("approve")
    @JWTTokenNeeded
    public PaymentBeneficiaryResponse doApproveUSX(@Valid ApproveOrDeleteRequest request) {
    
       return payments.doApprove(request);
          
    }
    
    @POST
    @Path("delete")
    @JWTTokenNeeded
    public PaymentBeneficiaryResponse doDeleteUSX(@Valid @NotNull ApproveOrDeleteRequest request) {
    
     return payments.doDelete(request);
    
    }
    
    @POST
    @Path("list")
    @JWTTokenNeeded
    public PaymentSetupResponse doLoadSearchUSX(@Valid FilterRequest json) {
        log.info("- doLoadSearchXOX -- "+json);
       
       return  payments.doLoadList(json);
           
    }
    
   
    
    
}
