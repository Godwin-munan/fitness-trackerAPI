/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.munan.fitnesstrackerAPI.repository;

import com.munan.fitnesstrackerAPI.model.Exercise;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author godwi
 */
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findByName(String name);
}
