/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.services;

import com.paycraftsystems.cmfb.dto.CBAAccessToken;
import com.paycraftsystems.exceptions.InvalidRequestException;
import com.paycraftsystems.exceptions.ProcessingException;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Form;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author paycraftsystems-i
 */

@ApplicationScoped
@Slf4j
public class SessionService {
    
    
    @ConfigProperty(name = "cba.token.scope")
    String tokenScope;
    
    @ConfigProperty(name = "cba.service.url")
    String cbaServiceUrl;
    
    @ConfigProperty(name = "cba.token.grant.type")
    String tokenGrantType;
    
    @ConfigProperty(name = "cba.service.password")
    String cbaAPIPassword;
    
    @ConfigProperty(name = "cba.service.user")
    String cbaAPIUser;
    
    private static final ConcurrentHashMap<CBAAccessToken.TokenType, CBAAccessToken> tokenCache = new ConcurrentHashMap<>();
    private final static Lock lock = new ReentrantLock();

    public @NotNull CBAAccessToken getToken(CBAAccessToken.TokenType tokenType) {
        return obtainAndSetToken(tokenType);
    }

    private @NotNull CBAAccessToken obtainAndSetToken(CBAAccessToken.TokenType tokenType) {
        
        var token = tokenCache.get(tokenType);
        if (token != null && token.isValid())
            return token;

        lock.lock();
        try {
            if (token == null || !token.isValid()) {

                switch(tokenType) {
                    case TOKEN -> {
                        try (var client = ClientBuilder.newClient()) {
                            var target = client.target(String.format("%s/TOKEN", cbaServiceUrl));
                            var form = new Form();

                            form.param("username", cbaAPIUser);
                            form.param("password", cbaAPIPassword);
                            form.param("grant_type", tokenGrantType);
                            //form.param("scope", easyPayScope);

                            var response = target.request().post(jakarta.ws.rs.client.Entity.form(form));
                            switch(response.getStatus()) {
                                case 200:
                                    break;
                                case 400, 401:
                                    log.warn("Invalid client credentials {}", response.readEntity(String.class));
                                    throw new InvalidRequestException("Invalid client credentials");
                                default:
                                    log.warn("Token processing exception {}", response.readEntity(String.class));
                                    throw new ProcessingException("Error occurred while refreshing token");
                            }
                            token = response.readEntity(CBAAccessToken.class);

                            //Set the grant time
                            token.grantTime = java.time.LocalDateTime.now();
                            tokenCache.put(tokenType, token);
                            log.info("Easy pay token refreshed successfully @ {{}}", token.grantTime);
                        }
                    }

                    case OTHER -> {
                        
                         try (var client = ClientBuilder.newClient()) {
                            var target = client.target(String.format("%s/TOKEN", cbaServiceUrl));  //  tokenApiHost                          var target = client.target(String.format("%s/reset", tokenApiHost));  //
                    
                            var form = new Form();

                            //System.err.println(" BOOLEAN_MATCH @@---- form = tokenClientId " + tokenClientId+"  tokenSecret: "+tokenSecret+"  tokenGrant "+tokenGrant+" tokenScope "+tokenScope);
                            
                            form.param("username", cbaAPIUser);
                            form.param("password", cbaAPIPassword);
                            form.param("grant_type", tokenGrantType);
                            
                            var response = target.request().post(jakarta.ws.rs.client.Entity.form(form)); //

                            switch(response.getStatus()) {
                                case 200:
                                    break;
                                case 400, 401:
                                    log.warn("Invalid client credentials {}", response.readEntity(String.class));
                                    throw new InvalidRequestException("Invalid client credentials");
                                default:
                                    log.warn("Token processing exception {}", response.readEntity(String.class));
                                    throw new ProcessingException("Error occurred while refreshing token");
                            }
                            token = response.readEntity(CBAAccessToken.class);
                            
                            token.grantTime = java.time.LocalDateTime.now();
                            tokenCache.put(tokenType, token);
                            log.info("Boolean token refreshed successfully @ {{}}", token.grantTime);
                        }
                        
                        
                      
                    }
                }
            }
        } finally {
            lock.unlock();
        }

        return token;
    }
    
}
