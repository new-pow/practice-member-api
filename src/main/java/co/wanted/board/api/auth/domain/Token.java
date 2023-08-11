package co.wanted.board.api.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Token {
    private final Long id;
    private final Long memberId;
    private AccessToken accessToken;
    private final RefreshToken refreshToken;
    private final Instant created;

    @Builder
    public Token(Long id, Long ownId, AccessToken accessToken, RefreshToken refreshToken, Instant created) {
        this.id = id;
        this.memberId = ownId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.created = created;
    }

    public static Token of(Long memberId, AccessToken accessToken, RefreshToken refreshToken, Instant now) {
        return Token.builder()
                .ownId(memberId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .created(now)
                .build();
    }

    public static Token of(Long id, Long memberId, AccessToken accessToken, RefreshToken refreshToken, Instant now) {
        return Token.builder()
                .id(id)
                .ownId(memberId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .created(now)
                .build();
    }

    @JsonIgnore
    public String getAccessTokenValue() {
        return this.accessToken.getToken();
    }

    @JsonIgnore
    public String getRefreshTokenValue() {
        return this.refreshToken.getToken();
    }

    @JsonIgnore
    public Instant getRefreshExpires() {
        return this.refreshToken.getExpiration();
    }

    public void refreshAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
