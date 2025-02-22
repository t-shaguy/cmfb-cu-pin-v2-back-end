/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;


//import com.paycraftsystems.resources.ValidationHelper;
import com.paycraftsystems.resources.ValidationHelper;
import java.io.Serializable;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/**
 *
 * @author root
 */

public class UserSignUpDTO extends ValidationHelper implements Serializable {
    
    public long tid;
    public String fullName;
    public String firstName;
    public String lastName;
    public String middleName;
    public int status;
    public String emailAddress;
    public String phonePri;
    public String phoneSec;
    public String address1;
    public String address2;
    public long state;
    public String customerCode;
    public String superAgentCode;
    public String schemeCode;
    public String partnerId;
    public long clientCategory;
    public String bvn;
    public String userRole;
    public String code;
    public String userName;
    public String password;
    public String newPassword;
    public String newPin;
    public String pin;
    public String verifyPin;
    public String verifyPassword;
    public String otp;
    public String px;
    public String verifyPx;
    public long createdBy;
    public long lastUpdatedBy;
    public String refCode;
    public String profileImage;
    public String imageFormat;
    public long pid;
    public String userRoleStr;
    public String op;
    public String gender;
    public long country;
    public String deviceFingerprint;

    public UserSignUpDTO() {
    }


    public UserSignUpDTO(String fullName, String firstName, String lastName, String middleName) {
        this.fullName = fullName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }


    
    public JsonObject toJson() {
        
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
            
            job.add("fullName", toDefault(this.fullName))
               .add("firstName", toDefault(this.firstName))
               .add("lastName", toDefault(this.lastName))
               .add("middleName", toDefault(this.middleName))
               .add("status",  this.status)
               .add("emailAddress", toDefault(this.emailAddress))
               .add("phonePri", toDefault(this.phonePri))
               .add("phoneSec", toDefault(this.phoneSec))
               .add("address1", toDefault(this.address1))
               .add("address2", toDefault(this.address2))
               .add("customerCode", toDefault(this.customerCode))
               .add("userRole", toDefault(this.userRole))
               .add("refCode", toDefault(this.refCode))
               .add("code", toDefault(this.code))
               .add("userName", toDefault(this.userName))
               .add("password", "ITS ELECTRIC")
               .add("newPassword", "ITS ELECTRIC")//toDefault(this.newPassword))
               .add("verifyPassword", "ITS ELECTRIC");//toDefault(this.verifyPassword));
              
            
        } catch (Exception e) {
        
            e.printStackTrace();
        
        }
        
    
      return job.build();
    }

    @Override
    public String toString() {
        return "UserSignUpDTO{" + "tid=" + tid + ", fullName=" + fullName + ", firstName=" + firstName + ", lastName=" + lastName + ", middleName=" + middleName + ", status=" + status + ", emailAddress=" + emailAddress + ", phonePri=" + phonePri + ", phoneSec=" + phoneSec + ", address1=" + address1 + ", address2=" + address2 + ", state=" + state + ", customerCode=" + customerCode + ", superAgentCode=" + superAgentCode + ", schemeCode=" + schemeCode + ", partnerId=" + partnerId + ", clientCategory=" + clientCategory + ", bvn=" + bvn + ", userRole=" + userRole + ", code=" + code + ", userName=" + userName + ", password=" + password + ", newPassword=" + newPassword + ", newPin=" + newPin + ", pin=" + pin + ", verifyPin=" + verifyPin + ", verifyPassword=" + verifyPassword + ", otp=" + otp + ", px=" + px + ", verifyPx=" + verifyPx + ", createdBy=" + createdBy + ", lastUpdatedBy=" + lastUpdatedBy + ", refCode=" + refCode + ", profileImage=" + profileImage + ", imageFormat=" + imageFormat + ", pid=" + pid + ", userRoleStr=" + userRoleStr + ", op=" + op + ", gender=" + gender + ", country=" + country + ", deviceFingerprint=" + deviceFingerprint + '}';
    }

    
    


}
