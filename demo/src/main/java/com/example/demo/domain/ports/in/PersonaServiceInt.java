package com.example.demo.domain.ports.in;

import java.util.List;

import com.example.demo.domain.model.Persona;
import com.example.demo.domain.model.filter.PageResult;
import com.example.demo.domain.model.filter.PersonaFiltro;

public interface PersonaServiceInt {
    
    Persona personaNueva(Persona crearPersona);
    
    List<Persona> listarTodasLasPersonas();
    
    Persona buscarPersona(Integer id);
    
    Persona editarPersona(Integer id, Persona persona);
    
    void eliminarPersona(Integer id);
    PageResult<Persona> buscarConFiltro(PersonaFiltro filtro);
    
}
