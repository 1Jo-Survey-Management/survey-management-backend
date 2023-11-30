package com.douzone.surveymanagement.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Method Argument Valid Exception 의 경우 파라미터 객체를 매핑해주기 위한 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@RequiredArgsConstructor
public class MethodArgumentValidError {

    private final String field;
    private final Object rejectValue;
    private final String defaultMessage;

}
