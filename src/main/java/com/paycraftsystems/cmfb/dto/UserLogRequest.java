/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;

/**
 *
 * @author root
 */

public record UserLogRequest(long tid, String fullName,String firstName,String lastName,String middleName,int status,String emailAddress,String phonePri,String phoneSec,String address1,String address2,long state,String partnerCode,String partnerClassCode,String schemeCode,String bvn,String bvnOrNin,String userRole, String code, String userName,String password, String newPassword,String newPin,String pin,String verifyPin,String verifyPassword,  String otp, String px, String verifyPx,long createdBy,long lastUpdatedBy,String refCode,String profileImage,String imageFormat,long pid,String userRoleStr,String op)
{


    /*
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
               .add("partnerCode", toDefault(this.partnerCode))
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
 */

}
