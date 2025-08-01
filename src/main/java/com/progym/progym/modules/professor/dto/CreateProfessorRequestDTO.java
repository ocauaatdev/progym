package com.progym.progym.modules.professor.dto;

import org.hibernate.validator.constraints.br.CPF;

import com.progym.progym.Especialidades;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateProfessorRequestDTO {
    
    @NotBlank
    @Pattern(regexp = "\\S+", message = "O campo username não pode conter espaços")
    private String username;

    @NotBlank
    private String nome;

    @NotBlank
    @CPF(message = "CPF inválido")
    private String cpf;

    @Email(message = "Email inválido")
    @NotBlank
    private String email;

    @Pattern(regexp = "\\d{4,6}-[A-Z]/[A-Z]{2}", message = "Formato de CREF inválido. Ex: 123456-G/SP")
    private String cref;

    private String biografia;

    @Enumerated(EnumType.STRING)
    private Especialidades especialidade;

    private Integer experiencia;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "A senha deve conter pelo menos uma letra maiúscula, um número e um caractere especial")
    private String senha;
}
