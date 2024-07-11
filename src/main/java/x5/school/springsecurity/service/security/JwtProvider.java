package x5.school.springsecurity.service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import x5.school.springsecurity.service.security.pojo.Jwt;
import x5.school.springsecurity.service.security.pojo.JwtUser;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private static final String CLAIM_USERNAME_KEY = "username";
    private static final String CLAIM_AUTH_KEY = "auth";
    private Key secretKey;
    private final Duration expire;

    public JwtProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${security.jwt.expire:24h}") Duration expire) {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        this.expire = expire;
    }

    public Jwt createToken(UUID id, String name, String roleName) {
        var claims = Jwts.claims().setSubject(id.toString());
        claims.put(CLAIM_USERNAME_KEY, name);
        claims.put(CLAIM_AUTH_KEY, roleName);

        var now = LocalDateTime.now();
        var expired = LocalDateTime.from(expire.addTo(now));

        var token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant(ZoneOffset.UTC)))
                .setExpiration(Date.from(expired.toInstant(ZoneOffset.UTC)))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return new Jwt(token, expired);
    }

    public Authentication getAuthentication(String token) {
        validateToken(token);

        return new UsernamePasswordAuthenticationToken(
                new JwtUser(getUserId(token), getUserName(token)),
                null,
                getUserAuthorities(token));
    }

    private UUID getUserId(String token) {
        var idString =
                Optional.ofNullable(
                                Jwts.parser()
                                        .setSigningKey(secretKey)
                                        .parseClaimsJws(token)
                                        .getBody()
                                        .getSubject())
                        .orElseThrow();
        return UUID.fromString(idString);
    }

    private String getUserName(String token) {
        return
                Optional.ofNullable(
                                Jwts.parser()
                                        .setSigningKey(secretKey)
                                        .parseClaimsJws(token)
                                        .getBody()
                                        .get(CLAIM_USERNAME_KEY, String.class))
                        .orElseThrow();
    }

    private List<GrantedAuthority> getUserAuthorities(String token) {
        var authorities =
                Optional.ofNullable(
                                Jwts.parser()
                                        .setSigningKey(secretKey)
                                        .parseClaimsJws(token)
                                        .getBody()
                                        .get(CLAIM_AUTH_KEY, String.class))
                        .orElseThrow();
        return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private void validateToken(String token) {
        try {
            getUserId(token);
            getUserName(token);
            getUserAuthorities(token);
        } catch (Exception e) {
            throw new UnsupportedJwtException("Невозможно извлечь данные пользователя", e);
        }
    }
}

