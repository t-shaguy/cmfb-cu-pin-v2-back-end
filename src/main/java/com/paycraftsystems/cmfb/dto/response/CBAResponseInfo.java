/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto.response;

/**
 *
 * @author paycraftsystems-i
 */
public record CBAResponseInfo(int Retval, String Retmsg, String DepositorName, double Amount, String Narration, String TransactionDate, String ReferenceID) {

    
}
