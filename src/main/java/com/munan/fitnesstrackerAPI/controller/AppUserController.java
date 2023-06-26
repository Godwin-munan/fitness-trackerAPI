/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.controller;

import com.munan.fitnesstrackerAPI.dto.LoginRequest;
import com.munan.fitnesstrackerAPI.exceptions.AlreadyExistException;
import com.munan.fitnesstrackerAPI.exceptions.NotFoundException;
import com.munan.fitnesstrackerAPI.service.AppUserService;
import com.munan.fitnesstrackerAPI.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author godwi
 */
@RestController
@RequestMapping(value = "/api/user")
@Tag(name = "User Controller", description = "User Controller")
@SecurityRequirement(name = "Authorization")
public class AppUserController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final AppUserService userService;
    
    @Autowired
    public AppUserController(AppUserService userService){
        this.userService = userService;
    }
    
    @Operation(summary = "Authenticate User", description = "Authenticate User")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginRequest user) throws NotFoundException {
        return userService.login(user);
    }
    
    @Operation(summary = "User Registration", description = "Create a new account")
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody LoginRequest user) throws NotFoundException, AlreadyExistException {
        return userService.register(user);
    }
    
    @Operation(summary = "webpage", description = "webpage")
    @GetMapping("/get")
    public String getWebpage() throws NotFoundException {
        return "<h1>Hello World</h1>";
    }
    
}
