package com.expenseTracker.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    private static String jwtSecret = "#exp-25";
    
    public static String generateToken(String username, int id) {
        return Jwts.builder()
            .setSubject(username)
            .claim("userId", id)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() +  60 * 60 * 1000))
            .signWith(SignatureAlgorithm.HS256, jwtSecret)
            .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false; 
        }
    }

    public static String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }
    
    public static int getUserIdFromToken(String token) {
        return (Integer) Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody()
            .get("userId");
    }
}
