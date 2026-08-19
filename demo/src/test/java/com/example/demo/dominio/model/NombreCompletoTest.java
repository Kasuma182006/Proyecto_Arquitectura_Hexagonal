package com.example.demo.dominio.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.demo.domain.model.NombreCompleto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NombreCompletoTest {

    @Test
    @DisplayName("Debería crear NombreCompleto válido")
    void deberiaCrearNombreCompletoValido() {
        NombreCompleto nombreCompleto = new NombreCompleto("Juan", "Pérez");

        assertEquals("Juan", nombreCompleto.nombre());
        assertEquals("Pérez", nombreCompleto.apellido());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    @DisplayName("Debería lanzar excepción cuando el nombre está vacío")
    void deberiaLanzarExcepcionCuandoNombreEstaVacio(String nombreVacio) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new NombreCompleto(nombreVacio, "Pérez")
        );

        assertEquals("El nombre no puede estar vacío", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    @DisplayName("Debería lanzar excepción cuando el apellido está vacío")
    void deberiaLanzarExcepcionCuandoApellidoEstaVacio(String apellidoVacio) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new NombreCompleto("Juan", apellidoVacio)
        );

        assertEquals("El apellido no puede estar vacío", exception.getMessage());
    }
}