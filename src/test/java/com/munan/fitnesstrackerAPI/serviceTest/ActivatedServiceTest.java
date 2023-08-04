/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.serviceTest;

import com.munan.fitnesstrackerAPI.dto.ActiveExerciseDto;
import com.munan.fitnesstrackerAPI.exceptions.NotFoundException;
import com.munan.fitnesstrackerAPI.model.ActiveExercise;
import com.munan.fitnesstrackerAPI.model.AppUser;
import com.munan.fitnesstrackerAPI.model.Exercise;
import com.munan.fitnesstrackerAPI.repository.ActivatedRepository;
import com.munan.fitnesstrackerAPI.repository.ExerciseRepository;
import com.munan.fitnesstrackerAPI.service.ActivatedService;
import com.munan.fitnesstrackerAPI.service.AppUserService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author godwi
 */
@ExtendWith(MockitoExtension.class)
public class ActivatedServiceTest {
    
    @Mock
    ActivatedRepository repository;

    @Mock
    ExerciseRepository exRepository;
        
    @Mock
    AppUserService userService;
    
    @InjectMocks
    ActivatedService service;
    
    private Exercise exercise;
    private ActiveExerciseDto activeExDto;
    private ActiveExercise activeEx;
    private ActiveExercise activeEx2;
    private AppUser user;
    
    @BeforeEach
    public void init(){
        
        activeExDto = ActiveExerciseDto.builder()
            .name("push up")
            .state("completed")
            .calory(16.0)
            .duration(60.0)
            .date(LocalDate.now())
            .build();
        
        exercise = Exercise.builder()
            .id(1L)
            .name("push up")
            .calory(16.0)
            .duration(60.0)
            .build();    
        
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
    public void ActivatedService_addAtiveExercise_returnExerciseInResponseEntityBody() throws NotFoundException{

        when(userService.findUserById(Mockito.anyLong()))
                .thenReturn(user);
        
        when(repository.save(Mockito.any(ActiveExercise.class)))
                .thenReturn(activeEx);
        
        when(exRepository.findByName(Mockito.any(String.class)))
                .thenReturn(Optional.of(exercise));
        
        var savedExercise =  service.addAtiveExercise(user.getId(), activeExDto);
        
        Assertions.assertThat(savedExercise.getBody()).isNotNull();
        Assertions.assertThat(savedExercise.getBody().getData()).isEqualTo(exercise.getId());
    }
    
    @Test
    public void activatedService_getActiveExerciseList_returnExercisesInResponseEntityBody() throws NotFoundException{
    
//        List<ActiveExercise> exerciseList = Mockito.mock(List.class);
//        ResponseEntity<ApiResponse> reponseEntity = Mockito.mock(ResponseEntity.class);
        var exList = List.of(activeEx, activeEx2);
        
        when(userService.findUserById(Mockito.any(Long.class)))
            .thenReturn(user);

        when(repository.findByUser_id(Mockito.any(Long.class)))
//            .thenReturn(Optional.ofNullable(exerciseList));  
              .thenReturn(Optional.ofNullable(exList));
        
        var returnExercises = service.getActiveExerciseList(user.getId());
        
        Assertions.assertThat(returnExercises.getBody()).isNotNull();
        Assertions.assertThat(returnExercises.getBody().getData())
                .isEqualTo(exList);
    
        
    }
    
    /*
        This Test uses Java Reflection, Please note itn't standard practive. 
        It's only used experiment with the Java reflection Api. 
        Be adviced to avoid or refactor
    */
    @Test
    public void activatedService_mapDtoToActiveExercise_returnExerciseWithOutId() throws Exception{
        
        var reflectMethod =  service.getClass()
                  .getDeclaredMethod("mapDtoToActiveExercise", 
                          ActiveExerciseDto.class,
                          AppUser.class);
        
        reflectMethod.setAccessible(true);
        var ex= (ActiveExercise) reflectMethod
                .invoke(service,
                        activeExDto, 
                        user);
        

        Assertions.assertThat(ex).isNotNull();
        Assertions.assertThat(ex.getId()).isNull();
    }
}
