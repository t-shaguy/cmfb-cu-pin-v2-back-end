/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;

import com.paycraftsystems.resources.ValidationHelper;
import java.io.Serializable;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/**
 *
 * @author root
 */
public class RolesInfoDTO extends ValidationHelper implements Serializable {
   
    public Long tid;
    public String roleName;
    public String roleDesc;
    public String roleCode;
    public long createdBy;
    public Long lastUpdatedBy;

    public RolesInfoDTO() {
    }


    
    public JsonObject toJson() {
        
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
            job.add("roleName", toDefault(this.roleName))
               .add("roleDesc", toDefault(this.roleDesc))
               .add("roleCode", toDefault(this.roleCode));
        } 
        catch (Exception e) {
        
        }
      return job.build();
    }

    @Override
    public String toString() {
        return "RolesInfoDTO{" + "tid=" + tid + ", roleName=" + roleName + ", roleDesc=" + roleDesc + ", roleCode=" + roleCode + ", createdBy=" + createdBy + ", lastUpdatedBy=" + lastUpdatedBy + '}';
    }

   
    
}
