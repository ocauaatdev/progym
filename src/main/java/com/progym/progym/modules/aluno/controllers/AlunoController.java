package com.progym.progym.modules.aluno.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progym.progym.modules.aluno.dto.AlunoRequestDTO;
import com.progym.progym.modules.aluno.dto.AlunoUpdateRequestDTO;
import com.progym.progym.modules.aluno.dto.AuthAlunoRequestDTO;
import com.progym.progym.modules.aluno.entity.AlunoEntity;
import com.progym.progym.modules.aluno.usecases.AuthAlunoUseCase;
import com.progym.progym.modules.aluno.usecases.CreateAluno;
import com.progym.progym.modules.aluno.usecases.DeleteAlunoUseCase;
import com.progym.progym.modules.aluno.usecases.PerfilAlunoUseCase;
import com.progym.progym.modules.aluno.usecases.UpdateAlunoUseCase;
import com.progym.progym.modules.treino.dto.TreinoResumoDTO;
import com.progym.progym.modules.treino.usecases.ListarTreinoAlunoUseCase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.var;



@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/aluno")
public class AlunoController {
    
    @Autowired
    private CreateAluno createAluno;

    @Autowired
    private AuthAlunoUseCase authAlunoUseCase;

    @Autowired
    private PerfilAlunoUseCase perfilAlunoUseCase;

    @Autowired
    private UpdateAlunoUseCase updateAlunoUseCase;

    @Autowired
    private DeleteAlunoUseCase deleteAlunoUseCase;

    @Autowired
    private ListarTreinoAlunoUseCase listarTreinoAlunoUseCase;

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrarAluno(@Valid @RequestBody AlunoRequestDTO alunoRequestDTO){
        try {
          //  AlunoEntity alunoEntity= alunoRequestDTO.toEntity();
            var result = this.createAluno.execute(alunoRequestDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {           
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> authAluno(@RequestBody AuthAlunoRequestDTO authAlunoRequestDTO){
        try {
            var token = this.authAlunoUseCase.execute(authAlunoRequestDTO);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/perfil")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<Object> perfilAluno(HttpServletRequest request){

        var alunoId = request.getAttribute("aluno_id");
        try {
            var perfil = this.perfilAlunoUseCase.execute(UUID.fromString(alunoId.toString()));
            return ResponseEntity.ok().body(perfil);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody AlunoUpdateRequestDTO alunoUpdateDto, @AuthenticationPrincipal AlunoEntity alunoAutenticado){
        try {
            UUID idAlunoAutenticado = alunoAutenticado.getId();

            var result = this.updateAlunoUseCase.update(id, alunoUpdateDto, idAlunoAutenticado);
            return ResponseEntity.ok().body(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<String> delete(@PathVariable UUID id, @AuthenticationPrincipal AlunoEntity alunoAutenticado){
        try {
            UUID idAlunoAutenticado = alunoAutenticado.getId();
            this.deleteAlunoUseCase.delete(id, idAlunoAutenticado);
            return ResponseEntity.ok().build();

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/treinos")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<List<TreinoResumoDTO>> listarMeusTreinos(@AuthenticationPrincipal AlunoEntity alunoAutenticado){
        try {
            UUID alunoId = alunoAutenticado.getId();

            List<TreinoResumoDTO> treinos = this.listarTreinoAlunoUseCase.execute(alunoId);
            return ResponseEntity.ok().body(treinos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
}
