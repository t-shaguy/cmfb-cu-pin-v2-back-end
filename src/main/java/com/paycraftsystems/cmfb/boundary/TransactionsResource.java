/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.boundary;

import com.paycraftsystems.cmfb.controller.PaymentsController;
import com.paycraftsystems.cmfb.controller.SysDataController;
import com.paycraftsystems.cmfb.controller.TransactionsController;
import com.paycraftsystems.cmfb.dto.FilterRequest;
import com.paycraftsystems.cmfb.dto.response.TransactionLogProcessResponse;
import com.paycraftsystems.cmfb.filters.JWTTokenNeeded;

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
@Path("transactions")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class TransactionsResource implements Serializable {
    
 
    @Inject
    SysDataController sysData;
    
    @Inject
    PaymentsController payments;
    
    @Inject
    TransactionsController transController;
    
    
    
    @POST
    @Path("list")
    @JWTTokenNeeded
    public TransactionLogProcessResponse doLoadSearchUSX(@Valid FilterRequest json) {
        log.info("- doLoadSearchXOX -- "+json);
       return  transController.doLoadListTellerTransactions(json);
           
    }
//    
//    @POST
//    @Path("list-teller")
//    @JWTTokenNeeded
//    public PaymentSetupResponse doLoadSearchTellerUSX(@Valid FilterRequest json) {
//        log.info("- doLoadSearchTellerUSX -- "+json);
//       
//       return  payments.doLoadListTeller(json);
//           
//    }
    
    
    
}
