package co.wanted.board.api.member.application;

import co.wanted.board.api.auth.encode.application.PasswordEncoder;
import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.member.domain.Password;
import co.wanted.board.api.member.infrastructure.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member signup(String email, String password, String username) {
        Password encodedPassword = passwordEncoder.encrypt(password);
        Member preparedMember = Member.create(email, encodedPassword, username);


        return memberRepository.save(preparedMember);
    }
}
