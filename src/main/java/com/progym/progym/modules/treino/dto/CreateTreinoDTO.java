package com.progym.progym.modules.treino.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTreinoDTO {
    
    @NotNull(message = "O título do treino é obrigatório")
    private String titulo;
    private String descricao;

    @NotNull(message = "O ID do aluno é obrigatório")
    private UUID alunoId; // ID do aluno associado ao treino

    private UUID professorId; // ID do professor associado ao treino (opcional)
}
