package co.wanted.board.api.auth.infrastructure.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtProperties {
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.authorization-header}")
    private String headerKey;

    @Value("${jwt.access.claim-key}")
    private String accessClaimKey;
    @Value("${jwt.access.token-type}")
    private String accessTokenType;
    @Value("${jwt.access.expires-at}")
    private int accessExpiresAt;

    @Value("${jwt.refresh.claim-key}")
    private String refreshClaimKey;
    @Value("${jwt.refresh.token-type}")
    private String refreshTokenType;
    @Value("${jwt.refresh.expires-at}")
    private int refreshExpiresAt;
}
