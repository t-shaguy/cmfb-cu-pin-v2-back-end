/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.services;

import com.google.gson.Gson;
import com.paycraftsystems.cmfb.dto.CBAAccessToken;
import com.paycraftsystems.cmfb.dto.CBARequest;
import com.paycraftsystems.cmfb.dto.CBARequestObj;
import com.paycraftsystems.cmfb.dto.response.CBAResponseObj;
import com.paycraftsystems.exceptions.InvalidRequestException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author paycraftsystems-i
 */

@ApplicationScoped
@Slf4j
public class CBAService {
    
    
    @ConfigProperty(name = "cba.service.url")
    String  cbaServiceUrl;
    
    @ConfigProperty(name = "cba.service.user")
    String  cbaServiceUser;
    
    
    @ConfigProperty(name = "cba.service.password")
    String  cbaServicePassword;
    
    
    @ConfigProperty(name = "cba.service.transactionSecretKey")
    String  cbaTransactionSecretKey;
    
    
    @ConfigProperty(name = "cba.service.grant.type")
    String  cbaGrantType;
    
    @Inject
    SessionService sessionService;
    
    /*
    public @NotNull ValidateCheckResponse doValidityCheck(VerifyPaymentRequest request) {
        log.info("-- ValidateCheckResponse doValidityCheck request--"+request);
        ValidateCheckResponse requestResponse;
          
        try (var client = ClientBuilder.newClient()) {
            log.info("---- URL "+String.format("%s/Token",cbaServiceUrl));
            VerifyPaymentRequestObj irnDTO = new VerifyPaymentRequestObj(request);
            log.info("@@--irnDTO-- "+irnDTO);
            var target = client.target(String.format("%s/Token",cbaServiceUrl));
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
    */
    
    public @NotNull CBAResponseObj doVerifyOnCBA(@NotNull CBARequest request) {
       
        String tokenResponseStr = "";
        CBAResponseObj fromJson = null;
        var token = sessionService.getToken(CBAAccessToken.TokenType.TOKEN);
        //GrantTokenXchangeResponse tokenResponse;// == null;
        String basic = cbaServiceUser+":"+cbaServicePassword;
        String basicAuthToken ="Basic "+Base64.getEncoder().encodeToString(basic.getBytes());
        try (var client = ClientBuilder.newClient()) {
            CBARequestObj cbaObj = new CBARequestObj(request);
            var target = client.target(String.format("%s/Token", cbaServiceUrl));
            WebTarget queryParamTarget = target.queryParam("ReferenceID", cbaObj.transactionReference).queryParam("HashValue", cbaObj.doTransHash(cbaTransactionSecretKey)); 
           
            var requestBuilder = target.request();
            requestBuilder.header("Authorization", String.format("Bearer %s", token.access_token));
            requestBuilder.header("Content-Type", "application/json");
            
            
            
                 Form f = new Form();
                 f.param("grant_type",cbaGrantType);
            var httpResponse = requestBuilder.post(jakarta.ws.rs.client.Entity.form(f));
            switch (httpResponse.getStatus()) {
                case 200 -> 
                {
                }
                case 400, 401, 403 -> {
                    var body = httpResponse.readEntity(String.class);
                    log.warn("Invalid token error {} {}", httpResponse.getStatus(), body);
                    throw new InvalidRequestException(String.format("Invalid boolean token request {%s} : {%s}",
                            httpResponse.getStatus(), body));
                }
                default -> {
                    var body = httpResponse.readEntity(String.class);
                    log.warn("Token Exchange request exception {} {}", httpResponse.getStatus(), body);
                    throw new ProcessingException(String.format("Error occurred while processing token request {%s} : {%s}",
                            httpResponse.getStatus(), body));
                }
            }
            
             String resp = httpResponse.readEntity(String.class);
             log.info(" -- "+resp);
             fromJson = new Gson().fromJson(resp.replace("application/json", "applicationJson"), CBAResponseObj.class);
       
        }
       
        return fromJson;//new GrantTokenXchangeResponse(tokenResponse.access_token(), tokenResponse.token_type(), tokenResponse.expires_in());
    }
    
    
    
    
    
    
}
