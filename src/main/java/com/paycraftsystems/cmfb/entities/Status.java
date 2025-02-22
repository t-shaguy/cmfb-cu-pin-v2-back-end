/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author root
 */
@Entity
@Table(name = "status")
public class Status extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    public Long tid;
    @Column(name = "code")
    public String code;
    @Column(name = "description")
    public String description;

    public Status() {
    }

    public Status(Long tid) {
        this.tid = tid;
    }

    
    public static Status doFindById(long tid) {
        Status obj = null;
        try 
        {
            obj = find("tid", tid).firstResult();
            
        } catch (Exception e) {
        }
        
      return obj;
    }
    
    
    public static String doStatusDescById(long tid) {
        Status obj = null;
        try 
        {
            obj = find("tid", tid).firstResult();
            
        } catch (Exception e) {
        }
        
      return obj==null?"NA":obj.description;
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
        if (!(object instanceof Status)) {
            return false;
        }
        Status other = (Status) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Status{" + "tid=" + tid + ", code=" + code + ", description=" + description + '}';
    }

}
