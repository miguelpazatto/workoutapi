package com.miguelpazatto.workoutapi;

import org.springframework.boot.SpringApplication;

public class TestWorkoutapiApplication {

	public static void main(String[] args) {
		SpringApplication.from(WorkoutapiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
