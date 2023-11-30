package com.douzone.surveymanagement.survey.validator;

import com.douzone.surveymanagement.survey.annotation.FutureDateTime;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 들어온 value가 현재 시간보다 이후인지를 검사하는 Validator 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public class FutureDateTimeValidator implements ConstraintValidator<FutureDateTime, LocalDateTime> {


    /**
     * 현재 시간을 value로 받고 해당 value가 유효한지 판별하는 메서드 입니다.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return 유효하면 true, 유효하지 않으면 false
     * @author : 강명관
     */
    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {

        if (Objects.isNull(value)) {
            return false;
        }
        LocalDateTime currentDateTime = LocalDateTime.now();
        return value.isAfter(currentDateTime);
    }
}
