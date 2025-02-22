/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 *
 * @author taysayshaguy
 */
public record ResetRequest(@NotBlank @Email @Size(min=6, max=100, message="the ux value must be valid ") String ux)
{

}
