/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.entities;


import com.paycraftsystems.cmfb.dto.NotificationRequest;
import com.paycraftsystems.resources.ErrorCodes;
//import com.paycraftsystems.notifications.controller.NotificationsProcessor;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author root
 */
@Entity
@Table(name="notifications_log")
public class NotificationsFeed extends PanacheEntityBase implements Serializable {
    
    private static Logger LOGGER =  LoggerFactory.getLogger(NotificationsFeed.class);
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long tid;
    
    @Column(name="addressee")
    public String addressee;
    
    @Column(name="channel")
    public long channel;
    
    @Column(name="request_id")
    public String requestId;
    
    @Column(name="mesg")
    public String message;
    
    @Column(name="mesg_2")
    public String message2;
    
    @Column(name="send_from")
    public String sendFrom;
    
    @Column(name="send_to")
    public String receipient;
    
    @Column(name="response_date")
    public LocalDateTime responseDate;
    
    @Column(name="request_date")
    public LocalDateTime requestDate;
      
    @Column(name="provider_id")
    public long providerId;
    
    @Column(name="provider_status")
    public String providerStatus;
    
    @Column(name="request_type")
    public String requestType;
    
    @Column(name="status")
    public long  status;
    
    @Column(name="http_status")
    public long  httpStatus;
    
    @Column(name="to_share")
    public String toShare;
    
    @Column(name="to_share_label")
    public String toShareLabel;
    
    
    @Column(name="option_code")
    public String option;
    
    @Column(name="subject")
    public String subject;
    
    @Column(name="to_share_1")
    public String toShare1;
    
    @Column(name="to_share_2")
    public String toShare2;
    
    
    
    @Transactional
    public static NotificationsFeed doLog(NotificationRequest  requestObj, long channel, long defaultStatus, String requestType, String sendFrom) throws Exception {
        System.out.println(" ::: NotificationsFeed  doLog defaultStatus =::::  " + defaultStatus);
        NotificationsFeed merge = null;
        try 
        {
            
            NotificationsFeed obj = new NotificationsFeed();
           
           // if(requestObj != null && ""(requestObj.notificationType)
            obj.channel = channel;
            obj.toShare = requestObj.toShare;
            obj.toShare1 = requestObj.toShare1;
            obj.message = requestObj.message;
            obj.sendFrom = sendFrom;
            
            obj.message2 = "NA";
            obj.providerStatus = "NA";
            obj.requestDate = LocalDateTime.now();
            obj.receipient = requestObj.sendTo;
            obj.requestId = requestObj.requestId;
            obj.option = requestObj.option;
            obj.requestType = requestType;//requestObj.notificationType;
            obj.status = defaultStatus;
            obj.addressee = requestObj.addressee;
            //obj.toShare1 = requestObj.iv;
           // obj.toShare2 =  requestObj.key;    
            System.out.println("GOT HERE  = ");
            obj.subject = (requestObj.subject==null || requestObj.subject.trim().isEmpty())?"MESSAGE":requestObj.subject;
            System.out.println(" @@@GOT HERE  = ");
            merge = Panache.getEntityManager().merge(obj);
            
            System.out.println("GOT HERE  = "+merge);
        }
        catch (Exception e) {
        
           e.printStackTrace();
           LOGGER.error(" @@ Exception @ NotificationsFeed doLog ",e);
           
           throw new Exception(e);
          
        }
        
        
      return merge;
    }
    
    
    @Transactional
    public static NotificationsFeed doLogCW(NotificationRequest  requestObj, long channel, long defaultStatus, String requestType, String sendFrom) {
        System.out.println(" ::: NotificationsFeed  doLog defaultStatus =::::  " + defaultStatus);
        NotificationsFeed merge = null;
        try 
        {
            
            NotificationsFeed obj = new NotificationsFeed();
            
           // if(requestObj != null && ""(requestObj.notificationType)
            obj.channel = channel;
            obj.toShare = requestObj.toShare;
            obj.toShareLabel = requestObj.toShareLabel;
            obj.message = requestObj.message;
            obj.sendFrom = sendFrom;
            
            obj.message2 = "NA";
            obj.providerStatus = "NA";
            obj.requestDate = LocalDateTime.now();
            obj.receipient = requestObj.sendTo;
            obj.requestId = requestObj.requestId;
            obj.option = requestObj.option;
            obj.requestType = requestType;//requestObj.notificationType;
            obj.status = defaultStatus;
            obj.addressee = requestObj.addressee;
            obj.toShare1 = requestObj.toShare1;
            obj.toShare2 =  requestObj.toShare2;    
            obj.subject = (requestObj.subject==null || requestObj.subject.trim().isEmpty())?"MESSAGE":requestObj.subject;
            
            merge = Panache.getEntityManager().merge(obj);
            
        }
        catch (Exception e) {
        
            
           LOGGER.error(" Exception @ NotificationsFeed doLog ",e);
        }
        
        
      return merge;
    }
    
    
    @Transactional
    public static NotificationsFeed doSync(String requestId, String partnerResp) {
        //NotificationsFeed obj = null;
        NotificationsFeed merge = null;
        try 
        {
            
            NotificationsFeed obx = NotificationsFeed.doLookUp(requestId);
            if(obx !=null)
            {
                obx.providerStatus = partnerResp;
                obx.requestDate = LocalDateTime.now();
                merge = Panache.getEntityManager().merge(obx);
            }
          
            
        } 
        catch (Exception e) {
        
            
            //e.printStackTrace();
            
            LOGGER.error(" Exception @ NotificationsFeed doSync ",e);
        }
        
        
      return merge;
    }
    
    
    @Transactional
    public static NotificationsFeed doStamp(long requestId, int httpstatus, long status) {
        //NotificationsFeed obj = null;
        NotificationsFeed merge = null;
        try 
        {
            
            NotificationsFeed obx = NotificationsFeed.findById(requestId);
            if(obx !=null)
            {
                obx.status = status;
                obx.httpStatus = httpstatus;
                //obx. = LocalDateTime.now();
                merge = Panache.getEntityManager().merge(obx);
            }
          
            
        } 
        catch (Exception e) {
        
            
            LOGGER.error(" Exception @ NotificationsFeed doStamp ",e);
        }
        
        
      return merge;
    }
    
    
    //@Transactional
    public static NotificationsFeed doLookUp(String requestId) {
        NotificationsFeed obj = null;
        try 
        {
           obj = find("requestId = ?1 ", requestId).firstResult();
        } 
        catch (Exception e) {
        
            
            //e.printStackTrace();
            
             LOGGER.error(" Exception @ NotificationsFeed doLookUp ",e);
        }
        
       return obj;
        
    }
    
    
    public static List<NotificationsFeed> doLookUp(long status, int limit) {
        List<NotificationsFeed> obj = new ArrayList<>();
        try 
        {
           obj = find("status = ?1 ", status).range(0, limit).list();
        } 
        catch (Exception e) {
        
            
           // e.printStackTrace();
            
             LOGGER.error(" Exception @ NotificationsFeed doLookUp 2 ",e);
        }
        
       return obj;
        
    }

    @Override
    public String toString() {
        return "NotificationsFeed{" + "tid=" + tid + ", requestId=" + requestId + ", message=" + message + ", message2=" + message2 + ", sendFrom=" + sendFrom + ", receipient=" + receipient + ", responseDate=" + responseDate + ", requestDate=" + requestDate + ", providerId=" + providerId + ", providerStatus=" + providerStatus + ", requestType=" + requestType + ", status=" + status + ", toShare=" + toShare + ", option=" + option + '}';
    }
   
}
