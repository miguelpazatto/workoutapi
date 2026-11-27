package com.miguelpazatto.workoutapi.workouts.exercises.repository;

import com.miguelpazatto.workoutapi.workouts.exercises.entity.Exercise;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import com.miguelpazatto.workoutapi.TestcontainersConfiguration;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

@Import(TestcontainersConfiguration.class)
@DataJpaTest
public class ExerciseRepositoryTest {

    @Autowired
    private ExerciseRepository repository;

    @Test
    void shouldRejectSameNameIgnoringCase() {
        UUID ownerId = UUID.randomUUID();

        Exercise exercise1 = new Exercise(ownerId, "Supino reto", null);
        repository.saveAndFlush(exercise1);

        Exercise exercise2 = new Exercise(ownerId, "supino reto", null);
        assertThatThrownBy(() -> repository.saveAndFlush(exercise2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void shouldInsertSameNameWhenOwnerIsDifferent() {
        String name = "Supino reto";

        Exercise exercise1 = new Exercise(UUID.randomUUID(), name, null);
        Exercise exercise2 = new Exercise(UUID.randomUUID(), name, null);
        List<Exercise> saved = repository.saveAllAndFlush(List.of(exercise1, exercise2));

        assertThat(saved).hasSize(2);
        assertThat(saved).allSatisfy(exercise -> assertThat(exercise.getId()).isNotNull());
        assertThat(repository.count()).isEqualTo(2);
    }

    @Test
    void shouldReturnTrueWhenOwnerIdAndNameExists() {
        UUID ownerId = UUID.randomUUID();

        Exercise exercise = new Exercise(ownerId, "SUPINO RETO", null);
        repository.saveAndFlush(exercise);

        assertThat(repository.existsByOwnerIdAndNameIgnoreCase(ownerId, "Supino reto")).isTrue();
    }

    @Test
    void shouldReturnFalseWhenOwnerIsDifferent() {
        Exercise exercise = new Exercise(UUID.randomUUID(), "SUPINO RETO", null);
        repository.saveAndFlush(exercise);

        UUID ownerId = UUID.randomUUID();
        assertThat(repository.existsByOwnerIdAndNameIgnoreCase(ownerId, "Supino reto")).isFalse();
    }

    @Test
    void shouldReturnFalseWhenNameIsDifferent() {
        UUID ownerId = UUID.randomUUID();

        Exercise exercise = new Exercise(ownerId, "Supino reto", null);
        repository.saveAndFlush(exercise);

        assertThat(repository.existsByOwnerIdAndNameIgnoreCase(ownerId, "Supino inclinado")).isFalse();
    }

}