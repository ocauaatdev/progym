package com.progym.progym.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTProfessorProvider {
    
    @Value("${security.token.secret.professor}")
    private String secretKey;

    public DecodedJWT validateToken (String token){
       
        token = token.replace("Bearer ", "");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            var tokenDecoded = JWT.require(algorithm)
            .build()
            .verify(token);

            return tokenDecoded;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
           
            return null;
        }
    }
}
