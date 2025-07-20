package com.progym.progym.modules.treino.usecases;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progym.progym.modules.treino.dto.TreinoProfessorResumoDTO;
import com.progym.progym.modules.treino.entity.TreinoEntity;
import com.progym.progym.modules.treino.repository.TreinoRepository;

@Service
public class ListarTreinoProfessorUseCase {
    
    @Autowired
    private TreinoRepository treinoRepository;

    public List<TreinoProfessorResumoDTO> execute(UUID professorId){

        List<TreinoEntity> treinos = treinoRepository.findByProfessorIdWithAluno(professorId);
        if (treinos.isEmpty()) {
            throw new RuntimeException("Nenhum treino encontrado para o professor com ID " + professorId);
        }

        return treinos.stream()
            .map(treino -> new TreinoProfessorResumoDTO(
                treino.getTitulo(),
                treino.getDescricao(),
                treino.getAluno().getNome()))
            .collect(Collectors.toList());

    }
}
