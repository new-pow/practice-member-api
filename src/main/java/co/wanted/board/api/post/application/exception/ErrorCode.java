package co.wanted.board.api.post.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NO_SUCH_POST(HttpStatus.BAD_REQUEST, "해당하는 글이 없습니다."),
    ANOTHER_AUTHOR(HttpStatus.UNAUTHORIZED, "본인의 글만 수정/삭제할 수 있습니다."),
    WRONG_ID(HttpStatus.BAD_REQUEST, "탐색 범위를 벗어났습니다.");

    private final HttpStatus code;
    private final String message;

    public String getName() {
        return name().toLowerCase();
    }
}
