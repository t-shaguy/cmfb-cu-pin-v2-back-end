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
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//io/quarkus/quarkus-mutiny-reactive-streams-operators/3.2.0.Final/quarkus-mutiny-reactive-streams-operators-3.2.0.Final.jar
/**
 *
 * @author root
 */

@ApplicationScoped
public class MailProcessor {
    
    private static Logger  LOGGER = LoggerFactory.getLogger(MailProcessor.class);
    
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
        LOGGER.info(" *** called async mailer sendWelcomeEmailAsync...... ");
        
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
        
             LOGGER.error(" $ Exception in sendMailAsync ",e);
             
             
             return null;
          
         
         }
          
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
        
             LOGGER.error(" $ Exception in sendMailAsync ",e);
             
             
             return null;
          
         
         }
          
    }
     */
     
    public CompletionStage<Boolean> sendOTPEmailAsync(NotificationsFeed  feedObj) {
        LOGGER.info(" *** called async mailer sendOTPEmailAsync...... ");
        
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
                
                        
                LOGGER.info(" ##-sendOTPEmailAsync- MailObj -- "+mail);
        
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
        
             LOGGER.error(" $ Exception in sendMailAsync ",e);
             
             
             return null;
          
         
         }
          
    }
    
    public CompletionStage<Boolean> sendResetEmailAsync(NotificationsFeed  feedObj) {
        LOGGER.info(" *** called async mailer sendResetEmailAsync...... ");
        
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
                
                        
                LOGGER.info(" ##--sendResetEmailAsync MailObj -- "+mail);
        
        return reactiveMailer.send(
               
                Mail.withHtml(mail.mailTo, mail.subject, new MailTemplates().doResetMailV2(mail)),
                Mail.withHtml(mail.cc,mail.subject, new MailTemplates().doResetMailV2(mail)))
                .subscribeAsCompletionStage().thenApply(x -> true);
        
        
        
         } 
         catch (Exception e) {
             
            // e.printStackTrace();
        
             LOGGER.error(" $ Exception in sendResetEmailAsync ",e);
             
             
             return null;
          
         
         }
          
    }
    
    public CompletionStage<Boolean> sendCustomerWelcomeEmailAsync(NotificationsFeed  feedObj) {
        LOGGER.info(" *** called async mailer sendCustomerWelcomeEmailAsync...... ");
        
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
                    
                LOGGER.info(" ##-- sendCustomerWelcomeEmailAsync MailObj -- "+mail);
        
        return reactiveMailer.send(
               
                Mail.withHtml(mail.mailTo, mail.subject, new MailTemplates().doCustomerWelcomeMailV2(mail)),
                Mail.withHtml(mail.cc,mail.subject, new MailTemplates().doCustomerWelcomeMailV2(mail)))
                .subscribeAsCompletionStage().thenApply(x -> true);
        
        
        
         } 
         catch (Exception e) {
             
            // e.printStackTrace();
        
             LOGGER.error(" $ Exception in sendCustomerWelcomeEmailAsync ",e);
             
             
             return null;
          
         
         }
          
    }
    
    public CompletionStage<Boolean> sendAdminWelcomeEmailAsync(NotificationRequest  feedObj) {
        LOGGER.info(" *** called async mailer sendAdminWelcomeEmailAsync...... ");
        
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
                    
                LOGGER.info(" ##-- sendCustomerWelcomeEmailAsync MailObj -- "+mail);
        
        return reactiveMailer.send( Mail.withHtml(mail.mailTo, mail.subject, new MailTemplates().doAdminWelcomeMail(mail)),
                Mail.withHtml(mail.cc,mail.subject, new MailTemplates().doAdminWelcomeMail(mail)))
                .subscribeAsCompletionStage().thenApply(x -> true);
        
        
        
         } 
         catch (Exception e) {
           
             LOGGER.error(" $ Exception in sendCustomerWelcomeEmailAsync ",e);
             return null;
         }
          
    }
    
    public CompletionStage<Boolean> sendOTPEmailAsync(NotificationRequest  feedObj) {
        LOGGER.info(" *** called async mailer sendOTPEmailAsync...... ");
        
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
                    
                LOGGER.info(" ##-- sendCustomerWelcomeEmailAsync MailObj -- "+mail);
        
        return reactiveMailer.send(
               
                Mail.withHtml(mail.mailTo, mail.subject, new MailTemplates().doOTPMail(mail)),
                Mail.withHtml(mail.cc,mail.subject, new MailTemplates().doAdminWelcomeMail(mail)))
                .subscribeAsCompletionStage().thenApply(x -> true);
        
         } 
         catch (Exception e) {
           
             LOGGER.error(" $ Exception in sendCustomerWelcomeEmailAsync ",e);
             return null;
         }
          
    }
    
    public CompletionStage<Boolean> sendAdminWelcomeEmailAsync(NotificationsFeed  feedObj) {
        LOGGER.info(" *** called async mailer sendAdminWelcomeEmailAsync...... ");
        
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
                    
                LOGGER.info(" ##-- sendCustomerWelcomeEmailAsync MailObj -- "+mail);
        
        return reactiveMailer.send(
               
                Mail.withHtml(mail.mailTo, mail.subject, new MailTemplates().doAdminWelcomeMail(mail)),
                Mail.withHtml(mail.cc,mail.subject, new MailTemplates().doAdminWelcomeMail(mail)))
                .subscribeAsCompletionStage().thenApply(x -> true);
        
        
        
         } 
         catch (Exception e) {
           
             LOGGER.error(" $ Exception in sendCustomerWelcomeEmailAsync ",e);
             
             
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
             
             LOGGER.info("-#- Exception error sendingMail -sending params  payeeInfo -- "+payeeInfo);
             LOGGER.info("-#- Exception error sendingMail -sending params  CUResponseObj -- "+CUResponseObj);
             
             LOGGER.error(" # Exception in Sending Mail  -- ",e);
         
         }
       return sent;
    }
    */
    
}
