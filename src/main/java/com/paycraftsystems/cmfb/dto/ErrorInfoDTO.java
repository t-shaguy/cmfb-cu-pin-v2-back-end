/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

/**
 *
 * @author paycraftsystems-i
 */
public class ErrorInfoDTO {
    
     public String timestamp;
    public String code;
    public String message;
    
    public String toErrorInfo() {
        
      return "error code : "+code+" error message  "+message;
    }
    

    @Override
    public String toString() {
        return "EasyPayErrorDTO{" + "timestamp=" + timestamp + ", code=" + code + ", message=" + message + '}';
    }
    
}
