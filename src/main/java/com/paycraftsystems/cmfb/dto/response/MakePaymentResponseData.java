/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto.response;

/**
 *
 * @author paycraftsystems-i
 */
public record MakePaymentResponseData(String success, String receiptno, String pinno, String amount, String payername, String payerid, String paymenttype, String tenant ) {
    
    
    /*
    {
"payparameter": {
"success":"1",
"receiptno":"777018679093",
"pinno":"77701610704768965305",
"amount":"150000",
"payername":"",
"payerid":"A018211518",
"paymenttype":"1",
"tenant":null
} }
    */
    
}
