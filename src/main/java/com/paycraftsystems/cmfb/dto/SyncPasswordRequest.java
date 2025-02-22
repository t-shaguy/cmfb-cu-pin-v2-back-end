/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

/**
 *
 * @author paycraftsystems-i
 */
public record SyncPasswordRequest(String code, String codeLink, String password, String verifyPassword,  String msisdn, long pid, String controlCode) {
    
    
    /*
    if(fromJson !=null  && rh.isNullorEmpty(fromJson.getPassword()))
            {
                return Response.status(ErrorCodes.INVALID_PASSWORD).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCode()))
            {
                return Response.status(ErrorCodes.INVALID_USER_CODE).build();
            }
            if(fromJson !=null  && rh.isNullorEmpty(fromJson.getCodeLink()))
            {
               return Response.status(ErrorCodes.INVALID_USER_CODELINK).build();
            }
            
            if(fromJson !=null  && !fromJson.getPassword().equals(fromJson.getVerifyPassword()))
            {
               return Response.status(ErrorCodes.INVALID_CONFIRM_PASSWORD).build();
               
            }
    private String code;
    private String codeLink;
    private String password;
    private String verifyPassword;
    private String pin;
    private String verifyPin;
    private String newPassword;
    private String newPin;
    private String msisdn;
    private String userCode;
    private long pid;
    private String controlCode;
    private String principal;
    private String principalControlCode;
    */
    
}
