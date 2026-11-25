package com.miguelpazatto.workoutapi.workouts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExerciseTest {

    private static final String NAME = "Supino reto";
    private static final String VIDEO_URL = "https://example.com/supino";

    private Exercise validExercise() {
        return new Exercise(UUID.randomUUID(), NAME, VIDEO_URL);
    }

    @Test
    void shouldCreateExerciseWithGivenValues() {
        UUID ownerId = UUID.randomUUID();

        Exercise exercise = new Exercise(ownerId, NAME, VIDEO_URL);

        assertThat(exercise.getOwnerId()).isEqualTo(ownerId);
        assertThat(exercise.getName()).isEqualTo(NAME);
        assertThat(exercise.getVideoUrl()).isEqualTo(VIDEO_URL);
    }

    @Test
    void shouldCreateExerciseAsActiveByDefault() {
        Exercise exercise = validExercise();

        assertThat(exercise.isActive()).isTrue();
    }

    @Test
    void shouldCreateExerciseWhenVideoUrlIsNull() {
        Exercise exercise = new Exercise(UUID.randomUUID(), NAME, null);

        assertThat(exercise.getVideoUrl()).isNull();
    }

    @Test
    void shouldRejectNullOwnerId() {
        assertThatThrownBy(() -> new Exercise(null, NAME, VIDEO_URL))
                .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    void shouldRejectInvalidName(String invalidName) {
        assertThatThrownBy(() -> new Exercise(UUID.randomUUID(), invalidName, VIDEO_URL))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name must not be blank");
    }

    @ParameterizedTest
    @ValueSource(strings = {" Supino reto", "Supino reto ", "  Supino reto  ", "\tSupino reto\n"})
    void shouldTrimSurroundingWhitespacesFromName(String paddedName) {
        Exercise exercise = new Exercise(UUID.randomUUID(), paddedName, VIDEO_URL);

        assertThat(exercise.getName()).isEqualTo(NAME);
    }
}
