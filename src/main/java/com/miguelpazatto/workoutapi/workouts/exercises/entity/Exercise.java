package com.miguelpazatto.workoutapi.workouts.exercises.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private UUID ownerId;

    @NotBlank
    private String name;

    @URL
    private String videoUrl;

    private boolean active = true;

    protected Exercise() {}

    public Exercise(UUID ownerId, String name, String videoUrl) {
        this.ownerId = Objects.requireNonNull(ownerId, "ownerId must not be null");
        this.name = validatedName(name);
        this.videoUrl = videoUrl;
    }

    public UUID getId() {
        return id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public boolean isActive() {
        return active;
    }

    private static String validatedName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        return name.trim();
    }
}
