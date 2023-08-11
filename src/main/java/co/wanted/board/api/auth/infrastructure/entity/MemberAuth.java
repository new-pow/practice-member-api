package co.wanted.board.api.auth.infrastructure.entity;

import co.wanted.board.api.auth.domain.Tokens;
import co.wanted.board.global.model.CreatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Table(name = "member_auth")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAuth extends CreatedEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private String accessToken;
    private String refreshToken;
    @Column(name = "expired_at")
    private Instant refreshTokenExpiredAt;

    @Builder
    protected MemberAuth(Long id, Long memberId, String accessToken, String refreshToken, Instant createdAt, Instant refreshTokenExpiredAt) {
        super(createdAt);
        this.id = id;
        this.memberId = memberId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiredAt = refreshTokenExpiredAt;
    }

    public static MemberAuth toEntity(Tokens token) {
        return MemberAuth.builder()
                .id(token.getId())
                .memberId(token.getMemberId())
                .accessToken(token.getAccessTokenValue())
                .refreshToken(token.getRefreshTokenValue())
                .refreshTokenExpiredAt(token.getRefreshExpires())
                .createdAt(token.getCreated())
                .build();
    }

    public Tokens toDomain() {
        return Tokens.of(id,
                memberId,
                Tokens.AccessToken.of(accessToken,null),
                Tokens.RefreshToken.of(refreshToken, refreshTokenExpiredAt),
                getCreatedAt());
    }
}
