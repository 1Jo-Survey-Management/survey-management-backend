package com.douzone.surveymanagement.common.exception;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public class BadRequestException extends RuntimeException {

    private static final String MESSAGE = "잘못된 요청입니다.";
    public BadRequestException(String message) {
        super(message);
    }
}
