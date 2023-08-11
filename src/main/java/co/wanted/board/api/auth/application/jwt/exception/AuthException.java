package co.wanted.board.api.auth.application.jwt.exception;

import co.wanted.board.global.model.CodedException;

public class AuthException extends CodedException {

    public AuthException(ErrorCode code) {
        super(code.getMessage(), code.getCode(), code.getName());
    }
}
