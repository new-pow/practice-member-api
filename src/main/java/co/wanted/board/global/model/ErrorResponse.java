package co.wanted.board.global.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

@Getter
public class ErrorResponse {

    private final HttpStatus httpStatus;
    private final String message;

    @Builder
    private ErrorResponse(HttpStatus status, String message) {
        this.httpStatus = status;
        this.message = message;
    }

    public static ErrorResponse send(HttpStatus status, Exception exception) {
        return new ErrorResponse(status, exception.getMessage());
    }

    public static ErrorResponse send(HttpStatus status, FieldError fieldError) {
        return new ErrorResponse(status, fieldError.getDefaultMessage());
    }
}
