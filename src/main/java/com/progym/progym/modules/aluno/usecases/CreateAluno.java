package com.progym.progym.modules.aluno.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.progym.progym.UsuarioRole;
import com.progym.progym.exceptions.IncorrectDoubleException;
import com.progym.progym.exceptions.UserFoundException;
import com.progym.progym.modules.aluno.dto.AlunoRequestDTO;
import com.progym.progym.modules.aluno.entity.AlunoEntity;
import com.progym.progym.modules.aluno.repository.AlunoRepository;

@Service
public class CreateAluno {
    
    @Autowired
    private AlunoRepository alunoRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public AlunoEntity execute(AlunoRequestDTO dto){

        this.alunoRepository.findByUsernameOrEmailOrCpfAndAtivoTrue(dto.getUsername(), dto.getEmail(), dto.getCpf()).
        ifPresent((user) -> {
            throw new UserFoundException();
        });

        AlunoEntity alunoEntity = new AlunoEntity();

        alunoEntity.setNome(dto.getNome());
        alunoEntity.setUsername(dto.getUsername());
        alunoEntity.setCpf(dto.getCpf());
        alunoEntity.setEmail(dto.getEmail().toLowerCase());
        alunoEntity.setSenha(passwordEncoder.encode(dto.getSenha()));

        try {
            alunoEntity.setAltura(Double.parseDouble(dto.getAltura().replace(",","."))); 
        } catch (Exception e) {
            throw new IncorrectDoubleException(); 
        }
       
        try {
            alunoEntity.setPeso(Double.parseDouble(dto.getPeso().replace(",",".")));
        } catch (Exception e) {
            throw new IncorrectDoubleException();
        }
        
        alunoEntity.setIdade(dto.getIdade());
        alunoEntity.setRole(UsuarioRole.ALUNO);
        

       return this.alunoRepository.save(alunoEntity);
    }
}