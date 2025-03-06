/*



 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.controller;


import com.google.gson.Gson;
import com.paycraftsystems.vanso.dto.SMSAccountObj;
import com.paycraftsystems.vanso.dto.SMSObject;
import com.paycraftsystems.vanso.dto.SMSRequest;
import com.paycraftsystems.vanso.dto.SMSRequestObj;
import com.paycraftsystems.vanso.dto.VansoResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.bind.JsonbBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author root
 */
@Slf4j
public class SMSProviderConnector //implements Runnable
{
    
    
    //SQLHelper sqlHelper_ = null;
    SMSObject SMSObject = null;
    SMSRequest smsRequest = null;
    SMSAccountObj smsaccount = null;
    String vansoUrl = "";
   // String username = "";
   // String password = "";

public SMSProviderConnector(SMSRequest sms, String vansoUrl,SMSAccountObj account) {

  this.smsRequest = sms;
  this.smsaccount = account;
  //this.SMSObject = sMSObject;
  this.vansoUrl = vansoUrl;
  //this.username = username;
  //this.password = password;

}



    public VansoResponse doSubmitMessageV2() {
        String response = "";
        VansoResponse fromJson =  null;
        try 
        {
            // Define the URL
            URL sendUrl = new URL(vansoUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) sendUrl.openConnection();
            
            // Set request properties
            httpConnection.setRequestMethod("POST"); // or "PUT"
            httpConnection.setDoOutput(true); // Enable sending data
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setRequestProperty("Accept", "application/json");
            
          //  smsRequest;
           
            JsonObjectBuilder smsJob = Json.createObjectBuilder();
            
            SMSRequestObj smsRequestObj = new SMSRequestObj(smsRequest, smsaccount);
            
            log.info("--smsRequestObj --  "+smsRequestObj);
            
            String toJson = new Gson().toJson(smsRequestObj);
            
            log.info("-- toJson -- "+toJson);
            
           // smsJob.add("sms", Json.createObjectBuilder().add("dest", this.SMSObject.getDestination()!=null?this.SMSObject.getDestination().trim():this.SMSObject.getDestination()).add("referenceId", ""+this.SMSObject.getTid()).add("src", this.SMSObject.getSource()).add("text", this.SMSObject.getMessage()).add("unicode", false));
            //smsJob.add("account", Json.createObjectBuilder().add("password", password !=null? password.trim():password).add("systemId", username !=null?username.trim():username));
            
            //JsonObject sms = smsJob.build();
           
            System.out.println("sms request = " + toJson);
            // Write JSON to request body
            try (OutputStream os = httpConnection.getOutputStream();
                 OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
                osw.write(toJson);
                osw.flush();
            }
            
          int responseCode = httpConnection.getResponseCode();
           System.out.println("Response Code: " + responseCode);
           
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            responseCode >= 200 && responseCode < 300 ? 
                            httpConnection.getInputStream() : httpConnection.getErrorStream(), 
                            StandardCharsets.UTF_8))) {
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                response = responseBuilder.toString();
                
                System.out.println(" doSubmitMessageV2 response = " + response);
                
                fromJson = JsonbBuilder.create().fromJson(response, VansoResponse.class);
            }
            
            System.out.println(" : fromJson "+fromJson);
            
            httpConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            
            response = "Error-"+e.getMessage();
        }
      return  fromJson;
    }
    


private static StringBuffer convertToUnicode(String regText) {

char[] chars = regText.toCharArray();

StringBuffer hexString = new StringBuffer();
for (int i = 0; i < chars.length; i++) {

String iniHexString = Integer.toHexString((int) chars[i]);

if (iniHexString.length() == 1)
iniHexString = "000" + iniHexString;
else if (iniHexString.length() == 2)
iniHexString = "00" + iniHexString;

else if (iniHexString.length() == 3)

iniHexString = "0" + iniHexString;

hexString.append(iniHexString);
}

System.out.println(hexString);
return hexString;
}


    /*
    @Override
    public void run() 
    {
        try 
        {
            String submitMessage = submitMessage();
            
            logger.info("-- logger --"+submitMessage);
            
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        
        }
        
    }
    */
}
