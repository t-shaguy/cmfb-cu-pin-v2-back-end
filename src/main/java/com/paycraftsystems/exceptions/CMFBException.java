/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.exceptions;

/**
 *
 * @author root
 */
import com.paycraftsystems.resources.ErrorCodes;
import jakarta.json.Json;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;

public class CMFBException extends WebApplicationException {

    public CMFBException(String message, Throwable cause) {
        super(Response.status(400).header("message", message).header("cause", cause.getMessage()).build());
    }
    
    public CMFBException(int code, int dovOrProd, Throwable cause) {
        super(Response.status(400).entity(Json.createObjectBuilder().add("code", code).add("message", ErrorCodes.doErrorDesc(code))).build());
    }
    
    public CMFBException(String message) {
        super(Response.status(400).header("message", message).build());
    }
    
    public CMFBException(int status, String message, Throwable cause, int prodOrDev) {
        
         super(Response.status(status).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(status)).build()).header("message", cause).header("cause", cause.getMessage()).build());
      
    }
    
     public CMFBException(int status, String message) {
        
         super(Response.status(status).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(status)).build()).build());
      
    }
    
    public CMFBException(int status, String cause,int prodOrDev) {
        
        
        super(Response.status(status).entity(Json.createObjectBuilder().add("errorDesc", ErrorCodes.doErrorDesc(status)).build()).header("message", cause).header("cause", cause).build());
        

    }

}
