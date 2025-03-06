/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.controller;


import com.paycraftsystems.cmfb.dto.OTPRequestObj;
import com.paycraftsystems.cmfb.entities.OTPLog;
import com.paycraftsystems.cmfb.entities.UserProfile;
import com.paycraftsystems.cmfb.enumz.OTPStatus;
import com.paycraftsystems.cmfb.enumz.ResourceStatusEnum;
import com.paycraftsystems.resources.ErrorCodes;
import io.quarkus.hibernate.orm.panache.Panache;
import java.math.BigInteger;
import java.time.LocalDateTime;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
//import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author paycraftsystems-i
 */

@ApplicationScoped
@Slf4j
public class OTPController {
    
     private static Logger LOGGER = LoggerFactory.getLogger(OTPController.class);
     
     
    @Inject
    EntityManager em;
    
    @Inject
    UserController userLogController;
    
    
   @Transactional
    public int doStampOTP(String msisdn, String otp) {
        int doStampOTP = 0;
        try 
        {
            
             doStampOTP = OTPLog.doStampOTP(msisdn, otp);
            
        } catch (Exception e) {
            
            LOGGER.error("Exception @ doStampOTP ", e);
        }
    return  doStampOTP;
    }
    
     public int doVerifyToken(String email, String msisdn,String hash) {
        int resp = ErrorCodes.INVALID_TOKEN;
        try 
        {
            OTPLog doLookUpByMobileOrEmail = OTPLog.doLookUpByToken(email, msisdn,hash);
            
            if(doLookUpByMobileOrEmail != null)
            {
                doLookUpByMobileOrEmail.status = OTPStatus.USED.name();
                doLookUpByMobileOrEmail.updatedDate =  LocalDateTime.now();
                OTPLog doSync = doSync(doLookUpByMobileOrEmail);
                
                if(doSync !=null)
                {
                     resp = ErrorCodes.SUCCESSFUL;
                }
                else if(doSync ==null)
                {
                    resp = ErrorCodes.DATABASE_ERROR;
                }
            
            }
            
            
        } 
        catch (Exception e) {
        
            e.printStackTrace();
            
            resp = ErrorCodes.SYSTEM_ERROR;
                    
        }
       return resp; 
    }
    @Transactional
    public void doStampOTP(UserProfile doLookup, OTPRequestObj fromJson, String status) {
        
        try 
        {
           
                 doLookup.status = ResourceStatusEnum.ACTIVE.name();// BigInteger.ONE.longValue();
                 doLookup.pinChange = BigInteger.ONE.longValue();
                 UserProfile merge = Panache.getEntityManager().merge(doLookup);
                                            
                 OTPLog.doStampOTP(fromJson.mobileNo, fromJson.otp);

                 LOGGER.info(" -::: SLAM THE AUTH STATUS >>>>> = " + merge);
                                         
            
        } catch (Exception e) {
            
            LOGGER.error("Exception @ doStampOTP ",e);
        }
        
    }
    
     public int doVerifyOTPRollback(String profilecode, String otp, String email) {
        int resp = ErrorCodes.INVALID_TOKEN;
        try 
        {
            OTPLog doLookUpByMobileOrEmail = OTPLog.doLookUpByMobileOrEmailRollback(profilecode,otp,  email);
            
            if(doLookUpByMobileOrEmail != null)
            {
                doLookUpByMobileOrEmail.status = OTPStatus.ACTIVE.name();
                doLookUpByMobileOrEmail.updatedDate = LocalDateTime.now();
                OTPLog doSync = doSync(doLookUpByMobileOrEmail);
                
                if(doSync !=null)
                {
                     resp = ErrorCodes.SUCCESSFUL;
                }
                else if(doSync ==null)
                {
                    resp = ErrorCodes.DATABASE_ERROR;
                }
            
            }
            
            
        } 
        catch (Exception e) {
        
            e.printStackTrace();
            
            resp = ErrorCodes.SYSTEM_ERROR;
                    
        }
       return resp; 
    }
    
    public int doVerifyOTP(String profilecode, String otp, String email, int status) {
        int resp = ErrorCodes.INVALID_TOKEN;
        try 
        {
            OTPLog doLookUpByMobileOrEmail = OTPLog.doLookUp(profilecode,otp,  email);
            
            if(doLookUpByMobileOrEmail != null  && (status == ErrorCodes.ACCEPTED || status == ErrorCodes.SUCCESSFUL))
            {
                
                doLookUpByMobileOrEmail.status = OTPStatus.USED.name();
                doLookUpByMobileOrEmail.updatedDate = LocalDateTime.now();
                OTPLog doSync = doSync(doLookUpByMobileOrEmail);
                
                if(doSync !=null)
                {
                     resp = ErrorCodes.SUCCESSFUL;
                }
                else if(doSync ==null)
                {
                    resp = ErrorCodes.DATABASE_ERROR;
                }
            
            }
            
            
        } 
        catch (Exception e) {
        
            e.printStackTrace();
            
            resp = ErrorCodes.SYSTEM_ERROR;
                    
        }
       return resp; 
    }
    
   @Transactional
   public int doLogOTP(String mobile, String email, String otp, String status)
   {
       System.out.println(" doLogOTP status = " +   status);
       int resp = ErrorCodes.DATABASE_ERROR;
       OTPLog  otpLog = null;
       try 
       {
           
                        
                      OTPLog doLookUp = OTPLog.doLookUp(mobile, email);
                      log.info("@@ -@@- doLogOTP doLookUp --  "+doLookUp);
                      //System.out.println(" OTPLog doLookUp = " + doLookUp);
                      //em.getTransaction().begin();
                      if(doLookUp == null)
                      {
                            otpLog = new OTPLog();
                            otpLog.status = status;
                            otpLog.mode = "M";
                            otpLog.otp = otp;
                            otpLog.profileCode = mobile;
                            otpLog.tokenDest = email;//mobile
                            otpLog.createdDate = LocalDateTime.now();
                            em.persist(otpLog);
                            //OTPLog merge = em.merge(otpLog);
                            em.flush();
                            
                            //OTPLog merge = Panache.getEntityManager().merge(otpLog);
                            
                            System.out.println(" @@ merge otpLog = " + otpLog);
                            
                            resp = ErrorCodes.SUCCESSFUL;
                      }
                      else 
                      {
                            doLookUp.updatedDate = LocalDateTime.now();
                            doLookUp.otp = otp;
                            doLookUp.status = OTPStatus.ACTIVE.name();
                            em.persist(doLookUp);
                            //OTPLog merge = em.merge(doLookUp);
                            em.flush();
                           // Panache.getEntityManager().merge(doLookUp);
                            
                            resp = ErrorCodes.SUCCESSFUL;
                      }
           
                     
                      //.getTransaction().commit();
                
           
       } 
       catch (Exception e) {
       
           LOGGER.error(" Exception doLogOTP ",e);
           
           e.printStackTrace();
       }
     
    return resp; 
   }
   
   
   @Transactional
   public int doLogOTPV2(String mobile, String email, String otp, String status)
   {
       int resp = ErrorCodes.DATABASE_ERROR;
       try 
       {
           
              
                      OTPLog doLookUp = OTPLog.doLookUp(mobile, email);
                    
                      //System.out.println(" OTPLog doLookUp = " + doLookUp);
                      if(doLookUp == null)
                      {
                            OTPLog  otpLog = new OTPLog();
                            otpLog.status = status;
                            otpLog.mode = "M";
                            otpLog.otp = otp;
                            otpLog.profileCode = mobile;
                            otpLog.tokenDest = email;//mobile
                            otpLog.createdDate = LocalDateTime.now();
                            Panache.getEntityManager().merge(otpLog);
                            
                            resp = ErrorCodes.SUCCESSFUL;
                      }
                      else 
                      {
                          
                          int doStampResetOTP = OTPLog.doStampResetOTP(mobile);
                          
                          log.info("---doLogOTPV2  doStampResetOTP -- "+doStampResetOTP);
                          
                            doLookUp.updatedDate = LocalDateTime.now();
                            doLookUp.otp = otp;
                            doLookUp.status = OTPStatus.ACTIVE.name();
                            Panache.getEntityManager().merge(doLookUp);
                            
                            resp = ErrorCodes.SUCCESSFUL;
                      }
           
                      
                
           
       } 
       catch (Exception e) {
       
           LOGGER.error(" Exception doLogOTP ",e);
           
           e.printStackTrace();
       }
     
    return resp; 
   }
     
    @Transactional
    public OTPLog doLog(OTPLog  oTPLog)
    {
        OTPLog resp = null;
        try
        {
                      OTPLog doLookUp = OTPLog.doLookUpByMode(oTPLog.profileCode, oTPLog.mode);
                      //System.out.println(" OTPLog doLookUp = " + doLookUp);
                      if(doLookUp == null)
                      {
                            oTPLog.createdDate =  LocalDateTime.now();
                            oTPLog.otp = oTPLog.otp;
                            oTPLog.profileCode = oTPLog.profileCode;
                            oTPLog.status = OTPStatus.ACTIVE.name();
                            resp = em.merge(oTPLog);
                      }
                      else
                      {
                            doLookUp.updatedDate =  LocalDateTime.now();
                            doLookUp.otp = oTPLog.otp;
                            doLookUp.profileCode = oTPLog.profileCode;
                            doLookUp.status = oTPLog.profileCode;
                            resp = em.merge(doLookUp);
                      }
                 
        }
        catch(Exception ex)
        {  //resp = "1";
            ex.printStackTrace();
        }
       return resp;
    }
    
    @Transactional
    public OTPLog doSync(OTPLog  oTPLog)
    {
        OTPLog resp = null;
        try
        {
                      //oTPLog.setCreatedDate(new Date());
                      //oTPLog.setOtp(oTPLog.getOtp());
                      //oTPLog.setProfileCode(oTPLog.getProfileCode());
                      //oTPLog.setStatus(BigInteger.TWO);
                      resp = em.merge(oTPLog);
       
        }
        catch(Exception ex)
        {  //resp = "1";
            ex.printStackTrace();
        }
       return resp;
    }
    
    
}
