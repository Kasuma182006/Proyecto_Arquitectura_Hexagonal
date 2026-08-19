package com.example.demo.infrastructure.adapters.web.in.controllers.controllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.domain.exceptions.PersonaNoEncontradaException;
import com.example.demo.domain.exceptions.TooManyRequestException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor 
public class GlobalExceptionHandler {

    private final HttpServletRequest request; 

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handlerArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<String> listaDeErroresValidacion = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> 
            listaDeErroresValidacion.add(error.getField() + " : " + error.getDefaultMessage())
        );

        return buildErrorResponse(HttpStatus.BAD_REQUEST, listaDeErroresValidacion);
    }

    @ExceptionHandler(PersonaNoEncontradaException.class)
    public ResponseEntity<ErrorResponseDto> handlerPersonaNoEncontradaException(PersonaNoEncontradaException exception) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handlerEmailDuplicadoExcepcionBaseDeDatos(DataIntegrityViolationException exception) {
        return buildErrorResponse(HttpStatus.CONFLICT, "Email duplicado, intenta con otro Email");
    }

    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handlerIlegalArguments(IllegalArgumentException exception) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Datos Invalidos");
    }

    @ExceptionHandler(TooManyRequestException.class)
    public ResponseEntity<ErrorResponseDto> handlerTooManyRequestException(TooManyRequestException exception) {
        return buildErrorResponse(HttpStatus.TOO_MANY_REQUESTS, exception.getMessage());
    }



   
    private ResponseEntity<ErrorResponseDto> buildErrorResponse(HttpStatus status, Object message) {
        ErrorResponseDto response = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())  
                .build();
        return ResponseEntity.status(status).body(response);


    }
}