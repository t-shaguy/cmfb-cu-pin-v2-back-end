/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;

/**
 *
 * @author paycraftsystems-i
 */
public class MailObj {
    
    public String ourUrl;
    public String addressee;
    public String mailTo;
    public String cc;
    public String US;
    public String www;
    public String accountNo;
    public String banner;
    public String otp;
    public String toShare;
    public String toShareLabel;
    public String otpExpiration;
    public String receipient;
    public String subject;
    public String message;
    public String senderInfo;
    public String supportInfo;
    public String supportLines;
    public String mailTitle;
    public String tableHDColor;
    public String tableBGColor;
    public String tableBorderColor;
    public String toShare1;
    public String toShare2;

    @Override
    public String toString() {
        return "MailObj{" + "ourUrl=" + ourUrl + ", addressee=" + addressee + ", mailTo=" + mailTo + ", cc=" + cc + ", US=" + US + ", www=" + www + ", accountNo=" + accountNo + ", banner=" + banner + ", otp=" + otp + ", toShare=" + toShare + ", toShareLabel=" + toShareLabel + ", otpExpiration=" + otpExpiration + ", receipient=" + receipient + ", subject=" + subject + ", message=" + message + ", senderInfo=" + senderInfo + ", supportInfo=" + supportInfo + ", supportLines=" + supportLines + ", mailTitle=" + mailTitle + ", tableHDColor=" + tableHDColor + ", tableBGColor=" + tableBGColor + ", tableBorderColor=" + tableBorderColor + ", toShare1=" + toShare1 + ", toShare2=" + toShare2 + '}';
    }

}
