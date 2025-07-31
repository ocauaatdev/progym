package com.progym.progym.modules.treino.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciciosDetalhesResponseDTO {
    
    private String nomeExercicio;
    private String descricaoExercicio;
    private String grupoMuscular;
    private String equipamento;

    private Double carga;
    private Integer series;
    private Integer repeticoes;
    private String observacoes;
}
