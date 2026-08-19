package com.example.demo.infrastructure.adapters.web.out;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import com.example.demo.domain.exceptions.PersonaNoEncontradaException;
import com.example.demo.domain.model.Persona;
import com.example.demo.domain.model.filter.PageResult;
import com.example.demo.domain.model.filter.PersonaFiltro;
import com.example.demo.domain.ports.out.PersonaRepositoryPort;
import com.example.demo.infrastructure.adapters.web.data.entity.PersonaEntity;
import com.example.demo.infrastructure.adapters.web.data.mappers.PersonaMapper;
import com.example.demo.infrastructure.adapters.web.data.repository.PersonaRepository;
import com.example.demo.infrastructure.adapters.web.data.specification.PersonaSpecification;

@Component
public class PersonaRepositoryAdapter implements PersonaRepositoryPort {
    
    private final PersonaRepository personaRepository;
    private final PersonaMapper mapper;

    public PersonaRepositoryAdapter( PersonaRepository personaRepository,PersonaMapper mapper){
        this.personaRepository = personaRepository;
        this.mapper = mapper;
    }
    
    public Persona guardar(Persona persona){
        return mapper.entityToPersona( personaRepository.save(mapper.personaToEntity(persona)));
    }

    public List<Persona> buscarTodasOrdenadasPorApellido(){
        return personaRepository.findAll(Sort.by("apellido")).stream()
                .map(mapper::entityToPersona)
                .toList();
    }

    public  Persona buscarPorId(Integer id){
        return mapper.entityToPersona(personaRepository.findById(id).orElseThrow(() -> new PersonaNoEncontradaException("No se han encontrado personas con ese ID")));
    }

    public boolean existePorId(Integer id){
        return personaRepository.existsById(id);
    }

    public  void eliminarPorId(Integer id){

        personaRepository.deleteById(id);

    }

    public PageResult<Persona> buscarConFiltro(PersonaFiltro filtro) {
        
        Sort sort = Sort.by(Sort.Direction.fromString(filtro.direccion()), filtro.orden());
        Pageable pageable = PageRequest.of(filtro.pagina(),filtro.tamano(), sort);

        
        Page<PersonaEntity> entityPage = personaRepository.findAll(
                PersonaSpecification.filter(filtro),
                pageable
        );


       return new PageResult<>(
        entityPage.getContent().stream().map(mapper::entityToPersona).toList(),
        entityPage.getTotalElements(),
        entityPage.getTotalPages(),
        entityPage.getNumber(),
        entityPage.getSize(),
        entityPage.isFirst(),
        entityPage.isLast()
        );
    }
}
