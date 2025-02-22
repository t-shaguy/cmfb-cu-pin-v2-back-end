/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 *
 * @author paycraftsystems-i
 */
public record RolesInfoEditRequest(@Min(1) long tid, @NotBlank @Size(max=30, min= 3, message="Role name must be valid ") String roleName, @NotBlank @Size(max=40, min= 3, message="Role desc must be valid ") String roleDesc, @Min(1) long actionBy, @NotBlank @Size(max=50, min= 3, message="action by must be valid ") String actionByStr) {
    
}
