/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto.response;

/**
 *
 * @author paycraftsystems-i
 */
public record SysLoginResponse(boolean success, int code, String errorDesc,  AuthSyncResponse loginData ) {
    
}
