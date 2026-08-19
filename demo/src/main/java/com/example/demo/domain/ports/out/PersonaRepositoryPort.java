package com.example.demo.domain.ports.out;

import java.util.List;


import com.example.demo.domain.model.Persona;
import com.example.demo.domain.model.filter.PageResult;
import com.example.demo.domain.model.filter.PersonaFiltro;

public interface PersonaRepositoryPort {
    Persona guardar(Persona persona);
    List<Persona> buscarTodasOrdenadasPorApellido(); 
    Persona buscarPorId(Integer id);
    boolean existePorId(Integer id);
    void eliminarPorId(Integer id);
    PageResult<Persona> buscarConFiltro(PersonaFiltro filtro);

}
