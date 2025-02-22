/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.entities;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.find;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author paycraftsystems-i
 */

@Entity
@Table(name="failed_login_info")
public class FailedLoginInfo extends PanacheEntityBase implements Serializable{
    
   private static Logger LOGGER =   LoggerFactory.getLogger(FailedLoginInfo.class);
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="tid")
    public long tid;
   
    @Column(name="failed_count")
    public long failedCount;
    
    @Column(name="profile_id")
    public long profileId;
    
    @Column(name="user_name")
    public String  userName;
    
    
    @Column(name="email_address")
    public String  emailAddress;
     
     
    @Column(name="mobile_no")
    public String  mobileNo;
    
    @Column(name="action_date")
    public LocalDate  actionDate;
    
    @Column(name="action_date_time")
    public LocalDateTime  actionDateTime;
    
    
    @Transactional
    public static FailedLoginInfo doLookup(long profileId, LocalDate activityDate) {
        System.out.println(" doLookup activityDate = " +   activityDate+" profileId : "+profileId);
        FailedLoginInfo obj = null;
        try 
        { 
           obj = find("profileId = ?1 and actionDate = ?2 ", profileId, activityDate).firstResult();
            
        } 
        catch (Exception e) {
            
            e.printStackTrace();
            LOGGER.error("Exception @ FailedLoginInfo doLookup ", e);
        
        }
     return obj;
    }
    
    public static long doClear(long profileId) {
        FailedLoginInfo obj = null;
        long delete = 0;
        try 
        { 
           delete = delete("profileId = ?1", profileId);
            
        } 
        catch (Exception e) {
            
            e.printStackTrace();
        
        }
     return delete;
    }
    
     @Transactional
    public static FailedLoginInfo doLookup(long profileId) {
        FailedLoginInfo obj = null;
        try 
        { 
           obj = find("profileId = ?1 ", profileId).firstResult();
            
        } 
        catch (Exception e) {
            
            e.printStackTrace();
        
        }
     return obj;
    }
    
    @Transactional
    public static FailedLoginInfo doLog(long profileId, String userName, String emailAddress, String msisdn,  LocalDate actiondate) {
        FailedLoginInfo obj = null;
        try 
        {
            FailedLoginInfo doLookup = doLookup(profileId, actiondate);
            if(doLookup !=null)
            {
                //doLookup = new FailedLoginInfo();
                System.out.println("FailedLoginInfo doLog LocalDate.now() = " + LocalDate.now());
                doLookup.actionDate = LocalDate.now();
                doLookup.actionDateTime = LocalDateTime.now();
                doLookup.profileId = profileId;
                doLookup.userName = userName;
                doLookup.emailAddress = emailAddress;
                doLookup.mobileNo = msisdn;
                doLookup.failedCount += 1;
                
                obj =  Panache.getEntityManager().merge(doLookup);
            }
            else
            {
                obj = new FailedLoginInfo();
                obj.actionDate = LocalDate.now();
                obj.actionDateTime = LocalDateTime.now();
                obj.profileId = profileId;
                obj.userName = userName;
                obj.emailAddress = emailAddress;
                obj.mobileNo = msisdn;
                obj.failedCount = 1;
                
                obj =  Panache.getEntityManager().merge(obj);
                
            }
         
            
        } 
        catch (Exception e) {
            
            e.printStackTrace();
            LOGGER.error("Exception @ FailedLoginInfo doLog", e);
        
        }
     return obj;
    }

    @Override
    public String toString() {
        return "FailedLoginInfo{" + "tid=" + tid + ", failedCount=" + failedCount + ", profileId=" + profileId + ", userName=" + userName + ", actionDate=" + actionDate + ", actionDateTime=" + actionDateTime + '}';
    }
   
    
    
    
    
}
