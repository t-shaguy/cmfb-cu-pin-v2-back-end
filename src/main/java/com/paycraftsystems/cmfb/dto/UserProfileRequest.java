/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;

import com.paycraftsystems.validators.E164PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


/**
 *
 * @author root
 */

public record UserProfileRequest(
        @NotBlank @Size(min=2, max=50, message="First Name must be valid") 
        String firstName,
        String middleName, @NotBlank @Size(min=2, max=50, message="Last Name must be valid") 
        String lastName, 
        @NotBlank 
        @E164PhoneNumber 
        String mobileNo, 
        @NotBlank 
        @Size(min=10, max=10, message="Till Account must be valid") 
        String tilAccountNo, 
        @NotBlank 
        @Email 
        String emailAddress, 
        @Min(1) 
        int userRole,
         @Min(1) 
        int actionBy,
        @NotBlank
        @Email 
        String actionByStr
        
        
        )
{
    
  
}
