package com.progym.progym.modules.professor.usecases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.progym.progym.modules.professor.dto.AuthProfessorRequestDTO;
import com.progym.progym.modules.professor.dto.AuthProfessorResponseDTO;
import com.progym.progym.modules.professor.repository.ProfessorRepository;



@Service
public class AuthProfessorUseCase {

    @Value("${security.token.secret.professor}")
    private String secretKey;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthProfessorResponseDTO execute(AuthProfessorRequestDTO requestDTO) throws AuthenticationException{

        var professor = this.professorRepository.findByUsername(requestDTO.username())
        .orElseThrow(() -> {
            throw new UsernameNotFoundException("Username/Password incorretos");
        });

        var senhasIguais = this.passwordEncoder.matches(requestDTO.password(), professor.getSenha());
        if (!senhasIguais) {
            throw new AuthenticationException("Username/Password incorretos");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));

        var token = JWT.create()
            .withIssuer("ProGym")
            .withSubject(professor.getId().toString())
            .withClaim("role",Arrays.asList("PROFESSOR"))
            .withExpiresAt(expiresIn)
            .sign(algorithm);

        var authProfessorResponse = AuthProfessorResponseDTO.builder().acess_token(token).expires_in(expiresIn.toEpochMilli())
        .build();

        return authProfessorResponse;
    }

}
