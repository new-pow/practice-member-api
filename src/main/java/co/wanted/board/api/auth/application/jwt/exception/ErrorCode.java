package co.wanted.board.api.auth.application.jwt.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    UNSUPPORTED_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰 타입입니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "refresh_token이 누락되었거나 grant_type이 누락되었습니다."),
    INVALID_CLIENT(HttpStatus.UNAUTHORIZED, "잘못된 member 요청입니다.");

    private final HttpStatus code;
    private final String message;

    public String getName() {
        return name().toLowerCase();
    }
}
