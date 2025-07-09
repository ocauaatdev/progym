package com.progym.progym.modules.treino.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progym.progym.UsuarioRole;
import com.progym.progym.modules.aluno.entity.AlunoEntity;
import com.progym.progym.modules.aluno.repository.AlunoRepository;
import com.progym.progym.modules.professor.entity.ProfessorEntity;
import com.progym.progym.modules.professor.repository.ProfessorRepository;
import com.progym.progym.modules.treino.dto.CreateTreinoDTO;
import com.progym.progym.modules.treino.entity.TreinoEntity;
import com.progym.progym.modules.treino.repository.TreinoRepository;

@Service
public class CreateTreinoUseCase {
    
    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public TreinoEntity execute(CreateTreinoDTO dto, UUID idUsuarioAutenticado, UsuarioRole roleUsuarioAutenticado){

        // Para criar um treino precisa haver um aluno associado
        // Nesse trecho ele verifica no banco de dados se o aluno existe e está ativo através do ID que foi passado no DTO (na requisição)
        AlunoEntity aluno = alunoRepository.findByIdAndAtivoTrue(dto.getAlunoId())
            .orElseThrow(()->{
                throw new RuntimeException("Aluno com ID " + dto.getAlunoId() + " não encontrado ou inativo.");
            });

        ProfessorEntity professor = null;

        // Se a role do usuario autenticado for Aluno, ele verifica se o id recebido no DTO é o mesmo do usuario autenticado
        if (UsuarioRole.ALUNO.equals(roleUsuarioAutenticado)) {
            if (!aluno.getId().equals(idUsuarioAutenticado)) {
                throw new RuntimeException("Aluno não pode criar um treino para outro aluno.");
            }
            if (dto.getProfessorId() != null) {
                throw new RuntimeException("O Aluno não pode associar um professor ao treino.");
            }
        } else if (UsuarioRole.PROFESSOR.equals(roleUsuarioAutenticado)){

            //Caso o id do professor tenha sido passado no DTO (na requisição),
            if (dto.getProfessorId() != null) {

            /*  Caso o treino seja prescrito por um professor (ou seja tenha um professorId no DTO),
                ele verifica no banco de dados se o professor existe e está ativo através do ID que foi passado no DTO (na requisição)*/

            professor = professorRepository.findByIdAndAtivoTrue(dto.getProfessorId())
                .orElseThrow(()->{
                    throw new RuntimeException("Professor com ID " + dto.getProfessorId() + " não encontrado ou inativo.");
                });

            // Se a role do usuario autenticado for Professor, ele verifica se o id recebido no DTO é o mesmo do usuario autenticado

            if (!professor.getId().equals(idUsuarioAutenticado)) {
                throw new RuntimeException("Professor não pode associar um treino a outro professor.");
            }
        }   
            /*  Se o usuário autenticado é um PROFESSOR e não forneceu professorId no DTO,
                assumimos que ele é o professor que está criando/prescrevendo o treino.*/
            else{
                professor = professorRepository.findByIdAndAtivoTrue(idUsuarioAutenticado)
                    .orElseThrow(()->{
                        throw new RuntimeException("Professor autenticado não encontrado ou inativo");
                    });
            }
        }
            TreinoEntity treino = new TreinoEntity();
            treino.setTitulo(dto.getTitulo());
            treino.setDescricao(dto.getDescricao());
            treino.setAluno(aluno);
            treino.setProfessor(professor);
            
            return treinoRepository.save(treino);
    }
}
