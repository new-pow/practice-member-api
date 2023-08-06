package co.wanted.board.api.member.application;

import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.member.exception.DuplicateEmailException;
import co.wanted.board.api.member.infrastructure.persistence.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BindException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class SignupServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    SignupService signupService;

    Member testMember;

    @BeforeEach
    void init () {
        String mail = "test@gmail.com";
        String password = "password1234";
        String name = "user";

        testMember = Member.builder()
                .id(1L)
                .email(mail)
                .password(password)
                .username(name)
                .build();
    }

    @Nested
    @DisplayName("회원 가입 시 ")
    class SignupTest {

        @DisplayName("회원가입을 할 수 있다.")
        @Test
        void signup_Success() {
            String mail = "test@gmail.com";
            String password = "password1234";
            String name = "test";
            signupService.signup(mail, password, name);

            assertThat(memberRepository.existsByEmail(mail)).isTrue();
        }

        @DisplayName("중복된 로그인 아이디로 회원가입을 시도하면 예외를 던진다.")
        @Test
        void givenDuplicatedEmail_thenThrowsException() {
            String mail = "test@gmail.com";
            memberRepository.save(testMember);

            assertThatThrownBy(() -> signupService.signup(mail, "newPassword1234", "새힘"))
                    .isInstanceOf(DuplicateEmailException.class);
        }
    }
}
