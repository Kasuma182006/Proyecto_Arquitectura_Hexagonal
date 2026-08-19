package com.example.demo.application;

import com.example.demo.domain.exceptions.PersonaNoEncontradaException;
import com.example.demo.domain.model.Email;
import com.example.demo.domain.model.FechaNacimiento;
import com.example.demo.domain.model.NombreCompleto;
import com.example.demo.domain.model.Persona;
import com.example.demo.domain.ports.out.PersonaRepositoryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonaUseCaseServiceTest {

    @Mock
    private PersonaRepositoryPort personaRepositoryPort;

    @InjectMocks
    private PersonaUseCaseService personaUseCaseService;

    private Persona personaTest;

    @BeforeEach
    void setUp() {
        personaTest = new Persona(
                1,
                new NombreCompleto("Carlos", "Santana"),
                new Email("carlos@mail.com"),
                new FechaNacimiento(LocalDate.of(1990, 5, 15))
        );
    }

    @Nested
    @DisplayName("Pruebas de creación de personas")
    class CrearPersonaTests {

        @Test
        @DisplayName("Debería guardar y retornar la nueva persona correctamente")
        void deberiaCrearPersonaExitosa() {
            when(personaRepositoryPort.guardar(any(Persona.class))).thenReturn(personaTest);

            Persona resultado = personaUseCaseService.personaNueva(personaTest);

            assertNotNull(resultado);
            assertEquals(personaTest, resultado);
            verify(personaRepositoryPort, times(1)).guardar(personaTest);
        }
    }

    @Nested
    @DisplayName("Pruebas de listado")
    class ListarPersonasTests {

        @Test
        @DisplayName("Debería retornar lista de personas ordenadas")
        void deberiaListarTodasLasPersonas() {
            List<Persona> listaEsperada = List.of(personaTest);
            when(personaRepositoryPort.buscarTodasOrdenadasPorApellido()).thenReturn(listaEsperada);

            List<Persona> resultado = personaUseCaseService.listarTodasLasPersonas();

            
            assertEquals(1, resultado.size());
            assertTrue(resultado.contains(personaTest));
            verify(personaRepositoryPort, times(1)).buscarTodasOrdenadasPorApellido();
        }
    }

    @Nested
    @DisplayName("Pruebas de búsqueda por ID")
    class BuscarPersonaTests {

        @Test
        @DisplayName("Debería retornar una persona si existe en la base de datos")
        void deberiaBuscarPersonaPorId() {
            when(personaRepositoryPort.buscarPorId(1)).thenReturn(personaTest);

            Persona resultado = personaUseCaseService.buscarPersona(1);

            assertEquals(personaTest, resultado);
            verify(personaRepositoryPort, times(1)).buscarPorId(1);
        }
    }

    @Nested
    @DisplayName("Pruebas de edición de datos")
    class EditarPersonaTests {

        @Test
        @DisplayName("Debería editar exitosamente una persona existente")
        void deberiaEditarPersonaExitosamente() {
            when(personaRepositoryPort.existePorId(1)).thenReturn(true);
            when(personaRepositoryPort.guardar(any(Persona.class))).thenReturn(personaTest);

            Persona resultado = personaUseCaseService.editarPersona(1, personaTest);

            assertEquals(personaTest, resultado);
            verify(personaRepositoryPort, times(1)).existePorId(1);
            verify(personaRepositoryPort, times(1)).guardar(personaTest);
        }

        @Test
        @DisplayName("Debería lanzar PersonaNoEncontradaException si la persona no existe")
        void deberiaLanzarExcepcionAlEditarSiNoExiste() {
            when(personaRepositoryPort.existePorId(99)).thenReturn(false);

            PersonaNoEncontradaException exception = assertThrows(
                    PersonaNoEncontradaException.class,
                    () -> personaUseCaseService.editarPersona(99, personaTest)
            );

            assertEquals("No se han encontrado personas con ese ID", exception.getMessage());
            verify(personaRepositoryPort, never()).guardar(any());
        }
    }

    @Nested
    @DisplayName("Pruebas de eliminación")
    class EliminarPersonaTests {

        @Test
        @DisplayName("Debería eliminar a la persona si existe")
        void deberiaEliminarPersonaExitosamente() {
            when(personaRepositoryPort.existePorId(1)).thenReturn(true);

            personaUseCaseService.eliminarPersona(1);

            verify(personaRepositoryPort, times(1)).existePorId(1);
            verify(personaRepositoryPort, times(1)).eliminarPorId(1);
        }

        @Test
        @DisplayName("Debería lanzar PersonaNoEncontradaException si la persona a eliminar no existe")
        void deberiaLanzarExcepcionAlEliminarSiNoExiste() {
            when(personaRepositoryPort.existePorId(99)).thenReturn(false);

            PersonaNoEncontradaException exception = assertThrows(
                    PersonaNoEncontradaException.class,
                    () -> personaUseCaseService.eliminarPersona(99)
            );

            assertEquals("No se han encontrado personas con ese ID", exception.getMessage());
            verify(personaRepositoryPort, never()).eliminarPorId(anyInt());
        }
    }
}