package com.progym.progym.modules.professor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progym.progym.modules.professor.dto.AuthProfessorRequestDTO;
import com.progym.progym.modules.professor.dto.CreateProfessorRequestDTO;
import com.progym.progym.modules.professor.usecases.AuthProfessorUseCase;
import com.progym.progym.modules.professor.usecases.CreateProfessorUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private CreateProfessorUseCase createProfessorUseCase;

    @Autowired
    private AuthProfessorUseCase authProfessorUseCase;
    
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
}
