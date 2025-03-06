/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.repositories;





import com.paycraftsystems.cmfb.controller.ESEQHelper;
import com.paycraftsystems.cmfb.dto.ApproveOrDeleteRequest;
import com.paycraftsystems.cmfb.dto.PaymentBeneficiaryEditRequestObj;
import com.paycraftsystems.cmfb.dto.PaymentBeneficiaryRequestObj;
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
public class PaymentsBeneficiaryRepository implements  PanacheRepository<PaymentBeneficiarySetup> {
    
    
    @Inject
    ESEQHelper eseqHelper;
    
    public PaymentBeneficiarySetup doLookUpById(long tid) {
    // log.info("obj = " + obj.reference);
     return find("tid = ?1", tid).firstResult();
    
    }
    
    public PaymentBeneficiarySetup doLookUpByName(String name) {
     log.info("obj = " + name);
     
     return find("paramName = ?1 ", name).firstResult();
    
    }
    
    public PaymentBeneficiarySetup doLookUpByName(String paymentDesc, String provider) {
     log.info("obj = " + paymentDesc+" provider : "+provider); 
     return find("beneficiaryDesc = ?1 and beneficiaryName = ?2 ", paymentDesc, provider).firstResult();
    
    }
    
    
    public PanacheQuery<PaymentBeneficiarySetup> findByParams(String status, LocalDateTime from, LocalDateTime today){
       return PaymentBeneficiarySetup.find("status = ?1 and createdDate between ?2 and ?3 order by createdDate desc", status, from, today);
    }
    
    public PanacheQuery<PaymentBeneficiarySetup> findByParams(String status,  LocalDateTime from, LocalDateTime today, String searchKey){
       return PaymentBeneficiarySetup.find("status = ?1 and createdDate between ?2 and ?3 and (beneficiaryContactPerson like ?4 or  beneficiaryDesc like ?4 or beneficiaryName like ?4 or or beneficiaryContactPerson like ?4 or beneficiaryContactPersonEmail like ?4 or  beneficiaryContactPersonMobile like ?4) order by createdDate desc", status, from, today, searchKey+'%');
    }
    
    public PanacheQuery<PaymentBeneficiarySetup> findByParams(LocalDateTime from, LocalDateTime today){
       return PaymentBeneficiarySetup.find("createdDate between ?1 and ?2 order by createdDate desc",from, today);
    }
    
    public PanacheQuery<PaymentBeneficiarySetup> findByParams(LocalDateTime from, LocalDateTime today, String searchKey){
       return PaymentBeneficiarySetup.find("createdDate between ?1 and ?2 and (beneficiaryDesc like ?4 or  productId like ?4  or  beneficiaryName like ?4 or beneficiaryContactPerson like ?4 or beneficiaryContactPerson like ?4 or beneficiaryContactPersonEmail like ?4 or  beneficiaryContactPersonMobile like ?4) order by createdDate desc",from, today, searchKey+'%');
    }
    
   
    
    
    @Transactional
    public PaymentBeneficiarySetup doLog(PaymentBeneficiaryRequestObj request) throws Exception {
        PaymentBeneficiarySetup obj = null;
        String firstname = "", lastname ="";
        try 
        {
            
           PaymentBeneficiarySetup doLookUpByParamName = doLookUpByName(request.beneficiaryDesc, request.beneficiaryName);// doLookUpByName(request.paramName);
           log.info("--doLookUpByParamName -- "+doLookUpByParamName);
           if(doLookUpByParamName == null)
           {
          
                obj = new PaymentBeneficiarySetup();
                //obj.beneficiaryCollectionAccount = request.beneficiaryCollectionAccount;
                //obj.beneficiaryCollectionAccountName = request.beneficiaryCollectionAccountName;
                obj.beneficiaryDesc = request.beneficiaryDesc;
                obj.beneficiaryName = request.beneficiaryName;
                obj.beneficiaryAddress  = request.beneficiaryAddress;
                obj.beneficiaryContactPerson = request.beneficiaryContactPersonName;
                obj.beneficiaryContactPersonEmail = request.beneficiaryContactPersonEmail;
                obj.beneficiaryContactPersonMobile = request.beneficiaryContactPersonMobile;
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
            //e.printStackTrace();
            log.error("Exception @ PaymentBeneficiarySetup doLog", e);
            throw new Exception(e);
        }
     return obj;
    }
    
    
    @Transactional
    public PaymentBeneficiarySetup doSync(PaymentBeneficiaryEditRequestObj request) throws Exception {
        log.info("-- PaymentBeneficiarySetup doSync --- "+request);
        PaymentBeneficiarySetup obj = new PaymentBeneficiarySetup();
        try 
        {
         
           PaymentBeneficiarySetup ps = doLookUpById(request.tid);
           log.info("-- PaymentBeneficiarySetup doSync --- "+ps);
           //log.info("-- do sync objx = " + doLookUpByParamName+" : : code "+code);
           if(ps != null)
           {
                ps.beneficiaryDesc = request.beneficiaryDesc;
                ps.beneficiaryName = request.beneficiaryName;
                
                //obj.beneficiaryCollectionAccount = request.beneficiaryCollectionAccount;
                //obj.beneficiaryCollectionAccountName = request.beneficiaryCollectionAccountName;
                ps.beneficiaryAddress  = request.beneficiaryAddress;
                ps.beneficiaryContactPerson = request.beneficiaryContactPersonName;
                ps.beneficiaryContactPersonEmail = request.beneficiaryContactPersonEmail;
                ps.beneficiaryContactPersonMobile = request.beneficiaryContactPersonMobile;
                ps.createdDate = LocalDateTime.now();
                ps.status = ResourceStatusEnum.INACTIVE.name();
                ps.updatedBy = request.actionBy;
                ps.createdByStr = request.actionByStr;
                ps.updatedBy = request.actionBy;
                ps.updatedByStr = request.actionByStr;
              
              PaymentBeneficiarySetup merge = Panache.getEntityManager().merge(ps);
              log.info(" doSync-- merge");
              
              return merge;
           }
           else
           {
               return null;
           }
          
        } 
        catch (Exception e) {
            //e.printStackTrace();
            log.error("Exception @ PaymentBeneficiarySetup doApprove", e);
            throw new Exception(e);
        }
     
    }
    
    @Transactional
    public PaymentBeneficiarySetup doApprove(ApproveOrDeleteRequest request) throws Exception {
        PaymentSetup obj = new PaymentSetup();
        try 
        {
         
           PaymentBeneficiarySetup ps = doLookUpById(request.tid());
           //log.info("-- do sync objx = " + doLookUpByParamName+" : : code "+code);
           if(ps != null)
           {
             
              ps.authorizedDate = LocalDateTime.now();
              ps.authorizedBy = request.actionBy();
              ps.status = ResourceStatusEnum.ACTIVE.name();
              
              PaymentBeneficiarySetup merge = Panache.getEntityManager().merge(ps);
              log.info(" doSync-- merge");
              
              return merge;
           }
           else
           {
               return null;
           }
          
        } 
        catch (Exception e) {
            //e.printStackTrace();
            log.error("Exception @ PaymentBeneficiarySetup doSync", e);
            throw new Exception(e);
        }
     
    }
    
    @Transactional
    public PaymentBeneficiarySetup doDelete(ApproveOrDeleteRequest request) throws Exception {
        PaymentBeneficiarySetup obj = new PaymentBeneficiarySetup();
        try 
        {
         
           PaymentBeneficiarySetup doLookUpByParamName = doLookUpById(request.tid());
           //log.info("-- do sync objx = " + doLookUpByParamName+" : : code "+code);
           if(doLookUpByParamName != null)
           {
              doLookUpByParamName.status  = ResourceStatusEnum.DELETED.name();
              doLookUpByParamName.updatedDate = LocalDateTime.now();
              doLookUpByParamName.updatedBy = request.actionBy();
             
              
              PaymentBeneficiarySetup merge = Panache.getEntityManager().merge(doLookUpByParamName);
              log.info(" doSync-- merge");
              
              return merge;
           }
           else
           {
               return null;
           }
          
        } 
        catch (Exception e) {
            log.error("Exception @ PaymentBeneficiarySetup doDelete", e);
            throw new Exception(e);
        }
     
    }
 
    
}
