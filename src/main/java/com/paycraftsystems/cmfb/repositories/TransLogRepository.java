/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.repositories;

import com.paycraftsystems.cmfb.controller.ESEQRepository;
import com.paycraftsystems.cmfb.dto.InitPaymentRequest;
import com.paycraftsystems.cmfb.dto.response.MakePaymentResponse;
import com.paycraftsystems.cmfb.dto.response.ValidateCheckResponse;
import com.paycraftsystems.cmfb.entities.TransactionLog;
import com.paycraftsystems.cmfb.resources.ResourceHelper;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author paycraftsystems-i
 */
@ApplicationScoped
@Slf4j
public class TransLogRepository  implements  PanacheRepository<TransactionLog> {
    
    
      @Inject
      ESEQRepository  eseqRepository;
    
     public   TransactionLog doLookupByTransactionId(@Valid String requestId) throws Exception {
        try 
        {
             return find("transactionId", requestId).firstResult();
        
        } 
        catch (Exception e) {
            
            throw new Exception(e);
        }
     
    }
     
     
     public   TransactionLog doLookupById(@Valid long tid) throws Exception {
        try 
        {
             return find("tid", tid).firstResult();
        
        } 
        catch (Exception e) {
            
            throw new Exception(e);
        }
     
    }
     
    public PanacheQuery<TransactionLog> findByParams(String status, LocalDateTime from, LocalDateTime today){
       return TransactionLog.find("transStatus = ?1 and createdDate between ?2 and ?3 order by createdDate desc", status, from, today);
    }
    
    public PanacheQuery<TransactionLog> findByParams(String status,  LocalDateTime from, LocalDateTime today, String searchKey){
       return TransactionLog.find("transStatus = ?1 and createdDate between ?2 and ?3 and (paymentDesc like ?4 or  productId like ?4 or beneficiaryName like ?4) order by createdDate desc", status, from, today, searchKey+'%');
    }
    
    public PanacheQuery<TransactionLog> findByParams(LocalDateTime from, LocalDateTime today){
       return TransactionLog.find("createdDate between ?1 and ?2 order by createdDate desc",from, today);
    }
    
    public PanacheQuery<TransactionLog> findByParams(LocalDateTime from, LocalDateTime today, String searchKey){
       return TransactionLog.find("createdDate between ?1 and ?2 and (paymentDesc like ?4 or  productId like ?4  or  beneficiaryName like ?4 ) order by createdDate desc",from, today, searchKey+'%');
    }
    
    public PanacheQuery<TransactionLog> findByParams(LocalDateTime from, LocalDateTime today, String searchKey, long tellerId){
       return TransactionLog.find("createdDate between ?1 and ?2 and (paymentDesc like ?4 or  productId like ?4  or  beneficiaryName like ?4)  and  createBy = ?5 order by createdDate desc",from, today, searchKey+'%', tellerId);
    }
    
   public   TransactionLog doLookupApprovalTransaction(@Valid String requestId, String transType) throws Exception {
        try 
        {
          return find("exTransId = ?1 and ourRespCode = ?2 and transTypeStr = ?3 ", requestId,"01", transType).firstResult();
          
        } 
        catch (Exception e) {
            
            throw new Exception(e);
        }
    }
    
    public   TransactionLog doLookUpByTransactionId(@Valid String requestId) throws Exception {
        
        
      try
      {
        
         return find("requestId", requestId).firstResult();
      } 
        catch (Exception e) {
            
            throw new Exception(e);
      }
   }
    
    
    public   TransactionLog doLookupById(@Valid String requestId, String srcAccount) throws Exception {
        
        
      try
      {
        
         return  find("exTransId =?1 and srcAccount = ?2 ", requestId, srcAccount).firstResult();
      
      } 
        catch (Exception e) {
            
            throw new Exception(e);
      }
    }
    
    
    public  List<TransactionLog> doLookupTransactionBySrcAccount( String srcAccount, LocalDateTime start, LocalDateTime end) throws Exception {
        
      try
      {
         return  find("srcAccount = ?1 and createdDate between ?2 and ?3 ",srcAccount, start, end).list();
      } 
      catch (Exception e) {
            
            throw new Exception(e);
      }
    }
    
    public  double doTransactionSumBySrcAccount( String srcAccount, LocalDateTime start, LocalDateTime end) throws Exception {
        double todaysTotal = 0;
        try 
        {
            
            List<TransactionLog> list = find(" srcAccount = ?1 and createdDate between ?2 and ?3 ",srcAccount, start, end).list();
    
            todaysTotal = list.stream().mapToDouble(x->x.transactionAmount).sum();
            
        } 
        catch (Exception e) {
            
            e.printStackTrace();
            
            throw new Exception(e);
        
        }
     return todaysTotal;
    }
    
    
    
    @Transactional
    public static TransactionLog doSync(TransactionLog log, boolean exceedsLimit) throws Exception {
        
        TransactionLog obj = null;
        try 
        {
            obj = Panache.getEntityManager().merge(log);
        } 
        catch (Exception e) {
       
            throw new Exception(e);
        }
        
      return obj;
    }
    
    
    @Transactional
    public  TransactionLog doLog(InitPaymentRequest pay, String transDesc, String beneficiaryName, String productId) throws Exception {
        
        TransactionLog obj = null;// new Te;
        String padda = "";
        
        ResourceHelper rh = new ResourceHelper();
        try 
        {
            String padZero = rh.padZero(3, pay.actionBy()+"");
            log.info("-- doLog padZero "+padZero);
            obj = new TransactionLog();
            obj.transactionId = eseqRepository.genCode(padZero, 10);
            obj.createdDate = LocalDateTime.now();
            obj.customerAddress = pay.payeeAddress();
            obj.beneficiaryName = beneficiaryName;
            obj.productId  = productId;
            obj.createdBy = pay.actionBy();
            obj.createByStr = pay.actionByStr();
            obj.transactionAmount = pay.amount();
            obj.customerMail = pay.payeeEmail();
            obj.customerMobile = pay.payeeMobileNo();
            obj.payerId = pay.payeeId();
            obj.paymentFee = pay.fee();
            obj.taxAmount = pay.taxAmount();
            //obj.transStatus = status;
            obj.transDesc = transDesc;
            obj.customerFullname = pay.doPayeeName();
            obj.trxFee = pay.fee();
            
            obj = Panache.getEntityManager().merge(obj);
        } 
        catch (Exception e) {
       
            log.error("Exception e doLog", e);
            throw new Exception(e);
            
            
        }
        
      return obj;
    }
    
    
    @Transactional
    public  TransactionLog doSync(TransactionLog tlog, ValidateCheckResponse resp, String status) throws Exception {
        
        TransactionLog obj = null;// new Te;
        String padda = "";
        
        ResourceHelper rh = new ResourceHelper();
        try 
        {
            if(tlog !=null)
            {
                TransactionLog doLookupById = doLookupById(tlog.tid);

                if(doLookupById != null)
                {
                    doLookupById.updatedDate = LocalDateTime.now();
                    doLookupById.paymentCode =  (resp.payparameter() !=null)?resp.payparameter().passcode():"NA";
                    doLookupById.destAccount = (resp.payparameter() !=null && resp.payparameter().accountsettlement() !=null)?resp.payparameter().accountsettlement().accountnumber1():"NA";
                    doLookupById.destAccount2 = (resp.payparameter() !=null && resp.payparameter().accountsettlement() !=null)?resp.payparameter().accountsettlement().accountnumber2():"NA";
                    doLookupById.responseStr = resp.toString();
                    doLookupById.transStatus = status;
                    doLookupById.partnerResp = resp.payparameter().responsename();

                   obj = Panache.getEntityManager().merge(doLookupById);
                }
                
            }
           
            
        } 
        catch (Exception e) {
       
            log.error("Exception e doSync",e);
            throw new Exception(e);
        }
        
      return obj;
    }
    
    
     @Transactional
    public  TransactionLog doSyncPayment(long tid, MakePaymentResponse resp) throws Exception {
        
        TransactionLog obj = null;// new Te;
        String padda = "";
        
        ResourceHelper rh = new ResourceHelper();
        try 
        {
            if(tid > 0)
            {
                TransactionLog doLookupById = doLookupById(tid);

                if(doLookupById != null)
                {
                    doLookupById.updatedDate = LocalDateTime.now();
                    doLookupById.customResponseParam1 =  (resp.payparameter() !=null)?resp.payparameter().receiptno():"NA";
                    doLookupById.customResponseParam2 = (resp.payparameter() !=null && resp.payparameter().pinno() !=null)?resp.payparameter().pinno():"NA";
                    //doLookupById.responseStr = resp.toString();
                    //doLookupById.partnerResp = resp.payparameter().responsename();

                   obj = Panache.getEntityManager().merge(doLookupById);
                }
                
            }
           
            
        } 
        catch (Exception e) {
       
            log.error("Exception e doSync",e);
            throw new Exception(e);
        }
        
      return obj;
    }
}
