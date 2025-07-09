package com.progym.progym.modules.treino.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progym.progym.modules.treino.entity.Exercicio;
import java.util.Optional;


public interface ExercicioRepository extends JpaRepository<Exercicio, UUID> {
    
    Optional<Exercicio> findByNome(String nome);
}
