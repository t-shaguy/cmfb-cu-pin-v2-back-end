/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.entities;


import com.paycraftsystems.resources.ValidationHelper;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author root
 */
@Entity
@Table(name = "roles_info")
@NamedQueries({
    @NamedQuery(name = RolesInfo.ALL, query = "SELECT r FROM RolesInfo r where r.createdDate between :from and :to order by createdDate desc"),
    @NamedQuery(name = RolesInfo.ALL_COUNT, query = "SELECT count(r) FROM RolesInfo r where r.createdDate between :from and :to order by createdDate desc"),
    
    @NamedQuery(name =  RolesInfo.BY_DESCRIPTION, query = "SELECT r FROM RolesInfo r WHERE r.createdDate between :from and :to and (r.roleDesc like :passed or r.roleName like :passed )"),
    @NamedQuery(name =  RolesInfo.BY_DESCRIPTION_COUNT, query = "SELECT count(r) FROM RolesInfo r WHERE r.createdDate between :from and :to and (r.roleDesc like :passed or r.roleName like :passed )"),
    
    @NamedQuery(name =  RolesInfo.BY_STATUS, query = "SELECT r FROM RolesInfo r WHERE r.createdDate between :from and :to and r.statusStr like :passed"),
    @NamedQuery(name =  RolesInfo.BY_STATUS_COUNT, query = "SELECT count(r) FROM RolesInfo r WHERE r.createdDate between :from and :to and r.statusStr like :passed"),
    
    @NamedQuery(name =  RolesInfo.BY_DASHBOARD_ADMIN_ROLES, query = "SELECT r FROM RolesInfo r WHERE r.dashboardAdminRoles = :passed"),
    @NamedQuery(name =  RolesInfo.BY_NAME, query = "SELECT r FROM RolesInfo r WHERE r.roleName = :passed")
})
public class RolesInfo extends PanacheEntityBase implements Serializable {
    
   private static Logger LOGGER =  LoggerFactory.getLogger(RolesInfo.class);
    
    public static final String ALL = "RolesInfo.findAll";
    public static final String ALL_COUNT = "RolesInfo.findAllCount";
    public static final String BY_DESCRIPTION = "RolesInfo.findByRoleByDescription";
    public static final String BY_DESCRIPTION_COUNT = "RolesInfo.findByRoleByDescriptionCount";
    public static final String BY_STATUS = "RolesInfo.findByRoleByStatus";
    public static final String BY_STATUS_COUNT = "RolesInfo.findByRoleByStatusCount";
    
    public static final String BY_DASHBOARD_ADMIN_ROLES = "RolesInfo.findByDashboardAdminRoles";
   // public static final String BY_STATUS = "RolesInfo.findByRoleStatus";
    public static final String BY_NAME = "RolesInfo.findByName";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    public Long tid;
    @Column(name = "role_name")
    public String roleName;
    @Column(name = "role_desc")
    public String roleDesc;
    @Column(name = "role_code")
    public String roleCode;
    @Column(name = "created_date")
   // @Temporal(TemporalType.TIMESTAMP)
    public  LocalDateTime createdDate;
    @Column(name = "update_date")
    //@Temporal(TemporalType.TIMESTAMP)
    public  LocalDateTime updateDate;
    
     @Column(name = "approved_date")
    //@Temporal(TemporalType.TIMESTAMP)
    public  LocalDateTime approvedDate;
    @Column(name = "status")//, referencedColumnName = "TID")
    //@ManyToOne
    public long status;
    
    @Column(name="approved_by")
    public long approvedBy;
    
    @Column(name = "status_str")//, referencedColumnName = "TID")
    //@ManyToOne
    public String statusStr;
     
    @Column(name = "created_by", columnDefinition="bigint(20) default 0 ")//, referencedColumnName = "tid")
    //@ManyToOne
    public  long createdBy;
    
    @Column(name = "created_by_str")//, referencedColumnName = "tid")
    //@ManyToOne
    public String createdByStr;   
    
    @Column(name = "updated_by")//, referencedColumnName = "tid")
    //@ManyToOne
    public Long updatedBy;
    
    @Column(name = "updated_by_str")//, referencedColumnName = "tid")
    //@ManyToOne
    public  String updatedByStr;
    
     
    @Column(name = "role_prefix")
    public String rolePrefix;
    
    @Column(name = "for_admin_dashboard")
    public String dashboardAdminRoles;

    public RolesInfo() {
    }

    public RolesInfo(Long tid) {
        this.tid = tid;
    }
    
    
    
    
    public static RolesInfo doFindByName(String name) {
        RolesInfo  obj = null;
        try 
        {
           obj = find("roleName", name).firstResult();
            
        } 
        catch (Exception e) {
        
        }
        
     return obj;
    }
    
    public static RolesInfo doFindByCode(String code) {
        RolesInfo  obj = null;
        try 
        {
           obj = find("roleCode", code).firstResult();
            
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        }
        
     return obj;
    }
    
    public static RolesInfo doFindByTid(long tid) {
        RolesInfo  obj = null;
        try 
        {
           obj = find("tid", tid).firstResult();
            
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        }
        
     return obj;
    }
    
    public static String doFindRoleDescByCode(String code) {
        RolesInfo  obj = null;
        try 
        {
           obj = find("roleCode", code).firstResult();
            
        } 
        catch (Exception e) {
        
        }
        
     return obj==null?"NA":obj.roleName;
    }
    
    
    @Transactional
    public static RolesInfo doSyncStatus(RolesInfo obj,long sts, Long actionBy) {
        LOGGER.info("-- RolesInfo  doSyncStatus -- "+obj);
        RolesInfo newObj = new RolesInfo();
        try 
        {
            obj.status = sts;
            obj.statusStr = Status.doStatusDescById(obj.status);
            if(sts == 3)
            {
               obj.updateDate = LocalDateTime.now();
            }
            else  if(sts == 1)
            {
                obj.approvedDate = LocalDateTime.now();
                obj.approvedBy = actionBy;
                
            }
            
            newObj = Panache.getEntityManager().merge(obj);
            
            LOGGER.info("-- RolesInfo doSyncStatus -- newObj "+newObj);
        } 
        catch (Exception e) {
            
            e.printStackTrace();
            
            LOGGER.error("Exception @ RolesInfo doSync", e);
       
        }
     return newObj;
    }
    
    public static String doFindRoleDescByCode(long tid) {
        RolesInfo  obj = null;
        try 
        {
           obj = find("tid", tid).firstResult();
            
        } 
        catch (Exception e) {
        
        }
        
     return obj==null?"NA":obj.roleName.toUpperCase(); // return obj==null?"NA":obj.roleDesc.toUpperCase();
    }
    
    public static String doFindRoleCodeById(long tid) {
        RolesInfo  obj = null;
        try 
        {
           obj = find("tid", tid).firstResult();
            
        } 
        catch (Exception e) {
        
        }
        
     return obj==null?"NA":obj.roleCode;
    }
    
    public static RolesInfo doLog(RolesInfo objx) {
        RolesInfo  obj = null;
        try 
        {
           obj =  Panache.getEntityManager().merge(objx);//find("name", name).firstResult();
            
        } 
        catch (Exception e) {
        
        }
        
     return obj;
    }
    
    
    public static List<RolesInfo> doFindAll() throws Exception {
        List<RolesInfo>  obj = new ArrayList<>();
        try 
        {
           obj = RolesInfo.listAll();
            
        } 
        catch (Exception e) {
        
            throw new Exception(e);
        }
        
     return obj;
    }
    
    public static List<RolesInfo> doFindByDateRangeAndStatus(LocalDateTime fromDate,LocalDateTime toDate, long status) {
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
    
    
     public static List<RolesInfo> doFindByStatus(long status) {
        List<RolesInfo>  obj = new ArrayList<>();
        try 
        {
           obj = find("status",status).list();
            
        } 
        catch (Exception e) {
        
        }
        
     return obj;
    }
     
     public static  JsonObject toJson(int noOfPages, int totalRecs, int pageId, JsonArray array) {
        
        ValidationHelper rh = new ValidationHelper();
        JsonObjectBuilder job = Json.createObjectBuilder();
        JsonObjectBuilder mainResponse = Json.createObjectBuilder();
        try 
        {
         
            mainResponse.add("pageCount",noOfPages).add("totalRecs", totalRecs).add("pageId", pageId).add("data", array);
           
            
        } catch (Exception e) {
        
            e.printStackTrace();
        
        }
      return mainResponse.build();
    }

   
    public JsonObject toJson() {
        ValidationHelper rh = new ValidationHelper();
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
            job.add("tid", this.tid)
               .add("roleName", rh.toDefault(this.roleName))
               .add("roleDesc", rh.toDefault(this.roleDesc))
               .add("status", this.status)
               .add("statusStr", rh.toDefault(this.statusStr))
               .add("roleDesc", rh.toDefault(this.roleDesc))
               .add("roleCode", rh.toDefault(this.roleCode))
               .add("createdDate", rh.toDefault(this.createdDate))
               .add("createdBy", rh.toDefault(this.createdBy))
               .add("createdByStr", rh.toDefault(this.createdByStr));
        } 
        catch (Exception e) {
        
        }
      return job.build();
    }
    
    public JsonObjectBuilder toJsonv() {
        ValidationHelper rh = new ValidationHelper();
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
            job.add("tid", this.tid)
               .add("roleName", rh.toDefault(this.roleName))
               .add("roleDesc", rh.toDefault(this.roleDesc))
               .add("statusStr", rh.toDefault(this.statusStr))
               .add("roleDesc", rh.toDefault(this.roleDesc))
               .add("roleCode", rh.toDefault(this.roleCode))
               .add("createdDate", rh.toDefault(this.createdDate))
               .add("createdBy", rh.toDefault(this.createdBy))
               .add("createdByStr", rh.toDefault(this.createdByStr));
        } 
        catch (Exception e) {
        
        }
      return job;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tid != null ? tid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolesInfo)) {
            return false;
        }
        RolesInfo other = (RolesInfo) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RolesInfo{" + "tid=" + tid + ", roleName=" + roleName + ", roleDesc=" + roleDesc + ", roleCode=" + roleCode + ", createdDate=" + createdDate + ", updateDate=" + updateDate + ", status=" + status + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", rolePrefix=" + rolePrefix + ", dashboardAdminRoles=" + dashboardAdminRoles + '}';
    }

}
