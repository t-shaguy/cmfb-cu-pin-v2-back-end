/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

import com.paycraftsystems.resources.ValidationHelper;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/**
 *
 * @author paycraftsystems-i
 */
public class ResetResponse
{
    public String responseDesc;
    public String iv;
    public String key;
    public long maxage;
    
    
    public JsonObject toJson() {
        
        ValidationHelper rh = new ValidationHelper();
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
            job.add("iv", rh.toDefault(this.iv))
               .add("key", rh.toDefault(this.key))
               .add("maxage", this.maxage)
                .add("errorDesc", rh.toDefault(this.responseDesc));
            
        } catch (Exception e) {
        
        }
     return job.build();
    }
    

    @Override
    public String toString() {
        return "ResetResponse{" + "responseDesc=" + responseDesc + ", iv=" + iv + ", key=" + key + '}';
    }
   
}
