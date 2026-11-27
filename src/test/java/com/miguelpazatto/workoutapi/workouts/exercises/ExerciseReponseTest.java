package com.miguelpazatto.workoutapi.workouts.exercises;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ExerciseReponseTest {

    @Test
    void shouldMapAllFieldsFromExercise() {
        Exercise exercise = new Exercise(UUID.randomUUID(), "Supino reto", "https://example.com/supino");

        ExerciseResponse response = ExerciseResponse.from(exercise);

        assertThat(response.name()).isEqualTo("Supino reto");
        assertThat(response.videoUrl()).isEqualTo("https://example.com/supino");
        assertThat(response.active()).isTrue();
        assertThat(response.id()).isNull();
    }
}
