/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.utils;

import static com.munan.fitnesstrackerAPI.constant.UserConstant.ROLE_ALREADY_EXIST;
import com.munan.fitnesstrackerAPI.enums.Roles;
import com.munan.fitnesstrackerAPI.exceptions.AlreadyExistException;
import com.munan.fitnesstrackerAPI.model.Role;
import com.munan.fitnesstrackerAPI.repository.RoleRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author godwi
 */
@Component
@Transactional
public class CommandLine implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLine.class);

    @Autowired
    public CommandLine(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args){

        try {
                String name = Roles.ROLE_SUPER_ADMIN.name();
                String authorities = "user:read,user:create,user:update,user:delete";
                
                Optional<Role> role = roleRepository.findByName(name);
                if(role.isPresent()){
                    throw new AlreadyExistException(name+ROLE_ALREADY_EXIST);
                }else {
                    Role newRole = new Role();
                    newRole.setName(name);
                    newRole.setAuthorities(authorities);
                    roleRepository.save(newRole);
                }
  
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
    }

}
