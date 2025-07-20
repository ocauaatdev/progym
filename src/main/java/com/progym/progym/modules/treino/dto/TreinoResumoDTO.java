package com.progym.progym.modules.treino.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreinoResumoDTO {
    private String titulo;
    private String descricao;
    private String professorNome;
}
