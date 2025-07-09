package com.progym.progym.modules.treino.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTreinoExerciciosDTO {
    
    @NotNull(message = "O ID do treino é obrigatório")
    private UUID treinoId;

    @NotNull(message = "O ID do exercício é obrigatório")
    private UUID exercicioId;

    private Integer series;
    private Integer repeticoes;
    private String carga;
    private String observacoes;
}
