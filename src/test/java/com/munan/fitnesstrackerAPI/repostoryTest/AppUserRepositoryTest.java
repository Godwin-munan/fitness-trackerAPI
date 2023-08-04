/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.repostoryTest;

import com.munan.fitnesstrackerAPI.model.AppUser;
import com.munan.fitnesstrackerAPI.repository.AppUserRepository;
import java.time.LocalDate;
import org.apache.commons.lang3.RandomStringUtils;
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
public class AppUserRepositoryTest {
    
    @Autowired
    AppUserRepository repository;
    
    @Test
    public void appUserRepository_findByUsername_returnUser(){
    
       var user = AppUser.builder()
               .username("admin@gmail.com")
               .password("1234")
               .birthday(LocalDate.now())
               .userId("FT"+RandomStringUtils.randomNumeric(6))
               .isNotLocked(true)
               .build();
       
       repository.save(user);
       
       var findUser = repository.findByUsername(user.getUsername()).get();
       
       Assertions.assertThat(findUser.getUsername()).isEqualTo(user.getUsername());

    }
}
