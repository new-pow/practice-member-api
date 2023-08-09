package co.wanted.board.api.member.presentation.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface Email {
    String message() default "이메일 형식이 아닙니다. '@'를 포함하여야 합니다.";
}
