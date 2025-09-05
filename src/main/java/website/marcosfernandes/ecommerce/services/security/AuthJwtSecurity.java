package website.marcosfernandes.ecommerce.services.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service @Getter @Setter
public class AuthJwtSecurity {
    private final String secret = System.getenv().getOrDefault("JWT_SECRET", "secret-123");
    private final int expirationMinutes = Integer.parseInt(System.getenv().getOrDefault("JWT_EXP_MINUTES", "60"));

    public String createToken(String subject, Map<String, Object> claims) {
        Instant now = Instant.now();
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(expirationMinutes * 60L)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}
