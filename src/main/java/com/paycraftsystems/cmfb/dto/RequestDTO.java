/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;

import com.paycraftsystems.resources.ValidationHelper;
import java.math.BigDecimal;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/**
 *
 * @author root
 */
public class RequestDTO extends ValidationHelper{
    
    
    private String srcAccount;
    private String destAccount;
    private String destBankCode;
    private String destBankName;
    private String amount;
    private String fee;
    private String msisdn;
    private String pin;
    private String newPin;
    private String verifyPin;
    private String telcoCode;
    private String billerId;
    private String beneficiaryAccountName;
    private String srcAccountName;
    private String narration;
    private String chequeNo;
    private int chequeLeaves;
    private String srcBVN;
    private String destBVN;
    private String srcKYC;
    private String destKYC;
    private String requestId;
    private String oRequestId;
    private String transLocation;
    private String nameEnquiryRequestId;
    private String OTP;
    private String message;
    private int userRole;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String mobileNo;
    private String actionbyStr;
    private long actionby;
    //private String actionbyStr;
    
    
    public String getSrcAccount() {
        return srcAccount;
    }

    public void setSrcAccount(String srcAccount) {
        this.srcAccount = srcAccount;
    }

    public String getDestAccount() {
        return destAccount;
    }

    public void setDestAccount(String destAccount) {
        this.destAccount = destAccount;
    }

    public String getDestBankCode() {
        return destBankCode;
    }

    public void setDestBankCode(String destBankCode) {
        this.destBankCode = destBankCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    
    

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getTelcoCode() {
        return telcoCode;
    }

    public void setTelcoCode(String telcoCode) {
        this.telcoCode = telcoCode;
    }

    public String getBillerId() {
        return billerId;
    }

    public void setBillerId(String billerId) {
        this.billerId = billerId;
    }

    public String getBeneficiaryAccountName() {
        return beneficiaryAccountName;
    }

    public void setBeneficiaryAccountName(String beneficiaryAccountName) {
        this.beneficiaryAccountName = beneficiaryAccountName;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public int getChequeLeaves() {
        return chequeLeaves;
    }

    public void setChequeLeaves(int chequeLeaves) {
        this.chequeLeaves = chequeLeaves;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }

   

    public String getSrcBVN() {
        return srcBVN;
    }

    public void setSrcBVN(String srcBVN) {
        this.srcBVN = srcBVN;
    }

    public String getDestBVN() {
        return destBVN;
    }

    public void setDestBVN(String destBVN) {
        this.destBVN = destBVN;
    }

    public String getSrcKYC() {
        return srcKYC;
    }

    public void setSrcKYC(String srcKYC) {
        this.srcKYC = srcKYC;
    }

    public String getDestKYC() {
        return destKYC;
    }

    public void setDestKYC(String destKYC) {
        this.destKYC = destKYC;
    }

    public String getTransLocation() {
        return transLocation;
    }

    public void setTransLocation(String transLocation) {
        this.transLocation = transLocation;
    }

    public String getSrcAccountName() {
        return srcAccountName;
    }

    public void setSrcAccountName(String srcAccountName) {
        this.srcAccountName = srcAccountName;
    }

    public String getVerifyPin() {
        return verifyPin;
    }

    public void setVerifyPin(String verifyPin) {
        this.verifyPin = verifyPin;
    }

    public String getDestBankName() {
        return destBankName;
    }

    public void setDestBankName(String destBankName) {
        this.destBankName = destBankName;
    }

    public String getActionbyStr() {
        return actionbyStr;
    }

    public void setActionbyStr(String actionbyStr) {
        this.actionbyStr = actionbyStr;
    }

    public long getActionby() {
        return actionby;
    }

    public void setActionby(long actionby) {
        this.actionby = actionby;
    }
    
    

    
    public JsonObject toJson() {
        
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
            
            job.add("srcAccount", toDefault(this.srcAccount))
               .add("destAccount", toDefault(this.destAccount))
               .add("destBankCode", toDefault(this.destBankCode))
               .add("amount", toDefault(this.amount, "0.0"))
               .add("msisdn", toDefault(this.msisdn))
               .add("pin", toDefault(this.pin))
               .add("telcoCode", toDefault(this.telcoCode))
               .add("billerId", toDefault(this.billerId))
               .add("beneficiaryAccountName", toDefault(this.beneficiaryAccountName))
               .add("narration", toDefault(this.narration))
               .add("chequeNo", toDefault(this.chequeNo))
               .add("chequeLeaves", this.chequeLeaves)
               .add("srcBVN", toDefault(this.srcBVN))
               .add("destBVN", toDefault(this.destBVN))
               .add("srcKYC", toDefault(this.srcKYC))
               .add("requestId", toDefault(this.requestId))
               .add("oRequestId", toDefault(this.oRequestId))
               .add("transLocation", toDefault(this.transLocation)) 
               .add("destKYC", toDefault(this.destKYC));
          
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        
        }
       return job.build(); 
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getoRequestId() {
        return oRequestId;
    }

    public void setoRequestId(String oRequestId) {
        this.oRequestId = oRequestId;
    }


    public String getNameEnquiryRequestId() {
        return nameEnquiryRequestId;
    }
    
    public void setNameEnquiryRequestId(String nameEnquiryRequestId) {
        this.nameEnquiryRequestId = nameEnquiryRequestId;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RequestDTO{" + "srcAccount=" + srcAccount + ", destAccount=" + destAccount + ", destBankCode=" + destBankCode + ", destBankName=" + destBankName + ", amount=" + amount + ", fee=" + fee + ", msisdn=" + msisdn + ", pin=" + pin + ", newPin=" + newPin + ", verifyPin=" + verifyPin + ", telcoCode=" + telcoCode + ", billerId=" + billerId + ", beneficiaryAccountName=" + beneficiaryAccountName + ", srcAccountName=" + srcAccountName + ", narration=" + narration + ", chequeNo=" + chequeNo + ", chequeLeaves=" + chequeLeaves + ", srcBVN=" + srcBVN + ", destBVN=" + destBVN + ", srcKYC=" + srcKYC + ", destKYC=" + destKYC + ", requestId=" + requestId + ", oRequestId=" + oRequestId + ", transLocation=" + transLocation + ", nameEnquiryRequestId=" + nameEnquiryRequestId + ", OTP=" + OTP + ", message=" + message + ", userRole=" + userRole + ", firstName=" + firstName + ", lastName=" + lastName + ", emailAddress=" + emailAddress + ", mobileNo=" + mobileNo + ", actionbyStr=" + actionbyStr + ", actionby=" + actionby + '}';
    }

}
