/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.entities;

import com.paycraftsystems.cmfb.enumz.OTPStatus;
import com.paycraftsystems.resources.ErrorCodes;
//import com.paycraftsystems.resources.ErrorCodes;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import static io.quarkus.hibernate.orm.panache.PanacheEntity_.id;
import io.quarkus.panache.common.Parameters;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author root
 */
@Entity
@Table(name = "otp_log")
public class OTPLog extends PanacheEntityBase implements Serializable {
    
      private static Logger LOGGER = LoggerFactory.getLogger(OTPLog.class);
    
    
    public static final String ALL = "OTPLog.findAll";
    public static final String BY_CODE = "OTPLog.findByCodeAndOTP";
    public static final String BY_MODE = "OTPLog.findByMode";
    public static final String BY_CODE_MODE = "OTPLog.findByCodeMode";
    
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TID")
    public Long tid;
    
    
    @Column(name = "profile_code")
    public String profileCode;
    
    @Column(name = "otp")
    public String otp;
    
    @Column(name = "mode")
    public String mode;
    
    @Column(name = "token_dest")
    public String tokenDest;
     
    @Column(name="create_date")
    //@Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime createdDate;
    
    @Column(name="updated_date")
    //@Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime updatedDate;
    
    @Column(name = "status")
    public String status;

    public OTPLog() {
    }

    public OTPLog(Long tid) {
        this.tid = tid;
    }
    
    public static OTPLog  doLookUpByToken(String  email, String mobile, String  tokenHash) {
        
        return find("(profileCode =:passed or profileCode =:passed2) and tokenHash = :passed3  and status = 'ACTIVE'", Parameters.with("passed", email).and("passed2", mobile).and("passed3", tokenHash)).firstResult();
    }
    
    
    public static OTPLog  doLookUpByProfileAndMode(String  profilecode, String  mode) {
        
        return find("profileCode =:passed and mode = :passed2", Parameters.with("passed", profilecode).and("mode", mode)).firstResult();
    }
    
    public static OTPLog  doLookUp(String  profileCode, String otp,  String email) {
        
       return find("(profileCode =:passed or profileCode =:passed2) and otp = :passed3  and status = 'ACTIVE'", Parameters.with("passed", profileCode).and("passed2", email).and("passed3", otp)).firstResult();
       // return find("profileCode =:passed and otp = :passed2  and status = 1", Parameters.with("passed", profileCode).and("passed2", otp)).firstResult();
    }
    
    public static OTPLog  doLookUp(String  profileCode,  String email) {
        
       return find("(profileCode =:passed or tokenDest =:passed)  or  (tokenDest =:passed2 or tokenDest =:passed2)  and status = 'ACTIVE'", Parameters.with("passed", profileCode).and("passed2", email)).firstResult();
       // return find("profileCode =:passed and otp = :passed2  and status = 1", Parameters.with("passed", profileCode).and("passed2", otp)).firstResult();
    }
    
    public static OTPLog  doLookUpTransaction(String  profileCode,  String email, String mode) {
        
       return find("(profileCode =:passed or tokenDest =:passed)  or  (tokenDest =:passed2 or tokenDest =:passed2)  and status = 'ACTIVE' and mode = :passed3", Parameters.with("passed", profileCode).and("passed2", email).and("passed3", mode)).firstResult();
       // return find("profileCode =:passed and otp = :passed2  and status = 1", Parameters.with("passed", profileCode).and("passed2", otp)).firstResult();
    }
    
    public static OTPLog doLookUpByProfileCodeAndCode(String  profilecode, String  code) {
      
        return find("profileCode =:passed and otp = :passed2 and status = 'ACTIVE' ", Parameters.with("passed", profilecode).and("passed2", code)).firstResult();
    }
    
    public static OTPLog  doLookUpByMobileOrEmailRollback(String  profileCode,  String otp,  String email) {
        
       return find("(profileCode =:passed or profileCode =:passed2) and otp = :passed3  and status = 'ACTIVE' ", Parameters.with("passed", profileCode).and("passed2", email).and("passed3", otp)).firstResult();
    }
    
    public static OTPLog  doLookUpByMode(String  profileCode, String mode) {
        
        return find("profileCode =:passed and mode = :passed2  and status = 'ACTIVE'", Parameters.with("passed", profileCode).and("passed2", mode)).firstResult();
    }
    /*
    public static OTPLog  doLookUpByProfileEmailAndMobile(String  email, String  mobile) {
      
        return find("profileCode =:passed or tokenDest = :passed2 and status = 1 ", Parameters.with("passed", email).and("passed2", mobile)).firstResult();
    }*/
    
    public static  int doVerifyOTP(String profilecode, String otp, String email) {
        int resp = ErrorCodes.INVALID_TOKEN;
        try 
        {
           OTPLog doLookUpByMobileOrEmail = doLookUp(profilecode,otp,  email);
            
            if(doLookUpByMobileOrEmail != null)
            {
                doLookUpByMobileOrEmail.status =OTPStatus.USED.name();
                doLookUpByMobileOrEmail.updatedDate = LocalDateTime.now();
                OTPLog doSync = Panache.getEntityManager().merge(doLookUpByMobileOrEmail);// doSync(doLookUpByMobileOrEmail);
                
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
    
    
    public static OTPLog  doLookUpByEmailOrMobileAndOTP(String  email, String mobile, String  code) {
        
        return find("(profileCode =:passed or profileCode =:passed2) and otp = :passed3  and status = 1", Parameters.with("passed", email).and("passed2", mobile).and("passed3", code)).firstResult();
    }
    
    public static OTPLog  doLookUpByEmailAndOTP(String  email, String mobile, String  code) {
        
        return find("(tokenDest =:passed or profileCode =:passed2) and otp = :passed3  and status = 'ACTIVE' ", Parameters.with("passed", email).and("passed2", mobile).and("passed3", code)).firstResult();
    }
    
    public static OTPLog  doLookUpByMobileAndRequestIdAndOTP(String  email, String mobile, String  code, String mode) {
        
        return find("(profileCode =:passed or profileCode =:passed2) and otp = :passed3  and status = 1 and mode = :passed4", Parameters.with("passed", email).and("passed2", mobile).and("passed3", code).and("passed4", mode)).firstResult();
    }
    
    public static int  doVerifyOTPRollback(String mobile, String  otp) {
        int resp = 0;
        try 
        {
              update("status = 1 where profileCode = ?1  and  otp = ?2 ",mobile,otp);
              resp = 1;
            
        } catch (Exception e) {
       
           e.printStackTrace();
        }
      return resp;
    }
    @Transactional
    public static int  doStampResetOTP(String mobile) {
        int resp = 0;
        try 
        {
            
        Query query = Panache.getEntityManager().createQuery("UPDATE OTPLog p SET p.status = :status WHERE p.profileCode = :id and p.status = 'ACTIVE' ");
        query.setParameter("status", OTPStatus.RESET.name());
        query.setParameter("id", mobile);

        // Execute the update statement
         return query.executeUpdate();
        
        } catch (Exception e) {
       
           e.printStackTrace();
           
           LOGGER.error("EXCEPTION @ doStampOTP ", e);
          
        }
      return resp;
    }
    
    
    @Transactional
    public static int  doStampOTP(String mobile, String otp) {
        int resp = 0;
        try 
        {
            OTPLog doLookUpByProfileCodeAndCode = doLookUpByProfileCodeAndCode(mobile, otp);
            LOGGER.info("STAMPED ---doLookUpByProfileCodeAndCode > "+doLookUpByProfileCodeAndCode);
            if(doLookUpByProfileCodeAndCode !=null)
            { 
                doLookUpByProfileCodeAndCode.status = OTPStatus.USED.name();
                doLookUpByProfileCodeAndCode.updatedDate = LocalDateTime.now();
                OTPLog merge = Panache.getEntityManager().merge(doLookUpByProfileCodeAndCode);
                LOGGER.info("STAMPED --- > "+merge);
                if(merge !=null)
                {
                    resp = 1;
                }
            }
            // resp = update("status = 0 where profileCode = ?1 and  otp = ?2 ",mobile,otp);
             //resp = 1;
            
        } catch (Exception e) {
       
           e.printStackTrace();
           
           LOGGER.error("EXCEPTION @ doStampOTP ", e);
          
        }
      return resp;
    }
    
    
    public static OTPLog  doLookUpByProfileCodeAndCodeAndMode(String  profilecode, String  code, String mode) {
        
        return find("profileCode =:passed and otp = :passed2", Parameters.with("passed", profilecode).and("otp", code).and("mode", mode)).firstResult();
    }
    
    
    //OTPLog.BY_CODE_ON_MOBILE_OR_EMAIL, OTPLog.class).setParameter("passed", profilecode.trim()).setParameter("passed2", code).setParameter("passed3", email);


    @Override
    public String toString() {
        return "OTPLog{" + "tid=" + tid + ", profileCode=" + profileCode + ", otp=" + otp + ", mode=" + mode + ", token_dest=" + tokenDest + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", status=" + status + '}';
    }

}
