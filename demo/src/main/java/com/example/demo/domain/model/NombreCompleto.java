package com.example.demo.domain.model;

import java.util.Objects;

public record NombreCompleto(String nombre, String apellido) {
    public NombreCompleto{
        Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        Objects.requireNonNull(apellido, "El apellido no puede ser nulo");
        if (nombre.isBlank()) throw new IllegalArgumentException("El nombre no puede estar vacío");
        if (apellido.isBlank()) throw new IllegalArgumentException("El apellido no puede estar vacío");
    }
}
