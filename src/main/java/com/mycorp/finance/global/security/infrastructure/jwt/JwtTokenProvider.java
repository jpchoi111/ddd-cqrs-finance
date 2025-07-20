package com.mycorp.finance.global.security.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;


/**
 * JWT Token utility for creating, validating tokens and extracting information.
 * This provider does not handle authorities/roles; it's focused on authentication.
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String rawSecretKey;

    private SecretKey secretKey;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(rawSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate JWT token with customerId as subject.
     *
     * @param customerId UUID of authenticated customer
     * @return signed JWT token string
     */
    public String createToken(UUID customerId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(customerId.toString())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validate the JWT token.
     *
     * @param token JWT token string
     * @return true if token is valid and not expired, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // log exception if necessary
            return false;
        }
    }

    /**
     * Extract customerId (UUID) from JWT token subject.
     *
     * @param token JWT token string
     * @return customer UUID extracted from token
     */
    public UUID getCustomerId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return UUID.fromString(claims.getSubject());
    }
}
