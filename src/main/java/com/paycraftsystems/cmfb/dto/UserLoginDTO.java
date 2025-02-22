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
public class UserLoginDTO {
    
    
    public String userName;
    public String password;
    public String partnerCode;
    @Override
    public String toString() {
        return "UserLoginDTO{" + "userName=" + userName + ", password=" + password + ", partnerCode=" + partnerCode + '}';
    }

}
