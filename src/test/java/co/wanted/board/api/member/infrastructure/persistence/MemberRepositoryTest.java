package co.wanted.board.api.member.infrastructure.persistence;

import co.wanted.board.api.auth.applicaion.PasswordEncoder;
import co.wanted.board.api.member.domain.Member;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    Member testMember;

    @BeforeEach
    void init() {
        testMember = Member.builder()
                .id(1L)
                .email("test@gmail.com")
                .username("회원")
                .password(passwordEncoder.encrypt("1234"))
                .build();
    }

    @Test
    @DisplayName("저장된 멤버를 조회할 수 있다.")
    void getMember() throws Exception{
        memberRepository.save(testMember);

        Member member = memberRepository.findById(1L).orElseThrow();

        assertThat(member.getEmail()).isEqualTo("test@gmail.com");
    }

}
