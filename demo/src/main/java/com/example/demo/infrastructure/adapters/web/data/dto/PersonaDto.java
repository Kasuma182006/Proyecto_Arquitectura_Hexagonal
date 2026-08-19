package com.example.demo.infrastructure.adapters.web.data.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Schema(description = "Clase DTO para empaquetar las respuestas del sistema hacía el usuario final")
public class PersonaDto {
    @Schema(description = "ID unico para indentificar a la persona",example = "1")
    private Integer id; 

    @Schema(description = "Nombre de la persona", example =  "Juan")
    @NotBlank(message = "El campo 'nombre' no puede estar vacio")
    private String nombre;

    @Schema(description = "Apellido de la persona", example = "Velasquez")
    @NotBlank(message = "El campo 'apellido' no puede estar vacio")
    private String apellido;

    @Schema(description = "Email de la persona, debe tener formato valido", example = "andres@gmail.com")
    @Email (message = "El formato no concuerda con un Email")
    @NotBlank(message = "El campo 'Email' no puede estar vacio")
    private String email;


    @Schema(description= "Fecha de nacimiento, no puede ser mayor al dia de hoy", example = "8/4/2026")
    @PastOrPresent(message = "La fecha no puede ser futura")
    @NotNull(message = "El campo 'fechaNacimiento' no puede estar vacio")
    private LocalDate fechaNacimiento;

    public PersonaDto(Integer id, String nombre, String apellido, String email, LocalDate fechaNacimiento){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
    }
    public PersonaDto() {
    }

    //Getters
    public Integer getId(){
        return id;
    }
  
    public String getNombre(){
        return nombre;
    }

    public String getApellido(){
        return apellido;
    }

    public String getEmail(){
        return email;
    }

    public LocalDate getFechaNacimiento(){
        return fechaNacimiento;
    }

    
    public void setId(Integer id){
        this.id = id;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }
}
