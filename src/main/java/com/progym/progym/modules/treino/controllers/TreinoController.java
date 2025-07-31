package com.progym.progym.modules.treino.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progym.progym.UsuarioRole;
import com.progym.progym.modules.aluno.entity.AlunoEntity;
import com.progym.progym.modules.professor.entity.ProfessorEntity;
import com.progym.progym.modules.treino.dto.CreateExercicioDTO;
import com.progym.progym.modules.treino.dto.CreateTreinoDTO;
import com.progym.progym.modules.treino.dto.CreateTreinoExerciciosDTO;
import com.progym.progym.modules.treino.dto.TreinoDetalhesResponseDTO;
import com.progym.progym.modules.treino.usecases.CreateExercicioUseCase;
import com.progym.progym.modules.treino.usecases.CreateTreinoExerciciosUseCase;
import com.progym.progym.modules.treino.usecases.CreateTreinoUseCase;
import com.progym.progym.modules.treino.usecases.GetTreinoDetalhesUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/treinos")
public class TreinoController {
    
    @Autowired
    private CreateTreinoUseCase createTreinoUseCase;

    @Autowired
    private CreateExercicioUseCase createExercicioUseCase;

    @Autowired
    private CreateTreinoExerciciosUseCase createTreinoExerciciosUseCase;

    @Autowired
    private GetTreinoDetalhesUseCase getTreinoDetalhesUseCase;

    @PostMapping
    @PreAuthorize("hasAnyRole('ALUNO','PROFESSOR')")
    public ResponseEntity<Object> createTreino(@RequestBody @Valid CreateTreinoDTO dto, @AuthenticationPrincipal Object usuarioAutenticado){
        UUID idUsuarioAutenticado;
        UsuarioRole roleUsuarioAutenticado;

        if (usuarioAutenticado instanceof AlunoEntity) {
            idUsuarioAutenticado = ((AlunoEntity)usuarioAutenticado).getId();
            roleUsuarioAutenticado = UsuarioRole.ALUNO;
        }
        else if (usuarioAutenticado instanceof ProfessorEntity){
            idUsuarioAutenticado = ((ProfessorEntity)usuarioAutenticado).getId();
            roleUsuarioAutenticado = UsuarioRole.PROFESSOR;
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            var result = this.createTreinoUseCase.execute(dto, idUsuarioAutenticado, roleUsuarioAutenticado);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createExercicio")
    public ResponseEntity<Object> createExercicio(@RequestBody @Valid CreateExercicioDTO dto){
        try {
            var result = this.createExercicioUseCase.execute(dto);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/adicionarExercicio")
    @PreAuthorize("hasAnyRole('ALUNO','PROFESSOR')")
    public ResponseEntity<Object> adicionarExercicio(@RequestBody @Valid CreateTreinoExerciciosDTO dto){
        try {
            var result = this.createTreinoExerciciosUseCase.execute(dto);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{treinoId}/detalhes")
    @PreAuthorize("hasAnyRole('ALUNO','PROFESSOR')")
    public ResponseEntity<TreinoDetalhesResponseDTO> getDetalhesTreino(
        @PathVariable UUID treinoId, 
        @AuthenticationPrincipal Object usuarioAutenticado){

        UUID idUsuarioAutenticado;
        UsuarioRole roleUsuarioAutenticado;

        if (usuarioAutenticado instanceof AlunoEntity) {
            idUsuarioAutenticado = ((AlunoEntity)usuarioAutenticado).getId();
            roleUsuarioAutenticado = UsuarioRole.ALUNO;
        }
        else if (usuarioAutenticado instanceof ProfessorEntity){
            idUsuarioAutenticado = ((ProfessorEntity)usuarioAutenticado).getId();
            roleUsuarioAutenticado = UsuarioRole.PROFESSOR;
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            TreinoDetalhesResponseDTO treinoDetalhes = getTreinoDetalhesUseCase.execute(treinoId, idUsuarioAutenticado, roleUsuarioAutenticado);
            return ResponseEntity.ok().body(treinoDetalhes);
        } catch (Exception e) {
            if (e.getMessage().contains("não encontrado")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else if (e.getMessage().contains("Acesso negado")) {
            // Qualquer exceção que comece com "Acesso negado" será tratada como 403
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
