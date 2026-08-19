package com.example.demo.infrastructure.adapters.web.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO de respuesta paginada con la lista de personas y metadatos de paginación")
public record PageResultDto(

    @Schema(description = "Lista de personas pertenecientes a la página actual")
    List<PersonaDto> contenido,

    @Schema(description = "Cantidad total de registros encontrados en la base de datos", example = "50")
    long totalElementos,

    @Schema(description = "Cantidad total de páginas disponibles según el tamaño de página", example = "5")
    int totalPaginas,

    @Schema(description = "Número de la página actual (iniciando en índice 0)", example = "0")
    int paginaActual,

    @Schema(description = "Cantidad máxima de elementos solicitados por página", example = "10")
    int tamaño,

    @Schema(description = "Indica si la página actual es la primera de la consulta", example = "true")
    boolean primero,

    @Schema(description = "Indica si la página actual es la última de la consulta", example = "false")
    boolean ultimo

) {}