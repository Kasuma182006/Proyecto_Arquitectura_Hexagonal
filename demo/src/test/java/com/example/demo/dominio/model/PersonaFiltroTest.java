package com.example.demo.dominio.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.domain.model.filter.PersonaFiltro;

import static org.junit.jupiter.api.Assertions.*;

class PersonaFiltroTest {

    @Test
    @DisplayName("Debe crear la instancia de PersonaFiltro y recuperar sus valores correctamente")
    void shouldCreatePersonaFiltroAndVerifyAttributes() {
        
        String nombre = "Carlos";
        String apellido = "Perez";
        Integer anioNacimiento = 1990;
        int pagina = 0;
        int tamano = 20;
        String orden = "nombre";
        String direccion = "ASC";

        
        PersonaFiltro filtro = new PersonaFiltro(
                nombre, apellido, anioNacimiento, pagina, tamano, orden, direccion
        );

        
        assertNotNull(filtro);
        assertEquals(nombre, filtro.nombre());
        assertEquals(apellido, filtro.apellido());
        assertEquals(anioNacimiento, filtro.anioNacimiento());
        assertEquals(pagina, filtro.pagina());
        assertEquals(tamano, filtro.tamano());
        assertEquals(orden, filtro.orden());
        assertEquals(direccion, filtro.direccion());
    }


    @Test
    @DisplayName("Debe permitir valores nulos en objetos Wrapper como Integer o String")
    void shouldAllowNullValues() {
        
        PersonaFiltro filtro = new PersonaFiltro(null, null, null, 0, 10, null, null);

        assertNull(filtro.nombre());
        assertNull(filtro.apellido());
        assertNull(filtro.anioNacimiento());
        assertEquals(0, filtro.pagina()); 
        assertEquals(10, filtro.tamano());
    }
}
