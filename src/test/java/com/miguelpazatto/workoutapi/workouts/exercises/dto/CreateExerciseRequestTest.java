package com.miguelpazatto.workoutapi.workouts.exercises.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateExerciseRequestTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "    ", "\t"})
    void shouldConvertBlankVideoUrlToNull(String blankUrl) {
        CreateExerciseRequest request = new CreateExerciseRequest("Supino reto", blankUrl);

        assertThat(request.videoUrl()).isNull();
    }

    @Test
    void shouldKeepValidVideoUrl() {
        CreateExerciseRequest request = new CreateExerciseRequest("Supino reto", "https://example.com/supino");

        assertThat(request.videoUrl()).isEqualTo("https://example.com/supino");
    }
}
