/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.acme;

import com.paycraftsystems.cmfb.controller.BCrypt;
import java.time.LocalDate;

/**
 *
 * @author paycraftsystems-i
 */
public class NewClass {
        
        
   
    public static void main(String[] args) {
        
         String password = "";
        
         String CU_PASSWORD = "yxVO7Fcb";
    
         String CU_USER_TEST = "CovenantxNYL";
    
         String CU_PASSWORD_TEST = "eNp4z3F8";
        
         try 
         {
            
             
             
             System.out.println("CU_PASSWORD_TEST = " + LocalDate.now().toString());
              
            //BCrypt.hashpw(cuPayObject.getPword(), BCrypt.gensalt(12)))
            
            String hashpw = BCrypt.hashpw(CU_PASSWORD, BCrypt.gensalt(12));
            
            System.out.println("hashpw = " + hashpw);
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }
    
        
    
}
