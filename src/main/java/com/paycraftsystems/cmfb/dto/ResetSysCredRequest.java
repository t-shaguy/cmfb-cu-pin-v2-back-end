/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import java.math.BigDecimal;

/**
 *
 * @author paycraftsystems-i
 */
public record ResetSysCredRequest(String ux, String key, String partnerCode) {
    
    // JsonObject authJson = Json.createObjectBuilder().add("ux", rh.toDefault(doGetClientByID.clientId)).add("key", json.getString("key", "NA")).add("iv", json.getString("iv", "NA")).add("partnerCode",rh.toDefault(doGetClientByID.customerCode)).build();
        
    
    public  JsonObject toJson() {
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
            job.add("ux", this.ux).add("key", this.key).add("partnerCode", this.partnerCode);
            
        } 
        catch (Exception e) {
        
        }
        return job.build();
    }
    
    
}
