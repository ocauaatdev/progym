package com.progym.progym.modules.aluno.dto;

import lombok.Data;

@Data
public class AlunoUpdateRequestDTO {
    private String nome;
    private String username;
    private String email;
    private String peso;
    private String altura;
    private Integer idade;
}
