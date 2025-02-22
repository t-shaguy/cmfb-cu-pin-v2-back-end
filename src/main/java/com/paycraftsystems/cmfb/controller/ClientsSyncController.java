/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.controller;


import com.paycraftsystems.cmfb.dto.ClientsInfoObj;
import com.paycraftsystems.cmfb.entities.ClientsSync;
import com.paycraftsystems.cmfb.repositories.ClientsSyncRepository;
import com.paycraftsystems.exceptions.CMFBException;
import com.paycraftsystems.resources.ErrorCodes;
import io.quarkus.hibernate.orm.panache.Panache;
import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.find;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author paycraftsystems-i
 */

@ApplicationScoped
public class ClientsSyncController {
    
   private static  Logger LOGGER =  LoggerFactory.getLogger(ClientsSyncController.class);
    
    @Inject
    ClientsSyncRepository clientsSyncRepository;
    
    /*
    @Inject
    @RestClient
    PartnersClient partnersClient;
    
    */
    
    @Inject
    SysDataController sysDataController;
    
    
    @Transactional
    public  ClientsSync  doLogClient(ClientsSync obj) {
        LOGGER.info(" -- ClientsSync  doLogClient -- "+obj);
        ClientsSync cli = null;
        try 
        {
             ClientsSync doGetClientByID = doGetClientByID(obj.clientId);
             
             if(doGetClientByID  == null)
             {
                 cli = Panache.getEntityManager().merge(obj);
             }
             else
             {
                 return null;
             }
        } 
        catch (Exception e) {
       
            LOGGER.info(" -- doLogClient -- ",e);
        }
     return cli;
    }
    
    public  ClientsSync  doGetClientByID(String client) throws Exception {
        
        ClientsSync cli = null;
        try 
        {
             cli = clientsSyncRepository.doGetClientByID(client);// find("clientId",client).firstResult();
        } 
        catch (Exception e) {
              LOGGER.error("EXCEPTION @ doGetClientByID + ",e);
              throw new Exception(e);
        }
     return cli;
    }
    
    @Transactional
    public  ClientsSync  doSyncClient(ClientsSync obj, int prodOrDev) throws CMFBException {
       //int prodOrDev = Integer.parseInt(sysDataRepository.doFindOrDefaultTo("INTEGRATION-MODE", "0"));
       
        LOGGER.info(" -- ClientsSync  doSyncClient -- "+obj);
        ClientsSync cli = null;
        try
        {
             ClientsSync doGetClientByID = doGetClientByID(obj.clientId);
             LOGGER.info(" -- ClientsSync  doGetClientByID -- "+doGetClientByID);
             if(doGetClientByID  != null)
             {
                 cli = Panache.getEntityManager().merge(obj);
             }
             else
             {
                 throw new CMFBException(ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND), prodOrDev);
             }
        } 
        catch (Exception e) {
       
            LOGGER.info(" --ClientsSync doSyncClient -- ",e);
        }
     return cli;
    }
    
    @Transactional
    public static ClientsSync  doSync(ClientsSync obj) throws CMFBException, Exception {
        LOGGER.info(" -- ClientsSync  doSync -- "+obj);
        ClientsSync cli = null;
        try
        {
             //ClientsSync doGetClientByID = doGetClientByID(obj.clientId);
            // LOGGER.info(" -- ClientsSync  doSync -- "+doGetClientByID);
             if(obj  != null)
             {
                 cli = Panache.getEntityManager().merge(obj);
             }
            
        } 
        catch (Exception e) {
       
            LOGGER.info(" --ClientsSync doSyncClient -- ",e);
            throw new  Exception(e);
        }
     return cli;
    }
    
    public ClientsSync doSync(ClientsInfoObj clientObj,String  clientType) throws Exception {
        System.out.println("doSync = clientObj" + clientObj);
         ClientsSync doSyncClient = null;
        int prodOrDev = Integer.parseInt(sysDataController.doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
       
        try 
        {
            /*
            Response doLookupPartnerByCode = partnersClient.doLookupPartnerByCode(clientObj.customerCode);
            System.out.println("@@ doLookupPartnerByCode = " + doLookupPartnerByCode.getStatus());
            if(doLookupPartnerByCode == null || doLookupPartnerByCode.getStatus() != ErrorCodes.SUCCESSFUL)
            {
                throw new HelloException(ErrorCodes.INVALID_CUSTOMER_CODE, ErrorCodes.doErroDesc(ErrorCodes.INVALID_CUSTOMER_CODE, prodOrDev), prodOrDev);
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
            
            doSyncClient =  clientsSyncRepository.doSync(obj);
           
        } catch (Exception e) {
        
            LOGGER.error(" ClientsSync doSync EXCEPTION - @@",e);
            
            throw new Exception(e);
        
        }
        
     return doSyncClient;
    }
    /*
    public static ClientsSync  doGetClientByID(String client) throws Exception {
        
        ClientsSync cli = null;
        try 
        {
             cli = find("clientId",client).firstResult();
        } 
        catch (Exception e) {
              LOGGER.error("EXCEPTION @ doGetClientByID ",e);
              throw new Exception(e);
        }
     return cli;
    }
    */
    public static List<ClientsSync>  doGetClientByCustomer(String customerId) throws Exception {
        
        List<ClientsSync> cli = new ArrayList<>();
        try 
        {
             cli = find("customerCode",customerId).list();
        } 
        catch (Exception e) {
              LOGGER.error("EXCEPTION @ doGetClientByCustomer ",e);
              throw new Exception(e);
        }
     return cli;
    }
    
    
    
}
