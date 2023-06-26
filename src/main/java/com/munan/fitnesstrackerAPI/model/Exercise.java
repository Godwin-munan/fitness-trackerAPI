/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author godwi
 */
@Entity
@Table(name = "exercise")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "duration")
    private Double duration;
    
    @Column(name = "calory")
    private Double calory;
}
