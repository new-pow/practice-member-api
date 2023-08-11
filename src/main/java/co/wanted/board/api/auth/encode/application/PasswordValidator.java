package co.wanted.board.api.auth.applicaion;

import co.wanted.board.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordValidator {
    private final PasswordEncoder passwordEncoder;

    public boolean check(Member member, String inputPassword) {
        return passwordEncoder.encrypt(inputPassword, member.getSalt())
                .equals(member.getPassword());
    }

}
