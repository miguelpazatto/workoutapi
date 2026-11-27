package com.miguelpazatto.workoutapi.workouts.exercises.service;

import com.miguelpazatto.workoutapi.workouts.exercises.dto.CreateExerciseRequest;
import com.miguelpazatto.workoutapi.workouts.exercises.dto.ExerciseResponse;
import com.miguelpazatto.workoutapi.workouts.exercises.entity.Exercise;
import com.miguelpazatto.workoutapi.workouts.exercises.exception.DuplicateExerciseNameException;
import com.miguelpazatto.workoutapi.workouts.exercises.repository.ExerciseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExerciseService {

    private final ExerciseRepository repository;

    public ExerciseService(ExerciseRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ExerciseResponse insert(CreateExerciseRequest request, UUID ownerId) {

        Exercise exercise = new Exercise(ownerId, request.name(), request.videoUrl());

        if (repository.existsByOwnerIdAndNameIgnoreCase(ownerId, exercise.getName())) {
            throw new DuplicateExerciseNameException("another exercise already has the same name");
        }

        Exercise saved = repository.save(exercise);
        return ExerciseResponse.from(saved);
    }
}
