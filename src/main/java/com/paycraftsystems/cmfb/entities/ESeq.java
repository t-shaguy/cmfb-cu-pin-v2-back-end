/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.io.Serializable;
import java.math.BigInteger;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author root
 */
@Entity
@Table(name = "e_seq")
@NamedQueries({
   @NamedQuery(name = ESeq.ALL, query = "SELECT e from ESeq e"),
@NamedQuery(name = ESeq.BY_SEQCODE, query = "from ESeq  where seqCode = :passed")})
public class ESeq extends PanacheEntityBase implements Serializable { // 
    
    public static final String ALL = "ESeq.findAll";
    public static final String BY_SEQCODE = "ESeq.findBySeqCode";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TID")
    public Long tid;
    @Column(name = "SEQ_CODE")
    public String seqCode;
    @Column(name = "LAST_SEQ")
    public BigInteger lastSeq;
    @Column(name = "LENGTH")
    public Integer length;
   
    public static ESeq findBySeqCode(String code)
    {
        return find("seqCode", code).firstResult();
    }
    
    @Override
    public String toString() {
        return "ESeq{" + "tid=" + tid + ", seqCode=" + seqCode + ", lastSeq=" + lastSeq + ", length=" + length + '}';
    }

    
}
