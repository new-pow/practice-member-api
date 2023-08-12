package co.wanted.board.global.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CodedException extends RuntimeException {

    private final HttpStatus defaultStatus;
    private final String defaultCode;

    protected CodedException(String message, HttpStatus defaultStatus, String defaultCode) {
        super(message);
        this.defaultStatus = defaultStatus;
        this.defaultCode = defaultCode;
    }
}
