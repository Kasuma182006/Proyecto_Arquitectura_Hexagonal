package com.example.demo.infrastructure.adapters.web.data.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class PersonaEntity extends AuditoriaBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 200)
    private String apellido; 

    @Column(name = "email", nullable = false, length = 200,unique = true)
    private String email;

    @Column(name = "fechaNacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    public PersonaEntity(Integer id, String nombre, String apellido, String email, LocalDate fechaNacimiento){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        
    }
    
    public PersonaEntity(){

    }


    //Getters
    public int getId(){
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

    //Setters


    public void setId(int id){
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
