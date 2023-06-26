/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.service;

import com.munan.fitnesstrackerAPI.exceptions.NotFoundException;
import com.munan.fitnesstrackerAPI.model.Role;
import com.munan.fitnesstrackerAPI.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author godwi
 */
@Service
@Transactional
public class RoleService {
    
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    public Role findRoleByName(String name) throws NotFoundException {
        return roleRepository.findByName(name).orElseThrow(()-> new NotFoundException("Role "+name+" not Found"));
    }
    
}
