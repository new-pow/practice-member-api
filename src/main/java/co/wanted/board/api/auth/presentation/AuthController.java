package co.wanted.board.api.auth.presentation;

import co.wanted.board.api.auth.domain.Token;
import co.wanted.board.api.auth.presentation.dto.Refresh;
import co.wanted.board.global.model.BasicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/refresh")
    public BasicResponse<Token> refreshMemberToken(@RequestBody Refresh.Request request, HttpServletResponse response) throws IOException {
        authFacade.refreshToken(request, response);
        return BasicResponse.send("AccessToken이 새로 갱신되었습니다.", null);
    }
}
