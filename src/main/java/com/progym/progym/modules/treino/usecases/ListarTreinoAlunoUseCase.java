package com.progym.progym.modules.treino.usecases;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progym.progym.modules.treino.dto.TreinoResumoDTO;
import com.progym.progym.modules.treino.entity.TreinoEntity;
import com.progym.progym.modules.treino.repository.TreinoRepository;

@Service
public class ListarTreinoAlunoUseCase {
    
    @Autowired
    private TreinoRepository treinoRepository;

    public List<TreinoResumoDTO> execute(UUID alunoId){

        List<TreinoEntity> treinos = treinoRepository.findByAlunoId(alunoId);
        if (treinos.isEmpty()) {
            throw new RuntimeException("Nenhum treino encontrado para o aluno com ID " + alunoId);
        }

        return treinos.stream()
            .map(treino -> new TreinoResumoDTO(
            treino.getTitulo(), 
            treino.getDescricao(),
            treino.getProfessor() != null ? treino.getProfessor().getNome() : "Professor não atribuido"
            ))
            .collect(Collectors.toList());
    }
}
