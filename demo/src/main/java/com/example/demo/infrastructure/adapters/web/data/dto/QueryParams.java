package com.example.demo.infrastructure.adapters.web.data.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Parámetros para filtrado, ordenamiento y paginación de búsquedas")
public record QueryParams (

    @Parameter(description = "Filtro por nombre o coincidencia parcial", example = "Juan")
    String nombre,

    @Parameter(description = "Filtro por apellido o coincidencia parcial", example = "Pérez")
    String apellido,

    @Parameter(description = "Filtro por año de nacimiento", example = "1990")
    Integer anio,

    @Parameter(
        description = "Número de la página solicitada (inicia en 0)", 
        example = "0", 
        schema = @Schema(defaultValue = "0") 
    )
    Integer pagina,

    @Parameter(
        description = "Cantidad de registros a retornar por página", 
        example = "10", 
        schema = @Schema(defaultValue = "10") 
    )
    Integer tamano,

    @Parameter(
        description = "Atributo por el cual ordenar los resultados", 
        example = "apellido", 
        schema = @Schema(defaultValue = "apellido") 
    )
    String orden,

    @Parameter(
        description = "Dirección del ordenamiento ('asc' o 'desc')", 
        example = "asc", 
        schema = @Schema(defaultValue = "asc") 
    )
    String direccion

) {

    public QueryParams {
        pagina = (pagina == null) ? 0 : pagina;
        tamano = (tamano == null) ? 10 : tamano;
        orden = (orden == null || orden.isBlank()) ? "apellido" : orden;
        direccion = (direccion == null || direccion.isBlank()) ? "asc" : direccion;
    }
}