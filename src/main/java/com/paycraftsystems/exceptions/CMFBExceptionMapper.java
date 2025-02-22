/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.exceptions;


//import com.paycraftsystems.hellovas.dto.EasyPayErrorDTO;
import java.io.ByteArrayInputStream;
import jakarta.annotation.Priority;
//import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author paycraftsystems-i
 */
@Priority(40)
public class CMFBExceptionMapper implements ResponseExceptionMapper<RuntimeException> {
   private final static Logger LOGGER = LoggerFactory.getLogger(CMFBExceptionMapper.class);
    /*
    @Override
    public RuntimeException toThrowable(Response r) {
        return new NitroswitchException(r.getStatus() + " #-# " + r.readEntity(String.class));
    }*/
    
  @Override
  public RuntimeException toThrowable(Response response) {
    int status = response.getStatus();                     // (3)
    System.out.println("----status = " + status);
    String msg = getBody(response); // see below
    
    LOGGER.info("msg = " + msg);
    
    ErrorInfoDTO errorInfo = new ErrorInfoDTO();// JsonbBuilder.create().fromJson(msg, ErrorInfoDTO.class);
    
    //LOGGER.info("----ESAYPAY errorInfo = " + errorInfo);
    //LOGGER.info("@@----FWerrorInfo = " + errorInfo.description);

    RuntimeException re ;
    switch (status) {
        
      case 400: re = new CMFBException(msg);         // (4)
      break;
      case 401: re = new CMFBException(msg);         // (4)
      break;
      case 409: re = new CMFBException(errorInfo.toErrorInfo);         // (4)
      break;
      case 500: re = new CMFBException(msg);         // (4)
      break;
      default:
        re = new WebApplicationException(errorInfo.toErrorInfo, status);          // (5)
    }
    return re;
  }
    
  private String getBody(Response response) {
  ByteArrayInputStream is = (ByteArrayInputStream) response.getEntity();
  byte[] bytes = new byte[is.available()];
  is.read(bytes,0,is.available());
  String body = new String(bytes);
  LOGGER.info("##body = " + body);
  return body;
  }
}