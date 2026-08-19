package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
    
    info = @Info(
        title = "API para gestión de personas",
        version = "1.0.0",
        description = """
            Servicio web RESTful diseñado para centralizar la administración y el ciclo de vida completo de los registros de personas en el sistema.

            ### Funcionalidades principales:
            * **Operaciones CRUD:** Permite el registro de nuevas personas, consulta individual por identificador único (ID), actualización completa de datos y eliminación física de registros.
            * **Consultas Masivas y Filtrado Avanzado:** Incluye endpoints para listar la totalidad de los registros o realizar búsquedas dinámicas mediante filtros específicos con respuestas estructuradas y paginadas.
            * **Validación de Reglas de Negocio:** Garantiza la integridad de la información mediante la validación de campos obligatorios y el control de unicidad para evitar correos electrónicos duplicados.
            * **Gestión Estandarizada de Errores:** Retorna respuestas de error claras y homogéneas (`ErrorResponseDto`) ante fallos de validación (400), recursos no encontrados (404) o conflictos de duplicidad (409).
            """,
        contact = @Contact(
            name = "Equipo de Desarrollo",
            email = "andres.velasquez@ceiba.com.co",
            url = "https://www.ceiba.com.co/"
        )
    )
)

public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
