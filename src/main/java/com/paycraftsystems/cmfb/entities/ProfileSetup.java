 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.entities;

import com.paycraftsystems.resources.ValidationHelper;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
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
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author paycraftsystems-i
 */
@Entity
@Table(name="profiles_logs")
public class ProfileSetup extends PanacheEntityBase implements Serializable
{
  
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
    
   // @Column(name="pin_change")
   // public long pinChange;
    
   // @Column(name="status_str")
   // public String statusStr;
    
    @Column(name="login_status")
    public int loginStatus;
    
    @Column(name="email_address")
    public String emailAddress;
    
  
    @Column(name="till_account")
    public String tilAccount;
    
   
    @Column(name = "user_role")
    public long userRole;
    
    @Column(name = "user_role_str")
    public String userRoleStr;
     
    @Column(name="full_name")
    public String full_name;
    
     @Transient
     public String getFullName() {
    return firstName + " " + (middleName != null ? middleName + " " : "") + lastName;
    }     
     
     @Transient
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
    

   
}
