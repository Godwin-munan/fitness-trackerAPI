/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.repostoryTest;

import com.munan.fitnesstrackerAPI.enums.Roles;
import com.munan.fitnesstrackerAPI.model.Role;
import com.munan.fitnesstrackerAPI.repository.RoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 *
 * @author godwi
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RoleRepositoryTest {
    
    @Autowired
    private RoleRepository repository;
    
    @Test
    public void roleRepository_findByName_returnRole() {
        
        var role = Role.builder()
                .name(Roles.ROLE_USER.name())
                .authorities("user:read")
                .build();
       
        var savedRole = repository.save(role);

        
        var findRole = repository.findByName(role.getName()).get();
        
        Assertions.assertThat(findRole).isNotNull();
        Assertions.assertThat(findRole.getId()).isEqualTo(savedRole.getId());
        
    }
    
    
}
