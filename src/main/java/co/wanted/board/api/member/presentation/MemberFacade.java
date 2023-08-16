package co.wanted.board.api.member.presentation;

import co.wanted.board.api.auth.application.jwt.JwtService;
import co.wanted.board.api.auth.domain.Token;
import co.wanted.board.api.member.application.SearchService;
import co.wanted.board.api.member.application.SigninService;
import co.wanted.board.api.member.application.SignupService;
import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.member.presentation.dto.Signin;
import co.wanted.board.api.member.presentation.dto.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MemberFacade {

    private final SearchService searchService;
    private final SignupService signupService;
    private final SigninService signinService;
    private final JwtService jwtService;

    public Signup.Summary signNewMember (Signup.Request request) {
        searchService.checkValidNewMember(request);
        Member savedMember = signupService.signup(request.getEmail(), request.getPassword(), request.getUsername());
        return Signup.Summary.of(savedMember);
    }

    public Signin.Response memberAuth (Signin.Request request, HttpServletResponse response) throws IOException {
        Member member = searchService.getMemberByEmail(request.getEmail());
        Member signinedMember = signinService.signin(member, request.getPassword());

        Token token = jwtService.getToken(signinedMember.getId());
        jwtService.setAuthHeader(token, response);

        return Signin.Response.of(signinedMember, token);
    }

}
