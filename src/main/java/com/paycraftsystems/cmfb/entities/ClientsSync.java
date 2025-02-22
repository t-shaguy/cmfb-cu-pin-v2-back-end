/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.entities;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.io.Serializable;
import java.time.LocalDateTime;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.paycraftsystems.resources.ValidationHelper;

/**
 *
 * @author root
 */
@Entity
@Table(name = "clients_sync")
public class ClientsSync extends PanacheEntityBase implements Serializable {
    
     private static Logger LOGGER = LoggerFactory.getLogger(ClientsSync.class);
    
    
    public static final String ALL = "ClientsSync.findAll";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TID")
    public Long tid;
    @Column(name = "client_id")
    public String clientId;
    
    @Column(name = "reset_date")
    public LocalDateTime resetDate;
    
    @Column(name = "sync_date")
    public LocalDateTime syncDate;
    // @ManyToOne
    @Column(name = "status")
    public long status;
    
    @Column(name = "client_class")
    public long clientClass;
    
    @Column(name = "partner_id")
    public String partnerId;
    
    @Column(name="customer_code")
    public String customerCode;
    
    @Column(name="fee_code")
    public String feeCode;
    
    @Column(name="client_type")
    public String clientType;

    public ClientsSync() {
    }

    
    
    public static JsonArray  doListClients() {
        
        JsonArrayBuilder jar = Json.createArrayBuilder();
        try 
        {
             
              List<ClientsSync>  clients =  ClientsSync.listAll();
              clients.stream().forEach(a-> jar.add(a.toJson()));
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
               .add("clientId", rh.toDefault(this.clientId))
               .add("partnerId", rh.toDefault(this.partnerId))
               .add("customerCode", rh.toDefault(this.customerCode))
               .add("status", this.status)
               .add("clientClass", this.clientClass);
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
        if (!(object instanceof ClientsSync)) {
            return false;
        }
        ClientsSync other = (ClientsSync) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ClientsSync{" + "tid=" + tid + ", clientId=" + clientId + ", resetDate=" + resetDate + ", syncDate=" + syncDate + ", status=" + status + ", clientClass=" + clientClass + ", partnerId=" + partnerId + ", customerCode=" + customerCode + ", feeCode=" + feeCode + '}';
    }

    
    

    
}
