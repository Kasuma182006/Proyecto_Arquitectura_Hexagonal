package com.example.demo.infrastructure.adapters.web.in.controllers;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.domain.model.Persona;
import com.example.demo.domain.ports.in.PersonaServiceInt;
import com.example.demo.infrastructure.adapters.web.data.dto.PageResultDto;
import com.example.demo.infrastructure.adapters.web.data.dto.PersonaDto;
import com.example.demo.infrastructure.adapters.web.data.dto.QueryParams;
import com.example.demo.infrastructure.adapters.web.data.mappers.PersonaMapper;
import com.example.demo.infrastructure.adapters.web.in.controllers.controllerAdvice.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/persona")
@Tag(name = "Personas", description = "Endpoints para la gestión, consulta, registro, edición y eliminación de registros de personas")
public class PersonaController {
   
    private final PersonaServiceInt personaServiceInt;
    private final PersonaMapper mapper;

    public PersonaController(PersonaServiceInt personaServiceInt, PersonaMapper mapper){
        this.personaServiceInt = personaServiceInt;
        this.mapper = mapper;
    }

    @GetMapping("/listar-personas")
    @Operation(
        summary = "Obtener el listado completo de personas", 
        description = "Consulta y recupera la totalidad de los registros de personas existentes en el sistema sin aplicar ningun tipo de filtro o paginacion."
    )
    @ApiResponse(
        responseCode = "200", 
        description = "Respuesta exitosa: Se retorna la lista con todas las personas registradas en el sistema."
    )
    public ResponseEntity<List<PersonaDto>> listarPersonas() {
        return ResponseEntity.ok(mapper.domainToDtoList(personaServiceInt.listarTodasLasPersonas()));
    }
    
    @GetMapping("/buscar-persona/{id}")
    @Operation(
        summary = "Buscar la información detallada de una persona por su ID", 
        description = "Consulta la base de datos para recuperar los datos de una persona específica utilizando su identificador único (ID)."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Respuesta exitosa: Se encontró la persona y se retornan sus datos detallados.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PersonaDto.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Error 404 (Not Found): Ocurre cuando el ID ingresado en la URL no coincide con ninguna persona registrada en el sistema.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    public ResponseEntity<PersonaDto> buscarPersona(
        @Parameter(description = "ID único de la persona a buscar", example = "1") 
        @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(mapper.domainToDto(personaServiceInt.buscarPersona(id)));
    }
    
    @PostMapping("/crear-persona")
    @Operation(
        summary = "Registrar una nueva persona en el sistema", 
        description = "Recibe la información de una persona en el cuerpo de la petición, la valida y efectúa su registro en la base de datos retornando el objeto recién creado."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Registro exitoso (201 Created): La persona fue creada correctamente en el sistema.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PersonaDto.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Error 400 (Bad Request): Ocurre cuando los datos enviados en el cuerpo de la solicitud contienen errores de formato o incumplen las reglas de validación (por ejemplo, campos obligatorios nulos o vacíos).",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "409", 
            description = "Error 409 (Conflict): Ocurre cuando se intenta registrar una persona utilizando una dirección de correo electrónico que ya pertenece a otro usuario en la base de datos.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    public ResponseEntity<PersonaDto> crearPersona(@Validated @RequestBody PersonaDto personaNueva) {
        Persona personaCreada = personaServiceInt.personaNueva(mapper.dtoToDomain(personaNueva));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.domainToDto(personaCreada));
    }

    @PutMapping("/editar-persona/{id}")
    @Operation(
        summary = "Actualizar datos de una persona existente", 
        description = "Permite modificar la información almacenada de una persona previamente registrada identificada por su ID mediante los nuevos datos proporcionados."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Actualización exitosa (200 OK): Los datos de la persona han sido modificados correctamente.",
            content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", example = "Datos Actualizados")) 
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Error 400 (Bad Request): Ocurre cuando la estructura o el contenido de los campos a actualizar no son válidos o fallan las restricciones del objeto.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Error 404 (Not Found): Ocurre cuando se intenta actualizar la información de una persona cuyo ID no existe registrado en la base de datos.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "409", 
            description = "Error 409 (Conflict): Ocurre cuando se intenta modificar el correo electrónico a uno que ya está asignado a otra persona en el sistema.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    public ResponseEntity<String> actualizarPersona(
        @Parameter(description = "ID de la persona que se va a editar", example = "1") 
        @PathVariable("id") Integer id, 
        @Validated @RequestBody PersonaDto persona
    ) {
        personaServiceInt.editarPersona(id, mapper.dtoToDomain(persona));
        return ResponseEntity.ok("Datos actualizados");
    }

    @DeleteMapping("/eliminar-persona/{id}")
    @Operation(
        summary = "Eliminar permanentemente a una persona por ID", 
        description = "Remueve físicamente el registro de la persona de la base de datos del sistema utilizando su ID como parámetro de búsqueda."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204", 
            description = "Eliminación exitosa (204 No Content): El registro de la persona ha sido borrado correctamente del sistema."
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Error 404 (Not Found): Ocurre cuando se intenta eliminar una persona pero el ID proporcionado no corresponde a ningún registro en la base de datos.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    public ResponseEntity<Void> eliminarPersona(
        @Parameter(description = "ID único de la persona que se desea eliminar", example = "1") 
        @PathVariable("id") Integer id
    ){
        personaServiceInt.eliminarPersona(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar-filtro")
    @Operation(
        summary = "Buscar personas mediante filtros avanzados y respuesta paginada", 
        description = "Permite realizar búsquedas dinámicas aplicando filtros de consulta (por ejemplo por campos específicos) devolviendo un conjunto de resultados organizado por páginas."
    )
    @ApiResponse(
        responseCode = "200", 
        description = "Respuesta exitosa: Se retorna la página de resultados obtenida en función de los criterios de filtrado y parámetros de paginación solicitados."
    )
    public ResponseEntity<PageResultDto> buscarFiltro(@ParameterObject QueryParams parametros) {

        return ResponseEntity.ok(mapper.pageResultToPageResultDto(personaServiceInt.buscarConFiltro(mapper.queryToPersonaFiltro(parametros))));
    }
}