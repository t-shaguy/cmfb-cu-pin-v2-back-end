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
import com.paycraftsystems.cmfb.dto.SysLoginRequest;
import com.paycraftsystems.cmfb.dto.SysLoginRequestObj;
import com.paycraftsystems.cmfb.dto.UserLoginRequestV2;
import com.paycraftsystems.cmfb.dto.UserLoginRequestV2Obj;
import com.paycraftsystems.cmfb.dto.response.AuthSyncResponse;
import com.paycraftsystems.cmfb.dto.response.GenericResponse;
import com.paycraftsystems.cmfb.dto.response.GenericResponseObj;
import com.paycraftsystems.cmfb.dto.response.ResponseStatusHeaders;
import com.paycraftsystems.cmfb.dto.response.SysResetResponse;
import com.paycraftsystems.cmfb.dto.response.SysResetResponseObj;
import com.paycraftsystems.cmfb.dto.response.UserResponse;
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
public class AuthService {
    
    
    @ConfigProperty(name = "auth.service.url")
    String  authServiceUrl;
    
    
    
    public @NotNull AuthSyncResponse doSysLogin(SysLoginRequest request) {
        log.info("-- AuthSyncResponse --"+request);
        AuthSyncResponse requestResponse;
          
        try (var client = ClientBuilder.newClient()) {
           
            SysLoginRequestObj irnDTO = new SysLoginRequestObj(request);
            log.info("@@--irnDTO-- "+irnDTO);
            var target = client.target(String.format("%s/processor/login-v2",authServiceUrl));
            var requestBuilder = target.request();
            //requestBuilder.header("x-api-key", firstEIVCAPIKey);
            //requestBuilder.header("x-api-secret", firstEIVCAPISecret); 

            var httpResponse = requestBuilder.post(jakarta.ws.rs.client.Entity.json(new SysLoginRequestObj(request)));
            
            switch (httpResponse.getStatus()) {
                case 200,202 -> {
                }
                case 400, 401, 403,404,405,500,504,901 -> {
                    var body = httpResponse.readEntity(String.class);
                    log.warn("Invalid  sync profile response {} {}", httpResponse.getStatus(), body);
                    throw new InvalidRequestException(String.format("Invalid sys login {%s} : {%s}",
                            httpResponse.getStatus(), body));
                }
                default -> {
                    var body = httpResponse.readEntity(String.class);
                    log.warn("Invalid sync profile  exception {} {}", httpResponse.getStatus(), body);
                    throw new ProcessingException(String.format("Error occurred while  sys login {%s}  {%s} : {%s}",
                            request.ux(),httpResponse.getStatus(), body));
                }
            }

              AuthResponseData readEntity = httpResponse.readEntity(AuthResponseData.class);
              log.info("-- AuthResponseData -- "+readEntity);
            requestResponse = new AuthSyncResponse(new  ResponseStatusHeaders(httpResponse.getStatus(), "NA"), readEntity !=null? readEntity.AUTHORIZATION():"NA", readEntity !=null? readEntity.customerCode():"NA", readEntity !=null? readEntity.customerName():"NA", readEntity !=null? readEntity.max_age():"NA");
           //ErrorCodes.doErrorDesc(httpResponse.getStatus()) 
           // AuthSyncResponse(ResponseStatusHeaders statusHeaders, String bearerToken, String customerCode, String customerName, String tokenLifeInspanDays)
        }
        

        return  requestResponse;
    }
    
    //ChangeUserPasswordRequest changeUserPasswordRequest = new ChangeUserPasswordRequest(doLookup.tid, doLookup.emailAddress, doLookup.mobileNo, fromJson.password, fromJson.verifyPassword, fromJson.newPassword,  "WEB");
                
    
    public @NotNull GenericResponseObj doForceSync(SyncPasswordRequest request) {
        log.info("-- GenericResponse --"+request);
        GenericResponse requestResponse;
        //GenericResponseObj
        var status = 0;
        try (var client = ClientBuilder.newClient()) {
           
            SyncPasswordRequestObj irnDTO = new SyncPasswordRequestObj(request);
            log.info("@@--irnDTO-- "+irnDTO);
            var target = client.target(String.format("%s/processor/force-sync",authServiceUrl));
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
    
    
    public @NotNull GenericResponseObj doMaintain(ChangeUserPasswordRequest request) {
        log.info("-- GenericResponse doMaintain --"+request);
        GenericResponse requestResponse;
        //GenericResponseObj
        var status = 0;
        try (var client = ClientBuilder.newClient()) {
           
            ChangeUserPasswordRequestObj irnDTO = new ChangeUserPasswordRequestObj(request);
            log.info("@@--irnDTO-- "+irnDTO);
            var target = client.target(String.format("%s/processor/maintain-profile",authServiceUrl));
            var requestBuilder = target.request();
            //requestBuilder.header("x-api-key", firstEIVCAPIKey);
            //requestBuilder.header("x-api-secret", firstEIVCAPISecret); 

            var httpResponse = requestBuilder.post(jakarta.ws.rs.client.Entity.json(new ChangeUserPasswordRequestObj(request)));
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
    /*
    public @NotNull GenericResponseObj doForceSync(ChangeUserPasswordRequest request) {
        log.info("-- GenericResponse doMaintain --"+request);
        GenericResponse requestResponse;
        //GenericResponseObj
        var status = 0;
        try (var client = ClientBuilder.newClient()) {
           
            ChangeUserPasswordRequestObj irnDTO = new ChangeUserPasswordRequestObj(request);
            log.info("@@--irnDTO-- "+irnDTO);
            var target = client.target(String.format("%s/processor/maintain-profile",authServiceUrl));
            var requestBuilder = target.request();
            //requestBuilder.header("x-api-key", firstEIVCAPIKey);
            //requestBuilder.header("x-api-secret", firstEIVCAPISecret); 

            var httpResponse = requestBuilder.post(jakarta.ws.rs.client.Entity.json(new ChangeUserPasswordRequestObj(request)));
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
    */
    
    public @NotNull UserResponse doVerifyUser(UserLoginRequestV2 request) {
        log.info("-- UserResponse --"+request);
        UserResponse requestResponse;
          
        try (var client = ClientBuilder.newClient()) {
           
            UserLoginRequestV2Obj irnDTO = new UserLoginRequestV2Obj(request);
            log.info("@@--irnDTO-- "+irnDTO);
            var target = client.target(String.format("%s/processor/verify-user-v2",authServiceUrl));
            var requestBuilder = target.request();
            //requestBuilder.header("x-api-key", firstEIVCAPIKey);
            //requestBuilder.header("x-api-secret", firstEIVCAPISecret); 

            var httpResponse = requestBuilder.post(jakarta.ws.rs.client.Entity.json(new UserLoginRequestV2Obj(request)));
            
            switch (httpResponse.getStatus()) {
                case 200,202 -> {
                }
                case 400, 401, 403,404,405,500,504,901,850 -> {
                    var body = httpResponse.readEntity(String.class);
                    log.warn("Invalid  sync profile response {} {}", httpResponse.getStatus(), body);
                    throw new InvalidRequestException(String.format("Invalid user login {%s} : {%s}",
                            httpResponse.getStatus(), body));
                }
                default -> {
                    var body = httpResponse.readEntity(String.class);
                    log.warn("Invalid sync profile  exception {} {}", httpResponse.getStatus(), body);
                    throw new ProcessingException(String.format("Error occurred while  user login {%s}  {%s} : {%s}",
                            request.code(),httpResponse.getStatus(), body));
                }
            }

              //System.out.println("httpResponse = " + httpResponse.readEntity(String.class));
              AuthResponseData readEntity = httpResponse.readEntity(AuthResponseData.class);
              log.info("2-- AuthResponseData -- "+readEntity);
              requestResponse = new UserResponse(new  ResponseStatusHeaders(httpResponse.getStatus(), "NA"), readEntity !=null? readEntity.AUTHORIZATION():"NA");
            }
        

        return  requestResponse;
    }
    
   
    
    public @NotNull SysResetResponseObj doSysReset(ResetSysCredRequest request) {
        log.info("-- AuthSyncResponse --"+request);
        SysResetResponse requestResponse;
        int status = 0;
        try (var client = ClientBuilder.newClient()) {
            
            
            log.info("--- url --- "+String.format("%s/reset",authServiceUrl));
           
            ResetSysCredRequestObj irnDTO = new ResetSysCredRequestObj(request);
            log.info("@@--irnDTO-- "+irnDTO);
            var target = client.target(String.format("%s/processor/reset",authServiceUrl));
            var requestBuilder = target.request();
            //requestBuilder.header("x-api-key", firstEIVCAPIKey);
            //requestBuilder.header("x-api-secret", firstEIVCAPISecret); 

            var httpResponse = requestBuilder.post(jakarta.ws.rs.client.Entity.json(new ResetSysCredRequestObj(request)));
            status = httpResponse.getStatus();
            switch (status) {
                case 200,202 -> {
                }
                case 400, 401, 403,404,405,500,504 -> {
                    var body = httpResponse.readEntity(String.class);
                    log.warn("Invalid  sync profile response {} {}", httpResponse.getStatus(), body);
                    throw new InvalidRequestException(String.format("Invalid sys login {%s} : {%s}",
                            httpResponse.getStatus(), body));
                }
                default -> {
                    var body = httpResponse.readEntity(String.class);
                    log.warn("Invalid sync profile  exception {} {}", httpResponse.getStatus(), body);
                    throw new ProcessingException(String.format("Error occurred while  sys login {%s}  {%s} : {%s}",
                            request.ux(),httpResponse.getStatus(), body));
                }
            }

              requestResponse = httpResponse.readEntity(SysResetResponse.class);
              log.info("-- AuthResponseData -- "+requestResponse);
              
              if(status == ErrorCodes.SUCCESSFUL || status == ErrorCodes.ACCEPTED)
              {
                return new SysResetResponseObj(true, status,ErrorCodes.doErrorDesc(status), requestResponse);
                
              }
              else
              {
                  return new SysResetResponseObj(false, status, ErrorCodes.doErrorDesc(status), requestResponse);
              }
            
        
        }
        

       // return  requestResponse;
    }
    
    
    
    public @NotNull SysResetResponseObj doSysResend(ResetSysCredRequest request) {
        log.info("-- AuthSyncResponse --"+request);
        SysResetResponse requestResponse;
        int status = 0;
        try (var client = ClientBuilder.newClient()) {
           
            ResetSysCredRequestObj irnDTO = new ResetSysCredRequestObj(request);
            log.info("@@--irnDTO-- "+irnDTO);
            var target = client.target(String.format("%s/resend",authServiceUrl));
            var requestBuilder = target.request();
            //requestBuilder.header("x-api-key", firstEIVCAPIKey);
            //requestBuilder.header("x-api-secret", firstEIVCAPISecret); 

            var httpResponse = requestBuilder.post(jakarta.ws.rs.client.Entity.json(new ResetSysCredRequestObj(request)));
            status = httpResponse.getStatus();
            switch (status) {
                case 200 -> {
                }
                case 400, 401, 403,404,405,500,504 -> {
                    var body = httpResponse.readEntity(String.class);
                    log.warn("Invalid  sync profile response {} {}", httpResponse.getStatus(), body);
                    throw new InvalidRequestException(String.format("Invalid sys login {%s} : {%s}",
                            httpResponse.getStatus(), body));
                }
                default -> {
                    var body = httpResponse.readEntity(String.class);
                    log.warn("Invalid sync profile  exception {} {}", httpResponse.getStatus(), body);
                    throw new ProcessingException(String.format("Error occurred while  sys login {%s}  {%s} : {%s}",
                            request.ux(),httpResponse.getStatus(), body));
                }
            }

              requestResponse = httpResponse.readEntity(SysResetResponse.class);
              log.info("-- AuthResponseData -- "+requestResponse);
              
              if(status == ErrorCodes.SUCCESSFUL || status == ErrorCodes.ACCEPTED)
              {
                return new SysResetResponseObj(true, status,ErrorCodes.doErrorDesc(status), requestResponse);
                
              }
              else
              {
                  return new SysResetResponseObj(false, status, ErrorCodes.doErrorDesc(status), null);
              }
            
        
        }
        

       // return  requestResponse;
    }
    
}
