/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.repositories;





import com.paycraftsystems.cmfb.controller.ESEQHelper;
import com.paycraftsystems.cmfb.dto.UserProfileRequest;
import com.paycraftsystems.cmfb.dto.UserProfileRequestObj;
import com.paycraftsystems.cmfb.entities.PaymentBeneficiarySetup;
import com.paycraftsystems.cmfb.entities.ProfileSetup;
import com.paycraftsystems.cmfb.entities.RolesInfo;
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
public class ProfilesRepository implements  PanacheRepository<ProfileSetup> {
    
    
    @Inject
    ESEQHelper eseqHelper;
    
   
    
    
    public ProfileSetup doLookUpById(long tid) {
    // log.info("obj = " + obj.reference);
     return find("tid = ?1", tid).firstResult();
    
    }
    
    public ProfileSetup doLookUpByName(String name) {
     log.info("obj = " + name);
     
     return find("userName = ?1 ", name).firstResult();
    
    }
    /*
    public ProfileSetup doLookUpByName(String paymentDesc, String provider) {
     log.info("obj = " + paymentDesc+" provider : "+provider); 
     return find("paymentDesc = ?1 and beneficiaryName = ?2 ", paymentDesc, provider).firstResult();
    
    }
    */
    public ProfileSetup doLookUpByName(String emailAddress, String mobileNo) {
     log.info("obj = " + emailAddress+" mobileNo : "+mobileNo); 
     return find("emailAddress = ?1 and mobileNo = ?2 ", emailAddress, mobileNo).firstResult();
    
    }
    
    /*
    public String doLookUpByNameStr(String name, String defaultTo) {
      log.info("obj = " + name);
      PaymentSetup firstResult = find("plansDesc = ?1 ", name).firstResult();
      return firstResult !=null?firstResult.plansDesc:defaultTo;
    
    }
    */
    
    public PanacheQuery<ProfileSetup> findByParams(String status, LocalDateTime from, LocalDateTime today){
       return ProfileSetup.find("status = ?1 and createdDate between ?2 and ?3 order by createdDate desc", status, from, today);
    }
    
    public PanacheQuery<ProfileSetup> findByParams(String status,  LocalDateTime from, LocalDateTime today, String searchKey){
       return ProfileSetup.find("status = ?1 and createdDate between ?2 and ?3 and (emailAddress like ?4 or  full_name like ?4 or beneficiaryName like ?4) order by createdDate desc", status, from, today, searchKey+'%');
    }
    
    public PanacheQuery<ProfileSetup> findByParams(LocalDateTime from, LocalDateTime today){
       return ProfileSetup.find("createdDate between ?1 and ?2 order by createdDate desc",from, today);
    }
    
    public PanacheQuery<ProfileSetup> findByParams(LocalDateTime from, LocalDateTime today, String searchKey){
       return ProfileSetup.find("createdDate between ?1 and ?2 and (emailAddress like ?4 or  full_name like ?4  or  beneficiaryName like ?4 ) order by createdDate desc",from, today, searchKey+'%');
    }
    
   
    
    
    @Transactional
    public ProfileSetup doLog(UserProfileRequest request ) throws Exception {
        ProfileSetup profile = null;
        try 
        {
            
            UserProfileRequestObj fromJson =  new UserProfileRequestObj(request);
            
           ProfileSetup profilex = doLookUpByName(fromJson.emailAddress, fromJson.mobileNo);// doLookUpByName(request.paramName);
           log.info("--doLookUpByParamName -- "+profilex);
           if(profilex == null)
           {
                        // doLookUpByName(request.paymentDesc,request.beneficiaryId);
                      
                        profile = new ProfileSetup();
                        profile.createdDate = LocalDateTime.now();
                        profile.emailAddress = fromJson.emailAddress;
                        profile.firstName = fromJson.firstName;
                        profile.full_name = fromJson.lastName+" "+((fromJson.middleName ==null)?"":fromJson.middleName)+" "+fromJson.firstName;
                        profile.lastName = fromJson.lastName;
                        profile.loginStatus = 0;
                        profile.mobileNo = fromJson.mobileNo;
                        profile.tilAccount   = fromJson.tilAccount;
                        profile.status = ResourceStatusEnum.INACTIVE.name();// BigInteger.TEN.longValue();
                        //profile.statusStr = Status.doStatusDescById(profile.status);
                        profile.userRole = fromJson.userRole;
                        profile.userRoleStr = RolesInfo.doFindRoleDescByCode(profile.userRole);
                        //                        profile = Panache.getEntityManager().persist(this).merge(profile);
                        Panache.getEntityManager().persist(profile);//.merge(profile);
                        Panache.getEntityManager().flush();//.getTransaction()..commit();
           
           }            
           else
           {
               return profile;
           }
           
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
     return profile;
    }
    
    /*
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
 */
    
}
