/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;

/**
 *
 * @author root
 */
public class GenericFilterObject {
    
    public long status;
    public String fromDate;
    public String toDate;
    public String searchKey;
    public String searchKeyValue;
    public String transType;
    public double amount;
    public long pid;
    public String userCode;
    public String controlCode;
    public String userRole;
    public String msisdn;
    public int pageId;
    public int pageSize;
    public String phoneNumberPri;
    public String terminalId;
    public String rrn;
    public String srcOrDesAccountNo;
    public String disputeId;
    public long actionby;
    public String actionbyStr;
    public long categoryId;

    @Override
    public String toString() {
        return "GenericFilterObject{" + "status=" + status + ", fromDate=" + fromDate + ", toDate=" + toDate + ", searchKey=" + searchKey + ", searchKeyValue=" + searchKeyValue + ", transType=" + transType + ", amount=" + amount + ", pid=" + pid + ", userCode=" + userCode + ", controlCode=" + controlCode + ", userRole=" + userRole + ", msisdn=" + msisdn + ", pageId=" + pageId + ", pageSize=" + pageSize + ", phoneNumberPri=" + phoneNumberPri + ", terminalId=" + terminalId + ", rrn=" + rrn + ", srcOrDesAccountNo=" + srcOrDesAccountNo + ", disputeId=" + disputeId + ", actionby=" + actionby + ", actionbyStr=" + actionbyStr + ", categoryId=" + categoryId + '}';
    }

   
    
    

}
