package com.progym.progym.security;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.progym.progym.modules.aluno.entity.AlunoEntity;
import com.progym.progym.modules.aluno.repository.AlunoRepository;
import com.progym.progym.modules.professor.entity.ProfessorEntity;
import com.progym.progym.modules.professor.repository.ProfessorRepository;
import com.progym.progym.providers.JWTAlunoProvider;
import com.progym.progym.providers.JWTProfessorProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
    
    @Autowired
    private JWTProfessorProvider jwtProfessorProvider;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private JWTAlunoProvider jwtAlunoProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");
        DecodedJWT decodedToken = null;
        String userRole = null;

        try {
            decodedToken = JWT.decode(token);
            List<String> roles = decodedToken.getClaim("role").asList(String.class);
            if(roles != null && !roles.isEmpty()){
                userRole = roles.get(0).toUpperCase();
            }
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch(Exception e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Se decodificou, agora tenta VALIDAR o token com o provedor correto baseado na role.
        if (userRole != null) {
            DecodedJWT validatedToken = null;
            try {
                if ("ALUNO".equals(userRole)) {
                    validatedToken = jwtAlunoProvider.validateToken(header); 
                    System.out.println("DEBUG: Tentando validar como ALUNO...");
                } else if ("PROFESSOR".equals(userRole)) {
                    validatedToken = jwtProfessorProvider.validateToken(header); 
                    System.out.println("DEBUG: Tentando validar como PROFESSOR...");
                }

                if (validatedToken != null) {
                    
                    String userIdString = validatedToken.getSubject(); 
                    UUID userId = UUID.fromString(userIdString);
                    
                    // Define um atributo na requisição com o ID do usuário. Isso pode ser útil para controladores que precisam do ID do usuário autenticado.
                    //Agora opcional, ja que trabalharemos com o Principal
                    if ("ALUNO".equals(userRole)) {
                        request.setAttribute("aluno_id", userId);
                    } else if ("PROFESSOR".equals(userRole)) {
                        request.setAttribute("professor_id", userId);
                    }

                    var claimsRoles = validatedToken.getClaim("role").asList(Object.class); //pega as roles do token validado
                    var authorities = claimsRoles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())) 
                        .toList(); 

                    if ("ALUNO".equals(userRole)) {
                        AlunoEntity aluno = alunoRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("Aluno não encontrado para o id do token " + userId));

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(aluno, null, authorities);

                        //"authentication" define o objeto de autenticação no SecurityContextHolder, informando ao Spring Security que o usuário está autenticado e quais são suas permissões.
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        System.out.println("DEBUG: Autenticação BEM-SUCEDIDA para ALUNO (com AlunoEntity como Principal): " + userId);

                    }else if("PROFESSOR".equals(userRole)){
                        ProfessorEntity professor = professorRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("Professor não encontrado para o id do token " + userId));

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(professor, null, authorities);

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        System.out.println("DEBUG: Autenticação BEM-SUCEDIDA para PROFESSOR (com ProfessorEntity como Principal): " + userId);
                    }
                    filterChain.doFilter(request, response);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Se chegou aqui, significa que:
        // - O token era malformado (já tratado por JWTDecodeException)
        // - A role não foi encontrada ou não era ALUNO nem PROFESSOR
        // - A validação do token (com a secret key) falhou para a role identificada.
        // Em todos esses casos, a autenticação falhou e o acesso deve ser negado com 401.

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
    
    }
}
