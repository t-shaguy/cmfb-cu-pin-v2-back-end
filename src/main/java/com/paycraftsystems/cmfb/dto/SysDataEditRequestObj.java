/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;

import lombok.ToString;

/**
 *
 * @author root
 */

@ToString
public class SysDataEditRequestObj
{

    public SysDataEditRequestObj(SysDataEditRequest rx )
    {
         this.tid = rx.tid();
         this.paramName = rx.paramName();
         this.paramValue = rx.paramValue();
         this.actionBy = rx.actionBy();
         this.actionByStr = rx.actionByStr();

    }


    public Long tid;
    public String paramName;
    public String paramValue;
    public long actionBy;
    public String actionByStr;
    
}
