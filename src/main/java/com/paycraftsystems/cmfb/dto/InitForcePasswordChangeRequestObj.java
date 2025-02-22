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
public class InitForcePasswordChangeRequestObj
{
    public InitForcePasswordChangeRequestObj(InitForcePasswordChangeRequest rx)
    {
        this.emailAddress = rx.emailAddress();
    }
    
    
    public String emailAddress;
}