package co.wanted.board.global.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

@Getter
public class ErrorResponse {

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    @Builder
    private ErrorResponse(HttpStatus status, String errorCode, String message) {
        this.httpStatus = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public static ErrorResponse send(HttpStatus status, CodedException exception) {
        return new ErrorResponse(status, exception.getDefaultCode(), exception.getMessage());
    }

    public static ErrorResponse send(HttpStatus status, FieldError fieldError) {
        return new ErrorResponse(status, fieldError.getField(), fieldError.getDefaultMessage());
    }

    public static ErrorResponse send(HttpStatus status, Exception ex) {
        return new ErrorResponse(status, null ,ex.getMessage());
    }
}
