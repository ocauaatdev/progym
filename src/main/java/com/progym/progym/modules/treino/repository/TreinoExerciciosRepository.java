package com.progym.progym.modules.treino.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progym.progym.modules.treino.entity.TreinoExercicios;

public interface TreinoExerciciosRepository extends JpaRepository<TreinoExercicios, UUID>{
    
}
