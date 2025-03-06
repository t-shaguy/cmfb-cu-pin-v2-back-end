/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.repositories;





import com.paycraftsystems.cmfb.dto.ApproveOrDeleteRequest;
import com.paycraftsystems.cmfb.dto.SysDataEditRequestObj;
import com.paycraftsystems.cmfb.dto.SysDataRequestObj;
import com.paycraftsystems.cmfb.entities.SysData;
import com.paycraftsystems.cmfb.enumz.ResourceStatusEnum;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author paycraftsystems-i
 */

@ApplicationScoped
@Slf4j
public class SysDataRepository implements  PanacheRepository<SysData> {
    
    
    public SysData doLookUpById(long tid) {
    // log.info("obj = " + obj.reference);
     return find("tid = ?1", tid).firstResult();
    
    }
    
    public SysData doLookUpByName(String name) {
     log.info("obj = " + name);
     return find("paramName = ?1 ", name).firstResult();
    
    }
    
    
    public String doLookUpByNameStr(String name, String defaultTo) {
      log.info("obj = " + name);
      SysData firstResult = find("paramName = ?1 ", name).firstResult();
      return firstResult !=null?firstResult.paramValue:defaultTo;
    
    }
   
    
    public PanacheQuery<SysData> findByParams(String status, LocalDateTime from, LocalDateTime today){
       return SysData.find("status = ?1 and createdDate between ?2 and ?3 order by createdDate desc", status, from, today);
    }
    
    public PanacheQuery<SysData> findByParams(String status,  LocalDateTime from, LocalDateTime today, String searchKey){
       return SysData.find("status = ?1 and createdDate between ?2 and ?3 and (paramName like ?4 or  paramValue like ?4 ) order by createdDate desc", status, from, today, searchKey+'%');
    }
    
    public PanacheQuery<SysData> findByParams(LocalDateTime from, LocalDateTime today){
       return SysData.find("createdDate between ?1 and ?2 order by createdDate desc",from, today);
    }
    
    public PanacheQuery<SysData> findByParams(LocalDateTime from, LocalDateTime today, String searchKey){
       return SysData.find("createdDate between ?1 and ?2 and (paramName like ?4 or  paramValue like ?4 ) order by createdDate desc",from, today, searchKey+'%');
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
    public SysData doLog(SysDataRequestObj request) throws Exception {
        SysData obj = null;
        String firstname = "", lastname ="";
        try 
        {
            
           SysData doLookUpByParamName = doLookUpByName(request.paramName);
           log.info("--doLookUpByParamName -- "+doLookUpByParamName);
           if(doLookUpByParamName == null)
           {
          
                obj = new SysData();
          
                
           
                obj.paramName = request.paramName;
                obj.paramValue = request.paramValue;
                obj.createdDate = LocalDateTime.now();
                obj.authBy = Long.parseLong("0");
                obj.status = ResourceStatusEnum.INACTIVE.name();
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
    public SysData doSync(SysDataEditRequestObj request) throws Exception {
        SysData obj = new SysData();
        try 
        {
         
           SysData doLookUpByParamName = doLookUpByName(request.paramName);
           //log.info("-- do sync objx = " + doLookUpByParamName+" : : code "+code);
           if(doLookUpByParamName != null)
           {
             //log.info(" got inside objx = " + objx);
             //System.out.println("max count objx = " + maxCountObj);
              doLookUpByParamName.paramName  = request.paramName;
              doLookUpByParamName.paramValue  = request.paramValue;
              doLookUpByParamName.lastUpatedDate = LocalDateTime.now();
              doLookUpByParamName.lastUpdatedBy = request.actionBy;
              doLookUpByParamName.authBy = Long.parseLong("0");
              
              SysData merge = Panache.getEntityManager().merge(doLookUpByParamName);
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
    public SysData doApprove(ApproveOrDeleteRequest request) throws Exception {
        SysData obj = new SysData();
        try 
        {
         
           SysData doLookUpByParamName = doLookUpById(request.tid());
           //log.info("-- do sync objx = " + doLookUpByParamName+" : : code "+code);
           if(doLookUpByParamName != null)
           {
             
              //doLookUpByParamName.paramName  = request.paramName;
              //doLookUpByParamName.paramValue  = request.paramValue;
              doLookUpByParamName.authDate = LocalDateTime.now();
              doLookUpByParamName.authBy = request.actionBy();
              doLookUpByParamName.status = ResourceStatusEnum.ACTIVE.name();
              
              SysData merge = Panache.getEntityManager().merge(doLookUpByParamName);
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
    public SysData doDelete(ApproveOrDeleteRequest request) throws Exception {
        SysData obj = new SysData();
        try 
        {
         
           SysData doLookUpByParamName = doLookUpById(request.tid());
           //log.info("-- do sync objx = " + doLookUpByParamName+" : : code "+code);
           if(doLookUpByParamName != null)
           {
             //log.info(" got inside objx = " + objx);
             //System.out.println("max count objx = " + maxCountObj);
              //doLookUpByParamName.paramName  = request.paramName;
              doLookUpByParamName.status  = ResourceStatusEnum.DELETED.name();
              doLookUpByParamName.lastUpatedDate = LocalDateTime.now();
              doLookUpByParamName.lastUpdatedBy = request.actionBy();
             
              
              SysData merge = Panache.getEntityManager().merge(doLookUpByParamName);
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
