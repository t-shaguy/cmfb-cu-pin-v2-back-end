/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.resources;

//import com.paycraftsystems.cmfb.controller.NotificationProcessor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author taysayshaguy
 */
@Slf4j
public class CUResponse 
{
    
     //private static Logger LOGGER = LoggerFactory.getLogger(CUResponse.class);
   

     public  CUResponse()
     {
     
     }

     public  static CUResponseObj doResp(String respStr) 
     {
         log.info("doResp respStr = " +  respStr);
         CUResponseObj cu =  null;//new CUResponseObj();
         try 
         {
             
               if(respStr == null || respStr.trim().equals("") || respStr.trim().length() ==1)
               {
                   cu = new CUResponseObj("NA","NA","-1",respStr);
               }
               else if(respStr != null && respStr.trim().contains(":"))
               {
                   String[] params = respStr.trim().split(":");
                   
                    if(params !=null && params.length == 6)
                    {
                   
                        cu = new CUResponseObj(params[1],params[3],params[5], respStr);
                        
                        //CUResponseObj(@NotNull String pin,@NotNull String receiptNo,@NotNull String status,@NotNull String fullResp)
                    }
                    else
                    {
                        cu = new CUResponseObj("NA","NA",params[1],respStr);
                    }
                   
               }
               
         } 
         catch (Exception e) {
         
            e.printStackTrace();
         }
     return cu;
     }
    
     
     
     
     public static void main(String[] args) 
     {
          String rx = "pin:9995240694995470742880901:Receipt no:9990785576664:Success:0";
          
          String xz = "3";
          
          
         CUResponseObj doResp = doResp(rx);
          
          log.info(" : "+doResp);//xz.trim().length());
          
          
     }
     
     
  public static String narrateCUResponse(String resp)
  {
    String narration = "";

    if ((resp == null) || (resp.equals(""))) resp = "-1";

      switch (resp)
      {
      case "0":
        narration = "Successful Payment";
        break;
      case "1":
        narration = "Payment already exists";
        break;
      case "2":
        narration = "Transaction Failed";
        break;
      case "3":
        narration = "Invalid Matriculation Number";
        break;
      case "4":
        narration = "Transaction Failed";
        break;
      case "5":
        narration = "Internal Error";
        break;
      case "6":
        narration = "Invalid Username or Password";
        break;
      case "7":
        narration = "Invalid Bank Code";
        break;
      case "8":
        narration = "Invalid Amount";
        break;
      case "9":
        narration = "In-Active Collection";
        break;
      case "10":
        narration = "Format Error";
        break;
      case "11":
        narration = "Error: Sending request to Partner";
        break;
      case "-1":
        narration = "Invalid Parameters";
      default:
        narration = "Error : "+resp; break;
      }


    return narration;
  }
  
  
  public static String cuPaymentCategory(int postId)
  {
    String narration = "";

   
      switch (postId)
      {
      case 15380:
        narration = "School Fee";
        break;
      case 15351:
        narration = "Undergraduate Form";
        break;
      case 15443:
        narration = "Postgraduate Form";
        break;
      case 15448:
        narration = "PG School Fee"; //Post 
        break;
      default:
        narration = "Unknown"; 
        break;
      }
    
    return narration;
  }

}
