package com.rangoli.config;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkey123456";

    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 1 day

    // Generate Token
    public static String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
            
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract Username
    public static String extractUsername(String token) {

        return extractAllClaims(token).getSubject();
    }

    // Extract Expiry
    public static Date extractExpiration(String token) {

        return extractAllClaims(token).getExpiration();
    }

    // Extract Claims
    private static Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Check Expired
    private static boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    // Validate Token
    public static boolean validateToken(String token, String username) {

        String user = extractUsername(token);

        return user.equals(username) && !isTokenExpired(token);
    }
}