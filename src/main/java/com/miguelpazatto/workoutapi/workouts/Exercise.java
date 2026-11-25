package com.miguelpazatto.workoutapi.treinos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

@Entity
@Table(name = "tb_exercicio")
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GeneratedValue.UUID)
    private UUID id;

    @NotNull
    private UUID ownerId;

    private boolean active;

    @NotBlank
    private String name;

    @URL
    private String videoUrl;


}
