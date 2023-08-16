package co.wanted.board.global.exception;

import co.wanted.board.api.auth.application.jwt.exception.AuthException;
import co.wanted.board.api.member.application.exception.MemberException;
import co.wanted.board.api.post.application.exception.PostException;
import co.wanted.board.global.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        logException(ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.send(HttpStatus.UNAUTHORIZED, ex));
    }

    @ExceptionHandler(PostException.class)
    public ResponseEntity<ErrorResponse> handlePostException(PostException ex) {
        logException(ex);
        return ResponseEntity.status(ex.getDefaultStatus())
                .body(ErrorResponse.send(ex.getDefaultStatus(), ex));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthException ex) {
        logException(ex);
        return ResponseEntity.status(ex.getDefaultStatus())
                .body(ErrorResponse.send(ex.getDefaultStatus(), ex));
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> handleMemberException(MemberException ex) {
        logException(ex);
        return ResponseEntity.status(ex.getDefaultStatus())
                .body(ErrorResponse.send(ex.getDefaultStatus(), ex));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindErrorHandle(BindException ex) {
        logException(ex);
        FieldError fieldError = ex.getFieldError();
        assert fieldError != null;
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
