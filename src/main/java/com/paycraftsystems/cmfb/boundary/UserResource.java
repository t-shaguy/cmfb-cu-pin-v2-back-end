/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.boundary;


import com.paycraftsystems.cmfb.controller.UserController;
import com.paycraftsystems.cmfb.dto.ChangePasswordRequest;
import com.paycraftsystems.cmfb.dto.FilterRequest;
import com.paycraftsystems.cmfb.dto.ForcePasswordChangeRequest;
import com.paycraftsystems.cmfb.dto.ForceTXPRequest;
import com.paycraftsystems.cmfb.dto.InitForcePasswordChangeRequest;
import com.paycraftsystems.cmfb.dto.OTPRequest;
import com.paycraftsystems.cmfb.dto.UserLoginRequest;
import com.paycraftsystems.cmfb.dto.UserProfileRequest;
import com.paycraftsystems.cmfb.dto.response.ChangePasswordResponse;
import com.paycraftsystems.cmfb.dto.response.ChangeTXPResponse;
import com.paycraftsystems.cmfb.dto.response.ForcePasswordChangeResponse;
import com.paycraftsystems.cmfb.dto.response.GenericResponse;
import com.paycraftsystems.cmfb.dto.response.ManagedLoginResponseV2;
import com.paycraftsystems.cmfb.dto.response.ResendOTPResponse;
import com.paycraftsystems.cmfb.dto.response.UserProfileResponse;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author paycraftsystems-i
 */
@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UserResource implements Serializable {
    
  private static Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    
    //@Inject
    //AdminServicesController adminServicesController;
    
    @Inject
    UserController userController;
    
   // @Inject
   // EntityManager em;
    
    
    @POST
    @Path("/resend-otp")
    //@JWTTokenNeeded
    public ResendOTPResponse doResendOTPXOX(@Valid @NotNull  OTPRequest request) {
        
      return userController.doResendOTPAdmin(request);
    }
    
    @POST
    @Path("/create-profile")
    //@JWTTokenNeeded
    public UserProfileResponse doCreateProfileXOX(@Valid final UserProfileRequest  request) {
    
       return userController.doCreateProfileXOX(request);
        
    }
    
    @POST
    @Path("/votp")
    //@JWTTokenNeeded
    public GenericResponse doVOTPXOX(@Valid final OTPRequest  request ) {
        
      return userController.doVOTPAdmin(request);
      
    }
    
    @POST
    @Path("/user-login")
    //@JWTTokenNeeded
    public ManagedLoginResponseV2 doAdminUserLoginXOX(@Valid UserLoginRequest  request ) {
        
      return userController.doVerifyAdmin(request);
    }
    
    @POST
    @Path("force-password-change")
    //@JWTTokenNeeded
    public ChangePasswordResponse doForcePasswordChangeXOX(@Valid InitForcePasswordChangeRequest json) {
    
     return userController.doInitForceChangePassword(json);
    }
    
    /*
    @POST
    @Path("votp-n-force-txp")
    //@JWTTokenNeeded
    public Response doPNForceTwPMXX(@Valid JsonObject json) {
    
     return userController.doForceTxP(json);
    
    }
    */
    
    @POST
    @Path("votp-n-force-change")
    //@JWTTokenNeeded
    public ForcePasswordChangeResponse doVerifyOTPAndForceChangeXOX(@Valid ForcePasswordChangeRequest json) {
    
     return userController.doForceChangePassword(json);
    }
    /*
    @POST
    @Path("/votp-sec")
    @JWTTokenNeeded
    public Response doVOTPSECXOX(@Valid JsonObject  jsonObj ) {
      
      return adminServicesController.doVOTPResyncDeviceFingerPrint(jsonObj);
    }
    */
    /*
    @POST
    @Path("/user-login-sec")
    @JWTTokenNeeded
    public Response doUserLoginSecXOX(@Valid String  jsonObj ) {
        
      return adminServicesController.doVerifySec(jsonObj);
    }
    */
    @POST
    @Path("change-pin")
   // @JWTTokenNeeded
    public ChangePasswordResponse doChangePinUSX(@Valid ChangePasswordRequest json) {
       
        return userController.doChangePassword(json);
          
    }
    
    @POST
    @Path("change-password")
    //@JWTTokenNeeded
    public ChangePasswordResponse doChangePasswordUSX(@Valid ChangePasswordRequest json)  // public Response doChangePasswordXOX(@Valid JsonObject json) 
    {
    
      return userController.doChangePassword(json);
    }
    
    
    @POST
    @Path("force-pin-change")
    //@JWTTokenNeeded
    public ChangeTXPResponse doForcePinChangeXOX(@Valid ForceTXPRequest json) {
    
    return  userController.doInitForceTxpChange(json);
    }
    
    @POST
    @Path("votp-n-force-change-pin")
   // @JWTTokenNeeded
    public Response doVTOPAndForceChangePinXOX(@Valid JsonObject json) {
        System.out.println(" doVTOPAndForceChangePinXOX json = " +   json);
        JsonObjectBuilder job = Json.createObjectBuilder();
        String msisdn = "";
        String email = "";
       // try 
       // { 
            
            
            return null;//adminServicesController. doValidateOTPAndForceTxpChange(json);
           
        
        /*
        } catch (Exception e) {
        
            
            e.printStackTrace();
            
              LOGGER.error(" Exception doChangePin--  ",e);
              
              return Response.status(ErrorCodes.SYSTEM_ERROR).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(ErrorCodes.SYSTEM_ERROR)).build()).build();
      
        
        
        }*/
        //return null;
    }
    
    @POST
    @Path("list-users-search")
    //@JWTTokenNeeded
    public UserProfileResponse doLoadUsersSearchXOX(@Valid FilterRequest json) {
        LOGGER.info("- doLoadUsersSearchXOX -- "+json);
       
         return  userController.doListUsers(json);
    }
    
    
    /*
    @POST
    @Path("force-password-change")
    @JWTTokenNeeded
    public Response doForcePasswordChangeXOX(@Valid JsonObject json) {
    
    return adminServicesController.doInitForceChangePassword(json);
    }
    */
    
}
