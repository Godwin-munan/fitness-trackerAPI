/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.repostoryTest;

import com.munan.fitnesstrackerAPI.model.Exercise;
import com.munan.fitnesstrackerAPI.repository.ExerciseRepository;
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
public class ExerciseRepositoryTest {
    
    @Autowired
    ExerciseRepository repository;
    

    @Test
    public void exerciseRepository_findByName_returnExercise(){
        
        var exercise = Exercise.builder()
                .name("Push Up")
                .calory(18.0)
                .duration(60.0)
                .build();
        
        repository.save(exercise);
        
        var findExercise = repository.findByName(exercise.getName()).get();
        
        Assertions.assertThat(findExercise).isNotNull();
        Assertions.assertThat(findExercise.getName()).isEqualTo(exercise.getName());
    }
}
