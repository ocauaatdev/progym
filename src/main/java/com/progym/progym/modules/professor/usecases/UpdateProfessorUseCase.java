package com.progym.progym.modules.professor.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progym.progym.exceptions.SameInfoException;
import com.progym.progym.exceptions.UserFoundException;
import com.progym.progym.modules.professor.dto.ProfessorUpdateRequestDTO;
import com.progym.progym.modules.professor.entity.ProfessorEntity;
import com.progym.progym.modules.professor.repository.ProfessorRepository;

@Service
public class UpdateProfessorUseCase {
    
    @Autowired
    private ProfessorRepository professorRepository;

    public ProfessorEntity update(UUID idToUpdate, ProfessorUpdateRequestDTO dto, UUID professorAutenticado){

        if (!idToUpdate.equals(professorAutenticado)) {
            throw new RuntimeException("Acesso negado. Você não tem permissão para atualizar este perfil.");
        }

        var professorOptional = professorRepository.findByIdAndAtivoTrue(idToUpdate);

        if (professorOptional.isEmpty()) {
            throw new RuntimeException("Professor não encontrado.");
        }

        ProfessorEntity professorUpdate = professorOptional.get();

        if (dto.getNome() != null) { 
            professorUpdate.setNome(dto.getNome());
        }

        if (dto.getUsername() != null) {
            if (dto.getUsername().equals(professorUpdate.getUsername())) {
                throw new SameInfoException();
            }

            var usernameExistente = professorRepository.findByUsernameAndAtivoTrue(dto.getUsername());
            if (usernameExistente.isPresent()) {
                throw new UserFoundException();
            }
            professorUpdate.setUsername(dto.getUsername());
        }

        if(dto.getEmail() != null){
            if (dto.getEmail().equals(professorUpdate.getEmail())) {
                throw new SameInfoException();
            }

            var emailExistente = professorRepository.findByEmailAndAtivoTrue(dto.getEmail());
            if (emailExistente.isPresent()) {
                throw new UserFoundException();
            }
            professorUpdate.setEmail(dto.getEmail().toLowerCase());
        }

        if (dto.getCref() != null) { 
            professorUpdate.setCref(dto.getCref());
        }

        if (dto.getBiografia() != null) {
            professorUpdate.setBiografia(dto.getBiografia());
        }

        if (dto.getEspecialidade() != null) {
            professorUpdate.setEspecialidade(dto.getEspecialidade());
        }

        if (dto.getExperiencia() != null) {
            professorUpdate.setExperiencia(dto.getExperiencia());    
        }

        return professorRepository.save(professorUpdate);
    }
}
