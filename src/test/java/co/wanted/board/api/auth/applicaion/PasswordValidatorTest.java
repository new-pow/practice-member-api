package co.wanted.board.api.auth.applicaion;

import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.member.domain.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PasswordValidatorTest {

    Member testMember;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void init() {
        Password password = passwordEncoder.encrypt("1234asdf");

        testMember = Member.builder()
                .id(1L)
                .email("test@gmail.com")
                .username("회원")
                .password(password)
                .build();
    }


    @Test
    @DisplayName("회원 비밀번호가 맞는지 확인할 수 있다.")
    void isCorrectPassword() {
        byte[] salt = testMember.getSalt();
        Password otherPassword = passwordEncoder.encrypt("1234asdf", salt);

        assertThat(testMember.isCorrectPassword(otherPassword)).isTrue();
    }

    @Test
    @DisplayName("회원 비밀번호가 틀린지 확인할 수 있다.")
    void isIncorrectPassword() {
        byte[] salt = testMember.getSalt();
        Password otherPassword = passwordEncoder.encrypt("1234asdj", salt);

        assertThat(testMember.isCorrectPassword(otherPassword)).isFalse();
    }
}
