/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.entities;

//import com.paycraftsystems.resources.ValidationHelper;
//import com.hoptool.resources.ResourceHelper;
import com.paycraftsystems.cmfb.resources.ResourceHelper;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.Json;
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
//import jakarta.ws.rs.client.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 *
 * @author root
 */
@Entity
@Table(name = "sys_data")
@NamedQueries({
    @NamedQuery(name = SysData.ALL, query = "SELECT s FROM SysData s"),
    @NamedQuery(name = "SysData.findByTid", query = "SELECT s FROM SysData s WHERE s.tid = :tid"),
    @NamedQuery(name = "SysData.findByParamName", query = "SELECT s FROM SysData s WHERE s.paramName = :paramName")})
public class SysData extends PanacheEntityBase implements Serializable {
    
    public static final String ALL = "SysData.findAll";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TID")
    public Long tid;
    @Column(name = "PARAM_NAME")
    public String paramName;
    @Column(name = "PARAM_VALUE")
    public String paramValue;
    @Column(name = "CREATED_BY")
    public Long createdBy;
    @Column(name = "CREATED_DATE")
    //@Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime createdDate;
    @Column(name = "AUTH_BY" , columnDefinition="BigInt(20) default 0")
    public long authBy;
    @Column(name = "AUTH_DATE")
    //@Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime authDate;
    @Column(name = "OPR_COMMENT")
    public String oprComment;
    @Column(name = "status")//, columnDefinition="bigint(20) default 0")//, referencedColumnName = "TID")
   // @ManyToOne
    public String status;
    
    @Column(name="status_str")
    public String statusStr;
    
    
    @Column(name = "created_by_str")
    public String createdByStr;
    
    @Column(name="last_updated_by_str")
    public String lastUpdatedByStr;
    
    @Column(name = "last_updated_date")
    public LocalDateTime lastUpatedDate;
    
    @Column(name="last_updated_by" )
    public long lastUpdatedBy;
    
    @Column(name="auth_by_str")
    public String authByStr;
    
    

    public SysData() {
    }
    
    
    public JsonObject toJson() {
        ResourceHelper rh = new ResourceHelper();
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
            job.add("tid", this.tid)
               .add("paramName", rh.toDefault(this.paramName))
               .add("paramValue", rh.toDefault(this.paramValue))
                     .add("status", this.status)
               .add("statusStr", rh.toDefault(this.statusStr))
               .add("createdByStr", rh.toDefault(this.createdByStr))
               .add("lastUpatedDate", rh.toDefault(this.lastUpatedDate))
               .add("lastUpdatedByStr", rh.toDefault(this.lastUpdatedByStr))
               .add("lastUpatedDate", rh.toDefault(this.lastUpatedDate))
               .add("authDate", rh.toDefault(this.authDate));
                    
                  
        } 
        catch (Exception e) {
        
        
        }
        
        
      return job.build();
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
        if (!(object instanceof SysData)) {
            return false;
        }
        SysData other = (SysData) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SysData{" + "tid=" + tid + ", paramName=" + paramName + ", paramValue=" + paramValue + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", authBy=" + authBy + ", authDate=" + authDate + ", oprComment=" + oprComment + ", status=" + status + ", statusStr=" + statusStr + ", createdByStr=" + createdByStr + ", lastUpdatedByStr=" + lastUpdatedByStr + ", lastUpatedDate=" + lastUpatedDate + ", lastUpdatedBy=" + lastUpdatedBy + ", authByStr=" + authByStr + '}';
    }

   
    

    
}
