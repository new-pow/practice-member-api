package co.wanted.board.api.member.application;

import co.wanted.board.api.member.application.exception.ErrorCode;
import co.wanted.board.api.member.application.exception.MemberException;
import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.member.presentation.dto.Signin;
import co.wanted.board.api.member.presentation.dto.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberFacade {
    private final SignupService signupService;
    private final MemberService memberService;
    private final SigninService signinService;

    public Signup.Summary signup(Signup.Request request) {
        if (memberService.isExistMail(request.getEmail())) {
            throw new MemberException(ErrorCode.DUPLICATE_KEY_EMAIL);
        }

        if (memberService.isExistName(request.getUsername())) {
            throw new MemberException(ErrorCode.DUPLICATE_KEY_NAME);
        }

        Member savedMember = signupService.signup(request.getEmail(), request.getPassword(), request.getUsername());
        return Signup.Summary.of(savedMember);
    }

    public Signin.Response signin(Signin.Request request) {
        Member member = searchService.getMemberByEmail(request.getEmail());
        Member signinedMember = signinService.signin(member, request.getPassword());
        return Signin.Response.of(signinedMember);
    }
}
