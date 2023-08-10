package co.wanted.board.api.member.presentation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface Email {
    String message() default "이메일 형식이 아닙니다. '@'를 포함하여야 합니다.";
    Class<?>[] groups() default {}; // 기본적으로 Default 그룹에 속하도록 설정
    Class<? extends Payload>[] payload() default {};
}
