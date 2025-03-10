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
public class PaymentBeneficiaryRequestObj
{
    
   public PaymentBeneficiaryRequestObj(PaymentBeneficiaryRequest rx)
     {

        this.beneficiaryDesc= rx.beneficiaryDesc();
        this.beneficiaryName= rx.beneficiaryName();
        this.beneficiaryAddress= rx.beneficiaryAddress();
        this.beneficiaryContactPersonName= rx.beneficiaryContactPersonName();
        this.beneficiaryContactPersonEmail= rx.beneficiaryContactPersonEmail();
        this.beneficiaryContactPersonMobile= rx.beneficiaryContactPersonMobile();
        this.actionBy= rx.actionBy();
        this.actionByStr= rx.actionByStr();
        //this.beneficiaryCollectionAccount = rx.beneficiaryCollectionAccount();
        //this.beneficiaryCollectionAccountName = rx.beneficiaryCollectionAccountName();

     }
   
    public String beneficiaryDesc;
    public String beneficiaryName;
    public String paymentCollectionAccount;
    public String paymentCollectionAccountName;
    //public String beneficiaryCollectionAccountName;
    //public String beneficiaryCollectionAccount;
    public String beneficiaryAddress;
    public String beneficiaryContactPersonName;
    public String beneficiaryContactPersonEmail;
    public String beneficiaryContactPersonMobile;
    public long actionBy;
    public String actionByStr;
    
   
   
}
