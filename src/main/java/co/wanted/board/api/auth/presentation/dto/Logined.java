package co.wanted.board.api.auth.presentation.dto;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Logined {

    private Long memberId;

    @Builder
    public Logined(Long memberId) {
        this.memberId = memberId;
    }

    public static Logined from(Claims claims) {
        LoginedBuilder builder = Logined.builder();
        Optional.ofNullable(claims.get("memberId"))
                .ifPresent(id -> builder.memberId((Long)id));
        return builder.build();
    }

    public static Logined empty() {
        return new Logined();
    }

    @Override
    public int hashCode() {
        return this.getMemberId().intValue();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
