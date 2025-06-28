package com.codigo.CodeTest.util;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration}")
    private long expiration;
    
    public String generateToken(String email, Long userId) {
    	return Jwts.builder()
    			.setSubject(email)
    			.claim("userId", userId) 
    			.setIssuedAt(new Date())
    			.expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(secret), SignatureAlgorithm.HS256)
                .compact();
    }
    
    public String extractEmail(String token) {
        return Jwts
            .parser()
            .setSigningKey(getSigningKey(secret))
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSigningKey(secret)).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private Key getSigningKey(String secretKey) {
    	return Keys.hmacShaKeyFor(secret.getBytes());    
    }
    
}
