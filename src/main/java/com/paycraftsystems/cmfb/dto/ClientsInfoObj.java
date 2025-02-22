/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.dto;


import com.paycraftsystems.resources.ValidationHelper;
import java.io.Serializable;
import jakarta.json.Json;
import jakarta.json.JsonObject;

/**
 *
 * @author root
 */

public class ClientsInfoObj  implements Serializable {
    

    private static final long serialVersionUID = 1L;
    public Long tid;
    public String ipAddress;
    public boolean enforceIp;
    public String code;
    public String iv;
    public String cKey;
    public String clientName;
    public int tokenLifespanDays;
    public String partnerID;
    public String customerCode;
    public int clientCategory;
    public String feeCode;
    public String clientType;

    public ClientsInfoObj() {
    }

    
    
    public static String toJson(ClientsInfoObj  obj)
    {
        ValidationHelper rh = new ValidationHelper();
        return Json.createObjectBuilder()
                .add("tid",obj.tid)
                .add("clientCategory",obj.clientCategory)
                .add("ux",rh.toDefault(obj.clientName))
                .add("ip", rh.toDefault(obj.ipAddress))
                .add("enforceIp", obj.enforceIp)
                .add("tokenLifespanDays", obj.tokenLifespanDays)
                .add("iv", "MY SECRET")//rh.toDefault(obj.iv)
                .add("key", "OUR SECRET")// toDefault(obj.getCKey()))
                .add("code", rh.toDefault(obj.code))
                .add("partnerCode", rh.toDefault(obj.customerCode))
                .add("partnerId", rh.toDefault(obj.partnerID))
                
                
       .build().toString();
       
    }
    
     public static JsonObject toJsonObj(ClientsInfoObj  obj)
    {
        ValidationHelper rh = new ValidationHelper();
        return Json.createObjectBuilder()
                .add("tid",obj.tid)
                .add("clientCategory",obj.clientCategory)
                .add("ux",rh.toDefault(obj.clientName))
                .add("ip", rh.toDefault(obj.ipAddress))
                .add("enforceIp", obj.enforceIp)
                .add("tokenLifespanDays", obj.tokenLifespanDays)
                .add("iv", "MY SECRET")//rh.toDefault(obj.iv)
                .add("key", "OUR SECRET")// toDefault(obj.getCKey()))
                .add("code", rh.toDefault(obj.code))
                .add("partnerCode", rh.toDefault(obj.customerCode))
                .add("partnerId", rh.toDefault(obj.partnerID))
                
                
       .build();
       
    }
    
     public String toJson()
    {
        
        ValidationHelper rh = new ValidationHelper();
        return Json.createObjectBuilder()
                .add("tid",this.tid)
                .add("clientCategory",this.clientCategory)
                .add("ux", rh.toDefault(this.clientName))
                .add("ip", rh.toDefault(this.ipAddress))
                .add("enforceIp", this.enforceIp)
                .add("tokenLifespanDays", this.tokenLifespanDays)
                .add("iv", rh.toDefault(this.iv))
                .add("key", rh.toDefault(this.cKey))
                .add("code", rh.toDefault(this.code))
                .add("partnerCode", rh.toDefault(this.customerCode))
                .add("partnerId", rh.toDefault(this.partnerID))
                
       .build().toString();
       
    }
     
     public JsonObject toJsonObj()
    {
        
        ValidationHelper rh = new ValidationHelper();
        return Json.createObjectBuilder()
                //.add("tid",this.tid)
                .add("clientCategory",this.clientCategory)
                .add("clientName", rh.toDefault(this.clientName))
                .add("ipAddress", rh.toDefault(this.ipAddress))
                .add("enforceIp", this.enforceIp)
                .add("tokenLifespanDays", this.tokenLifespanDays)
                .add("iv", rh.toDefault(this.iv))
                .add("key", rh.toDefault(this.cKey))
                .add("code", rh.toDefault(this.code))
                .add("partnerCode", rh.toDefault(this.customerCode))
                .add("partnerID", rh.toDefault(this.partnerID))
                
       .build();
       
    }

    @Override
    public String toString() {
        return "ClientsInfoObj{" + "tid=" + tid + ", ipAddress=" + ipAddress + ", enforceIp=" + enforceIp + ", code=" + code + ", iv=" + iv + ", cKey=" + cKey + ", clientName=" + clientName + ", tokenLifespanDays=" + tokenLifespanDays + ", partnerID=" + partnerID + ", customerCode=" + customerCode + ", clientCategory=" + clientCategory + '}';
    }

   

}
