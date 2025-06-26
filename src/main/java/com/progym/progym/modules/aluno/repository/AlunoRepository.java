package com.progym.progym.modules.aluno.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.progym.progym.modules.aluno.entity.AlunoEntity;

public interface AlunoRepository extends JpaRepository<AlunoEntity, UUID> {
    
    @Query("SELECT a FROM aluno a WHERE (a.username = :username OR a.email = :email) AND a.ativo = true")
    Optional<AlunoEntity> findByUsernameOrEmailAndAtivoTrue(String username, String email);

    @Query("SELECT a FROM aluno a WHERE (a.username = :username OR a.email = :email OR a.cpf = :cpf) AND a.ativo = true") // Necessário implementar a consulta, pois o método findByUsernameOrEmailOrCpfAndAtivoTrue não é suportado diretamente pelo Spring Data JPA
    Optional<AlunoEntity> findByUsernameOrEmailOrCpfAndAtivoTrue(String username, String email, String cpf);
    
    Optional<AlunoEntity> findByUsernameAndAtivoTrue(String username);

    Optional<AlunoEntity> findByUsernameAndIdNotAndAtivoTrue(String username, UUID id);
    Optional<AlunoEntity> findByEmailAndIdNotAndAtivoTrue(String email, UUID id);
    Optional<AlunoEntity> findByIdAndAtivoTrue(UUID id);
}
