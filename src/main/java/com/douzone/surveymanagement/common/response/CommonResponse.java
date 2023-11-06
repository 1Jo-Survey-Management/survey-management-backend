package com.douzone.surveymanagement.common.response;

import lombok.Getter;

/**
 * 프로젝트에서 사용할 공통 응답 객체입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
public class CommonResponse<T> {

    private final boolean success;

    private final T content;

    private final ErrorResponse<T> errorResponse;

    private static final String DEFAULT_SUCCESS_MESSAGE = "Success!!";
    private static final String DEFAULT_FAIL_MESSAGE = "Fail!!";

    private CommonResponse(boolean success, T content, ErrorResponse<T> errorResponse) {
        this.success = success;
        this.content = content;
        this.errorResponse = errorResponse;
    }

    public static CommonResponse<String> success() {
        return new CommonResponse<>(Boolean.TRUE, DEFAULT_SUCCESS_MESSAGE, null);
    }

    public static CommonResponse<String> fail() {
        return new CommonResponse<>(Boolean.FALSE, DEFAULT_FAIL_MESSAGE, null);
    }

    public static <T> CommonResponse<T> successOf(T content ) {
        return new CommonResponse<>(Boolean.TRUE, content, null);
    }
    public static CommonResponse<ErrorResponse> error(ErrorResponse errorResponse) {
        return new CommonResponse<>(Boolean.FALSE, null, errorResponse);
    }


}
