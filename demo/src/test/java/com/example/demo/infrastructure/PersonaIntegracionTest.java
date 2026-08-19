package com.example.demo.infrastructure;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.infrastructure.adapters.web.data.dto.PersonaDto;
import com.example.demo.infrastructure.adapters.web.data.repository.PersonaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc 
@ActiveProfiles("test") 
public class PersonaIntegracionTest {

    private static final String URL_LISTAR = "/persona/listar-personas";
    private static final String URL_BUSCAR = "/persona/buscar-persona/{id}";
    private static final String URL_CREAR  = "/persona/crear-persona";
    private static final String URL_EDITAR = "/persona/editar-persona/{id}";
    private static final String URL_ELIMINAR = "/persona/eliminar-persona/{id}";
    private static final String MSG_NO_ENCONTRADO = "No se han encontrado personas con ese ID";
    private static final String MSG_EMAIL_DUPLICADO = "Email duplicado, intenta con otro Email";
    private static final String MSG_ACTUALIZACION_EXITOSA = "Datos actualizados";
    private static final String URL_BUSCAR_FILTRO = "/persona/buscar-filtro";

    @Autowired
    private MockMvc clienteHttpVirtual;

    private ObjectMapper mapeadorObjetos = new ObjectMapper().findAndRegisterModules();

    @Autowired
    private PersonaRepository personaRepository; 

    @BeforeEach
    void limpiarBaseDeDatos() {
        personaRepository.deleteAll();
    }

    
    private PersonaDto crearPersonaDePrueba(String nombre, String apellido, String email) throws Exception {
        PersonaDto personaDto = new PersonaDto(null, nombre, apellido, email, LocalDate.of(1990, 8, 20));
        String jsonRespuesta = clienteHttpVirtual.perform(post(URL_CREAR)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapeadorObjetos.writeValueAsString(personaDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return mapeadorObjetos.readValue(jsonRespuesta, PersonaDto.class);
    }

    @Nested
    @DisplayName("Pruebas de Creación de Personas")
    class CrearPersonaTests {

        @Test
        @DisplayName("Debería crear una persona correctamente en H2")
        void deberiaCrearPersonaExitosamente() throws Exception {
            PersonaDto personaNueva = new PersonaDto(null, "Andrés", "Gómez", "andres@example.com", LocalDate.of(1990, 8, 20));

            clienteHttpVirtual.perform(post(URL_CREAR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapeadorObjetos.writeValueAsString(personaNueva)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.nombre").value("Andrés"))
                    .andExpect(jsonPath("$.email").value("andres@example.com"));
        }

        @Test
        @DisplayName("Debería retornar 409 Conflict cuando el email ya existe")
        void deberia_Retornar409_Cuando_SeIntenteRegistrarUnEmailDuplicadoRealEnH2() throws Exception {
            crearPersonaDePrueba("Andrés", "Gómez", "andres@example.com");

            PersonaDto personaDuplicada = new PersonaDto(null, "Andrés", "Gómez", "andres@example.com", LocalDate.of(1990, 8, 20));

            clienteHttpVirtual.perform(post(URL_CREAR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapeadorObjetos.writeValueAsString(personaDuplicada)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
                    .andExpect(jsonPath("$.error").value(HttpStatus.CONFLICT.getReasonPhrase()))
                    .andExpect(jsonPath("$.message").value(MSG_EMAIL_DUPLICADO));
        }
    }

    @Nested
    @DisplayName("Pruebas de Listado de Personas")
    class ListarPersonasTests {

        @Test
        @DisplayName("Debería listar las personas existentes")
        void deberiaListarPersonas() throws Exception {
            PersonaDto personaCreada = crearPersonaDePrueba("Andrés", "Gómez", "andres@example.com");

            clienteHttpVirtual.perform(get(URL_LISTAR))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(jsonPath("$[0].id").value(personaCreada.getId()))
                    .andExpect(jsonPath("$[0].nombre").value("Andrés"));
        }
    }

    @Nested
    @DisplayName("Pruebas de Búsqueda por ID")
    class BuscarPersonaTests {

        @Test
        @DisplayName("Debería obtener una persona por su ID")
        void deberiaBuscarPersonaPorId() throws Exception {
            PersonaDto personaCreada = crearPersonaDePrueba("Andrés", "Gómez", "andres@example.com");

            clienteHttpVirtual.perform(get(URL_BUSCAR, personaCreada.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(personaCreada.getId()))
                    .andExpect(jsonPath("$.email").value("andres@example.com"));
        }

        @Test
        @DisplayName("Debería retornar 404 cuando la persona no existe")
        void deberiaRetornar404CuandoPersonaNoExiste() throws Exception {
            clienteHttpVirtual.perform(get(URL_BUSCAR, 999))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                    .andExpect(jsonPath("$.message").value(MSG_NO_ENCONTRADO));
        }
    }

    @Nested
    @DisplayName("Pruebas de Edición de Personas")
    class EditarPersonaTests {

        @Test
        @DisplayName("Debería actualizar los datos de una persona existente")
        void deberiaEditarPersonaExitosamente() throws Exception {
            PersonaDto personaCreada = crearPersonaDePrueba("Andrés", "Gómez", "andres@example.com");

            PersonaDto personaModificada = new PersonaDto(
                    personaCreada.getId(), 
                    "Andrés Felipe", 
                    "Gómez Restrepo", 
                    "andres.nuevo@example.com", 
                    LocalDate.of(1990, 8, 20)
            );

            clienteHttpVirtual.perform(put(URL_EDITAR, personaCreada.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapeadorObjetos.writeValueAsString(personaModificada)))
                    .andExpect(status().isOk())
                    .andExpect(content().string(MSG_ACTUALIZACION_EXITOSA));

         
            clienteHttpVirtual.perform(get(URL_BUSCAR, personaCreada.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nombre").value("Andrés Felipe"))
                    .andExpect(jsonPath("$.email").value("andres.nuevo@example.com"));
        }
    }

    @Nested
    @DisplayName("Pruebas de Eliminación de Personas")
    class EliminarPersonaTests {

        @Test
        @DisplayName("Debería eliminar a una persona y retornar 404 al buscarla posteriormente")
        void deberiaEliminarPersonaExitosamente() throws Exception {
            PersonaDto personaCreada = crearPersonaDePrueba("Andrés", "Gómez", "andres@example.com");

            clienteHttpVirtual.perform(delete(URL_ELIMINAR, personaCreada.getId()))
                    .andExpect(status().isNoContent());

            clienteHttpVirtual.perform(get(URL_BUSCAR, personaCreada.getId()))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                    .andExpect(jsonPath("$.message").value(MSG_NO_ENCONTRADO));
        }
    }
    @Nested
    @DisplayName("Pruebas de Búsqueda con Filtro y Paginación")
    class BuscarConFiltroTests {

        @Test
        @DisplayName("Debería filtrar personas por apellido, ordenar de forma ascendente y paginar el resultado")
        void deberiaFiltrarPersonasPorApellidoYPAGINAR() throws Exception {
        
            crearPersonaDePrueba("Andrés", "Gómez", "andres@example.com");
            crearPersonaDePrueba("Ana", "Gómez", "ana@example.com");
            crearPersonaDePrueba("Carlos", "Pérez", "carlos@example.com");
            clienteHttpVirtual.perform(get(URL_BUSCAR_FILTRO)
                    .param("apellido", "Gómez")
                    .param("pagina", "0")
                    .param("tamano", "10")
                    .param("orden", "nombre")
                    .param("direccion", "ASC"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.contenido").isArray())
                    .andExpect(jsonPath("$.contenido.length()").value(2))
                    .andExpect(jsonPath("$.contenido[0].nombre").value("Ana"))
                    .andExpect(jsonPath("$.contenido[1].nombre").value("Andrés"))
                    .andExpect(jsonPath("$.totalElementos").value(2))
                    .andExpect(jsonPath("$.totalPaginas").value(1))
                    .andExpect(jsonPath("$.paginaActual").value(0))
                    .andExpect(jsonPath("$.tamaño").value(10))
                    .andExpect(jsonPath("$.primero").value(true))
                    .andExpect(jsonPath("$.ultimo").value(true));
        }

        @Test
        @DisplayName("Debería retornar una lista vacía cuando ningún registro coincide con el filtro")
        void deberiaRetornarListaVaciaCuandoNoCoincideElFiltro() throws Exception {
           
            crearPersonaDePrueba("Andrés", "Gómez", "andres@example.com");

           
            clienteHttpVirtual.perform(get(URL_BUSCAR_FILTRO)
                    .param("nombre", "NoExistente")
                    .param("pagina", "0")
                    .param("tamano", "10")
                    .param("orden", "id")
                    .param("direccion", "ASC"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.contenido").isArray())
                    .andExpect(jsonPath("$.contenido.length()").value(0))
                    .andExpect(jsonPath("$.totalElementos").value(0))
                    .andExpect(jsonPath("$.totalPaginas").value(0))
                    .andExpect(jsonPath("$.primero").value(true))
                    .andExpect(jsonPath("$.ultimo").value(true));
        }

        @Test
        @DisplayName("Debería validar correctamente los metadatos de paginación (primero/último y páginas totales)")
        void deberiaPaginarCorrectamenteEnVariasPaginas() throws Exception {
            
            crearPersonaDePrueba("Andrés", "Gómez", "andres@example.com");
            crearPersonaDePrueba("Beatriz", "López", "beatriz@example.com");
            crearPersonaDePrueba("Carlos", "Pérez", "carlos@example.com");

            
            clienteHttpVirtual.perform(get(URL_BUSCAR_FILTRO)
                    .param("pagina", "0")
                    .param("tamano", "2")
                    .param("orden", "nombre")
                    .param("direccion", "ASC"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.contenido.length()").value(2))
                    .andExpect(jsonPath("$.totalElementos").value(3))
                    .andExpect(jsonPath("$.totalPaginas").value(2))
                    .andExpect(jsonPath("$.paginaActual").value(0))
                    .andExpect(jsonPath("$.primero").value(true))
                    .andExpect(jsonPath("$.ultimo").value(false));

            clienteHttpVirtual.perform(get(URL_BUSCAR_FILTRO)
                    .param("pagina", "1")
                    .param("tamano", "2")
                    .param("orden", "nombre")
                    .param("direccion", "ASC"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.contenido.length()").value(1))
                    .andExpect(jsonPath("$.totalElementos").value(3))
                    .andExpect(jsonPath("$.paginaActual").value(1))
                    .andExpect(jsonPath("$.primero").value(false))
                    .andExpect(jsonPath("$.ultimo").value(true));
        }
    }
}