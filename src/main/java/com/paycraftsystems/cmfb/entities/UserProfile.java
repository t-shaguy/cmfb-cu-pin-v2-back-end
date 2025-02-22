/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.entities;

import com.paycraftsystems.resources.ValidationHelper;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author root
 */

@Entity
@Table(name="user_profile")
public class UserProfile extends PanacheEntityBase implements Serializable{
    
   private static  Logger LOGGER =  LoggerFactory.getLogger(UserProfile.class);
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="tid")
    public long tid;
    
    @Column(name="first_name")
    public String firstName;
    
    @Column(name="middle_name")
    public String middleName;
    
    @Column(name="last_name")
    public String lastName;
    
    @Column(name="mobile_no")
    public String mobileNo;
    
    @Column(name="last_login")
    public LocalDateTime lastLogin;
    
    @Column(name="created_date")
    public LocalDateTime createdDate;
    
    @Column(name="last_update_date")
    public LocalDateTime lastUpateDate;
    
    @Column(name="status")
    public String status;
    
    @Column(name="pin_change")
    public long pinChange;
    
    @Column(name="status_str")
    public String statusStr;
    
    @Column(name="login_status")
    public int loginStatus;
    
    @Column(name="email_address")
    public String emailAddress;
    
  
    @Column(name="til_account")
    public String tilAccount;
    
   
    @Column(name = "user_role")
    public long userRole;
    
    @Column(name = "user_role_str")
    public String userRoleStr;
     
    @Column(name="full_name")
    public String full_name;
    
    
     public String doFullName() {
        
         return lastName==null?"":lastName+" "+middleName==null?"":middleName+" "+firstName==null?"":firstName;
     }
     
      public String doShortName() {
        
         return lastName+" "+firstName==null?"":firstName.substring(0, 1).toUpperCase();
     }
    
    
    public static  JsonObject toJson(int noOfPages, int totalRecs, int pageId, JsonArray array) {
        
        ValidationHelper rh = new ValidationHelper();
        JsonObjectBuilder job = Json.createObjectBuilder();
        JsonObjectBuilder mainResponse = Json.createObjectBuilder();
        try 
        {
         
            mainResponse.add("pageCount",noOfPages).add("totalRecs", totalRecs).add("pageId", pageId).add("data", array);
           
            
        } catch (Exception e) {
        
            e.printStackTrace();
        
        }
      return mainResponse.build();
    }
    
    
    
    public JsonObject toJson() {
        
        ValidationHelper rh = new ValidationHelper();
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
            
            job.add("firstName", rh.toDefault(this.firstName))
               .add("middleName", rh.toDefault(this.middleName))
               .add("lastName", rh.toDefault(this.lastName))
               .add("mobileNo", rh.toDefault(this.mobileNo))
               //.add("accountNo", rh.toDefault(this.accountNo))
               .add("emailAddress", rh.toDefault(this.emailAddress));
               //.add("accountType", rh.toDefault(this.accountType));
        } 
        catch (Exception e) {
        
        
        }
        
     return job.build();
    }
    
    public JsonObject toProfiledAccountsJson() {
        
        ValidationHelper rh = new ValidationHelper();
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
            
            job.add("firstName", rh.toDefault(this.firstName))
               .add("middleName", rh.toDefault(this.middleName))
               .add("middleName", rh.toDefault(this.middleName))
               .add("mobileNo", rh.toDefault(this.mobileNo))
               //.add("accountNo", rh.toDefault(this.accountNo))
               .add("emailAddress", rh.toDefault(this.emailAddress));
              // .add("accountType", rh.toDefault(this.accountType))
              // .add("principal", rh.toDefault(this.accountTag !=null && this.accountTag.equals("P")?"YES":"NO"));
        } 
        catch (Exception e) {
        
        
        }
        
     return job.build();
    }
    
    public JsonObject toJson(String jwt) {
        
        ValidationHelper rh = new ValidationHelper();
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
            
            job.add("jwt", rh.toDefault(jwt))
               .add("firstName", rh.toDefault(this.firstName))
               .add("middleName", rh.toDefault(this.middleName))
               .add("middleName", rh.toDefault(this.middleName))
               .add("mobileNo", rh.toDefault(this.mobileNo))
              // .add("accountNo", rh.toDefault(this.accountNo))
               .add("emailAddress", rh.toDefault(this.emailAddress))
              // .add("bvn", rh.toDefault(this.bvn))
               //.add("userName", rh.toDefault(this.))
               .add("tid", rh.toDefault(this.tid))
               .add("pinChange", this.pinChange);
               //.add("accountType", rh.toDefault(this.accountType))
               //.add("kyc", rh.toDefault(this.kyc));
        } 
        catch (Exception e) {
        
        
        }
        
     return job.build();
    }
    
    
    public static UserProfile loadByParams(String phoneNo)
    {
        UserProfile obj = null;
        try 
        {
            
             obj =  find("mobileNo", phoneNo).firstResult();
            
        } catch (Exception e) {
        
              ///e.printStackTrace();
            
              LOGGER.error(" loadByParams --  Exception - ",e);
        }
        
      return obj;
    }

    @Override
    public String toString() {
        return "UserProfile{" + "tid=" + tid + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", mobileNo=" + mobileNo + ", lastLogin=" + lastLogin + ", createdDate=" + createdDate + ", lastUpateDate=" + lastUpateDate + ", status=" + status + ", pinChange=" + pinChange + ", statusStr=" + statusStr + ", loginStatus=" + loginStatus + ", emailAddress=" + emailAddress + ", tilAccount=" + tilAccount + ", userRole=" + userRole + ", userRoleStr=" + userRoleStr + ", full_name=" + full_name + '}';
    }

   
    
    

}
