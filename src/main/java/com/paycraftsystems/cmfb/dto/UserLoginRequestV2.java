/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

/**
 *
 * @author paycraftsystems-i
 */
public record UserLoginRequestV2(String code, String codeLink, String password, String channel, long pid) {
    
  //  JsonObject authSync = Json.createObjectBuilder().add("code", rh.toDefault(doLookup.emailAddress)).add("codeLink", rh.toDefault(doLookup.mobileNo)).add("password", rh.toDefault(fromJson.password)).add("channel", sysDataHelper.getProps("CHANNEL", "0")).add("pid", doLookup.tid).build();
                  
    
}
