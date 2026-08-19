package com.example.demo.domain.model;

import java.util.Objects;

public record Email(String email){
    public Email{
         Objects.requireNonNull(email, "El email no puede ser nulo");
         if (!email.contains("@")) {
            throw new IllegalArgumentException("El formato del email es inválido");
        }
    }
}
