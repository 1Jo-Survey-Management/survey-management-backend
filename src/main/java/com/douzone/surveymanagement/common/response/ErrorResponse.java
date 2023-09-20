package com.douzone.surveymanagement.common.response;

import lombok.Getter;

/**
 * 에러를 응답하기위한 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
public class ErrorResponse<T> {

    private final String message;

    private final T content;

    private static final String METHOD_ARGUMENT_EXCEPTION_MESSAGE = "Method Arguments Valid Exception!";

    private ErrorResponse(String message, T content) {
        this.message = message;
        this.content = content;
    }

    /**
     * 일반적인 에러를 답하기 위한 메서드 입니다.
     *
     * @param message 에러 메시지
     * @return ErrorResponse
     * @author : 강명관
     */
    public static <T> ErrorResponse<T> of(String message) {
        return new ErrorResponse<>(message, null);
    }

    /**
     * MethodArgumentValidException 응답을 위한 ErrorResponse 객체를 만드는 메서드 입니다.
     *
     * @param methodArgumentValidErrors 인자에 대한 정보를 담은 MethodArgumentValidError 객체입니다.
     * @return ErrorResponse
     * @author : 강명관
     */
    public static <T> ErrorResponse<T> ofMethodArgumentValidError(T methodArgumentValidErrors) {
        return new ErrorResponse<>(METHOD_ARGUMENT_EXCEPTION_MESSAGE, methodArgumentValidErrors);
    }


}
