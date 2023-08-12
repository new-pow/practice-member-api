package co.wanted.board.api.auth.application.jwt;

import co.wanted.board.api.auth.domain.AccessToken;
import co.wanted.board.api.auth.domain.RefreshToken;
import co.wanted.board.api.auth.infrastructure.config.JwtProperties;
import io.jsonwebtoken.Claims;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;


@SpringBootTest
@ActiveProfiles("test")
class JwtProviderTest {

    @Autowired
    private JwtProvider provider;
    @Autowired
    private JwtProperties properties;
    private final Integer testWord = 10;

    @Test
    @DisplayName("Access token 생성할 수 있다.")
    void generateAccessToken_Success() throws Exception{
        //given
        Instant now = Instant.now();

        //when
        AccessToken accessToken = provider.generateAccessToken(testWord, now);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(accessToken).isNotNull();
            softAssertions.assertThat(accessToken.getExpiration()).isBefore(Instant.now().plusSeconds(properties.getAccessExpiresAt()));
        });
    }

    @Test
    @DisplayName("Refresh token 생성할 수 있다.")
    void generateRefreshToken_Success() throws Exception{
        //given
        Instant now = Instant.now();

        //when
        RefreshToken refreshToken = provider.generateRefreshToken(testWord, now);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(refreshToken).isNotNull();
            softAssertions.assertThat(refreshToken.getExpiration()).isBefore(Instant.now().plusSeconds(properties.getRefreshExpiresAt()));
        });
    }

    @Test
    @DisplayName("parsing을 하면 원래 데이터값을 알 수 있다.")
    void getClaim_Success() throws Exception{
        //given
        Instant now = Instant.now();

        //when
        RefreshToken refreshToken = provider.generateRefreshToken(testWord, now);
        Claims claim = provider.getClaim(refreshToken.getToken());

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(claim).isNotNull();
            softAssertions.assertThat(claim.get(properties.getRefreshClaimKey())).isEqualTo("10");
        });
    }
}
