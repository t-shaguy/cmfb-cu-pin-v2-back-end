/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.boundary;

import com.paycraftsystems.cmfb.controller.SysDataController;
import com.paycraftsystems.cmfb.dto.ApproveOrDeleteRequest;
import com.paycraftsystems.cmfb.dto.FilterSysDataRequest;
import com.paycraftsystems.cmfb.dto.SysDataEditRequest;
import com.paycraftsystems.cmfb.dto.SysDataRequest;
import com.paycraftsystems.cmfb.dto.response.SysDataResponse;
import com.paycraftsystems.cmfb.filters.JWTTokenNeeded;
import io.smallrye.common.constraint.NotNull;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author paycraftsystems-i
 */
@Path("config")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SysDataResource implements Serializable {
    
  private static Logger LOGGER = LoggerFactory.getLogger(SysDataResource.class);
    
    //@Inject
    //AdminServicesController adminServicesController;
    
    @Inject
    SysDataController sysData;
    
    @POST
    @Path("/create")
    @JWTTokenNeeded
    public SysDataResponse doCreateUSX(@Valid @NotNull  SysDataRequest request ) {
    
       return sysData.doCreate(request);
        
    }
    
    
    @POST
    @Path("/edit")
    @JWTTokenNeeded
    public SysDataResponse doEditUSX(@Valid  SysDataEditRequest request) {
        
      return sysData.doEdit(request);
    }
    
    @POST
    @Path("approve")
    @JWTTokenNeeded
    public SysDataResponse doApproveUSX(@Valid @NotNull ApproveOrDeleteRequest request) {
    
       return sysData.doApprove(request);
          
    }
    
    @POST
    @Path("delete")
    @JWTTokenNeeded
    public SysDataResponse doDeleteUSX(@Valid @NotNull ApproveOrDeleteRequest request) {
    
     return sysData.doDelete(request);
    
    }
    
    @POST
    @Path("list")
    @JWTTokenNeeded
    public SysDataResponse doLoadSearchUSX(@Valid FilterSysDataRequest json) {
        LOGGER.info("- doLoadSearchXOX -- "+json);
        /*
        JsonObjectBuilder job = Json.createObjectBuilder();
        long status = -1;
        JsonArrayBuilder jar = Json.createArrayBuilder();
        JsonArray jarz = null;
        ResourceHelper resourceH = new ResourceHelper();
        */
       return  sysData.doLoadList(json);
           
    }
    
    
}
