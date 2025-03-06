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
    
 
}
