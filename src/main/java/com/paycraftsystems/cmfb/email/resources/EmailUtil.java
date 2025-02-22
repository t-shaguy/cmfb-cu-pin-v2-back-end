/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.email.resources;

/**
 *
 * @author paycraftsystems-i
 */


import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailUtil {
    
  private static  Logger LOGGER =  LoggerFactory.getLogger(EmailUtil.class);
  
    /*
     public static boolean doSendMail(String apiKey, String srcEmail, String senderName,  String receiverEmail, String receiverName, String emailContent, String emailSubject)
     {
        LOGGER.info(" -+- apiKey  -- "+apiKey+"   -+- srcEmail -- "+srcEmail+"   --- senderName -- "+senderName+"  -- receiverEmail -- "+receiverEmail+" -emailContent +  emailSubject -- "+emailSubject);
        PepipostClient client = new PepipostClient();
         boolean resp = false;
         try 
         {
             
        
                    MailSendController mailSendController = client.getMailSend();
                    Configuration.apiKey = apiKey;//  "673a6ddada05b69400c48e36e1e404a9";
                    Send body = new Send();

                    body.setFrom(new From());
                    body.getFrom().setEmail(srcEmail);//"hello@mail.bizzdeskgroup.com");
                    body.getFrom().setName(senderName);//"PaySureDigi");
                    body.setSubject(emailSubject);
                    body.setContent(new LinkedList<Content>());

                    Content body_content_0 = new Content();
                    body_content_0.setType(TypeEnum.HTML);
                    body_content_0.setValue(emailContent);
                    body.getContent().add(body_content_0);

                    body.setPersonalizations(new LinkedList<Personalizations>());

                    Personalizations body_personalizations_0 = new Personalizations();
                    body_personalizations_0.setTo(new LinkedList<EmailStruct>());

                    EmailStruct body_personalizations_0_to_0 = new EmailStruct();
                    body_personalizations_0_to_0.setName(receiverName);
                    body_personalizations_0_to_0.setEmail(receiverEmail);
                    body_personalizations_0.getTo().add(body_personalizations_0_to_0);

                    body.getPersonalizations().add(body_personalizations_0);

                    mailSendController.createGeneratethemailsendrequestAsync(body, new APICallBack<Object>() {
                        public void onSuccess(HttpContext context, Object response) {

                            System.out.println("-- success response -- "+response.toString());
                            System.out.println("-- success response status-- "+context.getResponse().getStatusCode());
                            

                        }
                        public void onFailure(HttpContext context, Throwable error) {

                            System.out.println("-- failed response -- "+error.getMessage());
                            System.out.println("-- failed response status -- "+context.getResponse().getStatusCode());


                        }
                    });
                    
                resp = true;
         } 
         catch (Exception e) {
         
               e.printStackTrace();
         }
         
     return resp;
    }
     
   */
}
