package com.progym.progym.modules.aluno.dto;

import java.util.UUID;

import com.progym.progym.UsuarioRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerfilAlunoResponseDTO {
    
    private UUID id;
    private String nome;
    private String username;
    private String cpf;
    private String email;
    private Double altura;
    private Double peso;
    private Integer idade;
    private UsuarioRole role;
}
