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
public record FilterSysDataRequest(
        //2016001244
        String searchParam,
        @NotBlank
        @Size(min=19,max=19, message="fromDate must be in the format yyyy-MM-dd HH:mm:ss")
        String fromDate,
        @NotBlank
        @Size(min=19,max=19, message="toDate must be in the format yyyy-MM-dd HH:mm:ss")
        String toDate, 
       
        @NotBlank
        @Size(min=3, max=12, message="status must be valid, possible values are all, pending, paid, cancelled ")       
        String status, 
        @Min(1)
        int pageIndex, 
        @Min(1)
        int pageSize
        ) 
{
   
}
