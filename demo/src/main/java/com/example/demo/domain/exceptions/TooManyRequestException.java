package com.example.demo.domain.exceptions;

public class TooManyRequestException extends RuntimeException {
    public TooManyRequestException(String mensaje){
        super(mensaje);
    }
}
