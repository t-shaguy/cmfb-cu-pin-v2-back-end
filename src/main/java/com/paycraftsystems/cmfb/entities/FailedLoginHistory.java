/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.entities;

import io.quarkus.hibernate.orm.panache.Panache;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;

/**
 *
 * @author paycraftsystems-i
 */

@Entity
@Table(name="failed_login_history")
public class FailedLoginHistory {
    
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
    
    @Column(name="action_info")
    public String actionInfo;
    
    @Column(name="action_date")
    public LocalDateTime  actionDate;
    
    @Column(name="log_date")
    public LocalDateTime  logDate;
    
    @Column(name="email_address")
    public String  emailAddress;
     
     
    @Column(name="mobile_no")
    public String  mobileNo;
    
    @Transactional
    public static FailedLoginHistory doLog(long profileId, long failedCount, String userName, String emailAddress, String msisdn, String actionInfo) {
        FailedLoginHistory obj = null;
        try 
        {
            obj = new FailedLoginHistory();
            obj.actionDate = LocalDateTime.now();
            obj.profileId = profileId;
            obj.logDate =  LocalDateTime.now();
            obj.userName = userName;
            obj.failedCount = failedCount;
            obj.actionInfo = actionInfo;
            obj.emailAddress = emailAddress;
            obj.mobileNo = msisdn;
            
           obj =  Panache.getEntityManager().merge(obj);
            
        } 
        catch (Exception e) {
            
            e.printStackTrace();
        
        }
     return obj;
    }
    
    
}
