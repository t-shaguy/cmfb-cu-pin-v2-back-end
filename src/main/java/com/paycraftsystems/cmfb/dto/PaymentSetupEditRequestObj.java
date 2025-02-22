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
public class PaymentSetupEditRequestObj
{
  
public PaymentSetupEditRequestObj(PaymentSetupEditRequest rx)
{
        this.tid = rx.tid();
        this.beneficiaryId = rx.beneficiaryId();
        this.amountFixed = rx.amountFixed();
        this.min_amount = rx.min_amount();
        this.amount = rx.amount();
        this.actionBy = rx.actionBy();
        this.actionByStr = rx.actionByStr();
        //this.providerName = rx.providerName();
        this.paymentDesc = rx.paymentDesc();
        this.includeTax = rx.taxInc();
        this.taxAmount = rx.taxAmount();

}

 public long beneficiaryId;
 public long tid;
 public String plansDesc;
 public Boolean amountFixed;
 public Boolean includeTax;
 public double taxAmount;
 public double min_amount;
 public double amount;
 public long actionBy;
 public String actionByStr;
// public String providerName;
 public String paymentDesc;



}
