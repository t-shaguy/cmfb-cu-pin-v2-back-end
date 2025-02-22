/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.sec;


import jakarta.enterprise.context.ApplicationScoped;
import java.security.Key;

/**
 * @author Antonio Goncalves  http://www.antoniogoncalves.org
 * @author taysayshaguy        --
 */
//@ApplicationScoped
public interface KeyGenerator {

    Key generateKey();
    
    Key generateKey(String key);
    
    Key generateKey(String userid, String password);
    
    Key generateKey(String userid, String password, String random);
}
