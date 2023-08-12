package co.wanted.board.api.auth.application.jwt;

import co.wanted.board.api.auth.application.jwt.exception.AuthException;
import co.wanted.board.api.auth.application.jwt.exception.ErrorCode;
import co.wanted.board.api.auth.domain.AccessToken;
import co.wanted.board.api.auth.domain.RefreshToken;
import co.wanted.board.api.auth.domain.Token;
import co.wanted.board.api.auth.infrastructure.AuthRepository;
import co.wanted.board.api.auth.infrastructure.config.JwtProperties;
import co.wanted.board.api.auth.infrastructure.entity.MemberAuth;
import co.wanted.board.api.auth.presentation.dto.Logined;
import co.wanted.board.api.auth.presentation.dto.Refresh;
import co.wanted.board.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProvider jwtProvider;
    private final AuthRepository authRepository;
    private final JwtProperties properties;

    @Transactional
    public Token getToken(Long memberId) throws IOException {
        Instant now = Instant.now();
        AccessToken accessToken = jwtProvider.generateAccessToken(new Logined(memberId), now);
        RefreshToken refreshToken = jwtProvider.generateRefreshToken(new Logined(memberId), now);

        Token token = Token.of(memberId, accessToken, refreshToken, now);
        authRepository.save(MemberAuth.toEntity(token));
        return token;
    }

    public void setAuthHeader(Token token, HttpServletResponse response) {
        response.setHeader(properties.getHeaderKey(), generateHeaderValue(token));
    }

    private String generateHeaderValue(Token token) {
        return String.format("%s %s", properties.getAccessTokenType(), token.getAccessTokenValue());
    }

    @Transactional
    public Token refreshAccessToken(Member member, String refreshToken) throws IOException {
        Token token = authRepository.findByIdAndRefreshToken(member.getId(), refreshToken)
                .orElseThrow(() -> new AuthException(ErrorCode.INVALID_TOKEN))
                .toDomain();

        if (!validMember(member, token)) {
            throw new AuthException(ErrorCode.INVALID_CLIENT);
        }

        AccessToken accessToken = jwtProvider.refreshAccessToken(member.getId(), token.getRefreshTokenValue());
        token.refreshAccessToken(accessToken);
        authRepository.save(MemberAuth.toEntity(token));

        return token;
    }

    private boolean validMember(Member request, Token token) {
        if (request.getId() != token.getMemberId()) {
            return false;
        }
        return true;
    }
}
