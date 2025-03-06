/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.vanso.dto;

import java.util.Objects;

/**
 *
 * @author taysayshaguy
 */
public record SMSRequestObjX(Long tid,String sendReply,String username,String password,String destAddress,String message, String url)
{
   
}
