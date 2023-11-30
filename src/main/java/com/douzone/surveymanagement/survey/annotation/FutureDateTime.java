package com.douzone.surveymanagement.survey.annotation;

import com.douzone.surveymanagement.survey.validator.FutureDateTimeValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 입력된 시간이 현재 시간보다 이후인지 검사하기 위한 애너테이션입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureDateTimeValidator.class)
public @interface FutureDateTime {
    String message() default "입력된 날짜 및 시간은 미래 시간이어야 합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
