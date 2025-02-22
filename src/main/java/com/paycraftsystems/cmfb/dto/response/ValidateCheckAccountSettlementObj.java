/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto.response;

/**
 *
 * @author paycraftsystems-i
 */
public record ValidateCheckAccountSettlementObj(String accountnumber1, String accamount1, String accountnumber2, String accmount2) {
    
    /*
    "accountsettlement": {
            "accountnumber1": "0520000322",
            "accamount1": -49,
            "accountnumber2": "",
            "accmount2": "50"
        }
    */
    
}
