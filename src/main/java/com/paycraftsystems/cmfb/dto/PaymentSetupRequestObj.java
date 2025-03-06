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
public class PaymentSetupRequestObj
{
  
public PaymentSetupRequestObj(PaymentSetupRequest rx)
{
        //this.tid = rx.tid();
        //this.plansDesc = rx.paymentDesc();
        this.amountFixed = rx.amountFixed();
        this.payeeIdLabel = rx.payeeIdLabel();
        this.min_amount = rx.min_amount();
        this.amount = rx.amount();
        this.actionBy = rx.actionBy();
        this.actionByStr = rx.actionByStr();
        this.beneficiaryId = rx.beneficiaryId();
        this.paymentDesc = rx.paymentDesc();
        this.includeTax = rx.taxInc();
        this.taxAmount = rx.taxAmount();
        this.fee = rx.fee();
        this.paymentCollectionAccount = rx.paymentCollectionAccount();
        this.paymentCollectionAccountName = rx.paymentCollectionAccountName();

}

 
 public String paymentCollectionAccount;
 public String paymentCollectionAccountName;
 public long tid;
 public long beneficiaryId;
 public String payeeIdLabel;
 public Boolean amountFixed;
 public Boolean includeTax;
 public double taxAmount;
 public double min_amount;
 public double amount;
 public double fee;
 public long actionBy;
 public String actionByStr;
 public String paymentDesc;



}
