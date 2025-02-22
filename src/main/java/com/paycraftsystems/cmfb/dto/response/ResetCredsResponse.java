/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto.response;

import jakarta.json.JsonObject;

/**
 *
 * @author paycraftsystems-i
 */
public record ResetCredsResponse(boolean success, int errorCode, String respDesc, JsonObject respData, String error) {
    
}
