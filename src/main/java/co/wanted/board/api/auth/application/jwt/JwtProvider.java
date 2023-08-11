package co.wanted.board.api.auth.application.jwt;

import co.wanted.board.api.auth.application.jwt.exception.AuthException;
import co.wanted.board.api.auth.application.jwt.exception.ErrorCode;
import co.wanted.board.api.auth.domain.AccessToken;
import co.wanted.board.api.auth.domain.RefreshToken;
import co.wanted.board.api.auth.infrastructure.config.JwtProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final ObjectMapper objectMapper;
    private final JwtProperties properties;

    public AccessToken generateAccessToken(Object object, Instant now) throws IOException {
        Instant expireAt = now.plusSeconds(properties.getAccessExpiresAt());

        String token = Jwts.builder()
                .claim(properties.getAccessClaimKey(), objectMapper.writeValueAsString(object))
                .setExpiration(Date.from(expireAt))
                .setIssuedAt(Date.from(now))
                .signWith(SignatureAlgorithm.HS256, properties.getSecretKey())
                .compact();

        return AccessToken.of(token, expireAt);
    }

    public RefreshToken generateRefreshToken(Object object, Instant now) throws IOException {
        Instant expireAt = now.plusSeconds(properties.getRefreshExpiresAt());
        String token = Jwts.builder()
                .claim(properties.getRefreshClaimKey(), objectMapper.writeValueAsString(object))
                .setExpiration(Date.from(expireAt))
                .setIssuedAt(Date.from(now))
                .signWith(SignatureAlgorithm.HS256, properties.getSecretKey())
                .compact();

        return RefreshToken.of(token, expireAt);
    }

    public AccessToken refreshAccessToken(Long memberId, String refreshToken) throws IOException {
        Claims claims = Jwts.parser()
                .setSigningKey(properties.getSecretKey())
                .parseClaimsJws(refreshToken)
                .getBody();

        if (!validateRefreshToken(claims)) {
            throw new JwtException(ErrorCode.EXPIRED_TOKEN.getMessage());
        }

        Instant now = Instant.now();
        return generateAccessToken(memberId, now);
    }

    private boolean validateRefreshToken(Claims claims) {
        Date expiration = claims.getExpiration();
        Date now = Date.from(Instant.now());
        return now.before(expiration);
    }

    public Claims getClaim(String token) throws JwtException {
        try {
            return Jwts.parser()
                .setSigningKey(properties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
        } catch (MalformedJwtException e) {
            throw new AuthException(ErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new AuthException(ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new AuthException(ErrorCode.UNSUPPORTED_TYPE_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new AuthException(ErrorCode.INVALID_REQUEST);
        }
    }
}
