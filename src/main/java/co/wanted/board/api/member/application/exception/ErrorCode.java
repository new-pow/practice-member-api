package co.wanted.board.api.member.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATE_KEY_EMAIL(HttpStatus.CONFLICT,"중복된 이메일이 있습니다."),
    DUPLICATE_KEY_NAME(HttpStatus.CONFLICT, "중복된 회원 이름입니다."),
    NO_SUCH_MEMBER(HttpStatus.UNAUTHORIZED, "해당하는 이메일을 가진 회원이 없습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");

    private final HttpStatus code;
    private final String message;
}
