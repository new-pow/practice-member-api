package co.wanted.board.api.member.application;

import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.member.infrastructure.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final MemberRepository memberRepository;


    public Member signup(String email, String password, String username) {
        Member preparedMember = Member.create(email, password, username);
        return memberRepository.save(preparedMember);
    }
}
