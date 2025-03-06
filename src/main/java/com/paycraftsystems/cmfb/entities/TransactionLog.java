/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.entities;

import com.paycraftsystems.cmfb.dto.PaymentPreviewResponse;
import com.paycraftsystems.cmfb.dto.PaymentResponse;
import com.paycraftsystems.cmfb.dto.response.TransactionLogObj;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author root
 */

@Entity
@Table(name="trx_log")
@NamedQueries({
 @NamedQuery(name=TransactionLog.ALL_TRANSACTIONS,  query="Select p from TransactionLog p where p.createdDate between :from and :to  and p.transTypeStr not in :passed order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.ALL_TRANSACTIONS_BY_TELLER,  query="Select p from TransactionLog p where p.createdDate between :from and :to  and p.transTypeStr not in :passed and p.createdBy = :passed2 order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.ALL_TRANSACTIONS_COUNT_BY_TELLER,  query="Select count(p) from TransactionLog p where p.createdDate between :from and :to  and p.transTypeStr not in :passed and p.createdBy = :passed2 order by p.createdDate desc "),
 
 @NamedQuery(name=TransactionLog.ALL_TRANSACTIONS_COUNT,  query="Select count(p) from TransactionLog p where p.createdDate between :from and :to  and p.transTypeStr not in :passed order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.ALL_TRANSACTIONS_PENDING_AUTH,  query="Select p from TransactionLog p where p.createdDate between :from and :to  and p.respCode = '01' and p.transTypeStr not in :passed order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.ALL_TRANSACTIONS_COUNT_PENDING_AUTH,  query="Select count(p) from TransactionLog p where p.createdDate between :from  and :to and p.respCode = '01' and p.transTypeStr not in :passed order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_ACCOUNT,  query="Select p from TransactionLog p where p.createdDate between :from and :to  and p.transTypeStr like :passed and (p.destAccount = :passed2 or p.destAccount = :passed2) order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_ACCOUNT_BY_TELLER,  query="Select p from TransactionLog p where p.createdDate between :from and :to  and p.transTypeStr like :passed and (p.destAccount = :passed2 or p.destAccount = :passed2) and p.createdBy = :passed3 order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_ACCOUNT_COUNT_BY_TELLER,  query="Select count(p) from TransactionLog p where p.createdDate between :from and :to and p.respCode = '01' and p.transTypeStr = 'AUTH_EXTERNAL_FT'  and (p.destAccount = :passed2 or p.destAccount = :passed2) and p.createdBy = :passed3 order by p.createdDate desc "),
 
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_ACCOUNT_COUNT,  query="Select count(p) from TransactionLog p where p.createdDate between :from and :to and p.respCode = '01' and p.transTypeStr = 'AUTH_EXTERNAL_FT'  and (p.destAccount = :passed2 or p.destAccount = :passed2) order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_ACCOUNT_PENDING_AUTH,  query="Select p from TransactionLog p where p.createdDate between :from and :to and p.respCode = '01' and p.transTypeStr = 'AUTH_EXTERNAL_FT' and  (p.destAccount = :passed2 or p.destAccount = :passed2) order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_ACCOUNT_PENDING_AUTH_COUNT,  query="Select count(p) from TransactionLog p where p.createdDate between :from and :to and p.transTypeStr = 'AUTH_EXTERNAL_FT'  and (p.destAccount = :passed2 or p.destAccount = :passed2) order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_REQUESTID_PENDING_AUTH,  query="Select p from TransactionLog p where p.createdDate between :from and :to  and p.respCode = '01' and p.transTypeStr = 'AUTH_EXTERNAL_FT'  and p.requestId = :passed2 order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_REQUESTID_PENDING_AUTH_COUNT,  query="Select count(p) from TransactionLog p where p.createdDate between :from and :to  and p.respCode = '01' and p.transTypeStr = 'AUTH_EXTERNAL_FT'  and p.requestId = :passed2 order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_REQUESTID,  query="Select p from TransactionLog p where p.createdDate between :from and :to  and p.transTypeStr like :passed and p.requestId = :passed2 order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_REQUESTID_BY_TELLER,  query="Select p from TransactionLog p where p.createdDate between :from and :to  and p.transTypeStr like :passed and p.requestId = :passed2  and p.createdBy = :passed3 order by p.createdDate desc "),
 
 
 
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_REQUESTID_COUNT_BY_TELLER,  query="Select count(p) from TransactionLog p where p.createdDate between :from and :to and p.transTypeStr like :passed and p.requestId = :passed2 and p.createdBy = :passed3 order by p.createdDate desc "),

 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_REQUESTID_COUNT,  query="Select count(p) from TransactionLog p where p.createdDate between :from and :to and p.transTypeStr like :passed and p.requestId = :passed2 order by p.createdDate desc "),

@NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_AMOUNT_BY_TELLER,  query="Select p from TransactionLog p where p.createdDate between :from and :to   and p.transactionAmount = :passed2 and p.transTypeStr like :passed  and p.createdBy = :passed3 order by p.createdDate desc "),
 
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_AMOUNT,  query="Select p from TransactionLog p where p.createdDate between :from and :to   and p.transactionAmount = :passed2 and p.transTypeStr like :passed  order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_AMOUNT_COUNT,  query="Select count(p) from TransactionLog p where p.createdDate between :from and :to and p.transTypeStr like :passed and p.transactionAmount = :passed2 order by p.createdDate desc "),
 //@NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_AMOUNT_BY_TELLER,  query="Select count(p) from TransactionLog p where p.createdDate between :from and :to and p.transTypeStr like :passed and p.transactionAmount = :passed2  and p.createBy = :passed3 order by p.createdDate desc "),

 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_AMOUNT_PENDING_AUTH,  query="Select p from TransactionLog p where p.createdDate between :from and :to  and p.respCode = '01' and p.transTypeStr = 'AUTH_EXTERNAL_FT'  and p.transactionAmount = :passed2 and p.transTypeStr like :passed  order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_AMOUNT_PENDING_AUTH_COUNT,  query="Select count(p) from TransactionLog p where p.createdDate between :from and :to  and p.respCode = '01' and p.transTypeStr = 'AUTH_EXTERNAL_FT' and p.transTypeStr like :passed and p.transactionAmount = :passed2 order by p.createdDate desc "),


 //@NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_AMOUNT,  query="Select p from TransactionLog p where p.createdDate between :from and :to   and p.transactionAmount = :passed2 and p.transTypeStr like :passed  order by p.createdDate desc "),
 
// @NamedQuery(name=TransactionLog.TRANSACTIONS_WITHIN_DATE_AND_AMOUNT_COUNT,  query="Select count(p) from TransactionLog p where p.createdDate between :from and :to  and p.transactionAmount = :passed2 and p.transTypeStr like :passed  order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITH_GENERIC_SEARCHKEY_BY_TELLER,  query="Select p from TransactionLog p where p.createdDate between :from and :to  and  (p.customerMail like :passed2 or p.customerMobile like :passed2 or p.destAccount like :passed2 or p.destAccount like :passed2  or p.srcAccount like :passed2 or p.srcAccountName like :passed2 or p.destBankName like :passed2) and p.transTypeStr like :passed  and p.createdBy = :passed3  order by p.createdDate desc "),
 
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITH_GENERIC_SEARCHKEY,  query="Select p from TransactionLog p where p.createdDate between :from and :to  and  (p.customerMail like :passed2 or p.customerMobile like :passed2 or p.destAccount like :passed2 or p.destAccount like :passed2  or p.srcAccount like :passed2 or p.srcAccountName like :passed2 or p.destBankName like :passed2) and p.transTypeStr like :passed  order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITH_GENERIC_SEARCHKEY_COUNT,  query="Select count(p) from TransactionLog p where p.createdDate between :from and :to  and (p.customerMail like :passed2 or p.customerMobile like :passed2 or p.destAccount like :passed2 or p.srcAccount like :passed2   or p.srcAccount like :passed2 or p.srcAccountName like :passed2 or p.destBankName like :passed2) and p.transTypeStr like :passed  order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITH_GENERIC_SEARCHKEY_COUNT_BY_TELLER,  query="Select count(p) from TransactionLog p where p.createdDate between :from and :to  and (p.customerMail like :passed2 or p.customerMobile like :passed2 or p.destAccount like :passed2 or p.srcAccount like :passed2 or p.srcAccount like :passed2 or p.srcAccountName like :passed2 or p.destBankName like :passed2) and p.transTypeStr like :passed  and p.createdBy = :passed3 order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITH_GENERIC_SEARCHKEY_PENDING_AUTH,  query="Select p from TransactionLog p where p.createdDate between :from and :to  and p.respCode = '01' and p.transTypeStr = 'AUTH_EXTERNAL_FT' and  (p.customerMail like :passed2 or p.customerMobile like :passed2 or p.destAccount like :passed2 or p.destAccount like :passed2  or p.srcAccount like :passed2 or p.srcAccountName like :passed2 or p.destBankName like :passed2) and p.transTypeStr like :passed  order by p.createdDate desc "),
 @NamedQuery(name=TransactionLog.TRANSACTIONS_WITH_GENERIC_SEARCHKEY_PENDING_AUTH_COUNT,  query="Select count(p) from TransactionLog p where p.createdDate between :from and :to  and p.respCode = '01' and p.transTypeStr = 'AUTH_EXTERNAL_FT' and (p.customerMail like :passed2 or p.customerMobile like :passed2 or p.destAccount like :passed2 or p.srcAccount like :passed2 or p.srcAccount like :passed2 or p.srcAccountName like :passed2 or p.destBankName like :passed2) and p.transTypeStr like :passed  order by p.createdDate desc "),


})
@ToString
public class TransactionLog  extends PanacheEntityBase implements Serializable{
    
    
    public static final  String  ALL_TRANSACTIONS_BY_TELLER = "TransactionLog.AllDisputedTransactionsByTeller";
    public static final  String  ALL_TRANSACTIONS = "TransactionLog.AllDisputedTransactions";
    public static final  String  ALL_TRANSACTIONS_COUNT = "TransactionLog.AllTransactionsCount";
    public static final  String  ALL_TRANSACTIONS_COUNT_BY_TELLER = "TransactionLog.AllTransactionsCountByTeller";
    public static final  String  ALL_TRANSACTIONS_PENDING_AUTH = "TransactionLog.AllDisputedTransactionsPendingAuth";
    public static final  String  ALL_TRANSACTIONS_COUNT_PENDING_AUTH = "TransactionLog.AllTransactionsCountPendingAuth";
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_ACCOUNT = "TransactionLog.TransactionsWithinDateAndAccount";
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_ACCOUNT_BY_TELLER = "TransactionLog.TransactionsWithinDateAndAccountByTeller";
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_ACCOUNT_COUNT = "TransactionLog.TransactionsWithinDateAndAccounsCount";
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_ACCOUNT_COUNT_BY_TELLER = "TransactionLog.TransactionsWithinDateAndAccounsCountByTeller";
    
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_ACCOUNT_PENDING_AUTH = "TransactionLog.TransactionsWithinDateAndAccountPendingAuth";
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_ACCOUNT_PENDING_AUTH_COUNT = "TransactionLog.TransactionsWithinDateAndAccounsCountPendingAuth";
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_REQUESTID = "TransactionLog.TransactionsWithinDateAndRequestId";
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_REQUESTID_BY_TELLER = "TransactionLog.TransactionsWithinDateAndRequestIdBYTeller";
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_REQUESTID_COUNT = "TransactionLog.TransactionsWithinDateAndRequestIdCount";
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_REQUESTID_COUNT_BY_TELLER = "TransactionLog.TransactionsWithinDateAndRequestIdCountByTeller";
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_REQUESTID_PENDING_AUTH = "TransactionLog.TransactionsWithinDateAndRequestIdPendingAuth";
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_REQUESTID_PENDING_AUTH_COUNT = "TransactionLog.TransactionsWithinDateAndRequestIdCountPendingAuth";
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_AMOUNT = "TransactionLog.TransactionsWithDateAndAmount";
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_AMOUNT_BY_TELLER = "TransactionLog.TransactionsWithDateAndAmountByTeller";
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_AMOUNT_COUNT = "TransactionLog.TransactionsWithinDataAndAmountCount";
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_AMOUNT_COUNT_BY_TELLER = "TransactionLog.TransactionsWithinDataAndAmountCountByTeller";
    
    public static final  String TRANSACTIONS_WITHIN_DATE_AND_AMOUNT_PENDING_AUTH = "TransactionLog.TransactionsWithDateAndAmountPendingAuth";
    public static final  String  TRANSACTIONS_WITHIN_DATE_AND_AMOUNT_PENDING_AUTH_COUNT = "TransactionLog.TransactionsWithinDataAndAmountCountPendingAuth";
    
    public static final  String  TRANSACTIONS_WITH_GENERIC_SEARCHKEY_BY_TELLER = "TransactionLog.TransactionsWithGenericSearchKeyByTeller";
    public static final  String  TRANSACTIONS_WITH_GENERIC_SEARCHKEY = "TransactionLog.TransactionsWithGenericSearchKey";
    public static final  String  TRANSACTIONS_WITH_GENERIC_SEARCHKEY_COUNT = "TransactionLog.TransactionsWithGenericSearchKeyCount";
    public static final  String  TRANSACTIONS_WITH_GENERIC_SEARCHKEY_COUNT_BY_TELLER = "TransactionLog.TransactionsWithGenericSearchKeyCountByTeller";
    public static final  String  TRANSACTIONS_WITH_GENERIC_SEARCHKEY_PENDING_AUTH = "TransactionLog.TransactionsWithGenericSearchKeyPendingAuth";
    public static final  String  TRANSACTIONS_WITH_GENERIC_SEARCHKEY_PENDING_AUTH_COUNT = "TransactionLog.TransactionsWithGenericSearchKeyPendingAuthCount";
    
    
    private static Logger LOGGER =  LoggerFactory.getLogger(TransactionLog.class);
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    public Long tid;
    
    @Column(name="src_account")
    public String srcAccount;
    
    @Column(name="src_account_name")
    public String srcAccountName;
    
    @Column(name="dest_account")
    public String destAccount;
    
    @Column(name="dest_account2")
    public String destAccount2;
    
    @Column(name="transaction_amount")
    public double transactionAmount;
    
    @Column(name="tax_amount")
    public double taxAmount;
    
    @Column(name="payment_fee")
    public double paymentFee;
    
    @Column(name="resp_code")
    public String respCode;
    
    @Column(name="custom_response_param_1")
    public String customResponseParam1;
    
    @Column(name="custom_response_param_2")
    public String customResponseParam2;
    
    @Column(name="beneficiary_name")
    public String beneficiaryName;
    
    @Column(name="product_id")
    public String productId;
    
    @Column(name="created_by")
    public long createdBy;
    
    @Column(name="created_by_str")
    public String createByStr;
    
    @Column(name="created_date")
    public LocalDateTime createdDate;
   
    @Column(name="customer_email")
    public String customerMail;
    
    @Column(name="customer_fullname")
    public String customerFullname;
    
    @Column(name="customer_address")
    public String customerAddress;
    
    @Column(name="customer_mobile")
    public String customerMobile;
    
    @Column(name="updated_by")
    public long updatedBy;
    
    @Column(name="updated_by_str")
    public String updatedByStr;
    
     @Column(name="updated_date")
    public LocalDateTime updatedDate;
    
    @Column(name="authorized_date")
    public LocalDateTime authDate;
    
    @Column(name="authorized_by")
    public long authorizedBy; 
    
    @Column(name="authorized_by_str")
    public String authorizedByStr;
    
    
    @Column(name="REQX_STR")
    public String requestStr;
    
    @Column(name="RESP_STR")
    public String responseStr;
    
    @Column(name="partner_resp")
    public String partnerResp;
    
    @Column(name="TRX_STATUS")
    public String transStatus;
    
    @Column(name="cba_status")
    public String cbaStatus;
    
    @Column(name="SRC_BANK")
    public String srcBank;
    
    
    @Column(name="DEST_BANK")
    public String destBank;
    
    @Column(name="src_bank_code")
    public String sourceInstitutionCode;
    
    @Column(name="transaction_reference")
    public String transactionReference;
    
    @Column(name="DEST_BANK_NAME")
    public String destBankName;
    
    @Column(name="payer_id")
    public String payerId;
    
    @Column(name="partner_response_code")
    public String partnerResponseCode;
    
    @Column(name="transaction_id")
    public String transactionId;
    
    @Column(name="tenant_code")
    public String tenantCode;
    
    @Column(name="TRANS_TYPE_STR")
    public String transTypeStr;
    
    @Column(name="payment_code")
    public String paymentCode;
    
    @Column(name="ext_response_code")
    public String extResponseCode;
    
    @Column(name="ext_response_desc")
    public String extResponseDesc;
    
    
    @Column(name="TRX_FEE")
    public double trxFee;
    
    @Column(name="OUR_TRANSID")
    public String requestId;
    
    
    @Column(name="EXT_TRANS_NO")
    public String exTransId;
    
    @Column(name="trans_desc")
    public String transDesc;
    
    @Column(name="payment_reference")
    public String paymentReference;
    
    
    @Column(name="biller_id")
    public String billerId;
    
    
    
    public TransactionLogObj toObj() {
        
        return new TransactionLogObj(this.tid,this.srcAccount, this.destAccount, this.transactionAmount,this.paymentFee,this.respCode,this.createdBy,this.createByStr,this.createdDate,this.customerMail,this.customerAddress,this.customerMobile,this.updatedBy,this.updatedByStr, this.authDate,this.authorizedBy,this.authorizedByStr,this.requestStr,this.responseStr,this.transStatus,this.cbaStatus,this.srcBank,this.destBank,this.sourceInstitutionCode,this.transactionReference,this.destBankName,this.payerId,this.transactionId,this.tenantCode,this.transTypeStr,this.paymentCode,this.extResponseCode,this.extResponseDesc,this.trxFee,this.requestId,this.exTransId,this.transDesc,this.paymentReference,this.billerId);

    }
    
    
    public PaymentPreviewResponse toPreviewObj() {
        
        
        return new PaymentPreviewResponse(this.tid, this.trxFee, this.taxAmount,  this.transactionAmount, this.createdBy, this.createByStr,this.beneficiaryName,this.customerFullname,this.customerMail,this.customerAddress ,  this.customerMobile, this.payerId, this.partnerResp);
    }
    
    
    public PaymentResponse toCUReceiptObj() {
        
        return new PaymentResponse(this.tid,  this.transactionAmount, this.createdBy, this.createByStr, this.beneficiaryName,this.customerFullname,this.customerMail,this.customerAddress,  this.customerMobile, this.payerId, this.customResponseParam1, this.customResponseParam2, this.partnerResponseCode !=null && this.partnerResponseCode.trim().equals("1")?"SUCCESSFUL":"FAILED");

    }

    
    
 
}
