package com.progym.progym.modules.professor.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.progym.progym.UsuarioRole;
import com.progym.progym.exceptions.UserFoundException;
import com.progym.progym.modules.professor.dto.CreateProfessorRequestDTO;
import com.progym.progym.modules.professor.entity.ProfessorEntity;
import com.progym.progym.modules.professor.repository.ProfessorRepository;

@Service
public class CreateProfessorUseCase {
    
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ProfessorEntity execute(CreateProfessorRequestDTO dto){

        this.professorRepository.findByUsernameOrEmailOrCpfAndAtivoTrue(dto.getUsername(), dto.getEmail(), dto.getCpf())
        .ifPresent(user -> {
            throw new UserFoundException();
        });

        ProfessorEntity professorEntity = new ProfessorEntity();
        professorEntity.setUsername(dto.getUsername());
        professorEntity.setNome(dto.getNome());
        professorEntity.setCpf(dto.getCpf());
        professorEntity.setEmail(dto.getEmail().toLowerCase());
        professorEntity.setBiografia(dto.getBiografia());
        professorEntity.setEspecialidade(dto.getEspecialidade());
        professorEntity.setCref(dto.getCref());
        professorEntity.setExperiencia(dto.getExperiencia());
        professorEntity.setSenha(passwordEncoder.encode(dto.getSenha()));
        professorEntity.setRole(UsuarioRole.PROFESSOR);

        return this.professorRepository.save(professorEntity);
    }
}
