/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.email.resources;



//import com.paycraftsystems.entities.NotificationsFeed;
import com.paycraftsystems.cmfb.controller.SysDataController;
import com.paycraftsystems.cmfb.dto.MailObj;
import com.paycraftsystems.cmfb.dto.NotificationRequest;
import com.paycraftsystems.cmfb.entities.NotificationsFeed;
import io.quarkus.mailer.Mail;
import java.util.concurrent.CompletionStage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.quarkus.mailer.Mailer;

import io.quarkus.mailer.reactive.ReactiveMailer;
import io.smallrye.reactive.messaging.annotations.Blocking;
import java.text.DecimalFormat;
import jakarta.annotation.PostConstruct;
import static java.lang.Math.log;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//io/quarkus/quarkus-mutiny-reactive-streams-operators/3.2.0.Final/quarkus-mutiny-reactive-streams-operators-3.2.0.Final.jar
/**
 *
 * @author root
 */

@ApplicationScoped
@Slf4j
public class MailProcessor {
    
    
    
    @Inject
    SysDataController sysDataHelper;
    
   
    @PostConstruct
    public void doInit() {
        
    }
    
    
    @Inject
    Mailer mailer;

    @Inject
    ReactiveMailer reactiveMailer;
    
    DecimalFormat decimalFormat = new DecimalFormat("#,000.00");
  
    
     public CompletionStage<Boolean> sendWelcomeEmailAsync(NotificationsFeed  feedObj) {
        log.info(" *** called async mailer sendWelcomeEmailAsync...... ");
        
         try 
         {
               MailObj mail = new MailObj();

                
                mail.addressee = feedObj.addressee;
                mail.toShare = feedObj.toShare;
                mail.toShareLabel = feedObj.toShareLabel;
                mail.mailTo = feedObj.receipient;
                mail.subject = feedObj.subject;
                mail.US = sysDataHelper.getProps("SHORT-NAME", "NA");
                mail.mailTitle = sysDataHelper.getProps("TITLE", "NA");
                mail.supportInfo = sysDataHelper.getProps("SUPPORT-TEAM", "NA");
                mail.banner = sysDataHelper.getProps("WELCOME-BANNER", "NA");
                mail.ourUrl = sysDataHelper.getProps("WWW", "NA");
                mail.supportLines = sysDataHelper.getProps("SUPPORT-LINES", "NA");
                mail.tableBGColor = sysDataHelper.getProps("MAIL-TABLE-BG-COLOR", "NA");
                mail.tableHDColor = sysDataHelper.getProps("MAIL-TABLE-HD-COLOR", "NA");
                mail.tableBorderColor = sysDataHelper.getProps("MAIL-TABLE-BORDER-COLOR", "NA");
               
        return reactiveMailer.send(
               
                Mail.withHtml(mail.mailTo, mail.subject, new MailTemplates().doWelcomeMail(mail)))
                .subscribeAsCompletionStage().thenApply(x -> true);
        
           /*
         return reactiveMailer.send(
               
                Mail.withHtml(mail.mailTo, mail.subject, new MailTemplates().doWelcomeMail(mail)),
                Mail.withHtml(mail.cc,mail.subject, new MailTemplates().doWelcomeMail(mail)))
                .subscribeAsCompletionStage().thenApply(x -> true);
        */
             
         } 
         catch (Exception e) {
        
             log.error(" $ Exception in sendMailAsync ",e);
             
             
             return null;
          
         
         }
          
    }
     
     
     @Incoming("payment-notifications")
    @Blocking
    public  boolean doPaymentNotificationMail(String payload) {
        log.info(" doOTPMail-- otp-- doPaymentNotificationMail "+payload);
        //MailResponse doEmail = null;
        String addressee; List<String> email= new ArrayList<>(); String otp;
        try 
        {
                 
            String[] split = payload.split("#");
            email.add(split[1]);
            // EmailNotificationRequest emailNotificationRequest = new EmailNotificationRequest(sysDataRepo.doLookUpByNameStr("OTP-SIGNUP", ""), "", doOTPMailHtmlStr, email.toArray(new String[0]), true, "EMAIL", sysDataRepo.doLookUpByNameStr("MAIL-PROVIDER", ""));
           
           // EmailNotificationRequest emailNotificationRequest = new EmailNotificationRequest(split[3], "", doOTPMailHtmlStr, email.toArray(new String[0]), true, "EMAIL", split[4]);
           // log.info("== emailNotificationRequest == "+emailNotificationRequest);
           // doEmail = hoptoolNotificationService.doEmail(emailNotificationRequest);
            
           // log.info("--doOTPMail "+doEmail);
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        
        
       return  false;//(doEmail !=null && doEmail.statusCode().equals("00"))?true:false;
    }
     
    /*
     public CompletionStage<Boolean> sendWelcomeEmailAsync(String mailFrom, String mailTo, String message, String subject, String addresse, String accountNo) {
        System.out.println(" *** called async mailer sendWelcomeEmailAsync...... ");
        
         try 
         {
               MailObj mail = new MailObj();

                mail.addressee = addresse;
                mail.toShare = mailTo;
                mail.accountNo = accountNo;
                mail.message = message;
                //mail.setReceiptNo(CUResponseObj !=null?CUResponseObj.getReceiptNo():"NA");
               // mail.setReceipient(payeeInfo.getFullname());
                mail.cc = 
                mail.subject = subject;
        
        return reactiveMailer.send(
               
                Mail.withHtml(mail.mailTo, mail.subject, new MailTemplates().doWelcomeMail(mail)),
                Mail.withHtml(mail.cc,mail.subject, new MailTemplates().doWelcomeMail(mail)))
                .subscribeAsCompletionStage().thenApply(x -> true);
             
         } 
         catch (Exception e) {
        
             log.error(" $ Exception in sendMailAsync ",e);
             
             
             return null;
          
         
         }
          
    }
     */
     
    public CompletionStage<Boolean> sendOTPEmailAsync(NotificationsFeed  feedObj) {
        log.info(" *** called async mailer sendOTPEmailAsync...... ");
        
         try 
         {
                MailObj mail = new MailObj();

                mail.addressee = feedObj.addressee;
                mail.toShare = feedObj.toShare;
                mail.mailTo = feedObj.receipient;
                mail.subject = feedObj.subject;
                mail.US = sysDataHelper.getProps("SHORT-NAME", "NA");
                mail.mailTitle = sysDataHelper.getProps("AUTH-OTP-TITLE", "NA");
                mail.supportInfo = sysDataHelper.getProps("SUPPORT-TEAM", "NA");
                mail.banner = sysDataHelper.getProps("OTP-BANNER", "NA");
                mail.ourUrl = sysDataHelper.getProps("WWW", "NA");
                mail.supportLines = sysDataHelper.getProps("SUPPORT-LINES", "NA");
                mail.otpExpiration = sysDataHelper.getProps("OTP-EXPIRATION", "NA");
                mail.tableBGColor = sysDataHelper.getProps("MAIL-TABLE-BG-COLOR", "NA");
                mail.tableHDColor = sysDataHelper.getProps("MAIL-TABLE-HD-COLOR", "NA");
                mail.tableBorderColor = sysDataHelper.getProps("MAIL-TABLE-BORDER-COLOR", "NA");
                
                        
                log.info(" ##-sendOTPEmailAsync- MailObj -- "+mail);
        
        return reactiveMailer.send(
               
                Mail.withHtml(mail.mailTo, mail.subject, new MailTemplates().doOTPMailV2(mail)),
                Mail.withHtml(mail.cc,mail.subject, new MailTemplates().doOTPMail(mail)))
                .subscribeAsCompletionStage().thenApply(x -> true);
        
            /*
            return reactiveMailer.send(

                   Mail.withHtml(mail.mailTo, mail.subject, new MailTemplates().doOTPMail(mail)),
                   Mail.withHtml(mail.cc,mail.subject, new MailTemplates().doOTPMail(mail)))
                   .subscribeAsCompletionStage().thenApply(x -> true);
           */
             
         } 
         catch (Exception e) {
             
            // e.printStackTrace();
        
             log.error(" $ Exception in sendMailAsync ",e);
             
             
             return null;
          
         
         }
          
    }
    
    public CompletionStage<Boolean> sendResetEmailAsync(NotificationsFeed  feedObj) {
        log.info(" *** called async mailer sendResetEmailAsync...... ");
        
         try 
         {
                MailObj mail = new MailObj();
                mail.toShare1 = feedObj.toShare1;
                mail.toShare2 = feedObj.toShare2;
                mail.addressee = feedObj.addressee;
                mail.toShare = feedObj.toShare;
                mail.mailTo = feedObj.receipient;
                mail.subject = feedObj.subject;
                mail.US = sysDataHelper.getProps("SHORT-NAME", "NA");
                mail.mailTitle = sysDataHelper.getProps("RESET-TITLE", "NA");
                mail.supportInfo = sysDataHelper.getProps("SUPPORT-TEAM", "NA");
                mail.banner = sysDataHelper.getProps("OTP-BANNER", "NA");
                mail.ourUrl = sysDataHelper.getProps("WWW", "NA");
                mail.supportLines = sysDataHelper.getProps("SUPPORT-LINES", "NA");
                mail.otpExpiration = sysDataHelper.getProps("OTP-EXPIRATION", "NA");
                mail.tableBGColor = sysDataHelper.getProps("MAIL-TABLE-BG-COLOR", "NA");
                mail.tableHDColor = sysDataHelper.getProps("MAIL-TABLE-HD-COLOR", "NA");
                mail.tableBorderColor = sysDataHelper.getProps("MAIL-TABLE-BORDER-COLOR", "NA");
                
                        
                log.info(" ##--sendResetEmailAsync MailObj -- "+mail);
        
        return reactiveMailer.send(
               
                Mail.withHtml(mail.mailTo, mail.subject, new MailTemplates().doResetMailV2(mail)),
                Mail.withHtml(mail.cc,mail.subject, new MailTemplates().doResetMailV2(mail)))
                .subscribeAsCompletionStage().thenApply(x -> true);
        
        
        
         } 
         catch (Exception e) {
             
            // e.printStackTrace();
        
             log.error(" $ Exception in sendResetEmailAsync ",e);
             
             
             return null;
          
         
         }
          
    }
    
    public CompletionStage<Boolean> sendCustomerWelcomeEmailAsync(NotificationsFeed  feedObj) {
        log.info(" *** called async mailer sendCustomerWelcomeEmailAsync...... ");
        
         try 
         {
                MailObj mail = new MailObj();
                mail.toShare1 = feedObj.toShare1;
                mail.toShare2 = feedObj.toShare2;
                mail.addressee = feedObj.addressee;
                mail.toShare = feedObj.toShare;
                mail.mailTo = feedObj.receipient;
                mail.subject = feedObj.subject;
                mail.message = feedObj.message;
                mail.US = sysDataHelper.getProps("SHORT-NAME", "NA");
                mail.mailTitle = sysDataHelper.getProps("WELCOME-TITLE", "NA");
                mail.supportInfo = sysDataHelper.getProps("SUPPORT-TEAM", "NA");
                mail.banner = sysDataHelper.getProps("OTP-BANNER", "NA");
                mail.ourUrl = sysDataHelper.getProps("WWW", "NA");
                mail.supportLines = sysDataHelper.getProps("SUPPORT-LINES", "NA");
                mail.otpExpiration = sysDataHelper.getProps("OTP-EXPIRATION", "NA");
                mail.tableBGColor = sysDataHelper.getProps("MAIL-TABLE-BG-COLOR", "NA");
                mail.tableHDColor = sysDataHelper.getProps("MAIL-TABLE-HD-COLOR", "NA");
                mail.tableBorderColor = sysDataHelper.getProps("MAIL-TABLE-BORDER-COLOR", "NA");
                    
                log.info(" ##-- sendCustomerWelcomeEmailAsync MailObj -- "+mail);
        
        return reactiveMailer.send(
               
                Mail.withHtml(mail.mailTo, mail.subject, new MailTemplates().doCustomerWelcomeMailV2(mail)),
                Mail.withHtml(mail.cc,mail.subject, new MailTemplates().doCustomerWelcomeMailV2(mail)))
                .subscribeAsCompletionStage().thenApply(x -> true);
        
        
        
         } 
         catch (Exception e) {
             
            // e.printStackTrace();
        
             log.error(" $ Exception in sendCustomerWelcomeEmailAsync ",e);
             
             
             return null;
          
         
         }
          
    }
    
    public CompletionStage<Boolean> sendAdminWelcomeEmailAsync(NotificationRequest  feedObj) {
        log.info(" *** called async mailer sendAdminWelcomeEmailAsync...... ");
        
         try 
         {
                MailObj mail = new MailObj();
                mail.toShare1 = feedObj.toShare1;
                mail.toShare2 = feedObj.toShare2;
                mail.addressee = feedObj.addressee;
                mail.toShare = feedObj.toShare;
                mail.mailTo = feedObj.sendTo;//.receipient;
                mail.subject = feedObj.subject;
                mail.message = feedObj.message; 
                mail.US = sysDataHelper.getProps("SHORT-NAME", "NA");
                mail.mailTitle = sysDataHelper.getProps("WELCOME-TITLE", "NA");
                mail.supportInfo = sysDataHelper.getProps("SUPPORT-TEAM", "NA");
                mail.banner = sysDataHelper.getProps("OTP-BANNER", "NA");
                mail.ourUrl = sysDataHelper.getProps("WWW", "NA");
                mail.supportLines = sysDataHelper.getProps("SUPPORT-LINES", "NA");
                mail.otpExpiration = sysDataHelper.getProps("OTP-EXPIRATION", "NA");
                mail.tableBGColor = sysDataHelper.getProps("MAIL-TABLE-BG-COLOR", "NA");
                mail.tableHDColor = sysDataHelper.getProps("MAIL-TABLE-HD-COLOR", "NA");
                mail.tableBorderColor = sysDataHelper.getProps("MAIL-TABLE-BORDER-COLOR", "NA");
                    
                log.info(" ##-- sendCustomerWelcomeEmailAsync MailObj -- "+mail);
        
        return reactiveMailer.send( Mail.withHtml(mail.mailTo, mail.subject, new MailTemplates().doAdminWelcomeMail(mail)),
                Mail.withHtml(mail.cc,mail.subject, new MailTemplates().doAdminWelcomeMail(mail)))
                .subscribeAsCompletionStage().thenApply(x -> true);
        
        
        
         } 
         catch (Exception e) {
           
             log.error(" $ Exception in sendCustomerWelcomeEmailAsync ",e);
             return null;
         }
          
    }
    
    public CompletionStage<Boolean> sendOTPEmailAsync(NotificationRequest  feedObj) {
        log.info(" *** called async mailer sendOTPEmailAsync...... ");
        
         try 
         {
                MailObj mail = new MailObj();
                mail.toShare1 = feedObj.toShare1;
                mail.toShare2 = feedObj.toShare2;
                mail.addressee = feedObj.addressee;
                mail.toShare = feedObj.toShare;
                mail.mailTo = feedObj.sendTo;//.receipient;
                mail.subject = feedObj.subject;
                mail.message = feedObj.message; 
                mail.US = sysDataHelper.getProps("SHORT-NAME", "NA");
                mail.mailTitle = sysDataHelper.getProps("WELCOME-TITLE", "NA");
                mail.supportInfo = sysDataHelper.getProps("SUPPORT-TEAM", "NA");
                mail.banner = sysDataHelper.getProps("OTP-BANNER", "NA");
                mail.ourUrl = sysDataHelper.getProps("WWW", "NA");
                mail.supportLines = sysDataHelper.getProps("SUPPORT-LINES", "NA");
                mail.otpExpiration = sysDataHelper.getProps("OTP-EXPIRATION", "NA");
                mail.tableBGColor = sysDataHelper.getProps("MAIL-TABLE-BG-COLOR", "NA");
                mail.tableHDColor = sysDataHelper.getProps("MAIL-TABLE-HD-COLOR", "NA");
                mail.tableBorderColor = sysDataHelper.getProps("MAIL-TABLE-BORDER-COLOR", "NA");
                    
                log.info(" ##-- sendCustomerWelcomeEmailAsync MailObj -- "+mail);
        
        return reactiveMailer.send(
               
                Mail.withHtml(mail.mailTo, mail.subject, new MailTemplates().doOTPMail(mail)),
                Mail.withHtml(mail.cc,mail.subject, new MailTemplates().doAdminWelcomeMail(mail)))
                .subscribeAsCompletionStage().thenApply(x -> true);
        
         } 
         catch (Exception e) {
           
             log.error(" $ Exception in sendCustomerWelcomeEmailAsync ",e);
             return null;
         }
          
    }
    
    public CompletionStage<Boolean> sendAdminWelcomeEmailAsync(NotificationsFeed  feedObj) {
        log.info(" *** called async mailer sendAdminWelcomeEmailAsync...... ");
        
         try 
         {
                MailObj mail = new MailObj();
                mail.toShare1 = feedObj.toShare1;
                mail.toShare2 = feedObj.toShare2;
                mail.addressee = feedObj.addressee;
                mail.toShare = feedObj.toShare;
                mail.mailTo = feedObj.receipient;
                mail.subject = feedObj.subject;
                mail.message = feedObj.message; 
                mail.US = sysDataHelper.getProps("SHORT-NAME", "NA");
                mail.mailTitle = sysDataHelper.getProps("WELCOME-TITLE", "NA");
                mail.supportInfo = sysDataHelper.getProps("SUPPORT-TEAM", "NA");
                mail.banner = sysDataHelper.getProps("OTP-BANNER", "NA");
                mail.ourUrl = sysDataHelper.getProps("WWW", "NA");
                mail.supportLines = sysDataHelper.getProps("SUPPORT-LINES", "NA");
                mail.otpExpiration = sysDataHelper.getProps("OTP-EXPIRATION", "NA");
                mail.tableBGColor = sysDataHelper.getProps("MAIL-TABLE-BG-COLOR", "NA");
                mail.tableHDColor = sysDataHelper.getProps("MAIL-TABLE-HD-COLOR", "NA");
                mail.tableBorderColor = sysDataHelper.getProps("MAIL-TABLE-BORDER-COLOR", "NA");
                    
                log.info(" ##-- sendCustomerWelcomeEmailAsync MailObj -- "+mail);
        
        return reactiveMailer.send(
               
                Mail.withHtml(mail.mailTo, mail.subject, new MailTemplates().doAdminWelcomeMail(mail)),
                Mail.withHtml(mail.cc,mail.subject, new MailTemplates().doAdminWelcomeMail(mail)))
                .subscribeAsCompletionStage().thenApply(x -> true);
        
        
        
         } 
         catch (Exception e) {
           
             log.error(" $ Exception in sendCustomerWelcomeEmailAsync ",e);
             
             
             return null;
          
         
         }
          
    }
    
   /*
    public boolean sendingMail(PaymentNotificationLog payeeInfo, CUResponseObj CUResponseObj) {
        
        boolean sent = false;
        try 
        {
           
          
            MailObj mail = new MailObj();
            
            mail.setAmount("N "+decimalFormat.format(payeeInfo.getPaidAmount()));
          
            mail.setMailto(payeeInfo.getCustomerEmail());//payeeInfo.getEmail()
            mail.setPaymentDate(new Timestamp(payeeInfo.getPaidDate().getTime()).toLocalDateTime().format(DateTimeFormatter.ISO_DATE));
            mail.setPaymentReference(payeeInfo.getPaymentRef());
            mail.setPin(CUResponseObj !=null?CUResponseObj.getPin():"NA");
            mail.setReceiptNo(CUResponseObj !=null?CUResponseObj.getReceiptNo():"NA");
            mail.setOtherInfo(CUResponse.narrateCUResponse(CUResponseObj !=null?CUResponseObj.getStatus():"NA"));
            mail.setReceipient(payeeInfo.getFullName());
            mail.setSubject("Covenant University PIN/RECEIPT Notification");//CU PAYMENT NOTIFICATION");

            mailer.send(Mail.withHtml(mail.getMailto(), mail.getSubject(), new MailTemplates().doMail(mail)),
                    Mail.withHtml("payments@covenantmfb.com.ng", mail.getSubject(), new MailTemplates().doMail(mail)));
 
            sent = true;
         
        
        }
        catch (Exception e) {
        
             e.printStackTrace();
             
             log.info("-#- Exception error sendingMail -sending params  payeeInfo -- "+payeeInfo);
             log.info("-#- Exception error sendingMail -sending params  CUResponseObj -- "+CUResponseObj);
             
             log.error(" # Exception in Sending Mail  -- ",e);
         
         }
       return sent;
    }
    */
    
}
