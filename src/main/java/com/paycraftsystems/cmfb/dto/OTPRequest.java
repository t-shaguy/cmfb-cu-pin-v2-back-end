/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author root
 */
@VerifyPasswordValidator(field = "password", matchField = "verifyPassword", message = "Password and verify password must match")

public record OTPRequest(
       
        @NotBlank 
        @CMFBUserPassword 
        String password,
        @NotBlank 
        @CMFBUserPassword 
        String verifyPassword,
        @NotBlank 
        @DigitsOnly @Size(min=4, max=6, message="the otp must be at least 4 and at most 6 digits only") 
        String otp,
        @NotBlank 
        @Size(min=1, max=100, message="The email Address must be valid ") 
        @Email String emailAddress
        )
{
  //,@DigitsOnly @Size(min=4, max=6, message="the pin must be at least 4 and at most 6 digits only") String pin, @DigitsOnly @Size(min=4, max=6, message="the pin must be at least 4 and at most 6 digits only")  String verifyPin, 

}
