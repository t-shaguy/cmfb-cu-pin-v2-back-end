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
public class FilterSysDataRequestObj{
    
    
    
    public FilterSysDataRequestObj(FilterSysDataRequest rx) {
    
           this.searchParam = rx.searchParam();
           this.fromDate = rx.fromDate();
           this.toDate = rx.toDate();
           this.status = rx.status();
           this.pageIndex = rx.pageIndex();
           this.pageSize = rx.pageSize();
    }
    
    
    public String fromDate;
    public String toDate;
    public String status;
    public int pageIndex;
    public int pageSize;
    public String searchParam;
    
}
