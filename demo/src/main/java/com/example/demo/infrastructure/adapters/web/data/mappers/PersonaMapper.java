package com.example.demo.infrastructure.adapters.web.data.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.domain.model.Email;
import com.example.demo.domain.model.FechaNacimiento;
import com.example.demo.domain.model.NombreCompleto;
import com.example.demo.domain.model.Persona;
import com.example.demo.domain.model.filter.PageResult;
import com.example.demo.domain.model.filter.PersonaFiltro;
import com.example.demo.infrastructure.adapters.web.data.dto.PageResultDto;
import com.example.demo.infrastructure.adapters.web.data.dto.PersonaDto;
import com.example.demo.infrastructure.adapters.web.data.dto.QueryParams;
import com.example.demo.infrastructure.adapters.web.data.entity.PersonaEntity;

@Component
public class PersonaMapper {


      public Persona dtoToDomain(PersonaDto dto) {
        return new Persona(
            dto.getId(),
            new NombreCompleto(dto.getNombre(), dto.getApellido()),
            new Email(dto.getEmail()),
            new FechaNacimiento(dto.getFechaNacimiento())
        );
    }

    public PersonaDto domainToDto(Persona domain) {
       return new PersonaDto(
            domain.id(),
            domain.nombreCompleto().nombre(),
            domain.nombreCompleto().apellido(),
            domain.email().email() ,
            domain.fechaNacimiento().fechaNacimiento()
        );
        
    }

    public List<PersonaDto> domainToDtoList(List<Persona> domains) {
        return domains.stream().map(this::domainToDto).collect(Collectors.toList());
    }


    public PersonaEntity personaToEntity(Persona persona){
        return new PersonaEntity(
            persona.id(),
            persona.nombreCompleto().nombre(),
            persona.nombreCompleto().apellido(),
            persona.email().email(),
            persona.fechaNacimiento().fechaNacimiento()
        );
    }

    public Persona entityToPersona(PersonaEntity entity){
        return new Persona(
            entity.getId(),
            new NombreCompleto(entity.getNombre(), entity.getApellido()),
            new Email(entity.getEmail()),
            new FechaNacimiento(entity.getFechaNacimiento())
        );
    }


    public PageResultDto pageResultToPageResultDto (PageResult<Persona> paginas){
        return new PageResultDto(
            paginas.contenido().stream().map(this::domainToDto).toList(),
            paginas.totalElementos(), 
            paginas.totalPaginas(),
            paginas.paginaActual(), 
            paginas.tamaño(),
            paginas.primero(),
            paginas.ultimo()
        );
    }

    public PersonaFiltro queryToPersonaFiltro(QueryParams parametros){
        return new PersonaFiltro(
            parametros.nombre(),
            parametros.apellido(),
            parametros.anio(),
            parametros.pagina(),
            parametros.tamano(),
            parametros.orden(),
            parametros.direccion()
        );
    }

    
}
