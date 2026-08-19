package com.example.demo.dominio.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.domain.model.FechaNacimiento;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FechaNacimientoTest {

    @Test
    @DisplayName("Debería crear FechaNacimiento válida cuando es una fecha pasada o de hoy")
    void deberiaCrearFechaNacimientoValida() {
        LocalDate fechaValida = LocalDate.of(1995, 5, 20);

        FechaNacimiento fechaNacimiento = new FechaNacimiento(fechaValida);

        assertEquals(fechaValida, fechaNacimiento.fechaNacimiento());
    }

    @Test
    @DisplayName("Debería lanzar excepción si la fecha de nacimiento es futura")
    void deberiaLanzarExcepcionSiFechaEsFutura() {
        LocalDate fechaFutura = LocalDate.now().plusDays(1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new FechaNacimiento(fechaFutura)
        );

        assertEquals("La fecha de nacimiento no puede ser futura", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción si la fecha es nula")
    void deberiaLanzarExcepcionSiFechaEsNula() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new FechaNacimiento(null)
        );

        assertEquals("La fecha no puede ser nula", exception.getMessage());
    }
}