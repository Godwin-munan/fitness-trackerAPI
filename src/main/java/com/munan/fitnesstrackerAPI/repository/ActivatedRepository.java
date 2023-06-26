/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.munan.fitnesstrackerAPI.repository;

import com.munan.fitnesstrackerAPI.model.ActiveExercise;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author godwi
 */
public interface ActivatedRepository extends JpaRepository<ActiveExercise, Long>{
    Optional<List<ActiveExercise>> findByUser_id(Long id);
}
