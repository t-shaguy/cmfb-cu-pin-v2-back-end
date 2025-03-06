package com.paycraftsystems.cmfb.dto;

import lombok.ToString;

/**
 * @author Rasaq Sulaimon
 * @date 02/02/2025 23:11
 */
@ToString
public class ApproveOrDeleteRequestObj
{
    
       public ApproveOrDeleteRequestObj(ApproveOrDeleteRequest rx)
       {
           
           this.tid = rx.tid();
           this.actionBy = rx.actionBy();
           this.actionByStr = rx.actionByStr();
           
       }
        
       
        public long tid;
        public long actionBy;
        public String actionByStr;
}
