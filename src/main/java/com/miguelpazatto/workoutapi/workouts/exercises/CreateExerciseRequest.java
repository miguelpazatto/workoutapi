package com.miguelpazatto.workoutapi.workouts.exercises;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record CreateExerciseRequest(
        @NotBlank String name,
        @URL String videoUrl
) {
        public CreateExerciseRequest {
                if (videoUrl != null && videoUrl.isBlank()) {
                        videoUrl = null;
                }
        }
}
