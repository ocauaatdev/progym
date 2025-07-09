package com.progym.progym.modules.treino.entity;

import java.util.UUID;

import com.progym.progym.modules.aluno.entity.AlunoEntity;
import com.progym.progym.modules.professor.entity.ProfessorEntity;

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
@Entity(name = "treino")
@NoArgsConstructor
@AllArgsConstructor
public class TreinoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String titulo;

    private String descricao;

    @ManyToOne // Um treino pertence a um aluno
    @JoinColumn(name="aluno_id", referencedColumnName = "id", nullable = false)
    private AlunoEntity aluno;

    @ManyToOne
    @JoinColumn(name = "professor_id", referencedColumnName = "id", nullable = true)
    private ProfessorEntity professor;
}
