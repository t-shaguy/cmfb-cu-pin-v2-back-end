/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.filters;

/**
 *
 * @author taysayshaguy
 */




import com.google.gson.Gson;
import com.paycraftsystems.cmfb.dto.HeaderClientObj;
import com.paycraftsystems.cmfb.dto.UserSecRequest;
import com.paycraftsystems.cmfb.repositories.SysDataRepository;
import com.paycraftsystems.cmfb.sec.AESCrypter;
import com.paycraftsystems.cmfb.sec.KeyGenerator;
import com.paycraftsystems.cmfb.services.AuthService;
import com.paycraftsystems.exceptions.CMFBException;
import com.paycraftsystems.exceptions.SecException;
import com.paycraftsystems.resources.ErrorCodes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import jakarta.json.Json;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
//import org.jboss.resteasy.core.ResourceMethodInvoker;
//import org.jboss.resteasy.core.ResourceMethodInvoker;
//import org.jboss.resteasy.core.ResourceMethodInvoker;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
@Slf4j
@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

    
   // private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JWTTokenNeededFilter.class);
    
    
    @Inject
    KeyGenerator sysKeyGenerator;

    
    @Inject
    SysDataRepository sysDataRepo;
    
    
    @Context
    private ResourceInfo  resourceInfo;
    
    
    @Inject
    AuthService authService;
    //@RestClient
    //jakarta.inject.Provider<AuthClient> authclient;
    
    String doLog = "-1";
    
    //@Inject
    //ClientInfoHelper clientInfoHelper;

    // ======================================
    // =          Business methods          =
    // ======================================

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException 
    {
        String key = sysDataRepo.doLookUpByNameStr("SYS_KEY", "XXX");
        String iv = sysDataRepo.doLookUpByNameStr("SYS_IV", "XXX");
        log.info("@@--filter -- iv "+iv+" key : "+key);
        doLog = sysDataRepo.doLookUpByNameStr("DO-LOG-FILTER", "0");
        log.info(" ### requestContext doLog = " + doLog);  
        String token = "";
        String usertoken = "";
        int prodOrDev = Integer.parseInt(sysDataRepo.doLookUpByNameStr("INTEGRATION-MODE", "0"));
       try 
       {
        log.info("-- integration mode -- "+prodOrDev);
        String encryptedfeed = "";
        
        
        String pathType = requestContext.getUriInfo().getPath();
        
        log.info("--  pathType - "+pathType);
        
        Method method = resourceInfo.getResourceMethod();// methodInvoker.getMethod();

        log.info("---XXX method "+method);
        
        if(method !=null) log.info("-- method  method.getName() "+method.getName());
        
        String userAuthorizationHeader = requestContext.getHeaderString("USER_AUTHORIZATION");
        if(doLog.trim().equals("1"))log.info("#### userAuthorizationHeader : *** " + userAuthorizationHeader);
        
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if(doLog.trim().equals("1"))log.info("-@- #### authorizationHeader startswith "+(authorizationHeader !=null?authorizationHeader.trim().startsWith("Bearer"):"NA"));
        if(doLog.trim().equals("1"))log.info("-- #### authorizationHeader : *** " + authorizationHeader);
        
        if((pathType.contains("uploads") || pathType.contains("contact-person") || pathType.contains("dns-management") || pathType.contains("user-management") || pathType.contains("supplier-management") || pathType.contains("invoice") || pathType.contains("reports-and-analytics")  || pathType.contains("business-management")  || pathType.contains("business-management") || pathType.contains("plan-management")  || pathType.contains("custom-data-management")  || pathType.contains("custom-data-management")) && (authorizationHeader ==null || !authorizationHeader.trim().startsWith("Bearer")))
        {  
            log.info("-- @@ authorizationHeader @@ !!!--"+pathType+" ++ "+authorizationHeader);
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        
        
        if(authorizationHeader ==null)
        {  
            log.info("-- @@ authorizationHeader @@ !!!--"+pathType+" ++ "+authorizationHeader);
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        
        
        if(method ==null || method.getName() == null)
        {  
            log.info("-- @@ authorizationHeader @@ !!!--"+pathType+" ++ "+authorizationHeader);
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        
        
        if(method !=null && method.getName() !=null && method.getName().endsWith("USX") && userAuthorizationHeader ==null)
        {  
            log.info("-- @@ authorizationHeader @@ !!!--"+pathType+" ++ "+authorizationHeader);
            throw new CMFBException(ErrorCodes.INVALID_USER_JWT, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USER_JWT));
                 
        }
        /*
        {  
            log.info("-- @@ authorizationHeader @@ !!!--"+pathType+" ++ "+authorizationHeader);
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        */
        if(doLog.trim().equals("1"))log.info(" !! ### authorizationHeader +1+ = " + authorizationHeader.substring("Bearer".length()+1).length());
        
         token = authorizationHeader.substring("Bearer".length()+1).trim();
         usertoken = (userAuthorizationHeader !=null)? userAuthorizationHeader.substring("Bearer".length()+1).trim():"MA";
       
        Jws<Claims> userParseClaimsJws = null;
        Jws<Claims> parseClaimsJws = null;
        String userType = "";
        UserSecRequest doParseUserClientParams = null;
        HeaderClientObj doParseClientParams = null;
        Claims userClaims = null;
        Claims claims = null;
        String requestStr = "";
        if(doLog.trim().equals("1"))log.info(" ** auth -- token = " + token);
       
            
            Key byClientKey = sysKeyGenerator.generateKey(iv, key);//clientInfoHelper.getByClientKey(apiuser);
           
            if(doLog.trim().equals("1"))log.info("key algo = " + byClientKey.getAlgorithm()+" : token.trim().length() "+token.trim().length());//+" : "+token.length());
           
            if((pathType.contains("uploads") || pathType.contains("dns-management") || pathType.contains("contact-person") || pathType.contains("user-management") || pathType.contains("supplier-management") || pathType.contains("invoice") || pathType.contains("reports-and-analytics")  || pathType.contains("business-management")  || pathType.contains("business-management") || pathType.contains("plan-management")  || pathType.contains("custom-data-management")  || pathType.contains("custom-data-management")) && userAuthorizationHeader !=null && userAuthorizationHeader.startsWith("Bearer"))
            {
              userType = "USX";
              userParseClaimsJws = Jwts.parser().setSigningKey(byClientKey).parseClaimsJws(usertoken.trim());
              userClaims = userParseClaimsJws.getBody();
              doParseUserClientParams =  JsonbBuilder.create().fromJson(new AESCrypter(sysDataRepo.doLookUpByNameStr("SYS_KEY", "KEY"),sysDataRepo.doLookUpByNameStr("SYS_IV", "IV")).decrypt(userClaims.getSubject()), UserSecRequest.class);// new RequestUtil().doParseClientParams(new AESCrypter(defaultobjectbean.getSystemParamValue("DS_KEY").getParamValue(),defaultobjectbean.getSystemParamValue("SYS_IV").getParamValue()).decrypt(claims.getSubject()));
              
            }
            
            if(method !=null && authorizationHeader.startsWith("Bearer"))
            {
               userType = "INTX";
               parseClaimsJws = Jwts.parser().setSigningKey(byClientKey).parseClaimsJws(token.trim());
               claims = parseClaimsJws.getBody();
               
               log.info(" : claims.getSubject() : "+claims.getSubject());

               String decrypt = new AESCrypter(key,iv).decrypt(claims.getSubject());
               
               log.info("=== decrypt "+decrypt);
              // LOGGER.info(" --- SUB ---- "+decrypt);
              //JsonbBuilder.create().fromJson(decrypt, HeaderClientObj.class);
               doParseClientParams = new Gson().fromJson(decrypt, HeaderClientObj.class);// new RequestUtil().doParseClientParams(new AESCrypter(defaultobjectbean.getSystemParamValue("SYS_KEY").getParamValue(),defaultobjectbean.getSystemParamValue("SYS_IV").getParamValue()).decrypt(claims.getSubject()));

               if(doLog.trim().equals("1"))
               {
                   log.error("++------doParseClientParams = " + doParseClientParams);

                   log.info("ID: " + claims.getId());
                   log.info("Subject: " + claims.getSubject());
                   //System.out.println("Subject: " + new AESCrypter(defaultobjectbean.getSystemParamValue("DS_KEY").getParamValue(),defaultobjectbean.getSystemParamValue("SYS_IV").getParamValue()).decrypt(claims.getSubject()));
                   log.info("Issuer: " + claims.getIssuer());
                   log.info("Expiration: " + claims.getExpiration());
                   log.info("doParseUserClientParams: " + doParseUserClientParams);

                   //log.info(" ** called method = " + method.getName());
               }
            }
            
            if(userType.equals("INTX"))// || pathType.equals("doListProfiledAccount") || pathType.equals("doAddAccount") ||  pathType.equals("doCreateProfile") ||  pathType.equals("doUserLogin") ||  pathType.equals("doBalanceEnquiry")  || pathType.equals("doMiniStatement") ||  pathType.equals("doStopCheque") || pathType.equals("doChequeRequest") || pathType.equals("doLocalFundsTransfer") || pathType.equals("doExtFundsTransfer") ||  method.getName().equals("doAccountsList") || method.getName().equals("doPinValidation") ||   method.getName().equals("doChangePin") ||    method.getName().equals("doPinGeneration") ||  method.getName().equals("doNameEnquiry") ||   method.getName().equals("doExtNameEnquiry") ||   method.getName().equals("doTSQ"))   //if( method.getName().equals("doVOTP") || method.getName().equals("doListProfiledAccount") || method.getName().equals("doAddAccount") ||  method.getName().equals("doCreateProfile") ||  method.getName().equals("doUserLogin") ||  method.getName().equals("doBalanceEnquiry")  || method.getName().equals("doMiniStatement") ||  method.getName().equals("doStopCheque") || method.getName().equals("doChequeRequest") || method.getName().equals("doLocalFundsTransfer") || method.getName().equals("doExtFundsTransfer") ||  method.getName().equals("doAccountsList") || method.getName().equals("doPinValidation") ||   method.getName().equals("doChangePin") ||    method.getName().equals("doPinGeneration") ||  method.getName().equals("doNameEnquiry") ||   method.getName().equals("doExtNameEnquiry") ||   method.getName().equals("doTSQ"))        
            {
              
                encryptedfeed = IOUtils.toString(requestContext.getEntityStream(), Charsets.UTF_8);
                
                //System.out.println(" @@@@@@@@ encryptedfeed @@@@@@ = " + encryptedfeed);
                
                if(doLog.trim().equals("1"))log.info("encryptedfeed = " + encryptedfeed);
                
                
               // encryptedfeed = new AESCrypter(key,iv).decrypt(encryptedfeed);
                
                if(doLog.trim().equals("1")) log.info(" ** encryptedfeed ** "+encryptedfeed+"##");// + new AESCrypter(doParseClientParams.getPx(),doParseClientParams.getRx()).decrypt(encryptedfeed));
            
            }
            //System.out.println("@@ GOT HERE method.getName().endsWith(\"USX\") = " + method.getName().endsWith("USX")+" ");
            if(userType.endsWith("USX"))
            {
                     if(userAuthorizationHeader != null && !userAuthorizationHeader.trim().equals(""))
                     {
                         
                        encryptedfeed = IOUtils.toString(requestContext.getEntityStream(), Charsets.UTF_8);
                                    
                        log.info(" @@++++### +++++++ USX+ RAW ENCRYPTED  ~  MSG ---- = " + encryptedfeed);
                                    
                       // log.info("+ --- doParseClientParams ---  "+doParseClientParams);
                        encryptedfeed = new AESCrypter(key,iv).decrypt(encryptedfeed.replaceAll("\"", ""));

                        log.info(" ++++### +++++++ USX+ PLAIN  ~  MSG ---- = " + encryptedfeed);
                     
                        log.info(" ++++### +++++++ USX+ PLAIN  ~  MSG --##-- = " + encryptedfeed);
                           
                        requestStr = encryptedfeed+"##"+new Gson().toJson(doParseUserClientParams);
                           
                        log.info("## doParseUserClientParams plain rqx=!!!>>>> " + requestStr);
                           
                        requestContext.setEntityStream(IOUtils.toInputStream(requestStr));
                     }
                     else
                     {
                           throw new CMFBException(ErrorCodes.INVALID_USER_JWT, ErrorCodes.doErrorDesc(ErrorCodes.INVALID_USER_JWT));
                     }
                 
                 
            }
            try
            {
              
               if(userType.endsWith("INTX"))
               {
                   requestContext.setEntityStream(IOUtils.toInputStream(encryptedfeed+"##"+doParseClientParams.toObj(), StandardCharsets.UTF_8));
               
               }/*
               else if(method.getName().endsWith("NP") && !method.getName().endsWith("XOXNP")) // added to grip to methods with no params
               {
                   log.info(" XXXX doParseClientParams.toObj() "+doParseClientParams.toObj());
                   requestContext.setEntityStream(IOUtils.toInputStream(encryptedfeed+"##"+doParseClientParams.toObj(), StandardCharsets.UTF_8));
               
               }
               else  if(method.getName().endsWith("USX"))
               {
                    
                    requestContext.setEntityStream(IOUtils.toInputStream(encryptedfeed+"##"+new Gson().toJson(doParseUserClientParams), StandardCharsets.UTF_8));
               
               }*/
               else
               {
                   log.info("-- got here --- ");
                    encryptedfeed = IOUtils.toString(requestContext.getEntityStream(), Charsets.UTF_8);
                    
                    log.info("-- encryptedfeed -- "+encryptedfeed);
               }
               
            }
            catch(Exception e)
            {
                log.info(" WAHALA : ",e);
            }

        }  
        catch (CMFBException e) 
        {
            System.out.println("CMFBException e " + e.getMessage());
            e.printStackTrace();
            if(doLog.trim().equals("1"))log.error("#### invalid token yy : " + token);
            requestContext.abortWith(Response.status(e.getResponse().getStatus()).entity(Json.createObjectBuilder().add("respDesc", ErrorCodes.doErrorDesc(e.getResponse().getStatus())).build()).build());
            if(doLog.trim().equals("1"))e.printStackTrace();
            if(doLog.trim().equals("1")) log.error("#### invalid token : sig ",e);
        }
        catch (io.jsonwebtoken.SignatureException e) 
        {
            //System.out.println("e = " + e.getMessage());
            e.printStackTrace();
            if(doLog.trim().equals("1"))log.error("#### invalid token yy : " + token);
            requestContext.abortWith(Response.status(ErrorCodes.JWT_SIGNATURE_EXCEPTION).entity(Json.createObjectBuilder().add("respDesc", ErrorCodes.doErrorDesc(ErrorCodes.JWT_SIGNATURE_EXCEPTION)).build()).build());
            if(doLog.trim().equals("1"))e.printStackTrace();
            if(doLog.trim().equals("1")) log.error("#### invalid token : sig ",e);
        }
        catch(io.jsonwebtoken.ExpiredJwtException x)
        {
            log.error("#### invalid token xx : " + token);
            requestContext.abortWith(Response.status(ErrorCodes.EXPIRED_JWT).entity(Json.createObjectBuilder().add("respDesc", ErrorCodes.doErrorDesc(ErrorCodes.EXPIRED_JWT)).build()).build());
            if(doLog.trim().equals("1"))x.printStackTrace();
              if(doLog.trim().equals("1")) log.error("#### invalid token : exp ",x);
        }
        catch(SecException x)
        {
            if(doLog.trim().equals("1"))log.info("####  error decrypting request : " + x.getMessage());
            requestContext.abortWith(Response.status(ErrorCodes.DECRYPTION_ERROR).entity(Json.createObjectBuilder().add("respDesc", ErrorCodes.doErrorDesc(ErrorCodes.DECRYPTION_ERROR)).build()).build());
            if(doLog.trim().equals("1"))x.printStackTrace();
            if(doLog.trim().equals("1")) log.info("#### invalid token : sex ",x);
        }
        catch (Exception e) 
        {
          
            if(doLog.trim().equals("1"))e.printStackTrace();
            if(doLog.trim().equals("1")) log.info("+#### invalid token : ## " + token);
            if(doLog.trim().equals("1")) log.error("#### invalid token : ex ",e);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(Json.createObjectBuilder().add("respDesc", ErrorCodes.doErrorDesc(401)).build()).build());
        }
    }
    
}
