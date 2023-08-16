package co.wanted.board.api.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class RefreshToken {

    private final String token;
    private final Instant expiration;

    public static RefreshToken of(String token, Instant expireAt) {
        return new RefreshToken(token, expireAt);
    }
}
