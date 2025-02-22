/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 *
 * @author root
 */

public record SysDataRequest(@NotBlank @Size(min=2, max= 100, message="Sys Data param must be valid") String paramName,@NotBlank @Size(min=1, max= 200, message="Sys Data value must be valid")String paramValue,@Min(1)long actionBy, @NotBlank @Size(min=3, max= 100, message="Sys Data account by Str must be valid") String actionByStr)
{
    
}
