/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.entities;

//import com.paycraftsystems.resources.ValidationHelper;
import com.paycraftsystems.resources.ValidationHelper;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.io.Serializable;
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
@Table(name = "country")
public class Country extends PanacheEntityBase implements Serializable {
    
    public static final String ALL = "States.findAll";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TID")
    public Long tid;
    @Column(name = "code")
    public String code;
    @Column(name = "country_name")
    public String countryName;
    @Column(name = "status")
    public long status;
 

    public Country() {
    }

    
    
    public static JsonArray  doListCountries() {
        
        JsonArrayBuilder jar = Json.createArrayBuilder();
        try 
        {
             
              List<Country>  states =  Country.listAll();
              states.stream().forEach(a-> jar.add(a.toJson()));
        } 
        catch (Exception e) {
       
        
        }
     return jar.build();
    }
    
    public static JsonArray  doListCountriesByStatus(long status) {
        
        JsonArrayBuilder jar = Json.createArrayBuilder();
        try 
        {
             
              List<Country>  states =  Country.listAll();
              states.stream().filter(x->x.status == status).forEach(a-> jar.add(a.toJson()));
        } 
        catch (Exception e) {
       
        
        }
     return jar.build();
    }
    
     public static String  doGetCountryName(long tid) {
        
        Country obj  = null;
        try 
        {
           
             obj =  find("tid", tid).firstResult();
        } 
        catch (Exception e) {
       
        
        }
     return obj==null?"NA":obj.countryName;
    }
     
      public static Country  doGetCountryByTid(long tid) {
        
        Country obj  = null;
        try 
        {
           
             obj =  find("tid", tid).firstResult();
        } 
        catch (Exception e) {
       
        
        }
     return obj;
    }
    
    public JsonObject toJson() {
        JsonObjectBuilder job = Json.createObjectBuilder();
        ValidationHelper rh = new ValidationHelper();
        try 
        {
            job.add("tid", this.tid)
               .add("code", rh.toDefault(this.code))
               .add("name", rh.toDefault(this.countryName));
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
        if (!(object instanceof Country)) {
            return false;
        }
        Country other = (Country) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Country{" + "tid=" + tid + ", code=" + code + ", countryName=" + countryName + ", status=" + status + '}';
    }

}
