/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author godwi
 */
@Entity
@Table(name = "active_exercise")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActiveExercise {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "duration")
    private Double duration;
    
    @Column(name = "calory")
    private Double calory;
    
    
    @Column(name = "date", columnDefinition = "DATE")
    private LocalDate date;
    
    @Column(name = "state")
    private String state;
    
    
    @ManyToOne
    @JsonIgnore
    private AppUser user;
}
