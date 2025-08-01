package com.progym.progym.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {


    @Autowired
    private final SecurityFilter securityFilter;


    SecurityConfig(SecurityFilter securityFilter) { 
    this.securityFilter = securityFilter;
}
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                auth.requestMatchers("/aluno/cadastrar").permitAll()
                .requestMatchers("/aluno/auth").permitAll()
                .requestMatchers("/professor/cadastrar").permitAll()
                .requestMatchers("/professor/auth").permitAll()
                .requestMatchers("/treinos/createExercicio").permitAll();
               
                auth.anyRequest().authenticated();
            })
            .addFilterBefore(securityFilter, BasicAuthenticationFilter.class)
            ;
            return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
