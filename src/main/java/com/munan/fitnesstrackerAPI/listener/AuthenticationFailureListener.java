/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.listener;

import com.munan.fitnesstrackerAPI.service.LoginAttemptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author godwi
 */
@Component
public class AuthenticationFailureListener {
    private final LoginAttemptService loginAttemptService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFailureListener.class);

    @Autowired
    public AuthenticationFailureListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
        
    }

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        
        Object principal = event.getAuthentication().getPrincipal();
        LOGGER.info("FAILURE PRINCIPAL ${}", principal);
        if (principal instanceof String) {
            String username = (String) event.getAuthentication().getPrincipal();
            loginAttemptService.addUserToLoginAttemptCache(username);
        }
    }
}
