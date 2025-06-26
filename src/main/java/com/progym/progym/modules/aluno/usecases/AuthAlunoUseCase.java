package com.progym.progym.modules.aluno.usecases;

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
import com.progym.progym.modules.aluno.dto.AuthAlunoRequestDTO;
import com.progym.progym.modules.aluno.dto.AuthAlunoResponseDTO;
import com.progym.progym.modules.aluno.repository.AlunoRepository;

@Service
public class AuthAlunoUseCase {
    

    @Value("${security.token.secret.aluno}")
    private String secretKey;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthAlunoResponseDTO execute(AuthAlunoRequestDTO authAlunoRequestDTO) throws AuthenticationException{

        var aluno = this.alunoRepository.findByUsernameAndAtivoTrue(authAlunoRequestDTO.username())
        .orElseThrow(() -> {
            throw new UsernameNotFoundException("Username/Password incorretos");
        });

        var senhasIguais = this.passwordEncoder.matches(authAlunoRequestDTO.password(), aluno.getSenha());

        if (!senhasIguais) {
            throw new AuthenticationException("Username/Password incorretos");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
        
        var token = JWT.create() 
            .withIssuer("ProGym")
            .withSubject(aluno.getId().toString())
            .withClaim("role", Arrays.asList("ALUNO"))
            .withExpiresAt(expiresIn)
            .sign(algorithm);

        var authAlunoResponseDTO = AuthAlunoResponseDTO.builder().acess_token(token).expires_in(expiresIn.toEpochMilli())
        .build();

        return authAlunoResponseDTO;

    }
}
