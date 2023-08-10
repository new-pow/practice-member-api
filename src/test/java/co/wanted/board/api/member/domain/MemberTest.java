package co.wanted.board.api.member.domain;

import co.wanted.board.api.auth.applicaion.PasswordEncoder;
import co.wanted.board.api.auth.applicaion.Pbkdf2Encoder;
import co.wanted.board.fixture.MemberFixtureFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MemberTest {

    @Autowired
    private MemberFixtureFactory memberFixtureFactory;

    @Test
    @DisplayName("회원 생성을 할 수 있다.")
    void create() {
        Member defaultMember = memberFixtureFactory.getDefaultMember();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(defaultMember.getEmail()).isEqualTo("test@gmail.com");
        });
    }

}
