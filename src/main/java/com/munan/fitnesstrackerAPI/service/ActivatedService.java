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
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author godwi
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ActivatedService {
    
    private final ActivatedRepository activeRepository;
    private final ExerciseRepository exerciseRepository;
    private final AppUserService userService;
    

    //Create Finished Exercises by User Id
    public ResponseEntity<ApiResponse<Long>> addAtiveExercise(Long userId,ActiveExerciseDto ex) throws NotFoundException {
        Optional<Exercise> existEx = exerciseRepository.findByName(ex.getName());
        AppUser user = userService.findUserById(userId);
        
        if(existEx.isEmpty()){
            throw new NotFoundException(EXERCISE_NOT_FOUND);
        }
        
        ActiveExercise exercise = mapDtoToActiveExercise(ex, user);
        ActiveExercise savedExercise = activeRepository.save(exercise);
        
       return  new ResponseEntity<>(new ApiResponse<>(
                        HttpStatus.OK.value(), 
                        SUCCESSFULL,
                        savedExercise.getUser().getId()),
                        HttpStatus.CREATED);
    }

    //Get Finished Exercise by User Id
    public ResponseEntity<ApiResponse<List<ActiveExercise>>> getActiveExerciseList(Long userId) throws NotFoundException {
        
        userService.findUserById(userId);
        
        return ResponseEntity.ok(new ApiResponse<>(
                        HttpStatus.OK.value(), 
                        SUCCESSFULL,
                        activeRepository.findByUser_id(userId).get()));
    }
    
    //Private method to map active ExerciseDto to active Exercise
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
