/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.resources;

/**
 *
 * @author taysayshaguy
 */
public class ThirdPartyPayOBJ {
    
    private String mcode;
    private String key;
    private String payref;
    private String paymentname;
    private String merchant;
    private String receiptNo;
    private String sysusername;
    private String bankid;
    private String phone;
    private String payeeAddress;
    private String transDate;
    private String payId;
    private String paymentDesc;
    private String amount;
    private String syspassword;
    private String lastname;
    private String othernames;

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPayref() {
        return payref;
    }

    public void setPayref(String payref) {
        this.payref = payref;
    }

    public String getPaymentname() {
        return paymentname;
    }

    public void setPaymentname(String paymentname) {
        this.paymentname = paymentname;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public ThirdPartyPayOBJ() {
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getSysusername() {
        return sysusername;
    }

    public void setSysusername(String sysusername) {
        this.sysusername = sysusername;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPayeeAddress() {
        return payeeAddress;
    }

    public void setPayeeAddress(String payeeAddress) {
        this.payeeAddress = payeeAddress;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPaymentDesc() {
        return paymentDesc;
    }

    public void setPaymentDesc(String paymentDesc) {
        this.paymentDesc = paymentDesc;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSyspassword() {
        return syspassword;
    }

    public void setSyspassword(String syspassword) {
        this.syspassword = syspassword;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getOthernames() {
        return othernames;
    }

    public void setOthernames(String othernames) {
        this.othernames = othernames;
    }

    public ThirdPartyPayOBJ(String mcode, String key, String payref, String paymentname, String merchant, String receiptNo, String sysusername, String bankid, String phone, String payeeAddress, String transDate, String payId, String paymentDesc, String amount, String syspassword, String lastname, String othernames) {
        this.mcode = mcode;
        this.key = key;
        this.payref = payref;
        this.paymentname = paymentname;
        this.merchant = merchant;
        this.receiptNo = receiptNo;
        this.sysusername = sysusername;
        this.bankid = bankid;
        this.phone = phone;
        this.payeeAddress = payeeAddress;
        this.transDate = transDate;
        this.payId = payId;
        this.paymentDesc = paymentDesc;
        this.amount = amount;
        this.syspassword = syspassword;
        this.lastname = lastname;
        this.othernames = othernames;
    }

    @Override
    public String toString() {
        return "ThirdPartyPayOBJ{" + "mcode=" + mcode + ", key=" + key + ", payref=" + payref + ", paymentname=" + paymentname + ", merchant=" + merchant + ", receiptNo=" + receiptNo + ", sysusername=" + sysusername + ", bankid=" + bankid + ", phone=" + phone + ", payeeAddress=" + payeeAddress + ", transDate=" + transDate + ", payId=" + payId + ", paymentDesc=" + paymentDesc + ", amount=" + amount + ", syspassword=" + syspassword + ", lastname=" + lastname + ", othernames=" + othernames + '}';
    }

    
}
