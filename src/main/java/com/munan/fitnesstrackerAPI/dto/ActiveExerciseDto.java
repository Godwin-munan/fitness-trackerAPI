/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.dto;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author godwi
 */
@Data
@NoArgsConstructor
public class ActiveExerciseDto {
   
    private String name;
    
    private Double duration;
    
    private Double calory;
    
    private Date date;
    
    private String state;
}
