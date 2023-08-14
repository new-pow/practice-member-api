package co.wanted.board.api.post.application.exception;

import co.wanted.board.global.model.CodedException;
import lombok.Getter;

@Getter
public class PostException extends CodedException {

    protected PostException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode.getCode(), errorCode.getName());
    }
}
