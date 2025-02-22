/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.entities;


import com.paycraftsystems.resources.ValidationHelper;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
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
@Table(name = "states")
public class States extends PanacheEntityBase implements Serializable {
    
    public static final String ALL = "States.findAll";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TID")
    public Long tid;
    @Column(name = "state_code")
    public String stateCode;
    @Column(name = "state_name")
    public String stateName;
    @Column(name = "country_id")
    public long countryId;
   // @Column(name = "STS", columnDefinition="bigint(20) default 0")//, referencedColumnName = "TID")
   // @ManyToOne
    //public long sts;

    public States() {
    }

    
    public  List<States>  doListStates() {
        
        JsonArrayBuilder jar = Json.createArrayBuilder();
        List<States>  states =  new ArrayList<>();
        try 
        {
             
              states =  States.listAll();
              //states.stream().forEach(a-> jar.add(a.toJson()));
        } 
        catch (Exception e) {
       
            //LOGGER.error(" --  Exception -doListStates - ",e);
        
        }
     return states;// jar.build();
    }
    
    
   
    
    public static String  doGetStateNamebyID(long tid) {
        
        States states = null;
        try 
        {
             states =  States.findById(tid);
        } 
        catch (Exception e) {
       
        
        }
     return states==null?"NA":states.stateName;
    }
    
    public static JsonArray  doListStates(long countryCode) {
        
        JsonArrayBuilder jar = Json.createArrayBuilder();
        try 
        {
             
              List<States>  states =  States.listAll();
              states.stream().filter(x->x.countryId == countryCode).forEach(a-> jar.add(a.toJson()));
        } 
        catch (Exception e) {
       
        
        }
     return jar.build();
    }
    
    
    
    public JsonObject toJson() {
        JsonObjectBuilder job = Json.createObjectBuilder();
        ValidationHelper rh = new ValidationHelper();
        try 
        {
            job.add("tid", this.tid)
               .add("stateCode", rh.toDefault(this.stateCode))
               .add("stateName", rh.toDefault(this.stateName))
               .add("countryId", this.countryId);
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
        if (!(object instanceof States)) {
            return false;
        }
        States other = (States) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "States{" + "tid=" + tid + ", stateCode=" + stateCode + ", stateName=" + stateName + ", countryId=" + countryId + '}';
    }

    

    
}
