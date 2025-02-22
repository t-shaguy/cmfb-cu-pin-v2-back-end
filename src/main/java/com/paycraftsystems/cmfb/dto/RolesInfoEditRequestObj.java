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
public class RolesInfoEditRequestObj
{
    public  RolesInfoEditRequestObj(RolesInfoEditRequest rx) {
        
         this.roleName = rx.roleName();
         this.roleDesc = rx.roleDesc();
         this.actionBy = rx.actionBy();
         this.actionByStr = rx.actionByStr();
         this.tid = rx.tid();
        
    }
    
        
   public long tid;
   public String roleName;
   public String roleDesc;
   public long actionBy;
   public String actionByStr;
    
}
