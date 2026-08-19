package com.example.demo.domain.model;

import java.time.LocalDate;
import java.util.Objects;

public record FechaNacimiento(LocalDate fechaNacimiento){
    public FechaNacimiento{

        Objects.requireNonNull(fechaNacimiento,"La fecha no puede ser nula");

        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
        }
    }
}
