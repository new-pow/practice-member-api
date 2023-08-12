package co.wanted.board.api.member.domain;

import co.wanted.board.fixture.MemberFixtureFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

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
