/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.resources;

//import io.smallrye.common.constraint.NotNull;
import io.smallrye.common.constraint.NotNull;
import java.math.BigDecimal;
import jakarta.json.Json;

/**
 *
 * @author taysayshaguy
 */
public record CUResponseObj(@NotNull String pin,@NotNull String receiptNo,@NotNull String status,@NotNull String fullResp)
{
   
    public String toJson() {
        
        
        return Json.createObjectBuilder().add("pin", this.pin).add("receiptNo", this.receiptNo).add("status", this.status).add("fullResp", this.fullResp).build().toString();
    }
    
    

    
}
