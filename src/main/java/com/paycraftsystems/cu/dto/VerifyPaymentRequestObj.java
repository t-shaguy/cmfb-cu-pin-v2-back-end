/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cu.dto;

import lombok.ToString;

/**
 *
 * @author paycraftsystems-i
 */
@ToString
public class VerifyPaymentRequestObj
{
    public VerifyPaymentRequestObj(VerifyPaymentRequest rx)
    {
        
        this.payerid= rx.payerid();
        this.username= rx.username();
        this.pword= rx.pword();
        this.transid= rx.transid();
        this.payername= rx.payername();
        this.payernum= rx.payernum();
        this.paymenttype= rx.paymenttype();
        this.tenantcode= rx.tenantcode();
        this.paydate= rx.paydate();
        this.amount= rx.amount();
        
    }
    
    
    
      
    
    public String payerid;
    public String username;
    public String pword;
    public String transid;
    public String payername;
    public String payernum;
    public String paymenttype;
    public String tenantcode;
    public String paydate;
    public String amount;
    
            
}
