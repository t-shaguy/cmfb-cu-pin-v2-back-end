/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.controller;

import com.paycraftsystems.cmfb.dto.FilterRequest;
import com.paycraftsystems.cmfb.dto.FilterRequestObj;
import com.paycraftsystems.cmfb.dto.response.PaymentSetupResponse;
import com.paycraftsystems.cmfb.dto.response.TransactionLogProcessResponse;
import com.paycraftsystems.cmfb.entities.PaymentSetup;
import com.paycraftsystems.cmfb.entities.TransactionLog;
import com.paycraftsystems.cmfb.entities.UserProfile;
import com.paycraftsystems.cmfb.repositories.PaymentsRepository;
import com.paycraftsystems.cmfb.repositories.SysDataRepository;
import com.paycraftsystems.cmfb.repositories.TransLogRepository;
import com.paycraftsystems.cmfb.repositories.UserRepository;
import com.paycraftsystems.cmfb.resources.ResourceHelper;
import com.paycraftsystems.resources.ErrorCodes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
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
public class TransactionsController {
    
    
    @Inject 
    PaymentsRepository paymentsRepo;
    
    @Inject 
    SysDataRepository sysDataRepo;
    
    @Inject
    TransLogRepository transLogRepo;
    
    
    @Inject
    UserRepository userRepo;
   
   
    
    public TransactionLogProcessResponse doLookup(@Valid long tid) {
      
        try 
        {
          
            TransactionLog doLookUpByParamName = doLookUpById(tid);
            
            if (doLookUpByParamName != null) 
            {
                //confs.add(doLookUpByParamName);
                return new TransactionLogProcessResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doLookUpByParamName,null); 
            
            }
            else
            {
                return new TransactionLogProcessResponse(false,ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND), null,null); 
            
            }
           
        } 
        catch (Exception e) {
            
            
                log.error("- EXCEPTION SysDataHelper doLog ", e);//e.printStackTrace();
            
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                       return new TransactionLogProcessResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,e.getMessage()); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                   
                      return new TransactionLogProcessResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                  
                  return new TransactionLogProcessResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
        
        
        }
        
    }
    
    
    
     public TransactionLogProcessResponse doLoadList(@Valid  FilterRequest request) {
         //int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        ResourceHelper rh = new ResourceHelper();
        long status = -1;
        List<TransactionLog> doTransList =  null;
        JsonArrayBuilder jar = jakarta.json.Json.createArrayBuilder();
        try 
        {
          
            FilterRequestObj fromJson = new FilterRequestObj(request);
           
             if(rh.strToLDT(fromJson.toDate).isBefore( rh.strToLDT(fromJson.fromDate)))
             {
                 
                    return new TransactionLogProcessResponse(false,ErrorCodes.DATE_DISPARITY, ErrorCodes.doErrorDesc(ErrorCodes.DATE_DISPARITY), null,null); 
            
             }
             else if((fromJson.pageId) < 1 || fromJson.pageSize < 1)// || !resourceH.isValidWithLen(fromJson.getPhoneNumberPri().replaceAll("\\+", ""), 13))
             {
               
                return new TransactionLogProcessResponse(false,ErrorCodes.INVALID_PAGE_ID_OR_FETCH_SIZE, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_PAGE_ID_OR_FETCH_SIZE), null,null); 
            
             }
             else
             {
                 
                     UserProfile doFindByTID = userRepo.doFindByTID(fromJson.actionBy);
                 
                    
                     
                   //  doTransList =  doLoadList(fromJson.fromDate, fromJson.toDate,  fromJson.searchParam, fromJson.status,  fromJson.pageId, fromJson.pageSize);
                  
                     
                    if(doTransList != null) 
                    {

                       return new TransactionLogProcessResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doTransList,null); 
            

                    }
                    else
                    {
                        
                         return new TransactionLogProcessResponse(false,ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND), null,null); 
            
                    }
             }
         
        } 
        catch (Exception e) {
            
            
                log.error("- EXCEPTION  TransactionLogProcessResponse doLoadList ", e);//e.printStackTrace();
            
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                   
                    return new TransactionLogProcessResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                    
                     return new TransactionLogProcessResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                    
                     return new TransactionLogProcessResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
        
        
        }
        
    }
     
     
    public TransactionLogProcessResponse doLoadListTellerTransactions(@Valid  FilterRequest request) {
         //int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        ResourceHelper rh = new ResourceHelper();
        long status = -1;
        JsonArrayBuilder jar = jakarta.json.Json.createArrayBuilder();
        List<TransactionLog> doLoadList = null;
        try 
        {
          
            FilterRequestObj fromJson = new FilterRequestObj(request);
           
             if(rh.strToLDT(fromJson.toDate).isBefore( rh.strToLDT(fromJson.fromDate)))
             {
                 
                    return new TransactionLogProcessResponse(false,ErrorCodes.DATE_DISPARITY, ErrorCodes.doErrorDesc(ErrorCodes.DATE_DISPARITY), null,null); 
            
             }
             else if((fromJson.pageId) < 1 || fromJson.pageSize < 1)// || !resourceH.isValidWithLen(fromJson.getPhoneNumberPri().replaceAll("\\+", ""), 13))
             {
               
                return new TransactionLogProcessResponse(false,ErrorCodes.INVALID_PAGE_ID_OR_FETCH_SIZE, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_PAGE_ID_OR_FETCH_SIZE), null,null); 
            
             }
             else
             {
                 
                     UserProfile doFindByTID = userRepo.doFindByTID(fromJson.actionBy);
                     
                     if(doFindByTID ==null)
                     {
                         return new TransactionLogProcessResponse(false,ErrorCodes.TRANSACTION_FORBIDDEN, ErrorCodes.doErrorDesc(ErrorCodes.TRANSACTION_FORBIDDEN), null,null); 
             
                     }
                     if(doFindByTID.userRoleStr !=null && doFindByTID.userRoleStr.equals("TELLER"))
                     {
                         doLoadList =  doLoadTransactionsList(fromJson.fromDate, fromJson.toDate,  fromJson.searchParam, fromJson.status,  fromJson.pageId, fromJson.pageSize, doFindByTID.tid, true);
                     }
                     else
                     {
                         doLoadList =  doLoadTransactionsList(fromJson.fromDate, fromJson.toDate,  fromJson.searchParam, fromJson.status,  fromJson.pageId, fromJson.pageSize, doFindByTID.tid, false);
                   
                     }
                    
                       
                       
                    if(doLoadList != null) 
                    {

                       return new TransactionLogProcessResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doLoadList,null); 
            

                    }
                    else
                    {
                        
                         return new TransactionLogProcessResponse(false,ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND), null,null); 
            
                    }
             }
         
        } 
        catch (Exception e) {
            
            
                log.error("- EXCEPTION TransactionLogProcessResponse doLoadListTeller ", e);//e.printStackTrace();
            
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                   
                    return new TransactionLogProcessResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                    
                     return new TransactionLogProcessResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                    
                     return new TransactionLogProcessResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
        
        
        }
        
    }
     
     
     public TransactionLogProcessResponse doLoadAll() {
        //int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
       
        JsonArrayBuilder jar = jakarta.json.Json.createArrayBuilder();
        try 
        {
          
                    List<TransactionLog> doLoadList = TransactionLog.listAll();//doLoadList(rh.strToLDT(fromJson.fromDate), rh.strToLDT(fromJson.toDate),  fromJson.status, fromJson.searchKey);
                 
                    log.info(" ++ TransactionLog ++ "+doLoadList.size());
                    if (doLoadList != null) 
                    {

                        return new TransactionLogProcessResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doLoadList,null); 
            

                    }
                    else
                    {
                       
                        return new TransactionLogProcessResponse(false,ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND), null,null); 
            
                    }
             
         
        } 
        catch (Exception e) {
            
            
                log.error("- EXCEPTION TransactionLogProcessResponse doLoadAll ", e);//e.printStackTrace();
            
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                     return new TransactionLogProcessResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,e.getMessage()); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                   
                    return new TransactionLogProcessResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                      
                    return new TransactionLogProcessResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
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
    
   
    
    public TransactionLog doLookUpById(long tid) {
        TransactionLog obj = null;
        try 
        {
            
            obj  = transLogRepo.findById(tid);// find("tid",tid).firstResult();
           
            
        } catch (Exception e) {
        
        }
      return obj;
    }
    
    public List<TransactionLog> doLoadList(LocalDateTime fromLLdt, LocalDateTime toLdt,  String status, String paramName) {
       
        List<TransactionLog> obj = new ArrayList<>();
        try 
        {
               if(status !=null && status.equals("ALL") && (paramName == null || paramName.trim().equals("")))
               {
                     obj = transLogRepo.findByParams(fromLLdt, toLdt).list();// find("createdDate between ?1 and ?2 ",fromLLdt, toLdt).list();
                     
               }
               else if(status !=null && !status.equals("ALL") && (paramName == null || paramName.trim().equals("")))
               {
                    obj = transLogRepo.findByParams(status, fromLLdt, toLdt).list();//  find("createdDate between ?1 and ?2  and sts = ?3 ",fromLLdt, toLdt, status).list();
                    
               }
               else if(status !=null && !status.equals("ALL") && (paramName != null && !paramName.trim().equals("")))
               {
                    obj = transLogRepo.findByParams(status, fromLLdt, toLdt, paramName).list();// find("createdDate between ?1 and ?2  and sts = ?3 and (paramName like ?4  or  paramValue like ?4 )",fromLLdt, toLdt, status, paramName+'%').list();
                    
               }
           
            
        } catch (Exception e) {
            
            
            //e.printStackTrace();
            
            log.error("Exception @ doLoadList ",e);
        
        }
      return obj;
    }
    
    public List<TransactionLog> doLoadTransactionsList(String  fromDate, String toDate,  String searchKey,  String status, int pageIndex, int pageSize, long tellerId, boolean isTeller) {
        List<TransactionLog> obj = new ArrayList<>();
        JsonArrayBuilder jar = Json.createArrayBuilder();
        JsonObjectBuilder job = Json.createObjectBuilder();
        ResourceHelper rh = new ResourceHelper();
        List<TransactionLog> query = new ArrayList<>();
        
        try 
        {
            
             long dataCounta = transLogRepo.count();
           
             log.info(" COUNTA doLoadTransactionsList--- "+dataCounta+" pageSize "+pageSize+" status "+status);
           
             long lastPage = (dataCounta/pageSize) +1;
             job.add("lastPage", lastPage);
             job.add("totalRecords", dataCounta);
             
             
             if((status == null || status.equalsIgnoreCase("All")) &&  ("NA".equals(searchKey) || searchKey == null))
             {
                log.info("lastPage =status " + status);
                query = transLogRepo.findByParams(rh.strToLDT(fromDate),  rh.strToLDT(toDate)).list();//List<SysData>)em.createNamedQuery(SysData.ALL_CONFIG_BY_DATE, SysData.class).setParameter("from", rh.strToLDT(fromDate)).setParameter("to", rh.strToLDT(toDate)).setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
                
                log.info("--  HERE 0 @ "+query.size());
             }
             if((rh.isNullorEmpty(status) && !status.equalsIgnoreCase("All")) && !rh.isNullorEmpty(searchKey) && !"NA".equals(searchKey))
             {
                 query = transLogRepo.findByParams(rh.strToLDT(fromDate),  rh.strToLDT(toDate), searchKey).list(); //(List<SysData>)em.createNamedQuery(SysData.ALL_CONFIG_BY_DATE_AND_SEARCHKEY, SysData.class).setParameter("passed2", searchKey+'%').setParameter("from", rh.strToLDT(fromDate)).setParameter("to", rh.strToLDT(toDate)).setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
           
                 log.info("  HERE 1 "+query);
             
             }
             
             if((!rh.isNullorEmpty(status) && !status.equalsIgnoreCase("All")) && (!rh.isNullorEmpty(searchKey) || "NA".equals(searchKey) || searchKey == null))
             {
                log.info("lastPage =status " + status);
                query = transLogRepo.findByParams(status, rh.strToLDT(fromDate),  rh.strToLDT(toDate)).list();// (List<SysData>)em.createNamedQuery(SysData.ALL_CONFIG_BY_DATE_AND_STATUS, SysData.class).setParameter("from", rh.strToLDT(fromDate)).setParameter("to", rh.strToLDT(toDate)).setParameter("passed", status).setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
                log.info("--  HERE 0A @ "+query.size());
             }
             if((!rh.isNullorEmpty(status) && !status.equalsIgnoreCase("All")) && !rh.isNullorEmpty(searchKey) && !"NA".equals(searchKey))
             {
                 query =  transLogRepo.findByParams(status, rh.strToLDT(fromDate),  rh.strToLDT(toDate), searchKey).list();// (List<SysData>)em.createNamedQuery(SysData.ALL_CONFIG_BY_DATE_AND_SEARCHKEY_AND_STATUS, SysData.class).setParameter("from", rh.strToLDT(fromDate)).setParameter("to", rh.strToLDT(toDate)).setParameter("passed", status).setParameter("passed2", searchKey+'%').setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
                
                 System.out.println("  HERE 1A "+query);
             
             }
            
             System.out.println("query.size = " + query.size());
            
            
        } catch (Exception e) {
            
            
            e.printStackTrace();
        
        }
      return query;
    }
   
   
    
}
