package com.progym.progym.modules.treino.usecases;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progym.progym.UsuarioRole;
import com.progym.progym.modules.treino.dto.ExerciciosDetalhesResponseDTO;
import com.progym.progym.modules.treino.dto.TreinoDetalhesResponseDTO;
import com.progym.progym.modules.treino.entity.TreinoEntity;
import com.progym.progym.modules.treino.entity.TreinoExercicios;
import com.progym.progym.modules.treino.repository.TreinoExerciciosRepository;
import com.progym.progym.modules.treino.repository.TreinoRepository;

@Service
public class GetTreinoDetalhesUseCase {
    
    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private TreinoExerciciosRepository treinoExerciciosRepository;

    public TreinoDetalhesResponseDTO execute(UUID treinoId, UUID idUsuarioAutenticado, UsuarioRole roleUsuarioAutenticado){

        TreinoEntity treino = treinoRepository.findById(treinoId)
            .orElseThrow(() -> new RuntimeException("Treino com ID " + treinoId + " não encontrado."));

        validarAcesso(treino, idUsuarioAutenticado, roleUsuarioAutenticado);

        List<TreinoExercicios> treinoExercicios = treinoExerciciosRepository.findByTreinoIdWithExerciciosAndTreino(treinoId);

        List<ExerciciosDetalhesResponseDTO> exerciciosDetalhes = treinoExercicios.stream()
            .map(te -> new ExerciciosDetalhesResponseDTO(
                te.getExercicio().getNome(),
                te.getExercicio().getDescricao(),
                te.getExercicio().getGrupoMuscular(),
                te.getExercicio().getEquipamento(),
                te.getCarga(),
                te.getSeries(),
                te.getRepeticoes(),
                te.getObservacoes()
            ))
            .collect(Collectors.toList());

        return new TreinoDetalhesResponseDTO(
            treino.getTitulo(),
            treino.getDescricao(),
            treino.getProfessor() != null ? treino.getProfessor().getNome() : null,
            treino.getAluno().getNome(),
            exerciciosDetalhes
        );
    }

    private void validarAcesso(TreinoEntity treino, UUID idUsuarioAutenticado, UsuarioRole roleUsuarioAutenticado){

        if(roleUsuarioAutenticado.equals(UsuarioRole.ALUNO)){
            if (!treino.getAluno().getId().equals(idUsuarioAutenticado)) {
                throw new RuntimeException("Aluno não tem acesso a este treino.");
            }
        }
        else if(roleUsuarioAutenticado.equals(UsuarioRole.PROFESSOR)){
            if (treino.getProfessor() == null || !treino.getProfessor().getId().equals(idUsuarioAutenticado)) {
                throw new RuntimeException("Professor não tem acesso a este treino.");
            }
        }
        else{
            throw new RuntimeException("Usuário não autorizado a acessar os detalhes do treino.");
        }
    }
}
