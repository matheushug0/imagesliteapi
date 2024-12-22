package br.com.mh.imageliteapi.application.jwt;

import br.com.mh.imageliteapi.domain.AccessToken;
import br.com.mh.imageliteapi.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecretKeyGenerator secretKeyGenerator;

    public AccessToken getAccessToken(User user) {

        SecretKey secretKey = secretKeyGenerator.getSecretKey();
        Date expirationDate = getExpirationDateFromToken();
        Map<String, Object> claimsFromToken = getClaimsFromToken(user);

        String token = Jwts
                .builder()
                .signWith(secretKey)
                .subject(user.getEmail())
                .expiration(expirationDate)
                .claims(claimsFromToken)
                .compact();

        return new AccessToken(token);
    }

    private Date getExpirationDateFromToken() {
        long expirationMinutes = 60L;
        LocalDateTime now = LocalDateTime.now().plusMinutes(expirationMinutes);
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Map<String, Object> getClaimsFromToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getUsername());
        claims.put("ID", user.getId());
        return claims;
    }

    public String getEmailFromToken(String token) {
        try {
            JwtParser build = Jwts.parser()
                    .verifyWith(secretKeyGenerator.getSecretKey())
                    .build();

            Claims payload = build.parseSignedClaims(token)
                    .getPayload();

            return payload.getSubject();
        }
        catch (JwtException e) {
            throw new InvalidTokenException(e.getMessage());
        }
    }
}
