/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

import lombok.ToString;

/**
 *
 * @author paycraftsystems-i
 */
@ToString
public class ResetSysCredRequestObj
{
              
    
    public ResetSysCredRequestObj(ResetSysCredRequest rx)
    {
        this.ux = rx.ux();
        this.key = rx.key();
        this.partnerCode = rx.partnerCode();
        
    }
    
    public String ux;
    public String key;
    public String partnerCode;
}
