package com.progym.progym.modules.treino.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "treino_exercicios")
@AllArgsConstructor
@NoArgsConstructor
public class TreinoExercicios {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "treino_id", referencedColumnName = "id")
    private TreinoEntity treino;

    @ManyToOne
    @JoinColumn(name = "exercicio_id", referencedColumnName = "id")
    private Exercicio exercicio;

    private Double carga;
    private Integer series;
    private Integer repeticoes;
    private String observacoes;
}
