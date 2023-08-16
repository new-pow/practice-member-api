package co.wanted.board.api.member.application;

import co.wanted.board.api.auth.application.encode.PasswordValidator;
import co.wanted.board.api.member.application.exception.ErrorCode;
import co.wanted.board.api.member.application.exception.MemberException;
import co.wanted.board.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SigninService {

    private final PasswordValidator passwordValidator;

    public Member signin (Member member, String password) {
        if (!passwordValidator.check(member, password)) {
            throw new MemberException(ErrorCode.INVALID_PASSWORD);
        }
        return member;
    }
}
