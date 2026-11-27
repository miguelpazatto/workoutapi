package com.miguelpazatto.workoutapi.workouts.exercises.repository;

import com.miguelpazatto.workoutapi.workouts.exercises.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {

    boolean existsByOwnerIdAndNameIgnoreCase(UUID ownerId, String name);
    
}
