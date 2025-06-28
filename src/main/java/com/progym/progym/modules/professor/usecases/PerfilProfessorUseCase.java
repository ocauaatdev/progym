package com.progym.progym.modules.professor.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.progym.progym.modules.professor.dto.PerfilProfessorResponseDTO;
import com.progym.progym.modules.professor.repository.ProfessorRepository;

@Service
public class PerfilProfessorUseCase {
    
    @Autowired
    private ProfessorRepository professorRepository;

    public PerfilProfessorResponseDTO execute(UUID professorId){

        var professor = this.professorRepository.findByIdAndAtivoTrue(professorId)
            .orElseThrow(()-> {
                throw new UsernameNotFoundException("Professor não encontrado");
        });

        var professorDTO = PerfilProfessorResponseDTO.builder()
            .id(professor.getId())
            .username(professor.getUsername())
            .nome(professor.getNome())
            .cpf(professor.getCpf())
            .email(professor.getEmail())
            .reputacao(professor.getReputacao())
            .role(professor.getRole())
            .build();

            return professorDTO;
    }
}
