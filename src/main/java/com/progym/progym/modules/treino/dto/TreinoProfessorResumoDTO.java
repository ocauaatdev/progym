package com.progym.progym.modules.treino.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreinoProfessorResumoDTO {
    String titulo;
    String descricao;
    String alunoNome;
}
