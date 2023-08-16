package co.wanted.board.api.member.application;

import co.wanted.board.api.member.application.exception.MemberException;
import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.member.infrastructure.persistence.MemberRepository;
import co.wanted.board.api.member.presentation.MemberFacade;
import co.wanted.board.api.member.presentation.dto.Signup;
import co.wanted.board.fixture.MemberFixtureFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class MemberFacadeTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberFacade memberFacade;
    @Autowired
    private MemberFixtureFactory memberFixtureFactory;

    Member testMember;

    @BeforeEach
    void init () {
        testMember =memberFixtureFactory.getDefaultMember();
    }

    @Test
    @DisplayName("중복된 메일로 회원가입을 시도하면 예외를 던진다.")
    void givenDuplicatedEmail_thenThrowsException() {
        memberRepository.save(testMember);
        String mail = "test@gmail.com";

        assertThatThrownBy(() -> memberFacade.signNewMember(new Signup.Request(mail, "newPassword1234", "새힘")))
                .isInstanceOf(MemberException.class);
    }


    @Test
    @DisplayName("중복된 유저이름으로 회원가입을 시도하면 예외를 던진다.")
    void givenDuplicatedUsername_thenThrowsException() {
        memberRepository.save(testMember);
        String name = "회원";

        assertThatThrownBy(() -> memberFacade.signNewMember(new Signup.Request("testemail2@gmail.com", "newPassword1234", name)))
                .isInstanceOf(MemberException.class);
    }
}
