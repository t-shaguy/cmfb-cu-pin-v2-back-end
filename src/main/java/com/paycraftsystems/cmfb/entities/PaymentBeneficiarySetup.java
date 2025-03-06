 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.ToString;

/**
 *
 * @author paycraftsystems-i
 */
@Entity
@Table(name="payment_beneficiary_log")
@ToString
public class PaymentBeneficiarySetup extends PanacheEntityBase implements Serializable
{
   ///private static  Logger LOGGER =  LoggerFactory.getLogger(PaymentSetup.class);
   
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="tid")
    public long tid;
    
    @Column(name="beneficiary_desc")
    public String beneficiaryDesc;
    
    @Column(name="beneficiary_name")
    public String beneficiaryName;
    
    @Column(name="beneficiary_address")
    public String beneficiaryAddress;
    
    @Column(name="beneficiary_contact_person")
    public String beneficiaryContactPerson;
    
    @Column(name="beneficiary_contact_person_email")
    public String beneficiaryContactPersonEmail;
    
   @Column(name="beneficiary_contact_person_mobile")
    public String beneficiaryContactPersonMobile;
  
    
    @Column(name="status")
    public String status;
    
   // @Column(name="status_str")
   // public String statusStr;
    
    
    @Column(name="created_by")
    public long createdBy;
    
    @Column(name="created_by_str")
    public String createdByStr;
    
    
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
    
   
    
   
   
}
