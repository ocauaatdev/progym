package com.progym.progym.modules.treino.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progym.progym.exceptions.IncorrectDoubleException;
import com.progym.progym.modules.treino.dto.CreateTreinoExerciciosDTO;
import com.progym.progym.modules.treino.entity.TreinoExercicios;
import com.progym.progym.modules.treino.repository.ExercicioRepository;
import com.progym.progym.modules.treino.repository.TreinoExerciciosRepository;
import com.progym.progym.modules.treino.repository.TreinoRepository;

@Service
public class CreateTreinoExerciciosUseCase {
    
    @Autowired
    private TreinoExerciciosRepository treinoExerciciosRepository;

    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private ExercicioRepository exercicioRepository;

    public TreinoExercicios execute(CreateTreinoExerciciosDTO dto){

        var treino = treinoRepository.findById(dto.getTreinoId())
            .orElseThrow(() -> {
                throw new RuntimeException("Treino com ID " + dto.getTreinoId() + " não encontrado.");
            });

        var exercicio = exercicioRepository.findById(dto.getExercicioId())
            .orElseThrow(()->{
                throw new RuntimeException("Exercício com ID " + dto.getExercicioId() + " não encontrado.");
            });

        TreinoExercicios treinoExercicios = new TreinoExercicios();
        treinoExercicios.setTreino(treino);
        treinoExercicios.setExercicio(exercicio);
        treinoExercicios.setSeries(dto.getSeries());
        treinoExercicios.setRepeticoes(dto.getRepeticoes());

        try {
            treinoExercicios.setCarga(Double.parseDouble(dto.getCarga().replace(",",".")));
        } catch (Exception e) {
            throw new IncorrectDoubleException();
        }

        treinoExercicios.setObservacoes(dto.getObservacoes());

        return treinoExerciciosRepository.save(treinoExercicios);
    }
}
