package com.progym.progym.modules.aluno.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progym.progym.exceptions.IncorrectDoubleException;
import com.progym.progym.exceptions.SameInfoException;
import com.progym.progym.exceptions.UserFoundException;
import com.progym.progym.modules.aluno.dto.AlunoUpdateRequestDTO;
import com.progym.progym.modules.aluno.entity.AlunoEntity;
import com.progym.progym.modules.aluno.repository.AlunoRepository;

@Service
public class UpdateAlunoUseCase {
    
    @Autowired
    private AlunoRepository alunoRepository;

    public AlunoEntity update(UUID idToUpdate, AlunoUpdateRequestDTO dto, UUID alunoAutenticado){
        
        if (!idToUpdate.equals(alunoAutenticado)) {
            throw new RuntimeException("Acesso negado. Você não tem permissão para atualizar este perfil.");
        }

        var alunoOptional = alunoRepository.findById(idToUpdate); //ele pega o aluno pelo id que está sendo atualizado. O findById vai encontrar o aluno no banco de dados com o mesmo id que foi passado no parâmetro.

        if (alunoOptional.isEmpty()) {
            throw new RuntimeException("Aluno não encontrado.");
        }

        AlunoEntity alunoUpdate = alunoOptional.get();

        if (dto.getNome() != null) {
            alunoUpdate.setNome(dto.getNome());
        }

        if (dto.getUsername() != null) {
            if (dto.getUsername().equals(alunoUpdate.getUsername())) {
                throw new SameInfoException();
            }

            var usernameExistente = alunoRepository.findByUsernameAndAtivoTrue(dto.getUsername());
            if (usernameExistente.isPresent()) {
                throw new UserFoundException();
            }
            alunoUpdate.setUsername(dto.getUsername());
        }

        if (dto.getEmail() != null) {

            if (dto.getEmail().equals(alunoUpdate.getEmail())) {
                throw new SameInfoException();
            }

            var emailExistente = alunoRepository.findByEmailAndAtivoTrue(dto.getEmail());
            if (emailExistente.isPresent()) {
                throw new UserFoundException();
            }

            alunoUpdate.setEmail(dto.getEmail().toLowerCase());
        }

        try {
            if (dto.getAltura() != null) {
                alunoUpdate.setAltura(Double.parseDouble(dto.getAltura().replace(",",".")));
            }
        } catch (Exception e) {
            throw new IncorrectDoubleException(); 
        }

        try {
            if (dto.getPeso() != null) {
                alunoUpdate.setPeso(Double.parseDouble(dto.getPeso().replace(",", ".")));
            }
        } catch (Exception e) {
            throw new IncorrectDoubleException();
        }

        if (dto.getIdade() != null) {
            alunoUpdate.setIdade(dto.getIdade());
        }

        return alunoRepository.save(alunoUpdate);

    }
}
