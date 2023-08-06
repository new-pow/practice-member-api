package co.wanted.board.api.member.application;

import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.member.exception.DuplicateMemberException;
import co.wanted.board.api.member.presentation.dto.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberFacade {
    private final SignupService signupService;
    private final MemberService memberService;

    public Signup.Summary signup(Signup.Request request) {
        if (memberService.isExistMail(request.getEmail())) {
            throw new DuplicateMemberException("이미 존재하는 이메일입니다.");
        }

        if (memberService.isExistName(request.getUsername())) {
            throw new DuplicateMemberException("이미 존재하는 회원 이름입니다.");
        }

        Member savedMember = signupService.signup(request.getEmail(), request.getPassword(), request.getUsername());
        return Signup.Summary.of(savedMember);
    }
}
