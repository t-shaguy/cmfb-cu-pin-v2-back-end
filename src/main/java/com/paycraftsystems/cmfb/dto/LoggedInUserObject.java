/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

/**
 *
 * @author paycraftsystems-i
 */
public class LoggedInUserObject {
    
    public long tid;
    public long pid;
    public String code;
    public String userCode;
    public String  msisdn;
    public String  codeLink;
    //vHash1":"5D23178764735BA90674C6C1B621629746F89C0E2F87C3339CB1F16AB385438092BF1AD12AA19E56A462D6472CD9A76FCD3D4ABF0C78531DEE5910EC4ED0688F","vHash":"77b01a94f6147ca8949b12fc20f17cc7371efb0f6215441569850b021a70f6c62b74fa3a8e628b2189c213e2a6eb8466","
    public String syncDate;
    public String controlCode;
    public String lastLoginDate;
    public String modifiedDate;

    @Override
    public String toString() {
        return "LoggedInUserObject{" + "tid=" + tid + ", pid=" + pid + ", code=" + code + ", userCode=" + userCode + ", msisdn=" + msisdn + ", codeLink=" + codeLink + ", syncDate=" + syncDate + ", controlCode=" + controlCode + ", lastLoginDate=" + lastLoginDate + ", modifiedDate=" + modifiedDate + '}';
    }

    
}
