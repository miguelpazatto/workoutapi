package com.miguelpazatto.workoutapi.workouts.exercises;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateExerciseRequestTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "    ", "\t"})
    void shouldConvertBlankVideoUrlToNull(String blankUrl) {
        CreateExerciseRequest request = new CreateExerciseRequest("Supino reto", blankUrl);

        assertThat(request.videoUrl()).isNull();
    }
}
