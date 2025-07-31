package com.progym.progym.modules.treino.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.progym.progym.modules.treino.entity.TreinoExercicios;

public interface TreinoExerciciosRepository extends JpaRepository<TreinoExercicios, UUID>{
    

    @Query("SELECT te FROM treino_exercicios te JOIN FETCH te.exercicio e JOIN FETCH te.treino t WHERE t.id = :treinoId")
    List<TreinoExercicios> findByTreinoIdWithExerciciosAndTreino(@Param("treinoId") UUID treinoId);
}
