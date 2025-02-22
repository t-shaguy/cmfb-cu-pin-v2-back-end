/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.controller;

import com.paycraftsystems.cmfb.dto.ApproveOrDeleteRequest;
import com.paycraftsystems.cmfb.dto.ApproveOrDeleteRequestObj;
import com.paycraftsystems.cmfb.dto.FilterRequest;
import com.paycraftsystems.cmfb.dto.FilterRequestObj;
import com.paycraftsystems.cmfb.dto.InitPaymentRequest;
import com.paycraftsystems.cmfb.dto.InitPaymentRequestObj;
import com.paycraftsystems.cmfb.dto.OtherPaymentParams;
import com.paycraftsystems.cmfb.dto.PaymentSetupEditRequest;
import com.paycraftsystems.cmfb.dto.PaymentSetupEditRequestObj;
import com.paycraftsystems.cmfb.dto.PaymentSetupRequest;
import com.paycraftsystems.cmfb.dto.PaymentSetupRequestObj;
import com.paycraftsystems.cmfb.dto.response.PaymentProcessResponse;
import com.paycraftsystems.cmfb.dto.response.PaymentSetupResponse;
import com.paycraftsystems.cmfb.dto.response.ValidateCheckResponse;
import com.paycraftsystems.cmfb.entities.PaymentBeneficiarySetup;
import com.paycraftsystems.cmfb.entities.PaymentSetup;
import com.paycraftsystems.cmfb.entities.SysData;
import com.paycraftsystems.cmfb.entities.TransactionLog;
import com.paycraftsystems.cmfb.repositories.PaymentsBeneficiaryRepository;
import com.paycraftsystems.cmfb.repositories.PaymentsRepository;
import com.paycraftsystems.cmfb.repositories.SysDataRepository;
import com.paycraftsystems.cmfb.repositories.TransLogRepository;
import com.paycraftsystems.cmfb.resources.ResourceHelper;
import com.paycraftsystems.exceptions.CMFBException;
import com.paycraftsystems.exceptions.InvalidRequestException;
import com.paycraftsystems.resources.ErrorCodes;
import io.quarkus.hibernate.orm.panache.Panache;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author paycraftsystems-i
 */

@ApplicationScoped
@Slf4j
public class PaymentsController {
    
    
    @Inject 
    PaymentsRepository paymentsRepo;
    
    @Inject 
    SysDataRepository sysDataRepo;
    
    @Inject
    TransLogRepository transLogRepo;
    
    @Inject
    ThirdPartyPaymentController thirdPartyPay;
    
    
    @Inject
    PaymentsBeneficiaryRepository paymentsBeneficiaryRepo;
    
   
    public List<SysData>  all;
    
    
    
   // @Inject
   // EntityManager em;
    
    @PostConstruct
    public void doReadAll() throws Exception {
        List<SysData> query =  new ArrayList<>();
        try 
        {
               all = null;// paymentsRepo.listAll();//  SysData.listAll();// em.createNamedQuery(SysData.ALL, SysData.class);
              
        } catch (Exception e) {
        
             e.printStackTrace();
             
             throw new Exception(e);
             
            // return new ArrayList<SysData>(){};
        }
     //return query;
    }
    
    
    public long countAll()
    {
        return Long.parseLong("0");//paymentsRepo.findAll().count();//.doReadAll().size();
    }
    
    public int countAll(String query)
    {
        return  0;//em.createNamedQuery(query).getResultList().size();
    }
    
    
    public String doLookUpOrDefault(String  resp, String defaultTo) {
    
     return null;// paymentsRepo.doFindOrDefaultTo(resp,defaultTo);
    }
    
    
    public PaymentSetupResponse doCreate(PaymentSetupRequest jsonStr) 
    {
        //int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        List<PaymentSetup> confs = new ArrayList<>();
        try 
        {
             
             PaymentSetupRequestObj fromJson = new PaymentSetupRequestObj(jsonStr);
             PaymentBeneficiarySetup doLookUpById = paymentsBeneficiaryRepo.doLookUpById(fromJson.beneficiaryId);
                
                 if( doLookUpById == null)
                 {
                    return new PaymentSetupResponse(false, ErrorCodes.INVALID_PAYMENT_BENEFICIARY,ErrorCodes.doErrorDesc(ErrorCodes.INVALID_PAYMENT_BENEFICIARY), null, null);
            
                 }
           
                PaymentSetup doLog = paymentsRepo.doLog(fromJson, doLookUpById); //doFindByDescription(jsonStr)
                
                if(doLog != null)
                {
                   // return Response.ok().entity(doLog.toJson()).build();
                     confs.add(doLog);
                     return new PaymentSetupResponse(true, ErrorCodes.SUCCESSFUL,ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doLog, null);
                }
                else
                {
                    //return Response.status(ErrorCodes.DATABASE_ERROR).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doDesc(ErrorCodes.DATABASE_ERROR,prodOrDev)).build()).build();
                
                    return new PaymentSetupResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
               
                }
                
           
        
        }
        catch (Exception e) {
        
                
                log.error("Exception @ Response doCreate ...",e);
                if(e.getCause() instanceof CMFBException)
                {
                    System.err.println(" ** AHA = " + e.getMessage()+" cause "+e.getCause());
                    CMFBException exp =   (CMFBException) e.getCause();
                    
                     return new PaymentSetupResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,e.getMessage()); 
               
                }
                else if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                     //throw new HelloException(ErrorCodes.DATABASE_ERROR, e.getMessage(),prodOrDev);
                     
                      return new PaymentSetupResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,e.getMessage()); 
               
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                    return new PaymentSetupResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,e.getMessage()); 
               
                }
                else
                {
                    // throw new HelloException(ErrorCodes.SYSTEM_ERROR, e.getMessage(),prodOrDev);
                     
                      return new PaymentSetupResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,e.getMessage()); 
               
                }
          }
        
    }
    
    public PaymentSetupResponse doEdit(PaymentSetupEditRequest jsonStr) {
       // int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
       // List<SysData> confs = new ArrayList<>();
        try 
        {
                PaymentSetupEditRequestObj fromJson = new PaymentSetupEditRequestObj(jsonStr);
                
                PaymentBeneficiarySetup doLookUpById = paymentsBeneficiaryRepo.doLookUpById(fromJson.beneficiaryId);
                
                 if( doLookUpById == null)
                 {
                    return new PaymentSetupResponse(false, ErrorCodes.INVALID_PAYMENT_BENEFICIARY,ErrorCodes.doErrorDesc(ErrorCodes.INVALID_PAYMENT_BENEFICIARY), null, null);
            
                 }
          
                PaymentSetup doLog = paymentsRepo.doSync(fromJson, doLookUpById); //doFindByDescription(jsonStr)
            
                if(doLog != null)
                {
                    //return Response.ok().entity(doLog.toJson()).build();
                    //confs.add(doLog);
                    return new PaymentSetupResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doLog,null); 
               
                }
                else
                {
                   // return Response.status(ErrorCodes.DATABASE_ERROR).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doDesc(ErrorCodes.DATABASE_ERROR,prodOrDev)).build()).build();
               
                   return new PaymentSetupResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
               
                
                }
                
           
        
        }
        catch (CMFBException e) {
             e.printStackTrace();
            //return Response.status(e.getResponse().getStatus()).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(e.getResponse().getStatus(),prodOrDev)).build()).build();
       
             return new PaymentSetupResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
               
        
        }
        catch (Exception e) {
        
               log.error("Exception @ Response sysdata doEdit ",e);
               
                if(e.getCause() instanceof CMFBException)
                {
                    //System.err.println(" ** AHA = " + e.getMessage()+" cause "+e.getCause());
                    CMFBException exp =   (CMFBException) e.getCause();
                     return new PaymentSetupResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,e.getMessage()); 
            
                }
               
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                      return new PaymentSetupResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,e.getMessage()); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     return new PaymentSetupResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
                else
                {
                    
                    return new PaymentSetupResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
        
        }
        
    }
    
    
    public PaymentSetupResponse doDelete(ApproveOrDeleteRequest request) {
        //int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        //List<SysData> confs = new ArrayList<>();
        try 
        {
            //SysDataEditRequestObj fromJson = new SysDataEditRequestObj(jsonStr);
            //validate dto
            
                PaymentSetup doLog = paymentsRepo.doDelete(request); //doFindByDescription(jsonStr)
            
                if(doLog != null)
                {
                    //confs.add(doLog);
                    return new PaymentSetupResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doLog,null); 
            
                }
                else
                {
                  
                   return new PaymentSetupResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                
                }
                
           
        
        }/*
        catch (HoptoolException e) {
             e.printStackTrace();
            return Response.status(e.getResponse().getStatus()).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(e.getResponse().getStatus(),prodOrDev)).build()).build();
        }*/
        catch (Exception e) {
        
               log.error("Exception @ Response sysdata doEdit ",e);
               
                if(e.getCause() instanceof CMFBException)
                {
                    //System.err.println(" ** AHA = " + e.getMessage()+" cause "+e.getCause());
                    CMFBException exp =   (CMFBException) e.getCause();
                   // return Response.status(exp.getResponse().getStatus()).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErroDesc(exp.getResponse().getStatus(),prodOrDev)).build()).build();
                
                    return new PaymentSetupResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,e.getMessage()); 
            
                
                }
               
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                   
                     return new PaymentSetupResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                    // throw new HoptoolException(ErrorCodes.IO_EXCEPTION, e.getMessage(),prodOrDev);
                     
                      return new PaymentSetupResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                      //throw new HoptoolException(ErrorCodes.SYSTEM_ERROR, e.getMessage(),prodOrDev);
                     
                      return new PaymentSetupResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                }
        
            //e.printStackTrace();
            ///return  Response.status(ErrorCodes.SYSTEM_ERROR).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(ErrorCodes.SYSTEM_ERROR)).build()).build();
        }
        
    }
    
   @Transactional
    public PaymentSetupResponse doApprove(@Valid ApproveOrDeleteRequest json) {
        //int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        //List<SysData> confs = new ArrayList<>();
        try 
        {
            ApproveOrDeleteRequestObj fromJson = new ApproveOrDeleteRequestObj(json);//.fromJson(json, SysDataDTO.class);
            
            
                      PaymentSetup merge = paymentsRepo.doApprove(json);
                      
                      if (merge == null) 
                      {
                         
                           return new PaymentSetupResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                      }
                      else
                      {
                            ///return Response.ok().entity(merge.toJson()).build();
                           
                           //confs.add(merge);
                           return new PaymentSetupResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), merge,null); 
            
          
                      }
            
           
        }/*
        catch (HoptoolException e)
        {
            e.printStackTrace();
            return Response.status(e.getResponse().getStatus()).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(e.getResponse().getStatus(), 1))).build();
        }*/
        catch (Exception e) {
            
                if(e.getCause() instanceof CMFBException)
                {
                    System.out.println("e = " + e);
                    CMFBException ex = (CMFBException)e.getCause();
                    //return Response.status(ex.getResponse().getStatus()).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(ex.getResponse().getStatus(), 1))).build();
                
                    return new PaymentSetupResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                
                }
                log.error("- EXCEPTION sysdata doApprove ", e);//e.printStackTrace();
            
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                   
                    return new PaymentSetupResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     return new PaymentSetupResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                  
                    return new PaymentSetupResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
        
        
        }
        
    }
    
    /*
    @Transactional
    public SysDataResponse doDelete(@Valid SysDataEditRequest json) {
        ///int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
       
        System.out.println("json doDelete= " + json);
        try 
        {
            SysDataDTO fromJson = JsonbBuilder.create().fromJson(json.toString(), SysDataDTO.class);
            
            SysData sysobj = doLookUpById(fromJson.tid);
            
            if(sysobj != null) 
            {
               
                      sysobj.sts =  Long.parseLong(doLookUpByParamNameStr("DELETE-RSC-STS"));
                      sysobj.statusStr = ResourceStsInfo.findDescbyID(sysobj.sts);
                      sysobj.lastUpatedDate = LocalDateTime.now();
                      sysobj.lastUpdatedBy = fromJson.actionBy;
                      sysobj.lastUpdatedByStr = fromJson.actionByStr;//.lastUpdatedByStr;// UserLog.doFindNameByTID(sysobj.lastUpdatedBy);
                      
                      SysData merge = Panache.getEntityManager().merge(sysobj);
                      
                      if (merge == null) 
                      {
                           return Response.status(ErrorCodes.DATABASE_ERROR).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(ErrorCodes.DATABASE_ERROR,prodOrDev)).build()).build();
          
                      }
                      else
                      {
                           return Response.ok().entity(merge.toJson()).build();
          
                      }
                
            }
            else
            {
                return Response.status(ErrorCodes.NO_RECORD_FOUND).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(ErrorCodes.NO_RECORD_FOUND,prodOrDev)).build()).build();
            }
         
        } 
        catch (HelloException e)
        {
            e.printStackTrace();
            return Response.status(e.getResponse().getStatus()).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(e.getResponse().getStatus(), 1))).build();
        }
        catch (Exception e) {
            
            
                LOGGER.error("- EXCEPTION SysDataHelper doApprove ", e);//e.printStackTrace();
            
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                     throw new HelloException(ErrorCodes.DATABASE_ERROR, e.getMessage(),prodOrDev);
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     throw new HelloException(ErrorCodes.IO_EXCEPTION, e.getMessage(),prodOrDev);
                }
                else
                {
                     throw new HelloException(ErrorCodes.SYSTEM_ERROR, e.getMessage(),prodOrDev);
                }
        
        
        }
        
    }
    
   */ 
    
    public PaymentSetupResponse doLookup(@Valid long tid) {
        //int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        //List<SysData> confs = new ArrayList<>();
        try 
        {
          
            PaymentSetup doLookUpByParamName = doLookUpById(tid);
            
            if (doLookUpByParamName != null) 
            {
                //confs.add(doLookUpByParamName);
                return new PaymentSetupResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doLookUpByParamName,null); 
            
            }
            else
            {
               
                return new PaymentSetupResponse(false,ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND), null,null); 
            
            
            }
           
        } 
        catch (Exception e) {
            
            
                log.error("- EXCEPTION SysDataHelper doLog ", e);//e.printStackTrace();
            
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                       return new PaymentSetupResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,e.getMessage()); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                   
                      return new PaymentSetupResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                  
                  return new PaymentSetupResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
        
        
        }
        
    }
    
    
    
     public PaymentSetupResponse doLoadList(@Valid  FilterRequest request) {
         //int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        ResourceHelper rh = new ResourceHelper();
        long status = -1;
        JsonArrayBuilder jar = jakarta.json.Json.createArrayBuilder();
        try 
        {
          
            FilterRequestObj fromJson = new FilterRequestObj(request);
           
             if(rh.strToLDT(fromJson.toDate).isBefore( rh.strToLDT(fromJson.fromDate)))
             {
                 
                    return new PaymentSetupResponse(false,ErrorCodes.DATE_DISPARITY, ErrorCodes.doErrorDesc(ErrorCodes.DATE_DISPARITY), null,null); 
            
             }
             else if((fromJson.pageId) < 1 || fromJson.pageSize < 1)// || !resourceH.isValidWithLen(fromJson.getPhoneNumberPri().replaceAll("\\+", ""), 13))
             {
               
                return new PaymentSetupResponse(false,ErrorCodes.INVALID_PAGE_ID_OR_FETCH_SIZE, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_PAGE_ID_OR_FETCH_SIZE), null,null); 
            
             }
             else
             {
                 
                     List<PaymentSetup> doLoadList =  doLoadPaymentsList(fromJson.fromDate, fromJson.toDate,  fromJson.searchParam, fromJson.status,  fromJson.pageId, fromJson.pageSize);
                   
                    if(doLoadList != null) 
                    {

                       return new PaymentSetupResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doLoadList,null); 
            

                    }
                    else
                    {
                        
                         return new PaymentSetupResponse(false,ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND), null,null); 
            
                    }
             }
         
        } 
        catch (Exception e) {
            
            
                log.error("- EXCEPTION SysDataHelper doLoadList ", e);//e.printStackTrace();
            
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                   
                    return new PaymentSetupResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                    
                     return new PaymentSetupResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                    
                     return new PaymentSetupResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
        
        
        }
        
    }
     
     
    public PaymentSetupResponse doLoadListTeller(@Valid  FilterRequest request) {
         //int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        ResourceHelper rh = new ResourceHelper();
        long status = -1;
        JsonArrayBuilder jar = jakarta.json.Json.createArrayBuilder();
        try 
        {
          
            FilterRequestObj fromJson = new FilterRequestObj(request);
           
             if(rh.strToLDT(fromJson.toDate).isBefore( rh.strToLDT(fromJson.fromDate)))
             {
                 
                    return new PaymentSetupResponse(false,ErrorCodes.DATE_DISPARITY, ErrorCodes.doErrorDesc(ErrorCodes.DATE_DISPARITY), null,null); 
            
             }
             else if((fromJson.pageId) < 1 || fromJson.pageSize < 1)// || !resourceH.isValidWithLen(fromJson.getPhoneNumberPri().replaceAll("\\+", ""), 13))
             {
               
                return new PaymentSetupResponse(false,ErrorCodes.INVALID_PAGE_ID_OR_FETCH_SIZE, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_PAGE_ID_OR_FETCH_SIZE), null,null); 
            
             }
             else
             {
                 
                     List<PaymentSetup> doLoadList =  doLoadPaymentsList(fromJson.fromDate, fromJson.toDate,  fromJson.searchParam, "ACTIVE",  fromJson.pageId, fromJson.pageSize);
                   
                    if(doLoadList != null) 
                    {

                       return new PaymentSetupResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doLoadList,null); 
            

                    }
                    else
                    {
                        
                         return new PaymentSetupResponse(false,ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND), null,null); 
            
                    }
             }
         
        } 
        catch (Exception e) {
            
            
                log.error("- EXCEPTION SysDataHelper doLoadList ", e);//e.printStackTrace();
            
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                   
                    return new PaymentSetupResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                    
                     return new PaymentSetupResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                    
                     return new PaymentSetupResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
        
        
        }
        
    }
     
     
     public PaymentSetupResponse doLoadAllConfig() {
        //int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
       
        JsonArrayBuilder jar = jakarta.json.Json.createArrayBuilder();
        try 
        {
          
                    List<PaymentSetup> doLoadList = PaymentSetup.listAll();//doLoadList(rh.strToLDT(fromJson.fromDate), rh.strToLDT(fromJson.toDate),  fromJson.status, fromJson.searchKey);
                 
                    log.info(" ++ doLoadAllConfig ++ "+doLoadList.size());
                    if (doLoadList != null) 
                    {

                        //doLoadList.stream().map(a->a.toJson()).forEach(f->jar.add(f));
                        
                        return new PaymentSetupResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doLoadList,null); 
            

                    }
                    else
                    {
                       
                        return new PaymentSetupResponse(false,ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND), null,null); 
            
                    }
             
         
        } 
        catch (Exception e) {
            
            
                log.error("- EXCEPTION SysDataHelper doLoadList ", e);//e.printStackTrace();
            
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                     return new PaymentSetupResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,e.getMessage()); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                   
                    return new PaymentSetupResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                      
                    return new PaymentSetupResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
        
        
        }
        
    }
    
    
    public PaymentSetup doLookUpByParamName(String paramName) {
        PaymentSetup obj = null;
        try 
        {
            
            obj  = paymentsRepo.doLookUpByName(paramName);// find("paramName",paramName).firstResult();
           
            
        } catch (Exception e) {
        
        }
      return obj;
    }
    
    public PaymentSetup doLookUpByParamNameStr(String paramName) {
        PaymentSetup obj = null;
        try 
        {
            
            obj  = paymentsRepo.doLookUpByName(paramName);// find("paramName",paramName).firstResult();
           
            
        } catch (Exception e) {
        
        }
      return obj==null?null:obj;
    }
    
   
    
    public PaymentSetup doLookUpById(long tid) {
        PaymentSetup obj = null;
        try 
        {
            
            obj  = paymentsRepo.findById(tid);// find("tid",tid).firstResult();
           
            
        } catch (Exception e) {
        
        }
      return obj;
    }
    
    public List<PaymentSetup> doLoadList(LocalDateTime fromLLdt, LocalDateTime toLdt,  String status, String paramName) {
       
        List<PaymentSetup> obj = new ArrayList<>();
        try 
        {
               if(status !=null && status.equals("ALL") && (paramName == null || paramName.trim().equals("")))
               {
                     obj = paymentsRepo.findByParams(fromLLdt, toLdt).list();// find("createdDate between ?1 and ?2 ",fromLLdt, toLdt).list();
                     
               }
               else if(status !=null && !status.equals("ALL") && (paramName == null || paramName.trim().equals("")))
               {
                    obj = paymentsRepo.findByParams(status, fromLLdt, toLdt).list();//  find("createdDate between ?1 and ?2  and sts = ?3 ",fromLLdt, toLdt, status).list();
                    
               }
               else if(status !=null && !status.equals("ALL") && (paramName != null && !paramName.trim().equals("")))
               {
                    obj = paymentsRepo.findByParams(status, fromLLdt, toLdt, paramName).list();// find("createdDate between ?1 and ?2  and sts = ?3 and (paramName like ?4  or  paramValue like ?4 )",fromLLdt, toLdt, status, paramName+'%').list();
                    
               }
           
            
        } catch (Exception e) {
            
            
            //e.printStackTrace();
            
            log.error("Exception @ doLoadList ",e);
        
        }
      return obj;
    }
    
    public PaymentProcessResponse doInitPayment(InitPaymentRequest request) {
        
        try 
        {
            InitPaymentRequestObj  paymentObj = new InitPaymentRequestObj(request);
            
            PaymentSetup doLookUpById = paymentsRepo.doLookUpById(paymentObj.paymentId);
            
            if(doLookUpById ==null)
            {
                throw new InvalidRequestException(String.format("Invalid Payment {%d}", paymentObj.paymentId));
            }
            
            log.info("-- PaymentProcessResponse doInitPayment --- doLookUpById "+doLookUpById);
            
            if(!doLookUpById.amountFixed  && doLookUpById.amount !=paymentObj.amount)
            {
                throw new InvalidRequestException(String.format("Invalid transaction Amount for payment for {%s} : {%s} : {%s}",
                            doLookUpById.productId, request.doPayeeName(), request.payeeId()));
            }
            
            String transDesc = LocalDate.now()+"/"+doLookUpById.productId+"/"+request.doPayeeName()+"/"+request.amount();
            TransactionLog doLog = transLogRepo.doLog(request, transDesc);
            //OtherPaymentParams(String username, String plainpassword, String transid, String tenantcode, String paymenttype)
            OtherPaymentParams otherPaymentParams = new OtherPaymentParams(sysDataRepo.doLookUpByNameStr("CU-USERNAME", "NA"), sysDataRepo.doLookUpByNameStr("CU-PASSWORD", "NA"), doLog.transactionId, sysDataRepo.doLookUpByNameStr("CU-TENANTCODE", "NA"), doLookUpById.productId);
           
            log.info("-- otherPaymentParams -- "+otherPaymentParams);
            
            ValidateCheckResponse doInitiateCUPayment = thirdPartyPay.doInitiateCUPayment(request, otherPaymentParams);
            
            log.info("--  doInitiateCUPayment -- "+doInitiateCUPayment);
            ///PaymentProcessResponse(boolean success, int errorCode, String errorDesc, Object data, String error)
            if(doInitiateCUPayment !=null && doInitiateCUPayment.payparameter() !=null && doInitiateCUPayment.payparameter().responsecode() !=null && doInitiateCUPayment.payparameter().responsecode().equals("09"))
            {
           
                  TransactionLog doSync = transLogRepo.doSync(doLog, doInitiateCUPayment);
                  
                  log.info("--TransactionLog  doSync --  "+doSync);
                  
                  return new PaymentProcessResponse(true, ErrorCodes.ACCEPTED, ErrorCodes.doErrorDesc(ErrorCodes.ACCEPTED), doLog.toPreviewObj(), doInitiateCUPayment.payparameter().responsename());
            }
            else  if(doInitiateCUPayment !=null && doInitiateCUPayment.payparameter() !=null && doInitiateCUPayment.payparameter().responsecode() !=null && !doInitiateCUPayment.payparameter().responsecode().equals("00"))
            {
                  TransactionLog doSync = transLogRepo.doSync(doLog, doInitiateCUPayment);
                  
                  log.info("-@@-TransactionLog  doSync --  "+doSync);
                
                 return new PaymentProcessResponse(false, ErrorCodes.PARTNER_VALIDATION_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.PARTNER_VALIDATION_ERROR), null, doInitiateCUPayment.payparameter().responsename());
          
            }
            else
            {
                 return new PaymentProcessResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, null);
          
            }
        
            
        }catch(InvalidRequestException e)
        {
            e.printStackTrace();
            return new PaymentProcessResponse(false, ErrorCodes.INVALID_AMOUNT, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_AMOUNT), null, null);
          
        }
        catch (Exception e) {
        
        
            log.error("Exception @ doInitPayment", e);
            
            return new PaymentProcessResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, e.getMessage());
        }
  
     // return null;
    }
    
    
    public List<PaymentSetup> doLoadPaymentsList(String  fromDate, String toDate,  String searchKey,  String status, int pageIndex, int pageSize) {
        List<PaymentSetup> obj = new ArrayList<>();
        JsonArrayBuilder jar = Json.createArrayBuilder();
        JsonObjectBuilder job = Json.createObjectBuilder();
        ResourceHelper rh = new ResourceHelper();
        List<PaymentSetup> query = new ArrayList<>();
        
        try 
        {
            
             long dataCounta = paymentsRepo.count();// SysData.doList(searchKey, status, fromDate,  toDate).size();// doPullWalletTransactionSummaryByTransactionTypeCount(fromDate, toDate, transType);
           
             log.info(" COUNTA doLoadConfigsList--- "+dataCounta+" pageSize "+pageSize+" status "+status);
           
             long lastPage = (dataCounta/pageSize) +1;
             job.add("lastPage", lastPage);
             job.add("totalRecords", dataCounta);
             
             
             if((status == null || status.equalsIgnoreCase("All")) &&  ("NA".equals(searchKey) || searchKey == null))
             {
                log.info("lastPage =status " + status);
                query = paymentsRepo.findByParams(rh.strToLDT(fromDate),  rh.strToLDT(toDate)).list();//List<SysData>)em.createNamedQuery(SysData.ALL_CONFIG_BY_DATE, SysData.class).setParameter("from", rh.strToLDT(fromDate)).setParameter("to", rh.strToLDT(toDate)).setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
                
                log.info("--  HERE 0 @ "+query.size());
             }
             if((rh.isNullorEmpty(status) && !status.equalsIgnoreCase("All")) && !rh.isNullorEmpty(searchKey) && !"NA".equals(searchKey))
             {
                 query = paymentsRepo.findByParams(rh.strToLDT(fromDate),  rh.strToLDT(toDate), searchKey).list(); //(List<SysData>)em.createNamedQuery(SysData.ALL_CONFIG_BY_DATE_AND_SEARCHKEY, SysData.class).setParameter("passed2", searchKey+'%').setParameter("from", rh.strToLDT(fromDate)).setParameter("to", rh.strToLDT(toDate)).setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
           
                 log.info("  HERE 1 "+query);
             
             }
             
             if((!rh.isNullorEmpty(status) && !status.equalsIgnoreCase("All")) && (!rh.isNullorEmpty(searchKey) || "NA".equals(searchKey) || searchKey == null))
             {
                log.info("lastPage =status " + status);
                query = paymentsRepo.findByParams(status, rh.strToLDT(fromDate),  rh.strToLDT(toDate)).list();// (List<SysData>)em.createNamedQuery(SysData.ALL_CONFIG_BY_DATE_AND_STATUS, SysData.class).setParameter("from", rh.strToLDT(fromDate)).setParameter("to", rh.strToLDT(toDate)).setParameter("passed", status).setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
                log.info("--  HERE 0A @ "+query.size());
             }
             if((!rh.isNullorEmpty(status) && !status.equalsIgnoreCase("All")) && !rh.isNullorEmpty(searchKey) && !"NA".equals(searchKey))
             {
                 query =  paymentsRepo.findByParams(status, rh.strToLDT(fromDate),  rh.strToLDT(toDate), searchKey).list();// (List<SysData>)em.createNamedQuery(SysData.ALL_CONFIG_BY_DATE_AND_SEARCHKEY_AND_STATUS, SysData.class).setParameter("from", rh.strToLDT(fromDate)).setParameter("to", rh.strToLDT(toDate)).setParameter("passed", status).setParameter("passed2", searchKey+'%').setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
                
                 System.out.println("  HERE 1A "+query);
             
             }
            
             System.out.println("query.size = " + query.size());
             query.stream().forEach(a->a.toJson());
             //query.stream().forEach(a->System.out.print("---"+a.toJsonX().build()));
             query.stream().forEach(a->obj.add(a));
             /*
             JsonArray jarray = jar.build();
           
             if(!jarray.isEmpty())
             {
               
                 job.add("data", jarray);
             }
             else
             {   
               
                 job.add("data", Json.createArrayBuilder().build());
             }
            */
            
        } catch (Exception e) {
            
            
            e.printStackTrace();
        
        }
      return obj;
    }
    public SysData syncObj(SysData  vcode)
    {
        SysData resp = null;
        try
        {
           resp = Panache.getEntityManager().merge(vcode);
           
        }
        catch(Exception ex)
        {  
            ex.printStackTrace();
        }
       return resp;
    }
    
    @Transactional
    public SysData createObj(SysData  objx)
    {
        SysData resp = null;
        try
        {
           resp  =  Panache.getEntityManager().merge(objx);
           
        }
        catch(Exception ex)
        {  
            ex.printStackTrace();
        }
       return resp;
    }
    

    public List<SysData> getAll() {
        return all;
    }

    public void setAll(List<SysData> all) {
        this.all = all;
    }
    
   public String getProps(final List<SysData> props, final String paraname)
   {      
       
       SysData orElse = props.stream().filter(a-> paraname.trim().equals(a.paramName)).findFirst().orElse(null);
       
       return (orElse == null)?"NA":orElse.paramValue;
   }
  
   
   public String getProps(final String paraname, String orDefaultTo)
   {      
       
       PaymentSetup orElse = paymentsRepo.doLookUpByName(paraname);// doReadAll().stream().filter(a-> paraname.trim().equals(a.paramName)).findFirst().orElse(null);
       
       return (orElse == null)?orDefaultTo:orElse.productId;
   }
   
    
}
