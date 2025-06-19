package com.progym.progym.modules.aluno.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progym.progym.modules.aluno.entity.AlunoEntity;

public interface AlunoRepository extends JpaRepository<AlunoEntity, UUID> {
    
    Optional<AlunoEntity> findByUsernameOrEmail(String username, String email);
    Optional<AlunoEntity> findByUsernameOrEmailOrCpf(String username, String email, String cpf);
    Optional<AlunoEntity> findByUsername(String username);

    Optional<AlunoEntity> findByUsernameAndIdNot(String username, UUID id);
    Optional<AlunoEntity> findByEmailAndIdNot(String email, UUID id);
}
