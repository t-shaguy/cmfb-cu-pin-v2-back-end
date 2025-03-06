/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paycraftsystems.cmfb.dto.ChangePasswordRequest;
import com.paycraftsystems.cmfb.dto.ChangePasswordRequestObj;
import com.paycraftsystems.cmfb.dto.ChangeUserPasswordRequest;
import com.paycraftsystems.cmfb.dto.FilterRequest;
import com.paycraftsystems.cmfb.dto.FilterRequestObj;
import com.paycraftsystems.cmfb.dto.ForcePasswordChangeRequest;
import com.paycraftsystems.cmfb.dto.ForcePasswordChangeRequestObj;
import com.paycraftsystems.cmfb.dto.ForceTXPRequest;
import com.paycraftsystems.cmfb.dto.ForceTXPRequestObj;
import com.paycraftsystems.cmfb.dto.InitForcePasswordChangeRequest;
import com.paycraftsystems.cmfb.dto.InitForcePasswordChangeRequestObj;
import com.paycraftsystems.cmfb.dto.NotificationRequest;
import com.paycraftsystems.cmfb.dto.OTPRequest;
import com.paycraftsystems.cmfb.dto.OTPRequestObj;
import com.paycraftsystems.cmfb.dto.RequestDTO;
import com.paycraftsystems.cmfb.dto.ResetUserPassword;
import com.paycraftsystems.cmfb.dto.ResetUserPasswordObj;
import com.paycraftsystems.cmfb.dto.SyncPasswordRequest;
import com.paycraftsystems.cmfb.dto.UserLoginRequest;
import com.paycraftsystems.cmfb.dto.UserLoginRequestObj;
import com.paycraftsystems.cmfb.dto.UserLoginRequestV2;
//import com.paycraftsystems.cmfb.dto.UserProfileDTO;
import com.paycraftsystems.cmfb.dto.UserProfileRequest;
import com.paycraftsystems.cmfb.dto.UserProfileRequestObj;
import com.paycraftsystems.cmfb.dto.response.ChangePasswordResponse;
import com.paycraftsystems.cmfb.dto.response.ChangeTXPResponse;
import com.paycraftsystems.cmfb.dto.response.ForcePasswordChangeResponse;
import com.paycraftsystems.cmfb.dto.response.GenericResponse;
import com.paycraftsystems.cmfb.dto.response.GenericResponseObj;
import com.paycraftsystems.cmfb.dto.response.InvalidRequestResponseObj;
import com.paycraftsystems.cmfb.dto.response.ResendOTPResponse;
import com.paycraftsystems.cmfb.dto.response.UserProfileResponse;
import com.paycraftsystems.cmfb.dto.response.ManagedLoginResponseV2;
import com.paycraftsystems.cmfb.dto.response.UserLogResponse;
import com.paycraftsystems.cmfb.dto.response.UserResponse;
import com.paycraftsystems.cmfb.email.resources.MailProcessor;
import com.paycraftsystems.cmfb.entities.FailedLoginHistory;
import com.paycraftsystems.cmfb.entities.FailedLoginInfo;
import com.paycraftsystems.cmfb.entities.OTPLog;
import com.paycraftsystems.cmfb.entities.RolesInfo;
import com.paycraftsystems.cmfb.entities.UserLog;
import com.paycraftsystems.cmfb.entities.UserProfile;
import com.paycraftsystems.cmfb.enumz.OTPStatus;
import com.paycraftsystems.cmfb.enumz.ResourceStatusEnum;
import com.paycraftsystems.cmfb.repositories.UserLogRepository;
import com.paycraftsystems.cmfb.repositories.UserRepository;
import com.paycraftsystems.cmfb.services.AuthService;
import com.paycraftsystems.exceptions.CMFBException;
import com.paycraftsystems.exceptions.InvalidRequestException;

import com.paycraftsystems.resources.ErrorCodes;
import com.paycraftsystems.resources.RandomCharacter;
import com.paycraftsystems.resources.ValidationHelper;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import static io.quarkus.logging.Log.info;
import io.quarkus.panache.common.Page;
import io.smallrye.common.constraint.NotNull;
import jakarta.ws.rs.WebApplicationException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author paycraftsystems-i
 */

@ApplicationScoped
@Slf4j
public class UserController {
    
    
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    
    
    @Inject
    SysDataController sysDataHelper;
    
    @Inject
    UserLogRepository userLogRepo;
    
    @Inject
    ESEQRepository eSEQHelper;
    
    @Inject
    RolesInfoHelper rolesInfoHelper;
   
    
    @Inject
    UserRepository  userRepository;
    
    @Inject
    OTPController otpController;
    
    
   // @Inject
   // EntityManager em;
    
    @Inject
    AuthService authservice;
    
    @Inject
    OTPController oTPController;
    
    @Inject
    MailProcessor mailProcessor;
    
    
    @ConfigProperty(name="dashboard.url")
    String dashboardUrl;
    
    @ConfigProperty(name="admin.mail.addy")
    String adminMail;
    
    
    ValidationHelper rh = new ValidationHelper();
    
    /*
    
    @Transactional
    public Response doVerify(@Valid JsonObject json) {
        
        LOGGER.info("- doVerify - "+(json==null));
        
        try
        {
        
             UserProfileDTO fromJson = JsonbBuilder.create().fromJson(json.toString(), UserProfileDTO.class);
            
             if(!rh.isWithinValidLen(fromJson.emailAddress,70))
             {
                 return Response.status(ErrorCodes.INVALID_USERNAME).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USERNAME)).build()).build();
             }
             
             if(!rh.isValid(fromJson.password))
             {
                 return Response.status(ErrorCodes.INVALID_PASSWORD).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INVALID_PASSWORD)).build()).build();
             }

            
            UserProfile doLookup = doLookupByUserName(fromJson.emailAddress);
            
            LOGGER.info(" @-## - doLookup - "+doLookup);
            
            if(doLookup != null && doLookup.status == 1)
            {
              
                              //LOGGER.info(" inside ## - doLookup - "+doLookup);
                             JsonObject authSync = Json.createObjectBuilder().add("code", rh.toDefault(doLookup.emailAddress)).add("codeLink", rh.toDefault(doLookup.emailAddress)).add("password", rh.toDefault(fromJson.password)).add("controlCode", rh.toDefault(doLookup.userRoleStr)).add("msisdn", rh.toDefault(doLookup.mobileNo)).build();
                            
                             int status = 0;
                             try 
                             {
                                
                                Response authResp = authClient.doVerifyUser(authSync);
                                
                                 //System.out.println("authResp = " + authResp.getStatus());
                                 
                                if(authResp !=null) LOGGER.info(" ---- authSync authResp ---- "+authResp.getStatus());
                               
                                if(authResp !=null && authResp.getStatus() == ErrorCodes.ACCEPTED)
                                {
                                    
                                    
                                    ProfileSyncObj profileSyncObj = JsonbBuilder.create().fromJson(authResp.readEntity(JsonObject.class).toString(), ProfileSyncObj.class);
                                   
                                    LOGGER.info(" *** --- authResp.getHeaderString(Authorization)  --- "+authResp.getHeaderString("Authorization")==null?"NOT PROVIDED":"PROVIDED");
                                    
                                    WalletInfo doLookupByMSISDN = walletInfoController.doLookupByMSISDN(doLookup.phonePri);
                                     
                                    String mappedAccount = (doLookupByMSISDN == null)?"NA":doLookupByMSISDN.mappedAccount;
                                    
                                    //check the fee from scheme
                                    
                                    String ftFee = "50.00";
                                    String billpaymentfee = "50.00";
                                    String enquiryFee = "0.0";
                                     
                                    return Response.ok().entity(doLookup.toJson(authResp.getHeaderString("Authorization"), profileSyncObj.lastLoginDate,mappedAccount, ftFee, billpaymentfee, enquiryFee)).header(AUTHORIZATION, "Bearer " +  authResp.getHeaderString("Authorization")).build();
                                  
                                }
                                else if(authResp !=null && authResp.getStatus() != ErrorCodes.ACCEPTED)
                                {
                                    //return authResp;
                                    
                                    return Response.status(authResp.getStatus()).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(authResp.getStatus())).build()).build();
                                }
                                else
                                {
                                    return Response.status(ErrorCodes.SYSTEM_ERROR).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR)).build()).build();
                                }
                                
                                 
                             } catch (WebApplicationException e) {
                            
                                  status = e.getResponse().getStatus();
                                  
                                  LOGGER.info(" -#- doVerify -#- "+status);
                                   
                                  return Response.status(status).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(status)).build()).build();
                             }
                         
            }
            else if( doLookup != null && doLookup.status == 2)//doLookup != null && doLookup.status == 4  || 
            {
                 return Response.status(ErrorCodes.INACTIVE_USER).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INACTIVE_USER)).build()).build();
            }
            else if( doLookup != null && doLookup.status == 11)//doLookup != null && doLookup.status == 4  || 
            {
                 return Response.status(ErrorCodes.PASSWORD_CHANGE_REQUIRED).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.PASSWORD_CHANGE_REQUIRED)).build()).build();
            }
            else if( doLookup != null && doLookup.status == 12)//doLookup != null && doLookup.status == 4  || 
            {
                 return Response.status(ErrorCodes.PIN_CHANGE_REQUIRED).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.PIN_CHANGE_REQUIRED)).build()).build();
            }
            else if(doLookup != null && doLookup.status == 4  ||  doLookup != null && doLookup.status == 3)
            {
                 return Response.status(ErrorCodes.FORBIDDEN_ACTION).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.FORBIDDEN_ACTION)).build()).build();
            }
            else if(doLookup != null && doLookup.status == Long.parseLong("13") )
            {
                 LOGGER.info(" WILL HAVE TO RESYNC SYNC WALLET  ");
                 JsonObjectBuilder job = Json.createObjectBuilder();
                                job.add("accountNumber", doLookup.phonePri.replaceAll("\\+234", ""))
                                .add("msisdn", doLookup.phonePri)
                                .add("accountName", doLookup.fullName);
                                             
                 Response doWalletSync = doWalletSync(doLookup, job.build());
                 
                 if(doWalletSync !=null) LOGGER.info(" RESYNC SYNC WALLET "+doWalletSync.getStatus());
                 else
                 {
                     LOGGER.error(" ERROR --> RESYNC SYNC WALLET NULL "+doWalletSync);
                 }
                 
                 if(doWalletSync != null && doWalletSync.getStatus() == ErrorCodes.SUCCESSFUL)
                 {
                     
                    return  doProcessLogin(doLookup, fromJson.password);// Response.status(sts).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(sts)).build()).build();
                 }
                 else
                 {
                     return doWalletSync;
                 }
            }
            else if(doLookup != null && doLookup.status != 0 )
            {
                 int sts = Integer.parseInt(""+doLookup.status);
                 return Response.status(sts).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(sts)).build()).build();
            }
            else
            {
                  //System.out.println("-#####-- doLookup = " +  doLookup);   
                 return Response.status(ErrorCodes.INVALID_USER_OR_PASSWORD).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USER_OR_PASSWORD)).build()).build();
            }
                
            
        } 
        catch (JsonbException e) {
        
            LOGGER.error("  -- doVerify JsonbException -- ",e);
            
             throw new HelloException(ErrorCodes.SYSTEM_ERROR, e.getMessage());
        }
        catch (Exception e) {
            
               LOGGER.error("  -- doVerify Exception -- ",e);
        
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException || (e.getCause() instanceof jakarta.persistence.PersistenceException)))
                {
                     throw new HelloException(ErrorCodes.DATABASE_ERROR, e.getMessage());
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     throw new HelloException(ErrorCodes.IO_EXCEPTION, e.getMessage());
                }
                else
                {
                     throw new HelloException(ErrorCodes.SYSTEM_ERROR, e.getMessage());
                }
            
        }
        
    }
    
    */
    
    @Transactional
     public GenericResponse doVOTPAdmin(@Valid OTPRequest json) {
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
      
        LOGGER.info(" --doVOTPAdmin--  "+json);
        int resp = 0;
        try 
        {
          
            OTPRequestObj fromJson = new OTPRequestObj(json);
            if(fromJson != null)
            {
                 
                    /*
                    if(!fromJson.password.equals(fromJson.verifyPassword))
                    {
                      
                        return Response.status(ErrorCodes.PASSWORD_MISMATCH).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.PASSWORD_MISMATCH, prodOrDev)).build()).build();
    
                    }
                    */
                    UserProfile doLookup = userRepository.doFindByEmailAddress(fromJson.emailAddress);//.doFindByEmailAddress(fromJson.emailAddress);//loadByMobileAndAccountNo(fromJson.mobileNo, fromJson.accountNo);// doLookupByMSISDN(fromJson.getPhoneNumberPri());  //fromJson.getEmailAddress()

                    if(doLookup != null && doLookup.status !=null && (doLookup.status.equalsIgnoreCase("INIT_FORCE_PASSWORD_CHANGE") || doLookup.status.equalsIgnoreCase("INACTIVE")))
                    {

                             if("INACTIVE".equals(doLookup.status))// == 10)
                             {
                                // doLogOTP(merge.mobileNo, merge.emailAddress, otp);
                                 OTPLog doVerifyOTP = OTPLog.doLookUpByEmailAndOTP(fromJson.emailAddress, doLookup.mobileNo, fromJson.otp);
                                 
                                 LOGGER.info("-- OTP CHECK doVerifyOTP  "+doVerifyOTP);
                                 if(doVerifyOTP !=null )
                                 {
                                     
                                     LocalDateTime otpDate = (doVerifyOTP.createdDate !=null && doVerifyOTP.updatedDate== null)?doVerifyOTP.createdDate:doVerifyOTP.updatedDate;
                                     
                                     LOGGER.info("OTP CHECK otpDate  "+otpDate);
                                     
                                     long until = (otpDate !=null)? otpDate.until(LocalDateTime.now(), ChronoUnit.MINUTES):0;
                
                                     LOGGER.info("OTP TIME DIFF "+until);
                                      // OTP-TIME-OUT
                                     if(until > Integer.parseInt(sysDataHelper.getProps("OTP-TIME-OUT", "0")))
                                     {
                                                LOGGER.info(" EXPIRED OTP TRAPPED -- "+fromJson);
                                         
                                                OTPLog OTPLog = new OTPLog();
                                                if("1".equals(sysDataHelper.getProps("INTEGRATION-MODE")))
                                                {
                                                     OTPLog.otp = sysDataHelper.getProps("INTEGRATION-PIN");
                                                }
                                                else  
                                                {
                                                     OTPLog.otp = RandomCharacter.doRandomNum(Integer.parseInt(sysDataHelper.getProps("DEFAULT-OTP-LENGTH", "0")));//.substring(0, 4);;
                                                }
                                         
                                         
                                                RequestDTO reqst  =  new RequestDTO();
                                                reqst.setAmount("0.0");
                                                reqst.setNarration("Expired OTP Resync ");
                                                reqst.setSrcAccount(fromJson.accountNo);
                                                reqst.setMsisdn(fromJson.mobileNo);
                                                reqst.setOTP(OTPLog.otp);
                                                reqst.setMessage("Your OTP is "+OTPLog.otp+" Kindly note that, it expires in 10 minutes, Please do not disclose");

                                              //  send mail 
                                                        
                                                String otpmsg = "Your OTP is "+OTPLog.otp+" Kindly note that, it expires in "+sysDataHelper.getProps("OTP-TIME-OUT", "0")+" minutes, Please do not disclose";


                                                NotificationRequest mailinfo = new NotificationRequest();
                                                mailinfo.toShare = OTPLog.otp;
                                                mailinfo.toShare1 = otpmsg;
                                                mailinfo.addressee = doLookup.lastName;
                                                mailinfo.sendTo = fromJson.emailAddress;
                                                mailinfo.message = otpmsg;
                                                
                                                CompletionStage<Boolean> sendAdminWelcomeEmailAsync = mailProcessor.sendAdminWelcomeEmailAsync(mailinfo);
                                                LOGGER.info(" -- sendAdminWelcomeEmailAsync RESEND OTP --  "+sendAdminWelcomeEmailAsync);
                                                
                                                 
                                                
                                     }

                                     //doLookup
                                     int doSyncProfile = doSyncProfileAdmin(doLookup.mobileNo, fromJson.password,  doLookup.emailAddress, doLookup.tid, sysDataHelper.getProps("CHANNEL", "0"), doLookup.userRoleStr);
                                     
                                      LOGGER.info(" -- ::: doSyncProfile >>>>> = " + doSyncProfile);

                                      if(doSyncProfile == ErrorCodes.ACCEPTED || doSyncProfile == ErrorCodes.SUCCESSFUL)
                                      {
                                          
                                          otpController.doStampOTP(doLookup,  fromJson, OTPStatus.USED.name());
                                       
                                         
                                         return new  GenericResponse(true, ErrorCodes.ACCEPTED, ErrorCodes.doErrorDesc(ErrorCodes.ACCEPTED), null, null);

                                      }
                                      else if(doSyncProfile == ErrorCodes.DUPLICATE_TRANSACTION)
                                      {
                                         
                                           return new  GenericResponse(false, ErrorCodes.USER_ALREADY_EXIST, ErrorCodes.doErrorDesc(ErrorCodes.USER_ALREADY_EXIST), null, null);
                                      }
                                      else
                                      {
                                          if(doSyncProfile == ErrorCodes.COMM_LINK)
                                          {
                                             int doVerifyOTPRollback = OTPLog.doVerifyOTPRollback(fromJson.mobileNo, fromJson.otp);

                                             LOGGER.info("ROLLBACK -OTP - STATUS --"+doVerifyOTPRollback);
                                          }
                                         
                                          return new  GenericResponse(false, doSyncProfile, ErrorCodes.doErrorDesc(doSyncProfile), null, null);
                                    
                                      }

                                 }
                                 else
                                 {
                                     
                                    return new  GenericResponse(false, ErrorCodes.INVALID_TOKEN, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_TOKEN), null, null);
                                    
                                 }

                             }
                             else
                             {
                                 
                                   return new  GenericResponse(false, ErrorCodes.TRANSACTION_FORBIDDEN, ErrorCodes.doErrorDesc(ErrorCodes.TRANSACTION_FORBIDDEN), null, null);
                                    
                             }

                    }
                    else
                    {
                        // return Response.status(ErrorCodes.INVALID_USERNAME).build();
                      
                        return new  GenericResponse(false, ErrorCodes.INVALID_USERNAME, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USERNAME), null, null);
                                   
                    
                    }
            
            }
            else
            {
              
                return new  GenericResponse(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null, null);
                       
            }
                
            
        }/*
        catch (JsonbException e) {
        
              LOGGER.error(" JsonbException @ doVOTP-- ",e);
              return Response.status(ErrorCodes.FORMAT_ERROR).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR, prodOrDev)).build()).build();
    
        }*/
        catch (Exception e) {
        
             LOGGER.error(" Exception @ doVOTP-- ",e);
           
            return new  GenericResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, null);
            
        }
       
    }
    
    public UserProfile doLookupByUserName(String username) {
        
        UserProfile obj = null;
        try 
        {
            obj = userRepository.doFindByUsername(username);
                   
            
        } catch (Exception e) {
        
          e.printStackTrace();
        
        }
     
        return obj;  
        
    }
    
    public UserProfile doLookupByMSISDN(String msisdn) {
        
        UserProfile obj = null;
        try 
        {
            obj = userRepository.loadByMobile(msisdn);
                    
        } catch (Exception e) {
        
          e.printStackTrace();
        
        }
     
        return obj;  
        
    }
    
     /*
    @Transactional
    public Response doSyncSelf(@Valid JsonObject json) {
       
        try
        {
        
            UserProfileDTO fromJson = JsonbBuilder.create().fromJson(json.toString(), UserProfileDTO.class);
            
            LOGGER.info(" -- UserProfileDTO -- "+fromJson);
            
            if(!rh.isValidEmail(fromJson.emailAddress))
            {
                return Response.status(ErrorCodes.INVALID_EMAIL).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INVALID_EMAIL)).build()).build();
            }
         
            if(!rh.isValidPhoneNumber(fromJson.mobileNo))
            {
               return Response.status(ErrorCodes.INVALID_MSISDN).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INVALID_MSISDN)).build()).build();
            }

            
            UserProfile doLookup =  UserProfile.doFindByUsername(fromJson.mobileNo);//.doFindByMsisdnAndEmail(fromJson.phonePri, fromJson.emailAddress);// doUserLookUp(fromJson);
            
            if(doLookup != null)
            {
                //UserProfile obj = new UserProfile();
                        UserProfile doFindByEmail = UserProfile.doFindByEmailAddress(fromJson.emailAddress);
                        
                        if(doFindByEmail  !=null && doFindByEmail.tid != doLookup.tid)
                        {
                            return Response.status(ErrorCodes.DUPLICATE_EMAIL_ADDRESS).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.DUPLICATE_EMAIL_ADDRESS)).build()).build();
                        }
                       
                         //obj.setCode(genCode);
                         //doLookup.setEmailAddress(fromJson.getEmailAddress());
                         doLookup.firstName = fromJson.firstName;
                       //  doLookup.fullName = fromJson.fullName;//.getFullName();
                         doLookup.lastName = fromJson.lastName;
                         doLookup.middleName = fromJson.middleName==null?"NA":fromJson.middleName;
                         
                         if(doLookup.full_name == null)
                         {
                              doLookup.full_name = fromJson.lastName+" "+doLookup.middleName+" "+fromJson.firstName;
                         }
                       
                         doLookup.status = BigInteger.ONE.longValue();
                         doLookup.statusStr = Status.doStatusDescById(doLookup.status);
                        
                         UserProfile merge = UserProfile.doLog(doLookup);
                         
                         if(merge !=null)
                         {
                             
                            return Response.status(ErrorCodes.ACCEPTED).entity(merge.toJson()).build();
                             
                         }
                         else
                         {
                              return Response.status(ErrorCodes.DATABASE_ERROR).build();
                         }
           
            }
            else
            {
                 return Response.status(ErrorCodes.INVALID_USERNAME).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USERNAME)).build()).build();
            }
                
            
        } 
        catch (JsonbException e) {
        
             //e.printStackTrace();
             LOGGER.info(" JsonbException in doSync x ", e);
            return Response.status(ErrorCodes.FORMAT_ERROR).build();
        }
        catch (Exception e) {
        
             //e.printStackTrace();
              LOGGER.info(" Exception in doSync x ", e);
               if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                     throw new HelloException(ErrorCodes.DATABASE_ERROR, e.getMessage());
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     throw new HelloException(ErrorCodes.IO_EXCEPTION, e.getMessage());
                }
                else
                {
                     throw new HelloException(ErrorCodes.SYSTEM_ERROR, e.getMessage());
                }
            
        }
        
    }
   
    @Transactional
    public Response doSync(@Valid JsonObject json) {
       LOGGER.info(" -- UserProfileDTO doSync-- "+json);
        try
        {
        
            UserProfileDTO fromJson = JsonbBuilder.create().fromJson(json.toString(), UserProfileDTO.class);
            
            LOGGER.info(" -- UserProfileDTO -- "+fromJson);
            
            if(!rh.isValidEmail(fromJson.userName))
            {
                return Response.status(ErrorCodes.INVALID_EMAIL).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INVALID_EMAIL)).build()).build();
            }
         
            if(fromJson.phonePri !=null && !rh.isValidPhoneNumber(fromJson.phonePri))
            {
               return Response.status(ErrorCodes.INVALID_PHONE_NO).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INVALID_PHONE_NO)).build()).build();
            }

            
            UserProfile doLookup =  UserProfile.doFindByUsername(fromJson.userName);//fromJson.emailAddress);//.doFindByMsisdnAndEmail(fromJson.phonePri, fromJson.emailAddress);// doUserLookUp(fromJson);
            
            if(doLookup != null)
            {
                //UserProfile obj = new UserProfile();
                        UserProfile doFindByEmail = UserProfile.doFindByEmail(fromJson.userName);
                        
                        if(doFindByEmail  !=null && doFindByEmail.tid != doLookup.tid)
                        {
                            return Response.status(ErrorCodes.DUPLICATE_EMAIL_ADDRESS).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.DUPLICATE_EMAIL_ADDRESS)).build()).build();
                        }
                         
                        
                         //obj.setCode(genCode);
                         //doLookup.setEmailAddress(fromJson.getEmailAddress());
                         doLookup.firstName = fromJson.firstName;
                         doLookup.fullName = fromJson.fullName;//();
                         doLookup.lastName = fromJson.lastName;
                         doLookup.middleName = (fromJson.middleName==null)?"NA":fromJson.middleName;
                         
                         LOGGER.info(" --fromJson.state -- "+fromJson.state);
                         if(fromJson.state  > 0)
                         {
                             doLookup.state = fromJson.state;
                             doLookup.stateStr = States.doGetStateNamebyID(doLookup.state);
                         }
                          LOGGER.info(" --fromJson.partnerCode -- "+fromJson.partnerCode);
                         if(rh.isValid(fromJson.partnerCode))
                         {
                             doLookup.partnerCode = fromJson.partnerCode;
                         }
                         
                          //doLookup.phoneSec = fromJson.phoneSec;
                         doLookup.phoneSec = fromJson.phoneSec;
                         doLookup.status = BigInteger.ONE.longValue();
                         doLookup.statusStr = Status.doStatusDescById(doLookup.status);
                        // doLookup.subcriberId = "NA";
                        
                         if(doLookup.fullName == null)
                         {
                              doLookup.fullName = fromJson.lastName+" "+doLookup.middleName+" "+fromJson.firstName;
                         }
                        
                         UserProfile merge = UserProfile.doLog(doLookup);
                         
                         if(merge !=null)
                         {
                             
                            return Response.status(ErrorCodes.ACCEPTED).entity(merge.toJson()).build();
                             
                         }
                         else
                         {
                              return Response.status(ErrorCodes.DATABASE_ERROR).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR)).build()).build();
                         }
           
            }
            else
            {
                 return Response.status(ErrorCodes.INVALID_USERNAME).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USERNAME)).build()).build();
            }
                
            
        } 
        catch (JsonbException e) {
        
             //e.printStackTrace();
             LOGGER.info(" JsonbException in doSync x ", e);
            return Response.status(ErrorCodes.FORMAT_ERROR).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR))).build();
        }
        catch (Exception e) {
        
             //e.printStackTrace();
              LOGGER.info(" Exception in doSync x ", e);
               if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                     throw new HelloException(ErrorCodes.DATABASE_ERROR, e.getMessage());
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     throw new HelloException(ErrorCodes.IO_EXCEPTION, e.getMessage());
                }
                else
                {
                     throw new HelloException(ErrorCodes.SYSTEM_ERROR, e.getMessage());
                }
            
        }
        
        
    }
    */
    
    public UserLogResponse doCreateProfileXOXV2(@Valid UserProfileRequest json) {
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
        
        LOGGER.info(" @@@@-: doCreateProfileXOX -: "+json);
        String otp = "";
        UserProfile userProfile =  null;
        List<UserLog> profiles = new ArrayList<>();
        //NotificationsFeed doLog = null;
        try 
        {
            UserProfileRequestObj fromJson = new UserProfileRequestObj(json);
            log.info(" -- fromJson -- "+fromJson);
            if(fromJson == null)
            {
               
                 return new UserLogResponse(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null, null);
            
            }
            else
            {
                        userProfile = userRepository.loadByTellerParams(fromJson.lastName, fromJson.mobileNo, fromJson.tilAccount);//, fromJson.accountNo);
                        
                        LOGGER.info(" -- userProfile --  "+userProfile);
                        if(userProfile != null)
                        {
                            return new UserLogResponse(false, ErrorCodes.USER_PROFILE_EXISTS, ErrorCodes.doErrorDesc(ErrorCodes.USER_PROFILE_EXISTS), null, null);
            
                        }
                        /*
                        UserLog doFindByTID = userLogRepo.doFindByTID(fromJson.actionBy);
                        log.info(" @@@ --- "+doFindByTID);
                        if(doFindByTID == null || (fromJson != null && fromJson.userRole == 1 || doFindByTID.userRole == fromJson.userRole))
                        {
                            return new UserLogResponse(false, ErrorCodes.TRANSACTION_FORBIDDEN, ErrorCodes.doErrorDesc(ErrorCodes.TRANSACTION_FORBIDDEN)+" - You cannot create a user with the same role as you", null, null);
            
                        }
                        */
                        if(sysDataHelper.getProps("INTEGRATION-MODE", "0").equals("1"))
                        {

                            otp = sysDataHelper.getProps("INTEGRATION-PIN", "0000");
                        }
                        else
                        {
                            otp = String.valueOf(new Random().nextInt(1234567890)).substring(0, 4);
                        }
                
                        LOGGER.info(" - CREATE PROFILE  DEFAULT PIN "+otp);
                        
                        String otpmsg = "Your OTP is "+otp+" Kindly note that, it expires in "+sysDataHelper.getProps("OTP-TIME-OUT", "0")+" minutes, Please do not disclose";
                        /*
                        UserProfile profile = new UserProfile();
                        
                        profile.createdDate = LocalDateTime.now();
                        profile.emailAddress = fromJson.emailAddress;
                        profile.firstName = fromJson.firstName;
                        profile.full_name = fromJson.lastName+" "+((fromJson.middleName ==null)?"":fromJson.middleName)+" "+fromJson.firstName;
                        profile.lastName = fromJson.lastName;
                        profile.loginStatus = 0;
                        profile.mobileNo = fromJson.mobileNo;
                        profile.tilAccount   = fromJson.tilAccount;
                        profile.status = ResourceStatusEnum.INACTIVE.name();// BigInteger.TEN.longValue();
                        //profile.statusStr = Status.doStatusDescById(profile.status);
                        profile.userRole = fromJson.userRole;
                        profile.userRoleStr = RolesInfo.doFindRoleDescByCode(profile.userRole);
                        */
                        log.info("--++  here -- ** @@");
                       
                        
                        UserProfile merge = userLogRepo.doLogV2(fromJson, otp, otpmsg);// userLogRepo.doLog(fromJson, otp, otpmsg);//, fromJson.userRole);// Panache.getEntityManager().merge(user);  UserProfile.doLog(fromJson, fromJson.userRole);

                        //profiles.add(merge);
                        LOGGER.info(" **-  #### merge = " + merge);

                             
                         return new UserLogResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), merge, null);
                           
                         /*
                            if(merge == null)
                            {
                                return new UserProfileResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null, null);
                            }
                            else
                            {
                         
                                  NotificationRequest mailinfo = new NotificationRequest();
                        mailinfo.toShare = otp;
                        mailinfo.toShare1 = otpmsg;
                        mailinfo.addressee = fromJson.lastName;
                        mailinfo.sendTo = fromJson.emailAddress;
                        mailinfo.message = otpmsg;
                                                      //System.out.println(" $$$$$$$ ");
                                                   // return Response.ok().entity(merge.toJson()).build();
                                                      int doLogOTP =  otpController.doLogOTP(merge.mobileNo, merge.emailAddress, otp, OTPStatus.ACTIVE.name()); 
                                                      System.out.println(" ** doLogOTP = " + doLogOTP);
                                                      if(doLogOTP == ErrorCodes.SUCCESSFUL)
                                                      {
                                                          CompletionStage<Boolean> sendAdminWelcomeEmailAsync = mailProcessor.sendAdminWelcomeEmailAsync(mailinfo);
                                                          LOGGER.info(" -- sendAdminWelcomeEmailAsync --  "+sendAdminWelcomeEmailAsync);
                                                          
                                                         // return Response.ok().entity(merge.toJson()).build();
                                                          
                                                         return new UserProfileResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), profiles, null);
                           
                                                      }
                                                      else
                                                      {
                                                          System.out.println("doLogOTP = ????? "+merge);
                                                         // merge.delete();
                                                          
                                                          return new UserProfileResponse(false, doLogOTP, ErrorCodes.doErrorDesc(doLogOTP), profiles, null);
                           
                                                      }
                              
                            
                                 }
                 
                   */
            
            }
           
        }
        catch(WebApplicationException e)
        {
         
           return new UserLogResponse(false, e.getResponse().getStatus(), ErrorCodes.doErrorDesc(e.getResponse().getStatus()), profiles, null);
          
        }
        catch (Exception e) {
       
               LOGGER.info(" -!!-  doCreateProfile Exception ",e);
           // e.printStackTrace();
               if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException || (e.getCause() instanceof org.hibernate.exception.DataException)))
                {
                    
                    return new UserLogResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR)+"-"+e.getMessage(), profiles, null);
           
                }//
                else if((e.getCause() instanceof java.net.ConnectException))
                {
                   
                    return new UserLogResponse(false, ErrorCodes.COMM_LINK, ErrorCodes.doErrorDesc(ErrorCodes.COMM_LINK)+"-"+e.getMessage(), profiles, null);
           
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                    
                    return new UserLogResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), profiles, null);
           
                }
                else
                {
                   
                     return new UserLogResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), profiles, null);
           
                }
        }
    }
    
    @Transactional
    public UserLogResponse doCreateProfileXOXV2XXX(@Valid UserProfileRequest json) {
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
        
        LOGGER.info(" @@@@-: doCreateProfileXOX -: "+json);
        String otp = "2222";
        UserProfile userProfile =  null;
        List<UserLog> profiles = new ArrayList<>();
        //NotificationsFeed doLog = null;
        try 
        {
            UserProfileRequestObj fromJson = new UserProfileRequestObj(json);
            log.info(" -- fromJson -- "+fromJson);
                                  
                
                        LOGGER.info(" - CREATE PROFILE  DEFAULT PIN "+otp);
                        
                        String otpmsg = "Your OTP is "+otp+" Kindly note that, it expires in "+sysDataHelper.getProps("OTP-TIME-OUT", "0")+" minutes, Please do not disclose";
                       
                        long merge =  userLogRepo.doLog(fromJson, otp, otpmsg);// userLogRepo.doLog(fromJson, otp, otpmsg);//, fromJson.userRole);// Panache.getEntityManager().merge(user);  UserProfile.doLog(fromJson, fromJson.userRole);

                        //profiles.add(merge);
                        LOGGER.info(" **-  #### merge = " + merge);

                             
                         return new UserLogResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), merge, null);
                        
            
            
           
        }
        catch(WebApplicationException e)
        {
            e.printStackTrace();
           return new UserLogResponse(false, e.getResponse().getStatus(), ErrorCodes.doErrorDesc(e.getResponse().getStatus()), profiles, null);
          
        }
        catch (Exception e) {
               e.printStackTrace();
               LOGGER.info(" -!!-  doCreateProfile Exception ",e);
           // e
            return new UserLogResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR)+"-"+e.getMessage(), profiles, null);
           
        }
    }
    
    
     @Transactional
    public UserProfileResponse doCreateProfileXOX(@Valid UserProfileRequest json) {
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
        
        LOGGER.info(" @@@@-: doCreateProfileXOX -: "+json);
        String otp = "";
        UserProfile userProfile =  null;
        List<UserProfile> profiles = new ArrayList<>();
        //NotificationsFeed doLog = null;
        try 
        {
            UserProfileRequestObj fromJson = new UserProfileRequestObj(json);
            log.info(" -- fromJson -- "+fromJson);
            if(fromJson == null)
            {
               
                 return new UserProfileResponse(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null, null);
            
            }
            else
            {
                        userProfile = userRepository.loadByTellerParams(fromJson.lastName, fromJson.mobileNo, fromJson.tilAccount);//, fromJson.accountNo);
                        
                        LOGGER.info(" -- userProfile --  "+userProfile);
                        if(userProfile != null)
                        {
                            return new UserProfileResponse(false, ErrorCodes.USER_PROFILE_EXISTS, ErrorCodes.doErrorDesc(ErrorCodes.USER_PROFILE_EXISTS), null, null);
            
                        }
                        
                        UserProfile doFindByTID = userRepository.doFindByTID(fromJson.actionBy);
                        log.info(" @@@ --- "+doFindByTID);
                        if(doFindByTID == null || (fromJson != null && fromJson.userRole == 1 || doFindByTID.userRole == fromJson.userRole))
                        {
                            return new UserProfileResponse(false, ErrorCodes.TRANSACTION_FORBIDDEN, ErrorCodes.doErrorDesc(ErrorCodes.TRANSACTION_FORBIDDEN)+" - You cannot create a user with the same role as you", null, null);
            
                        }
                    
                        if(sysDataHelper.getProps("INTEGRATION-MODE", "0").equals("1"))
                        {

                            otp = sysDataHelper.getProps("INTEGRATION-PIN", "0000");
                        }
                        else
                        {
                            otp = String.valueOf(new Random().nextInt(1234567890)).substring(0, 4);
                        }
                
                        LOGGER.info(" - CREATE PROFILE  DEFAULT PIN "+otp);
                        
                        String otpmsg = "Your OTP is "+otp+" Kindly note that, it expires in "+sysDataHelper.getProps("OTP-TIME-OUT", "0")+" minutes, Please do not disclose";
                        /*
                        UserProfile profile = new UserProfile();
                        
                        profile.createdDate = LocalDateTime.now();
                        profile.emailAddress = fromJson.emailAddress;
                        profile.firstName = fromJson.firstName;
                        profile.full_name = fromJson.lastName+" "+((fromJson.middleName ==null)?"":fromJson.middleName)+" "+fromJson.firstName;
                        profile.lastName = fromJson.lastName;
                        profile.loginStatus = 0;
                        profile.mobileNo = fromJson.mobileNo;
                        profile.tilAccount   = fromJson.tilAccount;
                        profile.status = ResourceStatusEnum.INACTIVE.name();// BigInteger.TEN.longValue();
                        //profile.statusStr = Status.doStatusDescById(profile.status);
                        profile.userRole = fromJson.userRole;
                        profile.userRoleStr = RolesInfo.doFindRoleDescByCode(profile.userRole);
                        */
                        log.info("--++  here -- ** ");
                        NotificationRequest mailinfo = new NotificationRequest();
                        mailinfo.toShare = otp;
                        mailinfo.toShare1 = otpmsg;
                        mailinfo.addressee = fromJson.lastName;
                        mailinfo.sendTo = fromJson.emailAddress;
                        mailinfo.message = otpmsg;
                        
                        UserProfile merge = userRepository.doLog(fromJson, otp, otpmsg);//, fromJson.userRole);// Panache.getEntityManager().merge(user);  UserProfile.doLog(fromJson, fromJson.userRole);

                        profiles.add(merge);
                        LOGGER.info(" **-  #### merge = " + merge);

                             
                         return new UserProfileResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), profiles, null);
                           
                         /*
                            if(merge == null)
                            {
                                return new UserProfileResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null, null);
                            }
                            else
                            {
                                                      //System.out.println(" $$$$$$$ ");
                                                   // return Response.ok().entity(merge.toJson()).build();
                                                      int doLogOTP =  otpController.doLogOTP(merge.mobileNo, merge.emailAddress, otp, OTPStatus.ACTIVE.name()); 
                                                      System.out.println(" ** doLogOTP = " + doLogOTP);
                                                      if(doLogOTP == ErrorCodes.SUCCESSFUL)
                                                      {
                                                          CompletionStage<Boolean> sendAdminWelcomeEmailAsync = mailProcessor.sendAdminWelcomeEmailAsync(mailinfo);
                                                          LOGGER.info(" -- sendAdminWelcomeEmailAsync --  "+sendAdminWelcomeEmailAsync);
                                                          
                                                         // return Response.ok().entity(merge.toJson()).build();
                                                          
                                                         return new UserProfileResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), profiles, null);
                           
                                                      }
                                                      else
                                                      {
                                                          System.out.println("doLogOTP = ????? "+merge);
                                                         // merge.delete();
                                                          
                                                          return new UserProfileResponse(false, doLogOTP, ErrorCodes.doErrorDesc(doLogOTP), profiles, null);
                           
                                                      }
                              
                            
                                 }
                 
                   */
            
            }
           
        }
        catch(WebApplicationException e)
        {
         
           return new UserProfileResponse(false, e.getResponse().getStatus(), ErrorCodes.doErrorDesc(e.getResponse().getStatus()), profiles, null);
          
        }
        catch (Exception e) {
       
               LOGGER.info(" -!!-  doCreateProfile Exception ",e);
           // e.printStackTrace();
               if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException || (e.getCause() instanceof org.hibernate.exception.DataException)))
                {
                    
                    return new UserProfileResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR)+"-"+e.getMessage(), profiles, null);
           
                }//
                else if((e.getCause() instanceof java.net.ConnectException))
                {
                   
                    return new UserProfileResponse(false, ErrorCodes.COMM_LINK, ErrorCodes.doErrorDesc(ErrorCodes.COMM_LINK)+"-"+e.getMessage(), profiles, null);
           
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                    
                    return new UserProfileResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), profiles, null);
           
                }
                else
                {
                   
                     return new UserProfileResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), profiles, null);
           
                }
        }
    }
    
   
    public UserProfileResponse doLookupUsername(@Valid String username) {
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
        List<UserProfile> profiles = new ArrayList<>();
        try
        {
        
            
            LOGGER.info(" -- doLookupUsername -- "+username);
            
            if(!rh.isValidEmail(username))
            {
              
                 return new UserProfileResponse(false, ErrorCodes.INVALID_EMAIL, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_EMAIL), null, null);
           
            
            }
         
           
            UserProfile doLookup =  userRepository.doFindByUsername(username);//.doFindByMsisdnAndEmail(fromJson.phonePri, fromJson.emailAddress);// doUserLookUp(fromJson);
            
            if(doLookup != null)
            {
             
                profiles.add(doLookup);
                return new UserProfileResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), profiles, null);
           
               
            }
            else
            {
             
                return new UserProfileResponse(false, ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND), profiles, null);

            }
                
            
        }/*
        catch (JsonbException e) {
        
             //e.printStackTrace();
             LOGGER.info(" JsonbException in doSync x ", e);
           
            return new UserProfileResponse(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), profiles, null);

        
        }*/
        catch (Exception e) {
        
             //e.printStackTrace();
              LOGGER.info(" Exception in doSync x ", e);
               if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                
                  return new UserProfileResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), profiles, null);

                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                   
                    return new UserProfileResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), profiles, null);

                }
                else
                {
                    
                   return new UserProfileResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), profiles, null);

                }
            
        }
        
        
    }
    
    public UserProfileResponse doLookupUserId(@Valid long tid) {
       int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
       List<UserProfile> profiles = new ArrayList<>(); 
        try
        {
        
            
            LOGGER.info(" -- doLookupUserId -- "+tid);
           
            UserProfile doLookup =  userRepository.doFindByTID(tid);//.doFindByMsisdnAndEmail(fromJson.phonePri, fromJson.emailAddress);// doUserLookUp(fromJson);
            
            if(doLookup != null)
            {
               
              
                profiles.add(doLookup);
                
                return new UserProfileResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), profiles, null);

               
            }
            else
            {
               
                 return new UserProfileResponse(false, ErrorCodes.INVALID_USERNAME, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USERNAME), profiles, null);

            }
                
            
        }/*
        catch (JsonbException e) {
        
             //e.printStackTrace();
             LOGGER.info(" JsonbException in doSync x ", e);
          
            return new UserProfileResponse(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR)+"-"+e.getMessage(), profiles, null);

        }*/
        catch (Exception e) {
        
             //e.printStackTrace();
              LOGGER.info(" Exception in doSync x ", e);
               if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                    
                   return new UserProfileResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), profiles, null);

                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     
                     return new UserProfileResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), profiles, null);

                }
                else
                {
                   
                      return new UserProfileResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), profiles, null);

                }
            
        }
        
        
    }
    
    @Transactional
    public UserProfileResponse doBlockProfile(@Valid UserProfileRequest json) {
       int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
       List<UserProfile> profiles = new ArrayList<>(); 
        try
        {
        
            UserProfileRequestObj fromJson = new UserProfileRequestObj(json);
            
            LOGGER.info(" -- doBlockProfile UserProfileDTO -- "+fromJson);
            
            
            UserProfile doLookup = UserProfile.findById(fromJson.tid);//.doFindByMsisdnAndEmail(fromJson.phonePri, fromJson.emailAddress);// doUserLookUp(fromJson);
            
            if(doLookup != null)
            {
                
                        if(!"ACTIVE".equals(doLookup.status) && !"INACTIVE".equals(doLookup.status))
                        {
                         
                          return new UserProfileResponse(false, ErrorCodes.FORBIDDEN_ACTION, ErrorCodes.doErrorDesc(ErrorCodes.FORBIDDEN_ACTION), profiles, null);

                        
                        }
                    
                         doLookup.status = ResourceStatusEnum.BLACKLISTED.name();// Long.parseLong("5"); //blacklisted
                         //doLookup.statusStr = Status.doStatusDescById(doLookup.status);
                        // doLookup.subcriberId = "NA";
                        
                         UserProfile merge = userRepository.doLog(doLookup);
                         
                         if(merge !=null)
                         {
                             
                             profiles.add(merge);
                            return new UserProfileResponse(true, ErrorCodes.ACCEPTED, ErrorCodes.doErrorDesc(ErrorCodes.ACCEPTED), profiles, null);

                             
                         }
                         else
                         {
                            
                               return new UserProfileResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), profiles, null);

                         }
           
            }
            else
            {
                 return new UserProfileResponse(false, ErrorCodes.INVALID_USERNAME, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USERNAME), profiles, null);

            }
                
            
        }/*
        catch (JsonbException e) {
        
             //e.printStackTrace();
             LOGGER.info(" JsonbException in doBlockProfile x ", e);
           
            return new UserProfileResponse(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), profiles, null);

        }*/
        catch (Exception e) {
        
             //e.printStackTrace();
              LOGGER.info(" Exception in doBlockProfile x ", e);
               if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                    return new UserProfileResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), profiles, null);

                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                   
                      return new UserProfileResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), profiles, null);

                }
                else
                {
                   
                     return new UserProfileResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), profiles, null);

                }
            
        }
        
        
    }
    
    @Transactional
    public UserProfileResponse doDeleteProfile(@Valid UserProfileRequest json) {
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
        List<UserProfile> profiles = new ArrayList<>(); 
        try
        {
        
            UserProfileRequestObj fromJson =  new UserProfileRequestObj(json);
            
            LOGGER.info(" --doDeleteProfile UserProfileDTO -- "+fromJson);
            
            
            UserProfile doLookup = UserProfile.findById(fromJson.tid);//.doFindByMsisdnAndEmail(fromJson.phonePri, fromJson.emailAddress);// doUserLookUp(fromJson);
            
            if(doLookup != null)
            {
                
                        if(!"ACTIVE".equals(doLookup.status) && !"INACTIVE".equals(doLookup.status))
                        {
                          
                           return new UserProfileResponse(false, ErrorCodes.FORBIDDEN_ACTION, ErrorCodes.doErrorDesc(ErrorCodes.FORBIDDEN_ACTION), null, null);

                        
                        }
                    
                         
                         doLookup.status = ResourceStatusEnum.DELETED.name();// Long.parseLong("4"); //delete
                         //doLookup.statusStr = Status.doStatusDescById(doLookup.status);
                        // doLookup.subcriberId = "NA";
                        
                         UserProfile merge = userRepository.doLog(doLookup);
                         
                         if(merge !=null)
                         {
                             profiles.add(merge);
                             return new UserProfileResponse(true, ErrorCodes.ACCEPTED, ErrorCodes.doErrorDesc(ErrorCodes.ACCEPTED), profiles, null);

                             
                         }
                         else
                         {
                            
                               return new UserProfileResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null, null);

                         }
           
            }
            else
            {
                 return new UserProfileResponse(false, ErrorCodes.INVALID_USERNAME, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USERNAME), null, null);

            
            }
                
            
        }/* 
        catch (JsonbException e) {
        
             //e.printStackTrace();
             LOGGER.info(" JsonbException in doBlockProfile x ", e);
           
             return new UserProfileResponse(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null, null);

        
        }*/
        catch (Exception e) {
        
             //e.printStackTrace();
              LOGGER.info(" Exception in doBlockProfile x ", e);
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                   
                     return new UserProfileResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null, null);

                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                   
                    return new UserProfileResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null, null);

                }
                else
                {
                  
                   return new UserProfileResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, null);

                }
            
        }
        
        
    }
   
    @Transactional
    public  UserProfileResponse doLog(UserProfileRequest request, long role) throws Exception {
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
        UserProfile merge = null;
        List<UserProfile> profiles = new ArrayList<>(); 
        try 
        {
            UserProfileRequestObj fromJson = new UserProfileRequestObj(request);
            
            UserProfile user = new UserProfile();
            user.status = ResourceStatusEnum.INACTIVE.name();//   BigInteger.TEN.longValue();
            user.firstName = fromJson.firstName;
            user.middleName = fromJson.middleName;
            //user.accountTag = "A";
            user.lastName = fromJson.lastName;
           // user.deviceFingerprint = fromJson.deviceFingerprint;
            user.emailAddress = fromJson.emailAddress;
           // user.accountType = "ADMIN";// doBuildISOMsg.getString(125);
           // user.accountNo = fromJson.accountNo;
            user.mobileNo = fromJson.mobileNo;
            user.tilAccount = fromJson.tilAccount;
            user.userRole = role;// Integer.parseInt(SysData.loadValueByName("BANK_CUSTOMER_ROLE_ID", "0"));
            user.userRoleStr = RolesInfo.doFindRoleCodeById(user.userRole);
            user.full_name = fromJson.lastName==null?"":fromJson.lastName+" "+fromJson.middleName==null?"NA":fromJson.middleName+" "+fromJson.firstName ;//doBuildISOMsg.getString(125);
            //user.statusStr = Status.doStatusDescById(user.status);
            user.createdDate = LocalDateTime.now();
            //user.bvn = "NA";//(doBuildISOMsg.hasField(126))? (doBuildISOMsg.getString(126).split("#")[0]).replaceAll("\\+234", "0"):"NA";
           // user.kyc = "1";
            //System.out.println("##***********user = " + user);
            merge = userRepository.doSync(user);// Panache.getEntityManager().merge(user);
            
            //System.out.println(" ** merge = " + merge);
            
             profiles.add(merge);
            
             return new UserProfileResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), profiles, null);

            
        } catch (Exception e) {
        
            LOGGER.error(" ||||||| Exception @  UserProfile doLog ",e);
            //throw new Exception(e);
            return new UserProfileResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR)+e.getMessage(), null, null);

        
        }
     // return merge;
        
    }
    
    @Transactional
    public ManagedLoginResponseV2 doVerifyAdmin(@Valid  UserLoginRequest request) {
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
        LOGGER.info("!-@@@ doVerifyAdmin - "+request);
        UserProfile doLookup = null;
        Response userLoginResp = null;
        int resp = 0;
        try
        {
            UserLoginRequestObj fromJson = new UserLoginRequestObj(request);
            
            
            doLookup = userRepository.doFindByEmailAddress(fromJson.emailAddress);
            
            LOGGER.info("#@@- doLookup - "+doLookup);
            
            if(doLookup != null && !"ACTIVE".equalsIgnoreCase(doLookup.status))
            {
                
                 return new ManagedLoginResponseV2(false, ErrorCodes.FORBIDDEN_ACTION, ErrorCodes.doErrorDesc(ErrorCodes.FORBIDDEN_ACTION),null,null, null);
              
            }
            else if(doLookup != null && "ACTIVE".equalsIgnoreCase(doLookup.status))
            {
                   UserLoginRequestV2 userLoginRequestV2 = new UserLoginRequestV2(doLookup.emailAddress, doLookup.mobileNo, fromJson.password, sysDataHelper.getProps("CHANNEL", "0"), doLookup.tid);
                   ///JsonObject authSync = Json.createObjectBuilder().add("code", rh.toDefault(doLookup.emailAddress)).add("codeLink", rh.toDefault(doLookup.mobileNo)).add("password", rh.toDefault(fromJson.password)).add("channel", sysDataHelper.getProps("CHANNEL", "0")).add("pid", doLookup.tid).build();
                   LOGGER.info(" -- authSync "+userLoginRequestV2);
                   return  doAdminManageLogins(doLookup.tid, Integer.parseInt(sysDataHelper.getProps("MAX-ALLOWED-FAILED-LOGIN-ATTEMPTS", "0")), Integer.parseInt(sysDataHelper.getProps("FAILED-LOGIN-SUSPENSE", "0")),doLookup, userLoginRequestV2);

            }
            else
            {
                
                
                 return new ManagedLoginResponseV2(false, ErrorCodes.INVALID_USERNAME, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USERNAME),null,null, null);
              
            }
                
            
        }/*
        catch (JsonbException e) {
        
             LOGGER.info("- doVerify JsonbException- "+e);
           
             return new ManagedLoginResponse(ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null, null,  null, null, null, 0, 0, null);
              
        }*/
        catch(WebApplicationException wae)
        {
            LOGGER.info("WebApplicationException"+doLookup );
            LOGGER.error("WebApplicationException",wae);
            if(wae.getResponse().getStatus() == ErrorCodes.INVALID_USER_OR_PASSWORD &&  doLookup !=null)
            {
               FailedLoginInfo doLog = FailedLoginInfo.doLog(doLookup.tid, doLookup.lastName+" "+doLookup.middleName+" "+doLookup.firstName,doLookup.emailAddress ,doLookup.mobileNo, LocalDate.now());
                                    
               LOGGER.info(" @@  FailedLoginInfo "+doLog+" ");
            }
            
             return new ManagedLoginResponseV2(false, wae.getResponse().getStatus(), ErrorCodes.doErrorDesc(wae.getResponse().getStatus()),null, null, null);
          
            
        }
        catch (Exception e) {
        
            LOGGER.info("- doVerify Exception- "+e);
          
           return new ManagedLoginResponseV2(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,  null,  null);
          
            
        }
        
    }
   
    
    @Transactional
    public UserProfile doSync(@Valid UserProfile userObj) {
        UserProfile merge = null;
        try
        {
              merge = Panache.getEntityManager().merge(userObj);
            
        }
        catch (Exception e) {
        
             e.printStackTrace();
          
            
        }
     return merge; 
    }
   
    
    @Transactional
    public Response doResetPassword(@Valid ResetUserPassword request) {
        LOGGER.info(" doResetPassword  --- "+request);
        try
        {
        
             ResetUserPasswordObj fromJson =  new ResetUserPasswordObj(request);
            
            
             UserProfile doLookup = userRepository.doFindByUsername(fromJson.username);
            
             if(doLookup != null)
             {
                      //UserProfile obj = new UserProfile();

                                 doLookup.status = ResourceStatusEnum.INACTIVE.name();// BigInteger.valueOf(2).longValue();
                                 //doLookup.statusStr = Status.doStatusDescById(doLookup.status);
                               
                                 UserProfile merge = userRepository.doLog(doLookup);

                                 if(merge !=null)
                                 {

                                     
                                     int status = doProcessOTPNotification(merge, dashboardUrl, "R");
                                     
                                     LOGGER.info("  DO SYNC   status = " + status);


                                     if(status != ErrorCodes.SUCCESSFUL &&   status != ErrorCodes.ACCEPTED)
                                     {

                                         doLookup.status = ResourceStatusEnum.ACTIVE.name();// BigInteger.valueOf(1).longValue();
                                         //doLookup.statusStr = Status.doStatusDescById(doLookup.status);
                                         UserProfile mergex = userRepository.doLog(doLookup);
                                         
                                         if(mergex == null)
                                         {
                                              LOGGER.info("TAKE CARE OF THIS MESS --- "+doLookup);
                                         }

                                         return Response.status(status).build(); //.entity(json).build();
                                     }
                                     else
                                     {
                                         return Response.ok().build();  
                                     }


                                 }
                                 else
                                 {
                                      return Response.status(ErrorCodes.DATABASE_ERROR).build();
                                 }

                             
                      
            }
            else
            {
                 // for the mugu, you dont have him on file but respond as though you are sending him a new password regardless
                
                 // LOGGER.info(" -- JUST HOLA  --- "+json);
                 return Response.ok().build();
                 
                 
            }
        }/*
        catch (JsonbException e) {
        
            LOGGER.info(" -- JsonbException -- ", e);
            return Response.status(ErrorCodes.FORMAT_ERROR).build();
        }*/
        catch (Exception e) {
        
            LOGGER.info(" -- Exception -- ", e);
            return Response.status(ErrorCodes.SYSTEM_ERROR).build();
            
        }
      
    }
    
    public int doProcessOTPNotification(UserProfile merge, String dashboardUrl, String mode)  throws CMFBException
    {
        System.out.println(" ### doProcessOTPNotification mode = " +   mode);
        int resp =  ErrorCodes.SYSTEM_ERROR;
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
        
        try 
        {
            
                  OTPLog OTPLog = new OTPLog();
                  if("1".equals(sysDataHelper.getProps("INTEGRATION-MODE")))
                  {
                       OTPLog.otp = (sysDataHelper.getProps("INTEGRATION-PIN"));
                  }
                  else  
                  {
                       OTPLog.otp = (RandomCharacter.doRandomNum(Integer.parseInt(sysDataHelper.getProps("DEFAULT-OTP-LENGTH"))));
                  }

                  OTPLog.mode = mode;// "E";
                  OTPLog.tokenDest = merge.emailAddress;
                 // OTPLog.tokenHash = new AESCrypter(sysDataHelper.getProps("SYS_KEY"),sysDataHelper.getProps("SYS_IV")).encrypt(OTPLog.otp);
                  OTPLog.status = OTPStatus.ACTIVE.name();
                  OTPLog.profileCode = merge.mobileNo;  //OTPLog.setProfileCode(merge.getEmailAddress());
                  
                  OTPLog doLog = oTPController.doLog(OTPLog);

                  if(doLog == null)
                  {

                      // em.remove(merge);
                       
                       resp = ErrorCodes.DATABASE_ERROR;
                       
                  }
                  else
                  {
                      
                      JsonObjectBuilder job = Json.createObjectBuilder();
                    
                        job.add("notificationType", "2")
                           .add("recepient",doLog.tokenDest) //.add("recepient",doLog.profileCode)
                           .add("copyTo", adminMail)
                           .add("addressee", merge.firstName)
                           .add("subject", "OTP Validation")
                           .add("date", new SimpleDateFormat("yyyy-MM-dd HH:mm a").format(new Date()))
                           .add("message", doLog.otp)
                           //.add("dashboardUrl", dashboardUrl+doLog.tokenHash+"&e="+merge.emailAddress)
                           .add("shortMessage", doLog.otp);
                        
                           // emitter.send(job.build());
                           
                     
                      resp = ErrorCodes.SUCCESSFUL;
                      

                  }
            
        } 
        catch (Exception e) {
        
                LOGGER.info(" + doProcessOTPNotification  Exception + ",e);
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                     return  ErrorCodes.DATABASE_ERROR;//, e.getMessage(),prodOrDev);
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     return ErrorCodes.IO_EXCEPTION;
                }
                else
                {
                     return ErrorCodes.SYSTEM_ERROR;
                }
           
        }
        
     return resp;
    }
   
    public UserProfile doLookupByUsernameOrEmail(String mobileno, String email) {
        LOGGER.info("doLookupByUsernameOrEmail = username "+mobileno+" email = "+email);
        UserProfile obj = null;
        try 
        {
               obj = userRepository.loadByMobileAndEmail(mobileno, email);
         
            
        } catch (Exception e) {
        
          // e.printStackTrace();
          
           LOGGER.error(" -- doLookupByUsernameOrEmail -- ", e);
          
        
        }
     
        return obj;  
        
    }
    
    
    public int doProcessOTPNotification(UserProfileRequest userInfo,UserProfile merge, String mode)  throws CMFBException
    {
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
        
        LOGGER.info("@@ doProcessOTPNotification mode = " +  mode);
        int resp =  ErrorCodes.SYSTEM_ERROR;
        try 
        {
            
                  OTPLog OTPLog = new OTPLog();
                  if("1".equals(sysDataHelper.getProps("INTEGRATION-MODE")))
                  {
                       OTPLog.otp = sysDataHelper.getProps("INTEGRATION-PIN");
                  }
                  else  
                  {
                       OTPLog.otp = RandomCharacter.doRandomNum(Integer.parseInt(sysDataHelper.getProps("DEFAULT-OTP-LENGTH")));
                  }
                  
                   LOGGER.info("doProcessOTPNotification  OTPLog.otp = " +   OTPLog.otp);

                  OTPLog.mode = mode;//"D";
                  OTPLog.tokenDest = userInfo.emailAddress();
                  OTPLog.status = OTPStatus.ACTIVE.name();//BigInteger.ONE;
                  OTPLog.profileCode = merge.mobileNo;  //OTPLog.setProfileCode(merge.getEmailAddress());
                  OTPLog doLog = oTPController.doLog(OTPLog);

                  if(doLog == null)
                  {

                       userRepository.doRemove(merge);
                       
                       resp = ErrorCodes.DATABASE_ERROR;
                       
                  }
                  else
                  {
                      
                      JsonObjectBuilder job = Json.createObjectBuilder();
                    
                        job.add("notificationType", "2") //E-OTP
                           .add("recepient",merge.emailAddress)
                           .add("copyTo", adminMail)
                           .add("addressee", merge.firstName)
                           .add("requestId", UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20))
                           .add("subject", "BUCKS SIGN UP OTP")
                           .add("date", new SimpleDateFormat("yyyy-MM-dd HH:mm a").format(new Date()))
                           .add("message2", "")
                           .add("shortMessage", ""+doLog.otp)
                           .add("message", ""+doLog.otp);
                        
                            //emitter.send(job.build());
                      
                      
                            resp = ErrorCodes.SUCCESSFUL;
                      

                  }
            
        } 
        catch (Exception e) {
            
                e.printStackTrace();
                LOGGER.info(" -- doProcessOTPNotification  X -  ",e);
        
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                     return  ErrorCodes.DATABASE_ERROR;
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     return ErrorCodes.IO_EXCEPTION;
                }
                else
                {
                     return ErrorCodes.SYSTEM_ERROR;
                }
           
        }
        
     return resp;
    }
     
     
    public int doProcessOTPNotification(UserProfile merge, String mode)  throws CMFBException
    {
        LOGGER.info("*** doProcessOTPNotification mode = " +  mode);
        int resp =  ErrorCodes.SYSTEM_ERROR;
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
        
        try 
        {
            
                  OTPLog OTPLog = new OTPLog();
                  if("1".equals(sysDataHelper.getProps("INTEGRATION-MODE")))
                  {
                       OTPLog.otp = sysDataHelper.getProps("INTEGRATION-PIN");
                  }
                  else  
                  {
                       OTPLog.otp = RandomCharacter.doRandomNum(Integer.parseInt(sysDataHelper.getProps("DEFAULT-OTP-LENGTH")));
                  }

                  OTPLog.mode = mode;//"D";
                  OTPLog.tokenDest = merge.emailAddress;
                  OTPLog.status = OTPStatus.ACTIVE.name();// BigInteger.ONE;
                  OTPLog.profileCode = merge.mobileNo;  //OTPLog.setProfileCode(merge.getEmailAddress());
                  OTPLog doLog = oTPController.doLog(OTPLog);

                  if(doLog == null)
                  {

                       userRepository.doRemove(merge);
                       
                       resp = ErrorCodes.DATABASE_ERROR;
                       
                  }
                  else
                  {
                      
                      JsonObjectBuilder job = Json.createObjectBuilder();
                    
                        job.add("notificationType", "2")
                           .add("recepient",doLog.profileCode)
                           .add("copyTo", adminMail)
                           .add("addressee", merge.firstName)
                           .add("subject", "OTP Validation")
                           .add("date", new SimpleDateFormat("yyyy-MM-dd HH:mm a").format(new Date()))
                           .add("message", doLog.otp)
                           .add("shortMessage", doLog.otp);
                        
                       // emitter.send(job.build());
                      
                      
                      resp = ErrorCodes.SUCCESSFUL;
                      

                  }
            
        } 
        catch (Exception e) {
        
                LOGGER.error(" -- Exception -- ",e);
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException ))
                {
                     return ErrorCodes.DATABASE_ERROR;
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                      return  ErrorCodes.IO_EXCEPTION;
                }
                else
                {
                     return ErrorCodes.SYSTEM_ERROR;
                }
           
        }
        
     return resp;
    }
    
    
    public int doProcessForceResetOTPNotification(UserProfile merge, String mode)  throws CMFBException
    {
        LOGGER.info(":: doProcessForceResetOTPNotification mode = " +  mode+" merge - "+merge);
        int resp =  ErrorCodes.SYSTEM_ERROR;
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
        
        try 
        {
            
                  OTPLog OTPLog = new OTPLog();
                  if("1".equals(sysDataHelper.getProps("INTEGRATION-MODE")))
                  {
                       OTPLog.otp = sysDataHelper.getProps("INTEGRATION-PIN");
                  }
                  else  
                  {
                       OTPLog.otp = RandomCharacter.doRandomNum(Integer.parseInt(sysDataHelper.getProps("DEFAULT-OTP-LENGTH")));
                  }

                  OTPLog.mode = mode;//"D";
                  OTPLog.tokenDest = merge.emailAddress;
                  OTPLog.status = OTPStatus.ACTIVE.name();//BigInteger.ONE;
                  OTPLog.profileCode = merge.mobileNo;  //OTPLog.setProfileCode(merge.getEmailAddress());
                  OTPLog doLog = oTPController.doLog(OTPLog);

                  if(doLog == null)
                  {
                         merge.status = ResourceStatusEnum.ACTIVE.name();//.Long.parseLong("1");
                         //merge.statusStr = Status.doStatusDescById(merge.status);
                         UserProfile mergex = userRepository.doLog(merge);
                         
                         if(mergex == null)
                         {
                             LOGGER.info(" -- FAILED TO RESET PROFILE ");
                         }
                       
                       resp = ErrorCodes.DATABASE_ERROR;
                       
                  }
                  else
                  {
                      
                      JsonObjectBuilder job = Json.createObjectBuilder();
                    
                        job.add("notificationType", "2")
                           .add("recepient",merge.emailAddress)
                           .add("copyTo", adminMail)
                           .add("addressee", merge.firstName)
                           .add("subject", "OTP Validation")
                           .add("date", new SimpleDateFormat("yyyy-MM-dd HH:mm a").format(new Date()))
                           .add("message", doLog.otp)
                           .add("shortMessage", doLog.otp);
                        
                       // emitter.send(job.build());
                        
                        
                      
                      
                      resp = ErrorCodes.SUCCESSFUL;
                      

                  }
            
        } 
        catch (Exception e) {
        
                LOGGER.error(" -- doProcessForceResetOTPNotification Exception -- ",e);
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException ))
                {
                     return ErrorCodes.DATABASE_ERROR;
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     return ErrorCodes.IO_EXCEPTION;
                }
                else
                {
                     return ErrorCodes.SYSTEM_ERROR;
                }
           
        }
        
     return resp;
    }
    
   
    
    /*
      
    */
   
    
    public UserProfileResponse doList(@Valid FilterRequest request) {
        //System.out.println(" called ...... ");
        long status = -2;
        List<UserProfile> resultList = new ArrayList<>();
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
        
        try 
        {
            
                FilterRequestObj fromJson = new FilterRequestObj(request);//JsonbBuilder.create().fromJson(json.toString(), GenericFilterObject.class);
                
                LOGGER.info("   --UserProfile  doList -- "+fromJson);
                
                
                if(rh.strToLDT(fromJson.toDate).isBefore( rh.strToLDT(fromJson.fromDate)))
                {
                      
                       return  new UserProfileResponse(false, ErrorCodes.DATE_DISPARITY, ErrorCodes.doErrorDesc(ErrorCodes.DATE_DISPARITY), null, null);
         
                } 
                else
                {
                    
                         
                        //status = rh.toRolesPrivilegeStatus(fromJson.controlCode);
                        
                        LOGGER.error("   --UserProfile  fromDate -- "+rh.strToLDT(fromJson.fromDate)+"   toDate "+rh.strToLDT(fromJson.toDate)+"  status  -- "+status);
                       
                        
                        resultList = userRepository.doFindByDateRangeAndStatus(rh.strToLDT(fromJson.fromDate), rh.strToLDT(fromJson.toDate), status);
                      

                         LOGGER.error("   --UserProfile  resultList -- "+resultList.size());
               
                         
                        if(resultList != null && resultList.size() > 0)
                        {
                            return  new UserProfileResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), resultList, null);
                        }  
                        else if(resultList != null && resultList.size()  == 0)
                        {
                          
                             return  new UserProfileResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), resultList, null);
                        }  
                        else
                        {
                         
                            
                            return  new UserProfileResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null, null);
                        }
                    
                         
                    
                }
                
              
           
        }/* 
        catch (JsonbException e) {
        
             e.printStackTrace();
            return Response.status(ErrorCodes.FORMAT_ERROR).build();
        }*/
        catch (Exception e) {
        
          log.error("Exception @ UserProfileResponse "+e);
          return  new UserProfileResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, null);
                    
            
        }
        
    }
    /*
    @Transactional
    public ChangePasswordResponse doListLike(@Valid JsonObject json) {
        //System.out.println(" called ...... ");
        List<UserProfile> resultList = new ArrayList<>();
        try 
        {
            
                GenericFilterObject fromJson = JsonbBuilder.create().fromJson(json.toString(), GenericFilterObject.class);
                
                LOGGER.info("   --UserProfile  doListLike -- "+fromJson);
                
               
                    
                         LOGGER.error("   --doListLike  fromDate -- "+rh.strToLDT(fromJson.fromDate)+"   toDate "+rh.strToLDT(fromJson.toDate));
                       
                         resultList = userRepository.doFindByUsernameLikeAndStatus(fromJson.searchKey, fromJson.status);
                     
                         LOGGER.error("   --UserProfile  doListLike -- "+resultList.size());
               
                         
                        if(resultList != null && resultList.size() > 0)
                        {
                             return Response.ok(resultList.stream().map(a->a.toJson()).collect(toList()), MediaType.APPLICATION_JSON).build(); 
                        }  
                        else if(resultList != null && resultList.size()  == 0)
                        {
                            return Response.ok(Json.createArrayBuilder().build()).build(); 
                        }  
                        else
                        {
                            return Response.status(ErrorCodes.DATABASE_ERROR).build();
                        }
                    
              
           
        }
        catch (Exception e) {
        
           e.printStackTrace();
            return nuul//Response.status(ErrorCodes.SYSTEM_ERROR).build();
            
        }
        
    }
    */
    
    public int doSyncProfileAdmin(String msisdn, String password,  String email, long pid, String channel, String controlCode) {
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));

        LOGGER.info("doSyncProfileAdmin msisdn "+msisdn+" email : "+email+" password : "+password);
        int resp = ErrorCodes.SYSTEM_ERROR;
        try 
        {
             //JsonObject authSync = Json.createObjectBuilder().add("code", email).add("msisdn", msisdn).add("codeLink", msisdn).add("password", password).add("verifyPassword",  password).add("pid",  pid).add("channel", channel==null?"NA":channel).build();
            //SyncPasswordRequest(String code, String codeLink, String password, String verifyPassword,  String msisdn, long pid, String controlCode)
             SyncPasswordRequest syncPasswordRequest = new SyncPasswordRequest(email, msisdn, password, password,  msisdn,  pid,  controlCode);
             LOGGER.info("authSync = " + syncPasswordRequest);
                     try 
                     { 
                      GenericResponseObj doForceSync = authservice.doForceSync(syncPasswordRequest);
                      
                        log.info(" -- doForceSync --  "+doForceSync);
                      
                        resp = doForceSync.errorCode();
                      
                       log.info("--  doForceSync resp "+resp);
                         
                     } catch (WebApplicationException e) {

                          resp = e.getResponse().getStatus();

                     }
                     catch (Exception e) {


                         if(e.getCause() instanceof java.net.ConnectException)
                         {
                             resp = ErrorCodes.COMM_LINK;
                         }
                         else
                         {
                               resp = ErrorCodes.SYSTEM_ERROR;
                         }



                         e.printStackTrace();

                     }
            
        } catch (Exception e) {
        
            e.printStackTrace();
        
        }
        
     return resp;
    }
    
    
    
    @Transactional
    public ChangePasswordResponse doInitForceChangePassword(@Valid InitForcePasswordChangeRequest request) {
      int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
      
       LOGGER.info("-- doInitForceChangePassword-- "+request);
        try
        {
        
            InitForcePasswordChangeRequestObj  fromJson = new InitForcePasswordChangeRequestObj(request);
             
            
            UserProfile doLookup = userRepository.doFindByEmailAddress(fromJson.emailAddress);//.doFindByUsername(fromJson.emailAddress);
            
            if(doLookup != null)
            {
                
                              doLookup.status = ResourceStatusEnum.INIT_FORCE_PASSWORD_CHANGE.name();// Long.parseLong("11");
                              //doLookup.statusStr = Status.doStatusDescById(doLookup.status);
                              UserProfile merge = userRepository.doLog(doLookup);
                              
                              if(merge != null)
                              {
                                  int doResp = doProcessForceResetOTPNotificationV2(doLookup,  "FR"); //force reset
                                  
                                  LOGGER.info(" -- RESET PASSWORD PUSH FAILED-- "+doResp);
                                 
                                   return  new ChangePasswordResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), null);
              
                              }
                              else
                              {
                                
                                   return  new ChangePasswordResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null);
              
                              
                              }
                
                         
            }
            else
            {
              
                 return  new ChangePasswordResponse(false, ErrorCodes.INVALID_USERNAME, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USERNAME), null);
              
            }
                
            
        }/*
        catch (JsonbException e) {
        
             LOGGER.error(" -- doInitForceChangePassword JsonbException -- ",e);
          
             return  new ChangePasswordResponse(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null);
              
        
        }*/
        catch (Exception e) {
        
               LOGGER.error(" -- doInitForceChangePassword Exception -- ",e);
               if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException || (e.getCause() instanceof jakarta.persistence.PersistenceException)))
                {
                     return  new ChangePasswordResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null);
              
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                  return  new ChangePasswordResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null);
              
                }
                else
                {
                    return  new ChangePasswordResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null);
              
                }
            
        }
        
    }
    
    public int doProcessForceResetOTPNotificationV2(UserProfile merge, String mode)  throws CMFBException
    {
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
      
        LOGGER.info(":: doProcessForceResetOTPNotificationV2 mode = " +  mode+" merge - "+merge);
        int resp =  ErrorCodes.SYSTEM_ERROR;
        try 
        {
            
                  OTPLog OTPLog = new OTPLog();
                  if("1".equals(sysDataHelper.getProps("INTEGRATION-MODE")))
                  {
                       OTPLog.otp = sysDataHelper.getProps("INTEGRATION-PIN");
                  }
                  else  
                  {
                       OTPLog.otp = RandomCharacter.doRandomNum(Integer.parseInt(sysDataHelper.getProps("DEFAULT-OTP-LENGTH")));//.substring(0, 4);;
                  }
                  
                   String otpmsg = "Your OTP is "+OTPLog.otp+" Kindly note that, it expires in "+sysDataHelper.getProps("OTP-TIME-OUT", "0")+" minutes, Please do not disclose";
               
                
                        NotificationRequest mailinfo = new NotificationRequest();
                        mailinfo.toShare = OTPLog.otp;
                        mailinfo.toShare1 = otpmsg;
                        mailinfo.addressee = merge.lastName;
                        mailinfo.sendTo = "taysaycoding@gmail.com";//merge.emailAddress;
                        mailinfo.message = otpmsg;
                 
                  
                  CompletionStage<Boolean> sendAdminWelcomeEmailAsync = mailProcessor.sendAdminWelcomeEmailAsync(mailinfo);
                  
                  //force pin change
                  //ISOMsg doBuildISOMsg = iSO8583TrxProcessor.doBuildISOMsg(fromJson, "88");

                  //if(doBuildISOMsg !=null) doBuildISOMsg.dump(System.out, " FORCE PIN CHANGE");

                  OTPLog.mode = mode;//"D";
                  OTPLog.tokenDest = merge.emailAddress;
                  OTPLog.status = OTPStatus.ACTIVE.name();//BigInteger.ONE;
                  OTPLog.profileCode = merge.mobileNo;  //OTPLog.setProfileCode(merge.getEmailAddress());
                 // OTPLog doLog = oTPController.doLog(OTPLog);
                
                 
                  System.out.println("###sendAdminWelcomeEmailAsync = " +sendAdminWelcomeEmailAsync);
                  if(sendAdminWelcomeEmailAsync !=null)//doBuildISOMsg != null && doBuildISOMsg.hasField(39) &&  "00".equals(doBuildISOMsg.getString(39)))
                  {
                 
                        int doLogOTP = otpController.doLogOTP(OTPLog.profileCode, OTPLog.tokenDest, OTPLog.otp, OTPStatus.ACTIVE.name());

                        if(doLogOTP == 0)
                        {
                               merge.status = ResourceStatusEnum.ACTIVE.name();// Long.parseLong("1");
                               //merge.statusStr = Status.doStatusDescById(merge.status);
                               UserProfile mergex = userRepository.doLog(merge);

                               if(mergex == null)
                               {
                                   LOGGER.info(" -- FAILED TO RESET PROFILE ");
                               }

                             resp = ErrorCodes.DATABASE_ERROR;

                        }
                        else
                        {

                            JsonObjectBuilder job = Json.createObjectBuilder();

                              job.add("notificationType", "2")

                                 .add("recepient",merge.emailAddress)
                                // .add("copyTo", adminMail)
                                 .add("addressee", merge.firstName)
                                 .add("subject", "OTP Validation")
                                 .add("date", new SimpleDateFormat("yyyy-MM-dd HH:mm a").format(new Date()))
                                 .add("message", OTPLog.otp)
                                 .add("shortMessage", OTPLog.otp);

                             // emitter.send(job.build());


                            resp = ErrorCodes.SUCCESSFUL;


                        }
                  }
                  else
                  {
                       resp = ErrorCodes.SYSTEM_ERROR;
                  }
            
        } 
        catch (Exception e) {
        
                LOGGER.error(" -- doProcessForceResetOTPNotification Exception -- ",e);
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException ))
                {
                    return ErrorCodes.DATABASE_ERROR;
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     return ErrorCodes.IO_EXCEPTION;
                }
                else
                {
                     return ErrorCodes.SYSTEM_ERROR;
                }
           
        }
        
     return resp;
    }
    
    public ManagedLoginResponseV2 doAdminManageLogins(long tid, int maxAllowed, int suspenceTime,UserProfile user,  UserLoginRequestV2 userLoginRequestV2) {
       
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
      
        LOGGER.info(" doAdminManageLogins "+tid+"  maxAllowed "+maxAllowed+" suspenceTime "+suspenceTime+" json : "+userLoginRequestV2);
        UserResponse doVerifyUser = null;
        try 
        {
            FailedLoginInfo doLookup = FailedLoginInfo.doLookup(tid);
            LOGGER.info(" ---- CURRENT FAILED COUNT  doLookup  "+doLookup);
            if(doLookup !=null)LOGGER.info(" ---- CURRENT FAILED COUNT   "+doLookup.failedCount);
            if(doLookup == null || doLookup.failedCount < maxAllowed)
            {
                 log.info("-- @@@ doLookup -- "+doLookup);
                 doVerifyUser = authservice.doVerifyUser(userLoginRequestV2);
                
                 log.info("--  doVerifyUser-- "+doVerifyUser);
                
                if(doVerifyUser !=null && doVerifyUser.statusHeaders() != null && doVerifyUser.statusHeaders().statusCode() == ErrorCodes.ACCEPTED)
                {
                
                  return new ManagedLoginResponseV2(true,ErrorCodes.ACCEPTED, ErrorCodes.doErrorDesc(ErrorCodes.ACCEPTED),  user, "Bearer " + doVerifyUser.bearerToken(),  null);
                }
                else
                {
                     int status = (doVerifyUser !=null && doVerifyUser.statusHeaders() !=null)?doVerifyUser.statusHeaders().statusCode():0;
                     LOGGER.info(" @@  authResp.getStatus() "+status+" ");
                     if(status != ErrorCodes.SUCCESSFUL || status != ErrorCodes.ACCEPTED)
                     {
                           FailedLoginInfo doLog = FailedLoginInfo.doLog(doLookup.tid, user.lastName+" "+user.middleName+" "+user.firstName,user.emailAddress ,user.mobileNo, LocalDate.now());
                           
                           LOGGER.info(" @@  FailedLoginInfo "+doLog+" ");
                     }

                  return new ManagedLoginResponseV2(false,status, ErrorCodes.doErrorDesc(status), null, null,  null);
              
                }
           
                
            }
            else if (doLookup !=null &&  doLookup.failedCount >= maxAllowed) // or consider another block
            {
                 LOGGER.info(" TIME DIFFXXX FAILED COUNT NOW "+ doLookup.failedCount);
                
                long until = LocalDateTime.now().until(doLookup.actionDateTime, ChronoUnit.MINUTES);
                LOGGER.info(" TIME DIFF "+ Math.abs(until)+" suspenceTime "+suspenceTime);
                
                if(suspenceTime > Math.abs(until))
                {
                    LOGGER.info("NOTIFY USER ");
                    String error = "You cannot login now.Please try again in the next "+(suspenceTime-Math.abs(until))+" minutes";
                   // resp  = Response.status(ErrorCodes.MULTIPLE_FAILED_LOGINS).entity(Json.createObjectBuilder().add("errorDesc", error).build()).build();
                
                    return new ManagedLoginResponseV2(false, ErrorCodes.MULTIPLE_FAILED_LOGINS, ErrorCodes.doErrorDesc(ErrorCodes.MULTIPLE_FAILED_LOGINS),null, null, null);
              
                
                }
                else
                {
                    LOGGER.info(" AUTO CLEAR ATTEMPT ");
                    FailedLoginHistory doLog = FailedLoginHistory.doLog(doLookup.profileId, doLookup.failedCount, doLookup.userName, doLookup.emailAddress, doLookup.mobileNo, "FAILED LOGIN AUDIT AUTO REFRESH");
                    if(doLog  !=null)
                    {
                        long doClear = FailedLoginInfo.doClear(doLookup.profileId);
                        LOGGER.info(" AUTO CLEAR SUCCESS "+(doClear > 1)+" profileId "+doLookup.profileId);
                        if(doClear > 0)
                        {
                            doVerifyUser =  authservice.doVerifyUser(userLoginRequestV2);
                           
                            if(doVerifyUser !=null && doVerifyUser.statusHeaders() !=null && doVerifyUser.statusHeaders().statusCode() == ErrorCodes.ACCEPTED)
                            {

                               return new ManagedLoginResponseV2(true, ErrorCodes.ACCEPTED, ErrorCodes.doErrorDesc(ErrorCodes.ACCEPTED), user,"Bearer " + doVerifyUser.bearerToken(),  null);
               
                            }
                        }
                        else
                        {
                         
                          return new ManagedLoginResponseV2(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,  null,  null);
              
                        }
                    
                    }
                    else
                    {
                          LOGGER.info(" AUDIT HISTORY FAILURE  ");
                         
                          
                        return new ManagedLoginResponseV2(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null, null, null);
              
                    }
                    
                   
                }
             
           }
           else
           {
              LOGGER.info(" -- ???????? doLookup "+doLookup+" maxAllowed : "+maxAllowed+" ");
             
              
              
           }
           
             
            
        }
        catch(WebApplicationException e)
        {
            int  status = e.getResponse().getStatus();

            LOGGER.info(" 5 @@ WebApplicationException @@ FailedLoginInfo status @@- "+status+", exception data ",e);

            if(status != ErrorCodes.INVALID_USER_OR_PASSWORD )
            {
               FailedLoginInfo doLog = FailedLoginInfo.doLog(user.tid, user.lastName+" "+user.middleName+" "+user.firstName,user.emailAddress ,user.mobileNo, LocalDate.now());

              LOGGER.info(" 6 @@ WebApplicationException @@ FailedLoginInfo "+doLog+" ");
            }
            else
            {
                 LOGGER.info(" @@ NOT FOR LOGIN AUDIT WebApplicationException @@ status "+status+" ");
            }

         // return new ManagedLoginResponse(status, ErrorCodes.doErrorDesc(status), "Bearer " +  resp.getHeaderString("Authorization"), user.firstName,  user.middleName, user.mobileNo, user.emailAddress, user.tid, user.pinChange, null);
        
          return new ManagedLoginResponseV2(false, status, ErrorCodes.doErrorDesc(status), null, null,   null);
              
        }
        catch (InvalidRequestException e) {
        
            LOGGER.info("+ InvalidRequestException e @doManageLogins - doVerifyFailedLogins ", e);
            
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
                error = error.substring(error.indexOf("{")+1, error.indexOf("{")+4);
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
            
            FailedLoginInfo doLog = FailedLoginInfo.doLog(user.tid, user.lastName+" "+user.middleName+" "+user.firstName,user.emailAddress ,user.mobileNo, LocalDate.now());

             
            return new ManagedLoginResponseV2(false, status, ErrorCodes.doErrorDesc(status), null, null, null);
           
        }
        catch (Exception e) {
        
            LOGGER.info("Exception e @doManageLogins - doVerifyFailedLogins ", e);
        
             return new ManagedLoginResponseV2(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, null, null);
           
        }
      
        return new ManagedLoginResponseV2(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, null, null);
              
    }
    
    /*
    @Transactional
    public Response doForceTxP(@Valid JsonObject json) {
        LOGGER.info(" @@-- doForceTxP -- "+json);
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
      
        try
        {
        
             UserProfileDTO fromJson = JsonbBuilder.create().fromJson(json.toString(), UserProfileDTO.class);
             
             LOGGER.info(" -- doForceTxP -- fromJson "+fromJson);
             
             if(!rh.isValidPhoneNumber(fromJson.mobileNo))
             {
                 return Response.status(ErrorCodes.INVALID_USERNAME).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USERNAME, prodOrDev)).build()).build();
             }
            LOGGER.info(" -- doForceTxP -- fromJson.otp  "+fromJson.otp);
            
            if(!rh.isValid(fromJson.newPin))
            {
                return Response.status(ErrorCodes.INVALID_PIN).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INVALID_PIN, prodOrDev)).build()).build();
            }

            if(!fromJson.newPin.equals(fromJson.verifyPin))
            {
                return Response.status(ErrorCodes.INVALID_VERIFY_PIN).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INVALID_VERIFY_PIN, prodOrDev)).build()).build();
            }
           
            
            if(!rh.isStrongPIN(fromJson.newPin))
            {
                return Response.status(ErrorCodes.PIN_TOO_SIMPLE).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doErrorDesc(ErrorCodes.PIN_TOO_SIMPLE, prodOrDev)).build()).build();
            }
          
            
            UserProfile doLookup = userRepository.doFindByUsername(fromJson.mobileNo); //  UserLog doLookup = UserLog.doFindByUsername(fromJson.userName);
            
            if(doLookup != null && (doLookup.status == 12))
            {
                
                 
                            JsonObject auth = Json.createObjectBuilder().add("code", rh.toDefault(doLookup.mobileNo)).add("codeLink", rh.toDefault(doLookup.emailAddress)).add("password", rh.toDefault(fromJson.newPin)).add("controlCode", "SUBSCRIBER").add("msisdn", rh.toDefault(doLookup.mobileNo)).build();
                             //JsonObject authSync = Json.createObjectBuilder().add("code", resourceH.toDefault(fromJson.getUserName())).add("codeLink", resourceH.toDefault(doLookup.getSchemeCode())).add("password", resourceH.toDefault(fromJson.getPassword())).build();
                             LOGGER.info(" ----@ auth ---- "+auth);
                             
                             int status = 0;
                             try 
                             {
                                
                                Response authResp = authClient.doVerifyUser(auth);
                                
                                 //System.out.println("authResp = " + authResp.getStatus());
                                 
                                if(authResp !=null) LOGGER.info(" ###---- auth authResp ---- "+authResp.getStatus());
                               
                                if(authResp !=null && authResp.getStatus() == ErrorCodes.ACCEPTED)
                                {
                                   
                             
                                            JsonObject authSync = Json.createObjectBuilder().add("code", doLookup.mobileNo).add("codeLink", rh.toDefault(doLookup.emailAddress)).add("pin", rh.toDefault(fromJson.newPin)).add("verifyPin", rh.toDefault(fromJson.verifyPin)).build();
                                            LOGGER.info(" ##-- force auth  "+authSync);
                                            
                                            try 
                                            {

                                                 status = authClient.doForceSyncTxP(authSync).getStatus();

                                                 LOGGER.info(" -- doForceSyncTxP status "+status);
                                                 
                                                 if(status == ErrorCodes.ACCEPTED || status == ErrorCodes.SUCCESSFUL)
                                                 {
                                                        doLookup.status =  BigInteger.valueOf(1).longValue();
                                                        doLookup.statusStr = Status.doStatusDescById(doLookup.status);
                                                        UserProfile merge = userRepository.doLog(doLookup);

                                                        if(merge == null)
                                                        {

                                                             LOGGER.info(" -- force reset status resync failed "+merge);
                                                        }
                                                 }

                                            } catch (WebApplicationException e) {

                                                 status = e.getResponse().getStatus();
                                                 
                                                 LOGGER.error(" -- doForceTxp  authsync WebApplicationException --("+status+") ",e);

                                            }
                                            catch (Exception e) {

                                                //e.printStackTrace();
                                                
                                                LOGGER.error(" -- doForceTxp  authsync Exception -- ",e);

                                                status = ErrorCodes.SYSTEM_ERROR;
                                            }

                                            System.out.println("  DO SYNC   status = " + status);

                                           return Response.status(status).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(status, prodOrDev)).build()).build();

                                    }
                                    else
                                    {
                                       return Response.status(authResp.getStatus()).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(authResp.getStatus(), prodOrDev)).build()).build();

                                    }
                                
                               } 
                               catch (WebApplicationException e) {
                             
                                  status = e.getResponse().getStatus();
                                  
                                  LOGGER.info(" --doVerify - "+status);
                                   
                                  return Response.status(status).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(status, prodOrDev)).build()).build();
                             }
                         
                         
            }
            else  if(doLookup != null && doLookup.status != 12 ) // && doLookup.status != 11
            {
              
                return Response.status(ErrorCodes.FORBIDDEN_ACTION).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.FORBIDDEN_ACTION, prodOrDev)).build()).build();
        
            }
            else
            {
                 return Response.status(ErrorCodes.INVALID_USERNAME).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USERNAME, prodOrDev)).build()).build();
            }
                
            
        } 
        catch (JsonbException e) {
        
             LOGGER.error(" -- doForceTxp JsonbException -- ",e);
            return Response.status(ErrorCodes.FORMAT_ERROR).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR, prodOrDev)).build()).build();
        }
        catch (Exception e) {
        
               LOGGER.error(" -- doForceTxp Exception -- ",e);
               if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException || (e.getCause() instanceof jakarta.persistence.PersistenceException)))
                {
                     throw new HelloException(ErrorCodes.DATABASE_ERROR, e.getMessage(), prodOrDev);
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     throw new HelloException(ErrorCodes.IO_EXCEPTION, e.getMessage(), prodOrDev);
                }
                else
                {
                     throw new HelloException(ErrorCodes.SYSTEM_ERROR, e.getMessage(), prodOrDev);
                }
        }
        
    }
    */
    @Transactional
    public ForcePasswordChangeResponse doForceChangePassword(@Valid ForcePasswordChangeRequest request) {
        LOGGER.info(" -- doForceChangePassword -- "+request); 
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
      
        try
        {
        
            ForcePasswordChangeRequestObj fromJson = new ForcePasswordChangeRequestObj(request);
             
            LOGGER.info(" -- doForceChangePassword -- fromJson "+fromJson);
            
            
            UserProfile doLookup = userRepository.doFindByEmailAddress(fromJson.emailAddress);
            log.info("-- doLookup -- "+doLookup);
            if(doLookup != null && (!"DELETED".equals(doLookup.status)  ||  !"BLACKLISTED".equals(doLookup.status)) && "INIT_FORCE_PASSWORD_CHANGE".equals(doLookup.status))
            {
                
                
                         int doVerifyOTP = OTPLog.doVerifyOTP(doLookup.mobileNo, fromJson.otp, doLookup.emailAddress);
                         // int doVerifyOTP = oTPController.doVerifyOTP(fromJson.getEmailAddress(), fromJson.getOtp(), fromJson.getPhoneNumberPri());
                     
                         if(doVerifyOTP == ErrorCodes.SUCCESSFUL)
                         {
                             //JsonObject authSync = Json.createObjectBuilder().add("code", doLookup.getEmailAddress()).add("codeLink", resourceH.toDefault(doLookup.getSchemeCode())).add("password", resourceH.toDefault(fromJson.getPassword())).add("verifyPassword", resourceH.toDefault(fromJson.getVerifyPassword())).add("newPassword", resourceH.toDefault(fromJson.getNewPassword())).build();
                             SyncPasswordRequest syncPasswordRequest = new SyncPasswordRequest(doLookup.emailAddress, doLookup.mobileNo, fromJson.password, fromJson.verifyPassword,  doLookup.mobileNo,  doLookup.tid,  doLookup.userRoleStr);
            
                             JsonObject authSync = Json.createObjectBuilder().add("code", doLookup.mobileNo).add("codeLink", rh.toDefault(doLookup.emailAddress)).add("pid", doLookup.tid).add("password", rh.toDefault(fromJson.password)).add("verifyPassword", rh.toDefault(fromJson.verifyPassword)).add("channel", sysDataHelper.getProps("CHANNEL", "0")).build();
                             LOGGER.info(" @@@ -- force auth  xxx "+authSync);
                             
                             int status = 0;
                             try 
                             {
                                  status = authservice.doForceSync(syncPasswordRequest).errorCode();//authClient.doForceSync(authSync).getStatus();
                                  
                                  if(status == ErrorCodes.ACCEPTED || status == ErrorCodes.SUCCESSFUL)
                                  {
                                         doLookup.status = ResourceStatusEnum.ACTIVE.name();//  BigInteger.valueOf(1).longValue();
                                         //doLookup.statusStr = Status.doStatusDescById(doLookup.status);
                                         UserProfile merge = userRepository.doLog(doLookup);
                                         
                                         if(merge == null)
                                         {
                                         
                                              LOGGER.info(" -- force reset status resync failed "+merge);
                                         }
                                  }
                                  else
                                  {
                                     // regenerate pin or reuse?
                                  }
                                 
                             } catch (WebApplicationException e) {
                             
                                  status = e.getResponse().getStatus();
                             
                             }
                             catch (Exception e) {
                              
                                 e.printStackTrace();
                             
                                 status = ErrorCodes.SYSTEM_ERROR;
                             }
                            
                             LOGGER.info(" --   DO SYNC   status = " + status);
                             
                           // return Response.status(status).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(status, prodOrDev)).build()).build();
                             
                            return new ForcePasswordChangeResponse(((status==200 || status == 202)?true:false), status, ErrorCodes.doErrorDesc(status), null);
                      
                         }
                         else
                         {
                            // return Response.status(doVerifyOTP).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(doVerifyOTP, prodOrDev)).build()).build();
                         
                         
                             return new ForcePasswordChangeResponse(false, doVerifyOTP, ErrorCodes.doErrorDesc(doVerifyOTP), null);
                      
                         }
            
                         
                         
                         
            }
            else if(doLookup != null && ("DELETED".equals(doLookup.status)  ||  "BLACKLISTED".equals(doLookup.status)))
            {
             
               return new ForcePasswordChangeResponse(false, ErrorCodes.FORBIDDEN_ACTION, ErrorCodes.doErrorDesc(ErrorCodes.FORBIDDEN_ACTION), null);
                      
            }
            else
            {
                
                 return new ForcePasswordChangeResponse(false, ErrorCodes.INVALID_USERNAME, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USERNAME), null);
                      
            }
                
            
        }/*
        catch (JsonbException e) {
        
             LOGGER.error(" -- doForceChangePassword JsonbException -- ",e);
           
             return new ForcePasswordChangeResponse(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null);
                      
        }*/
        catch (Exception e) {
        
               LOGGER.error(" -- doForceChangePassword Exception -- ",e);
               if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException || (e.getCause() instanceof jakarta.persistence.PersistenceException)))
                {
                     
                     return new ForcePasswordChangeResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null);
              
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                    return new ForcePasswordChangeResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null);
                }
                else
                {
                   
                    return new ForcePasswordChangeResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null);
              
                }
        }
        
    }
    
    @Transactional
    public ChangeTXPResponse doInitForceTxpChange(@Valid ForceTXPRequest request) {
       int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
      
        try
        {
        
             ForceTXPRequestObj fromJson = new ForceTXPRequestObj(request);
            
            
            UserProfile doLookup = userRepository.doFindByUsername(fromJson.mobileNo);
            
            if(doLookup != null)
            {
                
                              doLookup.status = ResourceStatusEnum.INIT_FORCE_PIN_CHANGE.name();/// Long.parseLong("12");  
                              //doLookup.statusStr = Status.doStatusDescById(doLookup.status);
                              UserProfile merge = userRepository.doLog(doLookup);
                              
                              if(merge != null)
                              {
                                 int doResp = doProcessForceResetOTPNotification(doLookup,  "FR"); //force reset
                                 LOGGER.info(" FORCE PIN CHANGE OTP LOG "+doResp);
                                 if( doResp == ErrorCodes.SUCCESSFUL)
                                 {
                                   
                                     return new ChangeTXPResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), null);
                                 }
                                 else
                                 {
                                   
                                     return new ChangeTXPResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null);
                              
                                 }
                              }
                              else
                              {
                                 
                                  return new ChangeTXPResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null);
                              
                              }
                
                         
            }
            else
            {
               
                 return new ChangeTXPResponse(false, ErrorCodes.INVALID_USERNAME, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USERNAME), null);
                              
            }
                
            
        }/*
        catch (JsonbException e) {
        
             LOGGER.error(" -- doInitForceChangePassword JsonbException -- ",e);
           
            return new ChangeTXPResponse(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null);
                              
        
        }*/
        catch (Exception e) {
        
               LOGGER.error(" -- doInitForceChangePassword Exception -- ",e);
               if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException || (e.getCause() instanceof jakarta.persistence.PersistenceException)))
                {
                     return new ChangeTXPResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null);
                              
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                   
                     return new ChangeTXPResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null);
                              
                }
                else
                {
                    
                     return new ChangeTXPResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null);
                              
                }
            
        }
        
    }
    
    public String doFindNameByTID(long updatedBy) throws Exception {
        String doFindNameByTID = "";
        try 
        {
             doFindNameByTID = userRepository.doFindNameByTID(updatedBy);
            
        } catch (Exception e) {
            
            throw new Exception(e);
        }
      return doFindNameByTID;
    }
    
    @Transactional
    public ChangePasswordResponse doChangePassword(@Valid @NotNull ChangePasswordRequest request) {
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));

        LOGGER.info(" -- doChangePassword -- "+request);
        try
        {
        
            ChangePasswordRequestObj fromJson = new ChangePasswordRequestObj(request);
            
            
                
                UserProfile doLookup = userRepository.doFindByEmailAddress(fromJson.userName);
            
                LOGGER.info("== doLookup == "+doLookup);
                if(doLookup != null)
                {
                     ChangeUserPasswordRequest changeUserPasswordRequest = new ChangeUserPasswordRequest(doLookup.tid, doLookup.emailAddress, doLookup.mobileNo, fromJson.password, fromJson.verifyPassword, fromJson.newPassword,  "WEB");
                             
                             LOGGER.info("=== authSync ==== "+changeUserPasswordRequest);
                             int status = 0;
                             try 
                             {
                                  status = authservice.doMaintain(changeUserPasswordRequest).errorCode();// authClient.doMaintain(authSync).getStatus();
                                 
                             } 
                             catch (WebApplicationException e) {
                             
                                  status = e.getResponse().getStatus();
                             
                             }
                             catch (Exception e) {
                              
                                 e.printStackTrace();
                             
                             }
                            
                    LOGGER.info("  DO SYNC   status = " + status);
                    
                    return  new ChangePasswordResponse((status==200 || status== 202)?true:false, status, ErrorCodes.doErrorDesc(status), null);
                      
                }
                else
                {
                    return  new ChangePasswordResponse(false, ErrorCodes.INVALID_USERNAME, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USERNAME), null);
                }
                
           
            
        }/*
        catch (JsonbException e) {
        
             LOGGER.error(" -- doChangePassword JsonbException -- ",e);
           
             return  new ChangePasswordResponse(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null);
              
        }*/
        catch (Exception e) {
              
               
                LOGGER.error(" -- doChangePassword Exception -- ",e);
                
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException || (e.getCause() instanceof jakarta.persistence.PersistenceException)))
                {
                   
                    return  new ChangePasswordResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null);
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                   
                   return  new ChangePasswordResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null);
            
                }
                else
                {
                   
                    return  new ChangePasswordResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null);
            
                }
        }
        
    }
    
    
    public UserProfileResponse doListUsers(FilterRequest request) {
        int defaultPageSize = Integer.parseInt(sysDataHelper.getProps("DEFAULT-PAGE-SIZE", "5"));
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
        
        LOGGER.info("@@ doListUsers defaultPageSize = " + defaultPageSize);
        JsonArrayBuilder jar = Json.createArrayBuilder();
        PanacheQuery<UserProfile> findByQuery= null;
        ValidationHelper rh = new ValidationHelper();
        boolean isSearch = false;
        List<UserProfile> profiles = new ArrayList<>();
        try 
        {
            FilterRequestObj fromJson = new FilterRequestObj(request);
           
            LOGGER.info(" *** doListCategories fromJson = " +  fromJson);
            //validate dto
            
                         
                if(rh.strToLDT(fromJson.toDate).isBefore(rh.strToLDT(fromJson.fromDate)))
                {
                          
                    return new UserProfileResponse(true, ErrorCodes.DATE_DISPARITY, ErrorCodes.doErrorDesc(ErrorCodes.DATE_DISPARITY), profiles, null);
                }
                else if((fromJson.pageId) < 1 || fromJson.pageSize < 1)// || !resourceH.isValidWithLen(fromJson.getPhoneNumberPri().replaceAll("\\+", ""), 13))
                {
                    return new UserProfileResponse(true, ErrorCodes.INVALID_PAGE_ID_OR_FETCH_SIZE, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_PAGE_ID_OR_FETCH_SIZE), profiles, null);
               
                }
                else
                {
                    
               
                   if(fromJson.status.equalsIgnoreCase("all") && (!rh.isValid(fromJson.searchParam)))// || "NA".equals(fromJson.searchKey)))
                   {
                     findByQuery = userRepository.findByParams(rh.toLocalDateTime(fromJson.fromDate), rh.toLocalDateTime(fromJson.toDate)); //doFindByDescription(jsonStr)
                      LOGGER.info(" userRepository STEP 1 ");
                   }
                   else if(!fromJson.status.equalsIgnoreCase("all") && (!rh.isValid(fromJson.searchParam)))// || "NA".equals(fromJson.searchKey)))
                   {
                      findByQuery = userRepository.findByParams(fromJson.status, rh.toLocalDateTime(fromJson.fromDate), rh.toLocalDateTime(fromJson.toDate)); //doFindByDescription(jsonStr)
                      LOGGER.info(" userRepository STEP 1 A ");
                   }
                   else if(!fromJson.status.equalsIgnoreCase("all") && rh.isValid(fromJson.searchParam))
                   {
                       findByQuery = userRepository.findByParams(fromJson.status, rh.toLocalDateTime(fromJson.fromDate), rh.toLocalDateTime(fromJson.toDate), fromJson.searchParam); //doFindByDescription(jsonStr)
                       LOGGER.info(" @@@ userRepository STEP 2 ");
                   }
                   else if(fromJson.status.equalsIgnoreCase("all") && rh.isValid(fromJson.searchParam))
                   {
                       findByQuery = userRepository.findByParams(rh.toLocalDateTime(fromJson.fromDate), rh.toLocalDateTime(fromJson.toDate), fromJson.searchParam); //doFindByDescription(jsonStr)
                       LOGGER.info(" @@@ userRepository STEP 2 A ");
                   } 
                   
                
                        if(findByQuery !=null)findByQuery.page(Page.ofSize(fromJson.pageSize));
                        int numberOfPages = 0;
                        if(findByQuery !=null && !isSearch)
                        {
                             numberOfPages = findByQuery.pageCount();
                             LOGGER.info("-- numberOfPages = " + numberOfPages);
                        }
                        else if(findByQuery !=null && isSearch)
                        {
                             LOGGER.info(" B4 fromJson.pageId = " + fromJson.pageId);
                             fromJson.pageId = findByQuery.pageCount();
                             numberOfPages = findByQuery.pageCount();
                             fromJson.pageId = numberOfPages;
                             LOGGER.info("-- numberOfPages = " + numberOfPages);

                             LOGGER.info(" After fromJson.pageId = " + fromJson.pageId);
                        }

                        int totalRecs = (findByQuery !=null)?(int)findByQuery.count():0;//providerCategoryRepo.count();

                        LOGGER.info(" @@-- @@@ totalRecs = " + totalRecs +" fromJson.pageSize "+fromJson.pageSize+"  fromJson.page : "+fromJson.pageId+" ss findByQuery "+(findByQuery !=null));
                        if(findByQuery !=null) 
                        {
                             LOGGER.info("totalRecs B4 = " + findByQuery.pageCount());
                             findByQuery.page(Page.ofSize(fromJson.pageSize));
                             LOGGER.info("totalRecs AFTER = " + findByQuery.pageCount());
                             List<UserProfile> list = findByQuery.page(Page.of(fromJson.pageId-1,fromJson.pageSize)).list();

                           return new UserProfileResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), list, null);


                        }
                        else
                        { 
                             LOGGER.info(" HOW ?? ");
                          //  return Response.ok().entity(jar.build()).build();

                           return new UserProfileResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), profiles, null);

                        }
                
                }
        
        }
        catch (CMFBException e) {
             e.printStackTrace();
           
           return new UserProfileResponse(false, e.getResponse().getStatus(), ErrorCodes.doErrorDesc(e.getResponse().getStatus())+e.getMessage(), null, null);

        }
        catch (Exception e) {
        
               LOGGER.error("Exception @ Response doListPartners ",e);
               
                if(e.getCause() instanceof com.paycraftsystems.exceptions.CMFBException)
                {
                    System.err.println(" ** AHA = " + e.getMessage()+" cause "+e.getCause());
                    CMFBException exp =   (CMFBException) e.getCause();
                   
                    return new UserProfileResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR)+e.getMessage(), null, null);

                }
               
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                   
                    return new UserProfileResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR)+e.getMessage(), null, null);

                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                   return new UserProfileResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION)+e.getMessage(), null, null);

                }
                else
                {
                   
                      return new UserProfileResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR)+e.getMessage(), null, null);

                }
            }
        
     }
    
    @Transactional
    public UserProfileResponse doCreateProfile(@Valid UserProfileRequest request) {
         int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
      
        LOGGER.info(" doCreateProfile "+request);
        List<UserProfile> profiles = new ArrayList<>();
        String otp = "";
        try 
        {
            UserProfileRequestObj fromJson = new UserProfileRequestObj(request);
            
            if(fromJson != null)
            {
                if(sysDataHelper.getProps("INTEGRATION-MODE").equals("1"))
                {

                    otp = sysDataHelper.getProps("INTEGRATION-PIN");
                }
                else
                {
                    otp = String.valueOf(new Random().nextInt(1234567890)).substring(0, 4);
                }
                
                RequestDTO rqx = new RequestDTO();
                //String otpmsg = "Hello "+fromJson.lastName+" DO NOT DISCLOSE Use this OTP "+otp+" to complete your transaction. Expires by "+LocalDateTime.now().plusMinutes(Integer.parseInt(sysDataHelper.getProps("OTP-TIME-OUT", "0"))).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a"));
                String otpmsg = "Your OTP is "+otp+" Kindly note that, it expires in "+sysDataHelper.getProps("OTP-TIME-OUT", "0")+" minutes, Please do not disclose";
                rqx.setAmount("0.0");
                rqx.setNarration("Account Verification");
                rqx.setSrcAccount(fromJson.accountNo);
                rqx.setMsisdn(fromJson.mobileNo);
                rqx.setOTP(otpmsg);//otp);
                
                //Hello OLADIPUPO .DO NOT DISCLOSE Use this OTP 080255 to complete your transaction. Expires 12-Dec-22 06:36 PM
                
                 UserProfile userProfile = userRepository.loadByMobileAndEmail(fromJson.mobileNo, fromJson.emailAddress);
                 
                 if(userProfile != null)
                 {
                     return new UserProfileResponse(false, ErrorCodes.USER_PROFILE_EXISTS, ErrorCodes.doErrorDesc(ErrorCodes.USER_PROFILE_EXISTS), null, null);
           
                 }
                 else
                 {
                     
                 
                                if(userProfile == null)
                                {
                                         UserProfile user = new UserProfile();
                                         user.status = ResourceStatusEnum.INACTIVE.name();//BigInteger.TEN.longValue();
                                         user.firstName = fromJson.firstName;
                                         user.middleName = fromJson.middleName;
                                         //user.accountTag = "P";
                                         user.lastName = fromJson.lastName;
                                         user.tilAccount = fromJson.tilAccount;
                                         user.emailAddress = fromJson.emailAddress;
                                         //user.accountType = doBuildISOMsg.getString(125);
                                         //user.accountNo = fromJson.accountNo;
                                         user.mobileNo = fromJson.mobileNo;
                                         user.createdDate = LocalDateTime.now();
                                         //user.bvn = (doBuildISOMsg.hasField(126))? (doBuildISOMsg.getString(126).split("#")[0]).replaceAll("\\+234", "0"):"NA";
                                         //user.kyc = "1";
                                         UserProfile merge = Panache.getEntityManager().merge(user);

                                          System.out.println(" #### merge = " + merge);

                                          if(merge == null)
                                          {
                                             
                                              return new UserProfileResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null, null);
           
                                          }
                                          else
                                          {
                                              profiles.add(merge);

                                               int doLogOTP = otpController.doLogOTP(merge.mobileNo, merge.emailAddress, otp,OTPStatus.ACTIVE.name());
                                               LOGGER.info("- OTP LOGGER -- "+doLogOTP);
                                               if(doLogOTP == ErrorCodes.SUCCESSFUL)
                                               {
                              
                                                 return new UserProfileResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), profiles, null);
           
                                               }
                                               else
                                               {

                                                   merge.delete();
                                                  // return Response.status(doLogOTP).build();
                                                   
                                                   return new UserProfileResponse(false, doLogOTP, ErrorCodes.doErrorDesc(doLogOTP), profiles, null);
           
                                               }

                                          }

                                }
                                else
                                {
                                  
                                   return new UserProfileResponse(false, ErrorCodes.USER_PROFILE_EXISTS, ErrorCodes.doErrorDesc(ErrorCodes.USER_PROFILE_EXISTS), null, null);
           
                                }
                                
                           /*

                        }     
                        else if(doBuildISOMsg != null && doBuildISOMsg.hasField(39)  && doBuildISOMsg.getString(39).equals("12"))
                        {

                            return Response.status(ErrorCodes.INVALID_TRANSACTION).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INVALID_TRANSACTION, prodOrDev)).build()).build();

                        }
                        else if(doBuildISOMsg != null && doBuildISOMsg.hasField(39)  && doBuildISOMsg.getString(39).equals("91"))
                        {

                                return Response.status(ErrorCodes.ISSUER_OR_SWITCH_NOT_AVAILABLE).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.ISSUER_OR_SWITCH_NOT_AVAILABLE, prodOrDev)).build()).build();

                        }
                        else if(doBuildISOMsg != null && doBuildISOMsg.hasField(39)  && doBuildISOMsg.getString(39).equals("55"))
                        {
                            return Response.status(ErrorCodes.INVALID_PIN).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INVALID_PIN, prodOrDev)).build()).build();

                        }
                        else if(doBuildISOMsg != null && doBuildISOMsg.hasField(39) && doBuildISOMsg.getString(39).equals("52"))
                        {
                             return Response.status(ErrorCodes.INVALID_ACCOUNT).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.INVALID_ACCOUNT, prodOrDev)).build()).build();

                        }
                        else if(doBuildISOMsg != null && doBuildISOMsg.hasField(39) && doBuildISOMsg.getString(39).equals("40"))
                        {
                              return Response.status(ErrorCodes.MOBILE_ACCOUNT_MIX_MATCH).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.MOBILE_ACCOUNT_MIX_MATCH, prodOrDev)).build()).build();

                        }
                        else if(doBuildISOMsg != null && doBuildISOMsg.hasField(39) && doBuildISOMsg.getString(39) !=null)
                        {
                            return Response.status(ErrorCodes.SYSTEM_ERROR).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR, prodOrDev)+"-"+doBuildISOMsg.getString(39)).build()).build();

                        }
                        else
                        {
                             //return Response.status(ErrorCodes.MOBILE_ACCOUNT_MIX_MATCH).build();
                           return Response.status(ErrorCodes.SYSTEM_ERROR).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR, prodOrDev)).build()).build();

                        }
                      */

                    }//close elses
                    
            }
            else
            {
               
                 return new UserProfileResponse(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null, null);
           
            }
            
        } catch (Exception e) {
       
             e.printStackTrace();
             return new UserProfileResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR)+"-"+e.getMessage(), null, null);
           
        }
        
    }
    
     @Transactional
     public ResendOTPResponse doResendOTPAdmin(@Valid OTPRequest json) {
         int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
      
        int resp = 0;
        String otp = "";
        try 
        {
          
            OTPRequestObj fromJson = new OTPRequestObj(json);//.toString(), OTPDTO.class);
            if(fromJson != null)
            {
                
               
                    LOGGER.info("-- GOT HERE ---->>> "+fromJson);
                   //doResendOTPAdmin
                    UserProfile doLookup = userRepository.loadByMobileAndEmail(fromJson.mobileNo, fromJson.emailAddress);// doLookupByMSISDN(fromJson.getPhoneNumberPri());  //fromJson.getEmailAddress()

                       LOGGER.info("-- GOT HERE doLookup---->>> "+doLookup);
                       if(doLookup != null)
                       {

                             if("INACTIVE".equals(doLookup.status))
                             {
                                        
                                            if(sysDataHelper.getProps("INTEGRATION-MODE", "X").equals("1"))
                                            {

                                                otp = sysDataHelper.getProps("INTEGRATION-PIN", "0000");
                                            }
                                            else
                                            {
                                                otp = String.valueOf(new Random().nextInt(1234567890)).substring(0, 4);
                                            }
                                            
                                            

                                           int doLogOTP = otpController.doLogOTPV2(doLookup.mobileNo, doLookup.emailAddress, otp,OTPStatus.ACTIVE.name());

                                           if(doLogOTP == ErrorCodes.SUCCESSFUL)
                                           {
                                               
                                                    RequestDTO rqx = new RequestDTO();
                                                    // String otpmsg = "Hello "+doLookup.lastName+" DO NOT DISCLOSE Use this OTP "+otp+" to complete your transaction. Expires by "+LocalDateTime.now().plusMinutes(Integer.parseInt(sysDataHelper.getProps("OTP-TIME-OUT", "0"))).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a"));
                                                    String otpmsg = "Your OTP is "+otp+" Kindly note that, it expires in "+sysDataHelper.getProps("OTP-TIME-OUT", "0")+" minutes, Please do not disclose";
                                                    //Your OTP is 5910 Kindly note that, it expires in 10 minutes
                                                    rqx.setAmount("0.0");
                                                    rqx.setNarration(otpmsg);//"Your OTP is "+otp+"");
                                                    //rqx.setSrcAccount(doLookup.accountNo);
                                                    rqx.setMsisdn(fromJson.mobileNo);
                                                    rqx.setOTP(otpmsg);//otp);//"0000"
                                                    
                                                    // ??
                                                    
                                                return new ResendOTPResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), null);
                                            


                                           }
                                           else
                                           {

                                             return new ResendOTPResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null);
                                            
                                           }
                                        
                              
                             }
                             else
                             {
                                
                                  return new ResendOTPResponse(false,ErrorCodes.TRANSACTION_FORBIDDEN, ErrorCodes.doErrorDesc(ErrorCodes.TRANSACTION_FORBIDDEN), null);
                                            
                             }
                             

                    }
                    else
                    {
                      
                          return new ResendOTPResponse(false,ErrorCodes.INVALID_USERNAME, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USERNAME), null);
                                  
                    }
            
                
            }
            else
            {
                
                return new ResendOTPResponse(false,ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null);
                        
            }
          
        }/* 
        catch (JsonbException e) {
        
            // e.printStackTrace();
           
              return new ResendOTPResponse(false,ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null);
                
        
        }*/
        catch (Exception e) {
        
            // e.printStackTrace();
           
              return new ResendOTPResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null);
              
            
        }
       
    }
    
    public UserProfile  doFindUser(long id) throws Exception
    {
        //UserLog doFindByTID = UserLog.doFindByTID(id);
        return  userRepository.doFindByTID(id);
    }
     
    public String doFindUsernameById(long id) throws Exception
    {
        UserProfile doFindByTID = userRepository.doFindByTID(id);
        return doFindByTID ==null?"NA":doFindByTID.emailAddress;//userName;
    }
    
    
    
}
