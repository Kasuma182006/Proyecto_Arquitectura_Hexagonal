package com.example.demo.infrastructure.adapters.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.application.PersonaUseCaseService;
import com.example.demo.domain.ports.out.PersonaRepositoryPort;

@Configuration
public class PersonaBeanConfig {
    
    @Bean
    public PersonaUseCaseService personaUseCaseService(PersonaRepositoryPort personaRepositoryPort){

     return new PersonaUseCaseService(personaRepositoryPort);
    }
}
