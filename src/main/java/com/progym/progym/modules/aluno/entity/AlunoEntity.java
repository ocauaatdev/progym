package com.progym.progym.modules.aluno.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.progym.progym.Objetivos;
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

@Data
@Entity (name = "aluno")
@NoArgsConstructor
@AllArgsConstructor
public class AlunoEntity implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String nome;

    @NotBlank
    //@Pattern(regexp = "\\S+", message = "O campo username não pode conter espaços")
    private String username;

    @NotBlank
    @CPF(message = "CPF inválido")
    private String cpf;

    @Email(message = "Email inválido")
    private String email;

    @NotBlank
    private String senha;

    private Double altura;

    private Double peso;

    private Integer idade;

    private Boolean ativo = true;

    @Enumerated(EnumType.STRING)
    private UsuarioRole role;

    @CreationTimestamp
    private LocalDateTime dataCadastro;

    private LocalDateTime dataExclusao;

    @Enumerated(EnumType.STRING)
    private Objetivos objetivo;

    private String observacoes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }
}
