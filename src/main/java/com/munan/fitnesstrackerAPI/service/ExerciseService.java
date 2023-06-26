/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.service;

import static com.munan.fitnesstrackerAPI.constant.ErrorConstant.EXERCISE_NOT_FOUND;
import static com.munan.fitnesstrackerAPI.constant.UserConstant.EXERCISE_ALREADY_EXIST;
import static com.munan.fitnesstrackerAPI.constant.UserConstant.SUCCESSFULL;
import com.munan.fitnesstrackerAPI.exceptions.AlreadyExistException;
import com.munan.fitnesstrackerAPI.exceptions.NotFoundException;
import com.munan.fitnesstrackerAPI.model.Exercise;
import com.munan.fitnesstrackerAPI.repository.ExerciseRepository;
import com.munan.fitnesstrackerAPI.utils.ApiResponse;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author godwi
 */
@Service
@Transactional
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    
    private Logger LOGGER = LoggerFactory.getLogger(ExerciseService.class);
    
    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository){
        this.exerciseRepository = exerciseRepository;
    }

    public ResponseEntity<ApiResponse<?>> addExercise(Exercise exercise) throws AlreadyExistException {
       Optional<Exercise> existEx = exerciseRepository.findByName(exercise.getName());
       
       if(existEx.isPresent()){
           LOGGER.error(exercise.getName() + EXERCISE_ALREADY_EXIST);
           throw new AlreadyExistException(EXERCISE_ALREADY_EXIST);
       }
       exerciseRepository.save(exercise);
       return  ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(), 
                        "OK",
                        "Exercise added "+SUCCESSFULL
                        )
        );
    }

    public ResponseEntity<ApiResponse<?>> getExerciseList() {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(), 
                        SUCCESSFULL,
                        exerciseRepository.findAll()
                        )
        );
    }

    public ResponseEntity<ApiResponse<?>> getExerciseById(Long id) throws AlreadyExistException, NotFoundException{
        Optional<Exercise> ex = exerciseRepository.findById(id);
        
        if(ex.isEmpty()){
            throw new NotFoundException(ex.get().getName()+" "+EXERCISE_NOT_FOUND);
        }
        
        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(), 
                        SUCCESSFULL,
                        ex.get()
                        )
        );
    }
    
    
    
}
