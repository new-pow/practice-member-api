package co.wanted.board.api.member.application.exception;

import co.wanted.board.global.model.CodedException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MemberException extends CodedException {

    public MemberException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode.getCode(), errorCode.getName());
    }
}
