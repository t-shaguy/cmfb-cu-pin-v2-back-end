/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.dto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author paycraftsystems-i
 */
public class CBARequestObj
{
    public CBARequestObj(CBARequest trx) {
        
        this.transactionReference = trx.transactionReference();
    }
    
    public String doTransHash(String SecretKey)
    {
       return doCBAHash(SecretKey+this.transactionReference);
    }
    
    
    private String doCBAHash(String tohash) {
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
    
    public String transactionReference;
}
