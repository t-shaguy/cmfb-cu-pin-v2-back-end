/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto.response;


import com.paycraftsystems.cmfb.entities.RolesInfo;
import java.util.List;

/**
 *
 * @author paycraftsystems-i
 */
public record RolesResponse(boolean success, int errorCode, String respDesc, List<RolesInfo> data, String error) {
    
}
