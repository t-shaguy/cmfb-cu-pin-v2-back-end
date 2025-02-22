/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

/**
 *
 * @author paycraftsystems-i
 */
public record ChangeUserPasswordRequest(long pid, String code, String codeLink, String password, String verifyPassword, String newPassword,  String channel) {
                        
    
}
