/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;

/**
 *
 * @author taysayshaguy
 */
public class ChangePasswordLoginDTO {
    
    
    private String userName;
    private String defaultPassword;
    private String password;
    private String verifyPassword;
    private String partnerCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    @Override
    public String toString() {
        return "ChangePasswordLoginDTO{" + "userName=" + userName + ", defaultPassword=" + defaultPassword + ", password=" + password + ", verifyPassword=" + verifyPassword + ", partnerCode=" + partnerCode + '}';
    }

}
