/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import static com.munan.fitnesstrackerAPI.constant.UserConstant.SUCCESSFULL;
import com.munan.fitnesstrackerAPI.controller.ActivatedController;
import com.munan.fitnesstrackerAPI.dto.ActiveExerciseDto;
import com.munan.fitnesstrackerAPI.model.ActiveExercise;
import com.munan.fitnesstrackerAPI.model.AppUser;
import com.munan.fitnesstrackerAPI.service.ActivatedService;
import com.munan.fitnesstrackerAPI.utils.ApiResponse;
import java.time.LocalDate;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author godwi
 */
@WebMvcTest(controllers = ActivatedController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ActivatedControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    
    @Autowired
    ObjectMapper objectMapper;
    
    @MockBean
    private ActivatedService service;
    
    private ActiveExercise activeEx;
    private ActiveExercise activeEx2;
    private AppUser user;
    
    @BeforeEach
    public void init(){ 
        
        user = AppUser.builder()
            .id(1L)
            .username("admin@gmail.com")
            .password("1234")
            .birthday(LocalDate.now())
            .userId("FT"+RandomStringUtils.randomNumeric(6))
            .isNotLocked(true)
            .build();    
       
        activeEx = ActiveExercise.builder()
            .id(1L)
            .name("push up")
            .state("completed")
            .calory(16.0)
            .duration(60.0)
            .date(LocalDate.now())
            .user(user)
            .build();   
        
        activeEx2 = ActiveExercise.builder()
            .id(2L)
            .name("push up")
            .state("completed")
            .calory(16.0)
            .duration(60.0)
            .date(LocalDate.now())
            .user(user)
            .build(); 
    }
    
    @Test
    public void ActivatedController_getAllActiveExercises_returnExercises() throws Exception{
        List<ActiveExercise> exList = List.of(activeEx2, activeEx);
        
        ApiResponse<List<ActiveExercise>> apiResponse =  new ApiResponse<>(HttpStatus.OK.value(),
                SUCCESSFULL,exList);
    
        ResponseEntity<ApiResponse<List<ActiveExercise>>> reponseEntity = ResponseEntity.ok(apiResponse);
        
        when(service.getActiveExerciseList(Mockito.anyLong()))
                .thenReturn(reponseEntity);   
        
        mockMvc.perform(get("/api/activated/"+user.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(apiResponse)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data.size()", CoreMatchers.is(exList.size())));
                
    }
    
    
    @Test
    public void activatedController_addActiveExercise_returnUserId() throws Exception{
        
        var userId = user.getId();
    
        ApiResponse<Long> apiResponse =  new ApiResponse<>(HttpStatus.CREATED.value(),
                SUCCESSFULL,userId);  
        
        ResponseEntity<ApiResponse<Long>> reponseEntity = new ResponseEntity<>(apiResponse,
                HttpStatus.CREATED);
                
        when(service.addAtiveExercise(Mockito.anyLong(), 
                        Mockito.any(ActiveExerciseDto.class)))
                .thenReturn(reponseEntity);           
    
        ResultActions response = mockMvc
                .perform(post("/api/activated/"+userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(apiResponse)));
        
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.response", CoreMatchers.is(SUCCESSFULL)));
 
    }
    
    
}
