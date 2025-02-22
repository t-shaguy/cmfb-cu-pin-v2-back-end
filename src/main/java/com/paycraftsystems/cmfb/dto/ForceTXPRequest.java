/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

import com.paycraftsystems.validators.E164PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 *
 * @author paycraftsystems-i
 */
public record ForceTXPRequest(@NotBlank @Email @E164PhoneNumber String mobileNo)
{
    
}