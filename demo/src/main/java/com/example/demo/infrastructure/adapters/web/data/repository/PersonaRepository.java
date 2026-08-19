package com.example.demo.infrastructure.adapters.web.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.infrastructure.adapters.web.data.entity.PersonaEntity;


public interface PersonaRepository extends JpaRepository<PersonaEntity,Integer>,JpaSpecificationExecutor<PersonaEntity> {

}
