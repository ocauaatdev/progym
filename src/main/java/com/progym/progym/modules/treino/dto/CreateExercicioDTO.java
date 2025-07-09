package com.progym.progym.modules.treino.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateExercicioDTO {
    @NotNull(message = "O nome do exercício é obrigatório.")
    private String nome;

    @NotNull(message = "A descrição do exercício é obrigatória.")
    private String descricao;

    @NotNull(message = "O grupo muscular do exercício é obrigatório.")
    private String grupoMuscular;

    @NotNull(message = "O equipamento do exercício é obrigatório.")
    private String equipamento;
}
