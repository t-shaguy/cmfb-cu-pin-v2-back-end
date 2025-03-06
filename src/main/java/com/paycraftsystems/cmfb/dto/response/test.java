/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto.response;

import com.google.gson.Gson;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author paycraftsystems-i
 */
public class test {
    
    
    
    public static void main(String[] args) {
        //applicationJson
        String key = "Ah@FG4OgBiU;_%%%";
         String ref = "127266366748894999487664";
         String json = """
        {
          "application/json": {
            "Status": "Successful",
            "Response": {
              "Retval": 0,
              "Retmsg": "Successful",
              "DepositorName": "JACK BAUER",
              "Amount": 5000,
              "Narration": "CU Tution Payment - 127266366748894999487664",
              "TransactionDate": "2025-02-10 07:30:02.153",
              "ReferenceID": "127266366748894999487664"
            }
          }
        }""";
         
         
        CBAResponseObj fromJson = new Gson().fromJson(json.replace("application/json", "applicationJson"), CBAResponseObj.class);
        
        System.out.println("fromJson = " + fromJson);
        
        System.out.println("fromJson = " + doCBAHash(key+ref));
        
    }
    
    
    
    public static String doCBAHash(String tohash) {
        String resp = "";
        try {
            // Create a MessageDigest instance for SHA-512
            MessageDigest digest = MessageDigest.getInstance("SHA-512");

            
            // Perform the hash calculation
            byte[] hashBytes = digest.digest(tohash.getBytes());

            // Convert the hash bytes to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));  // Convert each byte to a 2-digit hex
            }
            resp = hexString.toString();
            // Print the resulting SHA-512 hash
            System.out.println("SHA-512 hash: " + hexString.toString());

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: SHA-512 algorithm not found.");
        }
      return resp;
    }
    
}
