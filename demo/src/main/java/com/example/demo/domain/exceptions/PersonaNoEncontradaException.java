package com.example.demo.domain.exceptions;

public class PersonaNoEncontradaException extends RuntimeException{
    public PersonaNoEncontradaException(String mensaje){
        super(mensaje);
    }
}
