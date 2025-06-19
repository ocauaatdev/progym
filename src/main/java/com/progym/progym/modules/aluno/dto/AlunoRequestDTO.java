package com.progym.progym.modules.aluno.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AlunoRequestDTO {
    @NotBlank(message = "O campo nome não pode estar vazio")
    private String nome;

    @NotBlank
    @Pattern(regexp = "\\S+", message = "O campo username não pode conter espaços")
    private String username;

    @NotBlank
    @CPF(message = "CPF inválido")
    private String cpf;

    @Email(message = "Email inválido")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "A senha deve conter pelo menos uma letra maiúscula, um número e um caractere especial")
    private String senha;


    private String altura;
    private String peso;
    private Integer idade;

}
