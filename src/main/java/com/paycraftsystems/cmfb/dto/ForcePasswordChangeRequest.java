/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

import com.paycraftsystems.validators.CMFBUserPassword;
import com.paycraftsystems.validators.DigitsOnly;
import com.paycraftsystems.validators.VerifyPasswordValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 *
 * @author paycraftsystems-i
 */
@VerifyPasswordValidator(field = "password", matchField = "verifyPassword", message = "Password and verify password must match")
public record ForcePasswordChangeRequest(
        @NotBlank @CMFBUserPassword String password, 
        @NotBlank @CMFBUserPassword String verifyPassword,
        @NotBlank 
        @Email @Size(min=3,max=100, message="email address must be valid") String emailAddress, 
        @NotBlank
        @DigitsOnly @Size(min=4, max=6, message="otp must be valid") 
        String otp
       
        )
{
    
}