/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.services;

import com.paycraftsystems.cmfb.dto.AuthResponseData;
import com.paycraftsystems.cmfb.dto.ChangeUserPasswordRequest;
import com.paycraftsystems.cmfb.dto.ChangeUserPasswordRequestObj;
import com.paycraftsystems.cmfb.dto.ResetSysCredRequest;
import com.paycraftsystems.cmfb.dto.ResetSysCredRequestObj;
import com.paycraftsystems.cmfb.dto.SyncPasswordRequest;
import com.paycraftsystems.cmfb.dto.SyncPasswordRequestObj;
import com.paycraftsystems.cmfb.dto.UserLoginRequestV2;
import com.paycraftsystems.cmfb.dto.UserLoginRequestV2Obj;
import com.paycraftsystems.cmfb.dto.response.AuthSyncResponse;
import com.paycraftsystems.cmfb.dto.response.GenericResponse;
import com.paycraftsystems.cmfb.dto.response.GenericResponseObj;
import com.paycraftsystems.cmfb.dto.response.ResponseStatusHeaders;
import com.paycraftsystems.cmfb.dto.response.SysResetResponse;
import com.paycraftsystems.cmfb.dto.response.SysResetResponseObj;
import com.paycraftsystems.cmfb.dto.response.UserResponse;
import com.paycraftsystems.cmfb.dto.response.ValidateCheckResponse;
import com.paycraftsystems.cu.dto.VerifyPaymentRequest;
import com.paycraftsystems.cu.dto.VerifyPaymentRequestObj;
import com.paycraftsystems.exceptions.InvalidRequestException;
import com.paycraftsystems.resources.ErrorCodes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author paycraftsystems-i
 */

@ApplicationScoped
@Slf4j
public class CUService {
    
    
    @ConfigProperty(name = "cu.service.url")
    String  cuServiceUrl;
    
    
    
    public @NotNull ValidateCheckResponse doValidityCheck(VerifyPaymentRequest request) {
        log.info("-- ValidateCheckResponse doValidityCheck request--"+request);
        ValidateCheckResponse requestResponse;
          
        try (var client = ClientBuilder.newClient()) {
            log.info("---- URL "+String.format("%s/validatecheck.php",cuServiceUrl));
            VerifyPaymentRequestObj irnDTO = new VerifyPaymentRequestObj(request);
            log.info("@@--irnDTO-- "+irnDTO);
            var target = client.target(String.format("%s/validatecheck.php",cuServiceUrl));
            var requestBuilder = target.request();
            //requestBuilder.header("x-api-key", firstEIVCAPIKey);
            //requestBuilder.header("x-api-secret", firstEIVCAPISecret); 

            var httpResponse = requestBuilder.post(jakarta.ws.rs.client.Entity.json(new VerifyPaymentRequestObj(request)));
            
            switch (httpResponse.getStatus()) {
                case 200,202 -> {
                }
                case 400, 401, 403,404,405,500,504,901 -> {
                    var body = httpResponse.readEntity(String.class);
                    log.warn("Invalid  doValidityCheck response {} {}", httpResponse.getStatus(), body);
                    throw new InvalidRequestException(String.format("Invalid doValidityCheck {%s} : {%s}",
                            httpResponse.getStatus(), body));
                }
                default -> {
                    var body = httpResponse.readEntity(String.class);
                    log.warn("Invalid doValidityCheck  exception {} {}", httpResponse.getStatus(), body);
                    throw new ProcessingException(String.format("Error occurred while running cu validaty check {%s}  {%s} : {%s}",
                            request.payerid(),httpResponse.getStatus(), body));
                }
            }

            requestResponse= httpResponse.readEntity(ValidateCheckResponse.class);
              log.info("-- doValidityCheck requestResponse-- "+requestResponse);
            //requestResponse = new AuthSyncResponse(new  ResponseStatusHeaders(httpResponse.getStatus(), "NA"), readEntity !=null? readEntity.AUTHORIZATION():"NA", readEntity !=null? readEntity.customerCode():"NA", readEntity !=null? readEntity.customerName():"NA", readEntity !=null? readEntity.max_age():"NA");
           //ErrorCodes.doErrorDesc(httpResponse.getStatus()) 
           // AuthSyncResponse(ResponseStatusHeaders statusHeaders, String bearerToken, String customerCode, String customerName, String tokenLifeInspanDays)
        }
        

        return  requestResponse;
    }
    
    //ChangeUserPasswordRequest changeUserPasswordRequest = new ChangeUserPasswordRequest(doLookup.tid, doLookup.emailAddress, doLookup.mobileNo, fromJson.password, fromJson.verifyPassword, fromJson.newPassword,  "WEB");
                
    
    public @NotNull GenericResponseObj doMakepayment(SyncPasswordRequest request) {
        log.info("-- GenericResponse --"+request);
        GenericResponse requestResponse;
        //GenericResponseObj
        var status = 0;
        try (var client = ClientBuilder.newClient()) {
           
            SyncPasswordRequestObj irnDTO = new SyncPasswordRequestObj(request);
            log.info("@@--irnDTO-- "+irnDTO);
            var target = client.target(String.format("%s/processor/force-sync",cuServiceUrl));
            var requestBuilder = target.request();
            //requestBuilder.header("x-api-key", firstEIVCAPIKey);
            //requestBuilder.header("x-api-secret", firstEIVCAPISecret); 

            var httpResponse = requestBuilder.post(jakarta.ws.rs.client.Entity.json(new SyncPasswordRequestObj(request)));
            status = httpResponse.getStatus();
            switch (status) {
                case 200,202 -> {
                    
                }
                case 400, 401, 403,404,405,500,504,901 -> {
                    var body = httpResponse.readEntity(String.class);
                    log.warn("Invalid  sync profile response {} {}", httpResponse.getStatus(), body);
                    throw new InvalidRequestException(String.format("Invalid doForceSync {%s} : {%s}",
                            httpResponse.getStatus(), body));
                }
                default -> {
                    var body = httpResponse.readEntity(String.class);
                    log.warn("Invalid doForceSync  exception {} {}", httpResponse.getStatus(), body);
                    throw new ProcessingException(String.format("Error occurred while  sys login {%s}  {%s} : {%s}",
                            request.code(),httpResponse.getStatus(), body));
                }
            }

              requestResponse = httpResponse.readEntity(GenericResponse.class);
               log.info("-- GenericResponse -- "+requestResponse);
              if(status == 200|| status == 202 && requestResponse !=null)
              {
                  return new GenericResponseObj(true, status, ErrorCodes.doErrorDesc(status), requestResponse, null);
              
              }
              else
              {
                 return new GenericResponseObj(false, status, ErrorCodes.doErrorDesc(status), requestResponse, null);
               
              }
           }
       
    }
    
    
}
