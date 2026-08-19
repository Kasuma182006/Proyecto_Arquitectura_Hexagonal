package com.example.demo.dominio.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.domain.model.filter.PageResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageResultTest {

    @Test
    @DisplayName("Debe crear la instancia de PageResult y recuperar sus valores correctamente")
    void shouldCreatePageResultAndVerifyAttributes() {
        
        List<String> contenido = List.of("Elemento 1", "Elemento 2");
        long totalElementos = 50;
        int totalPaginas = 5;
        int paginaActual = 1;
        int tamano = 10;
        boolean primero = false;
        boolean ultimo = false;

        PageResult<String> pageResult = new PageResult<>(
                contenido, totalElementos, totalPaginas, paginaActual, tamano, primero, ultimo
        );

     
        assertNotNull(pageResult);
        assertEquals(contenido, pageResult.contenido());
        assertEquals(totalElementos, pageResult.totalElementos());
        assertEquals(totalPaginas, pageResult.totalPaginas());
        assertEquals(paginaActual, pageResult.paginaActual());
        assertEquals(tamano, pageResult.tamaño());
        assertFalse(pageResult.primero());
        assertFalse(pageResult.ultimo());
    }

}