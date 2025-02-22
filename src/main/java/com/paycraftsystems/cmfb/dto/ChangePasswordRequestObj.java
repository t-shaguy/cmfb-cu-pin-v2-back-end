/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;

import lombok.ToString;

/**
 *
 * @author taysayshaguy
 */
@ToString
public class ChangePasswordRequestObj
{
    public ChangePasswordRequestObj(ChangePasswordRequest rx)
    {
        
          this.userName= rx.userName();
          this.password= rx.password();
          this.verifyPassword= rx.verifyPassword();
          this.newPassword = rx.newPassword();
            
    }
    
    
    public String userName;
    public String defaultPassword;
    public String password;
    public String verifyPassword;
    public String partnerCode;
    public String newPassword;
    
}
