package com.miguelpazatto.workoutapi.workouts.exercises.service;

import com.miguelpazatto.workoutapi.workouts.exercises.dto.CreateExerciseRequest;
import com.miguelpazatto.workoutapi.workouts.exercises.dto.ExerciseResponse;
import com.miguelpazatto.workoutapi.workouts.exercises.entity.Exercise;
import com.miguelpazatto.workoutapi.workouts.exercises.exception.DuplicateExerciseNameException;
import com.miguelpazatto.workoutapi.workouts.exercises.repository.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {

    private ExerciseService service;

    @Mock
    private ExerciseRepository repository;

    @BeforeEach
    void setUp() {
        service = new ExerciseService(repository);
    }

    @Test
    void shouldCreateExerciseAndReturnResponse() {
        CreateExerciseRequest request = new CreateExerciseRequest("Supino reto", "https://example.com/supino");
        UUID ownerId = UUID.randomUUID();

        when(repository.existsByOwnerIdAndNameIgnoreCase(ownerId, "Supino reto")).thenReturn(false);
        when(repository.save(any(Exercise.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ExerciseResponse response = service.insert(request, ownerId);

        ArgumentCaptor<Exercise> captor = ArgumentCaptor.forClass(Exercise.class);
        verify(repository).save(captor.capture());
        Exercise saved = captor.getValue();

        assertThat(response.name()).isEqualTo("Supino reto");
        assertThat(response.videoUrl()).isEqualTo("https://example.com/supino");
        assertThat(response.active()).isTrue();
        assertThat(saved.getOwnerId()).isEqualTo(ownerId);
    }

    @Test
    void shouldThrowWhenNameAlreadyExistsForOwner() {
        CreateExerciseRequest request = new CreateExerciseRequest("Supino reto", "https://example.com/supino");
        UUID ownerId = UUID.randomUUID();

        when(repository.existsByOwnerIdAndNameIgnoreCase(ownerId, "Supino reto")).thenReturn(true);

        assertThatThrownBy(() -> service.insert(request, ownerId))
            .isInstanceOf(DuplicateExerciseNameException.class);

        verify(repository, never()).save(any());
    }

    @Test
    void shouldCheckDuplicateUsingTrimmedName() {
        CreateExerciseRequest request = new CreateExerciseRequest("   Supino reto   ", "https://example.com/supino");
        UUID ownerId = UUID.randomUUID();

        when(repository.save(any(Exercise.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.insert(request, ownerId);

        verify(repository, times(1)).existsByOwnerIdAndNameIgnoreCase(ownerId, "Supino reto");
    }
}
