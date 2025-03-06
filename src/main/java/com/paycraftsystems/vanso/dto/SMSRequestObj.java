/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.vanso.dto;

import lombok.ToString;

/**
 *
 * @author paycraftsystems-i
 */

@ToString
public class SMSRequestObj {
    
    public SMSRequestObj(SMSRequest sms, SMSAccountObj account)
    {
        this.account = account;
        this.sms = sms;
        
    }
    // smsJob.add("sms", Json.createObjectBuilder().add("dest", this.SMSObject.getDestination()!=null?this.SMSObject.getDestination().trim():this.SMSObject.getDestination()).add("referenceId", ""+this.SMSObject.getTid()).add("src", this.SMSObject.getSource()).add("text", this.SMSObject.getMessage()).add("unicode", false));
     //       smsJob.add("account", Json.createObjectBuilder().add("password", password !=null? password.trim():password).add("systemId", username !=null?username.trim():username));
            
    //smsJob.add("sms", Json.createObjectBuilder().add("dest", this.SMSObject.getDestination()!=null?this.SMSObject.getDestination().trim():this.SMSObject.getDestination()).add("referenceId", ""+this.SMSObject.getTid()).add("src", this.SMSObject.getSource()).add("text", this.SMSObject.getMessage()).add("unicode", false));
 
    public SMSRequest sms;
    public SMSAccountObj account;
}
