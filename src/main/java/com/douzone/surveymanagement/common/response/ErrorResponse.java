package com.douzone.surveymanagement.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 에러를 응답하기위한 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Getter
@Schema(description = "API 응답에서 에러 정보를 제공하는 클래스")
public class ErrorResponse<T> {

    @Schema(description = "에러 메시지", example = "에러가 발생했습니다.")
    private final String message;

    @Schema(description = "에러에 대한 추가적인 내용")
    private final T content;

    private static final String METHOD_ARGUMENT_EXCEPTION_MESSAGE =
        "Method Arguments Valid Exception!";

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
