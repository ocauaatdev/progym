package com.progym.progym.modules.treino.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreinoDetalhesResponseDTO {
    
    private String titulo;
    private String descricao;
    private String professorNome;
    private String alunoNome;

    private List<ExerciciosDetalhesResponseDTO> exercicios;
}
