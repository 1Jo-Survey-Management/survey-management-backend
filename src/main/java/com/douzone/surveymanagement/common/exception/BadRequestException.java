package com.douzone.surveymanagement.common.exception;

/**
 * 잘못된 요청에 대해 공통으로 사용할 예외 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
