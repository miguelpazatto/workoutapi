package com.miguelpazatto.workoutapi.workouts.exercises;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import com.miguelpazatto.workoutapi.TestcontainersConfiguration;

import static org.assertj.core.api.Assertions.*;

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
        Exercise exercise2 = new Exercise(ownerId, "supino reto", null);
        repository.saveAndFlush(exercise1);

        assertThatThrownBy(() -> repository.saveAndFlush(exercise2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}