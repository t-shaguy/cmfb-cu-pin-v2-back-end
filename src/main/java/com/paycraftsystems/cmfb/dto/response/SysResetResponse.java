/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto.response;

/**
 *
 * @author paycraftsystems-i
 */
public record SysResetResponse(String code, String responseDesc, String iv, String key, String maxage) {
    
    
    //jsonObject = Json.createObjectBuilder().add("responseDesc", "Reset Successful, Please use new keys going forward (pass iv as the IV and key as the Key in AES encryption ) ").add("iv", ivee).add("key", key).add("maxage",byUniqueID.getTokenLifespanDays()).build();
           
    
}
