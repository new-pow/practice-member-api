package co.wanted.board.global.exception;

import co.wanted.board.api.member.exception.DuplicateEmailException;
import co.wanted.board.global.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmailException(DuplicateEmailException ex) {
        logException(ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.send(HttpStatus.CONFLICT, ex));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindErrorHandle(BindException ex) {
        logException(ex);
        return ResponseEntity.badRequest()
                .body(ErrorResponse.send(HttpStatus.BAD_REQUEST, ex));
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
