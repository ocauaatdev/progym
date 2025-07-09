package com.progym.progym.modules.treino.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progym.progym.modules.treino.dto.CreateExercicioDTO;
import com.progym.progym.modules.treino.entity.Exercicio;
import com.progym.progym.modules.treino.repository.ExercicioRepository;

@Service
public class CreateExercicioUseCase {
    
    @Autowired
    private ExercicioRepository exercicioRepository;

    public Exercicio execute(CreateExercicioDTO dto){

        this.exercicioRepository.findByNome(dto.getNome()).
        ifPresent((user) -> {
            throw new RuntimeException("Exercício já cadastrado.");
        });

        Exercicio exercicio = new Exercicio();
        exercicio.setNome(dto.getNome());
        exercicio.setDescricao(dto.getDescricao());
        exercicio.setGrupoMuscular(dto.getGrupoMuscular());
        exercicio.setEquipamento(dto.getEquipamento());

        return this.exercicioRepository.save(exercicio);
    }
}
