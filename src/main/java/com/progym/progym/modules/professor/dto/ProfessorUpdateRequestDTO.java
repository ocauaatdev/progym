package com.progym.progym.modules.professor.dto;

import com.progym.progym.Especialidades;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProfessorUpdateRequestDTO {
    private String nome;
    private String username;
    private String email;

    @Pattern(regexp = "\\d{4,6}-[A-Z]/[A-Z]{2}", message = "Formato de CREF inválido. Ex: 123456-G/SP")
    private String cref;
    private String biografia;

    @Enumerated(EnumType.STRING)
    private Especialidades especialidade; 

    private Integer experiencia;
}
