package com.progym.progym.modules.aluno.usecases;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progym.progym.modules.aluno.entity.AlunoEntity;
import com.progym.progym.modules.aluno.repository.AlunoRepository;

@Service
public class DeleteAlunoUseCase {
    
    @Autowired
    private AlunoRepository alunoRepository;

    public void delete(UUID idToDelete, UUID alunoAutenticado){

        if (!idToDelete.equals(alunoAutenticado)) {
            throw new RuntimeException("Acesso negado. Você não tem permissão para deletar este perfil.");
        }

        var alunoOptional = alunoRepository.findByIdAndAtivoTrue(idToDelete);

        if (alunoOptional.isEmpty()) {
            throw new RuntimeException("Aluno não encontrado.");
        }

        AlunoEntity aluno = alunoOptional.get();
        aluno.setAtivo(false);
        aluno.setDataExclusao(LocalDateTime.now());
        alunoRepository.save(aluno);
    }
}
