/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.controller;

import com.munan.fitnesstrackerAPI.dto.ActiveExerciseDto;
import com.munan.fitnesstrackerAPI.exceptions.AlreadyExistException;
import com.munan.fitnesstrackerAPI.exceptions.NotFoundException;
import com.munan.fitnesstrackerAPI.service.ActivatedService;
import com.munan.fitnesstrackerAPI.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(value = "/api/activated")
@Tag(name = "Executed Exercise Controller", description = "Executed Exercise Controller")
@SecurityRequirement(name = "Authorization")
public class ActivatedController {
    
    private final ActivatedService activeService;
    
    @Autowired
    public ActivatedController(ActivatedService activeService){
        this.activeService = activeService;
    }
    
    @Operation(summary = "Add Executed Exercise", description = "Add new Executed Exercise")
    @PostMapping("/{user_id}")
    public ResponseEntity<ApiResponse<?>> addActiveExercise(@PathVariable(value = "user_id") Long userId, @RequestBody ActiveExerciseDto exercise) throws AlreadyExistException, NotFoundException {
        return activeService.addAtiveExercise(userId,exercise);
    }
    
    
    @Operation(summary = "Get all Executed exercises", description = "Get all executed exercises")
    @GetMapping("/{user_id}")
    public ResponseEntity<ApiResponse<?>> getAllActiveExercises(@PathVariable(value = "user_id") Long userId) throws AlreadyExistException, NotFoundException  {
        return activeService.getActiveExerciseList(userId);
    }
    
}
