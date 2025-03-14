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

public record SysDataEditRequest(@Min(1)long tid,@NotBlank @Size(min=2, max=100, message="param name must be valid") String paramName,@NotBlank @Size(min=2, max=200, message="param value must be valid")String paramValue,@Min(1) long actionBy, @NotBlank @Size(min=2, max=100, message="action by String must be valid")String actionByStr)
{
    
}
