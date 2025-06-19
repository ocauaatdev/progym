package com.progym.progym.modules.aluno.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.progym.progym.modules.aluno.dto.PerfilAlunoResponseDTO;
import com.progym.progym.modules.aluno.repository.AlunoRepository;

@Service
public class PerfilAlunoUseCase {
    
    @Autowired
    private AlunoRepository alunoRepository;

    public PerfilAlunoResponseDTO execute(UUID alunoId){

        var aluno = this.alunoRepository.findById(alunoId)
        .orElseThrow(() -> {
            throw new UsernameNotFoundException("Aluno não encontrado");
        });

        var alunoDTO = PerfilAlunoResponseDTO.builder()
        .id(aluno.getId())
        .username(aluno.getUsername())
        .nome(aluno.getNome())
        .cpf(aluno.getCpf())
        .email(aluno.getEmail())
        .altura(aluno.getAltura())
        .peso(aluno.getPeso())
        .idade(aluno.getIdade())
        .role(aluno.getRole())
        .build();

        return alunoDTO;

    }
}
