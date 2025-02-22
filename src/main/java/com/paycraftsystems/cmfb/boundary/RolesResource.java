/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.boundary;


import com.paycraftsystems.cmfb.controller.RolesInfoHelper;
import com.paycraftsystems.cmfb.dto.FilterRequest;
import com.paycraftsystems.cmfb.dto.RolesInfoEditRequest;
import com.paycraftsystems.cmfb.dto.RolesInfoRequest;
import com.paycraftsystems.cmfb.dto.response.RolesResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author paycraftsystems-i
 */
@Path("roles")
@ApplicationScoped
public class RolesResource implements Serializable {
    
  private static Logger LOGGER = LoggerFactory.getLogger(RolesResource.class);
    
    @Inject
    RolesInfoHelper roles;
   
    
    @GET
    @Path("ping")
    public Response doPing() {
       LOGGER.info(" --- doPing json = ");
     return  Response.ok().entity(Json.createObjectBuilder().add("errorDesc", "it works").build()).build();//roles.doCreate(json);
    }
    
    @POST
    @Path("create")
    //@JWTTokenNeeded
    public RolesResponse doCreateRoleUSX(@Valid RolesInfoRequest json) {
       LOGGER.info(" --- doCreateRoleXOX json = " +   json);
     return roles.doLog(json);
    }
    
    @POST
    @Path("edit")
    //@JWTTokenNeeded
    public RolesResponse doEditTransactionLimitUSX(@Valid RolesInfoEditRequest json) {
    
    return roles.doSync(json); //jsonStr).doSync(
    }
    
    @POST
    @Path("approve")
    //@JWTTokenNeeded
    public RolesResponse doApproveTransactionLimitUSX(@Valid RolesInfoEditRequest json) {
    
     return roles.doApprove(json);
    }
    
    @POST
    @Path("disable")
    //@JWTTokenNeeded
    public RolesResponse doDisableTransactionLimitUSX(@Valid RolesInfoEditRequest json) {
    
    return roles.doDelete(json);
    }
    
    @POST
    @Path("list")
    //@JWTTokenNeeded
    public RolesResponse doListAllRolesXOXNP() {
    
      return roles.doAllList();
    }
    
    @POST
    @Path("list-roles-search")
    //@JWTTokenNeeded
    public RolesResponse doLoadRolesSearchXOX(@Valid FilterRequest json) {
        LOGGER.info("- doLoadRolesSearchXOX -- "+json);
        /*
        JsonObjectBuilder job = Json.createObjectBuilder();
        long status = -1;
        JsonArrayBuilder jar = Json.createArrayBuilder();
        JsonArray jarz = null;
        ResourceHelper resourceH = new ResourceHelper();
        */
        //try 
        //{
            return roles.doListRoles(json);
            /*
            GenericFilterObject fromJson = JsonbBuilder.create().fromJson(json.toString(), GenericFilterObject.class);
           
            
                            if(!resourceH.doValidateLDT(fromJson.fromDate))
                            {
                                 
                                 return Response.status(ErrorCodes.INVALID_START_DATE).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(ErrorCodes.INVALID_START_DATE)).build()).build();
                            }
                            else if(!resourceH.doValidateLDT(fromJson.toDate))
                            {

                                return Response.status(ErrorCodes.INVALID_END_DATE).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(ErrorCodes.INVALID_END_DATE)).build()).build();
                            }
                            else if(resourceH.strToLDT(fromJson.toDate).isBefore( resourceH.strToLDT(fromJson.fromDate)))
                            {
                                  return Response.status(ErrorCodes.DATE_DISPARITY).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(ErrorCodes.DATE_DISPARITY)).build()).build();     
                            }
                            else if(fromJson.pageId < 1 || fromJson.pageSize < 1)// || !resourceH.isValidWithLen(fromJson.getPhoneNumberPri().replaceAll("\\+", ""), 13))
                            {
                                return Response.status(ErrorCodes.INVALID_PAGE_ID_OR_FETCH_SIZE).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(ErrorCodes.INVALID_PAGE_ID_OR_FETCH_SIZE)).build()).build();
                            }
                            else
                            {
                                        JsonObject trxJson = doPullRolesSearch(resourceH.strToLDT(fromJson.fromDate), resourceH.strToLDT(fromJson.toDate),  fromJson.searchKey,  fromJson.searchKeyValue, fromJson.pageId, fromJson.pageSize);
                                        //doPullDisputesCount(LocalDateTime fromDate, LocalDateTime toDate, String searchKey)
                                        if(trxJson != null)
                                        {
                                             return Response.ok().entity(trxJson).build(); 

                                        }
                                        else
                                        {
                                            return Response.status(ErrorCodes.DATABASE_ERROR).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(ErrorCodes.DATABASE_ERROR)).build()).build();
                                        }
                            }
            
           
            
        }
        catch(HelloException e)
        {
            System.out.println(" ????? ");
           return Response.status(e.getResponse().getStatus()).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(e.getResponse().getStatus())).build()).build();
        }
        catch(WebApplicationException e)
        {
             System.out.println(" !!! ");
           return Response.status(e.getResponse().getStatus()).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doDesc(e.getResponse().getStatus())).build()).build();
        }
        catch (Exception e) {
       
                LOGGER.error(" -!!- doLoadTransactionLimitsSearchXOX Exception -- ",e);
                
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                     throw new HelloException(ErrorCodes.DATABASE_ERROR, e.getMessage());
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     throw new HelloException(ErrorCodes.IO_EXCEPTION, e.getMessage());
                }
                else
                {
                     throw new HelloException(ErrorCodes.SYSTEM_ERROR, e.getMessage());
                }
        }
         */
        
    }
    /*
    public JsonObject  doPullRolesSearch(LocalDateTime fromDate, LocalDateTime toDate,   String searchKey, String searchKeyValue,  int pageIndex, int pageSize)  throws Exception
    {
        LOGGER.info(" -- doPullRolesSearch -- fromdate -- "+fromDate+" -- toDate -- "+toDate+"  searchKeyValue -- "+searchKeyValue);
        JsonArrayBuilder jar = Json.createArrayBuilder();
        List<JsonObject> collect = new ArrayList<>();
        JsonObjectBuilder job = Json.createObjectBuilder();
        ResourceHelper rh = new ResourceHelper();
        List<RolesInfo> query =  null;
        try 
        {
            
            long dataCounta = doPullRolesCount(fromDate, toDate, searchKey, searchKeyValue);
            
            LOGGER.info(" COUNTA doPullRolesSearch--- "+dataCounta);
           
            long lastPage = (dataCounta/pageSize) +1;
            
            job.add("totalRecords", dataCounta);
            job.add("pageIndex", pageIndex);
            job.add("pageSize", pageSize);
            
            if("ALL".equalsIgnoreCase(searchKey))
            {
               query = (List<RolesInfo>)em.createNamedQuery(RolesInfo.ALL, RolesInfo.class).setParameter("from", fromDate).setParameter("to", toDate).setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
            }
            else if("description".equalsIgnoreCase(searchKey)) // && !rh.isValid(searchKey) || "NA".equals(searchKey)
            {
               query = (List<RolesInfo>)em.createNamedQuery(RolesInfo.BY_DESCRIPTION, RolesInfo.class).setParameter("passed", '%'+searchKeyValue+'%').setParameter("from", fromDate).setParameter("to", toDate).setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
            }
            else if("status".equalsIgnoreCase(searchKey) ) //&& !rh.isValid(searchKey) || "NA".equals(searchKey) && amount > 0
            {
               query = (List<RolesInfo>)em.createNamedQuery(RolesInfo.BY_STATUS, RolesInfo.class).setParameter("passed", searchKeyValue+'%').setParameter("from", fromDate).setParameter("to", toDate).setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
            }
            if(query !=null)query.stream().forEach(a->jar.add(a.toJson()));
            
             job.add("trxInfo", jar);
         
        } catch (Exception e) {
        
            LOGGER.error(" -- doPullRolesSearch ---Exception -- ",e);
            
            
                if((e.getCause() instanceof java.sql.SQLSyntaxErrorException) || ( e.getCause() instanceof  org.hibernate.HibernateException) || ( e.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) || ( e.getCause() instanceof  java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) || (e.getCause() instanceof jakarta.persistence.PersistenceException))
                {
                     throw new HelloException(ErrorCodes.DATABASE_ERROR, e.getMessage());
                }
                else if((e.getCause() instanceof java.io.IOException))
                {
                     throw new HelloException(ErrorCodes.IO_EXCEPTION, e.getMessage());
                }
                else
                {
                     throw new HelloException(ErrorCodes.SYSTEM_ERROR, e.getMessage());
                }
        
        }
      return job.build();  
    }
    */
    /*
    public long  doPullRolesCount(LocalDateTime fromDate, LocalDateTime toDate, String searchKey, String searchKeyValue) {
        LOGGER.info(" -- doPullRolesCount -- fromdate -- "+fromDate+" -- toDate -- "+toDate+" searchKey : "+searchKey+" searchKeyVaue "+searchKeyValue);
        JsonArrayBuilder jar = Json.createArrayBuilder();
        List<JsonObject> collect = new ArrayList<>();
       // String EXCLUDE []  = {"MINI_STATEMENT", "BALANCE_ENQUIRY"};
        ResourceHelper rh = new ResourceHelper();
        long counta = 0; 
        try 
        {
         
             if("all".equalsIgnoreCase(searchKey))
             {
               counta = (long)em.createNamedQuery(RolesInfo.ALL_COUNT).setParameter("from", fromDate).setParameter("to", toDate).getSingleResult();//.setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
             
             }
             else  if("description".equalsIgnoreCase(searchKey))
             {
               counta = (long)em.createNamedQuery(RolesInfo.BY_DESCRIPTION_COUNT).setParameter("from", fromDate).setParameter("to", toDate).setParameter("passed", searchKeyValue+'%').getSingleResult();//.setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
              
             }
             else if("status".equalsIgnoreCase(searchKey) ) //&& !rh.isValid(searchKey) || "NA".equals(searchKey) && amount > 0
             {
                 counta = (long)em.createNamedQuery(RolesInfo.BY_STATUS_COUNT).setParameter("from", fromDate).setParameter("to", toDate).setParameter("passed", searchKeyValue+'%').getSingleResult();//.setFirstResult((pageIndex -1) * pageSize).setMaxResults(pageSize).getResultList();
             }
            
             
        } catch (Exception e) {
        
            LOGGER.error(" -- doPullRolesCount ---Exception -- ",e);
        
        }
      return counta;  
    }
    */
    
}
