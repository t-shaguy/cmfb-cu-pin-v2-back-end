/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;

import lombok.ToString;

/**
 *
 * @author taysayshaguy
 */
@ToString
public class SysLoginRequestObj{

public SysLoginRequestObj(SysLoginRequest rx )
{
    this.ux = rx.ux();
    this.iv = rx.iv();
    this.key = rx.key();

}


public String ux;
public String iv;
public String key;

}
