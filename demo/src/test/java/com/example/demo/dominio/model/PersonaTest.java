package com.example.demo.dominio.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.domain.model.Email;
import com.example.demo.domain.model.FechaNacimiento;
import com.example.demo.domain.model.NombreCompleto;
import com.example.demo.domain.model.Persona;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonaTest {

    @Test
    @DisplayName("Debería instar una Persona correctamente con todos sus Value Objects")
    void deberiaCrearPersonaCorrectamente() {
        
        Integer id = 1;
        NombreCompleto nombre = new NombreCompleto("Ana", "Gómez");
        Email email = new Email("ana.gomez@mail.com");
        FechaNacimiento fechaNacimiento = new FechaNacimiento(LocalDate.of(2000, 1, 1));

        Persona persona = new Persona(id, nombre, email, fechaNacimiento);

        
        assertEquals(id, persona.id());
        assertEquals(nombre, persona.nombreCompleto());
        assertEquals(email, persona.email());
        assertEquals(fechaNacimiento, persona.fechaNacimiento());
    }
}