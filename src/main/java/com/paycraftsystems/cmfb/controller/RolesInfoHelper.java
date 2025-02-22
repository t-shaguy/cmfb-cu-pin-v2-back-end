/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.controller;







import com.paycraftsystems.cmfb.dto.FilterRequest;
import com.paycraftsystems.cmfb.dto.FilterRequestObj;
import com.paycraftsystems.cmfb.dto.GenericFilterObject;
import com.paycraftsystems.cmfb.dto.RolesInfoDTO;
import com.paycraftsystems.cmfb.dto.RolesInfoEditRequest;
import com.paycraftsystems.cmfb.dto.RolesInfoEditRequestObj;
import com.paycraftsystems.cmfb.dto.RolesInfoRequest;
import com.paycraftsystems.cmfb.dto.RolesInfoRequestObj;
import com.paycraftsystems.cmfb.dto.response.RolesResponse;
import com.paycraftsystems.cmfb.dto.response.UserProfileResponse;
import com.paycraftsystems.cmfb.entities.RolesInfo;
import com.paycraftsystems.cmfb.entities.UserProfile;
import com.paycraftsystems.cmfb.repositories.RolesRepository;
import com.paycraftsystems.exceptions.CMFBException;
import com.paycraftsystems.resources.ErrorCodes;
import com.paycraftsystems.resources.ValidationHelper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
//import jakarta.json.bind.JsonbBuilder;
//import jakarta.json.bind.JsonbException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author taysayshaguy
 */

@ApplicationScoped
@Slf4j
public class RolesInfoHelper
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(RolesInfoHelper.class);
   
   
    @Inject
    ESEQHelper eSEQHelper;
    
    @Inject
    SysDataController sysDataHelper;
    
    @Inject
    UserController userController;
    
    @Inject
    RolesRepository rolesRepository;
    
   
    
    ValidationHelper rh = new ValidationHelper();
   
   
    public List<RolesInfo>  all;
    
    
    
    @PostConstruct
    //@Transactional
    public void doReadAll() {
        List<RolesInfo> query =  new ArrayList<>();
        try 
        {
               all = RolesInfo.doFindAll();// em.createNamedQuery(RolesInfo.ALL, RolesInfo.class);
              
        } catch (Exception e) {
        
             e.printStackTrace();
             
             //return new ArrayList<RolesInfo>(){};
        }
     //return query;
    }
    
    
    public int countAll()
    {
        return rolesRepository.listAll().size();
    }
    
    
    public RolesResponse doLookupByCode(String code) {
        System.out.println(" doLookupByCode --code = " +  code);
        RolesInfo obj = null;
        List<RolesInfo> roles = new ArrayList<>();
        try 
        {
           obj = rolesRepository.doFindByCode(code);// RolesInfo.doFindByCode(username);// em.createNamedQuery(RolesInfo.BY_CODE, RolesInfo.class).setParameter("passed", username);
             
           if(obj !=null)
           {
                roles.add(obj);
             return  new RolesResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), roles, null);
           }
           else
           {
             return   new RolesResponse(false, ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND), null, null);
           
           }
            
        } catch (Exception e) {
        
          e.printStackTrace();
          
          return new RolesResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, e.getMessage());
           
        
        }
     
        
      //  return new RolesResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, null);
    
    }
    
    public RolesResponse doLookupById(long tid) {
        System.out.println(" doLookupByCode --tid = " +  tid);
        RolesInfo obj = null;
        List<RolesInfo> roles = new ArrayList<>();
        try 
        {
           obj = rolesRepository.doFindById(tid);// RolesInfo.doFindByCode(username);// em.createNamedQuery(RolesInfo.BY_CODE, RolesInfo.class).setParameter("passed", username);
          
           if(obj !=null)
           {
                roles.add(obj);
               new RolesResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), roles, null);
           }
           else
           {
               new RolesResponse(false, ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND), null, null);
           
           }
           
        } catch (Exception e) {
        
          e.printStackTrace();
          
          new RolesResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR)+"-"+e.getMessage(), null, null);
           
        
        }
     
        //return obj; 
        
       return  new RolesResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, null);
      
    }
   
    
    @Transactional
    public RolesResponse doLog(@Valid RolesInfoRequest request) {
        int prodOrDev = Integer.parseInt(sysDataHelper.doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
       
        int status = 0;
        
        List<RolesInfo> roles = new ArrayList<>();
        try 
        {
            RolesInfoRequestObj fromJson = new RolesInfoRequestObj(request);
            
            LOGGER.info(" rolesinfo fromJson = " + fromJson);
            
            
            
            RolesResponse doLookupByName = doLookupByName(fromJson.roleName);
            
            if(doLookupByName != null  & doLookupByName.data() !=null & doLookupByName.data().size() > 0)
            {
                
                  return new RolesResponse(false, ErrorCodes.DUPLICATE_TRANSACTION, ErrorCodes.doErrorDesc(ErrorCodes.DUPLICATE_TRANSACTION), null, null);
        
            }
            
            
            UserProfile doFindById = userController.doFindUser(fromJson.actionBy);
            
            if(doFindById == null)
            {
                
                    return new RolesResponse(false, ErrorCodes.INVALID_USER, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USER), null, null);
        
            }
            


                                 String genCode =  eSEQHelper.genCode("ROLE-CODE", 2);// eSEQHelper.genCode("ROLE-CODE", 2);
                                 
                                 LOGGER.info("  * do LogRole genCode = " + genCode);

                                 RolesInfo obj = new RolesInfo();
                                 obj.roleCode = genCode;
                                 obj.roleName = fromJson.roleName;
                                 obj.roleDesc = fromJson.roleDesc;
                                 obj.createdBy = doFindById.tid;
                                 obj.createdByStr = doFindById.emailAddress;//.userName;
                                 obj.createdDate = LocalDateTime.now();//new Date());
                                 obj.status = Long.parseLong(sysDataHelper.getProps("DEFAULT-RSC-STS", "3"));// new ResourceStsInfo(Long.parseLong(SysDataHelper.getProps("DEFAULT-RSC-STS", "3"))));
                                 //obj.statusStr = ResourceStsInfo.findDescbyID(obj.status);
                                 RolesInfo merge = RolesInfo.doLog(obj);
                                 
                                 if(merge != null)
                                 {
                                      roles.add(merge);
                                      return new RolesResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), roles, null);
        
                               
                                 }
                                 else
                                 {
                                   
                                     return new RolesResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null, null);
        
                                 }
           
        }/*
        catch (JsonbException e) {
        
             e.printStackTrace();
            return Response.status(ErrorCodes.FORMAT_ERROR).build();
        }*/
        catch (Exception e) {
        
              LOGGER.info(" @--- doCreateRole -- ", e);
              if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                    
                      return new RolesResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null, e.getMessage());
        
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     
                      return new RolesResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null, e.getMessage());
        
                }
                else
                {
                    
                      return new RolesResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, e.getMessage());
        
                }
            
        }
        
    }
    
    
    @Transactional
    public Response doLogx(@Valid JsonObject json) {
       
        int prodOrDev = Integer.parseInt(sysDataHelper.doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
       
        int status = 0;
        try 
        {
          

                                 String genCode =  eSEQHelper.genCode("ROLE-CODE", 2);// eSEQHelper.genCode("ROLE-CODE", 2);
                                 
                                 LOGGER.info("  * do LogRole genCode = " + genCode);
                                 
                               
                                 return Response.ok().build();
           
        }/*
        catch (JsonbException e) {
        
             e.printStackTrace();
            return Response.status(ErrorCodes.FORMAT_ERROR).build();
        }*/
        catch (Exception e) {
        
               LOGGER.info(" @--- doCreateRole -- ", e);
               if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                     throw new CMFBException(ErrorCodes.DATABASE_ERROR, e.getMessage(), prodOrDev);
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     throw new CMFBException(ErrorCodes.IO_EXCEPTION, e.getMessage(), prodOrDev);
                }
                else
                {
                     throw new CMFBException(ErrorCodes.SYSTEM_ERROR, e.getMessage(), prodOrDev);
                }
            
        }
        
    }
    
    
    @Transactional
    public RolesResponse doSync(@Valid RolesInfoEditRequest request) {
        //System.out.println(" ---------- ###### json = " +  json);
        int prodOrDev = Integer.parseInt(sysDataHelper.doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        List<RolesInfo> roles = new ArrayList<>();
        try 
        {
               
            RolesInfoEditRequestObj fromJson = new RolesInfoEditRequestObj(request);
            
            LOGGER.info(" ### fromJson = " + fromJson);
            
            RolesInfo doLookupByCode = RolesInfo.findById(fromJson.tid);
            
            if(doLookupByCode == null)
            {
                  
               return  new  RolesResponse(false, ErrorCodes.INVALID_ENTITY, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_ENTITY),null, null);
                              
            }
            
            
            
                   
                                 doLookupByCode.updatedBy = fromJson.actionBy;
                                 doLookupByCode.updatedByStr = userController.doFindUsernameById(doLookupByCode.updatedBy);
                                 doLookupByCode.roleDesc = fromJson.roleDesc;
                                 doLookupByCode.updateDate = LocalDateTime.now(); 
                                 doLookupByCode.status = Long.parseLong(sysDataHelper.getProps("DEFAULT-RSC-STS", "3"));
                                 RolesInfo merge = RolesInfo.doLog(doLookupByCode);
                                 
                                 if(merge != null)
                                 {
                                       
                                     roles.add(merge);
                                     
                                     return  new  RolesResponse(false, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL),roles, null);
                             
                               
                                 }
                                 else
                                 {
                                     return  new  RolesResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR),null, null);
                                 }
           
        }/*
        catch (JsonbException e) {
        
              LOGGER.info("-- JsonbException e ",e);
            return Response.status(ErrorCodes.FORMAT_ERROR).build();
        }*/
        catch (Exception e) {
        
               LOGGER.info(" @--- Exception doSync -- ", e);
                
               if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                  
                     return  new  RolesResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR),null, null);
           
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                 
                    return  new  RolesResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION),null, null);
           
                }
                else
                {
                  
                      return  new  RolesResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR),null, null);
           
                }
            
        }
        
    }
    
    @Transactional
    public RolesResponse doDelete(@Valid  RolesInfoEditRequest request) {
        LOGGER.info(" ---------- ###### json = " +  request);
        int prodOrDev = Integer.parseInt(sysDataHelper.doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        List<RolesInfo>  roles = new ArrayList<>();
        try 
        {
               
            RolesInfoEditRequestObj fromJson = new RolesInfoEditRequestObj(request);
            
            LOGGER.info(" ### fromJson = " + fromJson);
            
            RolesInfo doLookupByCode = RolesInfo.findById(fromJson.tid);
            
            if(doLookupByCode == null)
            {  
                    
                return  new  RolesResponse(false, ErrorCodes.INVALID_ENTITY, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_ENTITY),null, null);
           
            }
            
            
                   
                                 doLookupByCode.updatedBy = fromJson.actionBy;
                                 doLookupByCode.updatedByStr = userController.doFindUsernameById(doLookupByCode.updatedBy);
                                 doLookupByCode.updateDate = LocalDateTime.now();//new Date());
                                 doLookupByCode.status = Long.parseLong(sysDataHelper.getProps("DELETE-RSC-STS", "3"));
                                 //doLookupByCode.statusStr = ResourceStsInfo.findDescbyID(doLookupByCode.status);
                                 RolesInfo merge = RolesInfo.doLog(doLookupByCode);
                                 
                                 if(merge != null)
                                 {
                                  
                                     roles.add(merge);
                                     
                                      return  new  RolesResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL),roles, null);
           
                               
                                 }
                                 else
                                 {
                                  
                                     return  new  RolesResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR),null, null);
           
                                 
                                 }
           
        } /*
        catch (JsonbException e) {
        
            // e.printStackTrace();
             LOGGER.info(" @--- JsonbException doDelete -- ", e);
            return Response.status(ErrorCodes.FORMAT_ERROR).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR, prodOrDev)).build()).build();
        }*/
        catch (Exception e) {
        
               LOGGER.info(" @--- Exception doDelete -- ", e);
                
               if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                  
                     return  new  RolesResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR),null, null);
           
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                 
                    return  new  RolesResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION),null, null);
           
                }
                else
                {
                  
                      return  new  RolesResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR),null, null);
           
                }
            
        }
        
    }
    
    
    @Transactional
    public RolesResponse doApprove(@Valid RolesInfoEditRequest request) {
        LOGGER.info(" ---------- ###### json = " +  request);
        int prodOrDev = Integer.parseInt(sysDataHelper.doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        
        List<RolesInfo> roles = new ArrayList<>();
       
        try 
        {
               
            RolesInfoEditRequestObj fromJson = new RolesInfoEditRequestObj(request);//, RolesInfoDTO.class);
            
            LOGGER.info(" ###  RolesInfoDTO doApprove fromJson = " + fromJson);
            
            RolesInfo doLookupByCode = RolesInfo.findById(fromJson.tid);
            
            if(doLookupByCode == null)
            {
               
                return  new  RolesResponse(false, ErrorCodes.INVALID_ENTITY, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_ENTITY),null, null);
                
                //RolesResponse(boolean success, int errorCode, String respDesc, List<RolesInfo> data, String error)
            
            }
           
            if(doLookupByCode.status  == 3)
            {
                 return  new  RolesResponse(false, ErrorCodes.FORBIDDEN_ACTION, ErrorCodes.doErrorDesc(ErrorCodes.FORBIDDEN_ACTION),null, null);
             }
            
                   
                                 doLookupByCode.updatedBy = fromJson.actionBy;
                                 doLookupByCode.updatedByStr = userController.doFindUsernameById(doLookupByCode.updatedBy);
                                 doLookupByCode.updateDate = LocalDateTime.now();
                                 doLookupByCode.status = Long.parseLong(sysDataHelper.getProps("DEFAULT-RSC-APPROVAL-STS", "3"));
                                 //doLookupByCode.statusStr = ResourceStsInfo.findDescbyID(doLookupByCode.status);
                                 
                                 
                                 RolesInfo merge = RolesInfo.doLog(doLookupByCode);
                                 
                                 if(merge != null)
                                 {
                                      roles.add(merge);
                                     return  new  RolesResponse(true, ErrorCodes.ACCEPTED, ErrorCodes.doErrorDesc(ErrorCodes.ACCEPTED),roles, null);
                                 }
                                 else
                                 {
                                  
                                      return  new  RolesResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR),null, null);
           
                                 
                                 }
           
        }/* 
        catch (JsonbException e) {
        
             LOGGER.info(" @--- JsonbException doApprove -- ", e);
            return Response.status(ErrorCodes.FORMAT_ERROR).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR, prodOrDev)).build()).build();
        }*/
        catch (Exception e) {
        
           //e.printStackTrace();
           // return Response.status(ErrorCodes.SYSTEM_ERROR).build();
            
            
              LOGGER.info(" @--- Exception doApprove -- ", e);
                
               if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                  
                    return  new  RolesResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR)+"-"+e.getMessage(),null, null);
           
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                   
                     return  new  RolesResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION),null, null);
           
                }
                else
                {
                    
                     return  new  RolesResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR),null, null);
           
                }
            
        }
        
    }
    
   

    public List<RolesInfo> getAll() {
        return all;
    }

    public void setAll(List<RolesInfo> all) {
        this.all = all;
    }
    
    
    
    public List<JsonObject> toAllJson() {
        List<JsonObject> collect = new ArrayList<>();
        try 
        {
            return  getAll().stream().map(a->a.toJson()).collect(toList());
        } 
        catch (Exception e) {
        
        }
     return collect;
    }
    
   
  
  
    
    @Transactional
    public RolesResponse doList(@Valid FilterRequest request) {
        int prodOrDev = Integer.parseInt(sysDataHelper.doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
       
        long status = -1;
        List<RolesInfo> resultList = new ArrayList<>();
        try 
        {
            
                FilterRequestObj fromJson = new FilterRequestObj(request);//json.toString(), GenericFilterObject.class);
                
                LOGGER.info("   -- doList -- "+fromJson);
                
                if(rh.strToLDT(fromJson.toDate).isBefore( rh.strToLDT(fromJson.fromDate)))
                {
                   
                    return new RolesResponse(true, ErrorCodes.DATE_DISPARITY, ErrorCodes.doErrorDesc(ErrorCodes.DATE_DISPARITY), resultList,  null);
                     
                }
                else
                {
                       
                       //  status = rh.toRolesPrivilegeStatus(fromJson.controlCode);
                         
                         resultList = rolesRepository.doFindByDateRangeAndStatus(rh.strToLDT(fromJson.fromDate), rh.strToLDT(fromJson.toDate), status);
                      

                        if(resultList != null && resultList.size() > 0)
                        {
                           
                             return new RolesResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), resultList,  null);
                       
                        }  
                        else if(resultList != null && resultList.size()  == 0)
                        {
                          
                            return new RolesResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), resultList,  null);
                        }  
                        else
                        {
                            
                            return new RolesResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null, null);
                        }
                    
                         
                    
                }
                
              
           
        }/* 
        catch (JsonbException e) {
        
             e.printStackTrace();
            return Response.status(ErrorCodes.FORMAT_ERROR).build();
        }*/
        catch (Exception e) {
        
           e.printStackTrace();
         
            return new RolesResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, null);
                       
            
        }
        
    }
    
    @Transactional
    public RolesResponse doListAllRoles() {
        //System.out.println(" called ...... ");
         List<RolesInfo> resultList = new ArrayList<>();
        try 
        {
            
               
                       
                        resultList = RolesInfo.doFindAll();//.doFindByDateRangeAndStatus(rh.strToLDT(fromJson.fromDate), rh.strToLDT(fromJson.toDate), fromJson.status);
                      

                        if(resultList != null && resultList.size() > 0)
                        {
                            
                              return new RolesResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), resultList, null);
           
                        }  
                        else if(resultList != null && resultList.size()  == 0)
                        {
                           
                             return new RolesResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), resultList, null);
           
                        }  
                        else
                        {
                           return new RolesResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), resultList, null);
           
                        }
                    
              
           
        }/*
        catch (JsonbException e) {
        
             e.printStackTrace();
             return new RolesResponse(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null, e.getMessage());
           
        }*/
        catch (Exception e) {
        
             e.printStackTrace();
          
            return new RolesResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, e.getMessage());
           
            
        }
        
    }
    
    @Transactional
    public RolesResponse doListActiveRoles() {
        //System.out.println(" called ...... ");
        List<RolesInfo> resultList = new ArrayList<>();
        try 
        {
            
                resultList = rolesRepository.doFindByStatus(1);
               
                if(resultList != null && resultList.size() > 0)
                {
                     return new RolesResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), resultList, null);
                }  
                else if(resultList != null && resultList.size()  == 0)
                {
                   
                     return new RolesResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), resultList, null);
           
                }  
                else
                {
                  
                    return new RolesResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null, null);
           
                }
               
           
        }/*
        catch (JsonbException e) {
        
             e.printStackTrace();
           
            return new RolesResponse(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null, e.getMessage());
           
        }*/
        catch (Exception e) {
        
           e.printStackTrace();
           // return Response.status(ErrorCodes.SYSTEM_ERROR).build();
            
            return new RolesResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, e.getMessage());
            
        }
        
    }
    
    
    /*
     @Transactional
    public Response doListDashboardRoles(@Valid JsonObject json) {
        LOGGER.info("  called  doListDashboardRoles ...... "+json);
        try 
        {
            
                GenericFilterObject gobj = JsonbBuilder.create().fromJson(json.toString(), GenericFilterObject.class);
            
                TypedQuery<RolesInfo> query = em.createNamedQuery(RolesInfo.BY_DASHBOARD_ADMIN_ROLES, RolesInfo.class).setParameter("passed", "Y");
              
                List<RolesInfo> resultList = query.getResultList();
                
                //LOGGER.info("  called  doListDashboardRoles ..resultList.... "+resultList.size());
               
                if(resultList != null && resultList.size() > 0)
                {
                     return Response.ok(resultList.stream().map(a->a.toJson()).collect(toList()), MediaType.APPLICATION_JSON).build(); 
                }
                else
                {
                    return Response.status(ErrorCodes.DATABASE_ERROR).build();
                }
           
        } 
        catch (JsonbException e) {
        
             e.printStackTrace();
            return Response.status(ErrorCodes.FORMAT_ERROR).build();
        }
        catch (Exception e) {
        
           e.printStackTrace();
            return Response.status(ErrorCodes.SYSTEM_ERROR).build();
            
        }
        
    }
    */
    
    @Transactional
    public RolesResponse doAllList() {
        //System.out.println(" called ...... ");
         int prodOrDev = Integer.parseInt(sysDataHelper.doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        List<RolesInfo> roles = new ArrayList<>();
        try 
        {
              
                List<RolesInfo> resultList = RolesInfo.doFindAll();
                 
                return new RolesResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), resultList, null);
        
         
           
        }/*
        catch (JsonbException e) {
        
             e.printStackTrace();
            return Response.status(ErrorCodes.FORMAT_ERROR).build();
        }*/
        catch (Exception e) {
           // org.hibernate.exception.SQLGrammarException
             e.printStackTrace();
                if(e.getCause() instanceof CMFBException)
                {
                    System.err.println(" ** AHA = " + e.getMessage()+" cause "+e.getCause());
                    CMFBException exp =   (CMFBException) e.getCause();
                    
                    return new RolesResponse(false, exp.getResponse().getStatus(), ErrorCodes.doErrorDesc(exp.getResponse().getStatus()), null, e.getMessage());
        
                }
                LOGGER.error("Exception @ Response doAllList rolesinfo ...",e);
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                    return new RolesResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null, e.getMessage());
        
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                      return new RolesResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null, e.getMessage());
        
                }
                else
                {
                    return new RolesResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, e.getMessage());
        
                }
            
        }
        
    }
    
    public RolesResponse doLookupByName(String name) {
        
        RolesInfo obj = null;
        List<RolesInfo> roles = new ArrayList<>();
        try 
        {
            
            obj = RolesInfo.doFindByName(name);
            
            if(obj !=null)
            {
                 roles.add(obj);
                 return new RolesResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), roles, null);
        
            }
            else
            {
                 return new RolesResponse(false, ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND), null, null);
        
            }
              
        } catch (Exception e) {
        
           e.printStackTrace();
           return new RolesResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, e.getMessage());
        
        
        }
     
       // return obj;  
        
    }
    
    public RolesResponse doListRoles(FilterRequest request) {
        int defaultPageSize = Integer.parseInt(sysDataHelper.getProps("DEFAULT-PAGE-SIZE", "5"));
        int prodOrDev = Integer.parseInt(sysDataHelper.getProps("INTEGRATION-MODE", "0"));
        
        System.out.println(" doListRoles defaultPageSize = " + defaultPageSize);
        JsonArrayBuilder jar = Json.createArrayBuilder();
        PanacheQuery<RolesInfo> findByQuery= null;
        ValidationHelper rh = new ValidationHelper();
        List<RolesInfo> data = new ArrayList<>();
        boolean isSearch = false;
        try 
        {
            //
            FilterRequestObj fromJson = new FilterRequestObj(request);
            
           
            if(fromJson !=null)
            {
              
                if(rh.strToLDT(fromJson.toDate).isBefore(rh.strToLDT(fromJson.fromDate)))
                {
                          
                    return new RolesResponse(true, ErrorCodes.DATE_DISPARITY, ErrorCodes.doErrorDesc(ErrorCodes.DATE_DISPARITY), null, null);
                }
                else if((fromJson.pageId) < 1 || fromJson.pageSize < 1)// || !resourceH.isValidWithLen(fromJson.getPhoneNumberPri().replaceAll("\\+", ""), 13))
                {
                    return new RolesResponse(true, ErrorCodes.INVALID_PAGE_ID_OR_FETCH_SIZE, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_PAGE_ID_OR_FETCH_SIZE), null, null);
               
                }
                else
                {
                    
               
                   if(fromJson.status.equalsIgnoreCase("all") && (!rh.isValid(fromJson.searchParam)))// || "NA".equals(fromJson.searchKey)))
                   {
                      findByQuery = rolesRepository.findByParams(rh.toLocalDateTime(fromJson.fromDate), rh.toLocalDateTime(fromJson.toDate)); //doFindByDescription(jsonStr)
                      LOGGER.info(" userRepository STEP 1 ");
                   }
                   else if(!fromJson.status.equalsIgnoreCase("all") && (!rh.isValid(fromJson.searchParam)))// || "NA".equals(fromJson.searchKey)))
                   {
                      findByQuery = rolesRepository.findByParams(fromJson.status, rh.toLocalDateTime(fromJson.fromDate), rh.toLocalDateTime(fromJson.toDate)); //doFindByDescription(jsonStr)
                      LOGGER.info(" userRepository STEP 1 A ");
                   }
                   else if(!fromJson.status.equalsIgnoreCase("all") && rh.isValid(fromJson.searchParam))
                   {
                       findByQuery = rolesRepository.findByParams(fromJson.status, rh.toLocalDateTime(fromJson.fromDate), rh.toLocalDateTime(fromJson.toDate), fromJson.searchParam); //doFindByDescription(jsonStr)
                       LOGGER.info(" @@@ userRepository STEP 2 ");
                   }
                   else if(fromJson.status.equalsIgnoreCase("all") && rh.isValid(fromJson.searchParam))
                   {
                       findByQuery = rolesRepository.findByParams(rh.toLocalDateTime(fromJson.fromDate), rh.toLocalDateTime(fromJson.toDate), fromJson.searchParam); //doFindByDescription(jsonStr)
                       LOGGER.info(" @@@ userRepository STEP 2 A ");
                   } 
                
               
                
                        if(findByQuery !=null)findByQuery.page(Page.ofSize(fromJson.pageSize));
                        int numberOfPages = 0;
                        if(findByQuery !=null && !isSearch)
                        {
                             numberOfPages = findByQuery.pageCount();
                            System.out.println("-- numberOfPages = " + numberOfPages);
                        }
                        else if(findByQuery !=null && isSearch)
                        {
                            System.out.println(" B4 fromJson.pageId = " + fromJson.pageId);
                            fromJson.pageId = findByQuery.pageCount();
                            numberOfPages = findByQuery.pageCount();
                            fromJson.pageId = numberOfPages;
                            System.out.println("-- numberOfPages = " + numberOfPages);

                            System.out.println(" After fromJson.pageId = " + fromJson.pageId);
                        }

                        //if(findByQuery !=null)findByQuery.stream().forEach(a->System.out.println("-- xox --- "+a.toJson()));
                        int totalRecs = (findByQuery !=null)?(int)findByQuery.count():0;//providerCategoryRepo.count();


                        System.out.println(" @@-- @@@ totalRecs = " + totalRecs +" fromJson.pageSize "+fromJson.pageSize+"  fromJson.page : "+fromJson.pageId+" ss findByQuery "+findByQuery !=null);
                        if(findByQuery !=null) //findByStatus.size()  // && fromJson.pageSize >  totalRecs  && fromJson.pageId == 1
                        {
                             System.out.println("totalRecs B4 = " + findByQuery.pageCount());
                             findByQuery.page(Page.ofSize(fromJson.pageSize));
                             System.out.println("totalRecs AFTER = " + findByQuery.pageCount());
                            List<RolesInfo> list = findByQuery.page(Page.of(fromJson.pageId-1,fromJson.pageSize)).list();
                            list.stream().forEach(a->jar.add(a.toJson()));
                            //list.stream().forEach(a->jar.add(a.toJsonv()));

                           return new RolesResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), list, null);


                        }
                        else
                        { 
                            System.out.println(" HOW ?? ");

                            return new RolesResponse(true, ErrorCodes.SUCCESSFUL, ErrorCodes.doErrorDesc(ErrorCodes.SUCCESSFUL), data, null);
                        }
                }
            }
            else
            {
                return  new RolesResponse(false, ErrorCodes.FORMAT_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.FORMAT_ERROR), null, null);
            }
        
        }
        catch (CMFBException e) {
             e.printStackTrace();
             
          return  new RolesResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, e.getMessage());
        
        }
        catch (Exception e) {
        
               LOGGER.error("Exception @ Response doListPartners ",e);
               
                if(e.getCause() instanceof com.paycraftsystems.exceptions.CMFBException)
                {
                    System.err.println(" ** AHA = " + e.getMessage()+" cause "+e.getCause());
                    CMFBException exp =   (CMFBException) e.getCause();
                   
                     return  new RolesResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, e.getMessage());
        
                
                
                }
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)  || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                 
                   return  new RolesResponse(false, ErrorCodes.DATABASE_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.DATABASE_ERROR), null, e.getMessage());
        
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                      
                      return  new RolesResponse(false, ErrorCodes.IO_EXCEPTION, ErrorCodes.doErrorDesc(ErrorCodes.IO_EXCEPTION), null, e.getMessage());
        
                }
                else
                {
                     
                    return  new RolesResponse(false, ErrorCodes.SYSTEM_ERROR, ErrorCodes.doErrorDesc(ErrorCodes.SYSTEM_ERROR), null, e.getMessage());
        
                }
        }
        
     }
    
    
}
