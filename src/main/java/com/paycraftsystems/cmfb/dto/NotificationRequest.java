/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;


//import com.paycraftsystems.fsi.resources.ValidationHelper;
import com.paycraftsystems.resources.ValidationHelper;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/**
 *
 * @author paycraftsystems-i
 */
public class NotificationRequest {
    
    
    public String addressee;
    public String sendTo;
    public String subject;
    public String toShare;
    public String toShare1;
    public String toShare2;
    public String message;
    public String option;
    public String requestId;
    public String shortMessage;
    public String toShareLabel;
    public String iv;
    public String key;
    
    
    public JsonObject toJson() {
        
        ValidationHelper rh = new ValidationHelper();
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
            
            job.add("addressee", rh.toDefault(this.addressee))
               .add("sendTo", rh.toDefault(this.sendTo))
               .add("subject", rh.toDefault(this.subject))
               .add("toShare", rh.toDefault(this.toShare))
               .add("toShare1", rh.toDefault(this.toShare1))
               .add("toShare2", rh.toDefault(this.toShare2))
               .add("message", rh.toDefault(this.message))
               .add("option", rh.toDefault(this.option))
               .add("requestId", rh.toDefault(this.requestId))
               .add("shortMessage", rh.toDefault(this.shortMessage))
               .add("toShareLabel", rh.toDefault(this.toShareLabel));
          
            
        } catch (Exception e) {
        
            e.printStackTrace();
        
        }
        
        
        return job.build();
    }

    @Override
    public String toString() {
        return "NotificationRequest{" + "addressee=" + addressee + ", sendTo=" + sendTo + ", subject=" + subject + ", toShare=" + toShare + ", toShare1=" + toShare1 + ", toShare2=" + toShare2 + ", message=" + message + ", option=" + option + ", requestId=" + requestId + ", shortMessage=" + shortMessage + ", toShareLabel=" + toShareLabel + ", iv=" + iv + ", key=" + key + '}';
    }

}
