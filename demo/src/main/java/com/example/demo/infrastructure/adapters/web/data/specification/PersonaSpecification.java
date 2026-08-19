package com.example.demo.infrastructure.adapters.web.data.specification;


import com.example.demo.domain.model.filter.PersonaFiltro;
import com.example.demo.infrastructure.adapters.web.data.entity.PersonaEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonaSpecification {

    private PersonaSpecification() {
        
    }

    public static Specification<PersonaEntity> filter(PersonaFiltro filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            
            if (filtro.nombre() != null && !filtro.nombre().isBlank()) {
                predicates.add(
                    cb.like(cb.lower(root.get("nombre")), "%" + filtro.nombre().toLowerCase() + "%")
                );
            }

           
            if (filtro.apellido() != null && !filtro.apellido().isBlank()) {
                predicates.add(
                    cb.like(cb.lower(root.get("apellido")), "%" + filtro.apellido().toLowerCase() + "%")
                );
            }

         
            if (filtro.anioNacimiento() != null) {
                int primerMes = 1;
                int diaUno = 1;
                int ultimoMes = 12;
                int ultimoDia = 31;
                LocalDate startOfYear = LocalDate.of(filtro.anioNacimiento(), primerMes, diaUno);
                LocalDate endOfYear = LocalDate.of(filtro.anioNacimiento(), ultimoMes, ultimoDia);

                predicates.add(
                    cb.between(root.get("fechaNacimiento"), startOfYear, endOfYear)
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}