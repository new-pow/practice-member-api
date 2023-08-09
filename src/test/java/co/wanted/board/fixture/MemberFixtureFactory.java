package co.wanted.board.fixture;

import co.wanted.board.api.auth.applicaion.PasswordEncoder;
import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.member.domain.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberFixtureFactory {

    private Member defaultMember;
    private final PasswordEncoder passwordEncoder;

    public MemberFixtureFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        Password encodedpassword = passwordEncoder.encrypt("1234");
        defaultMember = Member.builder()
                .id(1L)
                .email("test@gmail.com")
                .username("회원")
                .password(encodedpassword)
                .build();;
    }

    public Member getDefaultMember() {
        return defaultMember;
    }

    public Member getMember (Long id, String email, String password, String name) {
        Password encodedpassword = passwordEncoder.encrypt(password);
        return Member.builder()
                .id(id)
                .email(email)
                .username(name)
                .password(encodedpassword)
                .build();
    }
}
