package com.example.demo.application;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.List;

import com.example.demo.domain.exceptions.PersonaNoEncontradaException;
import com.example.demo.domain.model.Persona;
import com.example.demo.domain.model.filter.PageResult;
import com.example.demo.domain.model.filter.PersonaFiltro;
import com.example.demo.domain.ports.in.PersonaServiceInt;
import com.example.demo.domain.ports.out.PersonaRepositoryPort;

public class PersonaUseCaseService implements PersonaServiceInt{
    private final PersonaRepositoryPort personaRepositoryPort;
    private static final Logger log = System.getLogger(PersonaUseCaseService.class.getName());
    public PersonaUseCaseService(PersonaRepositoryPort personaRepositoryPort){
        this.personaRepositoryPort = personaRepositoryPort;
    }

    
    public Persona personaNueva(Persona personaNueva) {
        log.log(Level.INFO, "Creando a: {0}", personaNueva.nombreCompleto());
        
        Persona personaCreada = personaRepositoryPort.guardar(personaNueva);
        log.log(Level.DEBUG, "Se creó a: {0}", personaCreada);
        return personaCreada;
    }

    
    public List<Persona> listarTodasLasPersonas() {
        log.log(Level.INFO, "Listando a todas las personas ...");
        return personaRepositoryPort.buscarTodasOrdenadasPorApellido();
    }

    
    public Persona buscarPersona(Integer id) {
        log.log(Level.INFO, "Buscando a: {0}", id);

        return personaRepositoryPort.buscarPorId(id);
    }

    
    public Persona editarPersona(Integer id, Persona personaDatosNuevos) {
        log.log(Level.DEBUG, "Editando a: {0}", personaDatosNuevos);

        
        if (!personaRepositoryPort.existePorId(id)) {
            throw new PersonaNoEncontradaException("No se han encontrado personas con ese ID");
        }
        
        Persona resultado = personaRepositoryPort.guardar(personaDatosNuevos);
        log.log(Level.INFO, "Datos actualizados de: {0}", id);
        return resultado;
    }

   
    public void eliminarPersona(Integer id) {
        if (!personaRepositoryPort.existePorId(id)) {
            throw new PersonaNoEncontradaException("No se han encontrado personas con ese ID");
        }
        log.log(Level.INFO, "Eliminando a: {0}", id);
        personaRepositoryPort.eliminarPorId(id);
        log.log(Level.INFO, "Persona eliminada: {0}", id);
    }

    public  PageResult<Persona> buscarConFiltro(PersonaFiltro filtro){
        return personaRepositoryPort.buscarConFiltro(filtro);
    }
}
