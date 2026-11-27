package com.miguelpazatto.workoutapi.workouts.exercises.exception;

public class DuplicateExerciseNameException extends RuntimeException {

    public DuplicateExerciseNameException(String msg) {
        super(msg);
    }
}
