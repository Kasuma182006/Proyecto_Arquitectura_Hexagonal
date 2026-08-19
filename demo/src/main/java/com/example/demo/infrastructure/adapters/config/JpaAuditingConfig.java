package com.example.demo.infrastructure.adapters.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "proveedorDeAuditor")
public class JpaAuditingConfig {
    @Bean
    public AuditorAware<String> proveedorDeAuditor() {
        return () -> Optional.of("Usuario_Desarrollo_API_"); 
    }
}
