/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.vanso.dto;

/**
 *
 * @author taysayshaguy
 */
public record VansoResponse(Long tid,Integer errorCode,String errorMessage,String ticketId,String destination,String referenceId,String status)
{    
      
}
