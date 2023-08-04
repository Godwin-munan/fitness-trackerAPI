/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.repostoryTest;

import com.munan.fitnesstrackerAPI.model.ActiveExercise;
import com.munan.fitnesstrackerAPI.model.AppUser;
import com.munan.fitnesstrackerAPI.repository.ActivatedRepository;
import com.munan.fitnesstrackerAPI.repository.AppUserRepository;
import java.time.LocalDate;
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
public class ActivatedRepositoryTest {
    
    @Autowired
    private ActivatedRepository repository;
    
    @Autowired
    private AppUserRepository userRepository;
            
   @Test
   public void activatedRespository_saveActivatedExercise_ReturnSavedExercise(){
      
       var exercise = ActiveExercise.builder()
              .name("push up")
              .state("completed")
              .calory(16.0)
              .duration(60.0)
              .date(LocalDate.now())
              .user(null)
              .build();
      
      var savedExercise = repository.save(exercise);
      
      Assertions.assertThat(savedExercise).isNotNull();
      Assertions.assertThat(savedExercise.getId()).isGreaterThan(0);
   }
   
   @Test
   public void activatedRespository_getActivatedExercises_ReturnMoreThanOneExercise(){
   
        var exercise1 = ActiveExercise.builder()
              .name("push up")
              .state("completed")
              .calory(16.0)
              .duration(60.0)
              .date(LocalDate.now())
              .user(null)
              .build();
                        
        var exercise2 = ActiveExercise.builder()
              .name("push up")
              .state("completed")
              .calory(16.0)
              .duration(60.0)
              .date(LocalDate.now())
              .user(null)
              .build();
        
        repository.save(exercise1);
        repository.save(exercise2);
        
        var exerciseList = repository.findAll();
        
        Assertions.assertThat(exerciseList).isNotNull();
        Assertions.assertThat(exerciseList.size()).isEqualTo(2);
        
   }
   
   @Test
   public void activatedRespository_getActivatedExercisesByUserId_ReturnMoreThanOneExercise(){
       
       var user = AppUser.builder()
               .username("admin@gmail.com")
               .password("1234")
               .birthday(LocalDate.now())
               .isNotLocked(true)
               .build();
       
        var userId = userRepository.save(user).getId();
   
        var exercise1 = ActiveExercise.builder()
              .name("push up")
              .state("completed")
              .calory(16.0)
              .duration(60.0)
              .date(LocalDate.now())
              .user(user)
              .build();
                        
        var exercise2 = ActiveExercise.builder()
              .name("push up")
              .state("completed")
              .calory(16.0)
              .duration(60.0)
              .date(LocalDate.now())
              .user(user)
              .build();
        
        
        repository.save(exercise1);
        repository.save(exercise2);
        
        var exerciseList = repository.findByUser_id(userId).get();
        
        Assertions.assertThat(exerciseList).isNotNull();
        Assertions.assertThat(exerciseList.size()).isEqualTo(2);
        Assertions.assertThat(
                exerciseList
                        .get(1)
                        .getUser()
                        .getId()).isEqualTo(userId);
        
        Assertions.assertThat(exerciseList.size()).isGreaterThan(1);        
        
   }
}

