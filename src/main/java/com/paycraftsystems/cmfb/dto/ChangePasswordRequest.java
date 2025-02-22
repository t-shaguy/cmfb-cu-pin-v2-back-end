/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;

import com.paycraftsystems.validators.CMFBUserPassword;
import com.paycraftsystems.validators.VerifyPasswordValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 *
 * @author taysayshaguy
 */
@VerifyPasswordValidator(field = "newPassword", matchField = "verifyPassword", message = "Password and verify password must match")

public record ChangePasswordRequest(
        @NotBlank 
        @CMFBUserPassword 
        String newPassword,
        @NotBlank
        @CMFBUserPassword 
        String verifyPassword,
        
        @NotBlank 
        @Size(min=3, max=60, message="user name must be valid") 
        String userName,
        
        @NotBlank 
        @CMFBUserPassword 
        String password
       ){
   
}
