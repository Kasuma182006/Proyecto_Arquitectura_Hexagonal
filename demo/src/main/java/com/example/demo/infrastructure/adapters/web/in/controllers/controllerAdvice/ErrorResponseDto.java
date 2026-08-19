package com.example.demo.infrastructure.adapters.web.in.controllers.controllerAdvice;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(description = "Dto enfocado en la respuesta del sistema a los errores que se pueden presentar")
public class ErrorResponseDto {

   @Schema(
        description = "Fecha y hora exacta en la que ocurrió el error", 
        example = "2026-08-04T15:30:00"
    )
    private LocalDateTime timestamp;

    @Schema(
        description = "Código numérico del estado HTTP del error", 
        example = "400"
    )
    private int status;

    @Schema(
        description = "Descripción corta del estado HTTP o tipo de error", 
        example = "Bad Request"
    )
    private String error;

    @Schema(
        description = "Mensaje detallado del error (puede ser un texto simple o una lista de errores de validación)", 
        example = "El campo 'nombre' es obligatorio y no puede estar vacío"
    )
    private Object message;

    @Schema(
        description = "Ruta o URL del endpoint en el que ocurrió la excepción", 
        example = "/ceiba/persona/crear-persona"
    )
    private String path;
}
