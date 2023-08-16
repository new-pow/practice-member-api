package co.wanted.board.api.member.application;

import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.member.infrastructure.persistence.MemberRepository;
import co.wanted.board.fixture.MemberFixtureFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class SignupServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    SignupService signupService;
    @Autowired
    MemberFixtureFactory memberFixtureFactory;

    Member testMember;

    @BeforeEach
    void init () {
        testMember = memberFixtureFactory.getDefaultMember();
    }

    @Nested
    @DisplayName("회원 가입 시 ")
    class SignupTest {

        @DisplayName("회원가입을 할 수 있다.")
        @Test
        void signupSuccess() {
            String mail = "test@gmail.com";
            String password = "password1234";
            String name = "test";
            signupService.signup(mail, password, name);

            assertThat(memberRepository.existsByEmail(mail)).isTrue();
        }
    }
}
