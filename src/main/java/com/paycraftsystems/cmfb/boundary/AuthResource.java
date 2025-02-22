/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.boundary;








import com.fasterxml.jackson.databind.ObjectMapper;
import com.paycraftsystems.cmfb.controller.ClientsSyncController;
import com.paycraftsystems.cmfb.controller.SysDataController;
import com.paycraftsystems.cmfb.dto.ClientsInfoObj;
import com.paycraftsystems.cmfb.dto.ResetRequest;
import com.paycraftsystems.cmfb.dto.ResetRequestObj;
import com.paycraftsystems.cmfb.dto.ResetResponse;
import com.paycraftsystems.cmfb.dto.ResetSysCredRequest;
import com.paycraftsystems.cmfb.dto.SysLoginRequest;
import com.paycraftsystems.cmfb.dto.SysLoginRequestObj;
import com.paycraftsystems.cmfb.dto.response.AuthSyncResponse;
import com.paycraftsystems.cmfb.dto.response.InvalidRequestResponseObj;
import com.paycraftsystems.cmfb.dto.response.SysLoginResponse;
import com.paycraftsystems.cmfb.dto.response.SysResetResponseObj;
import com.paycraftsystems.cmfb.entities.ClientsSync;
import com.paycraftsystems.cmfb.services.AuthService;
import com.paycraftsystems.exceptions.CMFBException;
import com.paycraftsystems.exceptions.InvalidRequestException;
import com.paycraftsystems.resources.ErrorCodes;
import com.paycraftsystems.resources.ValidationHelper;
import io.smallrye.common.constraint.NotNull;
//import io.smallrye.reactive.messaging.annotations.Emitter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author root
 */
@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Slf4j
public class AuthResource implements Serializable //implements AuthInterface
{
    
    
   // private static Logger log = LoggerFactory.getLogger(AuthResource.class);
    
    
    
    
    @Inject
    AuthService authService;
    
    @Inject
    SysDataController sysDataHelper;
  
    @Inject
    ClientsSyncController clientsSyncController;
    
    
    
    ValidationHelper rh = new ValidationHelper();
    

    @GET
    public Response doPing() {
     
            
            return Response.ok().build();
     
    }

    //@Override
    @POST
    @Path("sys-login")
    public SysLoginResponse doSysLogin( @Valid final SysLoginRequest json) {
        
        System.out.println("doSysLogin = " + json);
        
        int prodOrDev = Integer.parseInt(sysDataHelper.doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
            //
            SysLoginRequestObj syslogindto = new SysLoginRequestObj(json);
           
            log.info("** json = " + syslogindto);
             
            ClientsSync doGetClientByID = clientsSyncController.doGetClientByID(syslogindto.ux);
            
                log.info("doGetClientByID = " + doGetClientByID);
             if(doGetClientByID !=null)
             {
                JsonObject syslogin = job.build();
                log.info("- @---@@@@@@@ doGetClientByID syslogin " + syslogin);

                JsonObjectBuilder jobx = Json.createObjectBuilder();
                AuthSyncResponse doSysLogin = authService.doSysLogin(json);
                if((doSysLogin !=null && doSysLogin.statusHeaders() !=null) && (doSysLogin.statusHeaders().statusCode() == ErrorCodes.ACCEPTED || doSysLogin.statusHeaders().statusCode() == ErrorCodes.SUCCESSFUL))
                {
                    
                     return new SysLoginResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doSysLogin);
                }
                else if((doSysLogin !=null && doSysLogin.statusHeaders() !=null) && (doSysLogin.statusHeaders().statusCode() != ErrorCodes.ACCEPTED && doSysLogin.statusHeaders().statusCode() != ErrorCodes.SUCCESSFUL))
                {
                      
                    return new SysLoginResponse(false, doSysLogin.statusHeaders().statusCode() , ErrorCodes.doErrorDesc(doSysLogin.statusHeaders().statusCode()), null);
            
                }
                else
                {
                      return new SysLoginResponse(false, ErrorCodes.SYSTEM_ERROR , ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null);
            
                }
             }
             else
             {
                  return new SysLoginResponse(false, ErrorCodes.INVALID_USER, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USER), null);
             
             }
            
        }
        catch(jakarta.json.stream.JsonParsingException e)
        {
            
             return new SysLoginResponse(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR)+"-"+e.getMessage(), null);
        }
        catch (InvalidRequestException e) {
            Pattern pattern = Pattern.compile(".*\\d.*");
            int status = 0;
            String message = "";
            InvalidRequestResponseObj responseObj = null;
            String error = e.getMessage();
            System.out.println("@@@ error = " +  error);
            
            Matcher matcher = pattern.matcher(error);
            boolean matches = matcher.matches();
            if(matches)
            {
                System.out.println("-- error = " + error);
                message = error.substring(0,error.indexOf("{"));
                error = error.substring(error.indexOf("{"), error.indexOf("{")+4);
                System.out.println("error = " + error);
                status = Integer.parseInt(error.replaceAll("\\{", "").replaceAll("\\}", "").trim());
                
                //System.out.println("matches = " + matches+" status "+status);
            }
            else
            {
                error = error.substring(error.indexOf("{")+1).trim();
                error = error.substring(0, error.length()-1).trim();
                System.out.println("matches error  = " + error);
                ObjectMapper objectMapper = new ObjectMapper();
                try 
                {
                    responseObj = objectMapper.readValue(error, InvalidRequestResponseObj.class);
                    status = responseObj.code();
                    message = responseObj.data().message();
                    System.out.println("objectMapper = " + responseObj.code());
                    System.out.println("objectMapper message = " + message);

                } 
                catch (Exception ex) {


                    ex.printStackTrace();
                }

                log.info("--#InvalidRequestException @ InvalidRequestException  @ error "+error);
        
            }
           
         
           return new SysLoginResponse(false, status, ErrorCodes.doErrorDesc(status)+"-"+message, null);
              
        }
        catch (WebApplicationException e) {
        
              
           int status = e.getResponse().getStatus();
          
           log.error(" WebApplicationException doSysLogin : ("+status+") -- ",e);
         
           return new SysLoginResponse(false, status, ErrorCodes.doErrorDesc(status), null);
              
        }
        catch (Exception e) {
        
          log.error(" Exception doSysLogin -- ",e);
          
          //return Response.status(ErrorCodes.SYSTEM_ERROR).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR)).build()).build();
           
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                     throw new CMFBException(ErrorCodes.DATABASE_ERROR, e.getMessage(), prodOrDev);
                }/*
                else if((e.getCause() instanceof org.apache.http.conn.HttpHostConnectException) || (e.getCause() instanceof jakarta.ws.rs.ProcessingException ||  (e.getCause() instanceof  java.net.SocketTimeoutException)))
                {
                     
                    return new SysLoginResponse(false, ErrorCodes.COMM_LINK, ErrorCodes.doErrorDesc(ErrorCodes.COMM_LINK), null);
                }*/
                else if((e.getCause() instanceof java.io.IOException))
                {
                    
                     return new SysLoginResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null);
                }
                else
                {
                   
                     return new SysLoginResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR)+"-"+e.getMessage(), null);
                }
        
        }
        
       
      
      }
        
    
    
    @POST
    @Path("reset")
    public SysResetResponseObj doReset(@Valid final ResetRequest request) {
        int prodOrDev = 0;//Integer.parseInt(sysDataHelper.doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
       
        log.info("  -- doReset request -- "+request);
        ClientsSync doSyncClient = null;
        ResetResponse resetResponse;
        JsonObjectBuilder notificationjob = Json.createObjectBuilder();
        try 
        {
            prodOrDev = Integer.parseInt(sysDataHelper.doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
            
            ResetRequestObj json  = new ResetRequestObj(request);
       
            if(json !=null )
            {
                String clientId = json.ux;
                
                
                ClientsSync doGetClientByID = clientsSyncController.doGetClientByID(clientId);
                log.info(" ### @-- doReset clientId -- "+clientId+" doGetClientByID -- "+doGetClientByID);
                
                if(doGetClientByID !=null && doGetClientByID.status == 1) // synched client
                {
                      
                       ResetSysCredRequest resetSysCredRequest = new ResetSysCredRequest(doGetClientByID.clientId, "", doGetClientByID.customerCode);
                       
                       //resetResponse = JsonbBuilder.create().fromJson(authJson.toString(), ResetResponse.class);
                             
                       SysResetResponseObj res  =  authService.doSysReset(resetSysCredRequest);
                      
                       log.info("-- SysResetResponseObj --  "+res);
                       //resetResponse = JsonbBuilder.create().fromJson(res.readEntity(JsonObject.class).toString(), ResetResponse.class);
                        
                       if(res !=null && res.success())
                       {
                             log.info("resetResponse = " );    
                    
                            if((res.resetInfo() != null && Integer.parseInt((res.resetInfo().code() == null ||  res.resetInfo().code().isBlank())?"703":res.resetInfo().code()) == ErrorCodes.ACCEPTED) || "1".equals(sysDataHelper.doLookUpOrDefault("INTEGRATION-MODE","X")))
                            {
                              
                                return new SysResetResponseObj(true, ErrorCodes.ACCEPTED, ErrorCodes.doErrorDesc(ErrorCodes.ACCEPTED), res.resetInfo());
                 
                            }
                            else  
                            {
                           
                                 log.info(" ABOUT TO SEND EMAIL .... ");
                                 notificationjob.add("addressee", clientId)
                                 .add("recepient", clientId)
                                .add("sendTo", clientId)
                                .add("subject", "RESET NOTIFICATION")
                               // .add("toShare", resetResponse.maxage)
                                .add("notificationType", "RS")
                                .add("option", "4")
                                .add("requestId", UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20))
                                //.add("shortMessage", "You default OTP 1010")
                                .add("iv", rh.toDefault(res.resetInfo().iv()))
                                .add("key", rh.toDefault(res.resetInfo().key()))
                                .add("toShareLabel", "OTPX");

                              
                               return new SysResetResponseObj(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), res.resetInfo());
                 
                            
                            }
                            
                            
                       }
                    
                              
                    
                    // return Response.status(resetResp!=null?resetResp.getStatus():204).build();//;authclient.doReset(json);
                    
                }
                else  if(doGetClientByID !=null && (doGetClientByID.status == 0))// || doGetClientByID.status == 9))// || active client
                {
                   
                   // JsonObject authJson = Json.createObjectBuilder().add("ux", rh.toDefault(doGetClientByID.clientId)).add("key", json.getString("key", "NA")).add("iv", json.getString("iv", "NA")).add("partnerCode",rh.toDefault(doGetClientByID.customerCode)).build();
                   
                     ResetSysCredRequest resetSysCredRequest = new ResetSysCredRequest(doGetClientByID.clientId, "", doGetClientByID.customerCode);
                      
                     SysResetResponseObj res  =  authService.doSysReset(resetSysCredRequest);
                   
                    if(res !=null)
                    {
                        
                        doGetClientByID.resetDate = LocalDateTime.now();
                        doGetClientByID.status = Long.parseLong("1");
                        doSyncClient = clientsSyncController.doSyncClient(doGetClientByID,prodOrDev);
                        log.info("-- doSyncClient -- "+doSyncClient);
                        
                        if(doSyncClient == null)
                        {
                            
                             return new SysResetResponseObj(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), res.resetInfo());
                 
                        }
                        else
                        {
                            
                            
                            if("1".equals(sysDataHelper.doLookUpOrDefault("INTEGRATION-MODE", "0")))
                            {
                              // return Response.fromResponse(rx).entity(resetResponse.toJson()).build();
                               
                                  //return new ResetCredsResponse(true, ErrorCodes.ACCEPTED,ErrorCodes.doErrorDesc(ErrorCodes.ACCEPTED), res.resetInfo(), null);
                          
                               
                                 return new SysResetResponseObj(true, ErrorCodes.ACCEPTED, ErrorCodes.doErrorDesc(ErrorCodes.ACCEPTED), res.resetInfo());
                 
                            }
                            else  
                            {
                                log.info(" ABOUT TO SEND EMAIL ....1 ");
                                notificationjob.add("addressee", clientId)
                                .add("recepient", clientId)
                                .add("sendTo", clientId)
                                .add("subject", "RESET NOTIFICATION")
                                //.add("toShare", resetResponse.maxage)
                                .add("notificationType", "RS")
                                .add("option", "4")
                                .add("requestId", UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20))
                                //.add("shortMessage", "You default OTP 1010")
                                .add("iv", rh.toDefault(res.resetInfo().iv()))
                                .add("key", rh.toDefault(res.resetInfo().key()))
                                .add("toShareLabel", "OTPX");

                                
                                 return new SysResetResponseObj(true, ErrorCodes.ACCEPTED, ErrorCodes.doErrorDesc(ErrorCodes.ACCEPTED), res.resetInfo());
                 
                            
                            }
                             
                            
                            
                        }
                       
                    }
                    else
                    {
                      
                        //return new ResetCredsResponse(false, ErrorCodes.INVALID_CLIENT,ErrorCodes.doErrorDesc(ErrorCodes.INVALID_CLIENT), null, null);
                         
                         return new SysResetResponseObj(false, ErrorCodes.INVALID_CLIENT, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_CLIENT), null);
                 
                    }
             
                }
                else
                {
                  
                   return new SysResetResponseObj(false, ErrorCodes.INVALID_CLIENT, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_CLIENT), null);
                 
                
                }
            }
            else
            {
              
                 return new SysResetResponseObj(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null);
                    
            }      
                    
            
        }
        catch (WebApplicationException e) {
            e.printStackTrace();
            log.error("   WebApplicationException  doReset ", e);
            
              return new SysResetResponseObj(false, e.getResponse().getStatus(), ErrorCodes.doErrorDesc(e.getResponse().getStatus()), null);
                           
        }
        catch (Exception e) {
            e.printStackTrace();
            if(e.getCause()  !=null)System.out.println("xe = " + e.getCause()); System.out.println("-- xe = " + e.getMessage());
            log.error(" +   Exception doReset ", e);
              //org.hibernate.exception.SQLGrammarException org.hibernate.exception.SQLGrammarException
               if((e.getCause() instanceof org.hibernate.exception.SQLGrammarException) || (e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException) || (e.getMessage().startsWith("java.lang.Exception: org.hibernate.exception.SQLGrammarException") ))
                {
                   
                      return new SysResetResponseObj(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null);
            
                }/*
                else if((e.getCause() instanceof org.apache.http.conn.HttpHostConnectException))
                {
                  
                    return new SysResetResponseObj(false, ErrorCodes.COMM_LINK, ErrorCodes.doErrorDesc(ErrorCodes.COMM_LINK), null);
            
                }*/
                else if((e.getCause() instanceof java.io.IOException))
                {
                    
                       return new SysResetResponseObj(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null);
                }
                else
                {
                   
                    
                     return new SysResetResponseObj(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR)+"-"+e.getMessage(), null);
                }
        }
         
        
        return null;
    }

   
    
    @POST
    @Path("resend") 
    public SysResetResponseObj doResend(@Valid @NotNull ResetSysCredRequest request) {
        
        
       return authService.doSysResend(request);
    }

   

    //@Override
    @POST
    @Path("refresh")
    public Response doRefresh(JsonObject json) {
         return null;// authclient.doRefresh(json);
    }
    
    
    //@Override
    @POST
    @Path("list-clients")
   // @JWTTokenNeeded
    public Response doListClient() {
       
      return null;// authclient.doListClients();
    }
    
    
    //@Override
    @POST
    @Path("manage-api-client")
    //@JWTTokenNeeded
    /*
    @Operation(summary = "manage partner api client", description = "Returns the updated api client details")
    @APIResponse(responseCode = "200", description = "Successful")
    @APIResponse(responseCode = "!200", description = "Error codes as defined in the API documentation also check the ErrorDesc tag for the narration of the errror code")
    @APIResponse(description = "The API user JWT",

            responseCode = "200",

            content = @Content(mediaType = MediaType.APPLICATION_JSON,

                    schema = @Schema(implementation =  CustomerClientResponseSW.class,

                            readOnly = true,

                            description = "the api client details ",

                            required = true,

                            name = "doManageClientMXX"))) //PartnerDTOSW
   
    @Tag(name = "Customer API client Management Resource", description = "update an FSI  participating customer API CLient ")
    */
    public Response doManageClientMXX(@Valid JsonObject  jsonObj ) {
       
     return null;// authclient.doMaintainClient(jsonObj);
    }
  
    @POST
    @Path("delete-client")
    //@JWTTokenNeeded
    public Response doDeleteClientMXX(@Valid JsonObject  jsonObj ) {
       
     return null;// authclient.doDeleteClient(jsonObj);
    }
   
    //@Override
    @POST
    @Path("create-api-client")
    /*
    @Operation(summary = "create partner api client", description = "Returns the partner details")
    @APIResponse(responseCode = "200", description = "Successful")
    @APIResponse(responseCode = "!200", description = "Error codes as defined in the API documentation also check the ErrorDesc tag for the narration of the errror code")
    @APIResponse(description = "The API user JWT",

            responseCode = "200",

            content = @Content(mediaType = MediaType.APPLICATION_JSON,

                    schema = @Schema(implementation =  CustomerClientResponseSW.class,

                            readOnly = true,

                            description = "the Partner details ",

                            required = true,

                            name = "doCreateClientCXX"))) //PartnerDTOSW
   
    @Tag(name = "Customer API client Management Resource", description = "Creates an FSI  participating Partner API CLient ")
    */
    //@JWTTokenNeeded
    public Response doCreateClientMXX(@Valid JsonObject  jsonObj ) {
        int prodOrDev = Integer.parseInt(sysDataHelper.doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
       
        log.info("--  doCreateClientCXX --- "+jsonObj);
        try 
        {
            ClientsInfoObj fromJson =  null;//JsonbBuilder.create().fromJson(jsonObj.toString(), ClientsInfoObj.class);
            /*
            if(fromJson.clientCategory < 1 || fromJson.clientCategory > 3)
            {
                return Response.status(ErrorCodes.INVALID_CLIENT_CAT).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doErrorDesc(ErrorCodes.INVALID_CLIENT_CAT)).build()).build();
            }*/
            
            if(!rh.isValidEmail(fromJson.clientName))
            {
                return Response.status(ErrorCodes.INVALID_CLIENT_NAME).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doErrorDesc(ErrorCodes.INVALID_CLIENT_NAME, prodOrDev)).build()).build();
            }
            
            if(fromJson.enforceIp && !rh.isValidIP(fromJson.ipAddress))
            {
                return Response.status(ErrorCodes.INVALID_IP_ADDRESS).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doErrorDesc(ErrorCodes.INVALID_IP_ADDRESS, prodOrDev)).build()).build();
            }
            /*
            if(!rh.isValidPhoneNumber(fromJson.partnerID))
            {
                return Response.status(ErrorCodes.INVALID_PARTNER_ID).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doErrorDesc(ErrorCodes.INVALID_PARTNER_ID)).build()).build();
            }*/
            
            
            
            if(fromJson.tokenLifespanDays < 1 || fromJson.tokenLifespanDays > 365)
            {
                return Response.status(ErrorCodes.INVALID_TOKEN_AGE).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doErrorDesc(ErrorCodes.INVALID_TOKEN_AGE, prodOrDev)).build()).build();
            }
            
           
            
            if(!rh.isValid(fromJson.customerCode))
            {
                return Response.status(ErrorCodes.INVALID_CUSTOMER_CODE).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doErrorDesc(ErrorCodes.INVALID_CUSTOMER_CODE, prodOrDev)).build()).build();
            }
            
            List<ClientsSync> doGetClientByCustomer = clientsSyncController.doGetClientByCustomer(fromJson.customerCode);
            if(doGetClientByCustomer !=null && doGetClientByCustomer.size() > 2)
            {
                     return Response.status(ErrorCodes.MAX_NUMBER_OF_API_CLIENTS_EXCEEDED).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doErrorDesc(ErrorCodes.MAX_NUMBER_OF_API_CLIENTS_EXCEEDED, prodOrDev)).build()).build();
          
            }
            
            
            ClientsSync doGetClientByID = clientsSyncController.doGetClientByID(fromJson.clientName);
            if(doGetClientByID != null)
            {
                return Response.status(ErrorCodes.DUPLICATE_TRANSACTION).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doErrorDesc(ErrorCodes.DUPLICATE_TRANSACTION, prodOrDev)).build()).build();
            }
            fromJson.clientType = "C";
            ClientsSync doSync = clientsSyncController.doSync(fromJson, "C");
            if(doSync != null)
            {
                
                
                 log.info(" --- fromJson.toJsonObj()  -- "+fromJson.toJsonObj());
                 return null;// authclient.doCreateClient(fromJson.toJsonObj());
                 
                 // consider converting duplicate transaction to user exists
                
            }
            else
            {
                return Response.status(ErrorCodes.DATABASE_ERROR).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR, prodOrDev)).build()).build();
            }
            
        }
        catch (CMFBException e) {
        
            log.error("  ---  Exception FSIException ---- ",e);
        
             return Response.status(e.getResponse().getStatus()).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doErrorDesc(e.getResponse().getStatus(), prodOrDev)).build()).build();
        }
        catch (WebApplicationException e) {
        
            log.error("  ---  Exception WebApplicationException ---- ",e);
        
             return Response.status(e.getResponse().getStatus()).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doErrorDesc(e.getResponse().getStatus(), prodOrDev)).build()).build();
        }
        catch (Exception e) {
        
            log.info("  ### @@---  Exception doCreateClient @@@@ ---- "+e.getCause().getMessage());
            log.error("  @@---  Exception doCreateClient **---- ",e.getCause());
            
            /*
            if(e.getCause() instanceof org.jboss.resteasy.client.exception.ResteasyWebApplicationException)
            {
                
                 WebApplicationException ex =  (WebApplicationException) e.getCause();
                
                return Response.status(ex.getResponse().getStatus()).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doErrorDesc(ex.getResponse().getStatus(), prodOrDev)).build()).build();
       
            }*/
            if(e.getCause() instanceof org.apache.http.conn.HttpHostConnectException || e.getCause() instanceof java.net.ConnectException || e.getCause() instanceof  org.apache.http.conn.HttpHostConnectException || e.getCause() instanceof jakarta.ws.rs.ProcessingException)
            {
                 return Response.status(ErrorCodes.COMM_LINK).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doErrorDesc(ErrorCodes.COMM_LINK, prodOrDev)).build()).build();
       
            }
            
            
        
           return Response.status(ErrorCodes.SYSTEM_ERROR).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR, prodOrDev)).build()).build();
        }
       
    
    }
    
    //@Override
    @POST
    @Path("create-client/{id}")
    public Response doLookupClient(@Valid @PathParam("id")  long id ) {
     return null;// authclient.doLookupClient(id);
    }
    
   
   //@Override
   @POST
   @Path("get-client-byname/{clientName}")
   //@JWTTokenNeeded
   public Response doGetClient(@Valid @PathParam("clientName") String clientName)
   {
       return null;//authclient.doGetClient(clientName);
   }
   
  // 1zY1465- v1
   
   //W1089726983
   //800692 7753
   
   @POST
   @Path("list-clients-by-customer/{customerCode}")
   //@JWTTokenNeeded
   public Response doGetClientByCustomer(@Valid @PathParam("customerCode") String customerCode)
   {
       return  null;//authclient.doListClientsByCustomer(customerCode);
   }

   
    
}
