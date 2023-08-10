package co.wanted.board.global.exception;

import co.wanted.board.api.member.application.exception.MemberException;
import co.wanted.board.global.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmailException(MemberException ex) {
        logException(ex);
        return ResponseEntity.status(ex.getCode())
                .body(ErrorResponse.send(ex.getCode(), ex));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindErrorHandle(BindException ex) {
        logException(ex);
        FieldError fieldError = ex.getFieldError();
        return ResponseEntity.badRequest()
                .body(ErrorResponse.send(HttpStatus.BAD_REQUEST, fieldError));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        logException(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.send(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    private void logException(Exception ex) {
        log.error("Exception occured: ", ex);
        log.error("Exception message = {}", ex.getMessage());
    }
}
