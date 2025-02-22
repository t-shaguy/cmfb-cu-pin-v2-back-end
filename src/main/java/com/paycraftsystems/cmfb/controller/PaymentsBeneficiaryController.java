/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.controller;

import com.paycraftsystems.cmfb.dto.ApproveOrDeleteRequest;
import com.paycraftsystems.cmfb.dto.ApproveOrDeleteRequestObj;
import com.paycraftsystems.cmfb.dto.FilterRequest;
import com.paycraftsystems.cmfb.dto.FilterRequestObj;
import com.paycraftsystems.cmfb.dto.PaymentBeneficiaryEditRequest;
import com.paycraftsystems.cmfb.dto.PaymentBeneficiaryEditRequestObj;
import com.paycraftsystems.cmfb.dto.PaymentBeneficiaryRequest;
import com.paycraftsystems.cmfb.dto.PaymentBeneficiaryRequestObj;
import com.paycraftsystems.cmfb.dto.response.PaymentBeneficiaryResponse;
import com.paycraftsystems.cmfb.dto.response.PaymentSetupResponse;
import com.paycraftsystems.cmfb.entities.PaymentBeneficiarySetup;
import com.paycraftsystems.cmfb.entities.PaymentSetup;
import com.paycraftsystems.cmfb.entities.SysData;
import com.paycraftsystems.cmfb.repositories.PaymentsBeneficiaryRepository;
import com.paycraftsystems.cmfb.repositories.SysDataRepository;
import com.paycraftsystems.cmfb.repositories.TransLogRepository;
import com.paycraftsystems.cmfb.resources.ResourceHelper;
import com.paycraftsystems.exceptions.CMFBException;
import com.paycraftsystems.resources.ErrorCodes;
import io.quarkus.hibernate.orm.panache.Panache;
import io.smallrye.common.constraint.NotNull;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
public class PaymentsBeneficiaryController {
    
    
    @Inject 
    PaymentsBeneficiaryRepository paymentsBenefRepo;
    
    @Inject 
    SysDataRepository sysDataRepo;
    
    @Inject
    TransLogRepository transLogRepo;
    
    @Inject
    ThirdPartyPaymentController thirdPartyPay;
    
   
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
    
    
    public PaymentBeneficiaryResponse doCreate(PaymentBeneficiaryRequest jsonStr) 
    {
        //int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        List<PaymentSetup> confs = new ArrayList<>();
        try 
        {
             PaymentBeneficiaryRequestObj fromJson = new PaymentBeneficiaryRequestObj(jsonStr);
            
           
                PaymentBeneficiarySetup doLog = paymentsBenefRepo.doLog(fromJson); //doFindByDescription(jsonStr)
                
                if(doLog != null)
                {
                   // return Response.ok().entity(doLog.toJson()).build();
                     //confs.add(doLog);
                     return new PaymentBeneficiaryResponse(true, ErrorCodes.SUCCESSFUL,ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doLog, null);
                }
                else
                {
                    //return Response.status(ErrorCodes.DATABASE_ERROR).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doDesc(ErrorCodes.DATABASE_ERROR,prodOrDev)).build()).build();
                
                    return new PaymentBeneficiaryResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
               
                }
                
           
        
        }
        catch (Exception e) {
        
                
                log.error("Exception @ Response doCreate ...",e);
                if(e.getCause() instanceof CMFBException)
                {
                    System.err.println(" ** AHA = " + e.getMessage()+" cause "+e.getCause());
                    CMFBException exp =   (CMFBException) e.getCause();
                    
                     return new PaymentBeneficiaryResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,e.getMessage()); 
               
                }
                else if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                     //throw new HelloException(ErrorCodes.DATABASE_ERROR, e.getMessage(),prodOrDev);
                     
                      return new PaymentBeneficiaryResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,e.getMessage()); 
               
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                    return new PaymentBeneficiaryResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,e.getMessage()); 
               
                }
                else
                {
                    // throw new HelloException(ErrorCodes.SYSTEM_ERROR, e.getMessage(),prodOrDev);
                     
                      return new PaymentBeneficiaryResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,e.getMessage()); 
               
                }
          }
        
    }
    
    public PaymentBeneficiaryResponse doEdit(@NotNull PaymentBeneficiaryEditRequest jsonStr) {
        
        log.info("-- PaymentBeneficiaryResponse doEdit -- "+jsonStr);
       // int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
       // List<SysData> confs = new ArrayList<>();
        try 
        {
                if(jsonStr !=null)
                {
                    PaymentBeneficiaryEditRequestObj fromJson = new PaymentBeneficiaryEditRequestObj(jsonStr);

                    PaymentBeneficiarySetup doLog = paymentsBenefRepo.doSync(fromJson); //doFindByDescription(jsonStr)

                    if(doLog != null)
                    {
                        return new PaymentBeneficiaryResponse(false,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doLog,null); 

                    }
                    else
                    {
                       // return Response.status(ErrorCodes.DATABASE_ERROR).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doDesc(ErrorCodes.DATABASE_ERROR,prodOrDev)).build()).build();

                       return new PaymentBeneficiaryResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 


                    }
                }
                else
                {
                     return new PaymentBeneficiaryResponse(false,ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null,null); 

                }
                
           
        
        }
        catch (CMFBException e) {
             e.printStackTrace();
            //return Response.status(e.getResponse().getStatus()).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(e.getResponse().getStatus(),prodOrDev)).build()).build();
       
             return new PaymentBeneficiaryResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
               
        
        }
        catch (Exception e) {
        
               log.error("Exception @ Response sysdata doEdit ",e);
               
                if(e.getCause() instanceof CMFBException)
                {
                    //System.err.println(" ** AHA = " + e.getMessage()+" cause "+e.getCause());
                    CMFBException exp =   (CMFBException) e.getCause();
                     return new PaymentBeneficiaryResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,e.getMessage()); 
            
                }
               
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                      return new PaymentBeneficiaryResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,e.getMessage()); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     return new PaymentBeneficiaryResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
                else
                {
                    
                    return new PaymentBeneficiaryResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
        
        }
        
    }
    
    
    public PaymentBeneficiaryResponse doDelete(ApproveOrDeleteRequest request) {
        //int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        //List<SysData> confs = new ArrayList<>();
        try 
        {
            //SysDataEditRequestObj fromJson = new SysDataEditRequestObj(jsonStr);
            //validate dto
            
                PaymentBeneficiarySetup doLog = paymentsBenefRepo.doDelete(request); //doFindByDescription(jsonStr)
            
                if(doLog != null)
                {
                    //confs.add(doLog);
                    return new PaymentBeneficiaryResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doLog,null); 
            
                }
                else
                {
                  
                   return new PaymentBeneficiaryResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                
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
                
                    return new PaymentBeneficiaryResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,e.getMessage()); 
            
                
                }
               
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                   
                     return new PaymentBeneficiaryResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                    // throw new HoptoolException(ErrorCodes.IO_EXCEPTION, e.getMessage(),prodOrDev);
                     
                      return new PaymentBeneficiaryResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                      //throw new HoptoolException(ErrorCodes.SYSTEM_ERROR, e.getMessage(),prodOrDev);
                     
                      return new PaymentBeneficiaryResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                }
        
            //e.printStackTrace();
            ///return  Response.status(ErrorCodes.SYSTEM_ERROR).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(ErrorCodes.SYSTEM_ERROR)).build()).build();
        }
        
    }
    
   @Transactional
    public PaymentBeneficiaryResponse doApprove(@Valid ApproveOrDeleteRequest json) {
        //int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        //List<SysData> confs = new ArrayList<>();
        try 
        {
            ApproveOrDeleteRequestObj fromJson = new ApproveOrDeleteRequestObj(json);//.fromJson(json, SysDataDTO.class);
            
            
                      PaymentBeneficiarySetup merge = paymentsBenefRepo.doApprove(json);
                      
                      if (merge == null) 
                      {
                         
                           return new PaymentBeneficiaryResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                      }
                      else
                      {
                          
                           return new PaymentBeneficiaryResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), merge,null); 
            
          
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
                
                    return new PaymentBeneficiaryResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                
                }
                log.error("- EXCEPTION sysdata doApprove ", e);//e.printStackTrace();
            
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                   
                    return new PaymentBeneficiaryResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     return new PaymentBeneficiaryResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                  
                    return new PaymentBeneficiaryResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
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
          
            PaymentBeneficiarySetup doLookUpByParamName = doLookUpById(tid);
            
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
                 
                     List<PaymentBeneficiarySetup> doLoadList =  doLoadPaymentsList(fromJson.fromDate, fromJson.toDate,  fromJson.searchParam, fromJson.status,  fromJson.pageId, fromJson.pageSize);
                   
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
    
    
    public PaymentBeneficiarySetup doLookUpByParamName(String paramName) {
        PaymentBeneficiarySetup obj = null;
        try 
        {
            
            obj  = paymentsBenefRepo.doLookUpByName(paramName);// find("paramName",paramName).firstResult();
           
            
        } catch (Exception e) {
        
        }
      return obj;
    }
    
    public PaymentBeneficiarySetup doLookUpByParamNameStr(String paramName) {
        PaymentBeneficiarySetup obj = null;
        try 
        {
            
            obj  = paymentsBenefRepo.doLookUpByName(paramName);// find("paramName",paramName).firstResult();
           
            
        } catch (Exception e) {
        
        }
      return obj==null?null:obj;
    }
    
   
    
    public PaymentBeneficiarySetup doLookUpById(long tid) {
        PaymentBeneficiarySetup obj = null;
        try 
        {
            
            obj  = paymentsBenefRepo.findById(tid);// find("tid",tid).firstResult();
           
            
        } catch (Exception e) {
        
        }
      return obj;
    }
    
    public List<PaymentBeneficiarySetup> doLoadList(LocalDateTime fromLLdt, LocalDateTime toLdt,  String status, String paramName) {
       
        List<PaymentBeneficiarySetup> obj = new ArrayList<>();
        try 
        {
               if(status !=null && status.equals("ALL") && (paramName == null || paramName.trim().equals("")))
               {
                     obj = paymentsBenefRepo.findByParams(fromLLdt, toLdt).list();// find("createdDate between ?1 and ?2 ",fromLLdt, toLdt).list();
                     
               }
               else if(status !=null && !status.equals("ALL") && (paramName == null || paramName.trim().equals("")))
               {
                    obj = paymentsBenefRepo.findByParams(status, fromLLdt, toLdt).list();//  find("createdDate between ?1 and ?2  and sts = ?3 ",fromLLdt, toLdt, status).list();
                    
               }
               else if(status !=null && !status.equals("ALL") && (paramName != null && !paramName.trim().equals("")))
               {
                    obj = paymentsBenefRepo.findByParams(status, fromLLdt, toLdt, paramName).list();// find("createdDate between ?1 and ?2  and sts = ?3 and (paramName like ?4  or  paramValue like ?4 )",fromLLdt, toLdt, status, paramName+'%').list();
                    
               }
           
            
        } catch (Exception e) {
            
            
            //e.printStackTrace();
            
            log.error("Exception @ doLoadList ",e);
        
        }
      return obj;
    }
    
   
    
    public List<PaymentBeneficiarySetup> doLoadPaymentsList(String  fromDate, String toDate,  String searchKey,  String status, int pageIndex, int pageSize) {
        List<PaymentBeneficiarySetup> obj = new ArrayList<>();
        JsonArrayBuilder jar = Json.createArrayBuilder();
        //JsonObjectBuilder job = Json.createObjectBuilder();
        ResourceHelper rh = new ResourceHelper();
        List<PaymentBeneficiarySetup> query = new ArrayList<>();
        
        try 
        {
            
             long dataCounta = paymentsBenefRepo.count();// SysData.doList(searchKey, status, fromDate,  toDate).size();// doPullWalletTransactionSummaryByTransactionTypeCount(fromDate, toDate, transType);
           
             log.info(" COUNTA doLoadConfigsList--- "+dataCounta+" pageSize "+pageSize+" status "+status);
           
             long lastPage = (dataCounta/pageSize) +1;
             //job.add("lastPage", lastPage);
            // job.add("totalRecords", dataCounta);
             
             
             if((status == null || status.equalsIgnoreCase("All")) &&  ("NA".equals(searchKey) || searchKey == null))
             {
                log.info("lastPage =status " + status);
                query = paymentsBenefRepo.findByParams(rh.strToLDT(fromDate),  rh.strToLDT(toDate)).list();//List<SysData>)em.createNamedQuery(SysData.ALL_CONFIG_BY_DATE, SysData.class).setParameter("from", rh.strToLDT(fromDate)).setParameter("to", rh.strToLDT(toDate)).setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
                
                log.info("--  HERE 0 @ "+query.size());
             }
             if((rh.isNullorEmpty(status) && !status.equalsIgnoreCase("All")) && !rh.isNullorEmpty(searchKey) && !"NA".equals(searchKey))
             {
                 query = paymentsBenefRepo.findByParams(rh.strToLDT(fromDate),  rh.strToLDT(toDate), searchKey).list(); //(List<SysData>)em.createNamedQuery(SysData.ALL_CONFIG_BY_DATE_AND_SEARCHKEY, SysData.class).setParameter("passed2", searchKey+'%').setParameter("from", rh.strToLDT(fromDate)).setParameter("to", rh.strToLDT(toDate)).setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
           
                 log.info("  HERE 1 "+query);
             
             }
             
             if((!rh.isNullorEmpty(status) && !status.equalsIgnoreCase("All")) && (!rh.isNullorEmpty(searchKey) || "NA".equals(searchKey) || searchKey == null))
             {
                log.info("lastPage =status " + status);
                query = paymentsBenefRepo.findByParams(status, rh.strToLDT(fromDate),  rh.strToLDT(toDate)).list();// (List<SysData>)em.createNamedQuery(SysData.ALL_CONFIG_BY_DATE_AND_STATUS, SysData.class).setParameter("from", rh.strToLDT(fromDate)).setParameter("to", rh.strToLDT(toDate)).setParameter("passed", status).setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
                log.info("--  HERE 0A @ "+query.size());
             }
             if((!rh.isNullorEmpty(status) && !status.equalsIgnoreCase("All")) && !rh.isNullorEmpty(searchKey) && !"NA".equals(searchKey))
             {
                 query =  paymentsBenefRepo.findByParams(status, rh.strToLDT(fromDate),  rh.strToLDT(toDate), searchKey).list();// (List<SysData>)em.createNamedQuery(SysData.ALL_CONFIG_BY_DATE_AND_SEARCHKEY_AND_STATUS, SysData.class).setParameter("from", rh.strToLDT(fromDate)).setParameter("to", rh.strToLDT(toDate)).setParameter("passed", status).setParameter("passed2", searchKey+'%').setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
                
                 System.out.println("  HERE 1A "+query);
             
             }
            
             System.out.println("query.size = " + query.size());
            // query.stream().forEach(a->a.toJson());
             //query.stream().forEach(a->System.out.print("---"+a.toJsonX().build()));
            // query.stream().forEach(a->obj.add(a));
            
            
        } catch (Exception e) {
            
            
            e.printStackTrace();
        
        }
      return query;
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
       
       PaymentBeneficiarySetup orElse = paymentsBenefRepo.doLookUpByName(paraname);// doReadAll().stream().filter(a-> paraname.trim().equals(a.paramName)).findFirst().orElse(null);
       
       return (orElse == null)?orDefaultTo:orElse.beneficiaryDesc;
   }
   
    
}
