package co.wanted.board.api.member.application;

import co.wanted.board.api.member.application.exception.ErrorCode;
import co.wanted.board.api.member.application.exception.MemberException;
import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.member.infrastructure.persistence.MemberRepository;
import co.wanted.board.api.member.presentation.dto.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final MemberRepository memberRepository;

    public boolean isExistMail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean isExistName(String username) {
        return memberRepository.existsByUsername(username);
    }

    public Member getMemberByEmail (String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new MemberException(ErrorCode.NO_SUCH_MEMBER));
    }

    public void checkValidNewMember(Signup.Request request) {
        if (isExistMail(request.getEmail())) {
            throw new MemberException(ErrorCode.DUPLICATE_KEY_EMAIL);
        }
        if (isExistName(request.getUsername())) {
            throw new MemberException(ErrorCode.DUPLICATE_KEY_NAME);
        }
    }

    public boolean isExistMember(Long memberId) {
        return memberRepository.existsById(memberId);
    }

    public Member getMemberById(Long authorId) {
        return memberRepository.findById(authorId).orElseThrow(() -> new MemberException(ErrorCode.NO_SUCH_MEMBER));
    }
}
