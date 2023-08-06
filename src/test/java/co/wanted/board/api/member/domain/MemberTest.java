package co.wanted.board.api.member.domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    Member testMember;

    @BeforeEach
    void init() {
        testMember = Member.builder()
                .id(new Member.MemberId(1L))
                .email("test@gmail.com")
                .password("1234asdf")
                .name("새힘")
                .build();
    }

    @Test
    @DisplayName("회원 생성을 할 수 있다.")
    void create() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(testMember.getEmail()).isEqualTo("test@gmail.com");
            softAssertions.assertThat(testMember.getPassword()).isEqualTo("1234asdf");
            softAssertions.assertThat(testMember.getName()).isEqualTo("새힘");
        });
    }

    @Test
    @DisplayName("회원 비밀번호가 맞는지 확인할 수 있다.")
    void isCorrectPassword() {
        String otherPassword = "1234asdf";

        assertThat(testMember.isCorrectPassword(otherPassword)).isTrue();
    }

    @Test
    @DisplayName("회원 비밀번호가 틀린지 확인할 수 있다.")
    void isIncorrectPassword() {
        String otherPassword = "1234asdj";

        assertThat(testMember.isCorrectPassword(otherPassword)).isFalse();
    }
}
