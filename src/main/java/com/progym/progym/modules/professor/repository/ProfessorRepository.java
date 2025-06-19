package com.progym.progym.modules.professor.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progym.progym.modules.professor.entity.ProfessorEntity;

public interface ProfessorRepository extends JpaRepository<ProfessorEntity, UUID> {
    
    Optional<ProfessorEntity> findByUsernameOrEmailOrCpf(String username, String email, String cpf);
    Optional<ProfessorEntity> findByUsername(String username);
}
