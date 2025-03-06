/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.repositories;





import com.paycraftsystems.cmfb.controller.ESEQHelper;
import com.paycraftsystems.cmfb.dto.ApproveOrDeleteRequest;
import com.paycraftsystems.cmfb.dto.PaymentSetupEditRequestObj;
import com.paycraftsystems.cmfb.dto.PaymentSetupRequestObj;
import com.paycraftsystems.cmfb.entities.PaymentBeneficiarySetup;
import com.paycraftsystems.cmfb.entities.PaymentSetup;
import com.paycraftsystems.cmfb.enumz.ResourceStatusEnum;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author paycraftsystems-i
 */

@ApplicationScoped
@Slf4j
public class PaymentsRepository implements  PanacheRepository<PaymentSetup> {
    
    
    @Inject
    ESEQHelper eseqHelper;
    
    @Inject
    PaymentsBeneficiaryRepository paymentsBeneficiaryRepo;
    
    
    public PaymentSetup doLookUpById(long tid) {
    // log.info("obj = " + obj.reference);
     return find("tid = ?1", tid).firstResult();
    
    }
    
    public PaymentSetup doLookUpByName(String name) {
     log.info("obj = " + name);
     
     return find("paramName = ?1 ", name).firstResult();
    
    }
    
    public PaymentSetup doLookUpByName(String paymentDesc, String provider) {
     log.info("obj = " + paymentDesc+" provider : "+provider); 
     return find("paymentDesc = ?1 and beneficiaryName = ?2 ", paymentDesc, provider).firstResult();
    
    }
    
    public PaymentSetup doLookUpByName(String paymentDesc, long beneficiaryId) {
     log.info("obj = " + paymentDesc+" provider : "+beneficiaryId); 
     return find("paymentDesc = ?1 and beneficiaryId = ?2 ", paymentDesc, beneficiaryId).firstResult();
    
    }
    
    /*
    public String doLookUpByNameStr(String name, String defaultTo) {
      log.info("obj = " + name);
      PaymentSetup firstResult = find("plansDesc = ?1 ", name).firstResult();
      return firstResult !=null?firstResult.plansDesc:defaultTo;
    
    }
    */
    
    public PanacheQuery<PaymentSetup> findByParams(String status, LocalDateTime from, LocalDateTime today){
       return PaymentSetup.find("status = ?1 and createdDate between ?2 and ?3 order by createdDate desc", status, from, today);
    }
    
    public PanacheQuery<PaymentSetup> findByParams(String status,  LocalDateTime from, LocalDateTime today, String searchKey){
       return PaymentSetup.find("status = ?1 and createdDate between ?2 and ?3 and (paymentDesc like ?4 or  productId like ?4 or beneficiaryName like ?4) order by createdDate desc", status, from, today, searchKey+'%');
    }
    
    public PanacheQuery<PaymentSetup> findByParams(LocalDateTime from, LocalDateTime today){
       return PaymentSetup.find("createdDate between ?1 and ?2 order by createdDate desc",from, today);
    }
    
    public PanacheQuery<PaymentSetup> findByParams(LocalDateTime from, LocalDateTime today, String searchKey){
       return PaymentSetup.find("createdDate between ?1 and ?2 and (paymentDesc like ?4 or  productId like ?4  or  beneficiaryName like ?4 ) order by createdDate desc",from, today, searchKey+'%');
    }
    
    /*

     public PanacheQuery<InvoiceUpdateLogs> doList(LocalDateTime start, LocalDateTime end) {
       
        return find("requestDate between ?1 and ?2 order by requestDate desc", start, end);
    
    }
     
     
    public PanacheQuery<InvoiceUpdateLogs> doList(LocalDateTime start, LocalDateTime end, int status) {
       
        return find("requestDate between ?1 and ?2 and status = ?3 order by requestDate desc", start, end, status);
    
    }
    
    */
    
    
    @Transactional
    public PaymentSetup doLog(PaymentSetupRequestObj request , PaymentBeneficiarySetup doLookUpById) throws Exception {
        PaymentSetup obj = null;
        String firstname = "", lastname ="";
        try 
        {
            
           
            
           PaymentSetup doLookUpByParamName = doLookUpByName(request.paymentDesc, request.beneficiaryId);// doLookUpByName(request.paramName);
           log.info("--doLookUpByParamName -- "+doLookUpByParamName);
           if(doLookUpByParamName == null)
           {
               // doLookUpByName(request.paymentDesc,request.beneficiaryId);
               
                obj = new PaymentSetup();
                obj.amount = request.amount;
                obj.beneficiaryId = doLookUpById.tid;
                obj.beneficiaryName = doLookUpById.beneficiaryName;
                obj.payeeIdLabel = request.payeeIdLabel;
                obj.amountFixed = request.amountFixed;
                obj.paymentDesc =  request.paymentDesc;//LocalDateTime.now();
                obj.productId = eseqHelper.genCodePadded("COL", 8);
                //obj.providerName = request.providerName;
                obj.includeTax  = request.includeTax;
                obj.tax_amount = request.taxAmount;
                obj.paymentAccount  = request.paymentCollectionAccount;
                obj.paymentCollectionAccountName = request.paymentCollectionAccountName;
                obj.amountFixed = request.amountFixed==null?false: request.amountFixed;
                obj.includeTax = request.includeTax==null?false: request.includeTax;
                obj.min_amount = request.min_amount;
                obj.createdDate = LocalDateTime.now();
                obj.status = ResourceStatusEnum.INACTIVE.name();
                obj.createdBy = request.actionBy;
                obj.createdByStr = request.actionByStr;
                obj = Panache.getEntityManager().merge(obj);
           }
           else
           {
               return doLookUpByParamName;
           }
           
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
     return obj;
    }
    
    
    @Transactional
    public PaymentSetup doSync(PaymentSetupEditRequestObj request, PaymentBeneficiarySetup doLookUpById) throws Exception {
        PaymentSetup obj = new PaymentSetup();
        try 
        {
         
           PaymentSetup ps = doLookUpById(request.tid);
           //log.info("-- do sync objx = " + doLookUpByParamName+" : : code "+code);
           if(ps != null)
           {
             
                ps.amount = request.amount;
                ps.amountFixed = request.amountFixed;
                ps.paymentDesc =  request.paymentDesc;//LocalDateTime.now();
               // obj.productId = eseqHelper.genCodePadded(lastname, 8);
                //obj.providerName = request.providerName;
                ps.beneficiaryId = doLookUpById.tid;
                ps.beneficiaryName = doLookUpById.beneficiaryName;
                ps.includeTax  = obj.includeTax = request.includeTax==null?false: request.includeTax;
                ps.tax_amount = request.taxAmount;
                ps.amountFixed = obj.amountFixed = request.amountFixed==null?false: request.amountFixed;;
                ps.min_amount = request.min_amount;
                ps.updatedDate = LocalDateTime.now();
                ps.status = ResourceStatusEnum.INACTIVE.name();
                ps.updatedBy = request.actionBy;
                ps.updatedByStr = request.actionByStr;
              
              PaymentSetup merge = Panache.getEntityManager().merge(ps);
              log.info(" doSync-- merge");
              
              return merge;
           }
           else
           {
               return null;
           }
          
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
     
    }
    
    @Transactional
    public PaymentSetup doApprove(ApproveOrDeleteRequest request) throws Exception {
        PaymentSetup obj = new PaymentSetup();
        try 
        {
         
           PaymentSetup ps = doLookUpById(request.tid());
           //log.info("-- do sync objx = " + doLookUpByParamName+" : : code "+code);
           if(ps != null)
           {
             
              ps.authorizedDate = LocalDateTime.now();
              ps.authorizedBy = request.actionBy();
              ps.status = ResourceStatusEnum.ACTIVE.name();
              
              PaymentSetup merge = Panache.getEntityManager().merge(ps);
              log.info(" doSync-- merge");
              
              return merge;
           }
           else
           {
               return null;
           }
          
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
     
    }
    
    @Transactional
    public PaymentSetup doDelete(ApproveOrDeleteRequest request) throws Exception {
        PaymentSetup obj = new PaymentSetup();
        try 
        {
         
           PaymentSetup doLookUpByParamName = doLookUpById(request.tid());
           //log.info("-- do sync objx = " + doLookUpByParamName+" : : code "+code);
           if(doLookUpByParamName != null)
           {
              doLookUpByParamName.status  = ResourceStatusEnum.DELETED.name();
              doLookUpByParamName.updatedDate = LocalDateTime.now();
              doLookUpByParamName.updatedBy = request.actionBy();
             
              
              PaymentSetup merge = Panache.getEntityManager().merge(doLookUpByParamName);
              log.info(" doSync-- merge");
              
              return merge;
           }
           else
           {
               return null;
           }
          
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
     
    }
 
    
}
