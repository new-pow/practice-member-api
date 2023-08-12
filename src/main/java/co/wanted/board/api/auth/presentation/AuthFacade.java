package co.wanted.board.api.auth.presentation;

import co.wanted.board.api.auth.application.jwt.JwtService;
import co.wanted.board.api.auth.domain.Token;
import co.wanted.board.api.auth.presentation.dto.Refresh;
import co.wanted.board.api.member.application.SearchService;
import co.wanted.board.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final JwtService jwtService;
    private final SearchService searchService;

    @Transactional
    public void refreshToken(Refresh.Request request, HttpServletResponse response) throws IOException {
        Member member = searchService.getMemberByEmail(request.getMemberEmail());
        Token newToken = jwtService.refreshAccessToken(member, request.getRefreshToken());
        jwtService.setAuthHeader(newToken, response);
    }
}
