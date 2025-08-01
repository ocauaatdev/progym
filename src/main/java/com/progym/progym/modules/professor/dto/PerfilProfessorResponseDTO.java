package com.progym.progym.modules.professor.dto;

import java.util.UUID;

import com.progym.progym.Especialidades;
import com.progym.progym.UsuarioRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerfilProfessorResponseDTO {
    private UUID id;
    private String nome;
    private String username;
    private String cpf;
    private String email;
    private UsuarioRole role;
    private String cref;
    private String biografia;
    private Especialidades especialidade; 
    private Integer experiencia;
}
