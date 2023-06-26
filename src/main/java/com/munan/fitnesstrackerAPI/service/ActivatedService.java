/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.service;

import static com.munan.fitnesstrackerAPI.constant.ErrorConstant.EXERCISE_NOT_FOUND;
import static com.munan.fitnesstrackerAPI.constant.UserConstant.SUCCESSFULL;
import com.munan.fitnesstrackerAPI.dto.ActiveExerciseDto;
import com.munan.fitnesstrackerAPI.exceptions.NotFoundException;
import com.munan.fitnesstrackerAPI.model.ActiveExercise;
import com.munan.fitnesstrackerAPI.model.AppUser;
import com.munan.fitnesstrackerAPI.model.Exercise;
import com.munan.fitnesstrackerAPI.repository.ActivatedRepository;
import com.munan.fitnesstrackerAPI.repository.ExerciseRepository;
import com.munan.fitnesstrackerAPI.utils.ApiResponse;
import jakarta.transaction.Transactional;
import java.util.Optional;
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
public class ActivatedService {
    
    private final ActivatedRepository activeRepository;
    private final ExerciseRepository exerciseRepository;
    
    private final AppUserService userService;
    
    @Autowired
    public ActivatedService(ActivatedRepository activeRepository, ExerciseRepository exerciseRepository, AppUserService userService){
        this.activeRepository = activeRepository;
        this.exerciseRepository = exerciseRepository;
        this.userService = userService;
    }

    public ResponseEntity<ApiResponse<?>> addAtiveExercise(Long userId,ActiveExerciseDto ex) throws NotFoundException {
        Optional<Exercise> existEx = exerciseRepository.findByName(ex.getName());
        AppUser user = userService.findUserById(userId);
        
        if(existEx.isEmpty()){
            throw new NotFoundException(EXERCISE_NOT_FOUND);
        }
        
        ActiveExercise exercise = mapDtoToActiveExercise(ex, user);
        activeRepository.save(exercise);
        
       return  ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(), 
                        "OK",
                        "Executed Exercise added "+SUCCESSFULL
                        )
        );
    }

    public ResponseEntity<ApiResponse<?>> getActiveExerciseList(Long userId) throws NotFoundException {
        
        userService.findUserById(userId);
        
        
        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(), 
                        SUCCESSFULL,
                        activeRepository.findByUser_id(userId)
                        )
        );
    }
    
    private ActiveExercise mapDtoToActiveExercise(ActiveExerciseDto ex, AppUser user){
        ActiveExercise exercise = new ActiveExercise();
        
        exercise.setName(ex.getName());
        exercise.setDuration(ex.getDuration());
        exercise.setCalory(ex.getCalory());
        exercise.setState(ex.getState());
        exercise.setUser(user);
        exercise.setDate(ex.getDate());
        
        return exercise;
    }
}
