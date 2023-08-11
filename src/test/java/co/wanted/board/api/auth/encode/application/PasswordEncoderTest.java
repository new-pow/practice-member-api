package co.wanted.board.api.auth.applicaion;

import co.wanted.board.api.member.domain.Password;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class PasswordEncoderTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("비밀번호를 암호화 할 수 있다.")
    void testPasswordEncoder() throws Exception{
        //given
        String password = "password1234";
        
        //when
        Password encrypt = passwordEncoder.encrypt(password);
        log.info(encrypt.toString());
    }

    @Test
    @DisplayName("같은 비밀번호를 같은 salt로 암호화하면 해시가 같아야 한다.")
    void testPassword() throws Exception{
        //given
        String inputPassword1 = "password1234";
        String inputPassword2 = "password1234";

        //when
        Password password1 = passwordEncoder.encrypt(inputPassword1);
        byte[] salt = password1.getSalt();
        Password password2 = passwordEncoder.encrypt(inputPassword2, salt);

        assertThat(password1.getHash()).isEqualTo(password2.getHash());
        assertThat(password1).isEqualTo(password2);
    }
}
