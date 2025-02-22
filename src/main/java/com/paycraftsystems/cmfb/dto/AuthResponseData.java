/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

/**
 *
 * @author paycraftsystems-i
 */
public record AuthResponseData(String AUTHORIZATION, String customerCode, String customerName, String max_age) {
    
    /*
      return Response.accepted(Json.createObjectBuilder().add(AUTHORIZATION, "Bearer " + token).add("customerCode", rh.toDefault(doLoginObj.customerCode)).add("customerName", rh.toDefault(doLoginObj.apiUserCustomerName)).add("max-age", byCredentials.getTokenLifespanDays()).build().toString()).header(AUTHORIZATION, "Bearer " + token).build();
            
    */
    
}
