package com.paycraftsystems.cmfb.dto;

import lombok.ToString;

/**
 * @author Rasaq Sulaimon
 * @date 02/02/2025 23:11
 */

@ToString
public class FilterRequestObj
{
    
       public FilterRequestObj(FilterRequest rx)
       {
           
            this.fromDate = rx.fromDate();
            this.toDate = rx.toDate();
            this.searchParam = rx.searchParam();
            this.status = rx.status();
            this.pageId = rx.pageId();
            this.pageSize = rx.pageSize();
           
       }
    
    
        public String fromDate;
        public String toDate;
        public String searchParam;
        public String status;
        public int pageId;
        public int pageSize;
  
}
