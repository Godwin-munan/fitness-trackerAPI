/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.munan.fitnesstrackerAPI.model.AppUser;
import com.munan.fitnesstrackerAPI.security.MyUserDetailService;
import java.util.concurrent.ExecutionException;
import static java.util.concurrent.TimeUnit.MINUTES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author godwi
 */
@Service
public class LoginAttemptService {
    private static final int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
    private static final int ATTEMPT_INCREMENT = 1;
    private final LoadingCache<String, Integer> loginAttemptCache;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAttemptService.class);
    
    public LoginAttemptService() {
        super();
        loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(15, MINUTES)
                .maximumSize(100).build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }
    
    public void evictUserFromLoginAttemptCache(String username) {
        loginAttemptCache.invalidate(username);
    }
    
    public void addUserToLoginAttemptCache(String username) {
        int attempts = 0;
        LOGGER.info("LOCKED ACCOUNT: ${}", hasExceededMaxAttempts(username));
        try {
            attempts = ATTEMPT_INCREMENT + loginAttemptCache.get(username);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        loginAttemptCache.put(username, attempts);
    }
    
    public boolean hasExceededMaxAttempts(String username) {
        try {
            return loginAttemptCache.get(username) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void validateLoginAttempts(AppUser user) {
        if (user.isNotLocked()) {
            user.setNotLocked(!hasExceededMaxAttempts(user.getUsername()));
        } else {
            evictUserFromLoginAttemptCache(user.getUsername());
        }
    }
}
