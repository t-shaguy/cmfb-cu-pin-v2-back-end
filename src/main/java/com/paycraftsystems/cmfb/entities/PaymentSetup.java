 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.entities;

import com.paycraftsystems.resources.ValidationHelper;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author paycraftsystems-i
 */
@Entity
@Table(name="payments_log")
public class PaymentSetup extends PanacheEntityBase implements Serializable
{
   ///private static  Logger LOGGER =  LoggerFactory.getLogger(PaymentSetup.class);
   
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="tid")
    public long tid;
    
    @Column(name="payment_desc")
    public String paymentDesc;
    
    
    @Column(name="amount_flex")
    public Boolean amountFixed;
    
    @Column(name="in_tax")
    public Boolean includeTax;
 
    @Column(name="min_amount")
    public double min_amount;
    
    @Column(name="tax_amount")
    public double tax_amount;
    
    @Column(name="amount")
    public double amount;
    
    @Column(name="fee")
    public double fee;
    
    @Column(name="status")
    public String status;
    
    
    @Column(name="created_by")
    public long createdBy;
    
    @Column(name="beneficiary_id")
    public long beneficiaryId;
    
    @Column(name="beneficiary_name")
    public String beneficiaryName;
    
    @Column(name="payment_collection_account")
    public String paymentAccount;
    
    @Column(name="payment_collection_account_name")
    public String paymentCollectionAccountName;
    
    @Column(name="created_by_str")
    public String createdByStr;
    
    @Column(name="payee_id")
    public String payeeId;
     
    @Column(name="payee_id_label")
    public String payeeIdLabel;
    
    @Column(name="product_id")
    public String productId;
    
    @Column(name="created_date")
    public LocalDateTime createdDate;
    
    @Column(name="updated_by")
    public long updatedBy;
    
    @Column(name="updated_by_str")
    public String updatedByStr;
    
    @Column(name="updated_date")
    public LocalDateTime updatedDate;
    
    
    @Column(name="authorized_date")
    public LocalDateTime authorizedDate;
    
    @Column(name="authorized_by")
    public long authorizedBy;
    
    
    @Column(name="authorized_by_str")
    public String authorizeByStr;
    
   
    
    public JsonObject toSubJson(String msisdn)
    {
        JsonObjectBuilder job = Json.createObjectBuilder();
        ValidationHelper rh = new ValidationHelper();
        try 
        {
            job.add("tid", this.tid)
               .add("amount", rh.toDefault(this.amount))
               .add("productId", rh.toDefault(this.productId))
               .add("paymentDesc", rh.toDefault(this.paymentDesc))
               .add("paymentAccount", rh.toDefault(this.paymentAccount))
               .add("paymentCollectionAccountName", rh.toDefault(this.paymentCollectionAccountName))
               .add("status", rh.toDefault(this.status))
               .add("beneficiaryId", rh.toDefault(this.beneficiaryId))
               .add("beneficiaryName", rh.toDefault(this.beneficiaryName))
               .add("createdBy", rh.toDefault(this.createdBy))
               .add("createdByStr", rh.toDefault(this.createdByStr))
               .add("createdDate", rh.toDefault(this.createdDate))
               .add("updatedBy", rh.toDefault(this.updatedBy))
               .add("updatedDate", rh.toDefault(this.updatedDate))
               .add("updatedByStr", rh.toDefault(this.updatedByStr))
               .add("authorizedBy", rh.toDefault(this.authorizedBy))
               .add("authorizeByStr", rh.toDefault(this.authorizeByStr))
               .add("authorizedDate", rh.toDefault(this.authorizedDate));
               
            
        } catch (Exception e) {
       
            
           // throw new Exception(e);
        
        }
      
     return job.build();
    }
    
     public JsonObject toJson()
    {
        JsonObjectBuilder job = Json.createObjectBuilder();
        ValidationHelper rh = new ValidationHelper();
        try 
        {
            job.add("tid", this.tid)
               .add("amount", rh.toDefault(this.amount))
               .add("productId", rh.toDefault(this.productId))
               .add("paymentAccount", rh.toDefault(this.paymentAccount))
               .add("paymentCollectionAccountName", rh.toDefault(this.paymentCollectionAccountName))
               .add("paymentDesc", rh.toDefault(this.paymentDesc))
               .add("status", rh.toDefault(this.status))
               .add("beneficiaryId", rh.toDefault(this.beneficiaryId))
               .add("beneficiaryName", rh.toDefault(this.beneficiaryName))
               //.add("providerName", rh.toDefault(this.providerName))
               .add("createdBy", rh.toDefault(this.createdBy))
               .add("createdByStr", rh.toDefault(this.createdByStr))
               .add("createdDate", rh.toDefault(this.createdDate))
               .add("updatedBy", rh.toDefault(this.updatedBy))
               .add("updatedDate", rh.toDefault(this.updatedDate))
               .add("updatedByStr", rh.toDefault(this.updatedByStr))
               .add("authorizedBy", rh.toDefault(this.authorizedBy))
               .add("authorizeByStr", rh.toDefault(this.authorizeByStr))
               .add("authorizedDate", rh.toDefault(this.authorizedDate));
               
            
        } catch (Exception e) {
       
            
           // throw new Exception(e);
        
        }
      
     return job.build();
    }
    
     public JsonObject toJsonTrim(String msisdn){
        JsonObjectBuilder job = Json.createObjectBuilder();
        ValidationHelper rh = new ValidationHelper();
        try 
        {
            //DAILY ONETIME N100.00 /1 day
            job.add("tid", this.tid)
               .add("amount", rh.toDefault(this.amount))
               .add("productId", rh.toDefault(this.productId))
               .add("paymentDesc", rh.toDefault(this.paymentDesc))
               .add("msisdn", rh.toDefault(msisdn));
               
            
        } catch (Exception e) {
       
            
           // throw new Exception(e);
        
        }
      
     return job.build();
    }
     
    public JsonObject toJsonTrim(){
        JsonObjectBuilder job = Json.createObjectBuilder();
        ValidationHelper rh = new ValidationHelper();
        try 
        {
            //DAILY ONETIME N100.00 /1 day
            job.add("tid", this.tid)
               .add("amount", rh.toDefault(this.amount))
               .add("productId", rh.toDefault(this.productId))
               .add("paymentDesc", rh.toDefault(this.paymentDesc));
               
            
        } catch (Exception e) {
       
            
           // throw new Exception(e);
        
        }
      
     return job.build();
    }

   
}
