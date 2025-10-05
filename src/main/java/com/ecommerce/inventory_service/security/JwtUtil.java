package com.ecommerce.inventory_service.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Extract username (subject) from JWT
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ✅ extract roles and convert to Spring Authorities
    public List<SimpleGrantedAuthority> extractRoles(String token) {
        Claims claims = extractAllClaims(token);

        // Single "role" claim
        String singleRole = claims.get("role", String.class);
        if (singleRole != null) {
            return Collections.singletonList(new SimpleGrantedAuthority(singleRole));
        }

        // Suppose roles are stored like: { "roles": ["ROLE_ADMIN", "ROLE_USER"] }
        List<String> roles = claims.get("roles", List.class);

        if (roles == null) {
            return Collections.emptyList();
        }

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // ✅ validate token
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // check expiry
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();
    }

}

