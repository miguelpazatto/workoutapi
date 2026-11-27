package com.miguelpazatto.workoutapi.workouts.exercises;

import java.util.UUID;

public record ExerciseResponse(UUID id, String name, String videoUrl, boolean active) {

    public static ExerciseResponse from(Exercise exercise) {
        return new ExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getVideoUrl(),
                exercise.isActive());
    }

}
