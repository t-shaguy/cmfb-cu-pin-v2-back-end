/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cu.dto;

/**
 *
 * @author paycraftsystems-i
 */
public record VerifyPaymentRequest(String payerid, String username, String pword, String transid, String payername, String payernum, String paymenttype, String tenantcode, String paydate, String amount)
{
}
