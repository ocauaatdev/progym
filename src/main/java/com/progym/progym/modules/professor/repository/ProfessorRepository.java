package com.progym.progym.modules.professor.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.progym.progym.modules.professor.entity.ProfessorEntity;

public interface ProfessorRepository extends JpaRepository<ProfessorEntity, UUID> {
    
    @Query("SELECT a FROM professor a WHERE (a.username = :username OR a.email = :email OR a.cpf = :cpf) AND a.ativo = true")
    Optional<ProfessorEntity> findByUsernameOrEmailOrCpfAndAtivoTrue(String username, String email, String cpf);

    Optional<ProfessorEntity> findByUsernameAndAtivoTrue(String username);
    Optional<ProfessorEntity> findByIdAndAtivoTrue(UUID id);
    Optional<ProfessorEntity> findByEmailAndAtivoTrue(String email);
}
