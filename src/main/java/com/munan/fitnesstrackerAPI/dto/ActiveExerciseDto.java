/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author godwi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveExerciseDto {
   
    private String name;
    
    private Double duration;
    
    private Double calory;
    
    private LocalDate date;
    
    private String state;
}
