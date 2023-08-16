package co.wanted.board.api.member.application;

import co.wanted.board.api.member.application.exception.ErrorCode;
import co.wanted.board.api.member.application.exception.MemberException;
import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.member.infrastructure.persistence.MemberRepository;
import co.wanted.board.api.member.presentation.dto.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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

    @Transactional(readOnly = true)
    public Member getMemberByEmail (String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new MemberException(ErrorCode.NO_SUCH_MEMBER));
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public Member getMemberById(Long authorId) {
        return memberRepository.findById(authorId).orElseThrow(() -> new MemberException(ErrorCode.NO_SUCH_MEMBER));
    }

    @Transactional(readOnly = true)
    public Iterable<Member> getAllMembersByIds(Set<Long> authorIds) {
        return memberRepository.findAllById(authorIds);
    }
}
