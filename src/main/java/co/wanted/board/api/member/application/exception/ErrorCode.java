package co.wanted.board.api.member.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATE_KEY_EMAIL(HttpStatus.CONFLICT,"중복된 이메일이 있습니다."),
    DUPLICATE_KEY_NAME(HttpStatus.CONFLICT, "중복된 유저 이름입니다.");

    private final HttpStatus code;
    private final String message;
}
