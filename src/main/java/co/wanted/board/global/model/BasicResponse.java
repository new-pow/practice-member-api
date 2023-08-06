package co.wanted.board.global.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BasicResponse<T> {
    private final String message;
    private final T data;

    public static BasicResponse send(String message, Object data) {
        return new BasicResponse(message, data);
    }
}
