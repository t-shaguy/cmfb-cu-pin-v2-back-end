/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto.response;

/**
 *
 * @author paycraftsystems-i
 */
public record ManagedLoginResponse(boolean success, int errorCode, String errorDesc, String jwt, String firstName,  String middleName, String mobileNo, String emailAddress, long tid, long pinChange, String error) {
 
}
