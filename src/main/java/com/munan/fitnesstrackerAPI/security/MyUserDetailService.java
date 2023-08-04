/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.security;

import static com.munan.fitnesstrackerAPI.constant.UserConstant.FOUND_USER_BY_USERNAME;
import static com.munan.fitnesstrackerAPI.constant.UserConstant.USER_NOT_FOUND_BY_USERNAME;
import com.munan.fitnesstrackerAPI.model.AppUser;
import com.munan.fitnesstrackerAPI.repository.AppUserRepository;
import com.munan.fitnesstrackerAPI.service.LoginAttemptService;
import java.time.LocalDate;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author godwi
 */
@Service
public class MyUserDetailService implements UserDetailsService{
    private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailService.class);
    private final AppUserRepository userRepository;
    private final LoginAttemptService loginAttemptService;
    
    @Autowired
    public MyUserDetailService(AppUserRepository userRepository, LoginAttemptService loginAttemptService){
        this.userRepository = userRepository;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = userRepository.findByUsername(username);
        
        if(user.isEmpty()){
           LOGGER.error(USER_NOT_FOUND_BY_USERNAME + username);
           throw new UsernameNotFoundException(USER_NOT_FOUND_BY_USERNAME + username);
        }else {

            loginAttemptService.validateLoginAttempts(user.get());
            user.get().setLastLoginDateDisplay(user.get().getLastLoginDate());
            user.get().setLastLoginDate(LocalDate.now());
            userRepository.save(user.get());

            MyUserDetail userPrincipal;
            userPrincipal = new MyUserDetail(user.get());
            LOGGER.info(FOUND_USER_BY_USERNAME + username);
            return userPrincipal;
        }
        
        
    }
    
}
