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
public class CompletePaymentRequestObj
{
  
    public CompletePaymentRequestObj(CompletePaymentRequest rx)
    {
         this.paymentId = rx.paymentId();
         this.fee = rx.fee();
         this.taxAmount = rx.taxAmount();
         this.amount = rx.amount();
         this.actionBy = rx.actionBy();
         this.actionByStr = rx.actionByStr();
         this.paymentReference = rx.paymentReference();
        
    }
   
    
   public String paymentReference;
   public long paymentId;
   public double fee;
   public double taxAmount;
   public double amount;
   public long actionBy;
   public String actionByStr;
   
}
