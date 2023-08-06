package co.wanted.board.api.member.application;

import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.member.exception.DuplicateEmailException;
import co.wanted.board.api.member.presentation.dto.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberFacade {
    private final SignupService signupService;
    private final MemberService memberService;

    public Signup.Summary signup(Signup.Request request) {
        if (memberService.isExistMember(request.getEmail())) {
            throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
        }

        Member savedMember = signupService.signup(request.getEmail(), request.getPassword(), request.getUsername());
        return Signup.Summary.of(savedMember);
    }
}
