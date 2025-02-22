/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;

import jakarta.json.Json;


/**
 *
 * @author taysayshaguy
 */
public class HeaderClientObj 
{
    
    
    private String ux;
    private String ip;
    private String exip;
    private boolean forceIp;
    private String code;
    private String iv;
    private String key;
    private int tid;
    private String msisdn;
    private String partnercode;
    private String partnerId;
    private int tokenExpiryDays;
    private String emailAddress;

    public HeaderClientObj() {
    }

    public HeaderClientObj(String user, String ip, String exip, boolean forceIp, String code, String px, String rx, int tid, String partnercode, int tokenExpiryMins, String emailAddress, String partnerId) {
        this.ux = user;
        this.ip = ip;
        this.exip = exip;
        this.forceIp = forceIp;
        this.code = code;
        this.key = px;
        this.iv = rx;
        this.tid = tid;
        this.partnercode = partnercode;
        this.tokenExpiryDays = tokenExpiryMins;
        this.emailAddress = emailAddress;
        this.partnerId = partnerId;
    }
    
    
    
 
    public String getUx() {
        return ux;
    }

    public void setUx(String user) {
        this.ux = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isForceIp() {
        return forceIp;
    }

    public void setForceIp(boolean forceIp) {
        this.forceIp = forceIp;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

   
    
    private String toDefault(String value) {
        
        return (value ==null)?"":value.trim();
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getExip() {
        return exip;
    }

    public void setExip(String exip) {
        this.exip = exip;
    }

    public String getPartnercode() {
        return partnercode;
    }

    public void setPartnercode(String partnercode) {
        this.partnercode = partnercode;
    }


    public int getTokenExpiryDays() {
        return tokenExpiryDays;
    }

    public void setTokenExpiryDays(int tokenExpiryDays) {
        this.tokenExpiryDays = tokenExpiryDays;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
    

    public String toObj() {
    
        
      return Json.createObjectBuilder().
                add("tid", this.tid).
                add("ux", toDefault(this.ux)).
                add("ip", toDefault(this.ip)).
                add("exip", toDefault(this.exip)).
                add("forceIp", this.forceIp).
                add("code",toDefault( this.code)).
                add("partnercode", toDefault(this.partnercode)).  
                add("tokenExpiryDays", this.tokenExpiryDays).
                add("iv", toDefault(this.iv)).
                add("partnerId", toDefault(this.partnerId)).
                add("emailAddress", toDefault(this.emailAddress)).
                add("key", toDefault(this.key)).build().toString();
      /*
        .add("tid",obj.getTid())
                .add("ux",toDefault(obj.getClientName()))
                .add("ip", toDefault(obj.getIpAddress()))
                .add("forceIp", obj.getEnforceIp())
                .add("tokenExpiryDays", obj.getTokenLifespanDays())
                .add("px", toDefault(obj.getIv()))
                .add("rx", toDefault(obj.getCKey()))
                .add("code", toDefault(obj.getCode()))
                .add("partnerCode", toDefault(obj.partnerCode))
                .add("partnerId", toDefault(obj.partnerID))*/
      
    }

    @Override
    public String toString() {
        return "HeaderClientObj{" + "ux=" + ux + ", ip=" + ip + ", exip=" + exip + ", forceIp=" + forceIp + ", code=" + code + ", iv=" + iv + ", key=" + key + ", tid=" + tid + ", msisdn=" + msisdn + ", partnercode=" + partnercode + ", partnerId=" + partnerId + ", tokenExpiryDays=" + tokenExpiryDays + ", emailAddress=" + emailAddress + '}';
    }

   
    
   
    
}
