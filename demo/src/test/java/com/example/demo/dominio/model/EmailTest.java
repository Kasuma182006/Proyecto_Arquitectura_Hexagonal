package com.example.demo.dominio.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.demo.domain.model.Email;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @Test
    @DisplayName("Debería crear un Email válido cuando el formato es correcto")
    void deberiaCrearEmailValido() {
        String correoValido = "usuario@ejemplo.com";
        
        Email email = new Email(correoValido);
        
       
        assertEquals(correoValido, email.email());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el Email es nulo")
    void deberiaLanzarExcepcionCuandoEmailEsNulo() {
        
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new Email(null)
        );
        
        assertEquals("El email no puede ser nulo", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"correo-sin-arroba.com", "usuario.com", "invalido"})
    @DisplayName("Debería lanzar excepción cuando el formato del Email no contiene @")
    void deberiaLanzarExcepcionCuandoEmailEsInvalido(String emailInvalido) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Email(emailInvalido)
        );
        
        assertEquals("El formato del email es inválido", exception.getMessage());
    }
}