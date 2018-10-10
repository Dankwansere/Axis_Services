package com.sans.axis.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.sans.axis.security.SecurityConstants.EXPIRATION_TIME;
import static com.sans.axis.security.SecurityConstants.SECRET;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sans.axis.domain.User;



@Component
public class JwtService {
	
	private static final String ISSUER = "dankwansere";


  
    public static String genJwtToken(User user) throws IOException, URISyntaxException {
        String secretKey = "Gege";
        Date expiration = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
       
        String jwtToken;
        
        try {
        	jwtToken = JWT.create()
        			.withSubject(user.getUsername())
        			.withExpiresAt(expiration)
        			.withIssuer(ISSUER)
        			.sign(Algorithm.HMAC512(SECRET.getBytes()))
        			.toString();
        	
        } catch(Exception ex) {
        	return null;
        }
        
//		try {
//			jwtToken = Jwts.builder()
//			        .setSubject(user.getUsername())
//			        .setExpiration(expiration)
//			        .setIssuer(ISSUER)
//			        .signWith(SignatureAlgorithm.HS512, secretKey)
//			        .compact();
//		} catch (Exception e) {
//			System.out.println("Token generation error: " + e.getMessage());
//			return null;
//		}
        
        return jwtToken;
    }

}
