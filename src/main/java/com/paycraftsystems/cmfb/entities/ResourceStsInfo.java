/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author root
 */
@Entity
@Table(name = "resource_sts_info")
@NamedQueries({
    @NamedQuery(name = "ResourceStsInfo.findAll", query = "SELECT r FROM ResourceStsInfo r"),
    @NamedQuery(name = "ResourceStsInfo.findByTid", query = "SELECT r FROM ResourceStsInfo r WHERE r.tid = :tid"),
    @NamedQuery(name = "ResourceStsInfo.findByStsDesc", query = "SELECT r FROM ResourceStsInfo r WHERE r.stsDesc = :stsDesc"),
    @NamedQuery(name = "ResourceStsInfo.findByCreateDate", query = "SELECT r FROM ResourceStsInfo r WHERE r.createDate = :createDate"),
    @NamedQuery(name = "ResourceStsInfo.findByCreatedBy", query = "SELECT r FROM ResourceStsInfo r WHERE r.createdBy = :createdBy"),
    @NamedQuery(name = "ResourceStsInfo.findByUpdatedBy", query = "SELECT r FROM ResourceStsInfo r WHERE r.updatedBy = :updatedBy"),
    @NamedQuery(name = "ResourceStsInfo.findByUpdatedDate", query = "SELECT r FROM ResourceStsInfo r WHERE r.updatedDate = :updatedDate")})
public class ResourceStsInfo extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TID")
    public Long tid;
    @Column(name = "STS_DESC")
    public String stsDesc;
    @Column(name = "CREATE_DATE")
    //@Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime createDate;
    @Column(name = "CREATED_BY")
    public long createdBy;
    @Column(name = "UPDATED_BY")
    public long updatedBy;
    @Column(name = "UPDATED_DATE")
    //@Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime updatedDate;
    //@OneToMany(mappedBy = "status")
    /*
    private List<RolesInfo> rolesInfoList;
    @OneToMany(mappedBy = "status")
    private List<PlayChannels> playChannelsList;
    @OneToMany(mappedBy = "status")
    private List<InfluencerChannels> influencerChannelsList;
    
    @OneToMany(mappedBy = "resourceSts")
    private List<Influencers> influencersList;
    
    @OneToMany(mappedBy = "sts")
    private List<SysData> sysDataList;
    
    
    @OneToMany(mappedBy = "status")
    private List<GlInfo> glInfoList;
    
    */

    public ResourceStsInfo() {
    }

    public ResourceStsInfo(Long tid) {
        this.tid = tid;
    }

    
    
    public static ResourceStsInfo  findbyID(long tid) {
        
        ResourceStsInfo obj = null;
        try 
        {
           obj =  find("tid", tid).firstResult();
        } 
        catch (Exception e) {
        
        
        }
        
     return obj;
    }
    
    public static String  findDescbyID(long tid) {
        
        ResourceStsInfo obj = null;
        try 
        {
           obj =  find("tid", tid).firstResult();
        } 
        catch (Exception e) {
        
        
        }
        
     return obj==null?"NA":obj.stsDesc;
    }
    
    public static List<ResourceStsInfo>  doFindAll() {
        
        List<ResourceStsInfo> obj = new ArrayList<>();
        try 
        {
           obj = ResourceStsInfo.listAll();
        } 
        catch (Exception e) {
        
        
        }
        
     return obj;
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
        if (!(object instanceof ResourceStsInfo)) {
            return false;
        }
        ResourceStsInfo other = (ResourceStsInfo) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResourceStsInfo{" + "tid=" + tid + ", stsDesc=" + stsDesc + ", createDate=" + createDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + '}';
    }

    
}
