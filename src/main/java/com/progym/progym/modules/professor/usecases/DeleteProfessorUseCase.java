package com.progym.progym.modules.professor.usecases;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progym.progym.modules.professor.entity.ProfessorEntity;
import com.progym.progym.modules.professor.repository.ProfessorRepository;

@Service
public class DeleteProfessorUseCase {
    
    @Autowired
    private ProfessorRepository professorRepository;

    public void delete(UUID idToDelete, UUID professorAutenticado){

        if (!idToDelete.equals(professorAutenticado)) {
            throw new RuntimeException("Acesso negado. Você não tem permissão para deletar este perfil.");
        }

        var professorOptional = professorRepository.findByIdAndAtivoTrue(idToDelete);

        if (professorOptional.isEmpty()) {
            throw new RuntimeException("Professor não encontrado.");
        }

        ProfessorEntity professor = professorOptional.get();
        professor.setAtivo(false);
        professor.setDataExclusao(LocalDateTime.now());
        professorRepository.save(professor);
    }
}
