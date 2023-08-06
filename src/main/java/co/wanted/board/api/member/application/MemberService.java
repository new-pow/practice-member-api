package co.wanted.board.api.member.application;

import co.wanted.board.api.member.infrastructure.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean isExistMember(String email) {
        return memberRepository.existsByEmail(email);
    }
}
