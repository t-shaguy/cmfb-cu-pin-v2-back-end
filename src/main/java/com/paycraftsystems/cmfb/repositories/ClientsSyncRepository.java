/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.repositories;



import com.paycraftsystems.cmfb.dto.ClientsInfoObj;
import com.paycraftsystems.cmfb.entities.ClientsSync;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.math.BigInteger;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author paycraftsystems-i
 */
@ApplicationScoped
public class ClientsSyncRepository implements PanacheRepository<ClientsSync> {
    
    
    private static Logger LOGGER = LoggerFactory.getLogger(ClientsSyncRepository.class);
    
    @Inject
    SysDataRepository sysDataRepository;
    
   
    
    
    public ClientsSync doFindByName(String clientId)
    {
        return find("clientId", clientId).firstResult();
    }
    
    public  ClientsSync  doGetClientByID(String client) throws Exception {
        
        ClientsSync cli = null;
        try 
        {
             cli = find("clientId",client).firstResult();
        } 
        catch (Exception e) {
              LOGGER.error("EXCEPTION @ doGetClientByID  ",e);
              throw new Exception(e);
        }
     return cli;
    }
    /*
    public ClientsSync doFindByName(long id, String name, String code)
    {
        return find("tid = ?1 and providerName = ?2 and providerCode = ?3",id,  name, code).firstResult();
    }
    */
    
    public String doFindDescById(long tid)
    {
        
        ClientsSync findById = ClientsSync.findById(tid);
       
        return (findById !=null)?findById.customerCode:"NA";
    }
    //providerName, providerCode, servicesDesc, contactPerson, contactEmail, contactNumber, state_str, stateStr, countryStr, providerCategoryStr, 
    public PanacheQuery<ClientsSync> findByParams(long status, LocalDateTime from, LocalDateTime today){
       return ClientsSync.find("status = ?1 and syncDate between ?2 and ?3 order by syncDate desc", status, from, today);
    }
    
    public PanacheQuery<ClientsSync> findByParams(long status,  LocalDateTime from, LocalDateTime today, String searchKey){
       return ClientsSync.find("status = ?1 and syncDate between ?2 and ?3 and (clientId like ?4 or  partnerId like ?4 or  customerCode like ?4) order by syncDate desc", status, from, today, searchKey+'%');
    }
    
    public PanacheQuery<ClientsSync> findByParams(LocalDateTime from, LocalDateTime today){
       return ClientsSync.find("syncDate between ?1 and ?2 order by syncDate desc",from, today);
    }
    
    
    
    public ClientsSync doSync(ClientsSync clientObj) throws Exception {
      int prodOrDev = Integer.parseInt(sysDataRepository.doLookUpByNameStr("INTEGRATION-MODE", "0"));
       
        System.out.println("doSync = clientObj" + clientObj);
        ClientsSync doSyncClient = null;
        try 
        {
            /*
            Response doLookupPartnerByCode = partnersClient.doLookupPartnerByCode(clientObj.customerCode);
            System.out.println("@@ doLookupPartnerByCode = " + doLookupPartnerByCode.getStatus());
            if(doLookupPartnerByCode == null || doLookupPartnerByCode.getStatus() != ErrorCodes.SUCCESSFUL)
            {
                throw new PeeloException(ErrorCodes.INVALID_CUSTOMER_CODE, ErrorCodes.doErroDesc(ErrorCodes.INVALID_CUSTOMER_CODE, prodOrDev), prodOrDev);
            }
            */
            /*
            ClientsSync obj = new ClientsSync();
            
            obj.status = BigInteger.ONE.longValue();
            obj.clientId = clientObj.clientName;
            obj.syncDate = LocalDateTime.now();
            obj.clientClass = (clientObj.clientCategory < 1)?3:clientObj.clientCategory; 
            obj.partnerId = clientObj.partnerID==null?clientObj.customerCode:clientObj.partnerID;
            obj.customerCode = clientObj.customerCode;
            obj.status = 3l;
            obj.clientType = clientType;
            */
            doSyncClient =  PanacheEntityBase.getEntityManager().merge(clientObj);
           
        } catch (Exception e) {
        
            LOGGER.error(" ClientsSync doSync EXCEPTION - @@",e);
            
            throw new Exception(e);
        
        }
        
     return doSyncClient;
    }
    
    
    public ClientsSync doSync(ClientsInfoObj clientObj,String  clientType) throws Exception {
      int prodOrDev = Integer.parseInt(sysDataRepository.doLookUpByNameStr("INTEGRATION-MODE", "0"));
       
        System.out.println("doSync = clientObj" + clientObj);
         ClientsSync doSyncClient = null;
        try 
        {
            /*
            Response doLookupPartnerByCode = partnersClient.doLookupPartnerByCode(clientObj.customerCode);
            System.out.println("@@ doLookupPartnerByCode = " + doLookupPartnerByCode.getStatus());
            if(doLookupPartnerByCode == null || doLookupPartnerByCode.getStatus() != ErrorCodes.SUCCESSFUL)
            {
                throw new PeeloException(ErrorCodes.INVALID_CUSTOMER_CODE, ErrorCodes.doErroDesc(ErrorCodes.INVALID_CUSTOMER_CODE, prodOrDev), prodOrDev);
            }
            */
            
            ClientsSync obj = new ClientsSync();
            
            obj.status = BigInteger.ONE.longValue();
            obj.clientId = clientObj.clientName;
            obj.syncDate = LocalDateTime.now();
            obj.clientClass = (clientObj.clientCategory < 1)?3:clientObj.clientCategory; 
            obj.partnerId = clientObj.partnerID==null?clientObj.customerCode:clientObj.partnerID;
            obj.customerCode = clientObj.customerCode;
            obj.status = 3l;
            obj.clientType = clientType;
            doSyncClient =  PanacheEntityBase.getEntityManager().merge(obj);
           
        } catch (Exception e) {
        
            LOGGER.error(" ClientsSync doSync EXCEPTION - @@",e);
            
            throw new Exception(e);
        
        }
        
     return doSyncClient;
    }
    
    
    
    
}
