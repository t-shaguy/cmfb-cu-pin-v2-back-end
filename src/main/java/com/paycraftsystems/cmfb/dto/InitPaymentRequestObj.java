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
public class InitPaymentRequestObj
{
    
 public InitPaymentRequestObj(InitPaymentRequest rx)
 {
     
          this.paymentId = rx.paymentId();
          this.fee = rx.fee();
          this.amount = rx.amount();
          this.actionBy = rx.actionBy();
          this.actionByStr = rx.actionByStr();
          //this.paymentDesc = rx.
          this.payeeFirstName = rx.payeeFirstName();
          this.payeeMiddleName = rx.payeeMiddleName();
          this.payeeLastName = rx.payeeLastName();
          this.payeeEmail = rx.payeeEmail();
          this.payeeAddress = rx.payeeAddress();
          this.payeeMobileNo = rx.payeeMobileNo();
          this.payeeId = rx.payeeId();
     
 }
  
 
 
 
 public long paymentId;
// public String plansDesc;
// public Boolean amountFixed;
// public Boolean taxInc;
 public double fee;
 public double amount;
 public long actionBy;
 public String actionByStr;
 //public String providerName;
 public String paymentDesc;
 public String payeeFirstName; 
 public String payeeMiddleName;
 public String payeeLastName;
 public String payeeEmail;
 public String payeeAddress;
 public String payeeMobileNo;
 public String payeeId;
   
}
