package com.progym.progym.modules.professor.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progym.progym.modules.professor.dto.AuthProfessorRequestDTO;
import com.progym.progym.modules.professor.dto.CreateProfessorRequestDTO;
import com.progym.progym.modules.professor.dto.ProfessorUpdateRequestDTO;
import com.progym.progym.modules.professor.entity.ProfessorEntity;
import com.progym.progym.modules.professor.usecases.AuthProfessorUseCase;
import com.progym.progym.modules.professor.usecases.CreateProfessorUseCase;
import com.progym.progym.modules.professor.usecases.DeleteProfessorUseCase;
import com.progym.progym.modules.professor.usecases.PerfilProfessorUseCase;
import com.progym.progym.modules.professor.usecases.UpdateProfessorUseCase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private CreateProfessorUseCase createProfessorUseCase;

    @Autowired
    private AuthProfessorUseCase authProfessorUseCase;

    @Autowired
    private PerfilProfessorUseCase perfilProfessorUseCase;

    @Autowired
    private UpdateProfessorUseCase updateProfessorUseCase;

    @Autowired
    private DeleteProfessorUseCase deleteProfessorUseCase;
    
    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrarProfessor(@Valid @RequestBody CreateProfessorRequestDTO requestDTO){
        try {
            var result = this.createProfessorUseCase.execute(requestDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> authProfessor(@Valid @RequestBody AuthProfessorRequestDTO authProfessorRequestDTO){
        try {
            var result = this.authProfessorUseCase.execute(authProfessorRequestDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/perfil")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<Object> perfilProfessor(HttpServletRequest request){

        var professorId = request.getAttribute("professor_id");
        try {
            var perfil = this.perfilProfessorUseCase.execute(UUID.fromString(professorId.toString()));
            return ResponseEntity.ok().body(perfil);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody ProfessorUpdateRequestDTO dto, @AuthenticationPrincipal ProfessorEntity professorAutenticado){
        try {
            UUID professorId = professorAutenticado.getId();

            var result = this.updateProfessorUseCase.update(id, dto, professorId);
            return ResponseEntity.ok().body(result);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<Object> delete(@PathVariable UUID id, @AuthenticationPrincipal ProfessorEntity professorAutenticado){
        try {
            UUID professorId = professorAutenticado.getId();
            this.deleteProfessorUseCase.delete(id, professorId);
            return ResponseEntity.ok().build();

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
