/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.listener;

import com.munan.fitnesstrackerAPI.model.AppUser;
import com.munan.fitnesstrackerAPI.service.LoginAttemptService;
import java.nio.file.attribute.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author godwi
 */
@Component
public class AuthenticationSuccessListener {
    
    private final LoginAttemptService loginAttemptService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationSuccessListener.class);

    @Autowired
    public AuthenticationSuccessListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        
        LOGGER.info("SUCCESS PRINCIPAL ${}", principal);
        if (principal instanceof UserPrincipal) {
            UserPrincipal user = (UserPrincipal) event.getAuthentication().getPrincipal();
            loginAttemptService.evictUserFromLoginAttemptCache(user.getName());
        }
    }
}
