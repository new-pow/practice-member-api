package co.wanted.board.api.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class AccessToken {

    private final String token;
    private final Instant expiration;

    public static AccessToken of(String token, Instant expireAt) {
        return new AccessToken(token, expireAt);
    }
}
