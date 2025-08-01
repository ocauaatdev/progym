package com.progym.progym.modules.professor.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.progym.progym.Especialidades;
import com.progym.progym.UsuarioRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "professor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorEntity implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String username;

    @NotBlank
    private String nome;

    @NotBlank
    @CPF(message = "CPF inválido")
    private String cpf;

    @Email(message = "Email inválido")
    @NotBlank
    private String email;

    @NotBlank
    private String senha;

    @Enumerated(EnumType.STRING)
    private UsuarioRole role;

    @CreationTimestamp
    private LocalDateTime dataCadastro;

    private LocalDateTime dataExclusao;

    private Boolean ativo = true;

    private String cref;

    private String biografia;

    @Enumerated(EnumType.STRING)
    private Especialidades especialidade;

    private Integer experiencia;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getPassword() {
       return this.senha;
    }

    @Override
    public String getUsername() {
        return this.username;
    }


}
