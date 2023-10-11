package com.douzone.surveymanagement.common.exception;

/**
 * NotFoundException 을 처리하기 위해 공용으로 사용하기 위한 Exception 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public class NotFoundElementException extends RuntimeException{
    public NotFoundElementException(String message) {
        super(message);
    }
}
