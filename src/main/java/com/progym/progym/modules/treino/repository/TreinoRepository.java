package com.progym.progym.modules.treino.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.progym.progym.modules.treino.entity.TreinoEntity;

public interface TreinoRepository extends JpaRepository<TreinoEntity, UUID>{
    
   // Buscar treinos pelo ID do aluno, carregando o Professor junto
    @Query("SELECT t FROM treino t LEFT JOIN FETCH t.professor p WHERE t.aluno.id = :alunoId")
    List<TreinoEntity> findByAlunoId(@Param("alunoId") UUID alunoId); // Sobrescreve o método existente

    //Buscar treinos pelo ID do professor, carregando o Aluno junto
    @Query("SELECT t FROM treino t JOIN FETCH t.aluno a WHERE t.professor.id = :professorId")
    List<TreinoEntity> findByProfessorIdWithAluno(@Param("professorId") UUID professorId);
}
