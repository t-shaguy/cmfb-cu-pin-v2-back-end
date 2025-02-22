/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.repositories;



import com.paycraftsystems.cmfb.entities.RolesInfo;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author paycraftsystems-i
 */
@ApplicationScoped
public class RolesRepository implements PanacheRepository<RolesInfo> {
    
    
    public String doFindOrDefaultTo(String name, String defaulto)
    {
        RolesInfo firstResult = RolesInfo.find("roleName", name).firstResult();
        
        return (firstResult == null || firstResult.roleDesc ==null)?defaulto:firstResult.roleDesc;
    }
    
    
    public RolesInfo doFindByCode(String name)
    {
        return RolesInfo.find("roleCode", name).firstResult();
        
       // return (firstResult == null || firstResult.roleDesc ==null)?defaulto:firstResult.roleDesc;
    }
    
    public RolesInfo doFindById(long tid)
    {
        return RolesInfo.find("tid", tid).firstResult();
        
       // return (firstResult == null || firstResult.roleDesc ==null)?defaulto:firstResult.roleDesc;
    }
    
    
    //providerName, providerCode, servicesDesc, contactPerson, contactEmail, contactNumber, state_str, stateStr, countryStr, providerCategoryStr, 
    public PanacheQuery<RolesInfo> findByParams(String status, LocalDateTime from, LocalDateTime today){
       return RolesInfo.find("status = ?1 and createdDate between ?2 and ?3 order by createdDate desc", status, from, today);
    }
    
    public PanacheQuery<RolesInfo> findByParams(String status,  LocalDateTime from, LocalDateTime today, String searchKey){
       return RolesInfo.find("status = ?1 and createdDate between ?2 and ?3 and (roleName like ?4 or  roleDesc like ?4 or roleCode like ?4 or statusStr like ?4 or createdByStr like ?4 or updatedByStr like ?4) order by createdDate desc", status, from, today, searchKey+'%');
    }
    
    public PanacheQuery<RolesInfo> findByParams(LocalDateTime from, LocalDateTime today){
       return RolesInfo.find("createdDate between ?1 and ?2 order by createdDate desc",from, today);
    }
    
    public PanacheQuery<RolesInfo> findByParams(LocalDateTime from, LocalDateTime today, String searchKey){
       return RolesInfo.find("createdDate between ?1 and ?2 and (roleName like ?3 or  roleDesc like ?3 or roleCode like ?3 or statusStr like ?3 or createdByStr like ?3 or updatedByStr like ?3) order by createdDate desc",from, today, searchKey+'%');
    }
    
    public  List<RolesInfo> doFindByStatus(long status) {
        List<RolesInfo>  obj = new ArrayList<>();
        try 
        {
           obj = find("status",status).list();
            
        } 
        catch (Exception e) {
        
        }
        
     return obj;
    }
    
    public  List<RolesInfo> doFindByDateRangeAndStatus(LocalDateTime fromDate,LocalDateTime toDate, long status) {
        List<RolesInfo>  obj = new ArrayList<>();
        try 
        {
             if(status == 0)
             {
                 obj = find("createdDate between ?1 and ?2 ", fromDate, toDate).list();
             }
             else
             {
                  obj = find("createdDate between ?1 and ?2  and ?3 ", fromDate, toDate, status).list();
             }
            
        } 
        catch (Exception e) {
        
        }
        
     return obj;
    }
    
}
