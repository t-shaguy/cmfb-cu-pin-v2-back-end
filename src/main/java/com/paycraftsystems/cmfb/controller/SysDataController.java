/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.controller;


//import com.paycraftsystems.resources.GenericFilterObject;


import com.paycraftsystems.cmfb.dto.ApproveOrDeleteRequest;
import com.paycraftsystems.cmfb.dto.ApproveOrDeleteRequestObj;
import com.paycraftsystems.cmfb.dto.FilterSysDataRequest;
import com.paycraftsystems.cmfb.dto.FilterSysDataRequestObj;
import com.paycraftsystems.cmfb.dto.SysDataEditRequest;
import com.paycraftsystems.cmfb.dto.SysDataEditRequestObj;
import com.paycraftsystems.cmfb.dto.SysDataRequest;
import com.paycraftsystems.cmfb.dto.SysDataRequestObj;
import com.paycraftsystems.cmfb.dto.response.SysDataResponse;
import com.paycraftsystems.cmfb.entities.SysData;
import com.paycraftsystems.cmfb.repositories.SysDataRepository;
import com.paycraftsystems.cmfb.resources.ResourceHelper;
import com.paycraftsystems.exceptions.CMFBException;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//import bind.JsonbBuilder;

/**
 *
 * @author paycraftsystems-i
 */

@ApplicationScoped
@Slf4j
public class SysDataController {
    
   // private static  Logger LOGGER = LoggerFactory.getLogger(SysDataController.class);
    
    
    @Inject 
    SysDataRepository sysDataRepository;
    
   
    public List<SysData>  all;
    
    
    
   // @Inject
   // EntityManager em;
    
    @PostConstruct
    public void doReadAll() throws Exception {
        List<SysData> query =  new ArrayList<>();
        try 
        {
               all = null;// sysDataRepository.listAll();//  SysData.listAll();// em.createNamedQuery(SysData.ALL, SysData.class);
              
        } catch (Exception e) {
        
             e.printStackTrace();
             
             throw new Exception(e);
             
            // return new ArrayList<SysData>(){};
        }
     //return query;
    }
    
    
    public long countAll()
    {
        return Long.parseLong("0");//sysDataRepository.findAll().count();//.doReadAll().size();
    }
    
    public int countAll(String query)
    {
        return  0;//em.createNamedQuery(query).getResultList().size();
    }
    
    
    public String doLookUpOrDefault(String  resp, String defaultTo) {
    
     return null;// sysDataRepository.doFindOrDefaultTo(resp,defaultTo);
    }
    
    
    public SysDataResponse doCreate(SysDataRequest jsonStr) 
    {
        int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        List<SysData> confs = new ArrayList<>();
        try 
        {
             SysDataRequestObj fromJson = new SysDataRequestObj(jsonStr);
            
           
                SysData doLog = sysDataRepository.doLog(fromJson); //doFindByDescription(jsonStr)
                
                if(doLog != null)
                {
                   // return Response.ok().entity(doLog.toJson()).build();
                     confs.add(doLog);
                     return new SysDataResponse(true, ErrorCodes.SUCCESSFUL,ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), confs, null);
                }
                else
                {
                    //return Response.status(ErrorCodes.DATABASE_ERROR).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doDesc(ErrorCodes.DATABASE_ERROR,prodOrDev)).build()).build();
                
                    return new SysDataResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
               
                }
                
           
        
        }
        catch (Exception e) {
        
                
                log.error("Exception @ Response doCreate ...",e);
                if(e.getCause() instanceof CMFBException)
                {
                    System.err.println(" ** AHA = " + e.getMessage()+" cause "+e.getCause());
                    CMFBException exp =   (CMFBException) e.getCause();
                    
                     return new SysDataResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,e.getMessage()); 
               
                }
                else if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                     //throw new HelloException(ErrorCodes.DATABASE_ERROR, e.getMessage(),prodOrDev);
                     
                      return new SysDataResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,e.getMessage()); 
               
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                    return new SysDataResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,e.getMessage()); 
               
                }
                else
                {
                    // throw new HelloException(ErrorCodes.SYSTEM_ERROR, e.getMessage(),prodOrDev);
                     
                      return new SysDataResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,e.getMessage()); 
               
                }
          }
        
    }
    
    public SysDataResponse doEdit(SysDataEditRequest jsonStr) {
        int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        List<SysData> confs = new ArrayList<>();
        try 
        {
                SysDataEditRequestObj fromJson = new SysDataEditRequestObj(jsonStr);
          
                SysData doLog = sysDataRepository.doSync(fromJson); //doFindByDescription(jsonStr)
            
                if(doLog != null)
                {
                    //return Response.ok().entity(doLog.toJson()).build();
                    confs.add(doLog);
                    return new SysDataResponse(false,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), confs,null); 
               
                }
                else
                {
                   // return Response.status(ErrorCodes.DATABASE_ERROR).entity(Json.createObjectBuilder().add("errorDesc",ErrorCodes.doDesc(ErrorCodes.DATABASE_ERROR,prodOrDev)).build()).build();
               
                   return new SysDataResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
               
                
                }
                
           
        
        }
        catch (CMFBException e) {
             e.printStackTrace();
            //return Response.status(e.getResponse().getStatus()).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(e.getResponse().getStatus(),prodOrDev)).build()).build();
       
             return new SysDataResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
               
        
        }
        catch (Exception e) {
        
               log.error("Exception @ Response sysdata doEdit ",e);
               
                if(e.getCause() instanceof CMFBException)
                {
                    //System.err.println(" ** AHA = " + e.getMessage()+" cause "+e.getCause());
                    CMFBException exp =   (CMFBException) e.getCause();
                     return new SysDataResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,e.getMessage()); 
            
                }
               
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                      return new SysDataResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,e.getMessage()); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     return new SysDataResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
                else
                {
                    
                    return new SysDataResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
        
        }
        
    }
    
    
    public SysDataResponse doDelete(ApproveOrDeleteRequest request) {
        int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        List<SysData> confs = new ArrayList<>();
        try 
        {
            //SysDataEditRequestObj fromJson = new SysDataEditRequestObj(jsonStr);
            //validate dto
            
                SysData doLog = sysDataRepository.doDelete(request); //doFindByDescription(jsonStr)
            
                if(doLog != null)
                {
                    confs.add(doLog);
                    return new SysDataResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), confs,null); 
            
                }
                else
                {
                  
                   return new SysDataResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                
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
                
                    return new SysDataResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,e.getMessage()); 
            
                
                }
               
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                   
                     return new SysDataResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                    // throw new HoptoolException(ErrorCodes.IO_EXCEPTION, e.getMessage(),prodOrDev);
                     
                      return new SysDataResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                      //throw new HoptoolException(ErrorCodes.SYSTEM_ERROR, e.getMessage(),prodOrDev);
                     
                      return new SysDataResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                }
        
            //e.printStackTrace();
            ///return  Response.status(ErrorCodes.SYSTEM_ERROR).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(ErrorCodes.SYSTEM_ERROR)).build()).build();
        }
        
    }
    
   @Transactional
    public SysDataResponse doApprove(@Valid ApproveOrDeleteRequest json) {
        int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        List<SysData> confs = new ArrayList<>();
        try 
        {
            ApproveOrDeleteRequestObj fromJson = new ApproveOrDeleteRequestObj(json);//.fromJson(json, SysDataDTO.class);
            
            
                      SysData merge = sysDataRepository.doApprove(json);
                      
                      if (merge == null) 
                      {
                         
                           return new SysDataResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                      }
                      else
                      {
                            ///return Response.ok().entity(merge.toJson()).build();
                           
                           confs.add(merge);
                           return new SysDataResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), confs,null); 
            
          
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
                
                    return new SysDataResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                
                }
                log.error("- EXCEPTION sysdata doApprove ", e);//e.printStackTrace();
            
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                   
                    return new SysDataResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     return new SysDataResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                  
                    return new SysDataResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
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
    
    public SysDataResponse doLookup(@Valid long tid) {
        int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        List<SysData> confs = new ArrayList<>();
        try 
        {
          
            SysData doLookUpByParamName = doLookUpById(tid);
            
            if (doLookUpByParamName != null) 
            {
                confs.add(doLookUpByParamName);
                return new SysDataResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), confs,null); 
            
            }
            else
            {
               
                return new SysDataResponse(false,ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND), null,null); 
            
            
            }
           
        } 
        catch (Exception e) {
            
            
                log.error("- EXCEPTION SysDataHelper doLog ", e);//e.printStackTrace();
            
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                       return new SysDataResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,e.getMessage()); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                   
                      return new SysDataResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                  
                  return new SysDataResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
        
        
        }
        
    }
    
    
     public SysDataResponse doLoadList(@Valid  FilterSysDataRequest request) {
         //int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        ResourceHelper rh = new ResourceHelper();
        long status = -1;
        JsonArrayBuilder jar = jakarta.json.Json.createArrayBuilder();
        try 
        {
          
            FilterSysDataRequestObj fromJson = new FilterSysDataRequestObj(request);
           
             if(rh.strToLDT(fromJson.toDate).isBefore( rh.strToLDT(fromJson.fromDate)))
             {
                 
                    return new SysDataResponse(false,ErrorCodes.DATE_DISPARITY, ErrorCodes.doErrorDesc(ErrorCodes.DATE_DISPARITY), null,null); 
            
             }
             else if((fromJson.pageIndex) < 1 || fromJson.pageSize < 1)// || !resourceH.isValidWithLen(fromJson.getPhoneNumberPri().replaceAll("\\+", ""), 13))
             {
               
                return new SysDataResponse(false,ErrorCodes.INVALID_PAGE_ID_OR_FETCH_SIZE, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_PAGE_ID_OR_FETCH_SIZE), null,null); 
            
             }
             else
             {
                 
                     List<SysData> doLoadList =  doLoadConfigsList(fromJson.fromDate, fromJson.toDate,  fromJson.searchParam, fromJson.status,  fromJson.pageIndex, fromJson.pageSize);
                   
                    if(doLoadList != null) 
                    {

                       return new SysDataResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doLoadList,null); 
            

                    }
                    else
                    {
                        
                         return new SysDataResponse(false,ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND), null,null); 
            
                    }
             }
         
        } 
        catch (Exception e) {
            
            
                log.error("- EXCEPTION SysDataHelper doLoadList ", e);//e.printStackTrace();
            
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                   
                    return new SysDataResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null,null); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                    
                     return new SysDataResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                    
                     return new SysDataResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
        
        
        }
        
    }
     
     
     public SysDataResponse doLoadAllConfig() {
        int prodOrDev = Integer.parseInt(doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
       
        JsonArrayBuilder jar = jakarta.json.Json.createArrayBuilder();
        try 
        {
          
                    List<SysData> doLoadList = SysData.listAll();//doLoadList(rh.strToLDT(fromJson.fromDate), rh.strToLDT(fromJson.toDate),  fromJson.status, fromJson.searchKey);
                 
                    log.info(" ++ doLoadAllConfig ++ "+doLoadList.size());
                    if (doLoadList != null) 
                    {

                        //doLoadList.stream().map(a->a.toJson()).forEach(f->jar.add(f));
                        
                        return new SysDataResponse(true,ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), doLoadList,null); 
            

                    }
                    else
                    {
                       
                        return new SysDataResponse(false,ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND), null,null); 
            
                    }
             
         
        } 
        catch (Exception e) {
            
            
                log.error("- EXCEPTION SysDataHelper doLoadList ", e);//e.printStackTrace();
            
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                     return new SysDataResponse(false,ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,e.getMessage()); 
            
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                   
                    return new SysDataResponse(false,ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null,null); 
            
                }
                else
                {
                      
                    return new SysDataResponse(false,ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null,null); 
            
                }
        
        
        }
        
    }
    
    
    public SysData doLookUpByParamName(String paramName) {
        SysData obj = null;
        try 
        {
            
            obj  = sysDataRepository.doLookUpByName(paramName);// find("paramName",paramName).firstResult();
           
            
        } catch (Exception e) {
        
        }
      return obj;
    }
    
    public String doLookUpByParamNameStr(String paramName) {
        SysData obj = null;
        try 
        {
            
            obj  = sysDataRepository.doLookUpByName(paramName);// find("paramName",paramName).firstResult();
           
            
        } catch (Exception e) {
        
        }
      return obj==null?"0":obj.paramValue;
    }
    
    public String doLookUpByParamNameStr(String paramName, String defaultTo) {
        System.out.println("defaultTo = " + paramName);
        SysData obj = null;
        try 
        {
            
            obj  =   sysDataRepository.doLookUpByName(paramName);//find("paramName",paramName).firstResult();
           
            
        } catch (Exception e) {
        
            e.printStackTrace();
        }
      return obj==null?defaultTo:obj.paramValue;
    }
    
    public SysData doLookUpById(long tid) {
        SysData obj = null;
        try 
        {
            
            obj  = sysDataRepository.findById(tid);// find("tid",tid).firstResult();
           
            
        } catch (Exception e) {
        
        }
      return obj;
    }
    
    public List<SysData> doLoadList(LocalDateTime fromLLdt, LocalDateTime toLdt,  String status, String paramName) {
       
        List<SysData> obj = new ArrayList<>();
        try 
        {
               if(status !=null && status.equals("ALL") && (paramName == null || paramName.trim().equals("")))
               {
                     obj = sysDataRepository.findByParams(fromLLdt, toLdt).list();// find("createdDate between ?1 and ?2 ",fromLLdt, toLdt).list();
                     
               }
               else if(status !=null && !status.equals("ALL") && (paramName == null || paramName.trim().equals("")))
               {
                    obj = sysDataRepository.findByParams(status, fromLLdt, toLdt).list();//  find("createdDate between ?1 and ?2  and sts = ?3 ",fromLLdt, toLdt, status).list();
                    
               }
               else if(status !=null && !status.equals("ALL") && (paramName != null && !paramName.trim().equals("")))
               {
                    obj = sysDataRepository.findByParams(status, fromLLdt, toLdt, paramName).list();// find("createdDate between ?1 and ?2  and sts = ?3 and (paramName like ?4  or  paramValue like ?4 )",fromLLdt, toLdt, status, paramName+'%').list();
                    
               }
           
            
        } catch (Exception e) {
            
            
            //e.printStackTrace();
            
            log.error("Exception @ doLoadList ",e);
        
        }
      return obj;
    }
    public List<SysData> doLoadConfigsList(String  fromDate, String toDate,  String searchKey,  String status, int pageIndex, int pageSize) {
        List<SysData> obj = new ArrayList<>();
        JsonArrayBuilder jar = Json.createArrayBuilder();
        JsonObjectBuilder job = Json.createObjectBuilder();
        ResourceHelper rh = new ResourceHelper();
        List<SysData> query = new ArrayList<>();
        
        try 
        {
            
             long dataCounta = sysDataRepository.count();// SysData.doList(searchKey, status, fromDate,  toDate).size();// doPullWalletTransactionSummaryByTransactionTypeCount(fromDate, toDate, transType);
           
             log.info(" COUNTA doLoadConfigsList--- "+dataCounta+" pageSize "+pageSize+" status "+status);
           
             long lastPage = (dataCounta/pageSize) +1;
             job.add("lastPage", lastPage);
             job.add("totalRecords", dataCounta);
             
             
             if((status == null || status.equalsIgnoreCase("All")) &&  ("NA".equals(searchKey) || searchKey == null))
             {
                log.info("lastPage =status " + status);
                query = sysDataRepository.findByParams(rh.strToLDT(fromDate),  rh.strToLDT(toDate)).list();//List<SysData>)em.createNamedQuery(SysData.ALL_CONFIG_BY_DATE, SysData.class).setParameter("from", rh.strToLDT(fromDate)).setParameter("to", rh.strToLDT(toDate)).setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
                
                log.info("--  HERE 0 @ "+query.size());
             }
             if((rh.isNullorEmpty(status) && !status.equalsIgnoreCase("All")) && !rh.isNullorEmpty(searchKey) && !"NA".equals(searchKey))
             {
                 query = sysDataRepository.findByParams(rh.strToLDT(fromDate),  rh.strToLDT(toDate), searchKey).list(); //(List<SysData>)em.createNamedQuery(SysData.ALL_CONFIG_BY_DATE_AND_SEARCHKEY, SysData.class).setParameter("passed2", searchKey+'%').setParameter("from", rh.strToLDT(fromDate)).setParameter("to", rh.strToLDT(toDate)).setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
           
                 log.info("  HERE 1 "+query);
             
             }
             
             if((!rh.isNullorEmpty(status) && !status.equalsIgnoreCase("All")) && (!rh.isNullorEmpty(searchKey) || "NA".equals(searchKey) || searchKey == null))
             {
                log.info("lastPage =status " + status);
                query = sysDataRepository.findByParams(status, rh.strToLDT(fromDate),  rh.strToLDT(toDate)).list();// (List<SysData>)em.createNamedQuery(SysData.ALL_CONFIG_BY_DATE_AND_STATUS, SysData.class).setParameter("from", rh.strToLDT(fromDate)).setParameter("to", rh.strToLDT(toDate)).setParameter("passed", status).setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
                log.info("--  HERE 0A @ "+query.size());
             }
             if((!rh.isNullorEmpty(status) && !status.equalsIgnoreCase("All")) && !rh.isNullorEmpty(searchKey) && !"NA".equals(searchKey))
             {
                 query =  sysDataRepository.findByParams(status, rh.strToLDT(fromDate),  rh.strToLDT(toDate), searchKey).list();// (List<SysData>)em.createNamedQuery(SysData.ALL_CONFIG_BY_DATE_AND_SEARCHKEY_AND_STATUS, SysData.class).setParameter("from", rh.strToLDT(fromDate)).setParameter("to", rh.strToLDT(toDate)).setParameter("passed", status).setParameter("passed2", searchKey+'%').setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
                
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
  
   public String getProps(final String paraname)
   {      
       
       SysData orElse = sysDataRepository.doLookUpByName(paraname);// doReadAll().stream().filter(a-> paraname.trim().equals(a.paramName)).findFirst().orElse(null);
       
       return (orElse == null)?"NA":orElse.paramValue;
   }
   
   public String getProps(final String paraname, String orDefaultTo)
   {      
       
       SysData orElse = sysDataRepository.doLookUpByName(paraname);// doReadAll().stream().filter(a-> paraname.trim().equals(a.paramName)).findFirst().orElse(null);
       
       return (orElse == null)?orDefaultTo:orElse.paramValue;
   }
   
    
}
