package com.douzone.surveymanagement.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Some description here.
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
