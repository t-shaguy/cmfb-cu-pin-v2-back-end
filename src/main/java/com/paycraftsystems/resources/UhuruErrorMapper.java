/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.resources;

import java.util.HashMap;

/**
 *
 * @author paycraftsystems-i
 */
public class UhuruErrorMapper {
    
    
    public String doErrorDesc(String error) {
        String respDesc = "";
        try 
        {
            
            HashMap<String, String> errorDesc = new  HashMap<String, String>();
            
            errorDesc.put("0", "Successful");
            errorDesc.put("1", "async mode,waiting for the callback");
            errorDesc.put("1000", "system abnormal");
            errorDesc.put("1001", "message check fail");
            errorDesc.put("1002", "MSISDN not-existent");
            errorDesc.put("1003", "Unknown networkid");
            errorDesc.put("1004", "Unknown operation code");
            errorDesc.put("1005", "ServiceID not-existent");
            errorDesc.put("1006", "ProductID not-existent");
            errorDesc.put("1007", "ServiceID suspended");
            errorDesc.put("1008", "SPID not-existent");
            errorDesc.put("1009", "SPID suspended");
            errorDesc.put("1010", "SPID and serviced mismatch");
            errorDesc.put("1011", "Operation not supported");
            errorDesc.put("1012", "Invalid parameter value");
            errorDesc.put("1100", "Message type not supported");
            errorDesc.put("1101", "Access number not configured");
            errorDesc.put("1102", "SMS failed to be delivered");
            errorDesc.put("1103", "USSN failed to be delivered");
            errorDesc.put("1104", "ServiceID not-existent");
            errorDesc.put("1105", "SMS delivery times out");
            errorDesc.put("1106", "USSN times out");
            errorDesc.put("1107", "USSD times out");
            errorDesc.put("1108", "User consent message times out");
            errorDesc.put("1200", "User non-existent");
            errorDesc.put("1201", "User reject the subscription or the deduction");
            errorDesc.put("1202", "On DND");
            errorDesc.put("1300", "User non-existent");
            errorDesc.put("1301", "User already deactivated");
            errorDesc.put("1302", "Package not matched");
            errorDesc.put("1400", "Operation prohibited due to the user status");
            errorDesc.put("1401", "Operation prohibited due to the user flag");
            errorDesc.put("1402", "Settlement time not arrived yet for renewal");
            errorDesc.put("1500", "Invalid deduction amount");
            errorDesc.put("1501", "Insufficient balance");
            errorDesc.put("1502", "Charging fails");
            errorDesc.put("1503", "ChargingID missing");
            errorDesc.put("1504", "ChargingID not configured");
            errorDesc.put("1505", "ChargingID doesn’t belong to this service");
            errorDesc.put("1506", "For sync charging flow, user consent is required");
            errorDesc.put("1507", "Invalid authorization code");
            errorDesc.put("1508", "authorization code not matching the serviceID");
            errorDesc.put("1509", "authorization code expires");
            errorDesc.put("1510", "User consent not yet granted");
            errorDesc.put("1511", "authorization code has been consumed");
            errorDesc.put("1512", "deduction amount doesn’t match the amount authorized");
            errorDesc.put("1513", "Too many user consent requests for the deduction");
            errorDesc.put("1514", "One-off charge is not supported and the user is on DNC");
            errorDesc.put("1600", "The service doesn’t support authorization flow");
            errorDesc.put("1601", "Authorization type not supported");
            errorDesc.put("1602", "Authorization template prohibited to be sent");
            errorDesc.put("1603", "Too many authorization requests");
            errorDesc.put("1604", "Invalid token for query");
            errorDesc.put("1605", "Token for query doesn’t match the serviceID");
            // For MS, 9Mobile:
            errorDesc.put("40000", "OPTIN_ACTIVE_OK");
            errorDesc.put("40001", "OPTIN_ACTIVE_WAIT_CHARGING");
            errorDesc.put("40002", "Too many user consent requests for the deduction");
            errorDesc.put("40003", "OPTIN_CONF_EXPIRED");
            errorDesc.put("40004", "OPTIN_CONF_MISSING_PARAM");
            errorDesc.put("40005", "OPTIN_CONF_SUB_NOT_EXIST");
            errorDesc.put("40006", "OPTIN_CONF_WRONG_PIN");
            errorDesc.put("40007", "OPTIN_CONFIG_NOT_FOUND");
            errorDesc.put("40008", "OPTIN_ERROR");
            errorDesc.put("40009", "OPTIN_MISSING_PARAM");
            errorDesc.put("40010", "OPTIN_NOT_PREACTIVE");
            errorDesc.put("40011", "OPTIN_PREACTIVE_WAIT_CONF");
            errorDesc.put("40012", "OPTIN_ACTIVE_OK");
            errorDesc.put("40013", "OPTOUT_CANCELED_OK");
            errorDesc.put("40014", "OPTOUT_CONFIG_NOT_FOUND");
            errorDesc.put("40015", "OPTOUT_MISSING_PARAM");
            errorDesc.put("40016", "OPTOUT_NO_SUB");
            errorDesc.put("40017", "GET_STATUS_OK");
            errorDesc.put("40018", "GET_STATUS_SUB_NOT_EXIST");
            errorDesc.put("40019", "UNEXPECTED_ERROR");
            errorDesc.put("40020", "INTERNAL_ERROR");
            errorDesc.put("40021", "INVALID_PRODUCT_ID");
            errorDesc.put("40022", "INVALID_MNC");
            errorDesc.put("40023", "INVALID_MCC");
            errorDesc.put("40024", "INVALID_USERIDENTIFIER");
            errorDesc.put("40025", "INVALID_USERIDENTIFIER_TYPE");
            errorDesc.put("40026", "INVALID_CREDENTIALS");
            errorDesc.put("40027", "GET_STATUS_OK");
            errorDesc.put("40028", "INVALID_MSISDN");
            errorDesc.put("40029", "INVALID_PARTNER_ROLE");
            errorDesc.put("40030", "BLACKLISTED_USER");
            errorDesc.put("40032", "Insufficient balance");
            
            if(errorDesc !=null)
            {
                respDesc = errorDesc.get(error)==null?"unknown error: "+error:errorDesc.get(error);
            }
            else
            {
                respDesc = "unknown error";
            }
            
            
        } catch (Exception e) {
        }
        
     return respDesc.replaceAll("_", " ");
    }
    
    
}
