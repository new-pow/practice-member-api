package co.wanted.board;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@EnableJpaAuditing
@SpringBootTest
@ActiveProfiles("test")
class BoardApplicationTests {

    @Test
    void contextLoads() {
    }

}
