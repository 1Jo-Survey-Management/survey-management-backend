package com.douzone.surveymanagement.common.exception;

/**
 * 업로드 파일의 타입이 적절하지 않을경우의 Exception 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public class NotAcceptableFileException extends RuntimeException {
    private static final String MESSAGE = "적절하지 않은 파일입니다.";

    public NotAcceptableFileException() {
        super(MESSAGE);
    }
}
