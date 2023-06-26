/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.controller;

import com.munan.fitnesstrackerAPI.exceptions.AlreadyExistException;
import com.munan.fitnesstrackerAPI.exceptions.NotFoundException;
import com.munan.fitnesstrackerAPI.model.Exercise;
import com.munan.fitnesstrackerAPI.service.ExerciseService;
import com.munan.fitnesstrackerAPI.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author godwi
 */
@RestController
@RequestMapping(value = "/api/exercise")
@Tag(name = "Exercise Controller", description = "Exercise Controller")
@SecurityRequirement(name = "Authorization")
public class ExerciseController {
    private final ExerciseService exerciseService;
    
    @Autowired
    public ExerciseController(ExerciseService exerciseService){
        this.exerciseService = exerciseService;
    }
    
    @Operation(summary = "Add Exercise", description = "Add new exercise")
    @PostMapping("")
    public ResponseEntity<ApiResponse<?>> addExercise(@RequestBody Exercise exercise) throws AlreadyExistException, NotFoundException {
        return exerciseService.addExercise(exercise);
    }
    
    
    @Operation(summary = "Get all exercises", description = "Get all exercises")
    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> getAllExercises() {
        return exerciseService.getExerciseList();
    }
    
    @Operation(summary = "Get exercise by Id", description = "Get exercise by Id")
    @GetMapping("/{ex_id}")
    public ResponseEntity<ApiResponse<?>> getExerciseById(@PathVariable(value = "ex_id") Long id) throws AlreadyExistException, NotFoundException{
        return exerciseService.getExerciseById(id);
    }
}
