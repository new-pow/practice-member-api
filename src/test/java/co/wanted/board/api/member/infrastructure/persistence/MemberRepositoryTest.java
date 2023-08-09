package co.wanted.board.api.member.infrastructure.persistence;

import co.wanted.board.api.member.domain.Member;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager entityManager;

    Member testMember;

    @BeforeEach
    void init() {
        testMember = Member.builder()
                .id(1L)
                .email("test@gmail.com")
                .password("1234asdf")
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
